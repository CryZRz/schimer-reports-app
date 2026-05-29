package com.schimer.reportsapp.domain.repositories;

import com.schimer.reportsapp.domain.entities.PtQualityCertificateDetailEntity;

public class QualityCertificateDetailRepository extends BaseRepository<PtQualityCertificateDetailEntity> {

    public QualityCertificateDetailRepository() {
        super(PtQualityCertificateDetailEntity.class);
    }
}
