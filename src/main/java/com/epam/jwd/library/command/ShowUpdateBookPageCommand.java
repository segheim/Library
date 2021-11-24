package com.epam.jwd.library.command;

import com.epam.jwd.library.controller.RequestFactory;

public class ShowUpdateBookPageCommand implements Command{

    private static final String PATH_UPDATE_BOOK_JSP = "/WEB-INF/jsp/updatebook.jsp";

    private final RequestFactory requestFactory = RequestFactory.getInstance();

    @Override
    public CommandResponse execute(CommandRequest request) {
        return requestFactory.createForwardResponse(PATH_UPDATE_BOOK_JSP);
    }

    public static ShowUpdateBookPageCommand getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final ShowUpdateBookPageCommand INSTANCE = new ShowUpdateBookPageCommand();
    }
}
