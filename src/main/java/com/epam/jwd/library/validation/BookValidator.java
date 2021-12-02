package com.epam.jwd.library.validation;

import java.sql.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BookValidator {

    private BookValidator() {
    }

    public static BookValidator getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static final BookValidator INSTANCE = new BookValidator();
    }

    public boolean validate(String title, Integer amountOfLeft) {
        if (title == null || amountOfLeft == null) {
            return false;
        }

        Pattern patternTitle = Pattern.compile(".{0,100}");
        Matcher matcherTitle = patternTitle.matcher(title);

        Pattern patternNumber = Pattern.compile("^\\d*$");
        Matcher matcherAmountOfLeft = patternNumber.matcher(amountOfLeft.toString());

        return matcherTitle.matches() && matcherAmountOfLeft.matches();
    }
}
