package com.ecommerce.infrastructure.messaging;

import java.time.LocalDateTime;

public record OrderNotificationEventMessage(
    String orderId,
    String customerId,
    String customerEmail,
    String customerName,
    String restaurantName,
    String notificationType,
    String message,
    LocalDateTime timestamp
) {}