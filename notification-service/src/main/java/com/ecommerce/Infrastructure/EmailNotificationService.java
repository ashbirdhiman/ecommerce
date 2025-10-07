package com.ecommerce.Infrastructure;

import com.ecommerce.Application.messaging.NotificationService;
import com.ecommerce.Application.usecase.SendNotificationUseCase;
import com.ecommerce.Infrastructure.dto.UserRegisteredEvent;
import com.ecommerce.domain.model.Notification;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

import java.util.UUID;

@ApplicationScoped
public class EmailNotificationService implements NotificationService {

    private static final Logger LOG = Logger.getLogger(EmailNotificationService.class);

    @Inject
    SendNotificationUseCase sendNotificationUseCase;

    @Inject
    ObjectMapper objectMapper;

    @Incoming("user.registered")
    public void onUserRegistered(String payload) throws JsonProcessingException {
        UserRegisteredEvent event = objectMapper.readValue(payload, UserRegisteredEvent.class);
        sendNotificationUseCase.handle(event);
    }

    public void send(Notification notification) {
        // Send email via SMTP or Mailgun
        LOG.infof("📧 Sending email to %s: %s", notification.getRecipient(), notification.getMessage());
        System.out.println("Sending email to " + notification.getRecipient());
    }

    /**
     * Send welcome email to newly registered user
     */
    public void sendWelcomeEmail(String email, UUID userId) {
        try {
            LOG.infof("📧 Preparing welcome email for user: %s (ID: %s)", email, userId);
            
            String welcomeMessage = String.format(
                "Welcome to our eCommerce platform! " +
                "Thank you for registering with us. " +
                "Your user ID is: %s. " +
                "We're excited to have you on board!", 
                userId
            );

            // Create notification object with proper constructor
            Notification welcomeNotification = new Notification(
                UUID.randomUUID(),
                email,
                welcomeMessage,
                com.ecommerce.domain.orderValue.NotificationType.EMAIL,
                java.time.LocalDateTime.now()
            );
            
            // Send the welcome email
            send(welcomeNotification);
            
            LOG.infof("✅ Welcome email sent successfully to: %s", email);
            
        } catch (Exception e) {
            LOG.errorf("❌ Failed to send welcome email to %s: %s", email, e.getMessage());
        }
    }
}