package com.epam.jwd.library.command;

import com.epam.jwd.library.controller.RequestFactory;

public class LogoutCommand implements Command{

    private final RequestFactory requestFactory = RequestFactory.getInstance();

    @Override
    public CommandResponse execute(CommandRequest request) {
        request.clearSession();
        return requestFactory.createRedirectResponse("index.jsp");
    }

    public static LogoutCommand getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final LogoutCommand INSTANCE = new LogoutCommand();
    }
}
