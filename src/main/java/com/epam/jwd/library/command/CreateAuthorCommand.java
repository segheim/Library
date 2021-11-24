package com.epam.jwd.library.command;

import com.epam.jwd.library.controller.RequestFactory;
import com.epam.jwd.library.model.Author;
import com.epam.jwd.library.service.AuthorService;

import java.util.List;
import java.util.Optional;

public class CreateAuthorCommand implements Command{

    private final AuthorService authorService;
    private final RequestFactory requestFactory = RequestFactory.getInstance();

    public CreateAuthorCommand(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        final String firstName = request.getParameter("first_name");
        final String lastName = request.getParameter("last_name");
        final Optional<Author> author = authorService.create(firstName, lastName);
        if (author.isPresent()) {
            final List<Author> authors = authorService.findAll();
            request.addAttributeToJsp("authors", authors);
            return requestFactory.createForwardResponse("/WEB-INF/jsp/authors.jsp");
        } else {
            request.addAttributeToJsp("errorCreateAuthorMessage", "Incorrect dates, please try again");
            return requestFactory.createForwardResponse("/WEB-INF/jsp/createauthor.jsp");
        }
    }

    public static CreateAuthorCommand getInstance() {
        return Holder.INSTANCE;
    }
    private static class Holder {
        public static final CreateAuthorCommand INSTANCE = new CreateAuthorCommand(AuthorService.getInstance());
    }
}
