package com.epam.jwd.library.command;

import com.epam.jwd.library.controller.RequestFactory;
import com.epam.jwd.library.model.Account;
import com.epam.jwd.library.service.AccountService;

import javax.servlet.http.HttpSession;
import java.util.Optional;

public class LoginCommand implements Command {

    private final AccountService accountService = AccountService.getInstance();
    private final RequestFactory requestFactory = RequestFactory.getInstance();

    @Override
    public CommandResponse execute(CommandRequest request) {

        final String login = request.getParameter("login");
        final String password = request.getParameter("password");
        final Optional<Account> account = accountService.authenticate(login, password);

        if (account.isPresent()) {
            final HttpSession session = request.createSession();
            session.setAttribute("account", account.get());
            return requestFactory.createRedirectResponse("index.jsp");
        } else {
            request.addAttributeToJsp("errorLoginPassMessage", "Incorrect login or password, try again");
            return requestFactory.createForwardResponse("/WEB-INF/jsp/login.jsp");
        }
    }

    public static LoginCommand getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final LoginCommand INSTANCE = new LoginCommand();
    }
}
