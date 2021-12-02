package com.epam.jwd.library.command;

import com.epam.jwd.library.controller.RequestFactory;
import com.epam.jwd.library.model.Account;
import com.epam.jwd.library.service.AccountService;

import java.util.Optional;

public class ShowAccountPageCommand implements Command{

    private static final String REQUEST_ATTRIBUTE_NAME = "account";
    private static final String PATH_ACCOUNT_NAME = "/WEB-INF/jsp/account.jsp";

    private final AccountService accountService;
    private final RequestFactory requestFactory = RequestFactory.getInstance();

    private ShowAccountPageCommand(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        final Long id = Long.valueOf(request.getParameter("id"));
        final Optional<Account> account = accountService.findById(id);
        if (account.isPresent()) {
            request.addAttributeToJsp(REQUEST_ATTRIBUTE_NAME, account.get());
            return requestFactory.createForwardResponse(PATH_ACCOUNT_NAME);
        } else {
            request.addAttributeToJsp("errorPassMassage", "Could not find your account!");
            return requestFactory.createForwardResponse("/WEB-INF/jsp/error.jsp");
        }
    }


    public static ShowAccountPageCommand getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final ShowAccountPageCommand INSTANCE = new ShowAccountPageCommand(AccountService.getInstance());
    }
}
