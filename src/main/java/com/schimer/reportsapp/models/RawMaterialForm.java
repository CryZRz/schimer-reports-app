package com.schimer.reportsapp.models;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RawMaterialForm {
    private Long rawMaterialId;
    private Long rawMaterialReleaseId;

    private StringProperty folio = new SimpleStringProperty("");
    private StringProperty batch = new SimpleStringProperty("");
    private StringProperty product = new SimpleStringProperty("");
    private ObjectProperty<LocalDate> releaseDate = new SimpleObjectProperty<>(LocalDate.now());
    private BooleanProperty status = new SimpleBooleanProperty(false);

    private ObjectProperty<LocalDate> createdAt = new SimpleObjectProperty<>(LocalDate.now());
    private ObjectProperty<LocalDate> expirationDate = new SimpleObjectProperty<>();
    private StringProperty amount = new SimpleStringProperty("");
    private StringProperty note = new SimpleStringProperty("");
    private BooleanProperty accepted = new SimpleBooleanProperty(false);

    private ObservableList<QualityFormRowMaterialRelease> qualityFormRows = FXCollections.observableArrayList();
}
