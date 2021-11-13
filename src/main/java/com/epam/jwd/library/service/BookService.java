package com.epam.jwd.library.service;

import com.epam.jwd.library.command.ShowMainPageCommand;
import com.epam.jwd.library.dao.AuthorDao;
import com.epam.jwd.library.dao.BookDao;
import com.epam.jwd.library.exception.BookDaoException;
import com.epam.jwd.library.model.Author;
import com.epam.jwd.library.model.Book;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sun.plugin.ClassLoaderInfo;
import sun.util.resources.LocaleData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BookService {

    private static final Logger LOG = LogManager.getLogger(BookService.class);
    private static BookService instance;

    private static BookDao bookDao;
    private static AuthorDao authorDao;
    private static Lock locker = new ReentrantLock();

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

    public List<Book> findAll() {
        final List<Book> books = bookDao.readAllWithAuthors();
        return books;
    }

    public static BookService getInstance() {
        if (instance == null){
            locker.lock();
            try {
                if (instance == null) {
                    instance = new BookService(BookDao.getInstance(), AuthorDao.getInstance());
                }
            } finally {
                locker.unlock();
            }
        }
        return instance;
    }
}
