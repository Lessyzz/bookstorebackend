package com.bookstore.dto.order;

import java.time.LocalDate;
import java.util.List;

public class OrderCreateDto {
    public LocalDate orderDate;
    public Long customerId;
    public List<Long> bookIds;
    public String shippingAddress;
    public int paymentMethod;
}