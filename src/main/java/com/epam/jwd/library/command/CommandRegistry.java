package com.epam.jwd.library.command;

import com.epam.jwd.library.dao.AuthorDao;
import com.epam.jwd.library.dao.BookDao;
import com.epam.jwd.library.service.AuthorService;
import com.epam.jwd.library.service.BookService;

public enum CommandRegistry {

    MAIN_PAGE(new ShowMainPageCommand(), "main_page"),
    AUTHOR_PAGE(new ShowAuthorsPageCommand(new AuthorService(AuthorDao.getInstance())), "author_page"),
    BOOKS_PAGE(new ShowBooksPageCommand(new BookService(BookDao.getInstance())), "catalog"),
    DEFAULT_PAGE(new Show404ErrorPageCommand(), "404error");

    private final Command command;
    private final String path;

    CommandRegistry(Command command, String path) {
        this.command = command;
        this.path = path;
    }

    static Command of(String name) {
        for (CommandRegistry commandRegistry : values()) {
            if (commandRegistry.path.equalsIgnoreCase(name)) {
                return commandRegistry.command;
            }
        }
        return DEFAULT_PAGE.command;
    }
}
