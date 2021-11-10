package com.epam.jwd.library.dao;

import com.epam.jwd.library.connection.ConnectionPool;
import com.epam.jwd.library.exception.BookDaoException;
import com.epam.jwd.library.model.Author;
import com.epam.jwd.library.exception.AuthorDaoException;
import com.epam.jwd.library.model.Book;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class AuthorDao extends AbstractDao<Author> implements BasicAuthorDao {

    private static final Logger LOG = LogManager.getLogger(AuthorDao.class);

    private static final String INSERT_NEW_AUTHOR = "insert into author (first_name, last_name) values (?,?)";
    private static final String SELECT_AUTHOR_BY_ID = "select id as id, first_name as f_name, last_name as l_name" +
            " from author where id=?";
    private static final String SELECT_ALL_AUTHORS = "select id as id, first_name as f_name, last_name as l_name\n" +
            "from author";
    private static final String UPDATE_AUTHOR = "update author set first_name=?, last_name=? where id=?";
    private static final String DELETE_AUTHOR_BY_ID = "delete from author where id=?";

    private static final String FIRST_NAME_COLUMN_NAME = "f_name";
    private static final String LAST_NAME_COLUMN_NAME = "l_name";
    private static final String AUTHOR_ID_COLUMN_NAME = "id";

    private AuthorDao(ConnectionPool pool) {
        super(pool, LOG);
    }

    @Override
    public Optional<Author> create(Author author) {
        LOG.trace("start create author");
        try (final Connection connection = pool.takeConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEW_AUTHOR, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, author.getFirst_name());
            preparedStatement.setString(2, author.getLast_name());
            final int numberChangedLines = preparedStatement.executeUpdate();
            if (numberChangedLines != 0) {
                final ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    long key = generatedKeys.getLong(1);
                    final Optional<Author> createAuthor = read(key);
                    final Author author1 = createAuthor.get();
                }
            } else
                throw new AuthorDaoException("could not create author");
        } catch (SQLException e) {
            LOG.error("sql error, could not create author", e);
        } catch (AuthorDaoException e) {
            LOG.error("could not create new author", e);
        } catch (InterruptedException e) {
            LOG.error("method takeConnection from ConnectionPool was interrupted", e);
            Thread.currentThread().interrupt();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Author> read(Long id) {
        LOG.trace("start read author (read by id)");
        Optional<Author> author = Optional.empty();
        try (final Connection connection = pool.takeConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(SELECT_AUTHOR_BY_ID)) {
            preparedStatement.setLong(1, id);
            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                final Author executedAuthor = executeAuthor(resultSet).orElseThrow(()
                        -> new AuthorDaoException("could not extract author"));
                author = Optional.of(executedAuthor);
            }
            return author;
        } catch (SQLException e) {
            LOG.error("sql error, could not found a author", e);
        } catch (AuthorDaoException e) {
            LOG.error("could not found a author", e);
        } catch (InterruptedException e) {
            LOG.error("method takeConnection from ConnectionPool was interrupted", e);
            Thread.currentThread().interrupt();
        }
        return author;
    }

    @Override
    public List<Author> readAll() {
        LOG.trace("start readAll");
        List<Author> authors = new ArrayList<>();
        try (final Connection connection = pool.takeConnection();
             final Statement statement = connection.createStatement();
             final ResultSet resultSet = statement.executeQuery(SELECT_ALL_AUTHORS)) {
            while (resultSet.next()) {
                final Author author = executeAuthor(resultSet).orElseThrow(() -> new AuthorDaoException("could not extract author"));
                authors.add(author);
            }
            return authors;
        } catch (SQLException e) {
            LOG.error("sql error, could not found authors", e);
        } catch (InterruptedException e) {
            LOG.error("method takeConnection from ConnectionPool was interrupted", e);
            Thread.currentThread().interrupt();
        } catch (AuthorDaoException e) {
            LOG.error("did not found authors", e);
        }
        return Collections.emptyList();
    }

    @Override
    public Optional<Author> update(Author author) {
        LOG.trace("start update author");
        try (final Connection connection = pool.takeConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_AUTHOR)) {
            preparedStatement.setString(1, author.getFirst_name());
            preparedStatement.setString(2, author.getLast_name());
            preparedStatement.setLong(3, author.getId());
            final int numberChangedLines = preparedStatement.executeUpdate();
            if (numberChangedLines != 0) {
                final ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    long key = generatedKeys.getLong(1);
                    LOG.info("key = {}", key);
                    final Optional<Author> createAuthor = read(key);
                    final Author author1 = createAuthor.get();
                    LOG.info("author = {} {}", author1.getFirst_name(), author1.getLast_name());
                }
                LOG.info("created new author: {} {}", author.getFirst_name(), author.getLast_name());
            } else
                throw new AuthorDaoException("could not update author");
        } catch (SQLException e) {
            LOG.error("sql error, could not update author", e);
        } catch (AuthorDaoException e) {
            LOG.error("could not update author");
        } catch (InterruptedException e) {
            LOG.error("method takeConnection from ConnectionPool was interrupted", e);
            Thread.currentThread().interrupt();
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(Author author) {
        LOG.trace("start delete author");
        return deleteAuthorById(author.getId());
    }

    public boolean deleteAuthorById(Long id) {
        LOG.trace("start deleteAuthorById");
        boolean deleteAuthor = false;
        try (final Connection connection = pool.takeConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(DELETE_AUTHOR_BY_ID)) {
            preparedStatement.setLong(1, id);
            final int numberChangedLines = preparedStatement.executeUpdate();
            if (numberChangedLines != 0) {
                deleteAuthor = true;
                LOG.info("deleted author with id: {}", id);
            } else
                throw new BookDaoException("could not delete author");
        } catch (SQLException e) {
            LOG.error("sql error, could not delete author", e);
        } catch (BookDaoException e) {
            LOG.error("could not delete new author", e);
        } catch (InterruptedException e) {
            LOG.error("method takeConnection from ConnectionPool was interrupted", e);
            Thread.currentThread().interrupt();
        }
        return deleteAuthor;
    }

    private Optional<Author> executeAuthor(ResultSet resultSet) {
        try {
            final Author author = new Author(resultSet.getLong(AUTHOR_ID_COLUMN_NAME), resultSet.getString(FIRST_NAME_COLUMN_NAME),
                    resultSet.getString(LAST_NAME_COLUMN_NAME));
            return Optional.of(author);
        } catch (SQLException e) {
            LOG.error("could not extract author from executeAuthor", e);
            return Optional.empty();
        }
    }

    private static class Holder {
        private static final AuthorDao INSTANCE = new AuthorDao(ConnectionPool.lockingPool());
    }

    public static AuthorDao getInstance() {
        return Holder.INSTANCE;
    }

}
