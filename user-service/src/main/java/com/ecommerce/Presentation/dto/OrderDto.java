package com.ecommerce.Presentation.dto;

import java.util.List;

public class OrderDto {
    private String customerId;
    private String restaurantId;
    private List<ItemDto> items;
    private DeliveryAddressDto deliveryAddress;
    private String deliveryInstructions;

    // Constructors
    public OrderDto() {}
    
    public OrderDto(String customerId, String restaurantId, List<ItemDto> items, 
                   DeliveryAddressDto deliveryAddress, String deliveryInstructions) {
        this.customerId = customerId;
        this.restaurantId = restaurantId;
        this.items = items;
        this.deliveryAddress = deliveryAddress;
        this.deliveryInstructions = deliveryInstructions;
    }

    // Getters and Setters
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    
    public String getRestaurantId() { return restaurantId; }
    public void setRestaurantId(String restaurantId) { this.restaurantId = restaurantId; }
    
    public List<ItemDto> getItems() { return items; }
    public void setItems(List<ItemDto> items) { this.items = items; }
    
    public DeliveryAddressDto getDeliveryAddress() { return deliveryAddress; }
    public void setDeliveryAddress(DeliveryAddressDto deliveryAddress) { this.deliveryAddress = deliveryAddress; }
    
    public String getDeliveryInstructions() { return deliveryInstructions; }
    public void setDeliveryInstructions(String deliveryInstructions) { this.deliveryInstructions = deliveryInstructions; }
}