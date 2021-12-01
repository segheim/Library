package com.epam.jwd.library.service;

import com.epam.jwd.library.model.Account;
import com.epam.jwd.library.model.Book;
import com.epam.jwd.library.model.Entity;
import com.epam.jwd.library.model.OrderType;

import java.util.List;
import java.util.Optional;

public interface BasicBookOrderService<T extends Entity> {

    Optional<T> createBookOrder(Account account, Book book, String orderType);

    List<T> findByIdAccount(Long id);

    List<T> findAllUncompleted();

    boolean isAccountWithOrderStatusIssue(Long id);

    boolean changeStatusBookOrderOnIssued(Long id);

    boolean changeStatusBookOrderOnEnded(Long id);
}
