package com.epam.jwd.library.command;

import com.epam.jwd.library.controller.RequestFactory;
import com.epam.jwd.library.model.Author;
import com.epam.jwd.library.service.AuthorService;

import java.util.List;

public class ShowAuthorsPageCommand implements Command{

    private final AuthorService authorService;
    private final RequestFactory requestFactory = RequestFactory.getInstance();


    private ShowAuthorsPageCommand(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        final List<Author> authors = authorService.findAll();
        request.addAttributeToJsp("authors", authors);
        return requestFactory.createResponse("/WEB-INF/jsp/author.jsp");
    }

    public static ShowAuthorsPageCommand getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final ShowAuthorsPageCommand INSTANCE = new ShowAuthorsPageCommand(AuthorService.getInstance());
    }

//    public static ShowAuthorsPageCommand getInstance() {
//        if (instance == null){
//            locker.lock();
//            try {
//                if (instance == null) {
//                    instance = new ShowAuthorsPageCommand(authorService);
//                }
//            } finally {
//                locker.unlock();
//            }
//        }
//        return instance;
//    }
}
