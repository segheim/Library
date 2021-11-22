package com.epam.jwd.library.command;

import com.epam.jwd.library.controller.RequestFactory;
import com.epam.jwd.library.service.BookService;

public class UpdateBookCommand implements Command {

    private final BookService bookService;
    private final RequestFactory requestFactory = RequestFactory.getInstance();

    public UpdateBookCommand(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        final Long idBook = Long.valueOf(request.getParameter("id"));
//        bookService.update(idBook);
        return null;
    }

    public static UpdateBookCommand getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final UpdateBookCommand INSTANCE = new UpdateBookCommand(BookService.getInstance());
    }
}
