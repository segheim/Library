package com.epam.jwd.library.command;

import com.epam.jwd.library.controller.RequestFactory;
import com.epam.jwd.library.model.Account;
import com.epam.jwd.library.service.AccountService;

import java.util.List;

public class DeleteAccountCommand implements Command {

    private final AccountService accountService;
    private final RequestFactory requestFactory = RequestFactory.getInstance();

    private DeleteAccountCommand(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        final Long idAccount = Long.valueOf(request.getParameter("id"));
        if (accountService.delete(idAccount)) {
            return requestFactory.createRedirectResponse("controller?command=accounts_page");
        } else {
            request.addAttributeToJsp("errorPassMassage", "Could not delete account");
            return requestFactory.createForwardResponse("/WEB-INF/jsp/error.jsp");
        }
    }

    public static DeleteAccountCommand getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final DeleteAccountCommand INSTANCE = new DeleteAccountCommand(AccountService.getInstance());
    }
}
