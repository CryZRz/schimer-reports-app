package com.schimer.reportsapp.services.rawMaterial;

import com.schimer.reportsapp.auth.UserSession;
import com.schimer.reportsapp.domain.entities.rawMaterial.RawMaterialEntity;
import com.schimer.reportsapp.domain.repositories.rawMaterial.RawMaterialRepository;
import com.schimer.reportsapp.domain.repositories.templates.TemplateMPRepository;
import com.schimer.reportsapp.models.RawMaterialForm;
import com.schimer.reportsapp.services.admin.TemplateMPService;
import com.schimer.reportsapp.utils.guest.RawMaterialMapper;

import java.util.List;

public class RawMaterialService {

    private final RawMaterialRepository rawMaterialRepository =  new RawMaterialRepository();
    private final TemplateMPService templateMPService = new TemplateMPService();
    private final TemplateMPRepository templateMPRepository = new TemplateMPRepository();

    public RawMaterialEntity save(RawMaterialForm context) {
        var template = templateMPRepository.getLast();
        if (template.isPresent()) {
            var entity = getRawMaterialEntity(context);
            var reportPath = templateMPService.generateReport(entity, template.get().getPath());
            entity.setReportPath(reportPath);
            entity.setTemplate(template.get());
            return rawMaterialRepository.save(entity);
        }

        throw new RuntimeException("No hay plantilla contacta con el administrador");
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

    public List<RawMaterialEntity> getByParam(String param) {
        return rawMaterialRepository.getByProductParam(param);
    }
}
