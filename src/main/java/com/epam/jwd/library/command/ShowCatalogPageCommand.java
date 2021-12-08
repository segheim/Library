package com.epam.jwd.library.command;

import com.epam.jwd.library.controller.RequestFactory;
import com.epam.jwd.library.model.Book;
import com.epam.jwd.library.service.BookService;

import java.util.List;

public class ShowCatalogPageCommand implements Command {

    private static final String REQUEST_ATTRIBUTE_NAME = "books";
    private static final String PATH_CATALOG_JSP = "/WEB-INF/jsp/catalog.jsp";

    private final BookService bookService;
    private final RequestFactory requestFactory = RequestFactory.getInstance();

    private ShowCatalogPageCommand(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        if (!bookService.findAll().isEmpty()) {
            final List<Book> books = bookService.findAll();
            request.addAttributeToJsp(REQUEST_ATTRIBUTE_NAME, books);
            return requestFactory.createForwardResponse(PATH_CATALOG_JSP);
        } else {
            request.addAttributeToJsp("errorPassMassage", "Could not find books");
            return requestFactory.createForwardResponse("/WEB-INF/jsp/error.jsp");
        }
    }

    public static ShowCatalogPageCommand getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final ShowCatalogPageCommand INSTANCE = new ShowCatalogPageCommand(BookService.getInstance());
    }
}
