package com.epam.jwd.library.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthorValidator {

    private AuthorValidator() {
    }

    public static AuthorValidator getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final AuthorValidator INSTANCE = new AuthorValidator();
    }

    public boolean validate(String firstName, String lastName) {
        if (firstName == null || lastName == null) {
            return false;
        }
        Pattern pattern = Pattern.compile("");
        Matcher matcherFirstName = pattern.matcher(firstName);
        Matcher matcherLastName = pattern.matcher(lastName);
        return matcherFirstName.matches() && matcherLastName.matches();
    }
}
