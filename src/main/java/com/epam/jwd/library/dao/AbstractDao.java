package com.epam.jwd.library.dao;

import com.epam.jwd.library.entity.Entity;
import com.epam.jwd.library.connection.ConnectionPool;

import java.sql.*;
import java.util.Collections;
import java.util.List;

public abstract class AbstractDao implements BasicDao {

    private final ConnectionPool pool;

    protected AbstractDao(ConnectionPool pool) {
        this.pool = pool;
    }

    @Override
    public Entity create(Entity entity) {
        return null;
    }

    @Override
    public Entity read(Long id) {
        return null;
    }

    @Override
    public List readAll() {
        return null;
    }

    private <T extends Entity> List<T> executeEntity(String sql, ResultSetExtractor<T> extractor) {
        try (final Connection connection = pool.takeConnection();
             final Statement statement = connection.createStatement();
             final ResultSet resultSet = statement.executeQuery(sql)) {
            return extractor.extractAll(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public Entity update(Entity entity) {
        return null;
    }

    @Override
    public boolean delete(Entity entity) {
        return false;
    }
}
