package com.schimer.reportsapp.controllers.guest;

import com.schimer.reportsapp.App;
import com.schimer.reportsapp.auth.UserSession;
import com.schimer.reportsapp.controllers.interfaces.WizardStep;
import com.schimer.reportsapp.models.ProductFinishedForm;
import com.schimer.reportsapp.utils.guest.ProductFinishedBindContext;
import javafx.fxml.FXML;

import java.io.IOException;

public class SidebarProductFinishedController implements WizardStep {
    private ProductFinishedForm context;

    @Override
    public void setFormContext(ProductFinishedForm context) {
        this.context = context;
    }

    public void onClickReports(){
        try {
            App.setRoot("views/guest/products-finished-list");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    @FXML
    public void onClickLogout() throws IOException {
        UserSession.logout();
        App.setRoot("views/auth/login");
    }
}
