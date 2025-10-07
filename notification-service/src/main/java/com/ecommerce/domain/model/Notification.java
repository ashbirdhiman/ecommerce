package com.ecommerce.domain.model;

import com.ecommerce.domain.orderValue.NotificationType;

import java.time.LocalDateTime;
import java.util.UUID;

public class Notification {
    private UUID id;
    private String recipient;
    private String message;
    private NotificationType type;
    private LocalDateTime timestamp;
    // constructor, getters, etc.

    String email;

    public Notification(UUID id, String recipient, String message, NotificationType type, LocalDateTime timestamp) {
        this.id = id;
        this.recipient = recipient;
        this.message = message;
        this.type = type;
        this.timestamp = timestamp;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
