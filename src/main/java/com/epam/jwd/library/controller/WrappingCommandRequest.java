package com.epam.jwd.library.controller;

import com.epam.jwd.library.command.CommandRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class WrappingCommandRequest implements CommandRequest {

    private static final Logger LOG = LogManager.getLogger(WrappingCommandRequest.class);

    private final HttpServletRequest httpServletRequest;

    public WrappingCommandRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public void addAttributeToJsp(String name, Object attribute) {
        httpServletRequest.setAttribute(name, attribute);
    }

    public String getParameter(String name) {
        final String parameter = httpServletRequest.getParameter(name);
        LOG.info("get Wrapping Parameter = {}", parameter);
        return parameter;
    }

    public HttpSession createSession() {
        return httpServletRequest.getSession();
    }
}
