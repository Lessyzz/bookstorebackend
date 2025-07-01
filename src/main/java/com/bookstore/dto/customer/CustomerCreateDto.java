package com.bookstore.dto.customer;

import java.time.LocalDate;

public class CustomerCreateDto {
    public String name;
    public String email;
    public String password;
    public String phone;
    public LocalDate birthDate;
    public String address;

    public String photoUrl;
    public int membershipLevel;
}