package com.epam.jwd.library.command;

import com.epam.jwd.library.controller.RequestFactory;
import com.epam.jwd.library.model.Book;
import com.epam.jwd.library.service.BookService;

import java.util.Optional;

public class ShowBookPageCommand implements Command{

    private final BookService bookService;
    private final RequestFactory requestFactory = RequestFactory.getInstance();

    public ShowBookPageCommand(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        final Long id = Long.valueOf(request.getParameter("id"));
        final Optional<Book> book = bookService.findById(id);
        request.addAttributeToJsp("book", book.get());
        return requestFactory.createForwardResponse("/WEB-INF/jsp/book.jsp");
    }

    public static ShowBookPageCommand getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final ShowBookPageCommand INSTANCE = new ShowBookPageCommand(BookService.getInstance());
    }
}
