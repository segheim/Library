package com.epam.jwd.library.command;

import com.epam.jwd.library.controller.RequestFactory;

public class ShowMainPageCommand implements Command {

    private static final String PATH_MAIN_JSP = "/WEB-INF/jsp/main.jsp";

    private final RequestFactory requestFactory = RequestFactory.getInstance();

    private ShowMainPageCommand() {
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        return requestFactory.createForwardResponse(PATH_MAIN_JSP);
    }

    public static ShowMainPageCommand getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final ShowMainPageCommand INSTANCE = new ShowMainPageCommand();
    }
}
