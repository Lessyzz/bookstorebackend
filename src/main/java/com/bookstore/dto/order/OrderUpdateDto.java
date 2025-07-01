package com.bookstore.dto.order;

import java.util.List;

public class OrderUpdateDto {
    public String orderStatus;
    public String shippingAddress;
    public List<Long> bookIds;
}
