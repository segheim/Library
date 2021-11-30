package com.epam.jwd.library.command;

import com.epam.jwd.library.controller.RequestFactory;
import com.epam.jwd.library.model.Account;
import com.epam.jwd.library.model.BookOrder;
import com.epam.jwd.library.model.Role;
import com.epam.jwd.library.service.BookOrderService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ShowBookOrderPageCommand implements Command{

    private final BookOrderService bookOrderService;
    private final RequestFactory requestFactory = RequestFactory.getInstance();

    public ShowBookOrderPageCommand(BookOrderService bookOrderService) {
        this.bookOrderService = bookOrderService;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        final Optional<Object> accountSession = request.takeFromSession("account");
        final Account account = (Account)accountSession.get();
        final Role role = account.getRole();
        List<BookOrder> bookOrders = new ArrayList<>();
        if (Role.READER.equals(role)) {
            bookOrders = bookOrderService.findByIdAccount(account.getId());
        } else {
            bookOrders = bookOrderService.findAll();
        }
        request.addAttributeToJsp("bookOrders", bookOrders);
        return requestFactory.createForwardResponse("/WEB-INF/jsp/bookorder.jsp");
    }

    public static ShowBookOrderPageCommand getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final ShowBookOrderPageCommand INSTANCE = new ShowBookOrderPageCommand(BookOrderService.getInstance());
    }
}
