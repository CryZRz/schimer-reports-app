package com.schimer.reportsapp.utils.guest;

import com.schimer.reportsapp.domain.entities.*;
import com.schimer.reportsapp.models.ProductFinishedForm;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProductFinishedMapper {

    public static ProductFinishedEntity toEntity(ProductFinishedForm context){
        var productFinished = new ProductFinishedEntity();
        productFinished.setProduct(context.getProduct().get());
        productFinished.setBatch(context.getBatch().get());

        return productFinished;
    }

    public static PtQualityCertificateEntity qualityCertificateMapper(ProductFinishedForm context){
        var qualityCertificate = new PtQualityCertificateEntity();
        qualityCertificate.setAmount(Integer.parseInt(context.getAmount().get()));
        qualityCertificate.setExpirationDate(context.getExpirationDate().get());
        return qualityCertificate;
    }

    public static List<PtQualityCertificateDetailEntity> qualityCertificateDetailsMapper(ProductFinishedForm context){
        var list =  new ArrayList<PtQualityCertificateDetailEntity>();
        context.getQualityFormRows().forEach(qualityFormRow -> {
            var qualityCertificate = new PtQualityCertificateDetailEntity();
            qualityCertificate.setParameterValue(qualityFormRow.getParameter());
            qualityCertificate.setResultValue(qualityFormRow.getResult());
            qualityCertificate.setUnitsValue(qualityFormRow.getUnits());
            qualityCertificate.setMethodologyValue(qualityFormRow.getMethodology());
            qualityCertificate.setSpecificationName(qualityFormRow.getSpecification());
            list.add(qualityCertificate);
        });

        return list;
    }

    public static PtQualityIndicatorsEntity qualityIndicatorsMapper(ProductFinishedForm context){
        var ptQualityIndicators = new PtQualityIndicatorsEntity();
        ptQualityIndicators.setStatus(context.getIsLiberated().get());
        ptQualityIndicators.setReciptDate(LocalDate.now());
        ptQualityIndicators.setReleaseDate(LocalDate.now());
        return ptQualityIndicators;
    }

    public static PtQualitySolidLiquidEntity qualitySolidLiquidMapper(ProductFinishedForm context){
        var ptQualitySolidLiquid = new PtQualitySolidLiquidEntity();
        ptQualitySolidLiquid.setSolids(new BigDecimal(context.getSolids().get()));
        ptQualitySolidLiquid.setPh(new BigDecimal(context.getPh().get()));
        ptQualitySolidLiquid.setApparentDensity(new BigDecimal(context.getApparentDensity().get()));
        ptQualitySolidLiquid.setAppearance(context.getAppearance().get());
        ptQualitySolidLiquid.setZincOxidePercentage(new BigDecimal(context.getZno().get()));
        ptQualitySolidLiquid.setKilograms(new  BigDecimal(context.getKilograms().get()));
        ptQualitySolidLiquid.setIdentificationReview(context.getIdentificationReview().get());
        ptQualitySolidLiquid.setPackagingReview(context.getPackagingReview().get());
        ptQualitySolidLiquid.setCertificate(Integer.parseInt(context.getCertificate().get()));

        return ptQualitySolidLiquid;
    }

}
