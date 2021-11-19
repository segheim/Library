package com.epam.jwd.library.command;

import com.epam.jwd.library.controller.RequestFactory;

public class ShowErrorPageCommand implements Command{

    private static final String PATH_ERROR_JSP = "/WEB-INF/jsp/error.jsp";

    private final RequestFactory requestFactory = RequestFactory.getInstance();

    private ShowErrorPageCommand() {
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        return requestFactory.createForwardResponse(PATH_ERROR_JSP);
    }

    public static ShowErrorPageCommand getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final ShowErrorPageCommand INSTANCE = new ShowErrorPageCommand();
    }
}
