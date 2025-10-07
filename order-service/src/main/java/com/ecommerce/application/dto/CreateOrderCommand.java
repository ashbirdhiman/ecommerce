package com.ecommerce.application.dto;

import io.smallrye.common.constraint.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CreateOrderCommand(
    @NotNull String customerId,
    @NotNull String restaurantId,
    @NotNull @Size(min = 1) List<OrderItemDTO> items,
    @NotNull AddressDTO deliveryAddress,
    String deliveryInstructions
) {
    public record OrderItemDTO(
        @NotNull String productId,
        @NotNull String productName,
        int quantity,
        @NotNull java.math.BigDecimal unitPrice,
        String specialInstructions
    ) {}
    
    public record AddressDTO(
        @NotNull String street,
        @NotNull String city,
        @NotNull String postalCode,
        @NotNull String country,
        Double latitude,
        Double longitude
    ) {}
}
