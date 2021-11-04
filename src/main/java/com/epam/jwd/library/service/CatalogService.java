package com.epam.jwd.library.service;

import com.epam.jwd.library.dao.AuthorDao;
import com.epam.jwd.library.dao.BookDao;

public class CatalogService {

    private final BookDao bookDao;
    private final AuthorDao authorDao;


    public CatalogService(BookDao bookDao, AuthorDao authorDao) {
        this.bookDao = bookDao;
        this.authorDao = authorDao;
    }

}
