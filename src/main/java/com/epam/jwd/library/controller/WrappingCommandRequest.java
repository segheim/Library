package com.epam.jwd.library.controller;

import com.epam.jwd.library.command.CommandRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

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

    @Override
    public void createSession() {
        httpServletRequest.getSession();
    }

    @Override
    public boolean addAttributeToSession(String name, Object attribute) {
        final HttpSession session = httpServletRequest.getSession(false);
        if (session != null) {
            session.setAttribute(name, attribute);
            return true;
        }
        return false;
    }

    @Override
    public void clearSession() {
        final HttpSession session = httpServletRequest.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

    @Override
    public Optional<Object> takeFromSession(String name) {
        final HttpSession session = httpServletRequest.getSession(false);
        final Optional<HttpSession> sessionOptional = Optional.ofNullable(session);
        return sessionOptional.map(s -> s.getAttribute(name));
    }

    @Override
    public boolean sessionExist() {
        return httpServletRequest.getSession(false) != null;
    }
}
