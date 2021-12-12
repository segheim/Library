package com.epam.jwd.library.command;

import com.epam.jwd.library.controller.RequestFactory;
import com.epam.jwd.library.util.ConfigurationManager;

public class ShowCreateAuthorPageCommand implements Command {

    private final RequestFactory requestFactory = RequestFactory.getInstance();

    private ShowCreateAuthorPageCommand() {
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        return requestFactory.createForwardResponse(ConfigurationManager.getProperty("path.page.create.author"));
    }

    public static ShowCreateAuthorPageCommand getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final ShowCreateAuthorPageCommand INSTANCE = new ShowCreateAuthorPageCommand();
    }
}
