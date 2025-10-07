package com.ecommerce.domain.event;

import java.time.LocalDateTime;
import java.util.UUID;

public record OrderConfirmedEvent(
    UUID orderId,
    UUID customerId,
    UUID restaurantId,
    LocalDateTime confirmedAt
) {}