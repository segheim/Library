package com.epam.jwd.library.command;

import com.epam.jwd.library.controller.RequestFactory;
import com.epam.jwd.library.model.Account;
import com.epam.jwd.library.model.Role;
import com.epam.jwd.library.service.BookOrderService;

import java.util.Optional;

public class DeleteBookOrderCommand implements Command {

    private final BookOrderService bookOrderService;
    private final RequestFactory requestFactory = RequestFactory.getInstance();

    private DeleteBookOrderCommand(BookOrderService bookOrderService) {
        this.bookOrderService = bookOrderService;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        final Long idBookOrder = Long.valueOf(request.getParameter("id"));
        final Optional<Object> accountSession = request.takeFromSession("account");
        final Account account = (Account) accountSession.get();
        final Role role = account.getRole();
        if (bookOrderService.delete(idBookOrder)) {
            if (Role.READER.equals(role)) {
                return requestFactory.createRedirectResponse("controller?command=reader_book_order_page");
            } else {
                return requestFactory.createRedirectResponse("controller?command=librarian_book_order_page");
            }
        }
        request.addAttributeToJsp("errorPassMassage", "Could not delete book order");
        return requestFactory.createForwardResponse("/WEB-INF/jsp/error.jsp");
    }

    public static DeleteBookOrderCommand getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final DeleteBookOrderCommand INSTANCE = new DeleteBookOrderCommand(BookOrderService.getInstance());
    }
}
