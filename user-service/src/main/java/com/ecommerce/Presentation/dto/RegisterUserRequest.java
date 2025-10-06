package com.ecommerce.Presentation.dto;

import com.ecommerce.domain.valueObject.Address;
import com.ecommerce.domain.valueObject.PaymentMethod;

public record RegisterUserRequest(


    String name,
    String email,
    String password,
    PaymentMethod paymentMethod,
    Address address
) {}