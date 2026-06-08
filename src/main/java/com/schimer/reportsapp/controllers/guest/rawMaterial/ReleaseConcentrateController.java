package com.schimer.reportsapp.controllers.guest.rawMaterial;

import com.schimer.reportsapp.App;
import com.schimer.reportsapp.auth.UserSession;
import com.schimer.reportsapp.controllers.guest.SectionPtInfoController;
import com.schimer.reportsapp.controllers.interfaces.WizardStepRawMaterial;
import com.schimer.reportsapp.models.RawMaterialForm;
import com.schimer.reportsapp.ui.components.WindowsUtils;
import com.schimer.reportsapp.utils.guest.RawMaterialBindContext;
import com.schimer.reportsapp.utils.validators.FormValidators;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import net.synedra.validatorfx.Validator;

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
    @FXML
    private SectionPtInfoController sectionPtInfoController;

    private RawMaterialForm context;
    private final UserSession userSession = UserSession.getInstance();
    private final Validator validator = new Validator();

    public void initialize() {
        initUserInfo();
        sidebarController.setFormContext(context);
        removeButton();
        initializeValidations();
    }

    private void initializeValidations() {
        FormValidators.addNotEmptyValidation(validator, folioProperty.textProperty(), folioProperty, "Folio");
        FormValidators.addNotEmptyValidation(validator, batchProperty.textProperty(), batchProperty, "Lote");
        FormValidators.addNotEmptyValidation(validator, productProperty.textProperty(), productProperty, "Producto");
        FormValidators.addNotEmptyValidation(validator, releaseDateProperty.valueProperty(), releaseDateProperty, "Fecha liberacion");
        FormValidators.addNotEmptyValidation(validator, statusProperty.textProperty(), statusProperty, "Estatus");
    }

    private void removeButton() {
        sectionPtInfoController.getBtnAdd().ifPresent(button -> {
            button.setVisible(false);
        });
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
        statusProperty.selectedProperty().bindBidirectional(context.getStatus());
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
            WindowsUtils.showAlertErrorSystem();
        }
    }

    @FXML
    public void onClickNext(){
        if (validator.validate()) {
            App.setRoot(
                    "views/guest/rawMaterial/create_release_raw_material",
                    loader -> (RawMaterialBindContext.bindContext(loader, this.context))
            );
        }
    }
}
