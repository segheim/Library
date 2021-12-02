package com.epam.jwd.library.service;

import com.epam.jwd.library.model.Entity;

import java.util.List;
import java.util.Optional;

public interface BasicAccountService<T extends Entity> {

    Optional<T> create(String login, String password, String firstName, String LastName);

    List<T> findAll();

    Optional<T> authenticate(String login, String password);

}
