package com.ecommerce.application.service;

import com.ecommerce.application.dto.CreateOrderCommand;
import com.ecommerce.application.dto.OrderResponse;
import com.ecommerce.application.mapper.OrderMapper;
import com.ecommerce.application.repository.OrderRepository;
import com.ecommerce.domain.aggregate.Order;
import com.ecommerce.domain.model.OrderItem;
import com.ecommerce.domain.valueobject.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class OrderApplicationService {
    
    @Inject
    OrderRepository orderRepository;
    
    @Inject
    OrderDomainService orderDomainService;
    
//    @Inject
//    OrderEventPublisher eventPublisher;
//
    @Inject
    OrderMapper orderMapper;

    @Transactional
    public OrderResponse createOrder(CreateOrderCommand command) {
        var customerId = new CustomerId(UUID.fromString(command.customerId()));
        var restaurantId = new RestaurantId(UUID.fromString(command.restaurantId()));
        
        var deliveryAddress = new Address(
            command.deliveryAddress().street(),
            command.deliveryAddress().city(),
            command.deliveryAddress().postalCode(),
            command.deliveryAddress().country(),
            command.deliveryAddress().latitude(),
            command.deliveryAddress().longitude()
        );
        
        var orderItems = command.items().stream()
            .map(item -> new OrderItem(
                item.productId(),
                item.productName(),
                item.quantity(),
                Money.of(item.unitPrice(), "CAD"),
                item.specialInstructions()
            ))
            .collect(Collectors.toList());
        
        var order = new Order.Builder()
            .customerId(customerId)
            .restaurantId(restaurantId)
            .deliveryAddress(deliveryAddress)
            .items(orderItems)
            .deliveryInstructions(command.deliveryInstructions())
            .build();
        
        orderDomainService.validateOrder(order);
        var savedOrder = orderRepository.save(order);
//        eventPublisher.publishOrderCreatedEvent(savedOrder);
        
        return orderMapper.toOrderResponse(savedOrder);
    }
    
    public OrderResponse getOrder(String orderId) {
        var order = orderRepository.findById(OrderId.of(orderId))
            .orElseThrow(() -> new OrderNotFoundException("Order not found: " + orderId));
        return orderMapper.toOrderResponse(order);
    }
    
    public List<OrderResponse> getCustomerOrders(String customerId) {
        var orders = orderRepository.findByCustomerId(
            new CustomerId(UUID.fromString(customerId))
        );
        return orders.stream()
            .map(orderMapper::toOrderResponse)
            .collect(Collectors.toList());
    }
    
    @Transactional
    public OrderResponse confirmOrder(String orderId) {
        var order = orderRepository.findById(OrderId.of(orderId))
            .orElseThrow(() -> new OrderNotFoundException("Order not found: " + orderId));
        
        order.confirm();
        var savedOrder = orderRepository.save(order);
//        eventPublisher.publishOrderConfirmedEvent(savedOrder);
        
        return orderMapper.toOrderResponse(savedOrder);
    }
    
    @Transactional
    public OrderResponse cancelOrder(String orderId, String reason) {
        var order = orderRepository.findById(OrderId.of(orderId))
            .orElseThrow(() -> new OrderNotFoundException("Order not found: " + orderId));
        
        if (!orderDomainService.canCancelOrder(order)) {
            throw new IllegalStateException("Order cannot be cancelled in current state");
        }
        
        order.cancel(reason);
        var savedOrder = orderRepository.save(order);
//        eventPublisher.publishOrderCancelledEvent(savedOrder);
        
        return orderMapper.toOrderResponse(savedOrder);
    }
    
    public static class OrderNotFoundException extends RuntimeException {
        public OrderNotFoundException(String message) {
            super(message);
        }
    }
}
