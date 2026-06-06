package com.schimer.reportsapp.services;

import com.schimer.reportsapp.auth.UserSession;
import com.schimer.reportsapp.domain.entities.ProductFinishedEntity;
import com.schimer.reportsapp.domain.repositories.ProductFinishedRepository;
import com.schimer.reportsapp.domain.repositories.templates.TemplatePTRepository;
import com.schimer.reportsapp.models.ProductFinishedForm;
import com.schimer.reportsapp.utils.guest.ProductFinishedMapper;

import java.util.List;

public class ProductFinishedService {
    private final ProductFinishedRepository productFinishedRepository = new ProductFinishedRepository();
    private final TemplatePTRepository templatePTRepository = new TemplatePTRepository();

    public ProductFinishedEntity create(ProductFinishedForm  productFinishedForm){
        var template = templatePTRepository.getLast();
        if(template.isPresent()){
            var data = bindProductFinishedForm(productFinishedForm);
            data.setTemplate(template.get());

            return productFinishedRepository.save(data);
        }

        throw new RuntimeException("No hay plantillas disponibles");
    }

    public ProductFinishedEntity update(ProductFinishedForm  productFinishedForm){
        var data = bindProductFinishedForm(productFinishedForm);

        return productFinishedRepository.update(data);
    }

    private ProductFinishedEntity bindProductFinishedForm(ProductFinishedForm productFinishedForm) {
        var productFinishedEntity =
                ProductFinishedMapper.toEntity(productFinishedForm);

        var qualityCertificateEntity =
                ProductFinishedMapper.qualityCertificateMapper(productFinishedForm);

        var qualityCertificateDetails =
                ProductFinishedMapper.qualityCertificateDetailsMapper(productFinishedForm);

        var qualityIndicatorsEntity =
                ProductFinishedMapper.qualityIndicatorsMapper(productFinishedForm);

        var qualitySolidLiquidEntity =
                ProductFinishedMapper.qualitySolidLiquidMapper(productFinishedForm);

        productFinishedEntity.setUser(UserSession.getInstance().getUser());

        qualityCertificateDetails.forEach(detailsEntity ->
                detailsEntity.setQualityCertificate(qualityCertificateEntity)
        );
        qualityCertificateEntity.setQualityDetails(qualityCertificateDetails);
        qualityCertificateEntity.setProductFinished(productFinishedEntity);

        qualityIndicatorsEntity.setProductFinished(productFinishedEntity);
        qualitySolidLiquidEntity.setProductFinished(productFinishedEntity);

        productFinishedEntity.setQualityCertificate(qualityCertificateEntity);
        productFinishedEntity.setQualityIndicators(qualityIndicatorsEntity);
        productFinishedEntity.setQualitySolidLiquid(qualitySolidLiquidEntity);

        return productFinishedEntity;
    }

    public List<ProductFinishedEntity> getAll(){
        return this.productFinishedRepository.getAll();
    }

}
