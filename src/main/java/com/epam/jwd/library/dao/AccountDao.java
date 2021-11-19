package com.epam.jwd.library.dao;

import com.epam.jwd.library.connection.ConnectionPool;
import com.epam.jwd.library.exception.AccountDaoException;
import com.epam.jwd.library.model.Account;
import com.epam.jwd.library.model.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AccountDao extends AbstractDao<Account> implements BasicAccountDao{

    private static final Logger LOG = LogManager.getLogger(AccountDao.class);

    private static final String SELECT_BY_LOGIN = "select a_id as id, login as login, password as password,\n" +
            "role.role_name as role_name from account join role  on role.id = account.role_id\n" +
            "where login=?";

    protected AccountDao(ConnectionPool pool) {
        super(pool, LOG);
    }

    public Optional<Account> readByLogin(String login) {
        LOG.trace("start read by login");
        Optional<Account> account = Optional.empty();
        try (final Connection connection = pool.takeConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_LOGIN)) {
            preparedStatement.setString(1, login);
            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                final Account executedAccount = executeAccount(resultSet).orElseThrow(()
                        -> new AccountDaoException("could not extract account"));
                account = Optional.of(executedAccount);
                return account;
            }
        } catch (SQLException e) {
            LOG.error("sql error, could not found a account", e);
        } catch (AccountDaoException e) {
            LOG.error("could not found a account", e);
        } catch (InterruptedException e) {
            LOG.error("method takeConnection from ConnectionPool was interrupted", e);
            Thread.currentThread().interrupt();
        }
        return account;
    }

    private Optional<Account> executeAccount(ResultSet resultSet){
        try {
            return Optional.of(new Account(resultSet.getLong("id"),
                    resultSet.getString("login"), resultSet.getString("password"),
                    Role.valueOf(resultSet.getString("role_name").toUpperCase())));
        } catch (SQLException e) {
            LOG.error("could not extract book from executeBook", e);
            return Optional.empty();
        }
    }


    public static AccountDao getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public Optional<Account> create(Account entity) {
        return Optional.empty();
    }

    @Override
    public Optional<Account> read(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Account> readAll() {
        return null;
    }

    @Override
    public Optional<Account> update(Account entity) {
        return Optional.empty();
    }

    @Override
    public boolean delete(Account entity) {
        return false;
    }

    private static class Holder {
        public static final AccountDao INSTANCE = new AccountDao(ConnectionPool.lockingPool());
    }
}
