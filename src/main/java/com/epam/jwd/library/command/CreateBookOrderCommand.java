package com.epam.jwd.library.command;

import com.epam.jwd.library.controller.RequestFactory;
import com.epam.jwd.library.dao.BookOrderDao;

public class CreateBookOrderCommand implements Command{

    private final BookOrderDao bookOrderDao;
    private final RequestFactory requestFactory = RequestFactory.getInstance();

    public CreateBookOrderCommand(BookOrderDao bookOrderDao) {
        this.bookOrderDao = bookOrderDao;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        return null;
    }

    public static CreateBookOrderCommand getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final CreateBookOrderCommand INSTANCE = new CreateBookOrderCommand(BookOrderDao.getInstance());
    }
}
