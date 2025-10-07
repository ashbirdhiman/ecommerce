package com.ecommerce.infrastructure.messaging;

import io.smallrye.reactive.messaging.annotations.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

@ApplicationScoped
public class OrderNotificationConsumer {
    
    private static final Logger logger = LoggerFactory.getLogger(OrderNotificationConsumer.class);
    
    @Incoming("order-notifications")
    @Blocking
    public void processOrderNotification(OrderNotificationEventMessage event) {
        try {
            logger.info("Processing order notification for order: {} to customer: {}", 
                event.orderId(), event.customerEmail());
            
            // Simulate sending notification (email, SMS, push notification, etc.)
            sendOrderNotification(event);
            
            logger.info("Successfully sent order notification for order: {}", event.orderId());
            
        } catch (Exception e) {
            logger.error("Failed to process order notification for order: {}", event.orderId(), e);
            // In a real application, you might want to retry or send to a dead letter queue
        }
    }
    
    private void sendOrderNotification(OrderNotificationEventMessage event) {
        // Simulate email sending logic
        logger.info("📧 Sending {} notification to {}", event.notificationType(), event.customerEmail());
        logger.info("📧 Subject: Order Update - {}", event.orderId());
        logger.info("📧 Message: {}", event.message());
        logger.info("📧 Customer: {} ({})", event.customerName(), event.customerEmail());
        logger.info("📧 Timestamp: {}", event.timestamp());
        
        // In a real application, you would integrate with:
        // - Email service (SendGrid, AWS SES, etc.)
        // - SMS service (Twilio, AWS SNS, etc.)
        // - Push notification service (Firebase, AWS SNS, etc.)
        // - In-app notification system
        
        // Simulate processing time
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}