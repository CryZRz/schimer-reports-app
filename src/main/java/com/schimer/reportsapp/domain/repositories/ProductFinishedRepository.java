package com.schimer.reportsapp.domain.repositories;

import com.schimer.reportsapp.domain.entities.ProductFinishedEntity;
import com.schimer.reportsapp.infrastructure.hibernate.HibernateConfig;

import java.util.List;

public class ProductFinishedRepository extends BaseRepository<ProductFinishedEntity> {
    public ProductFinishedRepository() {
        super(ProductFinishedEntity.class);
    }

    public List<ProductFinishedEntity> getAll() {
        try(var session = HibernateConfig.getSessionFactory().openSession()) {
            var query = session.createQuery(
                    "FROM ProductFinishedEntity p ORDER BY p.id DESC",
                    ProductFinishedEntity.class
            );
            return query.getResultList();
        }
    }
}
