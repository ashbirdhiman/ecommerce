package com.ecommerce.domain.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrderCreatedEvent(
    UUID orderId,
    UUID customerId,
    UUID restaurantId,
    List<OrderItemEvent> items,
    AddressEvent deliveryAddress,
    BigDecimal total,
    String currency,
    LocalDateTime createdAt
) {
    public record OrderItemEvent(
        String productId,
        String productName,
        int quantity,
        BigDecimal unitPrice,
        BigDecimal subtotal
    ) {}
    
    public record AddressEvent(
        String street,
        String city,
        String postalCode,
        String country
    ) {}
}