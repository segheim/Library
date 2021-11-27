package com.epam.jwd.library.command;

import com.epam.jwd.library.controller.RequestFactory;
import com.epam.jwd.library.model.Book;
import com.epam.jwd.library.service.BookService;

import java.util.Optional;

public class ShowBookOrderPageCommand implements Command{

    private final BookService bookService;
    private final RequestFactory requestFactory = RequestFactory.getInstance();

    public ShowBookOrderPageCommand(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        final Long idBook = Long.valueOf(request.getParameter("id"));
        final Optional<Book> book = bookService.findById(idBook);
        request.addAttributeToJsp("book", book.get());
        return requestFactory.createForwardResponse("/WEB-INF/jsp/bookorder.jsp");
    }

    public static ShowBookOrderPageCommand getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final ShowBookOrderPageCommand INSTANCE = new ShowBookOrderPageCommand(BookService.getInstance());
    }
}
