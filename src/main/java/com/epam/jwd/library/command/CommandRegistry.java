package com.epam.jwd.library.command;

public enum CommandRegistry {

    MAIN_PAGE(ShowMainPageCommand.getInstance(), "main_page"),
    AUTHOR_PAGE(ShowAuthorsPageCommand.getInstance(), "author_page"),
    BOOKS_PAGE(ShowCatalogPageCommand.getInstance(), "catalog"),
    DEFAULT_PAGE(ShowMainPageCommand.getInstance(), "");

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
