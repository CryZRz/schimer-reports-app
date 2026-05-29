package com.schimer.reportsapp.domain.repositories;

import com.schimer.reportsapp.domain.entities.PtQualityCertificateEntity;

public class QualityCertificateRepository extends BaseRepository<PtQualityCertificateEntity> {

    public QualityCertificateRepository() {
        super(PtQualityCertificateEntity.class);
    }
}
