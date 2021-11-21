package com.epam.jwd.library.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.epam.jwd.library.dao.AccountDao;
import com.epam.jwd.library.model.Account;

import java.util.List;
import java.util.Optional;

public class AccountService implements BasicAccountService, Service<Account>{

    private static final char[] PASSWORD_FOR_FUN = "bcrypt".toCharArray();
    private final AccountDao accountDao;

    public AccountService(AccountDao accountDao) {
        this.accountDao = accountDao;
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

    @Override
    public Optional<Account> create(Account account) {
        final String rawPassword = account.getPassword();
        final String hashedPassword = BCrypt.withDefaults().hashToString(4, rawPassword.toCharArray());
        final Account hashedAccount = account.withPassword(hashedPassword);
        return accountDao.create(hashedAccount);
    }

    @Override
    public Optional<Account> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Account> findAll() {
        return accountDao.readAll();
    }

    @Override
    public Optional<Account> update(Account entity) {
        return Optional.empty();
    }

    @Override
    public boolean delete(Account entity) {
        return false;
    }

    public static AccountService getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final AccountService INSTANCE = new AccountService(AccountDao.getInstance());
    }
}
