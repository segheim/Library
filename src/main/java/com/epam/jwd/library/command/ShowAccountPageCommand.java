package com.epam.jwd.library.command;

import com.epam.jwd.library.controller.RequestFactory;
import com.epam.jwd.library.model.Account;
import com.epam.jwd.library.service.AccountService;

import java.util.List;

public class ShowAccountPageCommand implements Command{

    private static final String REQUEST_ATTRIBUTE_NAME = "accounts";
    private static final String PATH_ACCOUNT_NAME = "/WEB-INF/jsp/accounts.jsp";

    private final AccountService accountService;
    private final RequestFactory requestFactory = RequestFactory.getInstance();

    public ShowAccountPageCommand(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        final List<Account> accounts = accountService.findAll();
        request.addAttributeToJsp(REQUEST_ATTRIBUTE_NAME, accounts);
        return requestFactory.createForwardResponse(PATH_ACCOUNT_NAME);
    }

    public static ShowAccountPageCommand getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final ShowAccountPageCommand INSTANCE = new ShowAccountPageCommand(AccountService.getInstance());
    }
}
