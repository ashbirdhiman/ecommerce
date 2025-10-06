package com.ecommerce.Application.usecase;

import com.ecommerce.Application.dto.RegisterUserCommand;

import com.ecommerce.Application.port.EventPublisher;
import com.ecommerce.Application.port.UserRepository;
import com.ecommerce.domain.model.User;
import com.ecommerce.domain.event.UserRegisteredEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.UUID;

@ApplicationScoped
public class UserUseCase {

    @Inject
    UserRepository userRepository;

    @Inject
    EventPublisher eventPublisher;

    public UserUseCase(UserRepository userRepository, EventPublisher eventPublisher) {
        this.userRepository = userRepository;
        this.eventPublisher = eventPublisher;
    }

    public void registerUser(RegisterUserCommand command) {
        User user = new User(
                UUID.randomUUID(),
                command.name(),
                command.email(),
                command.password(),
                command.paymentMethod(),
                command.address()
        );

        userRepository.save(user);
        eventPublisher.publish(new UserRegisteredEvent(user.getId(),user.getEmail()));
    }

    public void updateUser(String email,RegisterUserCommand command){
        User user = new User(
                null,
                command.name(),
                command.email(),
                command.password(),
                command.paymentMethod(),
                command.address()
        );
        userRepository.updateUser(email,user);
    }
}