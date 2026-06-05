package com.schimer.reportsapp.domain.repositories.rawMaterial;

import com.schimer.reportsapp.domain.entities.rawMaterial.QualityParameterRawMaterialRelease;
import com.schimer.reportsapp.domain.repositories.BaseRepository;

public class ParameterRawMaterialRepository extends BaseRepository<QualityParameterRawMaterialRelease> {
    public ParameterRawMaterialRepository() {
        super(QualityParameterRawMaterialRelease.class);
    }
}
