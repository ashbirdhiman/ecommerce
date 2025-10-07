package com.ecommerce.infrastructure.mapper;

import com.ecommerce.domain.aggregate.Order;
import com.ecommerce.domain.valueobject.*;
import com.ecommerce.infrastructure.entity.OrderEntity;
import com.ecommerce.infrastructure.entity.OrderStatusEntity;

import java.util.List;

public class OrderMapper {

    public static OrderEntity toEntity(Order order) {
        OrderEntity entity = new OrderEntity(
            order.getOrderId().value(),
            order.getCustomerId().value(),
            order.getRestaurantId().value(),
            order.getDeliveryAddress().street(),
            order.getDeliveryAddress().city(),
            order.getDeliveryAddress().postalCode(),
            order.getDeliveryAddress().postalCode(),
            order.getDeliveryAddress().country()
        );

        entity.setStatus(OrderStatusEntity.valueOf(order.getStatus().name()));
        entity.setSubtotal(order.getSubtotal().amount());
        entity.setDeliveryFee(order.getDeliveryFee().amount());
        entity.setTax(order.getTax().amount());
        entity.setTotal(order.getTotal().amount());
        entity.setCurrency(order.getTotal().currency());
        entity.setCreatedAt(order.getCreatedAt());
        entity.setUpdatedAt(order.getUpdatedAt());
        entity.setDeliveryInstructions(order.getDeliveryInstructions());
        entity.setCancellationReason(order.getCancellationReason());

        return entity;
    }

    public static Order toDomain(OrderEntity entity) {
        var orderId = new OrderId(entity.getOrderId());
        var customerId = new CustomerId(entity.getCustomerId());
        var restaurantId = new RestaurantId(entity.getRestaurantId());
        
        var deliveryAddress = new Address(
            entity.getStreet(),
            entity.getCity(),
            entity.getPostalCode(),
            entity.getCountry(),
            null, // latitude not stored in entity
            null  // longitude not stored in entity
        );
        
        // Create a simple order with minimal items for now (since items are commented out in entity)
        // In a complete implementation, you'd need to fetch OrderItemEntity from repository
        var order = new Order.Builder()
            .orderId(orderId)
            .customerId(customerId)
            .restaurantId(restaurantId)
            .items(List.of()) // Empty items for now since OneToMany is commented out
            .deliveryAddress(deliveryAddress)
            .deliveryInstructions(entity.getDeliveryInstructions())
            .build();
        
        // Note: Since Order has a private constructor and calculates totals automatically,
        // we can't directly set the stored values from the entity. 
        // The domain object will recalculate based on items.
        // In a complete implementation, you'd need to either:
        // 1. Add a way to restore calculated fields, or
        // 2. Always recalculate from items
        
        return order;
    }
}