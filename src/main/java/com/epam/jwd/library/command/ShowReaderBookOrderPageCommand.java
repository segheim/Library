package com.epam.jwd.library.command;

import com.epam.jwd.library.controller.RequestFactory;
import com.epam.jwd.library.model.Account;
import com.epam.jwd.library.model.BookOrder;
import com.epam.jwd.library.model.Role;
import com.epam.jwd.library.service.BookOrderService;

import java.util.List;
import java.util.Optional;

public class ShowReaderBookOrderPageCommand implements Command{

    private final BookOrderService bookOrderService;
    private final RequestFactory requestFactory = RequestFactory.getInstance();

    public ShowReaderBookOrderPageCommand(BookOrderService bookOrderService) {
        this.bookOrderService = bookOrderService;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        final Optional<Object> accountSession = request.takeFromSession("account");
        final Account account = (Account)accountSession.get();
        final List<BookOrder> bookOrders = bookOrderService.findOrdersByIdAccount(account.getId());
        if (!bookOrders.isEmpty()) {
            request.addAttributeToJsp("bookOrders", bookOrders);
            return requestFactory.createForwardResponse("/WEB-INF/jsp/readerbookorder.jsp");
        } else {
            request.addAttributeToJsp("errorPassMassage", "You have not order!");
            return requestFactory.createForwardResponse("/WEB-INF/jsp/error.jsp");
        }
    }

    public static ShowReaderBookOrderPageCommand getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final ShowReaderBookOrderPageCommand INSTANCE = new ShowReaderBookOrderPageCommand(BookOrderService.getInstance());
    }
}
