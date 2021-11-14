package com.epam.jwd.library.controller;

import com.epam.jwd.library.command.CommandRequest;
import com.epam.jwd.library.command.CommandResponse;

import javax.servlet.http.HttpServletRequest;

public interface RequestFactory {

    CommandRequest createRequest(HttpServletRequest httpServletRequest);

    CommandResponse createResponse(String path);

    static RequestFactory getInstance() {
        return SimpleRequestFactory.getInstance();
    }

}
