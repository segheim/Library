package com.epam.jwd.library.model;

public class AccountDetails implements Entity{

    private final Long id;
    private final String firstName;
    private final String lastName;

    public AccountDetails(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public AccountDetails(String firstName, String lastName) {
        this(null, firstName, lastName);
    }

    @Override
    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
