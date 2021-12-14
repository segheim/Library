package com.epam.jwd.library.dao;

import com.epam.jwd.library.connection.ConnectionPool;
import com.epam.jwd.library.exception.AccountDaoException;
import com.epam.jwd.library.exception.AuthorDaoException;
import com.epam.jwd.library.exception.BookDaoException;
import com.epam.jwd.library.exception.BookOrderDaoException;
import com.epam.jwd.library.model.Entity;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public abstract class AbstractDao<T extends Entity>{

    protected final ConnectionPool pool;
    private final Logger logger;

    protected AbstractDao(ConnectionPool pool, Logger logger) {
        this.pool = pool;
        this.logger = logger;
    }

    public abstract Optional<T> create(T entity) throws BookDaoException, BookOrderDaoException, AccountDaoException, AuthorDaoException;

    public abstract Optional<T> read(Long id) throws BookOrderDaoException, AccountDaoException, BookDaoException, AuthorDaoException;

    public abstract List<T> readAll() throws BookOrderDaoException, AccountDaoException, BookDaoException, AuthorDaoException;

    public abstract Optional<T> update(T entity) throws BookDaoException, AuthorDaoException;

    public abstract boolean delete(Long id) throws BookOrderDaoException, AccountDaoException, BookDaoException, AuthorDaoException;

}
