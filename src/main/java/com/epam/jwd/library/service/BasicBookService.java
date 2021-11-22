package com.epam.jwd.library.service;

import java.sql.Date;

public interface BasicBookService {

    boolean createBook(String title, Date date, int amount_of_left, String authorFirstName, String authorLastName);
}
