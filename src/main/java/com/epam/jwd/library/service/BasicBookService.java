package com.epam.jwd.library.service;

import com.epam.jwd.library.exception.ServiceException;
import com.epam.jwd.library.model.Book;
import com.epam.jwd.library.model.Entity;

import java.util.Optional;

public interface BasicBookService extends Service<Book> {

    boolean createBookWithAuthor(String title, String date, int amount_of_left, String authorFirstName, String authorLastName) throws ServiceException;

    Optional<Book> update(Long id, String title, String date, Integer amountOfLeft) throws ServiceException;

    Optional<Book> findByTitle(String title) throws ServiceException;

    static BasicBookService getInstance() {
        return BookService.getInstance();
    }

}
