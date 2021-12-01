package com.epam.jwd.library.dao;

import com.epam.jwd.library.connection.ConnectionPool;
import com.epam.jwd.library.exception.BookOrderDaoException;
import com.epam.jwd.library.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class BookOrderDao extends AbstractDao<BookOrder>{

    private static final Logger LOG = LogManager.getLogger(BookOrderDao.class);

    private static final String INSERT_NEW_BOOK_ORDER = "insert into book_order (account_details_id, book_id, " +
            "order_type_id, date_create) values (?,?,?,?)";

    private static final String UPDATE_STATUS_ON_ISSUED_BY_ID_ACCOUNT = "update book_order bo set status_id=4 " +
            "where bo.book_order_id=?";

    private static final String UPDATE_STATUS_ON_ENDED_BY_ID_ACCOUNT = "update book_order bo set status_id=5 " +
            "where bo.book_order_id=?";

    private static final String REGISTER_DATE_ISSUE_BY_ID = "update book_order set date_issue=? where book_order_id=?";

    private static final String REGISTER_DATE_RETURN_BY_ID = "update book_order set date_return=? where book_order_id=?";

    private static final String SELECT_REPEATED_BOOK_IN_NO_ENDED_BOOK_ORDERS = "select bo.book_order_id as book_order_id, " +
            "ad.account_id as account_id, ad.ad_first_name as ad_f_name, ad.ad_last_name as ad_l_name, " +
            "b.b_id as book_id, b.b_title as book_title, b.b_date_published as book_date_published, b.b_amount_of_left " +
            "as b_amount_of_left, ot.o_t_id as a_t_id, ot.o_t_name as o_t_name, bo.date_create as date_create, bo.date_issue " +
            "as date_issue, bo.date_return as date_return, os.o_s_id as o_s_id, os.o_s_name as o_s_name " +
            "from book_order bo join account_details ad on ad.account_id = bo.account_details_id " +
            "join book b on bo.book_id = b.b_id join order_type ot on ot.o_t_id = bo.order_type_id " +
            "join order_status os on bo.status_id = os.o_s_id where account_details_id=? and book_id=? and status_id<>5";

    private static final String SELECT_ALL_BOOK_ORDERS = "select bo.book_order_id as book_order_id, " +
            "ad.account_id as account_id, ad.ad_first_name as ad_f_name, ad.ad_last_name as ad_l_name, " +
            "b.b_id as book_id, b.b_title as book_title, b.b_date_published as book_date_published, b.b_amount_of_left " +
            "as b_amount_of_left, ot.o_t_id as a_t_id, ot.o_t_name as o_t_name, bo.date_create as date_create, bo.date_issue " +
            "as date_issue, bo.date_return as date_return, os.o_s_id as o_s_id, os.o_s_name as o_s_name " +
            "from book_order bo join account_details ad on ad.account_id = bo.account_details_id " +
            "join book b on bo.book_id = b.b_id join order_type ot on ot.o_t_id = bo.order_type_id " +
            "join order_status os on bo.status_id = os.o_s_id";

    private static final String SELECT_BOOK_ORDER_BY_ID_ACCOUNT = "select bo.book_order_id as book_order_id, " +
            "ad.account_id as account_id, ad.ad_first_name as ad_f_name, ad.ad_last_name as ad_l_name, " +
            "b.b_id as book_id, b.b_title as book_title, b.b_date_published as book_date_published, b.b_amount_of_left " +
            "as b_amount_of_left, ot.o_t_id as a_t_id, ot.o_t_name as o_t_name, bo.date_create as date_create, bo.date_issue " +
            "as date_issue, bo.date_return as date_return, os.o_s_id as o_s_id, os.o_s_name as o_s_name " +
            "from book_order bo join account_details ad on ad.account_id = bo.account_details_id " +
            "join book b on bo.book_id = b.b_id join order_type ot on ot.o_t_id = bo.order_type_id " +
            "join order_status os on bo.status_id = os.o_s_id where ad.account_id=? order by os.o_s_id";

    private static final String SELECT_BOOK_ORDER_BY_ID = "select bo.book_order_id as book_order_id, " +
            "ad.account_id as account_id, ad.ad_first_name as ad_f_name, ad.ad_last_name as ad_l_name, " +
            "b.b_id as book_id, b.b_title as book_title, b.b_date_published as book_date_published, b.b_amount_of_left " +
            "as b_amount_of_left, ot.o_t_id as a_t_id, ot.o_t_name as o_t_name, bo.date_create as date_create, bo.date_issue " +
            "as date_issue, bo.date_return as date_return, os.o_s_id as o_s_id, os.o_s_name as o_s_name " +
            "from book_order bo join account_details ad on ad.account_id = bo.account_details_id " +
            "join book b on bo.book_id = b.b_id join order_type ot on ot.o_t_id = bo.order_type_id " +
            "join order_status os on bo.status_id = os.o_s_id where bo.book_order_id=?";

    private static final String SELECT_BY_ACCOUNT_WITH_ORDER_STATUS_ISSUED = "select bo.book_order_id as book_order_id," +
            "ad.account_id as account_id, ad.ad_first_name as ad_f_name, ad.ad_last_name as ad_l_name,b.b_id as " +
            "book_id, b.b_title as book_title, b.b_date_published as book_date_published, b.b_amount_of_left as b_amount_of_left, " +
            "ot.o_t_id as a_t_id, ot.o_t_name as o_t_name, bo.date_create as date_create, bo.date_issue as date_issue, bo.date_return " +
            "as date_return, os.o_s_id as o_s_id, os.o_s_name as o_s_name from book_order bo join account_details ad " +
            "on ad.account_id = bo.account_details_id join book b on bo.book_id = b.b_id join order_type ot " +
            "on ot.o_t_id = bo.order_type_id join order_status os on bo.status_id = os.o_s_id where ad.account_id=? " +
            "and os.o_s_name='issued'";

    private static final String SELECT_ALL_UNCOMPLETED = "select bo.book_order_id as book_order_id, ad.account_id " +
            "as account_id, ad.ad_first_name as ad_f_name, ad.ad_last_name as ad_l_name,b.b_id as book_id, b.b_title " +
            "as book_title, b.b_date_published as book_date_published, b.b_amount_of_left as b_amount_of_left, ot.o_t_id " +
            "as a_t_id, ot.o_t_name as o_t_name,bo.date_create as date_create, bo.date_issue as date_issue, bo.date_return " +
            "as date_return, os.o_s_id as o_s_id, os.o_s_name as o_s_name from book_order bo join account_details ad " +
            "on ad.account_id = bo.account_details_id join book b on bo.book_id = b.b_id join order_type ot " +
            "on ot.o_t_id = bo.order_type_id join order_status os on bo.status_id = os.o_s_id " +
            "where os.o_s_name='claimed' or os.o_s_name='issued'";

    protected BookOrderDao(ConnectionPool pool) {
        super(pool, LOG);
    }

    @Override
    public Optional<BookOrder> create(BookOrder order) {
        LOG.trace("start create book order");
        Optional<BookOrder> createdOrder = Optional.empty();
        try (final Connection connection = pool.takeConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEW_BOOK_ORDER, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, order.getDetails().getId());
            preparedStatement.setLong(2, order.getBook().getId());
            preparedStatement.setInt(3, order.getType().ordinal() + 1);
            preparedStatement.setDate(4, order.getDateCreate());
            final int numberChangedLines = preparedStatement.executeUpdate();
            final ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (numberChangedLines != 0 && generatedKeys.next()) {
                long key = generatedKeys.getLong(1);
                createdOrder = Optional.of(BookOrder.with()
                        .id(key)
                        .details(order.getDetails())
                        .book(order.getBook())
                        .type(order.getType())
                        .dateCreate(order.getDateCreate())
                        .status(OrderStatus.CLAIMED)
                        .create());
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
        LOG.trace("start read order");
        Optional<BookOrder> bookOrder = Optional.empty();
        try (final Connection connection = pool.takeConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BOOK_ORDER_BY_ID)) {
            preparedStatement.setLong(1, id);
            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                final BookOrder executedBookOrder = executeBookOrder(resultSet).orElseThrow(()
                        -> new BookOrderDaoException("could not extract book order"));
                bookOrder = Optional.of(executedBookOrder);
            }
        } catch (SQLException e) {
            LOG.error("sql error, could not read book order", e);
        } catch (BookOrderDaoException e) {
            LOG.error("could not read book order", e);
        } catch (InterruptedException e) {
            LOG.error("method takeConnection from ConnectionPool was interrupted", e);
            Thread.currentThread().interrupt();
        }
        return bookOrder;
    }

    @Override
    public List<BookOrder> readAll() {
        LOG.trace("start readAll orders");
        List<BookOrder> bookOrders = new ArrayList<>();
        try (final Connection connection = pool.takeConnection();
             final Statement statement = connection.createStatement();
             final ResultSet resultSet = statement.executeQuery(SELECT_ALL_BOOK_ORDERS)) {
            while (resultSet.next()) {
                final BookOrder bookOrder = executeBookOrder(resultSet).orElseThrow(() ->
                        new BookOrderDaoException("could not extract book order"));
                bookOrders.add(bookOrder);
            }
            return bookOrders;
        } catch (SQLException e) {
            LOG.error("sql error, could not read book order", e);
        } catch (BookOrderDaoException e) {
            LOG.error("could not read book order", e);
        } catch (InterruptedException e) {
            LOG.error("method takeConnection from ConnectionPool was interrupted", e);
            Thread.currentThread().interrupt();
        }
        return Collections.emptyList();
    }

    public List<BookOrder> readAllUncompleted() {
        LOG.trace("start readAll uncompleted orders");
        List<BookOrder> bookOrders = new ArrayList<>();
        try (final Connection connection = pool.takeConnection();
             final Statement statement = connection.createStatement();
             final ResultSet resultSet = statement.executeQuery(SELECT_ALL_UNCOMPLETED)) {
            while (resultSet.next()) {
                final BookOrder bookOrder = executeBookOrder(resultSet).orElseThrow(() ->
                        new BookOrderDaoException("could not extract book order"));
                bookOrders.add(bookOrder);
            }
            return bookOrders;
        } catch (SQLException e) {
            LOG.error("sql error, could not read book order", e);
        } catch (BookOrderDaoException e) {
            LOG.error("could not read book order", e);
        } catch (InterruptedException e) {
            LOG.error("method takeConnection from ConnectionPool was interrupted", e);
            Thread.currentThread().interrupt();
        }
        return Collections.emptyList();
    }

    public Optional<BookOrder> readRepeatedBook(Long idAccount, Long idBook){
        Optional<BookOrder> bookOrder = Optional.empty();
        try (final Connection connection = pool.takeConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(SELECT_REPEATED_BOOK_IN_NO_ENDED_BOOK_ORDERS)) {
            preparedStatement.setLong(1, idAccount);
            preparedStatement.setLong(2, idBook);
            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                final BookOrder extractedBookOrder = executeBookOrder(resultSet).orElseThrow(() ->
                        new BookOrderDaoException("could not extract book order"));
                bookOrder = Optional.of(extractedBookOrder);
            }
        } catch (SQLException e) {
            LOG.error("sql error, could not read book order", e);
        } catch (BookOrderDaoException e) {
            LOG.error("could not read book order", e);
        } catch (InterruptedException e) {
            LOG.error("method takeConnection from ConnectionPool was interrupted", e);
            Thread.currentThread().interrupt();
        }
        return bookOrder;
    }

    public List<BookOrder> readByIdAccount(Long idAccount) {
        LOG.trace("start read order by id account");
        List<BookOrder> bookOrders = new ArrayList<>();
        try (final Connection connection = pool.takeConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BOOK_ORDER_BY_ID_ACCOUNT)) {
            preparedStatement.setLong(1, idAccount);
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                final BookOrder bookOrder = executeBookOrder(resultSet).orElseThrow(() ->
                        new BookOrderDaoException("could not extract book order"));
                bookOrders.add(bookOrder);
            }
        } catch (SQLException e) {
            LOG.error("sql error, could not read book order", e);
        } catch (BookOrderDaoException e) {
            LOG.error("could not read book order", e);
        } catch (InterruptedException e) {
            LOG.error("method takeConnection from ConnectionPool was interrupted", e);
            Thread.currentThread().interrupt();
        }
        return bookOrders;
    }

    @Override
    public Optional<BookOrder> update(BookOrder entity) {
        return Optional.empty();
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    public Optional<BookOrder> readByAccountWithOrderStatusIssue(Long idAccount) {
        Optional<BookOrder> bookOrder = Optional.empty();
        try (final Connection connection = pool.takeConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ACCOUNT_WITH_ORDER_STATUS_ISSUED)) {
            preparedStatement.setLong(1, idAccount);
            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                final BookOrder executedBookOrder = executeBookOrder(resultSet).orElseThrow(() ->
                        new BookOrderDaoException("could not extract book order"));
                bookOrder = Optional.of(executedBookOrder);
            }
        } catch (SQLException e) {
            LOG.error("sql error, could not read book order", e);
        } catch (BookOrderDaoException e) {
            LOG.error("could not read book order", e);
        } catch (InterruptedException e) {
            LOG.error("method takeConnection from ConnectionPool was interrupted", e);
            Thread.currentThread().interrupt();
        }
        return bookOrder;
    }

    public boolean updateStatusOnIssuedById(Long idBookOrder) {
        boolean updatedStatusBookOrder = false;
        try (final Connection connection = pool.takeConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_STATUS_ON_ISSUED_BY_ID_ACCOUNT)) {
            preparedStatement.setLong(1, idBookOrder);
            final int NumberChangedLines = preparedStatement.executeUpdate();
            if (NumberChangedLines != 0) {
                updatedStatusBookOrder = true;
            }
        } catch (SQLException e) {
            LOG.error("sql error, could not update status on issued book order", e);
        } catch (InterruptedException e) {
            LOG.error("method takeConnection from ConnectionPool was interrupted", e);
            Thread.currentThread().interrupt();
        }
        return updatedStatusBookOrder;
    }

    public boolean updateStatusOnEndedById(Long idBookOrder) {
        boolean updatedStatusBookOrder = false;
        try (final Connection connection = pool.takeConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_STATUS_ON_ENDED_BY_ID_ACCOUNT)) {
            preparedStatement.setLong(1, idBookOrder);
            final int NumberChangedLines = preparedStatement.executeUpdate();
            if (NumberChangedLines != 0) {
                updatedStatusBookOrder = true;
            }
        } catch (SQLException e) {
            LOG.error("sql error, could not update status on ended book order", e);
        } catch (InterruptedException e) {
            LOG.error("method takeConnection from ConnectionPool was interrupted", e);
            Thread.currentThread().interrupt();
        }
        return updatedStatusBookOrder;
    }

    public boolean registerDateOfIssueById(Long idBookOrder, Date dateIssue) {
        boolean updatedStatusBookOrder = false;
        try (final Connection connection = pool.takeConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(REGISTER_DATE_ISSUE_BY_ID)) {
            preparedStatement.setDate(1, dateIssue);
            preparedStatement.setLong(2, idBookOrder);
            final int NumberChangedLines = preparedStatement.executeUpdate();
            if (NumberChangedLines != 0) {
                updatedStatusBookOrder = true;
            }
        } catch (SQLException e) {
            LOG.error("sql error, could not registered date issue book order", e);
        } catch (InterruptedException e) {
            LOG.error("method takeConnection from ConnectionPool was interrupted", e);
            Thread.currentThread().interrupt();
        }
        return updatedStatusBookOrder;
    }

    public boolean registerDateOfEndedById(Long idBookOrder, Date dateReturn) {
        boolean updatedStatusBookOrder = false;
        try (final Connection connection = pool.takeConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(REGISTER_DATE_RETURN_BY_ID)) {
            preparedStatement.setDate(1, dateReturn);
            preparedStatement.setLong(2, idBookOrder);
            final int NumberChangedLines = preparedStatement.executeUpdate();
            if (NumberChangedLines != 0) {
                updatedStatusBookOrder = true;
            }
        } catch (SQLException e) {
            LOG.error("sql error, could not registered date return book order", e);
        } catch (InterruptedException e) {
            LOG.error("method takeConnection from ConnectionPool was interrupted", e);
            Thread.currentThread().interrupt();
        }
        return updatedStatusBookOrder;
    }

    private Optional<BookOrder> executeBookOrder(ResultSet resultSet) {
        try {
            return Optional.of(BookOrder.with().id(resultSet.getLong("book_order_id"))
                    .details(new AccountDetails(resultSet.getLong("account_id"),
                            resultSet.getString("ad_f_name"), resultSet.getString("ad_l_name")))
                    .book(new Book(resultSet.getLong("book_id"), resultSet.getString("book_title"),
                            resultSet.getDate("book_date_published"), resultSet.getInt("b_amount_of_left")))
                    .type(OrderType.valueOf(resultSet.getString("o_t_name").toUpperCase()))
                    .dateCreate(resultSet.getDate("date_create"))
                    .dateIssue(resultSet.getDate("date_issue"))
                    .dateReturn(resultSet.getDate("date_return"))
                    .status(OrderStatus.valueOf(resultSet.getString("o_s_name").toUpperCase()))
                    .create());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public static BookOrderDao getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final BookOrderDao INSTANCE = new BookOrderDao(ConnectionPool.lockingPool());
    }
}
