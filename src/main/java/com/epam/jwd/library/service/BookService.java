package com.epam.jwd.library.service;

import com.epam.jwd.library.connection.ConnectionPool;
import com.epam.jwd.library.dao.AuthorDao;
import com.epam.jwd.library.dao.BookDao;
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

public class BookService implements Service<Book>, BasicBookService<Book>{

    private static final Logger LOG = LogManager.getLogger(BookService.class);

    private final BookDao bookDao;

    private BookService(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public Optional<Book> findById(Long id) {
        return bookDao.readWithAuthors(id);
    }

    public List<Book> findAll() {
        return bookDao.readAllWithAuthors();
    }

    @Override
    public boolean delete(Long id) {
        return bookDao.delete(id);
    }

    @Override
    public Optional<Book> update(Long id, String title, String date, Integer amountOfLeft) {
        try {
            if (!BookValidator.getInstance().validate(title, date, amountOfLeft)) {
                throw new ServiceException("Data are not valid");
            }
            final Date datePublished = Date.valueOf(date);
            Book book = new Book(id, title, datePublished, amountOfLeft);
            return bookDao.update(book);
        } catch (ServiceException e) {
            LOG.error("Could not update book");
        }
        return Optional.empty();
    }

    @Override
    public boolean createBookWithAuthor(String title, String date, int amountOfLeft, String authorFirstName, String authorLastName) {
        boolean createBookWithAuthor = false;
        Connection connection = ConnectionPool.lockingPool().takeConnection();
        try {
            checkBookData(title, date, amountOfLeft, authorFirstName, authorLastName);
            final Date datePublished = Date.valueOf(date);
            Book book = new Book(title, datePublished, amountOfLeft);
            Author author = new Author(authorFirstName, authorLastName);
            connection.setAutoCommit(false);
            BookDao bookDao = BookDao.getInstance();
            AuthorDao authorDao = AuthorDao.getInstance();
            final Long idBook = bookDao.create(book)
                    .map(Book::getId)
                    .orElseThrow(() -> new ServiceException("could not create book"));
            Long idAuthor = fetchIdAuthor(author, authorDao);
            if (!bookDao.createBookInAuthorToBook(idBook, idAuthor)) {
                connection.rollback();
            }
            createBookWithAuthor = true;
            connection.commit();
        } catch (SQLException e) {
            LOG.error("sql error, database access error occurs(setAutoCommit)", e);
        } catch (ServiceException e) {
            LOG.error("could not create new book", e);
            try {
                connection.rollback();
            } catch (SQLException ex) {
                LOG.error("Database access error occurs connection rollback", ex);
            }
        } finally {
            try {
                connection.setAutoCommit(true);
                connection.close();
            } catch (SQLException e) {
                LOG.error("Database access error occurs connection close", e);
            }
        }
        return createBookWithAuthor;
    }

    private Long fetchIdAuthor(Author author, AuthorDao authorDao) throws ServiceException {
        Long idAuthor;
        final Optional<Author> readAuthor = authorDao.readAuthorByFirstLastName(author);
        if (readAuthor.isPresent()) {
            idAuthor = readAuthor.get().getId();
        } else {
            idAuthor = authorDao.create(author)
                    .map(Author::getId)
                    .orElseThrow(() -> new ServiceException("could not create author"));
        }
        return idAuthor;
    }

    private void checkBookData(String title, String date, int amountOfLeft, String authorFirstName, String authorLastName) throws ServiceException {
        if (!BookValidator.getInstance().validate(title, date, amountOfLeft)
                || !FirstLastNameValidator.getInstance().validate(authorFirstName, authorLastName)) {
            throw new ServiceException("Data are not valid");
        }
    }

    public static BookService getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final BookService INSTANCE = new BookService(BookDao.getInstance());
    }
}
