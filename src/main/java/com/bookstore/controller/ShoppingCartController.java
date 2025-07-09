package com.bookstore.controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.transaction.Transactional;

import com.bookstore.dto.shoppingcard.ShoppingCartBuyDto;
import com.bookstore.dto.shoppingcard.ShoppingCartItemCreateDto;
import com.bookstore.entity.Book;
import com.bookstore.entity.Order;
import com.bookstore.entity.OrderItem;
import com.bookstore.entity.ShoppingCart;
import com.bookstore.entity.ShoppingCartItem;

@Path("/shoppingcart")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class ShoppingCartController {
    @PUT
    @Transactional
    public Response update(ShoppingCartItemCreateDto dto) {
        ShoppingCart cart = ShoppingCart.findById(dto.cartId);
        if (cart == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if (dto.quantity <= 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Quantity must be greater than zero")
                    .build();
        }

        ShoppingCartItem existingItem = cart.items.stream()
                .filter(item -> item.book.id.equals(dto.bookId))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            existingItem.quantity = dto.quantity;
            existingItem.persist();
            return Response.ok(existingItem).build();
        } else {
            ShoppingCartItem cartItem = new ShoppingCartItem();
            cartItem.cart = cart;
            cartItem.book = Book.findById(dto.bookId);
            cartItem.quantity = dto.quantity;
            cartItem.persist();
            return Response.ok(cartItem).build();
        }
    }

    @PUT
    @Path("/update-one")
    @Transactional
    public Response updateOne(ShoppingCartItemCreateDto dto) {
        ShoppingCart cart = ShoppingCart.findById(dto.cartId);
        if (cart == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        ShoppingCartItem existingItem = cart.items.stream()
                .filter(item -> item.book.id.equals(dto.bookId))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            existingItem.quantity += dto.quantity;
            if (existingItem.quantity <= 0)
            {
                ShoppingCartItem.delete("id", existingItem.id);
            }
            existingItem.persist();
            return Response.ok(existingItem).build();
        } else {
            ShoppingCartItem cartItem = new ShoppingCartItem();
            cartItem.cart = cart;
            cartItem.book = Book.findById(dto.bookId);
            cartItem.quantity = 1;
            cartItem.persist();
            return Response.ok(cartItem).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        ShoppingCart cartItem = ShoppingCart.findById(id);
        if (cartItem == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        ShoppingCartItem.delete("cart.id", id);

        cartItem.delete();

        return Response.noContent().build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        ShoppingCart cartItem = ShoppingCart.findById(id);
        if (cartItem == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(cartItem.items).build();
    }

    @GET
    @Path("/total-quantity/{id}")
    public Response getTotalQuantity(@QueryParam("id") Long cartId) {
        ShoppingCart cart = ShoppingCart.findById(cartId);
        if (cart == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        int totalQuantity = cart.items.stream()
                .mapToInt(item -> item.quantity)
                .sum();

        return Response.ok(totalQuantity).build();
    }

    @POST
    @Path("/buy")
    @Transactional
    public Response buy(ShoppingCartBuyDto dto) {
        ShoppingCart cart = ShoppingCart.findById(dto.cartId);
        if (cart == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if (cart.items.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Shopping cart is empty")
                    .build();
        }

        Order order = new Order();
        order.orderItems = new java.util.ArrayList<>();
        order.customer = cart.customer;
        order.orderDate = java.time.LocalDate.now();
        order.orderStatus = 0; // Default status: Pending
        order.shippingAddress = cart.customer.address;
        order.paymentMethod = 1;

        order.totalPrice = cart.items.stream()
                .mapToDouble(item -> item.book.price * item.quantity)
                .sum();

        order.persist();

        for (ShoppingCartItem item : cart.items) {
            Book book = Book.findById(item.book.id);
            if (book == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Book not found for the provided bookId: " + item.book.id)
                        .build();
            }
            OrderItem orderItem = new OrderItem();
            orderItem.order = order;
            orderItem.book = book;
            orderItem.price = book.price * item.quantity;
            orderItem.quantity = item.quantity;


            order.orderItems.add(orderItem);
        }

        order.persist();

        cart.items.clear();
        cart.persist();

        return Response.ok("Purchase successful").build();
    }
}