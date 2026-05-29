package com.schimer.reportsapp.controllers.guest;

import com.schimer.reportsapp.App;
import com.schimer.reportsapp.models.ProductFinishedForm;
import com.schimer.reportsapp.utils.guest.ProductFinishedBindContext;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class ListProductsFinishedController {
    @FXML
    private VBox sectionPtInfo;
    @FXML
    private SectionPtInfoController sectionPtInfoController;

    public void initialize(){
        initializeButtonAdd();
    }

    private void initializeButtonAdd() {
        var button = sectionPtInfoController.getBtnAdd();
        button.ifPresent(value -> value.setOnAction(this::onClickAddReport));
    }

    @FXML
    private void onClickAddReport(ActionEvent event) {
        App.setRoot(
                "views/guest/create-pt-folio-quality-certificate",
                loader -> ((ProductFinishedBindContext.bindContext(loader, new ProductFinishedForm())))
        );
    }

}
