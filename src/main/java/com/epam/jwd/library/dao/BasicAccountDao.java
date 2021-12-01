package com.epam.jwd.library.dao;

import java.util.Optional;

public interface BasicAccountDao<T> {

    Optional<T> readByLogin(String login);

}
