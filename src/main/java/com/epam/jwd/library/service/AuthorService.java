package com.epam.jwd.library.service;

import com.epam.jwd.library.dao.AuthorDao;
import com.epam.jwd.library.model.Author;

import java.util.List;
import java.util.Optional;

public class AuthorService implements Service<Author>, BasicAuthorService {

    private final AuthorDao authorDao;

    private AuthorService(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    @Override
    public Optional<Author> create(Author entity) {
        return Optional.empty();
    }

    @Override
    public Optional<Author> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Author> findAll() {
        final List<Author> authors = authorDao.readAll();
        return authors;
    }

    @Override
    public Optional<Author> update(Author entity) {
        return Optional.empty();
    }

    @Override
    public boolean delete(Author entity) {
        return false;
    }

    public static AuthorService getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final AuthorService INSTANCE = new AuthorService(AuthorDao.getInstance());
    }
}
