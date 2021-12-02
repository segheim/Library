package com.epam.jwd.library.command;

import com.epam.jwd.library.controller.RequestFactory;
import com.epam.jwd.library.model.Author;
import com.epam.jwd.library.service.AuthorService;

import java.util.List;
import java.util.Optional;

public class UpdateAuthorCommand implements Command {

    private final AuthorService authorService;
    private final RequestFactory requestFactory = RequestFactory.getInstance();

    private UpdateAuthorCommand(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        final Long idAuthor = Long.valueOf(request.getParameter("id"));
        final String firstName = request.getParameter("first_name");
        final String lastName = request.getParameter("last_name");
        final Optional<Author> updatedAuthor = authorService.update(idAuthor, firstName, lastName);
        if (updatedAuthor.isPresent()) {
            final List<Author> authors = authorService.findAll();
            request.addAttributeToJsp("authors", authors);
            return requestFactory.createRedirectResponse("controller?command=author_page");
        } else {
            request.addAttributeToJsp("errorPassMassage", "Could not update author");
            return requestFactory.createForwardResponse("/WEB-INF/jsp/error.jsp");
        }
    }

    public static UpdateAuthorCommand getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final UpdateAuthorCommand INSTANCE = new UpdateAuthorCommand(AuthorService.getInstance());
    }
}
