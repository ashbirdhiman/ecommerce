package com.ecommerce.Infrastructure.dto;


import java.util.UUID;

public record UserRegisteredEvent(UUID userId, String email) {}