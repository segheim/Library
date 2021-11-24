package com.epam.jwd.library.model;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Book implements Entity{

    private Long id;
    private String title;
    private Date datePublished;
    private Integer amountOfLeft;
    private List<Author> authors;

    public Book(Long id, String title, Date datePublished, Integer amountOfLeft, List<Author> authors) {
        this.id = id;
        this.title = title;
        this.datePublished = datePublished;
        this.amountOfLeft = amountOfLeft;
        this.authors = authors;
    }

//    public Book(String title, Date date_published, Integer amount_of_left) {
//        this(null, title, date_published, amount_of_left);
//    }

    public Book(String title, Date datePublished, Integer amountOfLeft) {
        this(null, title, datePublished, amountOfLeft);
    }

    public Book(Long id, String title, Date datePublished, Integer amountOfLeft) {
        this(id, title, datePublished, amountOfLeft, null);
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Date getDatePublished() {
        return datePublished;
    }

    public Integer getAmountOfLeft() {
        return amountOfLeft;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public Book getBookWithAuthors(List<Author> authors) {
        return new Book(id, title, datePublished, amountOfLeft, authors);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id) && Objects.equals(title, book.title)
                && Objects.equals(datePublished, book.datePublished)
                && Objects.equals(amountOfLeft, book.amountOfLeft)
                && Objects.equals(authors, book.authors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, datePublished, amountOfLeft, authors);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", date_published=" + datePublished +
                ", amount_of_left=" + amountOfLeft +
                ", authors=" + authors +
                '}';
    }
}
