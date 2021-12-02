package com.epam.jwd.library.service;

import com.epam.jwd.library.dao.AuthorDao;
import com.epam.jwd.library.model.Author;
import com.epam.jwd.library.validation.AuthorValidator;

import java.util.List;
import java.util.Optional;

public class AuthorService implements Service<Author>, BasicAuthorService<Author> {

    private final AuthorDao authorDao;

    private AuthorService(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    @Override
    public Optional<Author> create(String firstName, String lastName) {

        Author author = new Author(firstName, lastName);
        return authorDao.create(author);
    }

    @Override
    public Optional<Author> findById(Long id) {
        return authorDao.read(id);
    }

    @Override
    public List<Author> findAll() {
        final List<Author> authors = authorDao.readAll();
        return authors;
    }

    @Override
    public Optional<Author> update(Long id, String firstName, String lastName) {
        Author author = new Author(id, firstName, lastName);
        return authorDao.update(author);
    }

    @Override
    public boolean delete(Long id) {
        return authorDao.delete(id);
    }

    public static AuthorService getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final AuthorService INSTANCE = new AuthorService(AuthorDao.getInstance());
    }
}
