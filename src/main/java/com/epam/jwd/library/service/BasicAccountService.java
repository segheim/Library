package com.epam.jwd.library.service;

import java.util.List;
import java.util.Optional;

public interface BasicAccountService<T> {

    boolean create(String login, String password, String firstName, String LastName);

    List<T> findAll();

    Optional<T> authenticate(String login, String password);

}
