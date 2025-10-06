package com.ecommerce.Application.dto;

import java.time.LocalDateTime;

public record OrderNotificationEvent(
    String orderId,
    String customerId,
    String customerEmail,
    String customerName,
    String restaurantName,
    String notificationType, // "ORDER_CREATED", "ORDER_CONFIRMED", etc.
    String message,
    LocalDateTime timestamp
) {}