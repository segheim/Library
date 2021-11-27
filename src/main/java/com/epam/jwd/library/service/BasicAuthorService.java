package com.epam.jwd.library.service;

import com.epam.jwd.library.model.Entity;

import java.util.Optional;

public interface BasicAuthorService<T extends Entity> {

    Optional<T> create(String firstName, String lastName);

    Optional<T> update(Long id, String firstName, String lastName);
}
