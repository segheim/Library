package com.epam.jwd.library.command;

import com.epam.jwd.library.controller.RequestFactory;
import com.epam.jwd.library.model.BookOrder;
import com.epam.jwd.library.service.BookOrderService;

import java.util.List;

public class EndBookOrderCommand implements Command {

    private final BookOrderService bookOrderService;
    private final RequestFactory requestFactory = RequestFactory.getInstance();

    private EndBookOrderCommand(BookOrderService bookOrderService) {
        this.bookOrderService = bookOrderService;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        final Long idBookOrder = Long.valueOf(request.getParameter("id"));
        if (bookOrderService.changeStatusBookOrderOnEnded(idBookOrder)) {
            final List<BookOrder> bookOrders = bookOrderService.findAllUncompleted();
            request.addAttributeToJsp("bookOrders", bookOrders);
            return requestFactory.createRedirectResponse("controller?command=librarian_book_order_page");
        }
        request.addAttributeToJsp("errorPassMassage", "Could not change order status on ended");
        return requestFactory.createForwardResponse("/WEB-INF/jsp/error.jsp");
    }

    public static EndBookOrderCommand getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final EndBookOrderCommand INSTANCE = new EndBookOrderCommand(BookOrderService.getInstance());
    }
}
