package com.epam.jwd.library.command;

import com.epam.jwd.library.controller.RequestFactory;

public class ShowLoginPageCommand implements Command{

    private final RequestFactory requestFactory = RequestFactory.getInstance();

    @Override
    public CommandResponse execute(CommandRequest request) {
        return requestFactory.createResponse("/WEB-INF/jsp/login.jsp");
    }

    public static ShowLoginPageCommand getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final ShowLoginPageCommand INSTANCE = new ShowLoginPageCommand();
    }
}
