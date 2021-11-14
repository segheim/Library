package com.epam.jwd.library.service;

import com.epam.jwd.library.model.Entity;

import java.util.List;

public interface Service<T extends  Entity> {

    List<T> findAll();

}
