package com.epam.jwd.library.command;

import com.epam.jwd.library.controller.RequestFactory;
import com.epam.jwd.library.service.BookOrderService;

public class ShowBookOrderPageCommand implements Command{

    private final BookOrderService bookOrderService;
    private final RequestFactory requestFactory = RequestFactory.getInstance();

    public ShowBookOrderPageCommand(BookOrderService bookOrderService) {
        this.bookOrderService = bookOrderService;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {

//        bookOrderService.findById();
        return null;
    }

    public static ShowBookOrderPageCommand getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final ShowBookOrderPageCommand INSTANCE = new ShowBookOrderPageCommand(BookOrderService.getInstance());
    }
}
