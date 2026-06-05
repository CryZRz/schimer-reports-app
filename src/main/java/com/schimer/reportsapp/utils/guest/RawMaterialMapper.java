package com.schimer.reportsapp.utils.guest;

import com.schimer.reportsapp.domain.entities.rawMaterial.QualityParameterRawMaterialRelease;
import com.schimer.reportsapp.domain.entities.rawMaterial.RawMaterialEntity;
import com.schimer.reportsapp.domain.entities.rawMaterial.RawMaterialReleaseEntity;
import com.schimer.reportsapp.models.QualityFormRowMaterialRelease;
import com.schimer.reportsapp.models.RawMaterialForm;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;

import java.util.ArrayList;
import java.util.List;

public class RawMaterialMapper {

    public static RawMaterialEntity toEntity(RawMaterialForm context) {
        var rawMaterialEntity = new RawMaterialEntity();
        if (context.getRawMaterialId() != null) {
            rawMaterialEntity.setId(context.getRawMaterialId());
        }
        rawMaterialEntity.setProduct(context.getProduct().get());
        rawMaterialEntity.setBatch(context.getBatch().get());
        rawMaterialEntity.setReleaseDate(context.getReleaseDate().get());
        rawMaterialEntity.setStatus(context.getStatus().get());
        rawMaterialEntity.setFolio(context.getFolio().get());

        return rawMaterialEntity;
    }

    public static RawMaterialReleaseEntity rawMaterialReleaseToEntity(RawMaterialForm context) {
        var rawMaterialReleaseEntity = new RawMaterialReleaseEntity();
        if (context.getRawMaterialReleaseId() != null) {
            rawMaterialReleaseEntity.setId(context.getRawMaterialReleaseId());
        }
        rawMaterialReleaseEntity.setExpirationDate(context.getExpirationDate().get());
        rawMaterialReleaseEntity.setAmount(Integer.parseInt(context.getAmount().get()));
        rawMaterialReleaseEntity.setNote(context.getNote().get());
        rawMaterialReleaseEntity.setAccepted(context.getAccepted().get());

        return rawMaterialReleaseEntity;
    }

    public static List<QualityParameterRawMaterialRelease> parameterRawMaterialToEntity(List<QualityFormRowMaterialRelease> context) {
        var listParametersRawMaterialRelease = new ArrayList<QualityParameterRawMaterialRelease>();
        context.forEach(row -> {
            var qualityParameterRawMaterialRelease = new QualityParameterRawMaterialRelease();
            if (row.getId() != null && row.getId() != 0) {
                qualityParameterRawMaterialRelease.setId(row.getId());
            }
            qualityParameterRawMaterialRelease.setParameter(row.getParameter());
            qualityParameterRawMaterialRelease.setResult(row.getResult());
            qualityParameterRawMaterialRelease.setSpecification(row.getSpecification());
            listParametersRawMaterialRelease.add(qualityParameterRawMaterialRelease);
        });

        return listParametersRawMaterialRelease;
    }

    public static RawMaterialForm entityToForm(RawMaterialEntity productFinishedEntity) {
        var rawMaterialForm = new RawMaterialForm();
        rawMaterialForm.setRawMaterialId(productFinishedEntity.getId());
        rawMaterialForm.setRawMaterialReleaseId(productFinishedEntity.getRawMaterialRelease().getId());

        rawMaterialForm.setBatch(new SimpleStringProperty(productFinishedEntity.getBatch()));
        rawMaterialForm.setReleaseDate(new SimpleObjectProperty<>(productFinishedEntity.getReleaseDate()));
        rawMaterialForm.setProduct(new SimpleStringProperty(productFinishedEntity.getProduct()));
        rawMaterialForm.setStatus(new SimpleBooleanProperty(productFinishedEntity.isStatus()));
        rawMaterialForm.setFolio(new SimpleStringProperty(productFinishedEntity.getFolio()));

        rawMaterialForm.setExpirationDate(new SimpleObjectProperty<>(productFinishedEntity.getRawMaterialRelease().getExpirationDate()));
        rawMaterialForm.setAmount(new SimpleStringProperty(productFinishedEntity.getRawMaterialRelease().getAmount().toString()));
        rawMaterialForm.setNote(new SimpleStringProperty(productFinishedEntity.getRawMaterialRelease().getNote()));
        rawMaterialForm.setAccepted(new SimpleBooleanProperty(productFinishedEntity.getRawMaterialRelease().isAccepted()));

        var parametersRawMaterial = productFinishedEntity.getRawMaterialRelease().getQualityParameters().stream().map(parameter ->
                new QualityFormRowMaterialRelease(
                parameter.getId(),
                parameter.getParameter(),
                parameter.getSpecification(),
                parameter.getResult()
        )).toList();

        rawMaterialForm.setQualityFormRows(FXCollections.observableArrayList(parametersRawMaterial));

        return rawMaterialForm;
    }
}
