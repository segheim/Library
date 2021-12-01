package com.epam.jwd.library.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.epam.jwd.library.connection.ConnectionPool;
import com.epam.jwd.library.dao.AccountDao;
import com.epam.jwd.library.dao.AccountDetailsDao;
import com.epam.jwd.library.exception.AccountDaoException;
import com.epam.jwd.library.model.Account;
import com.epam.jwd.library.model.AccountDetails;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AccountService implements BasicAccountService<Account>, Service<Account> {

    private static final Logger LOG = LogManager.getLogger(AccountService.class);

    private static final char[] PASSWORD_FOR_FUN = "bcrypt".toCharArray();
    private static final int MIN_COST_FOR_HASHING = 4;

    private final AccountDao accountDao;

    public AccountService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public boolean create(String login, String password, String firstName, String lastName) {
        boolean createAccountWithDetails = false;
        final String hashedPassword = BCrypt.withDefaults().hashToString(MIN_COST_FOR_HASHING, password.toCharArray());
        final Account account = new Account(login, hashedPassword);
        try (Connection connection = ConnectionPool.lockingPool().takeConnection()) {
            connection.setAutoCommit(false);
            AccountDao accountDao = AccountDao.getInstance();
            AccountDetailsDao accountDetailsDao = AccountDetailsDao.getInstance();
            final Long idAccount = accountDao.create(account)
                    .map(Account::getId)
                    .orElseThrow(() -> new AccountDaoException("could not create account"));
            if (accountDetailsDao.create(new AccountDetails(idAccount, firstName, lastName)).isPresent()) {
                createAccountWithDetails = true;
            }
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            LOG.error("sql error, database access error occurs", e);
        } catch (InterruptedException e) {
            LOG.error("method takeConnection from ConnectionPool was interrupted", e);
            Thread.currentThread().interrupt();
        } catch (AccountDaoException e) {
            LOG.error("could not create new account", e);
        }
        return createAccountWithDetails;
    }

    @Override
    public Optional<Account> findById(Long id) {
        return accountDao.read(id);
    }

    @Override
    public List<Account> findAll() {
        return accountDao.readAll();
    }

    @Override
    public boolean delete(Long id) {
        return accountDao.delete(id);
    }

    @Override
    public Optional<Account> authenticate(String login, String password) {
        if (login == null || password == null) {
            return Optional.empty();
        }
        final Optional<Account> readAccount = accountDao.readByLogin(login);
        if (readAccount.isPresent()) {
            return readAccount.filter(account -> BCrypt.verifyer().verify(password.toCharArray(),
                    account.getPassword().toCharArray()).verified);
        } else {
            protectFromTimingAttack(password);
            return Optional.empty();
        }
    }

    private void protectFromTimingAttack(String password) {
        BCrypt.verifyer().verify(password.toCharArray(), PASSWORD_FOR_FUN);
    }


    public static AccountService getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final AccountService INSTANCE = new AccountService(AccountDao.getInstance());
    }
}
