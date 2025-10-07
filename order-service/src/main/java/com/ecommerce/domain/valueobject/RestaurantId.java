package com.ecommerce.domain.valueobject;

import java.util.Objects;
import java.util.UUID;

public record RestaurantId(UUID value) {
    public RestaurantId {
        Objects.requireNonNull(value, "RestaurantId cannot be null");
    }
    
    public static RestaurantId of(String value) {
        return new RestaurantId(UUID.fromString(value));
    }
    
    @Override
    public String toString() {
        return value.toString();
    }
}
