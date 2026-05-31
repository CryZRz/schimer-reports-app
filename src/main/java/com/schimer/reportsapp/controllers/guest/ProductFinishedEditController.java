package com.schimer.reportsapp.controllers.guest;

import com.schimer.reportsapp.App;
import com.schimer.reportsapp.controllers.interfaces.WizardStep;
import com.schimer.reportsapp.models.ProductFinishedForm;
import com.schimer.reportsapp.utils.guest.ProductFinishedBindContext;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class ProductFinishedEditController implements WizardStep {
    @FXML
    public VBox sidebar;
    @FXML
    private SidebarProductFinishedController sidebarController;
    private ProductFinishedForm context;


    @Override
    public void setFormContext(ProductFinishedForm context) {
        this.context = context;
        sidebarController.setFormContext(context);
    }

    public void onClickFolioQuality() {
        App.setRoot(
                "views/guest/create-pt-folio-quality-certificate",
                loader -> (ProductFinishedBindContext.bindContext(loader, this.context))
        );
    }

    public void onClickQualityCertificate() {
        App.setRoot(
                "views/guest/create-pt-quality-certificate",
                loader -> (ProductFinishedBindContext.bindContext(loader, this.context))
        );
    }

    public void onClickQualityIndicators() {
        App.setRoot(
                "views/guest/create-pt-quality-indicators",
                loader -> (ProductFinishedBindContext.bindContext(loader, this.context))
        );
    }

    public void onClickQualitySolidLiquid() {
        App.setRoot(
                "views/guest/create-pt-quality-liquid",
                loader -> (ProductFinishedBindContext.bindContext(loader, this.context))
        );
    }
}
