package com.bookstore.controller;

import java.util.UUID;

import com.bookstore.entity.Token;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Path("/token")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TokenController {
    @POST
    @Transactional
    public static String addToken(long id) 
    {   
        Token token = new Token();
        token.token = UUID.randomUUID().toString() + ":" + id;
        token.expirationDate = java.time.LocalDateTime.now().plusDays(7);
        token.persist();
        return token.token;
    }

    @jakarta.ws.rs.GET
    @Path("/validate/{token}")
    @Transactional
    public boolean validateToken(@jakarta.ws.rs.PathParam("token") String token) {
        Token existingToken = Token.find("token", token).firstResult();
        if (existingToken != null) {
            if (existingToken.expirationDate.isAfter(java.time.LocalDateTime.now())) {
                return true;
            } else {
                existingToken.delete();
                return false; // Token expired
            }
        }
        return false; // Token not found
    }
}
