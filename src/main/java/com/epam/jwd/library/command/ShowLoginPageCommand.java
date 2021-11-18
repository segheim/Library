package com.epam.jwd.library.command;

import com.epam.jwd.library.controller.RequestFactory;

public class ShowLoginPageCommand implements Command{

    private static final String PATH_LOGIN_JSP = "/WEB-INF/jsp/login.jsp";

    private final RequestFactory requestFactory = RequestFactory.getInstance();

    @Override
    public CommandResponse execute(CommandRequest request) {
        return requestFactory.createForwardResponse(PATH_LOGIN_JSP);
    }

    public static ShowLoginPageCommand getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final ShowLoginPageCommand INSTANCE = new ShowLoginPageCommand();
    }
}
