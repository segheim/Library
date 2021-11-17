package com.epam.jwd.library.service;

import com.epam.jwd.library.model.Account;

import java.util.Optional;

public interface BasicAccountService {

    Optional<Account> authenticate(String login, String password);
}
