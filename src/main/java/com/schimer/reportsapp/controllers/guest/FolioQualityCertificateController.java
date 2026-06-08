package com.schimer.reportsapp.controllers.guest;

import com.schimer.reportsapp.App;
import com.schimer.reportsapp.auth.UserSession;
import com.schimer.reportsapp.controllers.interfaces.WizardStep;
import com.schimer.reportsapp.models.ProductFinishedForm;
import com.schimer.reportsapp.ui.components.WindowsUtils;
import com.schimer.reportsapp.utils.guest.ProductFinishedBindContext;
import com.schimer.reportsapp.utils.validators.FormValidators;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import net.synedra.validatorfx.Validator;

import java.io.IOException;

public class FolioQualityCertificateController implements WizardStep {
    @FXML
    public TextField transmitterProperty;
    @FXML
    public TextField transmitterJobPositionProperty;
    @FXML
    public TextField folioProperty;
    @FXML
    public TextField batchProperty;
    @FXML
    public DatePicker createdAtProperty;
    @FXML
    public TextField productProperty;
    @FXML
    public VBox sidebar;
    @FXML
    private SidebarProductFinishedController sidebarController;
    @FXML
    private SectionPtInfoController sectionPtInfoController;

    private ProductFinishedForm context;
    private final UserSession userSession = UserSession.getInstance();
    private final Validator validator = new Validator();

    public void initialize() {
        initUserInfo();
        sidebarController.setFormContext(context);
        removeButton();
        initializeValidations();
    }

    private void initializeValidations() {
        FormValidators.addNotEmptyValidation(validator, batchProperty.textProperty(), batchProperty, "Lote");
        FormValidators.addNotEmptyValidation(validator, folioProperty.textProperty(), folioProperty, "Folio");
        FormValidators.addNotEmptyValidation(validator, productProperty.textProperty(), productProperty, "Producto");
    }

    private void removeButton() {
        sectionPtInfoController.getBtnAdd().ifPresent(button -> {
           button.setVisible(false);
        });
    }

    public void setFormContext(ProductFinishedForm context) {
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
        createdAtProperty.valueProperty().bindBidirectional(context.getCreatedAt());
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
            App.setRoot("views/guest/products-finished-list");
        } catch (IOException e) {
            WindowsUtils.showAlertErrorSystem();
        }
    }

    @FXML
    public void onClickNext(){
        if (validator.validate()) {
            App.setRoot(
                    "views/guest/create-pt-quality-certificate",
                    loader -> (ProductFinishedBindContext.bindContext(loader, this.context))
            );
        }
    }
}
