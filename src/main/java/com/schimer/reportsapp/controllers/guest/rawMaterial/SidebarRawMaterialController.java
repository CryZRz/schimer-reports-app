package com.schimer.reportsapp.controllers.guest.rawMaterial;

import com.schimer.reportsapp.App;
import com.schimer.reportsapp.auth.UserSession;
import com.schimer.reportsapp.controllers.interfaces.WizardStepRawMaterial;
import com.schimer.reportsapp.models.RawMaterialForm;
import com.schimer.reportsapp.utils.guest.RawMaterialBindContext;
import javafx.fxml.FXML;

import java.io.IOException;

public class SidebarRawMaterialController implements WizardStepRawMaterial {
    private RawMaterialForm context;

    @Override
    public void setFormContext(RawMaterialForm context) {
        this.context = context;
    }

    public void onClickReports(){
        try {
            App.setRoot("views/guest/rawMaterial/index");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onClickConcentrate() {
        App.setRoot(
                "views/guest/rawMaterial/create_release_concentrate",
                loader -> (RawMaterialBindContext.bindContext(loader, this.context))
        );
    }

    public void onClickLiberation() {
        App.setRoot(
                "views/guest/rawMaterial/create_release_raw_material",
                loader -> (RawMaterialBindContext.bindContext(loader, this.context))
        );
    }

    @FXML
    public void onClickLogout() throws IOException {
        UserSession.logout();
        App.setRoot("views/auth/login");
    }
}
