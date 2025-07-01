package com.bookstore.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "order_item")
public class OrderItem extends PanacheEntity {
    @ManyToOne
    public Order order;

    @ManyToOne
    public Book book;

    public int quantity;
    public double discount;
    public double price;

    public double getPrice() {
        return quantity * book.price;
    }
}