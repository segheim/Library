package com.epam.jwd.library.command;

import com.epam.jwd.library.controller.RequestFactory;

public class LogoutCommand implements Command {

    private static final String PATH_INDEX_JSP = "index.jsp";

    private final RequestFactory requestFactory = RequestFactory.getInstance();

    private LogoutCommand() {
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        request.clearSession();
        return requestFactory.createRedirectResponse(PATH_INDEX_JSP);
    }

    public static LogoutCommand getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final LogoutCommand INSTANCE = new LogoutCommand();
    }
}
