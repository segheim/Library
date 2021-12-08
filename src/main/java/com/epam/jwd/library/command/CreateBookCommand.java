package com.epam.jwd.library.command;

import com.epam.jwd.library.controller.RequestFactory;
import com.epam.jwd.library.service.BookService;

import java.sql.Date;

public class CreateBookCommand implements Command {

    private static final String TITLE_PARAMETER_NAME = "title";
    private static final String DATE_PUBLISHED_PARAMETER_NAME = "date_published";
    private static final String AMOUNT_OF_LEFT_PARAMETER_NAME = "amount_of_left";
    private static final String AUTHOR_FIRST_NAME_PARAMETER_NAME = "author_first_name";
    private static final String AUTHOR_LAST_NAME_PARAMETER_NAME = "author_last_name";
    private static final String URL_CATALOG_PAGE = "controller?command=catalog_page";
    private static final String PATH_CREATE_BOOK_JSP = "/WEB-INF/jsp/createBook.jsp";
    private static final String ERROR_CREAT_BOOK_MESSAGE_NAME = "errorCreatBookMessage";
    private static final String ERROR_CREATE_BOOK_MESSAGE_ATTRIBUTE = "Incorrect dates, please try again";

    private final BookService bookService;
    private final RequestFactory requestFactory = RequestFactory.getInstance();

    private CreateBookCommand(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        final String title = request.getParameter(TITLE_PARAMETER_NAME);
        final Date datePublished = Date.valueOf(request.getParameter(DATE_PUBLISHED_PARAMETER_NAME));
        final Integer amountOfLeft = Integer.valueOf(request.getParameter(AMOUNT_OF_LEFT_PARAMETER_NAME));
        final String authorFirstName = request.getParameter(AUTHOR_FIRST_NAME_PARAMETER_NAME);
        final String authorLastName = request.getParameter(AUTHOR_LAST_NAME_PARAMETER_NAME);
        final boolean isCreateBook = bookService.createBookWithAuthor(title, datePublished, amountOfLeft, authorFirstName, authorLastName);
        if (isCreateBook) {
            return requestFactory.createRedirectResponse(URL_CATALOG_PAGE);
        } else {
            request.addAttributeToJsp(ERROR_CREAT_BOOK_MESSAGE_NAME, ERROR_CREATE_BOOK_MESSAGE_ATTRIBUTE);
            return requestFactory.createForwardResponse(PATH_CREATE_BOOK_JSP);
        }
    }

    public static CreateBookCommand getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final CreateBookCommand INSTANCE = new CreateBookCommand(BookService.getInstance());
    }
}
