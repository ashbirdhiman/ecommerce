package com.ecommerce.domain.event;

import java.util.UUID;

public record UserRegisteredEvent(UUID userId, String email) {}