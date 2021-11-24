package com.epam.jwd.library.command;

import com.epam.jwd.library.controller.RequestFactory;

public class ShowCreateBookPageCommand implements Command{

    private final RequestFactory requestFactory = RequestFactory.getInstance();

    @Override
    public CommandResponse execute(CommandRequest request) {
        return requestFactory.createForwardResponse("/WEB-INF/jsp/createbook.jsp");
    }

    public static ShowCreateBookPageCommand getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final ShowCreateBookPageCommand INSTANCE = new ShowCreateBookPageCommand();
    }
}
