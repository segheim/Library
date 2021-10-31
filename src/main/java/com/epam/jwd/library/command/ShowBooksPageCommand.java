package com.epam.jwd.library.command;

import com.epam.jwd.library.entity.Book;
import com.epam.jwd.library.service.BookService;

import java.util.List;

public class ShowBooksPageCommand implements Command{

    private final BookService bookService;

    private static final CommandResponse FORWARD_TO_BOOKS_PAGE_RESPONSE = new CommandResponse() {
        @Override
        public boolean isRedirect() {
            return false;
        }

        @Override
        public String getPath() {
            return "/WEB-INF/jsp/catalog.jsp";
        }
    };

    public ShowBooksPageCommand(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        final List<Book> books = bookService.findAll();
        request.addAttributeToJsp("books", books);
        return FORWARD_TO_BOOKS_PAGE_RESPONSE;
    }
}
