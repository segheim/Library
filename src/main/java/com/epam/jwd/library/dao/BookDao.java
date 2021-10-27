package com.epam.jwd.library.dao;

import com.epam.jwd.library.connection.ConnectionPool;
import com.epam.jwd.library.entity.Book;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class BookDao extends AbstractDao<Book>{

    private static final Logger LOG = LogManager.getLogger(BookDao.class);

    protected BookDao(ConnectionPool pool) {
        super(pool, LOG);
    }

    @Override
    public boolean create(Book entity) {
        return false;
    }

    @Override
    public Book read(Long id) {
        return null;
    }

    @Override
    public List<Book> readAll() {
        return null;
    }

    @Override
    public Book update(Book entity) {
        return null;
    }

    @Override
    public boolean delete(Book entity) {
        return false;
    }
}
