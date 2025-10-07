package com.ecommerce.application.mapper;

import com.ecommerce.application.dto.OrderResponse;
import com.ecommerce.domain.aggregate.Order;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OrderMapper {
    
    public OrderResponse toOrderResponse(Order order) {
        var items = order.getItems().stream()
            .map(item -> new OrderResponse.OrderItemResponse(
                item.getProductId(),
                item.getProductName(),
                item.getQuantity(),
                item.getUnitPrice().amount(),
                item.getSubtotal().amount(),
                item.getSpecialInstructions()
            ))
            .toList();
        
        var address = new OrderResponse.AddressResponse(
            order.getDeliveryAddress().street(),
            order.getDeliveryAddress().city(),
            order.getDeliveryAddress().postalCode(),
            order.getDeliveryAddress().country()
        );
        
        return new OrderResponse(
            order.getOrderId().toString(),
            order.getCustomerId().value().toString(),
            order.getRestaurantId().value().toString(),
            items,
            order.getStatus().name(),
            address,
            order.getSubtotal().amount(),
            order.getDeliveryFee().amount(),
            order.getTax().amount(),
            order.getTotal().amount(),
            order.getCreatedAt(),
            order.getUpdatedAt(),
            order.getDeliveryInstructions()
        );
    }
}
