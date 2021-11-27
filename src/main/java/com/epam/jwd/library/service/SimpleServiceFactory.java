package com.epam.jwd.library.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleServiceFactory implements ServiceFactory{

    private final Map<ServiceType, Service> serviceCash;

    private SimpleServiceFactory() {
        this.serviceCash = new ConcurrentHashMap<>();
    }

    @Override
    public Service createService(ServiceType serviceType) {
        return serviceCash.computeIfAbsent(serviceType, s -> fetchService(s));
    }

    public Service fetchService(ServiceType serviceType) {

        Service service;

        switch (serviceType) {
//            case ACCOUNT_SERVICE:
//                service = UserService.getInstance();
//                break;
            case BOOK_SERVICE:
                service = BookService.getInstance();
                break;
            case AUTHOR_SERVICE:
                service = AuthorService.getInstance();
                break;
            default:
                throw new IllegalArgumentException("wrong service type");
        }
        return service;
    }


    public static SimpleServiceFactory getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final SimpleServiceFactory INSTANCE = new SimpleServiceFactory();
    }
}
