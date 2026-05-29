package com.schimer.reportsapp.domain.repositories;

import com.schimer.reportsapp.domain.entities.PtQualityIndicatorsEntity;

public class QualityIndicatorsRepository extends BaseRepository<PtQualityIndicatorsEntity>{

    public QualityIndicatorsRepository() {
        super(PtQualityIndicatorsEntity.class);
    }
}
