package com.epam.jwd.library.command;

import com.epam.jwd.library.entity.Author;
import com.epam.jwd.library.service.AuthorService;

import java.util.List;

public class ShowAuthorsPageCommand implements Command{

    private final AuthorService authorService;

    public static final CommandResponse FORWARD_TO_AUTHOR_PAGE_RESPONSE = new CommandResponse() {
        @Override
        public boolean isRedirect() {
            return false;
        }

        @Override
        public String getPath() {
            return "/WEB-INF/jsp/author.jsp";
        }
    };

    public ShowAuthorsPageCommand(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        final List<Author> authors = authorService.findAll();
        request.addAttributeToJsp("authors", authors);
        return FORWARD_TO_AUTHOR_PAGE_RESPONSE;
    }
}
