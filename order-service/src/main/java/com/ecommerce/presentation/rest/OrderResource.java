package com.ecommerce.presentation.rest;

import com.ecommerce.application.dto.CreateOrderCommand;
import com.ecommerce.application.dto.OrderResponse;
import com.ecommerce.application.service.OrderApplicationService;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/api/v1/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {
    
    @Inject
    OrderApplicationService orderService;
    
    @POST
    public Response createOrder(@Valid CreateOrderCommand command) {
        var order = orderService.createOrder(command);
        return Response.status(Response.Status.CREATED)
            .entity(order)
            .build();
    }
    
    @GET
    @Path("/{orderId}")
    public Response getOrder(@PathParam("orderId") String orderId) {
        var order = orderService.getOrder(orderId);
        return Response.ok(order).build();
    }
    
    @GET
    @Path("/customer/{customerId}")
    public Response getCustomerOrders(@PathParam("customerId") String customerId) {
        List<OrderResponse> orders = orderService.getCustomerOrders(customerId);
        return Response.ok(orders).build();
    }
    
    @PUT
    @Path("/{orderId}/confirm")
    public Response confirmOrder(@PathParam("orderId") String orderId) {
        var order = orderService.confirmOrder(orderId);
        return Response.ok(order).build();
    }
    
    @PUT
    @Path("/{orderId}/cancel")
    public Response cancelOrder(
            @PathParam("orderId") String orderId,
            @QueryParam("reason") String reason) {
        var order = orderService.cancelOrder(orderId, reason);
        return Response.ok(order).build();
    }
}
