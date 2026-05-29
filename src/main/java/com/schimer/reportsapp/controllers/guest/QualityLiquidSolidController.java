package com.schimer.reportsapp.controllers.guest;

import com.schimer.reportsapp.App;
import com.schimer.reportsapp.auth.UserSession;
import com.schimer.reportsapp.controllers.interfaces.WizardStep;
import com.schimer.reportsapp.domain.repositories.UserRepository;
import com.schimer.reportsapp.models.ProductFinishedForm;
import com.schimer.reportsapp.services.ProductFinishedService;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.io.IOException;

public class QualityLiquidSolidController implements WizardStep {

    @FXML
    public TextField transmitterProperty;
    @FXML
    public TextField transmitterJobPositionProperty;
    @FXML
    public TextField batchProperty;
    @FXML
    public TextField productProperty;
    @FXML
    public TextField solidsProperty;
    @FXML
    public TextField phProperty;
    @FXML
    public TextField apparentDensityProperty;
    @FXML
    public TextField appearanceProperty;
    @FXML
    public TextField znoProperty;
    @FXML
    public TextField kilogramsProperty;
    @FXML
    public CheckBox identificationReviewProperty;
    @FXML
    public CheckBox packagingReviewProperty;
    @FXML
    public TextField certificateProperty;

    private ProductFinishedForm context;
    private UserSession userSession ;
    private UserRepository userService = new UserRepository();
    private final ProductFinishedService productFinishedService = new ProductFinishedService();

    public void initialize() {
        var user = userService.getUserByEmail("paulo@itl.com").get();
        UserSession.login(user);
        userSession = UserSession.getInstance();
        initUserInfo();
    }

    @Override
    public void setFormContext(ProductFinishedForm context) {
        this.context = context;
        bindPropertiesWithContext();
    }

    private void bindPropertiesWithContext() {
        batchProperty.textProperty().bindBidirectional(context.getBatch());
        solidsProperty.textProperty().bindBidirectional(context.getSolids());
        phProperty.textProperty().bindBidirectional(context.getPh());
        apparentDensityProperty.textProperty().bindBidirectional(context.getApparentDensity());
        znoProperty.textProperty().bindBidirectional(context.getZno());
        kilogramsProperty.textProperty().bindBidirectional(context.getKilograms());
        identificationReviewProperty.selectedProperty().bindBidirectional(context.getIdentificationReview());
        packagingReviewProperty.selectedProperty().bindBidirectional(context.getPackagingReview());
        certificateProperty.textProperty().bindBidirectional(context.getCertificate());
    }

    private void initUserInfo() {
        var user = userSession.getUser();
        transmitterProperty.setText(user.getName()+" " +user.getLastName());
        transmitterJobPositionProperty.setText(user.getJobPosition());
    }

    @FXML
    public void onClickNext(){
        try {
            productFinishedService.create(context);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
