package com.ecommerce.infrastructure.messaging;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderCreatedEventMessage(
    String orderId,
    String customerId,
    String restaurantId,
    List<OrderItemEventMessage> items,
    DeliveryAddressEventMessage deliveryAddress,
    String deliveryInstructions,
    LocalDateTime createdAt
) {
    public record OrderItemEventMessage(
        String productId,
        String productName,
        int quantity,
        BigDecimal unitPrice,
        String specialInstructions
    ) {}
    
    public record DeliveryAddressEventMessage(
        String street,
        String city,
        String postalCode,
        String country,
        Double latitude,
        Double longitude
    ) {}
}