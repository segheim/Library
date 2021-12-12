package com.epam.jwd.library.command;

import com.epam.jwd.library.controller.RequestFactory;
import com.epam.jwd.library.util.ConfigurationManager;

public class ShowErrorPageCommand implements Command{

    private final RequestFactory requestFactory = RequestFactory.getInstance();

    private ShowErrorPageCommand() {
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        return requestFactory.createForwardResponse(ConfigurationManager.getProperty("path.page.error"));
    }

    public static ShowErrorPageCommand getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final ShowErrorPageCommand INSTANCE = new ShowErrorPageCommand();
    }
}
