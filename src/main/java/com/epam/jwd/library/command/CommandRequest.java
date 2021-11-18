package com.epam.jwd.library.command;

import javax.servlet.http.HttpSession;
import java.util.Optional;

public interface CommandRequest {

    String getParameter(String name);

    void addAttributeToJsp(String name, Object attribute);

    void createSession();

    boolean addAttributeToSession(String name, Object attribute);

    void clearSession();

    Optional<Object> takeFromSession(String name);

    boolean sessionExist();

}
