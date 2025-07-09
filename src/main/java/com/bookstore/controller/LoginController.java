package com.bookstore.controller;

import com.bookstore.dto.user.UserLoginDto;
import com.bookstore.entity.Customer;
import com.bookstore.entity.Publisher;
import jakarta.ws.rs.core.MediaType;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

@Path("/login")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LoginController {

    @POST
    public Response login(UserLoginDto userLoginDto) {
        if (userLoginDto.email == null || userLoginDto.password == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Email and password must be provided")
                    .build();
        }
        Publisher publisher = Publisher.find("email", userLoginDto.email).firstResult();
        Customer customer = Customer.find("email", userLoginDto.email).firstResult();
        if (publisher != null && publisher.password.equals(userLoginDto.password)) {
            String token = TokenController.addToken(publisher.id);
            publisher.token = token;
            return Response.ok(publisher).build();
        } else if (customer != null && customer.password.equals(userLoginDto.password)) {
            String token = TokenController.addToken(customer.id);
            customer.token = token;
            return Response.ok(customer).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED)
                .entity("Invalid email or password")
                .build();
        }
    }
}