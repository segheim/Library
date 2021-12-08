package com.epam.jwd.library.command;

import com.epam.jwd.library.controller.RequestFactory;
import com.epam.jwd.library.model.BookOrder;
import com.epam.jwd.library.service.BookOrderService;

import java.util.List;

public class ShowLibrarianBookOrderPageCommand implements Command{

    private final BookOrderService bookOrderService;
    private final RequestFactory requestFactory = RequestFactory.getInstance();

    private ShowLibrarianBookOrderPageCommand(BookOrderService bookOrderService) {
        this.bookOrderService = bookOrderService;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        final List<BookOrder> bookOrders = bookOrderService.findAllUncompleted();
        if (!bookOrders.isEmpty()) {
            request.addAttributeToJsp("bookOrders", bookOrders);
            return requestFactory.createForwardResponse("/WEB-INF/jsp/librarianBookOrder.jsp");
        } else {
            request.addAttributeToJsp("errorPassMassage", "There is no uncompleted orders!");
            return requestFactory.createForwardResponse("/WEB-INF/jsp/error.jsp");
        }
    }

    public static ShowLibrarianBookOrderPageCommand getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final ShowLibrarianBookOrderPageCommand INSTANCE = new ShowLibrarianBookOrderPageCommand(BookOrderService.getInstance());
    }
}
