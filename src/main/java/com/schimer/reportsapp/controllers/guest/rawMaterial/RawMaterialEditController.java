package com.schimer.reportsapp.controllers.guest.rawMaterial;

import com.schimer.reportsapp.App;
import com.schimer.reportsapp.controllers.interfaces.WizardStepRawMaterial;
import com.schimer.reportsapp.models.RawMaterialForm;
import com.schimer.reportsapp.utils.guest.RawMaterialBindContext;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class RawMaterialEditController implements WizardStepRawMaterial {
    @FXML
    public VBox sectionPtInfo;
    @FXML
    private SidebarRawMaterialController sidebarController;
    private RawMaterialForm context;

    private void initializeSidebar() {
        sidebarController.setFormContext(context);
    }

    @Override
    public void setFormContext(RawMaterialForm context) {
        this.context = context;
        initializeSidebar();
    }

    public void onClickReleaseConcentrate() {
        App.setRoot(
                "views/guest/rawMaterial/create_release_concentrate",
                loader -> (RawMaterialBindContext.bindContext(loader, this.context))
        );

    }

    public void onClickReleaseRawMateria() {
        App.setRoot(
                "views/guest/rawMaterial/create_release_raw_material",
                loader -> (RawMaterialBindContext.bindContext(loader, this.context))
        );
    }
}
