package com.schimer.reportsapp.domain.repositories.rawMaterial;

import com.schimer.reportsapp.domain.entities.rawMaterial.RawMaterialEntity;
import com.schimer.reportsapp.domain.repositories.BaseRepository;

public class RawMaterialRepository extends BaseRepository<RawMaterialEntity> {
    public RawMaterialRepository() {
        super(RawMaterialEntity.class);
    }
}
