package com.ecommerce.Application.port;

import com.ecommerce.domain.model.User;


public interface UserRepository {
    void save(User user);
    User findByEmail(String email);

    void updateUser(String email, User user);
}