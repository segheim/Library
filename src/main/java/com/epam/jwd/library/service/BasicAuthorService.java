package com.epam.jwd.library.service;

import java.util.List;
import java.util.Optional;

public interface BasicAuthorService<T> {

    List<T> findAll();

    Optional<T> update(Long id, String firstName, String lastName);
}
