package com.schimer.reportsapp.services;

import com.schimer.reportsapp.auth.UserSession;
import com.schimer.reportsapp.domain.repositories.ProductFinishedRepository;
import com.schimer.reportsapp.domain.repositories.QualityCertificateRepository;
import com.schimer.reportsapp.domain.repositories.QualityIndicatorsRepository;
import com.schimer.reportsapp.domain.repositories.QualitySolidLiquidRepository;
import com.schimer.reportsapp.models.ProductFinishedForm;
import com.schimer.reportsapp.utils.guest.ProductFinishedMapper;

public class ProductFinishedService {
    private final ProductFinishedRepository productFinishedRepository = new ProductFinishedRepository();
    private final QualityCertificateRepository qualityCertificateRepository = new QualityCertificateRepository();
    private final QualityIndicatorsRepository qualityIndicatorsRepository = new QualityIndicatorsRepository();
    private final QualitySolidLiquidRepository qualitySolidLiquidRepository = new QualitySolidLiquidRepository();

    public void create(ProductFinishedForm  productFinishedForm){
        var productFinishedEntity = ProductFinishedMapper.toEntity(productFinishedForm);
        var qualityCertificateEntity = ProductFinishedMapper.qualityCertificateMapper(productFinishedForm);
        var qualityIndicatorsEntity = ProductFinishedMapper.qualityIndicatorsMapper(productFinishedForm);
        var qualitySolidLiquidEntity = ProductFinishedMapper.qualitySolidLiquidMapper(productFinishedForm);

        productFinishedEntity.setUser(UserSession.getInstance().getUser());
        productFinishedRepository.save(productFinishedEntity);

        qualityCertificateEntity.setProductFinished(productFinishedEntity);
        qualityCertificateRepository.save(qualityCertificateEntity);

        qualityIndicatorsEntity.setProductFinished(productFinishedEntity);
        qualityIndicatorsRepository.save(qualityIndicatorsEntity);

        qualitySolidLiquidEntity.setProductFinished(productFinishedEntity);
        qualitySolidLiquidRepository.save(qualitySolidLiquidEntity);
    }
}
