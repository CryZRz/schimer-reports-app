package com.schimer.reportsapp.utils.guest;

import com.schimer.reportsapp.domain.entities.*;
import com.schimer.reportsapp.models.ProductFinishedForm;
import com.schimer.reportsapp.models.QualityFormRow;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProductFinishedMapper {

    public static ProductFinishedEntity toEntity(ProductFinishedForm context){
        var productFinished = new ProductFinishedEntity();
        if (context.getProductFinishedFormId() != null) {
            productFinished.setId(context.getProductFinishedFormId());
        }
        productFinished.setProduct(context.getProduct().get());
        productFinished.setBatch(context.getBatch().get());
        productFinished.setFolio(context.getFolio().get());
        productFinished.setCreatedAt(context.getCreatedAt().get());

        return productFinished;
    }

    public static PtQualityCertificateEntity qualityCertificateMapper(ProductFinishedForm context){
        var qualityCertificate = new PtQualityCertificateEntity();
        if (context.getQualityCertificateId() != null) {
            qualityCertificate.setId(context.getQualityCertificateId());
        }
        qualityCertificate.setAmount(Integer.parseInt(context.getAmount().get()));
        qualityCertificate.setExpirationDate(context.getExpirationDate().get());
        return qualityCertificate;
    }

    public static List<PtQualityCertificateDetailEntity> qualityCertificateDetailsMapper(ProductFinishedForm context){
        var list =  new ArrayList<PtQualityCertificateDetailEntity>();
        context.getQualityFormRows().forEach(qualityFormRow -> {
            var qualityCertificateDetail = new PtQualityCertificateDetailEntity();
            if (context.getQualityCertificateId() != null) {
                qualityCertificateDetail.setId(qualityFormRow.getId());
            }
            qualityCertificateDetail.setParameterValue(qualityFormRow.getParameter());
            qualityCertificateDetail.setResultValue(qualityFormRow.getResult());
            qualityCertificateDetail.setUnitsValue(qualityFormRow.getUnits());
            qualityCertificateDetail.setMethodologyValue(qualityFormRow.getMethodology());
            qualityCertificateDetail.setSpecificationName(qualityFormRow.getSpecification());
            list.add(qualityCertificateDetail);
        });

        return list;
    }

    public static PtQualityIndicatorsEntity qualityIndicatorsMapper(ProductFinishedForm context){
        var ptQualityIndicators = new PtQualityIndicatorsEntity();
        if (context.getQualityIndicatorId() != null) {
            ptQualityIndicators.setId(context.getQualityIndicatorId());
        }
        ptQualityIndicators.setStatus(context.getIsLiberated().get());
        ptQualityIndicators.setReciptDate(context.getCreatedAt().get());
        ptQualityIndicators.setReleaseDate(LocalDate.now());
        return ptQualityIndicators;
    }

    public static PtQualitySolidLiquidEntity qualitySolidLiquidMapper(ProductFinishedForm context){
        var ptQualitySolidLiquid = new PtQualitySolidLiquidEntity();
        if (context.getQualitySolidLiquidId() != null){
            ptQualitySolidLiquid.setId(context.getQualitySolidLiquidId());
        }
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

    public static ProductFinishedForm entityToForm(ProductFinishedEntity entity){
        var form = new ProductFinishedForm();
        var qualityCertificate = entity.getQualityCertificate();
        var qualityIndicators = entity.getQualityIndicators();
        var qualitySolidLiquid = entity.getQualitySolidLiquid();

        form.setProductFinishedFormId(entity.getId());
        form.setQualityCertificateId(qualityCertificate.getId());
        form.setQualityIndicatorId(qualityIndicators.getId());
        form.setQualitySolidLiquidId(qualitySolidLiquid.getId());

        form.setFolio(new SimpleStringProperty(entity.getFolio()));
        form.setBatch(new SimpleStringProperty(entity.getBatch()));
        form.setBatch(new SimpleStringProperty(entity.getBatch()));
        form.setProduct(new SimpleStringProperty(entity.getProduct()));
        form.setCreatedAt(new SimpleObjectProperty<>(entity.getCreatedAt()));

        form.setAmount(new SimpleStringProperty(qualityCertificate.getAmount().toString()));
        form.setExpirationDate(new SimpleObjectProperty<>(qualityCertificate.getExpirationDate()));
        form.setQualityFormRows(FXCollections.observableArrayList(
                qualityCertificate.getQualityDetails().stream().map(
                        quality -> new QualityFormRow(
                                quality.getId(),
                                quality.getSpecificationName(),
                                quality.getParameterValue(),
                                quality.getResultValue(),
                                quality.getUnitsValue(),
                                quality.getMethodologyValue()
                        )
                ).toList()
        ));

        form.setIsLiberated(new SimpleBooleanProperty(qualityIndicators.isStatus()));

        form.setSolids(new SimpleStringProperty(qualitySolidLiquid.getSolids().toString()));
        form.setPh(new SimpleStringProperty(qualitySolidLiquid.getPh().toString()));
        form.setApparentDensity(new SimpleStringProperty(qualitySolidLiquid.getApparentDensity().toString()));
        form.setApparentDensity(new SimpleStringProperty(qualitySolidLiquid.getApparentDensity().toString()));
        form.setAppearance(new SimpleStringProperty(qualitySolidLiquid.getAppearance()));
        form.setZno(new SimpleStringProperty(qualitySolidLiquid.getZincOxidePercentage().toString()));
        form.setKilograms(new SimpleStringProperty(qualitySolidLiquid.getKilograms().toString()));
        form.setIdentificationReview(new SimpleBooleanProperty(qualitySolidLiquid.isIdentificationReview()));
        form.setPackagingReview(new SimpleBooleanProperty(qualitySolidLiquid.isPackagingReview()));
        form.setCertificate(new SimpleStringProperty(qualitySolidLiquid.getCertificate().toString()));


        return form;
    }

}
