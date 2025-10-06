package com.ecommerce.Application.port;

import com.ecommerce.domain.event.UserRegisteredEvent;

public interface EventPublisher {
    void publish(UserRegisteredEvent event);
}