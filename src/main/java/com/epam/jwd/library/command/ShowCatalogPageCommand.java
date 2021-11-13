package com.epam.jwd.library.command;

import com.epam.jwd.library.connection.ConnectionPool;
import com.epam.jwd.library.dao.AuthorDao;
import com.epam.jwd.library.dao.BookDao;
import com.epam.jwd.library.model.Book;
import com.epam.jwd.library.service.BookService;

import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ShowCatalogPageCommand implements Command{

    private static ShowCatalogPageCommand instance;
    private static Lock locker = new ReentrantLock();
    private static BookService bookService;



    private static final CommandResponse FORWARD_TO_BOOKS_PAGE_RESPONSE = new CommandResponse() {
        @Override
        public boolean isRedirect() {
            return false;
        }

        @Override
        public String getPath() {
            return "/WEB-INF/jsp/catalog.jsp";
        }
    };

    private ShowCatalogPageCommand(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        final List<Book> books = bookService.findAll();
        request.addAttributeToJsp("books", books);
        return FORWARD_TO_BOOKS_PAGE_RESPONSE;
    }

    public static ShowCatalogPageCommand getInstance() {
        if (instance == null){
            locker.lock();
            try {
                if (instance == null) {
                    instance = new ShowCatalogPageCommand(BookService.getInstance());
                }
            } finally {
                locker.unlock();
            }
        }
        return instance;
    }
}
