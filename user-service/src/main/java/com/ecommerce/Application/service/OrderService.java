package com.ecommerce.Application.service;

import com.ecommerce.Application.dto.OrderCreatedEvent;
import com.ecommerce.Application.dto.OrderNotificationEvent;
import com.ecommerce.Presentation.dto.DeliveryAddressDto;
import com.ecommerce.Presentation.dto.ItemDto;
import com.ecommerce.Presentation.dto.OrderDto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class OrderService {
    
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    
    @Inject
    OrderMessagePublisher messagePublisher;
    
    public String createOrder(OrderDto orderDto) {
        try {
            // Generate order ID
            String orderId = UUID.randomUUID().toString();
            logger.info("Creating order with ID: {}", orderId);
            
            // Create order event for the order service
            OrderCreatedEvent orderEvent = createOrderCreatedEvent(orderId, orderDto);
            
            // Create notification event for the notification service
            OrderNotificationEvent notificationEvent = createOrderNotificationEvent(orderId, orderDto);
            
            // Publish events to RabbitMQ
            messagePublisher.publishOrderCreatedEvent(orderEvent);
            messagePublisher.publishOrderNotificationEvent(notificationEvent);
            
            logger.info("Successfully created order: {}", orderId);
            return orderId;
            
        } catch (Exception e) {
            logger.error("Failed to create order", e);
            throw new RuntimeException("Failed to create order", e);
        }
    }
    
    private OrderCreatedEvent createOrderCreatedEvent(String orderId, OrderDto orderDto) {
        var items = orderDto.getItems().stream()
            .map(item -> new OrderCreatedEvent.OrderItemEvent(
                item.getProductId(),
                item.getProductName(),
                item.getQuantity(),
                item.getUnitPrice(),
                item.getSpecialInstructions()
            ))
            .collect(Collectors.toList());
            
        var deliveryAddress = new OrderCreatedEvent.DeliveryAddressEvent(
            orderDto.getDeliveryAddress().getStreet(),
            orderDto.getDeliveryAddress().getCity(),
            orderDto.getDeliveryAddress().getPostalCode(),
            orderDto.getDeliveryAddress().getCountry(),
            orderDto.getDeliveryAddress().getLatitude(),
            orderDto.getDeliveryAddress().getLongitude()
        );
        
        return new OrderCreatedEvent(
            orderId,
            orderDto.getCustomerId(),
            orderDto.getRestaurantId(),
            items,
            deliveryAddress,
            orderDto.getDeliveryInstructions(),
            LocalDateTime.now()
        );
    }
    
    private OrderNotificationEvent createOrderNotificationEvent(String orderId, OrderDto orderDto) {
        // In a real application, you would fetch customer details from the user service
        // For now, we'll use placeholder values
        String customerEmail = "customer@example.com"; // This should be fetched from user service
        String customerName = "Customer"; // This should be fetched from user service
        String restaurantName = "Restaurant"; // This should be fetched from restaurant service
        
        String message = String.format(
            "Your order #%s has been placed successfully at %s. " +
            "Items: %d. Delivery address: %s, %s. " +
            "We'll notify you when your order is confirmed.",
            orderId.substring(0, 8), // Short order ID
            restaurantName,
            orderDto.getItems().size(),
            orderDto.getDeliveryAddress().getStreet(),
            orderDto.getDeliveryAddress().getCity()
        );
        
        return new OrderNotificationEvent(
            orderId,
            orderDto.getCustomerId(),
            customerEmail,
            customerName,
            restaurantName,
            "ORDER_CREATED",
            message,
            LocalDateTime.now()
        );
    }
}