package com.epam.jwd.library.service;

import com.epam.jwd.library.dao.BookDao;
import com.epam.jwd.library.entity.Book;

import java.util.List;

public class BookService {

    private final BookDao bookDao;

    public BookService(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    public List<Book> findAll() {
        final List<Book> books = bookDao.readAll();
        return books;
    }
}
