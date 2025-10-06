package com.ecommerce.Application.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderCreatedEvent(
    String orderId,
    String customerId,
    String restaurantId,
    List<OrderItemEvent> items,
    DeliveryAddressEvent deliveryAddress,
    String deliveryInstructions,
    LocalDateTime createdAt
) {
    public record OrderItemEvent(
        String productId,
        String productName,
        int quantity,
        BigDecimal unitPrice,
        String specialInstructions
    ) {}
    
    public record DeliveryAddressEvent(
        String street,
        String city,
        String postalCode,
        String country,
        Double latitude,
        Double longitude
    ) {}
}