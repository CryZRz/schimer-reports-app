package com.schimer.reportsapp.domain.repositories.rawMaterial;

import com.schimer.reportsapp.domain.entities.ProductFinishedEntity;
import com.schimer.reportsapp.domain.entities.rawMaterial.RawMaterialEntity;
import com.schimer.reportsapp.domain.repositories.BaseRepository;
import com.schimer.reportsapp.infrastructure.hibernate.HibernateConfig;

import java.util.List;

public class RawMaterialRepository extends BaseRepository<RawMaterialEntity> {
    public RawMaterialRepository() {
        super(RawMaterialEntity.class);
    }

    public List<RawMaterialEntity> getByProductParam(String find) {
        try(var session = HibernateConfig.getSessionFactory().openSession()) {
            var query = session.createQuery(
                    "FROM RawMaterialEntity r " +
                            "WHERE r.batch LIKE :param OR LOWER(r.product) LIKE LOWER(:param) "+
                            "ORDER BY r.id DESC",
                    RawMaterialEntity.class
            );
            query.setParameter("param", "%"+find+"%");
            return query.getResultList();
        }
    }
}
