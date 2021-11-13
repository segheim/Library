package com.epam.jwd.library.command;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Show404ErrorPageCommand implements Command{


    private static Show404ErrorPageCommand instance;
    private static Lock locker = new ReentrantLock();

    private Show404ErrorPageCommand() {
    }

    private static final CommandResponse FORWARD_TO_404_ERROR_PAGE_RESPONSE = new CommandResponse() {
        @Override
        public boolean isRedirect() {
            return false;
        }

        @Override
        public String getPath() {
            return "/WEB-INF/jsp/404.jsp";
        }
    };

    @Override
    public CommandResponse execute(CommandRequest request) {
        return FORWARD_TO_404_ERROR_PAGE_RESPONSE;
    }

    public static Show404ErrorPageCommand getInstance() {
        if (instance == null){
            locker.lock();
            try {
                if (instance == null) {
                    instance = new Show404ErrorPageCommand();
                }
            } finally {
                locker.unlock();
            }
        }
        return instance;
    }
}
