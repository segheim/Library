package com.epam.jwd.library.command;

import com.epam.jwd.library.controller.RequestFactory;
import com.epam.jwd.library.service.BookService;

public class DeleteBookCommand implements Command {

    private static final String URL_CATALOG_PAGE = "controller?command=catalog_page";
    private static final String ID_PARAMETER_NAME = "id";
    private final BookService bookService;
    private final RequestFactory requestFactory = RequestFactory.getInstance();

    private DeleteBookCommand(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        final Long id = Long.valueOf(request.getParameter(ID_PARAMETER_NAME));
        if (bookService.delete(id)) {
            return requestFactory.createRedirectResponse(URL_CATALOG_PAGE);
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
