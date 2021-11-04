package com.epam.jwd.library.service;

import com.epam.jwd.library.dao.AuthorDao;
import com.epam.jwd.library.model.Author;

import java.util.List;

public class AuthorService implements BasicAuthorService {

    private final AuthorDao authorDao;

    public AuthorService(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    @Override
    public List<Author> findAll() {
        final List<Author> authors = authorDao.readAll();
        return authors;
    }
}
