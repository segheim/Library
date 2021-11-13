package com.epam.jwd.library.command;

import com.epam.jwd.library.connection.ConnectionPool;
import com.epam.jwd.library.dao.BookDao;
import com.epam.jwd.library.model.Author;
import com.epam.jwd.library.service.AuthorService;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ShowAuthorsPageCommand implements Command{

    private static ShowAuthorsPageCommand instance;
    private static Lock locker = new ReentrantLock();

    private static AuthorService authorService;

    public static final CommandResponse FORWARD_TO_AUTHOR_PAGE_RESPONSE = new CommandResponse() {
        @Override
        public boolean isRedirect() {
            return false;
        }

        @Override
        public String getPath() {
            return "/WEB-INF/jsp/author.jsp";
        }
    };

    private ShowAuthorsPageCommand(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        final List<Author> authors = authorService.findAll();
        request.addAttributeToJsp("authors", authors);
        return FORWARD_TO_AUTHOR_PAGE_RESPONSE;
    }
    public static ShowAuthorsPageCommand getInstance() {
        if (instance == null){
            locker.lock();
            try {
                if (instance == null) {
                    instance = new ShowAuthorsPageCommand(authorService);
                }
            } finally {
                locker.unlock();
            }
        }
        return instance;
    }
}
