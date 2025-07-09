package com.bookstore.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "customer")
public class Customer extends PanacheEntity {
    public String name;
    public String email;
    public String password;
    public String phone;
    public java.time.LocalDate birthDate;
    public String address;
    public int membershipLevel; // Silver, Gold, Platinum
    public String photoUrl;

    @jakarta.annotation.Nullable
    public String token;

    public int type = 2; // 1: Publisher, 2: Customer

    @OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    public List<Order> orders;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    public List<ShoppingCart> shoppingCarts;
}