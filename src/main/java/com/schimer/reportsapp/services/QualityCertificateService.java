package com.schimer.reportsapp.services;

import com.schimer.reportsapp.domain.entities.PtQualityCertificateEntity;
import com.schimer.reportsapp.domain.repositories.QualityCertificateRepository;

public class QualityCertificateService {
    private final QualityCertificateRepository qualityCertificateRepository = new QualityCertificateRepository();

    public PtQualityCertificateEntity create(PtQualityCertificateEntity entity){
        return this.qualityCertificateRepository.save(entity);
    }

    public PtQualityCertificateEntity update(PtQualityCertificateEntity entity){
        return this.qualityCertificateRepository.update(entity);
    }

    public void delete(PtQualityCertificateEntity entity){
        this.qualityCertificateRepository.delete(entity);
    }
}
