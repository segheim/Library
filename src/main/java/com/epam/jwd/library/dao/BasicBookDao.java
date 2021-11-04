package com.epam.jwd.library.dao;

import com.epam.jwd.library.model.Author;
import com.epam.jwd.library.model.Book;

import java.util.List;

public interface BasicBookDao {

    List<Book> readByIdAuthor(Author author);

}
