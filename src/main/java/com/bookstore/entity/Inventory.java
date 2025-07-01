package com.bookstore.entity;

import java.time.LocalDate;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.smallrye.common.constraint.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "inventory")
public class Inventory extends PanacheEntity {
    @ManyToOne
    public Book book;

    @Nullable
    public String format;  // Hardcover, Paperback, Audiobook vs.

    public int stockQuantity;
    public LocalDate lastUpdated;
}