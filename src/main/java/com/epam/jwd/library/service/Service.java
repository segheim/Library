package com.epam.jwd.library.service;

import com.epam.jwd.library.model.Entity;

import java.util.List;
import java.util.Optional;

public interface Service<T extends  Entity> {

    Optional<T> findById(Long id);

    List<T> findAll();

    boolean delete(Long id);




}
