package com.epam.jwd.library.command;

import com.epam.jwd.library.controller.RequestFactory;

public class ShowCreateBookPageCommand implements Command {

    private final RequestFactory requestFactory = RequestFactory.getInstance();

    private ShowCreateBookPageCommand() {
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        return requestFactory.createForwardResponse("/WEB-INF/jsp/createBook.jsp");
    }

    public static ShowCreateBookPageCommand getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final ShowCreateBookPageCommand INSTANCE = new ShowCreateBookPageCommand();
    }
}
