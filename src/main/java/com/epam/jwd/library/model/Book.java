package com.epam.jwd.library.model;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Book implements Entity{

    private Long id;
    private String title;
    private Date date_published;
    private Integer amount_of_left;
    private List<Author> authors;

    public Book(Long id, String title, Date date_published, Integer amount_of_left, List<Author> authors) {
        this.id = id;
        this.title = title;
        this.date_published = date_published;
        this.amount_of_left = amount_of_left;
        this.authors = authors;
    }

//    public Book(String title, Date date_published, Integer amount_of_left) {
//        this(null, title, date_published, amount_of_left);
//    }

    public Book(String title, Date date_published, Integer amount_of_left) {
        this(null, title, date_published, amount_of_left);
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

    public List<Author> getAuthors() {
        return authors;
    }

    public Book getBookWithAuthors(List<Author> authors) {
        return new Book(id, title, date_published, amount_of_left, authors);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id) && Objects.equals(title, book.title) && Objects.equals(date_published, book.date_published) && Objects.equals(amount_of_left, book.amount_of_left) && Objects.equals(authors, book.authors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, date_published, amount_of_left, authors);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", date_published=" + date_published +
                ", amount_of_left=" + amount_of_left +
                ", authors=" + authors +
                '}';
    }
}
