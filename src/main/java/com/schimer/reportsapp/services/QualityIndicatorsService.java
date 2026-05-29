package com.schimer.reportsapp.services;

import com.schimer.reportsapp.domain.entities.PtQualityIndicatorsEntity;
import com.schimer.reportsapp.domain.repositories.QualityIndicatorsRepository;

public class QualityIndicatorsService {
    private final QualityIndicatorsRepository qualityIndicatorsRepository = new QualityIndicatorsRepository();

    public PtQualityIndicatorsEntity create(PtQualityIndicatorsEntity entity){
        return this.qualityIndicatorsRepository.save(entity);
    }

    public PtQualityIndicatorsEntity  update(PtQualityIndicatorsEntity entity){
        return this.qualityIndicatorsRepository.update(entity);
    }

    public void delete(PtQualityIndicatorsEntity entity){
        this.qualityIndicatorsRepository.delete(entity);
    }
}