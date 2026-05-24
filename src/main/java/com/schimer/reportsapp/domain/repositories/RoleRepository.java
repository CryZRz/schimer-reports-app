package com.schimer.reportsapp.domain.repositories;

import com.schimer.reportsapp.domain.entities.RoleEntity;
import com.schimer.reportsapp.infrastructure.hibernate.HibernateConfig;

import java.util.Optional;

public class RoleRepository {

    public Optional<RoleEntity> getByName(String name) {
        try(var session = HibernateConfig.getSessionFactory().openSession()) {
            var query =  session.createQuery("FROM RoleEntity WHERE name = :name", RoleEntity.class);
            query.setParameter("name", name);

            return query.uniqueResultOptional();
        }
    }

}
