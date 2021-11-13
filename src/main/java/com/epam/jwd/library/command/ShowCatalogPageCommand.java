package com.epam.jwd.library.command;

import com.epam.jwd.library.controller.RequestFactory;
import com.epam.jwd.library.model.Book;
import com.epam.jwd.library.service.BookService;

import java.util.List;

public class ShowCatalogPageCommand implements Command{

    private final BookService bookService;
    private final RequestFactory requestFactory = RequestFactory.getInstance();

    private ShowCatalogPageCommand(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        final List<Book> books = bookService.findAll();
        request.addAttributeToJsp("books", books);
        return requestFactory.createResponse("/WEB-INF/jsp/catalog.jsp");
    }

    public static ShowCatalogPageCommand getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final ShowCatalogPageCommand INSTANCE = new ShowCatalogPageCommand(BookService.getInstance());
    }

}
