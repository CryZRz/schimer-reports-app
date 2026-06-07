package com.schimer.reportsapp.domain.repositories.templates;

import com.schimer.reportsapp.domain.entities.rawMaterial.TemplateRawMaterialEntity;
import com.schimer.reportsapp.domain.repositories.BaseRepository;
import com.schimer.reportsapp.infrastructure.hibernate.HibernateConfig;

import java.util.Optional;

public class TemplateMPRepository extends BaseRepository<TemplateRawMaterialEntity> {

    public TemplateMPRepository() {
        super(TemplateRawMaterialEntity.class);
    }

    public Optional<TemplateRawMaterialEntity> getLast() {
        try(var session = HibernateConfig.getSessionFactory().openSession()) {
            var query = session.createQuery("FROM TemplateRawMaterialEntity ORDER BY id DESC", TemplateRawMaterialEntity.class);
            query.setMaxResults(1);

            return query.uniqueResultOptional();
        }
    }
}
