package com.ecommerce.domain.aggregate;

import com.ecommerce.domain.model.OrderItem;
import com.ecommerce.domain.valueobject.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Order {
    private final OrderId orderId;
    private final CustomerId customerId;
    private final RestaurantId restaurantId;
    private final List<OrderItem> items;
    private OrderStatus status;
    private final Address deliveryAddress;
    private Money subtotal;
    private Money deliveryFee;
    private Money tax;
    private Money total;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String deliveryInstructions;
    private String cancellationReason;

    private Order(Builder builder) {
        this.orderId = builder.orderId;
        this.customerId = builder.customerId;
        this.restaurantId = builder.restaurantId;
        this.items = new ArrayList<>(builder.items);
        this.status = OrderStatus.PENDING;
        this.deliveryAddress = builder.deliveryAddress;
        this.deliveryInstructions = builder.deliveryInstructions;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        calculateTotals();
    }

    private void calculateTotals() {
        this.subtotal = items.stream()
                .map(OrderItem::getSubtotal)
                .reduce(Money.zero("CAD"), Money::add);

        this.deliveryFee = calculateDeliveryFee();
        this.tax = calculateTax();
        this.total = subtotal.add(deliveryFee).add(tax);
    }

    private Money calculateDeliveryFee() {
        return Money.of(new java.math.BigDecimal("4.99"), "CAD");
    }

    private Money calculateTax() {
        var taxAmount = subtotal.amount()
                .multiply(new java.math.BigDecimal("0.13"));
        return Money.of(taxAmount, "CAD");
    }

    public void confirm() {
        validateStatusTransition(OrderStatus.CONFIRMED);
        this.status = OrderStatus.CONFIRMED;
        this.updatedAt = LocalDateTime.now();
    }

    public void markAsPreparing() {
        validateStatusTransition(OrderStatus.PREPARING);
        this.status = OrderStatus.PREPARING;
        this.updatedAt = LocalDateTime.now();
    }

    public void markAsReadyForPickup() {
        validateStatusTransition(OrderStatus.READY_FOR_PICKUP);
        this.status = OrderStatus.READY_FOR_PICKUP;
        this.updatedAt = LocalDateTime.now();
    }

    public void markAsOutForDelivery() {
        validateStatusTransition(OrderStatus.OUT_FOR_DELIVERY);
        this.status = OrderStatus.OUT_FOR_DELIVERY;
        this.updatedAt = LocalDateTime.now();
    }

    public void markAsDelivered() {
        validateStatusTransition(OrderStatus.DELIVERED);
        this.status = OrderStatus.DELIVERED;
        this.updatedAt = LocalDateTime.now();
    }

    public void cancel(String reason) {
        validateStatusTransition(OrderStatus.CANCELLED);
        this.status = OrderStatus.CANCELLED;
        this.cancellationReason = reason;
        this.updatedAt = LocalDateTime.now();
    }

    private void validateStatusTransition(OrderStatus newStatus) {
        if (!status.canTransitionTo(newStatus)) {
            throw new IllegalStateException(
                    String.format("Cannot transition from %s to %s", status, newStatus)
            );
        }
    }

    public OrderId getOrderId() { return orderId; }
    public CustomerId getCustomerId() { return customerId; }
    public RestaurantId getRestaurantId() { return restaurantId; }
    public List<OrderItem> getItems() { return new ArrayList<>(items); }
    public OrderStatus getStatus() { return status; }
    public Address getDeliveryAddress() { return deliveryAddress; }
    public Money getSubtotal() { return subtotal; }
    public Money getDeliveryFee() { return deliveryFee; }
    public Money getTax() { return tax; }
    public Money getTotal() { return total; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public String getDeliveryInstructions() { return deliveryInstructions; }
    public String getCancellationReason() { return cancellationReason; }

    public static class Builder {
        private OrderId orderId;
        private CustomerId customerId;
        private RestaurantId restaurantId;
        private List<OrderItem> items = new ArrayList<>();
        private Address deliveryAddress;
        private String deliveryInstructions;

        public Builder orderId(OrderId orderId) {
            this.orderId = orderId;
            return this;
        }

        public Builder customerId(CustomerId customerId) {
            this.customerId = customerId;
            return this;
        }

        public Builder restaurantId(RestaurantId restaurantId) {
            this.restaurantId = restaurantId;
            return this;
        }

        public Builder items(List<OrderItem> items) {
            this.items = items;
            return this;
        }

        public Builder deliveryAddress(Address deliveryAddress) {
            this.deliveryAddress = deliveryAddress;
            return this;
        }

        public Builder deliveryInstructions(String instructions) {
            this.deliveryInstructions = instructions;
            return this;
        }

        public Order build() {
            Objects.requireNonNull(customerId, "Customer ID is required");
            Objects.requireNonNull(restaurantId, "Restaurant ID is required");
            Objects.requireNonNull(deliveryAddress, "Delivery address is required");
            if (items.isEmpty()) {
                throw new IllegalArgumentException("Order must have at least one item");
            }
            if (orderId == null) {
                orderId = OrderId.generate();
            }
            return new Order(this);
        }
    }
}
