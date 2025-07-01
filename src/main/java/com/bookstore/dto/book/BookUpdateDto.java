package com.bookstore.dto.book;

import java.time.LocalDate;

public class BookUpdateDto {
    public Long id;
    public String title;
    public String description;
    public String format;

    public long authorId;
    public long publisherId;
    public long genreId;

    public String isbn;
    public LocalDate publicationDate;
    public double price;
    public int stockQuantity;

    public String photoUrl;
    public int pageCount;
}
