package com.epam.jwd.library.service;

import com.epam.jwd.library.connection.ConnectionPool;
import com.epam.jwd.library.dao.AuthorDao;
import com.epam.jwd.library.dao.BookDao;
import com.epam.jwd.library.exception.AuthorDaoException;
import com.epam.jwd.library.exception.BookDaoException;
import com.epam.jwd.library.exception.ServiceException;
import com.epam.jwd.library.model.Author;
import com.epam.jwd.library.model.Book;
import com.epam.jwd.library.validation.FirstLastNameValidator;
import com.epam.jwd.library.validation.BookValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class BookService implements BasicBookService {

    private static final Logger LOG = LogManager.getLogger(BookService.class);

    private final BookDao bookDao;

    private BookService(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public Optional<Book> findById(Long id) throws ServiceException {
        try {
            return bookDao.readWithAuthors(id);
        } catch (BookDaoException e) {
            LOG.error("Dao error, could not read book", e);
            throw new ServiceException("could not find book");
        }
    }

    public List<Book> findAll() throws ServiceException {
        try {
            return bookDao.readAllWithAuthors();
        } catch (BookDaoException e) {
            LOG.error("Dao error, could not read all books", e);
            throw new ServiceException("could not find all books");
        }
    }

    @Override
    public boolean delete(Long id) throws ServiceException {
        try {
            return bookDao.delete(id);
        } catch (BookDaoException e) {
            LOG.error("Dao error, could not delete book", e);
            throw new ServiceException("could not delete book");
        }
    }

    @Override
    public Optional<Book> update(Long id, String title, String date, Integer amountOfLeft) throws ServiceException {
        try {
            if (!BookValidator.getInstance().validate(title, date, amountOfLeft)) {
                throw new ServiceException("Data are not valid");
            }
            final Date datePublished = Date.valueOf(date);
            Book book = new Book(id, title, datePublished, amountOfLeft);
            return bookDao.update(book);
        } catch (BookDaoException e) {
            LOG.error("Could not update book", e);
            throw new ServiceException("could not update book");
        }
    }

    @Override
    public boolean createBookWithAuthor(String title, String date, int amountOfLeft, String authorFirstName, String authorLastName) throws ServiceException {
        boolean createBookWithAuthor = false;
        Connection connection = ConnectionPool.lockingPool().takeConnection();
        if (checkBookData(title, date, amountOfLeft, authorFirstName, authorLastName)) {
            final Date datePublished = Date.valueOf(date);
            Book book = new Book(title, datePublished, amountOfLeft);
            try {
                Author author = new Author(authorFirstName, authorLastName);
                connection.setAutoCommit(false);
                BookDao bookDao = BookDao.getInstance();
                AuthorDao authorDao = AuthorDao.getInstance();
                final Long idBook = bookDao.create(book)
                        .map(Book::getId)
                        .orElseThrow(() -> new BookDaoException("could not create book"));
                Long idAuthor = fetchIdAuthor(author, authorDao);
                if (!bookDao.createBookInAuthorToBook(idBook, idAuthor)) {
                    createBookWithAuthor = false;
                    connection.rollback();
                }
                createBookWithAuthor = true;
                connection.commit();
            } catch (SQLException e) {
                LOG.error("sql error, database access error occurs(setAutoCommit)", e);
            } catch (BookDaoException | AuthorDaoException e) {
                LOG.error("could not create new book", e);
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    LOG.error("Database access error occurs connection rollback", ex);
                }
                throw new ServiceException("could not create new book");
            } finally {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    LOG.error("Database access error occurs connection close", e);
                }
            }
        }
        return createBookWithAuthor;
    }

    @Override
    public Optional<Book> findByTitle(String title) throws ServiceException {
        try {
            return bookDao.readByTitle(title);
        } catch (BookDaoException e) {
            LOG.error("Dao error, could not read by title book", e);
            throw new ServiceException("could not find by title book");
        }
    }

    private Long fetchIdAuthor(Author author, AuthorDao authorDao) throws AuthorDaoException {
        Long idAuthor;
        final Optional<Author> readAuthor = authorDao.readAuthorByFirstLastName(author);
        if (readAuthor.isPresent()) {
            idAuthor = readAuthor.get().getId();
        } else {
            idAuthor = authorDao.create(author)
                    .map(Author::getId)
                    .orElseThrow(() -> new AuthorDaoException("could not create author"));
        }
        return idAuthor;
    }

    private boolean checkBookData(String title, String date, int amountOfLeft, String authorFirstName, String authorLastName) {
        if (BookValidator.getInstance().validate(title, date, amountOfLeft)
                && FirstLastNameValidator.getInstance().validate(authorFirstName, authorLastName)) {
            return true;
        }
        return false;
    }

    static BookService getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final BookService INSTANCE = new BookService(BookDao.getInstance());
    }
}
