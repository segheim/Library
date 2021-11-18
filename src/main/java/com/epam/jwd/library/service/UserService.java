package com.epam.jwd.library.service;

import java.util.List;

public class UserService implements Service{

    private UserService() {
    }

    @Override
    public List findAll() {
        return null;
    }

    public static UserService getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final UserService INSTANCE = new UserService();
    }
}