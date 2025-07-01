package com.bookstore.dto.book;

import java.time.LocalDate;

public class BookCreateDto {
    public String title;
    public String description;
    public String format;

    public long authorId;
    public long publisherId;
    public long genreId;

    public String isbn;
    public LocalDate publicationDate; // ISO-8601 format (e.g., "2023-10-01")
    public double price;
    public int stockQuantity;
    
    public String photoUrl;
    public int pageCount;
}