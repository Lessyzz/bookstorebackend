package com.bookstore.controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.transaction.Transactional;
import java.util.List;

import com.bookstore.dto.customer.CustomerCreateDto;
import com.bookstore.dto.customer.CustomerUpdateDto;
import com.bookstore.entity.Customer;
import com.bookstore.entity.Order;
import com.bookstore.entity.ShoppingCart;

@Path("/customer")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerController {

    @GET
    public List<Customer> getCustomers() {
        return Customer.listAll();
    }

    @POST
    @Transactional
    public Response create(CustomerCreateDto dto) {
        if (dto.name == null || dto.name.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Customer name cannot be empty")
                    .build();
        }
        if (dto.email == null || dto.email.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Customer email cannot be empty")
                    .build();
        }

        Customer customer = new Customer();
        if (dto.name != null)
            customer.name = dto.name;
        if (dto.email != null)
            customer.email = dto.email;
        if (dto.password != null)
            customer.password = dto.password;
        if (dto.phone != null)
            customer.phone = dto.phone;
        if (dto.birthDate != null)
            customer.birthDate = dto.birthDate;
        if (dto.address != null)
            customer.address = dto.address;
        if (dto.membershipLevel != 0)
            customer.membershipLevel = dto.membershipLevel;
        if (dto.photoUrl != null)
            customer.photoUrl = dto.photoUrl;

        customer.persist();

        ShoppingCart cart = new ShoppingCart();
        cart.customer = customer;
        cart.persist();

        return Response.status(Response.Status.CREATED).entity(customer).build();
    }

    @PUT
    @Transactional
    public Response update(CustomerUpdateDto dto) {
        Customer existingCustomer = Customer.findById(dto.id);
        if (existingCustomer == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if (dto.name != null)
            existingCustomer.name = dto.name;
        if (dto.email != null)
            existingCustomer.email = dto.email;
        if (dto.password != null)
            existingCustomer.password = dto.password;
        if (dto.phone != null)
            existingCustomer.phone = dto.phone;
        if (dto.birthDate != null)
            existingCustomer.birthDate = dto.birthDate;
        if (dto.address != null)
            existingCustomer.address = dto.address;
        if (dto.membershipLevel != 0)
            existingCustomer.membershipLevel = dto.membershipLevel;
        if (dto.photoUrl != null)
            existingCustomer.photoUrl = dto.photoUrl;

        return Response.ok(existingCustomer).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        Customer customer = Customer.findById(id);
        if (customer == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        Order.delete("customer.id", id);
        ShoppingCart.delete("customer.id", id);

        customer.delete();
        return Response.noContent().build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        Customer customer = Customer.findById(id);
        if (customer == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(customer).build();
    }
}