package com.bookstore.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "shopping_cart_item")
public class ShoppingCartItem extends PanacheEntity {
    @ManyToOne
    public ShoppingCart cart;

    @ManyToOne
    public Book book;

    public int quantity;
}