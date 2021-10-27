package com.epam.jwd.library.command;

public enum CommandRegistry {

    MAIN_PAGE(ShowMainPageCommand.getInstance()),
    DEFAULT_PAGE(Show404ErrorPageCommand.getInstance());

    private final Command command;

    CommandRegistry(Command command) {
        this.command = command;
    }

    static Command of(String name) {
        for (CommandRegistry commandRegistry : values()) {
            if (commandRegistry.name().equals(name)) {
                return commandRegistry.command;
            }
        }
        return DEFAULT_PAGE.command;
    }
}
