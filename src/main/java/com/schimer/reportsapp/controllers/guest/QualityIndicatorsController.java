package com.schimer.reportsapp.controllers.guest;

import com.schimer.reportsapp.App;
import com.schimer.reportsapp.auth.UserSession;
import com.schimer.reportsapp.controllers.interfaces.WizardStep;
import com.schimer.reportsapp.models.ProductFinishedForm;
import com.schimer.reportsapp.utils.guest.ProductFinishedBindContext;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class QualityIndicatorsController implements WizardStep {

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
    public CheckBox isLiberated;
    @FXML
    public VBox sidebar;
    @FXML
    private SidebarProductFinishedController sidebarController;

    private ProductFinishedForm context;
    private UserSession userSession;

    public void initialize() {
        userSession = UserSession.getInstance();
        initUserInfo();
    }

    private void initializeSidebar() {
        sidebarController.setFormContext(context);
    }

    @Override
    public void setFormContext(ProductFinishedForm context) {
        this.context = context;
        bindPropertiesWithContext();
        initializeSidebar();
    }

    private void bindPropertiesWithContext() {
        folioProperty.textProperty().bindBidirectional(context.getFolio());
        batchProperty.textProperty().bindBidirectional(context.getBatch());
        createdAtProperty.valueProperty().bindBidirectional(context.getCreatedAt());
        productProperty.textProperty().bindBidirectional(context.getProduct());
        isLiberated.selectedProperty().bindBidirectional(context.getIsLiberated());
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
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onClickNext(){
        App.setRoot(
                "views/guest/create-pt-quality-liquid",
                loader -> (ProductFinishedBindContext.bindContext(loader, this.context))
        );
    }
}
