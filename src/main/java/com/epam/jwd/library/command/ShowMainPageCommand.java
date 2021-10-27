package com.epam.jwd.library.command;

public class ShowMainPageCommand implements Command {

    private static ShowMainPageCommand instance;

    private static final CommandResponse FORWARD_TO_MAIN_PAGE_RESPONSE = new CommandResponse() {
        @Override
        public boolean isRedirect() {
            return false;
        }

        @Override
        public String getPath() {
            return "/WEB-INF/jsp/main.jsp";
        }
    };

    @Override
    public CommandResponse execute(CommandRequest request) {
        return FORWARD_TO_MAIN_PAGE_RESPONSE;
    }

    public static ShowMainPageCommand getInstance() {
        if(instance == null) {
            instance = new ShowMainPageCommand();
        }
        return instance;
    }
}
