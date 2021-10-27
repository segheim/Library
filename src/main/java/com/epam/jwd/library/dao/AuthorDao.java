package com.epam.jwd.library.dao;

import com.epam.jwd.library.connection.ConnectionPool;
import com.epam.jwd.library.entity.Author;
import com.epam.jwd.library.entity.Entity;
import com.epam.jwd.library.exception.AuthorNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AuthorDao extends AbstractDao<Author>{

    private static final Logger LOG = LogManager.getLogger(AuthorDao.class);
    public static final String SELECT_ALL_AUTHORS = "select id as id, first_name as f_name, last_name as l_name from author";

    public AuthorDao(ConnectionPool pool) {
        super(pool, LOG);
    }

    @Override
    public boolean create(Author entity) {
        return false;
    }

    @Override
    public Author read(Long id) {
        return null;
    }

    @Override
    public List<Author> readAll() {
        List<Author> authors = new ArrayList<>();
        try (final Connection connection = pool.takeConnection();
             final Statement statement = connection.createStatement();
             final ResultSet resultSet = statement.executeQuery(SELECT_ALL_AUTHORS)){
            while (resultSet.next()) {
                final Author author = executeAuthor(resultSet).orElseThrow(() -> new AuthorNotFoundException(""));
                authors.add(author);
            }
        } catch (SQLException e) {
            LOG.error("sql error, could not found authors", e);
        } catch (AuthorNotFoundException e) {
            LOG.error("did not found authors", e);
        }
        return null;
    }

    private Optional<Author> executeAuthor(ResultSet resultSet){
        try {
            return Optional.of(new Author(resultSet.getLong("id"), resultSet.getString("f_name"),
                    resultSet.getString("l_name")));
        } catch (SQLException e) {
            LOG.error("could not extract author from executeAuthor", e);
            return Optional.empty();
        }
    }

    @Override
    public Author update(Author entity) {
        return null;
    }

    @Override
    public boolean delete(Author entity) {
        return false;
    }
}
