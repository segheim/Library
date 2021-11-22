package com.epam.jwd.library.command;

public class UpdateAuthorCommand implements Command{

    @Override
    public CommandResponse execute(CommandRequest request) {
        return null;
    }

    public static UpdateAuthorCommand getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final UpdateAuthorCommand INSTANCE = new UpdateAuthorCommand();
    }
}
