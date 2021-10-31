package com.epam.jwd.library.command;

public class Show404ErrorPageCommand implements Command{

    private static final CommandResponse FORWARD_TO_404_ERROR_PAGE_RESPONSE = new CommandResponse() {
        @Override
        public boolean isRedirect() {
            return false;
        }

        @Override
        public String getPath() {
            return "/WEB-INF/jsp/404.jsp";
        }
    };

    @Override
    public CommandResponse execute(CommandRequest request) {
        return FORWARD_TO_404_ERROR_PAGE_RESPONSE;
    }
}
