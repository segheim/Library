package com.epam.jwd.library.dao;

import java.util.List;
import java.util.Optional;

public interface BasicBookDao<T> {

    Optional<T> readByTitle(String title);

    boolean createBookInAuthorToBook(Long idBook, Long idAuthor);

    Optional<T> readWithAuthors(Long id);

    List<T> readAllWithAuthors();

    boolean decreaseAmountOfLeft(Long idBook, Integer amountOfLeft);

}
