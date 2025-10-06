package com.ecommerce.Infrastructure.persistance;

import com.ecommerce.Application.port.UserRepository;
import com.ecommerce.domain.model.User;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class JpaUserRepository implements UserRepository,PanacheRepositoryBase<UserEntity, UUID> {


    @Override
    public void save(User user) {
        UserEntity.fromDomain(user).persist();
    }

    @Override
    public User findByEmail(String email) {
        UserEntity entity = UserEntity.find("email", email).firstResult();
        return entity != null ? entity.toDomain() : null;

    }

    @Override
    public void updateUser(String email, User user) {
        UserEntity entity = UserEntity.find("email", email).firstResult();
        if (entity != null) {
            entity.name = user.getName();
            entity.password = user.getPassword();
            entity.paymentMethodCode = user.getPaymentMethod().name();
            entity.address = user.getAddress();
            entity.persist(); // Optional: Panache auto-tracks changes, but this ensures persistence
        }
    }
}
