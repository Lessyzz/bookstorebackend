package com.bookstore.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class Order extends PanacheEntity {
    public java.time.LocalDate orderDate;

    @ManyToOne
    public Customer customer;

    public double totalPrice;
    public int orderStatus; // 0: Pending, 1: Shipped, 2: Delivered, 3: Cancelled
    public String shippingAddress;
    public String paymentMethod;

    @OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    public List<OrderItem> orderItems;
}
