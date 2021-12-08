package com.epam.jwd.library.command;

import com.epam.jwd.library.controller.RequestFactory;

public class ShowCreateAuthorPageCommand implements Command {

    private static final String PATH_CREATE_AUTHOR_JSP = "/WEB-INF/jsp/createAuthor.jsp";

    private final RequestFactory requestFactory = RequestFactory.getInstance();

    private ShowCreateAuthorPageCommand() {
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        return requestFactory.createForwardResponse(PATH_CREATE_AUTHOR_JSP);
    }

    public static ShowCreateAuthorPageCommand getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final ShowCreateAuthorPageCommand INSTANCE = new ShowCreateAuthorPageCommand();
    }
}
