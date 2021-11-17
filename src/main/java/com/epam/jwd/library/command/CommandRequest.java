package com.epam.jwd.library.command;

import javax.servlet.http.HttpSession;

public interface CommandRequest {

    String getParameter(String name);

    void addAttributeToJsp(String name, Object attribute);

    HttpSession createSession();


}
