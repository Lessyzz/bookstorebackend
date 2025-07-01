package com.bookstore.controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.transaction.Transactional;
import java.util.List;

import com.bookstore.dto.author.AuthorCreateDto;
import com.bookstore.dto.author.AuthorUpdateDto;
import com.bookstore.entity.Author;
import com.bookstore.entity.Book;

@Path("/author")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthorController {

    @GET
    public List<Author> getAuthors() {
        return Author.listAll();
    }

    @POST
    @Transactional
    public Response create(AuthorCreateDto dto) {
        if (dto.name == null || dto.name.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Author name cannot be empty")
                    .build();
        }
        Author author = new Author();
        author.name = dto.name;
        author.persist();
        return Response.status(Response.Status.CREATED).entity(author).build();
    }

    @PUT
    @Transactional
    public Response update(AuthorUpdateDto dto) {
        Author existingAuthor = Author.findById(dto.id);
        if (existingAuthor == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        existingAuthor.name = dto.name;
        return Response.ok(existingAuthor).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        Author author = Author.findById(id);
        if (author == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        Book.delete("author.id", id);
        author.delete();
        return Response.noContent().build();
    }

    @GET
    @Path("/{id}")
    public Response getAuthorById(@PathParam("id") Long id) {
        Author author = Author.findById(id);
        if (author == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(author).build();
    }
}