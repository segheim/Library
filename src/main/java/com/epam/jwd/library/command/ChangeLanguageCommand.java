package com.epam.jwd.library.command;

import com.epam.jwd.library.controller.RequestFactory;
import com.epam.jwd.library.util.ConfigurationManager;

public class ChangeLanguageCommand implements Command {

    private static RequestFactory requestFactory = RequestFactory.getInstance();

    private ChangeLanguageCommand() {
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        return requestFactory.createRedirectResponse(ConfigurationManager.getProperty("url.main"));
    }

    public static ChangeLanguageCommand getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final ChangeLanguageCommand INSTANCE = new ChangeLanguageCommand();
    }
}
