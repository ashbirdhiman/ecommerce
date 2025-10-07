package com.ecommerce.domain.valueobject;

import java.util.Objects;
import java.util.UUID;

public record CustomerId(UUID value) {
    public CustomerId {
        Objects.requireNonNull(value, "CustomerId cannot be null");
    }
    
    public static CustomerId of(String value) {
        return new CustomerId(UUID.fromString(value));
    }
    
    @Override
    public String toString() {
        return value.toString();
    }
}
