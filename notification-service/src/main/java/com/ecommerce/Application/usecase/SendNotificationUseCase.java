package com.ecommerce.Application.usecase;

import com.ecommerce.Application.messaging.NotificationService;
import com.ecommerce.domain.model.Notification;
import com.ecommerce.domain.orderValue.NotificationType;
import com.ecommerce.Infrastructure.dto.UserRegisteredEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.LocalDateTime;
import java.util.UUID;

@ApplicationScoped
public class SendNotificationUseCase {


    @Inject
    NotificationService notificationService;

    public void handle(UserRegisteredEvent event) {
        Notification notification = new Notification(
            UUID.randomUUID(),
            event.email(),
            "Welcome " + event.email() + "!",
            NotificationType.EMAIL,
            LocalDateTime.now()
        );
        notificationService.send(notification);
    }
}