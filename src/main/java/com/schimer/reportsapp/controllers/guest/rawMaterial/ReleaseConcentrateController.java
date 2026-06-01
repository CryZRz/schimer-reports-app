package com.schimer.reportsapp.controllers.guest.rawMaterial;

import com.schimer.reportsapp.App;
import com.schimer.reportsapp.auth.UserSession;
import com.schimer.reportsapp.controllers.interfaces.WizardStepRawMaterial;
import com.schimer.reportsapp.models.RawMaterialForm;
import com.schimer.reportsapp.utils.guest.RawMaterialBindContext;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ReleaseConcentrateController implements WizardStepRawMaterial {
    @FXML
    public TextField transmitterProperty;
    @FXML
    public TextField transmitterJobPositionProperty;
    @FXML
    public TextField folioProperty;
    @FXML
    public TextField batchProperty;
    @FXML
    public DatePicker releaseDateProperty;
    @FXML
    public TextField productProperty;
    @FXML
    public VBox sidebar;
    @FXML
    public CheckBox statusProperty;
    @FXML
    private SidebarRawMaterialController sidebarController;

    private RawMaterialForm context;
    private final UserSession userSession = UserSession.getInstance();

    public void initialize() {
        initUserInfo();
        sidebarController.setFormContext(context);
    }

    public void setFormContext(RawMaterialForm context) {
        this.context = context;
        bindPropertiesWithContext();
        initializeSidebar();
    }

    private void initializeSidebar() {
        sidebarController.setFormContext(context);
    }

    private void bindPropertiesWithContext(){
        folioProperty.textProperty().bindBidirectional(context.getFolio());
        batchProperty.textProperty().bindBidirectional(context.getBatch());
        releaseDateProperty.valueProperty().bindBidirectional(context.getCreatedAt());
        productProperty.textProperty().bindBidirectional(context.getProduct());
    }

    private void initUserInfo() {
        var user = userSession.getUser();
        transmitterProperty.setText(user.getName()+" " +user.getLastName());
        transmitterJobPositionProperty.setText(user.getJobPosition());
    }

    @FXML
    public void onClickReports(){
        try {
            App.setRoot("views/guest/rawMaterial/index");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onClickNext(){
        App.setRoot(
                "views/guest/rawMaterial/create_release_raw_material",
                loader -> (RawMaterialBindContext.bindContext(loader, this.context))
        );
    }
}
