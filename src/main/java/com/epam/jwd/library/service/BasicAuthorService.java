package com.epam.jwd.library.service;

import java.util.Optional;

public interface BasicAuthorService<T> {

    Optional<T> create(String firstName, String lastName);

    Optional<T> update(Long id, String firstName, String lastName);
}
