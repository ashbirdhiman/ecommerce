package com.ecommerce.Infrastructure.messaging;

import com.ecommerce.Application.port.EventPublisher;
import com.ecommerce.domain.event.UserRegisteredEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.smallrye.reactive.messaging.annotations.Channel;
import io.smallrye.reactive.messaging.annotations.Emitter;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

@ApplicationScoped
public class UserEventPublisher implements EventPublisher {

    private static final Logger LOG = Logger.getLogger(UserEventPublisher.class);

    @Inject
    @Channel("user-registered")
    Emitter<String> userRegisteredEmitter;

    @Inject
    ObjectMapper objectMapper;

    @Override
    public void publish(UserRegisteredEvent event) {
        try {
            String eventJson = objectMapper.writeValueAsString(event);
            userRegisteredEmitter.send(eventJson);
            LOG.infof("Published user registered event for user ID: %s", event.userId());
        } catch (JsonProcessingException e) {
            LOG.errorf("Failed to serialize user registered event: %s", e.getMessage());
            throw new RuntimeException("Failed to publish user registered event", e);
        }
    }
}
