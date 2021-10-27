package com.epam.jwd.library.command;

public class Show404ErrorPageCommand implements Command{

    private static Show404ErrorPageCommand instance;

    public static final CommandResponse FORWARD_TO_404_ERROR_PAGE_RESPONSE = new CommandResponse() {
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
        return FORWARD_TO_404_ERROR_PAGE_RESPONSE;
    }

    public static Show404ErrorPageCommand getInstance() {
        if(instance == null) {
            instance = new Show404ErrorPageCommand();
        }
        return instance;
    }
}
