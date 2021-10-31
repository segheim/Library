package com.epam.jwd.library.dao;

import com.epam.jwd.library.connection.ConnectionPool;
import com.epam.jwd.library.entity.Author;
import com.epam.jwd.library.exception.AuthorNotCreateException;
import com.epam.jwd.library.exception.AuthorNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class AuthorDao extends AbstractDao<Author>{

    private static final Logger LOG = LogManager.getLogger(AuthorDao.class);

    private static final String SELECT_ALL_AUTHORS = "select id as id, first_name as f_name, last_name as l_name from author";
    private static final String LAST_NAME_COLUMN_NAME = "l_name";
    private static final String FIRST_NAME_COLUMN_NAME = "f_name";
    private static final String ID_COLUMN_NAME = "id";
    private static final String INSERT_NEW_AUTHOR = "insert into author (first_name, last_name) values (?,?)";

    private AuthorDao(ConnectionPool pool) {
        super(pool, LOG);
    }

    @Override
    public boolean create(Author author) {
        LOG.trace("start create");
        boolean createAuthor = false;
        try (final Connection connection = pool.takeConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEW_AUTHOR)) {
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
    public Author read(Long id) {
        return null;
    }

    @Override
    public List<Author> readAll() {
        LOG.trace("start readAll");
        List<Author> authors = new ArrayList<>();
        try (final Connection connection = pool.takeConnection();
             final Statement statement = connection.createStatement();
             final ResultSet resultSet = statement.executeQuery(SELECT_ALL_AUTHORS)){
            while (resultSet.next()) {
                final Author author = executeAuthor(resultSet).orElseThrow(() -> new AuthorNotFoundException("could not extract author"));
                authors.add(author);
            }
            return authors;
        } catch (SQLException e) {
            LOG.error("sql error, could not found authors", e);
        } catch (AuthorNotFoundException e) {
            LOG.error("did not found authors", e);
        } catch (InterruptedException e) {
            LOG.error("method takeConnection from ConnectionPool was interrupted", e);
            Thread.currentThread().interrupt();
        }
        return Collections.emptyList();
    }

    @Override
    public Author update(Author entity) {
        return null;
    }

    @Override
    public boolean delete(Author entity) {
        return false;
    }

    private Optional<Author> executeAuthor(ResultSet resultSet){
        try {
            return Optional.of(new Author(resultSet.getLong(ID_COLUMN_NAME), resultSet.getString(FIRST_NAME_COLUMN_NAME),
                    resultSet.getString(LAST_NAME_COLUMN_NAME)));
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
