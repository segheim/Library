package com.epam.jwd.library.service;

import com.epam.jwd.library.connection.ConnectionPool;
import com.epam.jwd.library.dao.BookDao;
import com.epam.jwd.library.dao.BookOrderDao;
import com.epam.jwd.library.exception.ServiceException;
import com.epam.jwd.library.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class BookOrderService implements Service<BookOrder>, BasicBookOrderService<BookOrder>{

    private static final Logger LOG = LogManager.getLogger(BookOrderService.class);

    private final BookOrderDao bookOrderDao;

    private BookOrderService(BookOrderDao bookOrderDao) {
        this.bookOrderDao = bookOrderDao;
    }

    @Override
    public Optional<BookOrder> findById(Long id) {
        return bookOrderDao.read(id);
    }

    @Override
    public List<BookOrder> findAll() {
        return bookOrderDao.readAll();
    }

    @Override
    public boolean delete(Long id) {
        return bookOrderDao.delete(id);
    }

    @Override
    public Optional<BookOrder> createBookOrder(Account account, Book book, String orderType) {
        final AccountDetails details = account.getDetails();
        final OrderType type = OrderType.valueOf(orderType);
        final LocalDate date = LocalDate.now();
        Date sqlDateCreate = Date.valueOf(date);
        BookOrder bookOrder = BookOrder.with()
                .details(details)
                .book(book)
                .type(type)
                .dateCreate(sqlDateCreate)
                .dateIssue(null)
                .dateReturn(null)
                .status(OrderStatus.CLAIMED)
                .createWithoutId();
        return bookOrderDao.create(bookOrder);
    }

    @Override
    public List<BookOrder> findOrdersByIdAccount(Long idAccount) {
        return bookOrderDao.readByIdAccount(idAccount);
    }

    @Override
    public List<BookOrder> findAllUncompleted() {
        return bookOrderDao.readAllUncompleted();
    }

    @Override
    public boolean isAccountWithOrderStatusIssue(Long id) {
        return bookOrderDao.readByAccountWithOrderStatusIssue(id).isPresent();
    }

    @Override
    public boolean changeStatusBookOrderOnIssued(Long idBookOrder) {
        boolean changedStatusBookOrder = false;
        try (Connection connection = ConnectionPool.lockingPool().takeConnection()) {
            connection.setAutoCommit(false);
            BookOrderDao bookOrderDao = BookOrderDao.getInstance();
            BookDao bookDao = BookDao.getInstance();
            final boolean isChangedStatusOnIssue = bookOrderDao.updateStatusOnIssuedById(idBookOrder);
            final LocalDate date = LocalDate.now();
            Date sqlDateIssue = Date.valueOf(date);
            final boolean isRegisteredDateIssue = bookOrderDao.registerDateOfIssueById(idBookOrder, sqlDateIssue);
            final BookOrder bookOrder = bookOrderDao.read(idBookOrder).orElseThrow(() -> new ServiceException("could not get BookOrder"));
            final Book book = bookOrder.getBook();
            final Long id = book.getId();
            final Integer amountOfLeft = book.getAmountOfLeft();
            if (amountOfLeft > 0) {
                final boolean isDecreasedAmountOfLeftInBook = bookDao.decreaseAmountOfLeft(id,  amountOfLeft - 1);
                if (!isChangedStatusOnIssue || !isRegisteredDateIssue || !isDecreasedAmountOfLeftInBook) {
                    throw new ServiceException("could not changed status, date issue, amount of left in book");
                }
                changedStatusBookOrder = true;
            }
            connection.setAutoCommit(true);
        } catch (InterruptedException e) {
            LOG.error("method takeConnection from ConnectionPool was interrupted", e);
            Thread.currentThread().interrupt();
        } catch (ServiceException e) {
            LOG.error("could not change status book order on issue", e);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return changedStatusBookOrder;
    }

    @Override
    public boolean changeStatusBookOrderOnEnded(Long idBookOrder) {
        boolean changedStatusBookOrder = false;
        try (Connection connection = ConnectionPool.lockingPool().takeConnection()) {
            connection.setAutoCommit(false);
            BookOrderDao bookOrderDao = BookOrderDao.getInstance();
            BookDao bookDao = BookDao.getInstance();
            final boolean isChangedStatusOnEnded = bookOrderDao.updateStatusOnEndedById(idBookOrder);
            final LocalDate date = LocalDate.now();
            Date sqlDateReturn = Date.valueOf(date);
            final boolean isRegisteredDateEnded = bookOrderDao.registerDateOfEndedById(idBookOrder, sqlDateReturn);
            final Book book = bookOrderDao.read(idBookOrder).orElseThrow(() -> new ServiceException("could not get BookOrder"))
                    .getBook();
            final Long idBook = book.getId();
            final Integer amountOfLeftBook = book.getAmountOfLeft();
            final boolean isIncreasedAmountOfLeftInBook = bookDao.decreaseAmountOfLeft(idBook,  amountOfLeftBook + 1);
            if (!isChangedStatusOnEnded || !isRegisteredDateEnded || !isIncreasedAmountOfLeftInBook) {
                throw new ServiceException("could not changed status, date ended, amount of left in book");
            }
            changedStatusBookOrder = true;
            connection.setAutoCommit(true);
        } catch (InterruptedException e) {
            LOG.error("method takeConnection from ConnectionPool was interrupted", e);
            Thread.currentThread().interrupt();
        } catch (ServiceException e) {
            LOG.error("could not change status book order on issue", e);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return changedStatusBookOrder;
    }

    @Override
    public boolean isRepeatedBookInNoEndedBookOrders(Long idAccount, Long idBook) {
       return bookOrderDao.readRepeatedBook(idAccount, idBook).isPresent();
    }

    public static BookOrderService getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final BookOrderService INSTANCE = new BookOrderService(BookOrderDao.getInstance());
    }
}
