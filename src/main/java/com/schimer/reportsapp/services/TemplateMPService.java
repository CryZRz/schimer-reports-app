package com.schimer.reportsapp.services;

import com.schimer.reportsapp.domain.entities.rawMaterial.TemplateRawMaterialEntity;
import com.schimer.reportsapp.domain.repositories.templates.TemplateMPRepository;

import java.util.List;

public class TemplateMPService {

    private final TemplateMPRepository repository = new TemplateMPRepository();

    public List<TemplateRawMaterialEntity> getAll(){
        return repository.getAll();
    }
}
