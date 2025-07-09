package com.bookstore.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "token")
public class Token extends PanacheEntity {
    public String token;
    public java.time.LocalDateTime expirationDate;
}