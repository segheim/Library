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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class BookService implements Service<Book>, BasicBookService{

    private static final Logger LOG = LogManager.getLogger(BookService.class);

    private final BookDao bookDao;
    private final AuthorDao authorDao;

    private BookService(BookDao bookDao, AuthorDao authorDao) {
        this.bookDao = bookDao;
        this.authorDao = authorDao;
    }


    public Book createBookWithAuthors() {
        String firstName = "Alexandr";
        String lastName = "Pyshkin";
        String insertDate = "2014-01-28";
        Date utilDate = null;
        try {
            utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(insertDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        java.sql.Date sqlDate= new java.sql.Date(utilDate.getTime());

        Author author = new Author(firstName, lastName);
        final Optional<Author> optionalAuthor = authorDao.readAuthorByLastName(lastName);
        if (optionalAuthor.isPresent()) {
            author = optionalAuthor.get();
        } else {
            final Optional<Author> newAuthor = authorDao.create(author);
            if (newAuthor.isPresent()) {
                author = newAuthor.get();
            }
        }
        Book book = null;
        final Optional<Book> newBook = bookDao.create(new Book("new Tittle", sqlDate, 2));
        try {
            book = newBook.orElseThrow(() -> new BookDaoException("could not create book"));
        } catch (BookDaoException e) {
            LOG.error("could not create book");
        }
        bookDao.createBookInAuthorToBook(book.getId(), author.getId());
        return book;
    }


    @Override
    public Optional<Book> create(Book en) {

        return Optional.empty();
    }

    @Override
    public Optional<Book> findById(Long id) {
        final Optional<Book> readBook = bookDao.readWithAuthors(id);
        return readBook;
    }

    public List<Book> findAll() {
        final List<Book> books = bookDao.readAllWithAuthors();
        return books;
    }

    @Override
    public boolean delete(Long id) {
        return bookDao.delete(id);
    }

    @Override
    public boolean createBook(String title, java.sql.Date date, int amountOfLeft, String authorFirstName, String authorLastName) {
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
            bookDao.createBookInAuthorToBook(idBook, idAuthor);
            createBookWithAuthor = true;
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
        public static final BookService INSTANCE = new BookService(BookDao.getInstance(), AuthorDao.getInstance());
    }
}
