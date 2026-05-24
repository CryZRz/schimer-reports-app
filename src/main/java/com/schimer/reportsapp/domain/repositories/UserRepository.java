package com.schimer.reportsapp.domain.repositories;

import com.schimer.reportsapp.domain.entities.UserEntity;
import com.schimer.reportsapp.infrastructure.hibernate.HibernateConfig;

import java.util.Optional;

public class UserRepository extends BaseRepository<UserEntity> {

    public UserRepository() {
        super(UserEntity.class);
    }

    public Optional<UserEntity> getUserById(Long userId) {
        try(var session = HibernateConfig.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(UserEntity.class, userId));
        }
    }

    public Optional<UserEntity> getUserByEmail(String email) {
        try(var session = HibernateConfig.getSessionFactory().openSession()) {
            var query = session.createQuery("FROM UserEntity WHERE email = :email", UserEntity.class);
            query.setParameter("email", email);
            return query.uniqueResultOptional();
        }
    }

}
