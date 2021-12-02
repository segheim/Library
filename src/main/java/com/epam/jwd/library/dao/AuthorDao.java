package com.epam.jwd.library.dao;

import com.epam.jwd.library.connection.ConnectionPool;
import com.epam.jwd.library.exception.BookDaoException;
import com.epam.jwd.library.model.Author;
import com.epam.jwd.library.exception.AuthorDaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;

public class AuthorDao extends AbstractDao<Author> implements BasicAuthorDao<Author> {

    private static final Logger LOG = LogManager.getLogger(AuthorDao.class);

    private static final String INSERT_NEW_AUTHOR = "insert into author (first_name, last_name) values (?,?)";
    private static final String SELECT_AUTHOR_BY_ID = "select id as id, first_name as f_name, last_name as l_name" +
            " from author where id=?";
    private static final String SELECT_ALL_AUTHORS = "select id as id, first_name as f_name, last_name as l_name\n" +
            "from author";
    private static final String SELECT_AUTHOR_BY_LAST_NAME ="select id, first_name, last_name from author" +
            " where first_name='?'";
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
        Optional<Author> createdAuthor = Optional.empty();
        try (final Connection connection = pool.takeConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEW_AUTHOR, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, author.getFirstName());
            preparedStatement.setString(2, author.getLastName());
            final int numberChangedLines = preparedStatement.executeUpdate();
            if (numberChangedLines != 0) {
                final ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    long key = generatedKeys.getLong(1);
                     createdAuthor = read(key);
                    return createdAuthor;
                }
            } else
                throw new AuthorDaoException("could not create author");
        } catch (SQLException e) {
            LOG.error("sql error, could not create author", e);
        } catch (AuthorDaoException e) {
            LOG.error("could not create new author", e);
        }
        return createdAuthor;
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
        } catch (SQLException e) {
            LOG.error("sql error, could not find a author", e);
        } catch (AuthorDaoException e) {
            LOG.error("could not find a author", e);
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
        } catch (AuthorDaoException e) {
            LOG.error("did not found authors", e);
        }
        return Collections.emptyList();
    }

    @Override
    public Optional<Author> update(Author author) {
        LOG.trace("start update author");
        Optional<Author> updatedAuthor = Optional.empty();
        try (final Connection connection = pool.takeConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_AUTHOR)) {
            preparedStatement.setString(1, author.getFirstName());
            preparedStatement.setString(2, author.getLastName());
            preparedStatement.setLong(3, author.getId());
            final int numberChangedLines = preparedStatement.executeUpdate();
            if (numberChangedLines > 0) {
                updatedAuthor = read(author.getId());
                return updatedAuthor;
            } else {
                throw new AuthorDaoException("could not changed lines for update author");
            }
        } catch (SQLException e) {
            LOG.error("sql error, could not update author", e);
        } catch (AuthorDaoException e) {
            LOG.error("could not update author");
        }
        return updatedAuthor;
    }

    @Override
    public boolean delete(Long idAuthor) {
        LOG.trace("start delete author");
        boolean deleteAuthor = false;
        try (final Connection connection = pool.takeConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(DELETE_AUTHOR_BY_ID)) {
            preparedStatement.setLong(1, idAuthor);
            final int numberChangedLines = preparedStatement.executeUpdate();
            if (numberChangedLines != 0) {
                deleteAuthor = true;
            } else
                throw new BookDaoException("could not change lines delete author");
        } catch (SQLException e) {
            LOG.error("sql error, could not delete author", e);
        } catch (BookDaoException e) {
            LOG.error("could not delete new author", e);
        }
        return deleteAuthor;
    }

    public Optional<Author> readAuthorByLastName(String lastName) {
        Optional<Author> author = Optional.empty();
        try (final Connection connection = pool.takeConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(SELECT_AUTHOR_BY_LAST_NAME)) {
            preparedStatement.setString(1, lastName);
            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                final Author executedAuthor = executeAuthor(resultSet).orElseThrow(()
                        -> new AuthorDaoException("could not extract author"));
                author = Optional.of(executedAuthor);
            }
            return author;
        } catch (SQLException e) {
            LOG.error("sql error, could not delete author", e);
        } catch (AuthorDaoException e) {
            LOG.error("could not delete new author", e);
        }
        return author;
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
