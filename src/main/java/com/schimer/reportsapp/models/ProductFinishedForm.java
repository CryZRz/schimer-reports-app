package com.schimer.reportsapp.models;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ProductFinishedForm {
    private Long productFinishedFormId;
    private Long qualityCertificateId;
    private Long qualityIndicatorId;
    private Long qualitySolidLiquidId;

    private StringProperty folio = new SimpleStringProperty("");
    private StringProperty batch = new SimpleStringProperty("");
    private StringProperty product = new SimpleStringProperty("");
    private ObjectProperty<LocalDate> createdAt = new SimpleObjectProperty<>(LocalDate.now());

    private StringProperty amount = new SimpleStringProperty("");
    private ObjectProperty<LocalDate> expirationDate = new SimpleObjectProperty<>();

    private ObservableList<QualityFormRow> qualityFormRows = FXCollections.observableArrayList();
    private BooleanProperty isLiberated = new SimpleBooleanProperty(false);

    private StringProperty solids = new SimpleStringProperty("");
    private StringProperty ph = new SimpleStringProperty("");
    private StringProperty apparentDensity = new SimpleStringProperty("");
    private StringProperty appearance = new SimpleStringProperty("");
    private StringProperty zno = new SimpleStringProperty("");
    private StringProperty kilograms = new SimpleStringProperty("");
    private BooleanProperty identificationReview = new SimpleBooleanProperty(false);
    private BooleanProperty packagingReview = new SimpleBooleanProperty(false);
    private StringProperty certificate = new SimpleStringProperty("");
}































































































































































