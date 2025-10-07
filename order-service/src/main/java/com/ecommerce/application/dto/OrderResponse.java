package com.ecommerce.application.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
    String orderId,
    String customerId,
    String restaurantId,
    List<OrderItemResponse> items,
    String status,
    AddressResponse deliveryAddress,
    BigDecimal subtotal,
    BigDecimal deliveryFee,
    BigDecimal tax,
    BigDecimal total,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    String deliveryInstructions
) {
    public record OrderItemResponse(
        String productId,
        String productName,
        int quantity,
        BigDecimal unitPrice,
        BigDecimal subtotal,
        String specialInstructions
    ) {}
    
    public record AddressResponse(
        String street,
        String city,
        String postalCode,
        String country
    ) {}
}
