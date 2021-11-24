package com.epam.jwd.library.model;

import java.util.Objects;

public class Author implements Entity{

    private Long id;
    private String firstName;
    private String lastName;

    public Author(Long id, String firstName, String lastName ) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Author(String first_name, String last_name) {
        this(null, first_name, last_name);
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

    public void setFirst_name(String first_name) {
        this.firstName = first_name;
    }

    public void setLast_name(String last_name) {
        this.lastName = last_name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(id, author.id)
                && Objects.equals(firstName, author.firstName)
                && Objects.equals(lastName, author.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName);
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", first_name='" + firstName + '\'' +
                ", last_name='" + lastName + '\'' +
                '}';
    }
}
