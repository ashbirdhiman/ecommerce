package com.ecommerce.domain.model;

import com.ecommerce.domain.valueobject.Money;
import java.util.UUID;

public class OrderItem {
    private final UUID id;
    private final String productId;
    private final String productName;
    private final int quantity;
    private final Money unitPrice;
    private final Money subtotal;
    private final String specialInstructions;

    public OrderItem(String productId, String productName, int quantity,
                     Money unitPrice, String specialInstructions) {
        this.id = UUID.randomUUID();
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.subtotal = unitPrice.multiply(quantity);
        this.specialInstructions = specialInstructions;
    }

    public UUID getId() { return id; }
    public String getProductId() { return productId; }
    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }
    public Money getUnitPrice() { return unitPrice; }
    public Money getSubtotal() { return subtotal; }
    public String getSpecialInstructions() { return specialInstructions; }
}
