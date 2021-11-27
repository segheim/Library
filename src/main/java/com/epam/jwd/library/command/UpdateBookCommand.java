package com.epam.jwd.library.command;

import com.epam.jwd.library.controller.RequestFactory;
import com.epam.jwd.library.model.Book;
import com.epam.jwd.library.service.BookService;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public class UpdateBookCommand implements Command {

    private final BookService bookService;
    private final RequestFactory requestFactory = RequestFactory.getInstance();

    public UpdateBookCommand(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        final Long idBook = Long.valueOf(request.getParameter("id"));
        final String title = request.getParameter("title");
        final Date datePublished = Date.valueOf(request.getParameter("date_published"));
        final Integer amountOfLeft = Integer.valueOf(request.getParameter("amount_of_left"));
        final Optional<Book> updateBook = bookService.update(idBook, title, datePublished, amountOfLeft);
        if (updateBook.isPresent()) {
            final List<Book> books = bookService.findAll();
            request.addAttributeToJsp("books", books);
            return requestFactory.createRedirectResponse("controller?command=catalog_page");
        }else {
            request.addAttributeToJsp("errorPassMassage", "Could not update book");
            return requestFactory.createForwardResponse("/WEB-INF/jsp/error.jsp");
        }
    }

    public static UpdateBookCommand getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final UpdateBookCommand INSTANCE = new UpdateBookCommand(BookService.getInstance());
    }
}
