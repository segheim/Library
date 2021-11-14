package com.epam.jwd.library.service;

public interface ServiceFactory {

    Service createService(ServiceType type);

    static ServiceFactory getInstance() {
        return SimpleServiceFactory.getInstance();
    }
}
