package com.bookstore.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "book")
public class Book extends PanacheEntity {
    public String title;
    public String description;
    public String format;  // Hardcover, Paperback, eBook, Audiobook

    @ManyToOne
    public Author author;

    @ManyToOne
    public Publisher publisher;

    @ManyToOne
    public Genre genre;

    public String isbn;
    public java.time.LocalDate publicationDate;
    public double price;
    public int stockQuantity;
    public int pageCount;
    public String photoUrl;
    public int discount;
}