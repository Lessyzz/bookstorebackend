package com.bookstore.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "publisher")
public class Publisher extends PanacheEntity {
    public String name;
    public String address;
    public String phone;
    public String email;
    public String password; 
    public String website;
    public int type = 1; // 1: Publisher, 2: Customer


    @jakarta.annotation.Nullable
    public String token;

    @OneToMany(mappedBy = "publisher", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    public List<Book> books;
}