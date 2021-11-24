package com.epam.jwd.library.service;

import java.util.List;
import java.util.Optional;

public class UserService implements Service{

    private UserService() {
    }

    @Override
    public List findAll() {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public Optional findById(Long id) {
        return Optional.empty();
    }

    public static UserService getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final UserService INSTANCE = new UserService();
    }
}
