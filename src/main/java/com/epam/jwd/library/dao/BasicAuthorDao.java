package com.epam.jwd.library.dao;

import com.epam.jwd.library.exception.AuthorDaoException;

import java.util.Optional;

public interface BasicAuthorDao<T> {

    Optional<T> readAuthorByFirstLastName(T t) throws AuthorDaoException;

}
