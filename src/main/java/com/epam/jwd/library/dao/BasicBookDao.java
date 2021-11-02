package com.epam.jwd.library.dao;

import com.epam.jwd.library.entity.Author;
import com.epam.jwd.library.entity.Book;

import java.util.List;

public interface BasicBookDao {

    List<Book> readByIdAuthor(Author author);

}
