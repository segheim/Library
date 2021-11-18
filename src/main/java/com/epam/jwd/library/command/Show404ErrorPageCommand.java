package com.epam.jwd.library.command;

import com.epam.jwd.library.controller.RequestFactory;

public class Show404ErrorPageCommand implements Command{

    private static final String PATH_404_JSP = "/WEB-INF/jsp/404.jsp";

    private final RequestFactory requestFactory = RequestFactory.getInstance();

    private Show404ErrorPageCommand() {
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        return requestFactory.createForwardResponse(PATH_404_JSP);
    }

    public static Show404ErrorPageCommand getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final Show404ErrorPageCommand INSTANCE = new Show404ErrorPageCommand();
    }
}
