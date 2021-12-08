package com.epam.jwd.library.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.epam.jwd.library.connection.ConnectionPool;
import com.epam.jwd.library.dao.AccountDao;
import com.epam.jwd.library.dao.AccountDetailsDao;
import com.epam.jwd.library.exception.AccountDaoException;
import com.epam.jwd.library.exception.ServiceException;
import com.epam.jwd.library.model.Account;
import com.epam.jwd.library.model.AccountDetails;
import com.epam.jwd.library.validation.AccountValidator;
import com.epam.jwd.library.validation.FirstLastNameValidator;
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

    private AccountService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public Optional<Account> create(String login, String password, String firstName, String lastName) {
        Optional<Account> createAccountWithDetails = Optional.empty();
        Connection connection = ConnectionPool.lockingPool().takeConnection();
        try{
            checkAccountData(login, password, firstName, lastName);
            final Account account = createAccountWithHashedPassword(login, password);
            connection.setAutoCommit(false);
            AccountDao accountDao = AccountDao.getInstance();
            AccountDetailsDao accountDetailsDao = AccountDetailsDao.getInstance();
            final Account createdAccount = accountDao.create(account).orElseThrow(() -> new ServiceException("could not create account"));
            final Long idAccount = createdAccount.getId();
            accountDetailsDao.create(new AccountDetails(idAccount, firstName, lastName));
            createAccountWithDetails = accountDao.readByLogin(login);
            if (createAccountWithDetails.isPresent()) {
                connection.commit();
            } else {
                connection.rollback();
            }
        } catch (SQLException e) {
            LOG.error("sql error, Database access error connection commit", e);
        } catch (ServiceException e) {
            LOG.error("could not create new account", e);
            try {
                connection.rollback();
            } catch (SQLException ex) {
                LOG.error("Database access error occurs connection rollback", ex);
            }
        } finally {
            try {
                connection.setAutoCommit(true);
                connection.close();
            } catch (SQLException e) {
                LOG.error("Database access error occurs connection close", e);
            }
        }
        return createAccountWithDetails;
    }

    private Account createAccountWithHashedPassword(String login, String password) {
        final String hashedPassword = BCrypt.withDefaults().hashToString(MIN_COST_FOR_HASHING, password.toCharArray());
        return new Account(login, hashedPassword);
    }

    private void checkAccountData(String login, String password, String firstName, String lastName) throws ServiceException {
        if (!AccountValidator.getInstance().validate(login, password)
                || !FirstLastNameValidator.getInstance().validate(firstName, lastName)) {
            throw new ServiceException("Account data are not valid");
        }
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
        if (!AccountValidator.getInstance().validate(login, password)) {
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
