package com.epam.jwd.library.command;

import com.epam.jwd.library.controller.RequestFactory;
import com.epam.jwd.library.service.AccountService;

public class RegistrationCommand implements Command {

    private final AccountService accountService;
    private final RequestFactory requestFactory = RequestFactory.getInstance();

    private RegistrationCommand(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        final String login = request.getParameter("login");
        final String password = request.getParameter("password");
        final String firstName = request.getParameter("first_name");
        final String lastName = request.getParameter("last_name");
        if (accountService.create(login, password, firstName, lastName).isPresent()) {
            return requestFactory.createRedirectResponse("index.jsp");
        } else {
            request.addAttributeToJsp("errorRegistrationMessage", "Incorrect dates, please try again");
            return requestFactory.createForwardResponse("/WEB-INF/jsp/registration.jsp");
        }
    }

    public static RegistrationCommand getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final RegistrationCommand INSTANCE = new RegistrationCommand(AccountService.getInstance());
    }
}
