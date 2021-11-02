package com.epam.jwd.library.dao;

import com.epam.jwd.library.connection.ConnectionPool;
import com.epam.jwd.library.entity.Author;
import com.epam.jwd.library.entity.Book;
import com.epam.jwd.library.exception.BookNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class BookDao extends AbstractDao<Book> implements BasicBookDao{

    private static final Logger LOG = LogManager.getLogger(BookDao.class);

    private static final String SELECT_ALL_BOOKS = "select book.id, author.first_name, author.last_name, " +
            "book.title, book.date_published, book.amount_of_left from author join author_to_book atb " +
            "on author.id = atb.author_id join book on atb.book_id = book.id";
    private static final String SELECT_BOOKS_BY_ID = "select author.first_name, author.last_name, book.title," +
            " book.date_published, book.amount_of_left from author join author_to_book atb" +
            " on author.id = atb.author_id join book on atb.book_id = book.id where book.id = ?";
    private static final String SELECT_BOOK_BY_ID_AUTHOR = "select author.first_name, author.last_name,book.title," +
            " book.date_published, book.amount_of_left from author join author_to_book atb " +
            "on author.id = atb.author_id join book on atb.book_id = book.id where author.id = ?";

    private static final String ID_COLUMN_NAME = "id";
    private static final String TITLE_COLUMN_NAME = "title";
    private static final String DATE_PUBLISHED_COLUMN_NAME = "date_published";
    private static final String AMOUNT_OF_LEFT_COLUMN_NAME = "amount_of_left";
    private static final String LAST_NAME_COLUMN_NAME_AUTHOR = "last_name";
    private static final String FIRST_NAME_COLUMN_NAME_AUTHOR = "first_name";

    private BookDao(ConnectionPool pool) {
        super(pool, LOG);
    }

    @Override
    public boolean create(Book entity) {
        return false;
    }

    @Override
    public Optional<Book> read(Long id) {
        LOG.trace("start read (read by id)");
        Optional<Book> book = Optional.empty();
        try (final Connection connection = pool.takeConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BOOKS_BY_ID)) {
            preparedStatement.setLong(1, id);
            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                final Book executedBook = executeBook(resultSet).orElseThrow(()
                        -> new BookNotFoundException("could not extract book"));
                book = Optional.of(executedBook);
            }
            return book;
        } catch (SQLException e) {
            LOG.error("sql error, could not found a book", e);
        } catch (BookNotFoundException e) {
            LOG.error("could not found a book", e);
        } catch (InterruptedException e) {
            LOG.error("method takeConnection from ConnectionPool was interrupted", e);
            Thread.currentThread().interrupt();
        }
        return book;
    }

    @Override
    public List<Book> readAll() {
        LOG.trace("start readAll");
        List<Book> books = new ArrayList<>();
        try (final Connection connection = pool.takeConnection();
             final Statement statement = connection.createStatement();
             final ResultSet resultSet = statement.executeQuery(SELECT_ALL_BOOKS)){
            while (resultSet.next()) {
                final Book book = executeBook(resultSet).orElseThrow(()
                        -> new BookNotFoundException("could not extract book"));
                books.add(book);
            }
            return books;
        } catch (SQLException e) {
            LOG.error("sql error, could not found books", e);
        } catch (BookNotFoundException e) {
            LOG.error("did not found books", e);
        } catch (InterruptedException e) {
            LOG.error("method takeConnection from ConnectionPool was interrupted", e);
            Thread.currentThread().interrupt();
        }
        return Collections.emptyList();
    }

    @Override
    public Book update(Book entity) {
        return null;
    }

    @Override
    public boolean delete(Book entity) {
        return false;
    }

    private Optional<Book> executeBook(ResultSet resultSet){
        try {
            return Optional.of(new Book(resultSet.getLong(ID_COLUMN_NAME), resultSet.getString(TITLE_COLUMN_NAME),
                    resultSet.getDate(DATE_PUBLISHED_COLUMN_NAME), resultSet.getInt(AMOUNT_OF_LEFT_COLUMN_NAME),
                    new Author(resultSet.getString(FIRST_NAME_COLUMN_NAME_AUTHOR), resultSet.getString(LAST_NAME_COLUMN_NAME_AUTHOR))));
        } catch (SQLException e) {
            LOG.error("could not extract book from executeBook", e);
            return Optional.empty();
        }
    }

    @Override
    public List<Book> readByIdAuthor(Author author) {
        LOG.trace("start readByAuthor");
        List<Book> books = new ArrayList<>();
        try (final Connection connection = pool.takeConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BOOK_BY_ID_AUTHOR)) {
            preparedStatement.setLong(1, author.getId());
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                final Book book = executeBook(resultSet).orElseThrow(()
                        -> new BookNotFoundException("could not extract book"));
                books.add(book);
            }
            return books;
        } catch (SQLException e) {
            LOG.error("sql error, could not found a book", e);
        } catch (BookNotFoundException e) {
            LOG.error("could not found a book", e);
        } catch (InterruptedException e) {
            LOG.error("method takeConnection from ConnectionPool was interrupted", e);
            Thread.currentThread().interrupt();
        }
        return Collections.emptyList();
    }



    private static class Holder {
        private static final BookDao INSTANCE = new BookDao(ConnectionPool.lockingPool());
    }

    public static BookDao getInstance() {
        return Holder.INSTANCE;
    }
}
