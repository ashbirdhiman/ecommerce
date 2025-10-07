package com.ecommerce.infrastructure.jparepository;


import com.ecommerce.application.repository.OrderRepository;
import com.ecommerce.domain.aggregate.Order;
import com.ecommerce.domain.valueobject.CustomerId;
import com.ecommerce.domain.valueobject.OrderId;
import com.ecommerce.domain.valueobject.OrderStatus;
import com.ecommerce.infrastructure.entity.OrderEntity;
import com.ecommerce.infrastructure.mapper.OrderMapper;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class OrderJpaRepository implements OrderRepository,PanacheRepositoryBase<OrderEntity, UUID> {


    public List<OrderEntity> findByCustomerId(UUID customerId) {
        return list("customerId", customerId);
    }
    
    public List<OrderEntity> findByStatus(String status) {
        return list("status", status);
    }

    public OrderEntity saveEntity(OrderEntity entity) {
        if (!isPersistent(entity)) {
            persist(entity);
        }
        return entity;
    }

    @Override
    public Order save(Order order) {

        OrderEntity entity = OrderMapper.toEntity(order);
        saveEntity(entity); // uses Panache persist logic
        return order;

    }

    @Override
    public Optional<Order> findById(OrderId orderId) {
        OrderEntity entity = findById(orderId.value());
        return Optional.ofNullable(entity != null ? OrderMapper.toDomain(entity) : null);
    }

    @Override
    public List<Order> findByCustomerId(CustomerId customerId) {
        // Try cache first, then database

        return List.of();
    }

    @Override
    public List<Order> findByStatus(OrderStatus status) {
        // Implementation would map OrderEntity list to Order list
        return List.of();
    }

    @Override
    public void delete(OrderId orderId) {
//        cacheService.invalidateOrder(orderId);
//        jpaRepository.deleteById(orderId.value());
    }
}
