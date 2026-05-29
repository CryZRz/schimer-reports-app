package com.schimer.reportsapp.domain.repositories;

import com.schimer.reportsapp.domain.entities.UserEntity;
import com.schimer.reportsapp.infrastructure.hibernate.HibernateConfig;
import com.schimer.reportsapp.utils.Constants;

import java.util.List;
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

    public List<UserEntity> getAllExceptAdmin() {
        try (var session = HibernateConfig.getSessionFactory().openSession()) {
            var query = session.createQuery(
                    "FROM UserEntity u WHERE u.role.id NOT IN (SELECT r.id FROM RoleEntity r WHERE r.name = :roleName) ORDER BY u.id DESC",
                    UserEntity.class
            );
            query.setParameter("roleName", Constants.ADMIN_ROLE);
            return query.getResultList();
        }
    }
}
