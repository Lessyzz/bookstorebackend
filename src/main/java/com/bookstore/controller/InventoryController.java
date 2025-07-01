// package com.bookstore.controller;

// import jakarta.ws.rs.*;
// import jakarta.ws.rs.core.MediaType;
// import jakarta.ws.rs.core.Response;
// import jakarta.transaction.Transactional;
// import java.util.List;

// import com.bookstore.dto.inventory.InventoryCreateDto;
// import com.bookstore.dto.inventory.InventoryUpdateDto;
// import com.bookstore.entity.Book;
// import com.bookstore.entity.Inventory;

// @Path("/inventory")
// @Produces(MediaType.APPLICATION_JSON)
// @Consumes(MediaType.APPLICATION_JSON)
// public class InventoryController {

//     @GET
//     public List<Inventory> getAll() {
//         return Inventory.listAll();
//     }

//     @POST
//     @Transactional
//     public Response create(InventoryCreateDto dto) {
//         if (dto.bookId == null || dto.stockQuantity <= 0) {
//             return Response.status(Response.Status.BAD_REQUEST)
//                     .entity("Book ID and stock quantity must be provided")
//                     .build();
//         }

//         Book book = com.bookstore.entity.Book.findById(dto.bookId);
//         if (book == null) {
//             return Response.status(Response.Status.BAD_REQUEST)
//                     .entity("Book not found for the provided bookId")
//                     .build();
//         }
//         Inventory inventory = new Inventory();
//         inventory.book = book;
//         inventory.stockQuantity = dto.stockQuantity;
//         inventory.format = dto.format;
//         inventory.lastUpdated = java.time.LocalDate.now();
//         inventory.persist();

//         return Response.status(Response.Status.CREATED).entity(inventory).build();
//     }

//     @PUT
//     @Transactional
//     public Response update(InventoryUpdateDto dto) {
//         Inventory existingInventory = Inventory.findById(dto.id);
//         if (existingInventory == null) {
//             return Response.status(Response.Status.NOT_FOUND).build();
//         }

//         if (dto.bookId != null) {
//             Book book = Book.findById(dto.bookId);
//             if (book == null) {
//                 return Response.status(Response.Status.BAD_REQUEST)
//                         .entity("Book not found for the provided bookId")
//                         .build();
//             }

//             existingInventory.book = book;
//             return Response.ok(existingInventory).build();
//         } else {
//             return Response.status(Response.Status.BAD_REQUEST)
//                     .entity("Book ID must be provided")
//                     .build();
//         }
//     }

//     @DELETE
//     @Path("/{id}")
//     @Transactional
//     public Response delete(@PathParam("id") Long id) {
//         Inventory inventory = Inventory.findById(id);
//         if (inventory == null)
//             return Response.status(Response.Status.NOT_FOUND).build();
//         inventory.delete();
//         return Response.noContent().build();
//     }

//     @GET
//     @Path("/{id}")
//     public Response getById(@PathParam("id") Long id) {
//         Inventory inventory = Inventory.findById(id);
//         if (inventory == null)
//             return Response.status(Response.Status.NOT_FOUND).build();
//         return Response.ok(inventory).build();
//     }
// }


// IF INVENTORY CONTROLLER IS NEEDED, RECODE IT.
// 