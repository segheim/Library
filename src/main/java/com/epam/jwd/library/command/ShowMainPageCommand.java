package com.epam.jwd.library.command;


import com.epam.jwd.library.connection.LockingConnectionPool;
import sun.plugin.ClassLoaderInfo;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ShowMainPageCommand implements Command {

    private static ShowMainPageCommand instance;
    private static Lock locker = new ReentrantLock();

    private ShowMainPageCommand() {
    }

    private static final CommandResponse FORWARD_TO_MAIN_PAGE_RESPONSE = new CommandResponse() {
        @Override
        public boolean isRedirect() {
            return false;
        }

        @Override
        public String getPath() {
            return "/WEB-INF/jsp/main.jsp";
        }
    };

    @Override
    public CommandResponse execute(CommandRequest request) {
        return FORWARD_TO_MAIN_PAGE_RESPONSE;
    }

    public static ShowMainPageCommand getInstance() {
        if (instance == null){
            locker.lock();
            try {
                if (instance == null) {
                    instance = new ShowMainPageCommand();
                }
            } finally {
                locker.unlock();
            }
        }
        return instance;
    }
}
