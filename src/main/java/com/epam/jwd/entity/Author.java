package com.epam.jwd.entity;

import java.util.Objects;

public class Author implements Entity{

    private final Long id;
    private final String first_name;
    private final String last_name;

    public Author(Long id, String first_name, String last_name) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public Author(String first_name, String last_name) {
        this(null, first_name, last_name);
    }

    public Long getId() {
        return id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(id, author.id) && Objects.equals(first_name, author.first_name) && Objects.equals(last_name, author.last_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, first_name, last_name);
    }

    @Override
    public String toString() {
        return "Author{" + "id=" + id + " " + first_name + " " + last_name + '}';
    }
}
