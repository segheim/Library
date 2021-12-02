package com.epam.jwd.library.command;

import com.epam.jwd.library.controller.RequestFactory;
import com.epam.jwd.library.model.Author;
import com.epam.jwd.library.service.AuthorService;

import java.util.List;

public class DeleteAuthorCommand implements Command {

    private final AuthorService authorService;
    private final RequestFactory requestFactory = RequestFactory.getInstance();

    private DeleteAuthorCommand(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        final Long idAuthor = Long.valueOf(request.getParameter("id"));
        if (authorService.delete(idAuthor)) {
            final List<Author> authors = authorService.findAll();
            request.addAttributeToJsp("authors", authors);
            return requestFactory.createRedirectResponse("controller?command=author_page");
        } else {
            request.addAttributeToJsp("errorPassMassage", "Could not delete author");
            return requestFactory.createForwardResponse("/WEB-INF/jsp/error.jsp");
        }
    }

    public static DeleteAuthorCommand getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final DeleteAuthorCommand INSTANCE = new DeleteAuthorCommand(AuthorService.getInstance());
    }
}
