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
    public Optional<Book> update(Long id, String title, java.sql.Date date, Integer amountOfLeft) {
        try {
            if (!BookValidator.getInstance().validate(title, amountOfLeft)) {
                throw new ServiceException("Data are not valid");
            }
            Book book = new Book(id, title, date, amountOfLeft);
            return bookDao.update(book);
        } catch (ServiceException e) {
            LOG.error("Could not update book");
        }
        return Optional.empty();
    }

    @Override
    public boolean createBookWithAuthor(String title, java.sql.Date date, int amountOfLeft, String authorFirstName, String authorLastName) {
        boolean createBookWithAuthor = false;
        Connection connection = ConnectionPool.lockingPool().takeConnection();
        try {
            checkBookData(title, amountOfLeft, authorFirstName, authorLastName);
            Book book = new Book(title, date, amountOfLeft);
            Author author = new Author(authorFirstName, authorLastName);
            connection.setAutoCommit(false);
            BookDao bookDao = BookDao.getInstance();
            AuthorDao authorDao = AuthorDao.getInstance();
            final Long idBook = bookDao.create(book)
                    .map(Book::getId)
                    .orElseThrow(() -> new ServiceException("could not create book"));
            final Long idAuthor = authorDao.create(author)
                    .map(Author::getId)
                    .orElseThrow(() -> new ServiceException("could not create author"));
            if (bookDao.createBookInAuthorToBook(idBook, idAuthor)) {
                createBookWithAuthor = true;
            }
            connection.commit();
        } catch (SQLException e) {
            LOG.error("sql error, database access error occurs(setAutoCommit)", e);
        } catch (ServiceException e) {
            LOG.error("could not create new book", e);
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();LOG.error("Database access error occurs connection rollback", ex);
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

    private void checkBookData(String title, int amountOfLeft, String authorFirstName, String authorLastName) throws ServiceException {
        if (!BookValidator.getInstance().validate(title, amountOfLeft)
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
