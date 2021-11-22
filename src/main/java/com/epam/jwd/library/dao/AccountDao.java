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

    private static final String INSET_NEW_ACCOUNT ="insert into l_account (login, password) values (?,?)";

    private static final String SELECT_ALL_ACCOUNTS = "select a_id as id, login as login, password as password, " +
            "a_role.role_name as role_name from l_account join a_role  on a_role.id = l_account.role_id";

    private static final String SELECT_BY_LOGIN = "select a_id as id, login as login, password as password, " +
            "a_role.role_name as role_name from l_account join a_role  on a_role.id = l_account.role_id " +
            "where login=?";

    private static final String ID_COLUMN_NAME = "id";
    private static final String LOGIN_COLUMN_NAME = "login";
    private static final String PASSWORD_COLUMN_NAME = "password";
    private static final String ROLE_NAME_COLUMN_NAME = "role_name";

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
    public Optional<Account> create(Account account) {
        LOG.trace("start create account");
        Optional<Account> createdAccount = Optional.empty();
        try (final Connection connection = pool.takeConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(INSET_NEW_ACCOUNT, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, account.getLogin());
            preparedStatement.setString(2, account.getPassword());
            final int numberChangedLines = preparedStatement.executeUpdate();
            if (numberChangedLines != 0) {
                final ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    long key = generatedKeys.getLong(1);
                    createdAccount = read(key);
                    return createdAccount;
                }
            } else
                throw new AccountDaoException("could not create account");
        } catch (SQLException e) {
            LOG.error("sql error, could not create account", e);
        } catch (AccountDaoException e) {
            LOG.error("could not create new account", e);
        } catch (InterruptedException e) {
            LOG.error("method takeConnection from ConnectionPool was interrupted", e);
            Thread.currentThread().interrupt();
        }
        return createdAccount;
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
    public boolean delete(Long idAccount) {
        return false;
    }

    private Optional<Account> executeAccount(ResultSet resultSet){
        try {
            return Optional.of(new Account(resultSet.getLong(ID_COLUMN_NAME),
                    resultSet.getString(LOGIN_COLUMN_NAME), resultSet.getString(PASSWORD_COLUMN_NAME),
                    Role.valueOf(resultSet.getString(ROLE_NAME_COLUMN_NAME).toUpperCase())));
        } catch (SQLException e) {
            LOG.error("could not extract account from executeBook", e);
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
