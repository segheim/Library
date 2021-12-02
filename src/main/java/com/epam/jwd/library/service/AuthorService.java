package com.epam.jwd.library.service;

import com.epam.jwd.library.dao.AuthorDao;
import com.epam.jwd.library.exception.ServiceException;
import com.epam.jwd.library.model.Author;
import com.epam.jwd.library.validation.FirstLastNameValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class AuthorService implements Service<Author>, BasicAuthorService<Author> {

    private static final Logger LOG = LogManager.getLogger(AuthorService.class);

    private final AuthorDao authorDao;

    private AuthorService(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    @Override
    public Optional<Author> create(String firstName, String lastName) {
        try {
            if (!FirstLastNameValidator.getInstance().validate(firstName, lastName)) {
                throw new ServiceException("Data are not valid");
            }
        Author author = new Author(firstName, lastName);
        return authorDao.create(author);
        } catch (ServiceException e) {
            LOG.error("Could not create author");
        }
        return Optional.empty();
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
        try {
            if (!FirstLastNameValidator.getInstance().validate(firstName, lastName)) {
                throw new ServiceException("Data are not valid");
            }
            Author author = new Author(id, firstName, lastName);
            return authorDao.update(author);
        } catch (ServiceException e) {
            LOG.error("Could not update author");
        }
        return Optional.empty();
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
