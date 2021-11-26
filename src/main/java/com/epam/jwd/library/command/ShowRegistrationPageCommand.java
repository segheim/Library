package com.epam.jwd.library.command;

import com.epam.jwd.library.controller.RequestFactory;

public class ShowRegistrationPageCommand implements Command{

    private final RequestFactory requestFactory = RequestFactory.getInstance();

    @Override
    public CommandResponse execute(CommandRequest request) {
        return requestFactory.createForwardResponse("/WEB-INF/jsp/registration.jsp");
    }

    public static ShowRegistrationPageCommand getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final ShowRegistrationPageCommand INSTANCE = new ShowRegistrationPageCommand();
    }
}
