package com.schimer.reportsapp.services.rawMaterial;

import com.schimer.reportsapp.auth.UserSession;
import com.schimer.reportsapp.domain.entities.rawMaterial.RawMaterialEntity;
import com.schimer.reportsapp.domain.repositories.rawMaterial.ParameterRawMaterialRepository;
import com.schimer.reportsapp.domain.repositories.rawMaterial.RawMaterialRepository;
import com.schimer.reportsapp.models.RawMaterialForm;
import com.schimer.reportsapp.utils.guest.RawMaterialMapper;

import java.util.List;

public class RawMaterialService {

    private final RawMaterialRepository rawMaterialRepository =  new RawMaterialRepository();

    public RawMaterialEntity save(RawMaterialForm context) {
        var entity = getRawMaterialEntity(context);
        return rawMaterialRepository.save(entity);
    }

    public List<RawMaterialEntity> getAll() {
        return rawMaterialRepository.getAll();
    }

    public RawMaterialEntity update(RawMaterialForm context) {
        var entity = getRawMaterialEntity(context);
        return rawMaterialRepository.update(entity);
    }

    private RawMaterialEntity getRawMaterialEntity(RawMaterialForm context) {
        var rawMaterialEntity = RawMaterialMapper.toEntity(context);
        var rawMaterialReleaseEntity = RawMaterialMapper.rawMaterialReleaseToEntity(context);
        var parametersRawMaterialEntities =  RawMaterialMapper.parameterRawMaterialToEntity(context.getQualityFormRows());
        var user = UserSession.getInstance().getUser();

        rawMaterialEntity.setUser(user);
        parametersRawMaterialEntities.forEach(entity -> {
            entity.setRawMaterialReleases(rawMaterialReleaseEntity);
        });
        rawMaterialReleaseEntity.setQualityParameters(parametersRawMaterialEntities);
        rawMaterialEntity.setRawMaterialRelease(rawMaterialReleaseEntity);
        rawMaterialReleaseEntity.setRawMaterial(rawMaterialEntity);

        return rawMaterialEntity;
    }
}
