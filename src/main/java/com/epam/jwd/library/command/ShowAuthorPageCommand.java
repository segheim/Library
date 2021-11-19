package com.epam.jwd.library.command;

import com.epam.jwd.library.controller.RequestFactory;
import com.epam.jwd.library.model.Author;
import com.epam.jwd.library.service.AuthorService;

import java.util.List;

public class ShowAuthorPageCommand implements Command{

    private static final String REQUEST_ATTRIBUTE_NAME = "authors";
    private static final String PATH_AUTHOR_JSP = "/WEB-INF/jsp/author.jsp";

    private final AuthorService authorService;
    private final RequestFactory requestFactory = RequestFactory.getInstance();


    private ShowAuthorPageCommand(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        final List<Author> authors = authorService.findAll();
        request.addAttributeToJsp(REQUEST_ATTRIBUTE_NAME, authors);
        return requestFactory.createForwardResponse(PATH_AUTHOR_JSP);
    }

    public static ShowAuthorPageCommand getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final ShowAuthorPageCommand INSTANCE = new ShowAuthorPageCommand(AuthorService.getInstance());
    }
}
