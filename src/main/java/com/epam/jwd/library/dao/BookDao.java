package com.epam.jwd.library.dao;

import com.epam.jwd.library.connection.ConnectionPool;
import com.epam.jwd.library.exception.AuthorNotCreateException;
import com.epam.jwd.library.model.Author;
import com.epam.jwd.library.model.Book;
import com.epam.jwd.library.exception.BookNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;

public class BookDao extends AbstractDao<Book> implements BasicBookDao{

    private static final Logger LOG = LogManager.getLogger(BookDao.class);

    private static final String SELECT_ALL_BOOKS = "select b.id as id_book, b.title as book_title, " +
            "b.date_published as book_date_published, b.amount_of_left as book_amount_of_left, " +
            "a.id as id_author, a.first_name as author_f_name, a.last_name as author_l_name " +
            "from book b join author_to_book atb on b.id = atb.book_id join author a " +
            "on atb.author_id = a.id order by b.id";

    private static final String SELECT_BOOKS_BY_ID = "select author.first_name, author.last_name, book.title," +
            " book.date_published, book.amount_of_left from author join author_to_book atb" +
            " on author.id = atb.author_id join book on atb.book_id = book.id where book.id = ?";
    private static final String SELECT_BOOK_BY_ID_AUTHOR = "select author.first_name, author.last_name,book.title," +
            " book.date_published, book.amount_of_left from author join author_to_book atb " +
            "on author.id = atb.author_id join book on atb.book_id = book.id where author.id = ?";

    private static final String ID_BOOK_COLUMN_NAME = "id_book";
    private static final String BOOK_TITLE_COLUMN_NAME = "book_title";
    private static final String BOOK_DATE_PUBLISHED_COLUMN_NAME = "book_date_published";
    private static final String BOOK_AMOUNT_OF_LEFT_COLUMN_NAME = "book_amount_of_left";
    private static final String ID_AUTHOR_COLUMN_NAME = "id_author";
    private static final String AUTHOR_LAST_NAME_COLUMN_NAME = "author_l_name";
    private static final String AUTHOR_FIRST_NAME_COLUMN_NAME = "author_f_name";

    private static Long idLastBook = 0L;

    private BookDao(ConnectionPool pool) {
        super(pool, LOG);
    }

    @Override
    public boolean create(Book entity) {
        LOG.trace("start create book");
        boolean createAuthor = false;
        try (final Connection connection = pool.takeConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement("insert into book (first_name, last_name) values (?,?)")) {
            preparedStatement.setString(1, author.getFirst_name());
            preparedStatement.setString(2, author.getLast_name());
            final int numberChangedLines = preparedStatement.executeUpdate();
            if (numberChangedLines != 0) {
                createAuthor = true;
                LOG.info("created new author: {} {}", author.getFirst_name(), author.getLast_name());
            } else
                throw new AuthorNotCreateException("could not create author");
        } catch (SQLException e) {
            LOG.error("sql error, could not create author", e);
        } catch (AuthorNotCreateException e) {
            LOG.error("could not create new author", e);
        } catch (InterruptedException e) {
            LOG.error("method takeConnection from ConnectionPool was interrupted", e);
            Thread.currentThread().interrupt();
        }
        return createAuthor;
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
        LinkedList<Book> books = new LinkedList<>();
        try (final Connection connection = pool.takeConnection();
             final Statement statement = connection.createStatement();
             final ResultSet resultSet = statement.executeQuery(SELECT_ALL_BOOKS)){
            while (resultSet.next()) {
                final Book book = executeBook(resultSet).orElseThrow(()
                        -> new BookNotFoundException("could not extract book"));
                if (idLastBook == book.getId()) {
                    idLastBook = book.getId();
                    final Book lastBook = books.getLast();
                    books.removeLast();
                    final List<Author> authors = lastBook.getAuthors();
                    authors.add(book.getAuthors().get(book.getAuthors().size()-1));
                    books.add(book.getBookWithAuthors(authors));
                } else {
                    idLastBook = book.getId();
                    books.add(book);
                }
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
            List<Author> authors = new ArrayList<>();
            authors.add(new Author(resultSet.getLong(ID_AUTHOR_COLUMN_NAME),resultSet.getString(AUTHOR_FIRST_NAME_COLUMN_NAME),
                    resultSet.getString(AUTHOR_LAST_NAME_COLUMN_NAME)));
            return Optional.of(new Book(resultSet.getLong(ID_BOOK_COLUMN_NAME), resultSet.getString(BOOK_TITLE_COLUMN_NAME),
                    resultSet.getDate(BOOK_DATE_PUBLISHED_COLUMN_NAME), resultSet.getInt(BOOK_AMOUNT_OF_LEFT_COLUMN_NAME),
                    authors));
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
