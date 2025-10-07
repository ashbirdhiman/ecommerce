package com.ecommerce.application.service;

import com.ecommerce.domain.aggregate.Order;
import com.ecommerce.domain.valueobject.OrderStatus;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OrderDomainService {
    
    public void validateOrder(Order order) {
        if (order.getItems().isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one item");
        }
        
        if (order.getTotal().amount().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Order total must be greater than zero");
        }
    }
    
    public boolean canCancelOrder(Order order) {
        return order.getStatus() == OrderStatus.PENDING ||
               order.getStatus() == OrderStatus.CONFIRMED;
    }
}
