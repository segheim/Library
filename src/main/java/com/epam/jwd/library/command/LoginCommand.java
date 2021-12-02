package com.epam.jwd.library.command;

import com.epam.jwd.library.controller.RequestFactory;
import com.epam.jwd.library.model.Account;
import com.epam.jwd.library.service.AccountService;

import java.util.Optional;

public class LoginCommand implements Command {

    private static final String SESSION_ATTRIBUTE_NAME = "account";
    private static final String LOGIN_REQUEST_PARAMETER = "login";
    private static final String PASSWORD_REQUEST_PARAMETER = "password";
    private static final String PATH_INDEX_JSP = "index.jsp";
    private static final String PATH_LOGIN_JSP = "/WEB-INF/jsp/login.jsp";
    private static final String ERROR_LOGIN_PASS_MESSAGE_NAME = "errorLoginPassMessage";
    private static final String ERROR = "Incorrect login or password, try again";
    private static final String ERROR_LOGIN_PASS_MASSAGE_ATTRIBUTE = "Incorrect login or password, try again";

    private final AccountService accountService;
    private final RequestFactory requestFactory = RequestFactory.getInstance();

    private LoginCommand(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        if (request.sessionExist() && request.takeFromSession(SESSION_ATTRIBUTE_NAME).isPresent()) {
            return null;
        }
        final String login = request.getParameter(LOGIN_REQUEST_PARAMETER);
        final String password = request.getParameter(PASSWORD_REQUEST_PARAMETER);
        final Optional<Account> account = accountService.authenticate(login, password);
        if (account.isPresent()) {
            request.clearSession();
            request.createSession();
            request.addAttributeToSession(SESSION_ATTRIBUTE_NAME, account.get());
            return requestFactory.createRedirectResponse(PATH_INDEX_JSP);
        } else {
            request.addAttributeToJsp(ERROR_LOGIN_PASS_MESSAGE_NAME, ERROR_LOGIN_PASS_MASSAGE_ATTRIBUTE);
            return requestFactory.createForwardResponse(PATH_LOGIN_JSP);
        }
    }

    public static LoginCommand getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final LoginCommand INSTANCE = new LoginCommand(AccountService.getInstance());
    }
}
