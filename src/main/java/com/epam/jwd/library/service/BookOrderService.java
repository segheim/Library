package com.epam.jwd.library.service;

import com.epam.jwd.library.model.BookOrder;

import java.util.List;
import java.util.Optional;

public class BookOrderService implements Service<BookOrder>, BasicBookOrderService<BookOrder>{


    @Override
    public Optional<BookOrder> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<BookOrder> findAll() {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
