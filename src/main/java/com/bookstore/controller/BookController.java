package com.bookstore.controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.transaction.Transactional;
import java.util.List;

import com.bookstore.dto.book.BookCreateDto;
import com.bookstore.dto.book.BookUpdateDto;
import com.bookstore.entity.Author;
import com.bookstore.entity.Book;
import com.bookstore.entity.Genre;
import com.bookstore.entity.Publisher;

@Path("/book")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookController {

    @GET
    public List<Book> getBooks() {
        return Book.listAll();
    }

    @POST
    @Transactional
    public Response create(BookCreateDto dto) {
        Book book = new Book();
        book.title = dto.title;
        book.description = dto.description;
        book.format = dto.format;
        book.author = Author.findById(dto.authorId);
        book.publisher = Publisher.findById(dto.publisherId);
        book.genre = Genre.findById(dto.genreId);
        book.isbn = dto.isbn;
        book.publicationDate = dto.publicationDate;
        book.price = dto.price;
        book.stockQuantity = dto.stockQuantity;
        book.pageCount = dto.pageCount;
        book.photoUrl = dto.photoUrl;

        book.persist();
        return Response.status(Response.Status.CREATED).entity(book).build();
    }

    @PUT
    @Transactional
    public Response update(BookUpdateDto dto) {
        Book existingBook = Book.findById(dto.id);

        if (existingBook == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if (dto.title != null)
            existingBook.title = dto.title;
        if (dto.description != null)
            existingBook.description = dto.description;
        if (dto.format != null)
            existingBook.format = dto.format;
        if (dto.authorId != 0)
            existingBook.author = Author.findById(dto.authorId);
        if (dto.publisherId != 0)
            existingBook.publisher = Publisher.findById(dto.publisherId);
        if (dto.genreId != 0)
            existingBook.genre = Genre.findById(dto.genreId);
        if (dto.isbn != null)
            existingBook.isbn = dto.isbn;
        if (dto.publicationDate != null)
            existingBook.publicationDate = dto.publicationDate;
        if (dto.price != 0)
            existingBook.price = dto.price;
        if (dto.stockQuantity != 0)
            existingBook.stockQuantity = dto.stockQuantity;
        if (dto.pageCount != 0)
            existingBook.pageCount = dto.pageCount;
        if (dto.photoUrl != null)
            existingBook.photoUrl = dto.photoUrl;

        return Response.ok(existingBook).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        Book book = Book.findById(id);
        if (book == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        book.delete();
        return Response.noContent().build();
    }

    @GET
    @Path("/{id}")
    public Book getBookById(@PathParam("id") Long id) {
        return Book.findById(id);
    }
}