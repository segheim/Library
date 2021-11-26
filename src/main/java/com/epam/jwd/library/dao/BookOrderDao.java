package com.epam.jwd.library.dao;

import com.epam.jwd.library.connection.ConnectionPool;
import com.epam.jwd.library.exception.AccountDaoException;
import com.epam.jwd.library.exception.BookOrderDaoException;
import com.epam.jwd.library.model.Account;
import com.epam.jwd.library.model.BookOrder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class BookOrderDao extends AbstractDao<BookOrder>{

    private static final Logger LOG = LogManager.getLogger(BookOrderDao.class);

    protected BookOrderDao(ConnectionPool pool) {
        super(pool, LOG);
    }

    @Override
    public Optional<BookOrder> create(BookOrder order) {
        LOG.trace("start create book order");
        Optional<BookOrder> createdOrder = Optional.empty();
        try (final Connection connection = pool.takeConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, );
            preparedStatement.setString(2, );
            preparedStatement.setInt(3, );
            final int numberChangedLines = preparedStatement.executeUpdate();
            if (numberChangedLines != 0) {
                final ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    long key = generatedKeys.getLong(1);
                    createdOrder = read(key);
                    return createdOrder;
                }
            } else
                throw new BookOrderDaoException("could not change lines for create book order");
        } catch (SQLException e) {
            LOG.error("sql error, could not create book order", e);
        } catch (BookOrderDaoException e) {
            LOG.error("could not create new book order", e);
        } catch (InterruptedException e) {
            LOG.error("method takeConnection from ConnectionPool was interrupted", e);
            Thread.currentThread().interrupt();
        }
        return createdOrder;
    }

    @Override
    public Optional<BookOrder> read(Long id) {
        return Optional.empty();
    }

    @Override
    public List<BookOrder> readAll() {
        return null;
    }

    @Override
    public Optional<BookOrder> update(BookOrder entity) {
        return Optional.empty();
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
