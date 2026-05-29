package com.schimer.reportsapp.domain.repositories;

import com.schimer.reportsapp.domain.entities.PtQualityCertificateDetailEntity;
import com.schimer.reportsapp.domain.entities.PtQualitySolidLiquidEntity;

public class QualitySolidLiquidRepository extends BaseRepository<PtQualitySolidLiquidEntity> {

    public QualitySolidLiquidRepository() {
        super(PtQualitySolidLiquidEntity.class);
    }
}
