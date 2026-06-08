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

    public List<ProductFinishedEntity> getByProductParam(String find) {
        try(var session = HibernateConfig.getSessionFactory().openSession()) {
            var query = session.createQuery(
                    "FROM ProductFinishedEntity p " +
                            "WHERE p.batch LIKE :param OR LOWER(p.product) LIKE LOWER(:param) "+
                            "ORDER BY p.id DESC",
                    ProductFinishedEntity.class
            );
            query.setParameter("param", "%"+find+"%");
            return query.getResultList();
        }
    }
}
