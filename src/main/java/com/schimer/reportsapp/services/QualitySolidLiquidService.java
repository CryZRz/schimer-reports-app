package com.schimer.reportsapp.services;

import com.schimer.reportsapp.domain.entities.PtQualitySolidLiquidEntity;
import com.schimer.reportsapp.domain.repositories.QualitySolidLiquidRepository;

public class QualitySolidLiquidService {
    private final QualitySolidLiquidRepository qualitySolidLiquidRepository = new QualitySolidLiquidRepository();

    public PtQualitySolidLiquidEntity create(PtQualitySolidLiquidEntity entity){
        return this.qualitySolidLiquidRepository.save(entity);
    }

    public PtQualitySolidLiquidEntity update(PtQualitySolidLiquidEntity entity){
        return this.qualitySolidLiquidRepository.update(entity);
    }

    public void delete(PtQualitySolidLiquidEntity entity){
        this.qualitySolidLiquidRepository.delete(entity);
    }
}
