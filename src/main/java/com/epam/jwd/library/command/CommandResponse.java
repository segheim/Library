package com.epam.jwd.library.command;

public interface CommandResponse {

    boolean isRedirect();

    String getPath();

}
