package com.schimer.reportsapp.domain.repositories.templates;

import com.schimer.reportsapp.domain.entities.TemplatePTEntity;
import com.schimer.reportsapp.domain.repositories.BaseRepository;
import com.schimer.reportsapp.infrastructure.hibernate.HibernateConfig;

import java.util.Optional;

public class TemplatePTRepository extends BaseRepository<TemplatePTEntity> {

    public TemplatePTRepository() {
        super(TemplatePTEntity.class);
    }

    public Optional<TemplatePTEntity> getLast() {
        try(var session = HibernateConfig.getSessionFactory().openSession()) {
            var query = session.createQuery("FROM TemplatePTEntity ORDER BY id DESC", TemplatePTEntity.class);
            query.setMaxResults(1);

            return query.uniqueResultOptional();
        }
    }
}
