package com.epam.jwd.library.command;

import com.epam.jwd.library.controller.RequestFactory;
import com.epam.jwd.library.model.Account;
import com.epam.jwd.library.service.AccountService;

import java.util.List;

public class ShowAccountsPageCommand implements Command {

    private static final String REQUEST_ATTRIBUTE_NAME = "accounts";
    private static final String PATH_ACCOUNT_NAME = "/WEB-INF/jsp/accounts.jsp";

    private final AccountService accountService;
    private final RequestFactory requestFactory = RequestFactory.getInstance();

    private ShowAccountsPageCommand(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        final List<Account> accounts = accountService.findAll();
        if (!accounts.isEmpty()) {
            request.addAttributeToJsp(REQUEST_ATTRIBUTE_NAME, accounts);
            return requestFactory.createForwardResponse(PATH_ACCOUNT_NAME);
        } else {
            request.addAttributeToJsp("errorPassMassage", "Could not find accounts!");
            return requestFactory.createForwardResponse("/WEB-INF/jsp/error.jsp");
        }
    }


    public static ShowAccountsPageCommand getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final ShowAccountsPageCommand INSTANCE = new ShowAccountsPageCommand(AccountService.getInstance());
    }
}
