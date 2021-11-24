package com.epam.jwd.library.command;

import com.epam.jwd.library.controller.RequestFactory;
import com.epam.jwd.library.service.AuthorService;

public class DeleteAuthorCommand implements Command{

    private final AuthorService authorService;
    private final RequestFactory requestFactory = RequestFactory.getInstance();

    public DeleteAuthorCommand(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        return null;
    }

    public static DeleteAuthorCommand getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final DeleteAuthorCommand INSTANCE = new DeleteAuthorCommand(AuthorService.getInstance());
    }
}
