package com.epam.jwd.library.service;

import com.epam.jwd.library.model.Entity;

import java.sql.Date;
import java.util.Optional;

public interface BasicBookService<T extends Entity> {

    boolean createBookWithAuthor(String title, String date, int amount_of_left, String authorFirstName, String authorLastName);

    Optional<T> update(Long id, String title, String date, Integer amountOfLeft);

}
