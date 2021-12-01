package com.epam.jwd.library.dao;

import com.epam.jwd.library.model.BookOrder;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface BasicBookOrderDao<T> {

    List<T> readAllUncompleted();

    Optional<T> readRepeatedBook(Long idAccount, Long idBook);

    List<T> readByIdAccount(Long idAccount);

    Optional<T> readByAccountWithOrderStatusIssue(Long idAccount);

    boolean updateStatusOnIssuedById(Long idBookOrder);

    boolean updateStatusOnEndedById(Long idBookOrder);

    boolean registerDateOfIssueById(Long idBookOrder, Date dateIssue);

    boolean registerDateOfEndedById(Long idBookOrder, Date dateReturn);

}
