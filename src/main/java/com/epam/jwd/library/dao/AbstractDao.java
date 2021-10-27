package com.epam.jwd.library.dao;

import com.epam.jwd.library.entity.Entity;

import java.util.List;

public abstract class AbstractDao<T extends Entity>{

    public abstract boolean create(T entity);

    public abstract T read(Long id);

    public abstract List<T> readAll();

    public abstract T update(T entity);

    public abstract boolean delete(T entity);
}
