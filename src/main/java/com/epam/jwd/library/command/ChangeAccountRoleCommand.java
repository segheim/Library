package com.epam.jwd.library.command;

import com.epam.jwd.library.controller.RequestFactory;
import com.epam.jwd.library.service.AccountService;

import static java.lang.String.format;

public class ChangeAccountRoleCommand implements Command{

    private final AccountService accountService;
    private final RequestFactory requestFactory = RequestFactory.getInstance();

    public ChangeAccountRoleCommand(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        final Long accountId = Long.valueOf(request.getParameter("id"));
        final String roleName = request.getParameter("role");
        if (accountService.changeRole(accountId, roleName)) {
            return requestFactory.createRedirectResponse(format("controller?command=account_page&id=%s", accountId));
        } else {
            request.addAttributeToJsp("errorPassMassage", "Could not change role in account");
            return requestFactory.createForwardResponse("/WEB-INF/jsp/error.jsp");
        }
    }

    public static ChangeAccountRoleCommand getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final ChangeAccountRoleCommand INSTANCE = new ChangeAccountRoleCommand(AccountService.getInstance());
    }
}
