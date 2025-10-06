package com.ecommerce.Presentation.dto;

import java.math.BigDecimal;

public class ItemDto {
    private String productId;
    private String productName;
    private int quantity;
    private BigDecimal unitPrice;
    private String specialInstructions;

    // Constructors
    public ItemDto() {}
    
    public ItemDto(String productId, String productName, int quantity, BigDecimal unitPrice, String specialInstructions) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.specialInstructions = specialInstructions;
    }

    // Getters and Setters
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }
    
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    
    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
    
    public String getSpecialInstructions() { return specialInstructions; }
    public void setSpecialInstructions(String specialInstructions) { this.specialInstructions = specialInstructions; }
}