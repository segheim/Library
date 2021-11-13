package com.epam.jwd.library.controller;

import com.epam.jwd.library.command.CommandRequest;

import javax.servlet.http.HttpServletRequest;

public class WrappingCommandRequest implements CommandRequest {

    private final HttpServletRequest httpServletRequest;

    public WrappingCommandRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public void addAttributeToJsp(String name, Object attribute) {
        httpServletRequest.setAttribute(name, attribute);
    }
}
