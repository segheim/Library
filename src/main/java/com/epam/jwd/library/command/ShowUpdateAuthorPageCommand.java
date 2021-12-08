package com.epam.jwd.library.command;

import com.epam.jwd.library.controller.RequestFactory;
import com.epam.jwd.library.model.Author;
import com.epam.jwd.library.service.AuthorService;

import java.util.Optional;

public class ShowUpdateAuthorPageCommand implements Command{

    private final AuthorService authorService;
    private final RequestFactory requestFactory = RequestFactory.getInstance();

    private ShowUpdateAuthorPageCommand(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        final Long idAuthor = Long.valueOf(request.getParameter("id"));
        final Optional<Author> author = authorService.findById(idAuthor);
        if (author.isPresent()) {
            request.addAttributeToJsp("author", author.get());
            return requestFactory.createForwardResponse("/WEB-INF/jsp/updateAuthor.jsp");
        } else {
            request.addAttributeToJsp("errorPassMassage", "Could not find author");
            return requestFactory.createForwardResponse("/WEB-INF/jsp/error.jsp");
        }
    }

    public static ShowUpdateAuthorPageCommand getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final ShowUpdateAuthorPageCommand INSTANCE = new ShowUpdateAuthorPageCommand(AuthorService.getInstance());
    }
}
