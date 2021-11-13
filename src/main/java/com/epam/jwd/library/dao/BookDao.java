package com.epam.jwd.library.dao;

import com.epam.jwd.library.connection.ConnectionPool;
import com.epam.jwd.library.exception.BookDaoException;
import com.epam.jwd.library.model.Author;
import com.epam.jwd.library.model.Book;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class BookDao extends AbstractDao<Book> implements BasicBookDao{

    private static final Logger LOG = LogManager.getLogger(BookDao.class);

    private static final String INSERT_BOOK = "insert into book (title, date_published, amount_of_left) values (?,?,?)";

    private static final String INSERT_BOOK_IN_AUTHOR_TO_BOOK = "insert into author_to_book (book_id) values (?)";

    private static final String SELECT_BY_ID = "select id as id_book, title as book_title, date_published as book_date_published, amount_of_left as book_amount_of_left from book where id = ?";

    private static final String SELECT_ALL_BOOKS = "select id as id_book, title as book_title, date_published as book_date_published, amount_of_left as book_amount_of_left from book";

    private static final String UPDATE_BOOK = "update book set title=?, date_published=?, amount_of_left=? where id=?";

    private static final String DELETE_BOOK_BY_ID = "delete from book where id=?";

    private static final String SELECT_ALL_BOOKS_WITH_AUTHORS = "select b.id as id_book, b.title as book_title, " +
            "b.date_published as book_date_published, b.amount_of_left as book_amount_of_left, " +
            "a.id as id_author, a.first_name as author_f_name, a.last_name as author_l_name " +
            "from book b join author_to_book atb on b.id = atb.book_id join author a " +
            "on atb.author_id = a.id order by b.id";

    private static final String SELECT_BOOK_BY_ID_WITH_AUTHORS = "select book.id as id_book, author.id as id_author, author.first_name as author_f_name, author.last_name as author_l_name, book.title as book_title," +
            " book.date_published as book_date_published, book.amount_of_left as book_amount_of_left from author join author_to_book atb" +
            " on author.id = atb.author_id join book on atb.book_id = book.id where book.id = ?";

    private static final String SELECT_BOOK_BY_ID_AUTHOR = "select author.first_name, author.last_name,book.title," +
            " book.date_published, book.amount_of_left from author join author_to_book atb " +
            "on author.id = atb.author_id join book on atb.book_id = book.id where author.id = ?";

    private static final String SELECT_BY_TITLE = "select id, title from book where title = ?";



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
    public Optional<Book> create(Book book) {
        LOG.trace("start create book");
        Optional<Book> createdBook = Optional.empty();
        try (final Connection connection = pool.takeConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(INSERT_BOOK, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setDate(2, (Date) book.getDate_published());
            preparedStatement.setInt(3, book.getAmount_of_left());
            final int numberChangedLines = preparedStatement.executeUpdate();
            if (numberChangedLines > 0) {
                final ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    long key = generatedKeys.getLong(1);
                    LOG.info("key = {}", key);
                    createdBook = read(key);
                }
                return createdBook;
            } else
                throw new BookDaoException("could not create book");
        } catch (SQLException e) {
            LOG.error("sql error, could not create book", e);
        } catch (BookDaoException e) {
            LOG.error("could not create new book", e);
        } catch (InterruptedException e) {
            LOG.error("method takeConnection from ConnectionPool was interrupted", e);
            Thread.currentThread().interrupt();
        }
        return createdBook;
    }

    public boolean createBookInAuthorToBook(Long idBook, Long idAuthor) {
        LOG.trace("start create book in author to book");
        boolean createBookInAuthorToBook = false;
        try (final Connection connection = pool.takeConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(INSERT_BOOK_IN_AUTHOR_TO_BOOK)) {
            preparedStatement.setLong(1, idBook);
            preparedStatement.setLong(1, idAuthor);
            final int numberChangedLines = preparedStatement.executeUpdate();
            if (numberChangedLines > 0) {
                createBookInAuthorToBook = true;
            }
        } catch (SQLException e) {
            LOG.error("sql error, could not create book in author to book", e);
        } catch (InterruptedException e) {
            LOG.error("method takeConnection from ConnectionPool was interrupted", e);
            Thread.currentThread().interrupt();
        }
        return createBookInAuthorToBook;
    }

    @Override
    public Optional<Book> read(Long id) {
        LOG.trace("start read (read by id)");
        Optional<Book> book = Optional.empty();
        try (final Connection connection = pool.takeConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
            preparedStatement.setLong(1, id);
            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                final Book executedBook = executeBook(resultSet).orElseThrow(()
                        -> new BookDaoException("could not extract book"));
                book = Optional.of(executedBook);
                return book;
            }
        } catch (SQLException e) {
            LOG.error("sql error, could not found a book", e);
        } catch (BookDaoException e) {
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
                        -> new BookDaoException("could not extract book"));
                    books.add(book);
            }
            return books;
        } catch (SQLException e) {
            LOG.error("sql error, could not found books", e);
        } catch (BookDaoException e) {
            LOG.error("did not found books", e);
        } catch (InterruptedException e) {
            LOG.error("method takeConnection from ConnectionPool was interrupted", e);
            Thread.currentThread().interrupt();
        }
        return Collections.emptyList();
    }

    @Override
    public Optional<Book> update(Book book) {
        LOG.trace("start update book");
        Optional<Book> updatedBook = Optional.empty();
        try (final Connection connection = pool.takeConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BOOK)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setDate(2, (Date) book.getDate_published());
            preparedStatement.setInt(3, book.getAmount_of_left());
            preparedStatement.setLong(4, book.getId());
            final int numberChangedLines = preparedStatement.executeUpdate();
            if (numberChangedLines > 0) {
                final ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    long key = generatedKeys.getLong(1);
                    LOG.info("key = {}", key);
                    updatedBook = read(key);
                }
                LOG.info("created new author: {} {}", book.getTitle(), book.getDate_published());
                return updatedBook;
            }
        } catch (SQLException e) {
            LOG.error("sql error, could not update book", e);
        } catch (InterruptedException e) {
            LOG.error("method takeConnection from ConnectionPool was interrupted", e);
            Thread.currentThread().interrupt();
        }
        return updatedBook;
    }

    @Override
    public boolean delete(Book book) {
        LOG.trace("start delete book");
        return deleteBookById(book.getId());
    }

    public boolean deleteBookById(Long id) {
        LOG.trace("start deleteBookById");
        boolean deleteBook = false;
        try (final Connection connection = pool.takeConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BOOK_BY_ID)) {
            preparedStatement.setLong(1, id);
            final int numberChangedLines = preparedStatement.executeUpdate();
            if (numberChangedLines != 0) {
                deleteBook = true;
                LOG.info("deleted book with id: {}", id);
            } else
                throw new BookDaoException("could not delete book");
        } catch (SQLException e) {
            LOG.error("sql error, could not delete book", e);
        } catch (BookDaoException e) {
            LOG.error("could not delete new book", e);
        } catch (InterruptedException e) {
            LOG.error("method takeConnection from ConnectionPool was interrupted", e);
            Thread.currentThread().interrupt();
        }
        return deleteBook;
    }


    private Optional<Book> executeBook(ResultSet resultSet){
        try {
            return Optional.of(new Book(resultSet.getLong(ID_BOOK_COLUMN_NAME), resultSet.getString(BOOK_TITLE_COLUMN_NAME),
                    resultSet.getDate(BOOK_DATE_PUBLISHED_COLUMN_NAME), resultSet.getInt(BOOK_AMOUNT_OF_LEFT_COLUMN_NAME)));
        } catch (SQLException e) {
            LOG.error("could not extract book from executeBook", e);
            return Optional.empty();
        }
    }

    private Optional<Book> executeBookWithAuthors(ResultSet resultSet){
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

    public Optional<Book> readWithAuthors(Long id) {
        LOG.trace("start readWithAuthors (read by id)");
        Optional<Book> book = Optional.empty();
        try (final Connection connection = pool.takeConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BOOK_BY_ID_WITH_AUTHORS)) {
            preparedStatement.setLong(1, id);
            final ResultSet resultSet = preparedStatement.executeQuery();
            Book lastBook = null;
            while (resultSet.next()) {
                final Book executedBook = executeBook(resultSet).orElseThrow(()
                        -> new BookDaoException("could not extract book"));
                if (lastBook != null) {
                    final List<Author> authors = lastBook.getAuthors();
                    authors.add(executedBook.getAuthors().get(executedBook.getAuthors().size() - 1));
                    final Book bookWithAuthors = lastBook.getBookWithAuthors(authors);
                    lastBook = bookWithAuthors;
                } else {
                    lastBook = executedBook;
                }
            }
            book = Optional.of(lastBook);
            return book;
        } catch (SQLException e) {
            LOG.error("sql error, could not found a book", e);
        } catch (BookDaoException e) {
            LOG.error("could not found a book", e);
        } catch (InterruptedException e) {
            LOG.error("method takeConnection from ConnectionPool was interrupted", e);
            Thread.currentThread().interrupt();
        }
        return book;
    }

    public List<Book> readAllWithAuthors() {
        LOG.trace("start readAllWithAuthors");
        LinkedList<Book> books = new LinkedList<>();
        try (final Connection connection = pool.takeConnection();
             final Statement statement = connection.createStatement();
             final ResultSet resultSet = statement.executeQuery(SELECT_ALL_BOOKS_WITH_AUTHORS)){
            while (resultSet.next()) {
                final Book book = executeBookWithAuthors(resultSet).orElseThrow(()
                        -> new BookDaoException("could not extract book"));
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
        } catch (BookDaoException e) {
            LOG.error("did not found books", e);
        } catch (InterruptedException e) {
            LOG.error("method takeConnection from ConnectionPool was interrupted", e);
            Thread.currentThread().interrupt();
        }
        return Collections.emptyList();
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
                        -> new BookDaoException("could not extract book"));
                books.add(book);
            }
            return books;
        } catch (SQLException e) {
            LOG.error("sql error, could not found a book", e);
        } catch (BookDaoException e) {
            LOG.error("could not found a book", e);
        } catch (InterruptedException e) {
            LOG.error("method takeConnection from ConnectionPool was interrupted", e);
            Thread.currentThread().interrupt();
        }
        return Collections.emptyList();
    }

    public Optional<Book> readByTitle(String title) {
        Optional<Book> book = Optional.empty();
        try (final Connection connection = pool.takeConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_TITLE)) {
            preparedStatement.setString(1, title);
            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                final Book executedBook = executeBook(resultSet).orElseThrow(()
                        -> new BookDaoException("could not extract book"));
                book = Optional.of(executedBook);
            }
            return book;
        } catch (SQLException e) {
            LOG.error("sql error, could not found a book", e);
        } catch (BookDaoException e) {
            LOG.error("could not found a book", e);
        } catch (InterruptedException e) {
            LOG.error("method takeConnection from ConnectionPool was interrupted", e);
            Thread.currentThread().interrupt();
        }
        return Optional.empty();
    }

    private static class Holder {
        private static final BookDao INSTANCE = new BookDao(ConnectionPool.lockingPool());
    }

    public static BookDao getInstance() {
        return Holder.INSTANCE;
    }
}
