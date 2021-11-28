package com.epam.jwd.library.command;

import com.epam.jwd.library.controller.RequestFactory;
import com.epam.jwd.library.dao.BookDao;
import com.epam.jwd.library.dao.BookOrderDao;
import com.epam.jwd.library.model.Account;
import com.epam.jwd.library.model.Book;
import com.epam.jwd.library.model.BookOrder;
import com.epam.jwd.library.model.OrderType;
import com.epam.jwd.library.service.BookOrderService;
import com.epam.jwd.library.service.BookService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class CreateBookOrderCommand implements Command{

    private static final Logger LOG = LogManager.getLogger(CreateBookOrderCommand.class);

    private static final String ERROR_PASS_MASSAGE = "errorBookOrderMassage";
    private static final String ERROR_CREATE_BOOK_ORDER_ATTRIBUTE = "Where your account? oO";
    private static final String PATH_ERROR_JSP = "/WEB-INF/jsp/error.jsp";

    private final BookOrderService bookOrderService;
    private final BookService bookService;
    private final RequestFactory requestFactory = RequestFactory.getInstance();

    public CreateBookOrderCommand(BookOrderService bookOrderService, BookService bookService) {
        this.bookOrderService = bookOrderService;
        this.bookService = bookService;
    }


    @Override
    public CommandResponse execute(CommandRequest request) {
        final Optional<Object> accountSession = request.takeFromSession("account");
        final Long idBook = Long.valueOf(request.getParameter("book_id"));
        final Optional<Book> readBook = bookService.findById(idBook);
        final String[] orderTypes = request.getParameterValues("order_type");
        if (!accountSession.isPresent() && !readBook.isPresent() && (orderTypes.length > 1)) {
            request.addAttributeToJsp(ERROR_PASS_MASSAGE, ERROR_CREATE_BOOK_ORDER_ATTRIBUTE);
            return requestFactory.createForwardResponse(PATH_ERROR_JSP);
        }
        final Account account = (Account)accountSession.get();
        final Book book = readBook.get();
        final String orderType = orderTypes[0];
        if (bookOrderService.createBookOrder(account, book, orderType).isPresent()) {
            final List<BookOrder> bookOrders = bookOrderService.findAll();
            request.addAttributeToJsp("bookOrders", bookOrders);
            return requestFactory.createForwardResponse("/WEB-INF/jsp/readerbookorder.jsp");
        } else {
            request.addAttributeToJsp("errorCreateBookOrderPassMessage", "Please, check checkbox");
            return requestFactory.createForwardResponse("/WEB-INF/jsp/createbookorder.jsp");
        }
    }

    public static CreateBookOrderCommand getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final CreateBookOrderCommand INSTANCE = new CreateBookOrderCommand(BookOrderService.getInstance(),
                BookService.getInstance());
    }
}
