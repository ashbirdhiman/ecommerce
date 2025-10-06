package com.ecommerce.Application.dto;

import com.ecommerce.domain.valueObject.Address;
import com.ecommerce.domain.valueObject.PaymentMethod;
import com.ecommerce.domain.valueObject.userID;

import java.util.UUID;

public record RegisterUserCommand(

    UUID id,
    String name,
    String email,
    String password,
    PaymentMethod paymentMethod,
    Address address
) {}