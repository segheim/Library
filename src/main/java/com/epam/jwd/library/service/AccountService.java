package com.epam.jwd.library.service;

import com.epam.jwd.library.dao.AccountDao;
import com.epam.jwd.library.model.Account;

import java.util.Optional;

public class AccountService implements BasicAccountService{

    private final AccountDao accountDao;

    public AccountService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public Optional<Account> authenticate(String login, String password) {
        final Optional<Account> readAccount = accountDao.readByLogin(login);
        final Optional<Account> checkedAccount = readAccount.filter(account -> account.getPassword().equals(password));
        return checkedAccount;
    }

    public static AccountService getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final AccountService INSTANCE = new AccountService(AccountDao.getInstance());
    }
}
