package com.bookstore.controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.transaction.Transactional;
import java.util.List;

import com.bookstore.dto.genre.GenreCreateDto;
import com.bookstore.dto.genre.GenreUpdateDto;
import com.bookstore.entity.Book;
import com.bookstore.entity.Genre;

@Path("/genre")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GenreController {

    @GET
    public List<Genre> getAll() {
        return Genre.listAll();
    }

    @POST
    @Transactional
    public Response create(GenreCreateDto genreCreateDto) {
        if (genreCreateDto.name == null || genreCreateDto.name.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Genre name cannot be empty")
                    .build();
        }

        Genre genre = new Genre();
        genre.name = genreCreateDto.name;
        genre.persist();
        return Response.status(Response.Status.CREATED).entity(genre).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") Long id, GenreUpdateDto genreUpdateDto) {
        Genre genre = Genre.findById(id);
        if (genre == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        genre.name = genreUpdateDto.name;
        return Response.ok(genre).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        Genre genre = Genre.findById(id);
        if (genre == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        Book.delete("genre.id", id);

        genre.delete();
        return Response.noContent().build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        Genre genre = Genre.findById(id);
        if (genre == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(genre).build();
    }
}