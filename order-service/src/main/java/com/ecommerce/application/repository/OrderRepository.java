package com.ecommerce.application.repository;

import com.ecommerce.domain.aggregate.Order;
import com.ecommerce.domain.valueobject.CustomerId;
import com.ecommerce.domain.valueobject.OrderId;
import com.ecommerce.domain.valueobject.OrderStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository {

    Order save(Order order);
    Optional<Order> findById(OrderId orderId);
    List<Order> findByCustomerId(CustomerId customerId);
    List<Order> findByStatus(OrderStatus status);
    void delete(OrderId orderId);
}
