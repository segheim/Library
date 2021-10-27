package com.epam.jwd.library.dao;

import com.epam.jwd.library.entity.Entity;

import java.util.List;

public interface BasicDao<T extends Entity> {

    T create(T entity);

    T read(Long id);

    List<T> readAll();

    T update(T entity);

    boolean delete(T entity);

}
