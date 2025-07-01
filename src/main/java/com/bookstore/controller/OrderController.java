package com.bookstore.controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;

import com.bookstore.dto.order.OrderCreateDto;
import com.bookstore.entity.Book;
import com.bookstore.entity.Order;
import com.bookstore.entity.OrderItem;

@Path("/order")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderController {

    @GET
    public List<Order> getOrders() {
        return Order.listAll();
    }

    @POST
    @Transactional
    public Response create(OrderCreateDto dto) {
        if (dto.customerId == null || dto.bookIds == null || dto.bookIds.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Customer ID and at least one Book ID must be provided")
                    .build();
        }
        Order order = new Order();
        order.customer = com.bookstore.entity.Customer.findById(dto.customerId);
        if (order.customer == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Customer not found for the provided customerId")
                    .build();
        }
        order.orderDate = LocalDate.now();
        order.orderStatus = 0; // Default status: Pending
        order.shippingAddress = dto.shippingAddress;

        for (Long bookId : dto.bookIds) {
            Book book = Book.findById(bookId);
            if (book != null) {
                OrderItem orderItem = new OrderItem();
                orderItem.order = order;
                orderItem.book = book;
                orderItem.price = book.price - (book.price * book.discount / 100);
                orderItem.quantity = 1;
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Book not found for the provided bookId: " + bookId)
                        .build();
            }
        }
        order.persist();

        return Response.status(Response.Status.CREATED).entity(order).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        Order order = Order.findById(id);
        if (order == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        OrderItem.delete("order.id", id);

        order.delete();

        return Response.noContent().build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Long id) {
        Order order = Order.findById(id);
        if (order == null)
            return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(order).build();
    }
}