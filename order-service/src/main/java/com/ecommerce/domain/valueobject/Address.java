package com.ecommerce.domain.valueobject;

import java.util.Objects;

public record Address(
    String street,
    String city,
    String postalCode,
    String country,
    Double latitude,
    Double longitude
) {
    public Address {
        Objects.requireNonNull(street, "Street cannot be null");
        Objects.requireNonNull(city, "City cannot be null");
        Objects.requireNonNull(postalCode, "Postal code cannot be null");
        Objects.requireNonNull(country, "Country cannot be null");
    }
}
