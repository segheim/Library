package com.epam.jwd.library.command;

import com.epam.jwd.library.controller.RequestFactory;
import com.epam.jwd.library.model.Book;
import com.epam.jwd.library.service.BookService;

import java.util.Optional;

public class ShowUpdateBookPageCommand implements Command{

    private static final String PATH_UPDATE_BOOK_JSP = "/WEB-INF/jsp/updatebook.jsp";

    private final BookService bookService;
    private final RequestFactory requestFactory = RequestFactory.getInstance();

    private ShowUpdateBookPageCommand(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        final Long idBook = Long.valueOf(request.getParameter("id"));
        final Optional<Book> book = bookService.findById(idBook);
        if (book.isPresent()) {
            request.addAttributeToJsp("book", book.get());
            return requestFactory.createForwardResponse("/WEB-INF/jsp/updatebook.jsp");
        } else {
            request.addAttributeToJsp("errorPassMassage", "Could not find book");
            return requestFactory.createForwardResponse("/WEB-INF/jsp/error.jsp");
        }
    }

    public static ShowUpdateBookPageCommand getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final ShowUpdateBookPageCommand INSTANCE = new ShowUpdateBookPageCommand(BookService.getInstance());
    }
}
