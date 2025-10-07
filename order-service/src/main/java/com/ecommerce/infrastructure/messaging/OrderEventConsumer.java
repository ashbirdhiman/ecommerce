package com.ecommerce.infrastructure.messaging;

import com.ecommerce.application.dto.CreateOrderCommand;
import com.ecommerce.application.service.OrderApplicationService;
import io.smallrye.reactive.messaging.annotations.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.stream.Collectors;

@ApplicationScoped
public class OrderEventConsumer {
    
    private static final Logger logger = LoggerFactory.getLogger(OrderEventConsumer.class);
    
    @Inject
    OrderApplicationService orderService;
    
    @Incoming("order-created")
    @Blocking
    public void processOrderCreatedEvent(OrderCreatedEventMessage event) {
        try {
            logger.info("Processing order created event for order: {}", event.orderId());
            
            // Convert message to CreateOrderCommand
            var items = event.items().stream()
                .map(item -> new CreateOrderCommand.OrderItemDTO(
                    item.productId(),
                    item.productName(),
                    item.quantity(),
                    item.unitPrice(),
                    item.specialInstructions()
                ))
                .collect(Collectors.toList());
                
            var address = new CreateOrderCommand.AddressDTO(
                event.deliveryAddress().street(),
                event.deliveryAddress().city(),
                event.deliveryAddress().postalCode(),
                event.deliveryAddress().country(),
                event.deliveryAddress().latitude(),
                event.deliveryAddress().longitude()
            );
            
            var command = new CreateOrderCommand(
                event.customerId(),
                event.restaurantId(),
                items,
                address,
                event.deliveryInstructions()
            );
            
            // Process the order
            var orderResponse = orderService.createOrder(command);
            logger.info("Successfully processed order created event. Order ID: {}", orderResponse.orderId());
            
        } catch (Exception e) {
            logger.error("Failed to process order created event for order: {}", event.orderId(), e);
            // In a real application, you might want to send to a dead letter queue or retry
        }
    }
}