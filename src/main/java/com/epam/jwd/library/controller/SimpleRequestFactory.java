package com.epam.jwd.library.controller;

import com.epam.jwd.library.command.CommandRequest;
import com.epam.jwd.library.command.CommandResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleRequestFactory implements RequestFactory{

    private final Map<String, CommandResponse> responseCash;

    private SimpleRequestFactory() {
        this.responseCash = new ConcurrentHashMap<>();
    }

    @Override
    public CommandRequest createRequest(HttpServletRequest httpServletRequest) {
        return new WrappingCommandRequest(httpServletRequest);
    }

    @Override
    public CommandResponse createResponse(String path) {
        return responseCash.computeIfAbsent(path, p -> new PlainCommandResponse(p));
    }

    public static SimpleRequestFactory getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final SimpleRequestFactory INSTANCE = new SimpleRequestFactory();
    }
}
