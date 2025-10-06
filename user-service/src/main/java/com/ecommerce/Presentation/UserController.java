package com.ecommerce.Presentation;

import com.ecommerce.Application.dto.RegisterUserCommand;
import com.ecommerce.Application.usecase.UserUseCase;
import com.ecommerce.Application.service.OrderService;
import com.ecommerce.Presentation.dto.OrderDto;
import com.ecommerce.Presentation.dto.RegisterUserRequest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Inject
    UserUseCase userUseCase;
    
    @Inject
    OrderService orderService;

    @POST
    @Transactional
    public Response registerUser(RegisterUserRequest request) {
        RegisterUserCommand command = new RegisterUserCommand(
            UUID.randomUUID(),
            request.name(),
            request.email(),
            request.password(),
            request.paymentMethod(),
            request.address()
        );
        userUseCase.registerUser(command);
        return Response.status(Response.Status.CREATED).build();
    }

    @POST
    @Path("/update/{email}")
    public Response updateUser(@PathParam("email") String email, RegisterUserRequest request) {
        RegisterUserCommand command = new RegisterUserCommand(
                UUID.randomUUID(),
                request.name(),
                request.email(),
                request.password(),
                request.paymentMethod(),
                request.address()
        );
        userUseCase.updateUser(email, command);
        return Response.ok().build();
    }

    @POST
    @Path("/orders")
    @Transactional
    public Response createOrder(OrderDto orderDto) {
        try {
            logger.info("Received order creation request for customer: {}", orderDto.getCustomerId());
            
            // Validate input
            if (orderDto.getCustomerId() == null || orderDto.getCustomerId().trim().isEmpty()) {
                logger.warn("Order creation failed: Customer ID is required");
                return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"Customer ID is required\"}")
                    .build();
            }
            
            if (orderDto.getRestaurantId() == null || orderDto.getRestaurantId().trim().isEmpty()) {
                logger.warn("Order creation failed: Restaurant ID is required");
                return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"Restaurant ID is required\"}")
                    .build();
            }
            
            if (orderDto.getItems() == null || orderDto.getItems().isEmpty()) {
                logger.warn("Order creation failed: Order items are required");
                return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"Order items are required\"}")
                    .build();
            }
            
            // Create order via messaging
            String orderId = orderService.createOrder(orderDto);
            
            logger.info("Order created successfully with ID: {}", orderId);
            return Response.status(Response.Status.CREATED)
                .entity("{\"orderId\": \"" + orderId + "\", \"message\": \"Order created successfully\"}")
                .build();
                
        } catch (Exception e) {
            logger.error("Failed to create order", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"error\": \"Failed to create order: " + e.getMessage() + "\"}")
                .build();
        }
    }

}