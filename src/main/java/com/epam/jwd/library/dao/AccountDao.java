package com.epam.jwd.library.dao;

import com.epam.jwd.library.connection.ConnectionPool;
import com.epam.jwd.library.exception.AccountDaoException;
import com.epam.jwd.library.model.Account;
import com.epam.jwd.library.model.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class AccountDao extends AbstractDao<Account> implements BasicAccountDao{

    private static final Logger LOG = LogManager.getLogger(AccountDao.class);

    private static final String SELECT_ALL_ACCOUNTS = "select a_id as id, login as login, password as password,\n" +
            "role.role_name as role_name from account join role  on role.id = account.role_id";

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
        LOG.trace("start readAll");
        List<Account> accounts = new ArrayList<>();
        try (final Connection connection = pool.takeConnection();
             final Statement statement = connection.createStatement();
             final ResultSet resultSet = statement.executeQuery(SELECT_ALL_ACCOUNTS)) {
            while (resultSet.next()) {
                final Account account = executeAccount(resultSet).orElseThrow(() -> new AccountDaoException("could not extract author"));
                accounts.add(account);
            }
            return accounts;
        } catch (SQLException e) {
            LOG.error("sql error, could not found accounts", e);
        } catch (AccountDaoException e) {
            LOG.error("did not found accounts", e);
        } catch (InterruptedException e) {
            LOG.error("method takeConnection from ConnectionPool was interrupted", e);
            Thread.currentThread().interrupt();
        }
        return Collections.emptyList();
    }

    @Override
    public Optional<Account> update(Account entity) {
        return Optional.empty();
    }

    @Override
    public boolean delete(Account entity) {
        return false;
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
    private static class Holder {
        public static final AccountDao INSTANCE = new AccountDao(ConnectionPool.lockingPool());
    }
}
