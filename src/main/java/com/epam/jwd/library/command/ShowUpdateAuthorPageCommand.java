package com.epam.jwd.library.command;

public class ShowUpdateAuthorPageCommand implements Command{

    @Override
    public CommandResponse execute(CommandRequest request) {
        return null;
    }

    public static ShowUpdateAuthorPageCommand getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final ShowUpdateAuthorPageCommand INSTANCE = new ShowUpdateAuthorPageCommand();
    }
}
