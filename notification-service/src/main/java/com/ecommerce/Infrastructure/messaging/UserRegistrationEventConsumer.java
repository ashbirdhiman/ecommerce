package com.ecommerce.infrastructure.messaging;

import com.ecommerce.domain.event.UserRegisteredEvent;
import com.ecommerce.Infrastructure.EmailNotificationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

@ApplicationScoped
public class UserRegistrationEventConsumer {

    private static final Logger LOG = Logger.getLogger(UserRegistrationEventConsumer.class);

    @Inject
    ObjectMapper objectMapper;

    @Inject
    EmailNotificationService emailNotificationService;

    @Incoming("user-registered-notifications")
    public void handleUserRegisteredEvent(String message) {
        try {
            UserRegisteredEvent event = objectMapper.readValue(message, UserRegisteredEvent.class);
            
            LOG.infof("🎉 Notification Service received user registered event for user ID: %s, email: %s", 
                     event.userId(), event.email());
            
            // Send welcome email notification
            sendWelcomeEmailNotification(event);
            
        } catch (JsonProcessingException e) {
            LOG.errorf("❌ Failed to deserialize user registered event: %s", e.getMessage());
        } catch (Exception e) {
            LOG.errorf("❌ Error processing user registered event: %s", e.getMessage());
        }
    }

    private void sendWelcomeEmailNotification(UserRegisteredEvent event) {
        try {
            LOG.infof("📧 Sending welcome email notification to: %s", event.email());
            
            // Use the existing EmailNotificationService
            emailNotificationService.sendWelcomeEmail(event.email(), event.userId());
            
            LOG.infof("✅ Welcome email notification sent successfully to: %s", event.email());
            
        } catch (Exception e) {
            LOG.errorf("❌ Failed to send welcome email to %s: %s", event.email(), e.getMessage());
        }
    }
}