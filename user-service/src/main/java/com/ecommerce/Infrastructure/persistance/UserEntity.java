package com.ecommerce.Infrastructure.persistance;

import com.ecommerce.domain.model.User;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import com.ecommerce.domain.valueObject.Address;
import com.ecommerce.domain.valueObject.PaymentMethod;

import java.util.UUID;

@Entity
@Table(name = "users")
public class UserEntity  extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public UUID id;

    @Column(nullable = false)
    public String name;

    @Column(nullable = false, unique = true)
    public String email;

    @Column(nullable = false)
    public String password;

//    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public String paymentMethodCode;

    @Embedded
    public Address address;

    // Optional: conversion methods
    public static UserEntity fromDomain(User user) {
        UserEntity entity = new UserEntity();
        entity.name = user.getName();
        entity.email = user.getEmail();
        entity.password = user.getPassword();
        entity.paymentMethodCode = user.getPaymentMethod().name();
        entity.address = user.getAddress();
        return entity;
    }

    public User toDomain() {
        return new User(
                id,
                name,
                email,
                password,
                PaymentMethod.valueOf(paymentMethodCode),
                address
        );
    }



}