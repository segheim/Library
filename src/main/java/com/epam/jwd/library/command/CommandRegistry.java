package com.epam.jwd.library.command;

import com.epam.jwd.library.connection.ConnectionPool;
import com.epam.jwd.library.dao.AuthorDao;
import com.epam.jwd.library.service.AuthorService;

public enum CommandRegistry {

    MAIN_PAGE(ShowMainPageCommand.getInstance(), "main_page"),
    AUTHOR_PAGE(new ShowAuthorsPageCommand(new AuthorService(new AuthorDao(ConnectionPool.lockingPool()))), "author_page"),
    DEFAULT_PAGE(Show404ErrorPageCommand.getInstance(), "404error");

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
