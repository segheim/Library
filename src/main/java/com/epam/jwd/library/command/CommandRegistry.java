package com.epam.jwd.library.command;

public enum CommandRegistry {

    MAIN_PAGE(ShowMainPageCommand.getInstance(), "main_page"),
    ACCOUNT_PAGE(ShowAccountPageCommand.getInstance(), "account_page"),
    AUTHOR_PAGE(ShowAuthorPageCommand.getInstance(), "author_page"),
    BOOK_PAGE(ShowBookPageCommand.getInstance(), "book_page"),
    CATALOG_PAGE(ShowCatalogPageCommand.getInstance(), "catalog_page"),
    CREATE_BOOK_PAGE(ShowCreateBookPageCommand.getInstance(), "create_book_page"),
    CREATE_AUTHOR_PAGE(ShowCreateAuthorPageCommand.getInstance(), "create_author_page"),
    UPDATE_BOOK_PAGE(ShowUpdateBookPageCommand.getInstance(), "update_book_page"),
    UPDATE_AUTHOR_PAGE(ShowUpdateAuthorPageCommand.getInstance(), "update_author_page"),
    BOOK_ORDER_PAGE(ShowBookOrderPageCommand.getInstance(), "book_order_page"),
    ERROR(ShowErrorPageCommand.getInstance(), "error_page"),
    DELETE_BOOK(DeleteBookCommand.getInstance(), "delete_book"),
    DELETE_AUTHOR(DeleteAuthorCommand.getInstance(), "delete_author"),
    DELETE_ACCOUNT(DeleteAccountCommand.getInstance(), "delete_account"),
    UPDATE_BOOK(UpdateBookCommand.getInstance(), "update_book"),
    UPDATE_AUTHOR(UpdateAuthorCommand.getInstance(), "update_author"),
    LOGIN_PAGE(ShowLoginPageCommand.getInstance(), "login_page"),
    CREATE_BOOK(CreateBookCommand.getInstance(), "create_book"),
    CREATE_AUTHOR(CreateAuthorCommand.getInstance(), "create_author"),
    CREATE_BOOK_ORDER(CreateBookOrderCommand.getInstance(), "create_book_order"),
    REGISTRATION_PAGE(ShowRegistrationPageCommand.getInstance(), "registration_page"),
    REGISTRATION(RegistrationCommand.getInstance(), "registration"),
    LOGIN(LoginCommand.getInstance(), "login"),
    LOGOUT(LogoutCommand.getInstance(), "logout"),
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
