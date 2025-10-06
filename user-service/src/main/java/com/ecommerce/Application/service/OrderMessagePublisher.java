package com.ecommerce.Application.service;

import com.ecommerce.Application.dto.OrderCreatedEvent;
import com.ecommerce.Application.dto.OrderNotificationEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class OrderMessagePublisher {
    
    private static final Logger logger = LoggerFactory.getLogger(OrderMessagePublisher.class);
    
    @Inject
    @Channel("order-created")
    Emitter<OrderCreatedEvent> orderCreatedEmitter;
    
    @Inject
    @Channel("order-notification")
    Emitter<OrderNotificationEvent> orderNotificationEmitter;
    
    public void publishOrderCreatedEvent(OrderCreatedEvent event) {
        try {
            logger.info("Publishing order created event for order: {}", event.orderId());
            orderCreatedEmitter.send(event);
            logger.info("Successfully published order created event for order: {}", event.orderId());
        } catch (Exception e) {
            logger.error("Failed to publish order created event for order: {}", event.orderId(), e);
            throw new RuntimeException("Failed to publish order created event", e);
        }
    }
    
    public void publishOrderNotificationEvent(OrderNotificationEvent event) {
        try {
            logger.info("Publishing order notification event for order: {}", event.orderId());
            orderNotificationEmitter.send(event);
            logger.info("Successfully published order notification event for order: {}", event.orderId());
        } catch (Exception e) {
            logger.error("Failed to publish order notification event for order: {}", event.orderId(), e);
            throw new RuntimeException("Failed to publish order notification event", e);
        }
    }
}