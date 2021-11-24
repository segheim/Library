package com.epam.jwd.library.command;

import com.epam.jwd.library.controller.RequestFactory;
import com.epam.jwd.library.model.Book;
import com.epam.jwd.library.service.BookService;

import java.util.List;

public class DeleteBookCommand implements Command{

    private static final String PATH_CATALOG_JSP = "/WEB-INF/jsp/catalog.jsp";
    private static final String ID_PARAMETER_NAME = "id";
    private static final String REQUEST_ATTRIBUTE_NAME = "books";
    private final BookService bookService;
    private final RequestFactory requestFactory = RequestFactory.getInstance();

    public DeleteBookCommand(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        final Long id = Long.valueOf(request.getParameter(ID_PARAMETER_NAME));
        if (bookService.delete(id)) {
            final List<Book> books = bookService.findAll();
            request.addAttributeToJsp(REQUEST_ATTRIBUTE_NAME, books);
            return requestFactory.createForwardResponse(PATH_CATALOG_JSP);
        } else {
            request.addAttributeToJsp("errorPassMassage", "Could not delete book");
            return requestFactory.createForwardResponse("/WEB-INF/jsp/error.jsp");
        }
    }

    public static DeleteBookCommand getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final DeleteBookCommand INSTANCE = new DeleteBookCommand(BookService.getInstance());
    }
}
