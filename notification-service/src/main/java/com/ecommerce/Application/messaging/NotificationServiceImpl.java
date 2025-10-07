package com.ecommerce.Application.messaging;

import com.ecommerce.domain.model.Notification;
import jakarta.enterprise.context.ApplicationScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class NotificationServiceImpl implements NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);

    @Override
    public void send(Notification notification) {
        // Simple implementation that logs the notification
        // In a real application, this would integrate with email/SMS/push notification services
        
        logger.info("📧 Sending {} notification to {}", 
            notification.getType(), notification.getRecipient());
        logger.info("📧 Message: {}", notification.getMessage());
        logger.info("📧 Timestamp: {}", notification.getTimestamp());
        
        // Simulate processing time
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Notification sending interrupted", e);
        }
        
        logger.info("✅ Notification sent successfully to {}", notification.getRecipient());
    }
}