package com.epam.jwd.library.entity;

import java.util.Date;
import java.util.Objects;

public class Book implements Entity{

    private final Long id;
    private final String title;
    private final Date date_published;
    private final Integer amount_of_left;
    private final Author author;

    public Book(Long id, String title, Date date_published, Integer amount_of_left, Author author) {
        this.id = id;
        this.title = title;
        this.date_published = date_published;
        this.amount_of_left = amount_of_left;
        this.author = author;
    }

    public Book(String title, Date date_published, Integer amount_of_left) {
        this(null, title, date_published, amount_of_left, null);
    }

    public Book(Long id, String title, Date date_published, Integer amount_of_left) {
        this(id, title, date_published, amount_of_left, null);
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Date getDate_published() {
        return date_published;
    }

    public Integer getAmount_of_left() {
        return amount_of_left;
    }

    public Author getAuthor() {
        return author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id) && Objects.equals(title, book.title) && Objects.equals(date_published, book.date_published) && Objects.equals(amount_of_left, book.amount_of_left) && Objects.equals(author, book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, date_published, amount_of_left, author);
    }
}
