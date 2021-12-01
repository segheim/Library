package com.epam.jwd.library.dao;

import java.util.Optional;

public interface BasicAuthorDao<T> {

    Optional<T> readAuthorByLastName(String lastName);

}
