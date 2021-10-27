package com.epam.jwd.library.exception;

public class AuthorNotCreateException extends EntityNotFoundException{

    public AuthorNotCreateException(String message) {
        super(message);
    }
}
