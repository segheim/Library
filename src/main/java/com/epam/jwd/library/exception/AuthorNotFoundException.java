package com.epam.jwd.library.exception;

public class AuthorNotFoundException extends EntityNotFoundException {

    public AuthorNotFoundException(String message) {
        super(message);
    }
}
