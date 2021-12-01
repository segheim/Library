package com.epam.jwd.library.service;

import com.epam.jwd.library.connection.ConnectionPool;
import com.epam.jwd.library.dao.AuthorDao;
import com.epam.jwd.library.dao.BookDao;
import com.epam.jwd.library.exception.AuthorDaoException;
import com.epam.jwd.library.exception.BookDaoException;
import com.epam.jwd.library.model.Author;
import com.epam.jwd.library.model.Book;
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
    public Optional<Book> update(Long id, String title, java.sql.Date date, Integer amountOfLeft) {
        Book book = new Book(id, title, date, amountOfLeft);
        return bookDao.update(book);
    }

    @Override
    public boolean delete(Long id) {
        return bookDao.delete(id);
    }

    @Override
    public boolean createBookWithAuthor(String title, java.sql.Date date, int amountOfLeft, String authorFirstName, String authorLastName) {
        boolean createBookWithAuthor = false;
        Book book = new Book(title, date, amountOfLeft);
        Author author = new Author(authorFirstName, authorLastName);
        try (Connection connection = ConnectionPool.lockingPool().takeConnection()) {
            connection.setAutoCommit(false);
            BookDao bookDao = BookDao.getInstance();
            AuthorDao authorDao = AuthorDao.getInstance();
            final Long idBook = bookDao.create(book)
                    .map(Book::getId)
                    .orElseThrow(() -> new BookDaoException("could not create book"));
            final Long idAuthor = authorDao.create(author)
                    .map(Author::getId)
                    .orElseThrow(() -> new AuthorDaoException("could not create author"));
            if (bookDao.createBookInAuthorToBook(idBook, idAuthor)) {
                createBookWithAuthor = true;
            }
            connection.setAutoCommit(true);
        } catch (InterruptedException e) {
            LOG.error("method takeConnection from ConnectionPool was interrupted", e);
            Thread.currentThread().interrupt();
        } catch (SQLException e) {
            LOG.error("sql error, database access error occurs", e);
        } catch (AuthorDaoException e) {
            LOG.error("could not create new author", e);
        } catch (BookDaoException e) {
            LOG.error("could not create new book", e);
        }
        return createBookWithAuthor;
    }

    public static BookService getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final BookService INSTANCE = new BookService(BookDao.getInstance());
    }
}
