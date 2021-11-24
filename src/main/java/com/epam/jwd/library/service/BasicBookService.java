package com.epam.jwd.library.service;

import java.sql.Date;
import java.util.Optional;

public interface BasicBookService<T> {

    boolean createBookWithAuthor(String title, Date date, int amount_of_left, String authorFirstName, String authorLastName);

    Optional<T> update(Long id, String title, Date date, Integer amountOfLeft);
}
