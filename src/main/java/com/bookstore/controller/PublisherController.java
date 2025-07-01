package com.bookstore.controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.transaction.Transactional;
import java.util.List;

import com.bookstore.dto.publisher.PublisherCreateDto;
import com.bookstore.dto.publisher.PublisherUpdateDto;
import com.bookstore.entity.Book;
import com.bookstore.entity.Publisher;

@Path("/publisher")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PublisherController {

    @GET
    public List<Publisher> getPublishers() {
        return Publisher.listAll();
    }

    @POST
    @Transactional
    public Response create(PublisherCreateDto dto) {
        if (dto.name.trim() == null || dto.name.trim().isEmpty() ||
                dto.address == null || dto.address.trim().isEmpty() ||
                dto.phone == null || dto.phone.trim().isEmpty() ||
                dto.email == null || dto.email.trim().isEmpty() ||
                dto.website == null || dto.website.trim().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Publisher name cannot be empty")
                    .build();
        }

        Publisher publisher = new Publisher();
        publisher.name = dto.name;
        publisher.address = dto.address;
        publisher.phone = dto.phone;
        publisher.email = dto.email;
        publisher.website = dto.website;

        publisher.persist();
        return Response.status(Response.Status.CREATED).entity(publisher).build();
    }

    @PUT
    @Transactional
    public Response update(PublisherUpdateDto dto) {
        Publisher publisher = Publisher.findById(dto.id);
        if (publisher == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if (dto.name != null && !dto.name.trim().isEmpty())
            publisher.name = dto.name;
        if (dto.address != null && !dto.address.trim().isEmpty())
            publisher.address = dto.address;
        if (dto.phone != null && !dto.phone.trim().isEmpty())
            publisher.phone = dto.phone;
        if (dto.email != null && !dto.email.trim().isEmpty())
            publisher.email = dto.email;
        if (dto.website != null && !dto.website.trim().isEmpty())
            publisher.website = dto.website;
        publisher.persist();
        return Response.ok(publisher).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        Publisher publisher = Publisher.findById(id);
        if (publisher == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        Book.delete("publisher.id", id);

        publisher.delete();

        return Response.noContent().build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        Publisher publisher = Publisher.findById(id);
        if (publisher == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(publisher).build();
    }
}