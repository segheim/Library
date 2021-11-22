package com.epam.jwd.library.command;

public class ShowUpdateBookPageCommand implements Command{

    @Override
    public CommandResponse execute(CommandRequest request) {
        return null;
    }

    public static ShowUpdateBookPageCommand getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final ShowUpdateBookPageCommand INSTANCE = new ShowUpdateBookPageCommand();
    }
}
