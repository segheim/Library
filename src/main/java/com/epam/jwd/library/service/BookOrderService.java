package com.epam.jwd.library.service;

import com.epam.jwd.library.dao.BookOrderDao;
import com.epam.jwd.library.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sun.util.resources.LocaleData;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class BookOrderService implements Service<BookOrder>, BasicBookOrderService<BookOrder>{

    private static final Logger LOG = LogManager.getLogger(BookOrderService.class);

    private final BookOrderDao bookOrderDao;

    public BookOrderService(BookOrderDao bookOrderDao) {
        this.bookOrderDao = bookOrderDao;
    }

    @Override
    public Optional<BookOrder> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<BookOrder> findAll() {
        return bookOrderDao.readAll();
    }

    @Override
    public boolean delete(Long id) {
        return false;
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
        LOG.info("book order: {}", bookOrder);
        return bookOrderDao.create(bookOrder);
    }

    @Override
    public List<BookOrder> findByIdAccount(Long id) {

        return bookOrderDao.readByIdAccount(id);
    }

    public static BookOrderService getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final BookOrderService INSTANCE = new BookOrderService(BookOrderDao.getInstance());
    }
}
