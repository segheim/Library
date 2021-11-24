package com.epam.jwd.library.service;

import com.epam.jwd.library.model.Account;

import java.util.List;
import java.util.Optional;

public interface BasicAccountService<T> {

    Optional<T> create(Account account);

    List<T> findAll();

    Optional<T> authenticate(String login, String password);

}
