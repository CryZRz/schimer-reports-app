package com.schimer.reportsapp.controllers.guest;

import com.schimer.reportsapp.App;
import com.schimer.reportsapp.auth.UserSession;
import com.schimer.reportsapp.controllers.interfaces.WizardStep;
import com.schimer.reportsapp.domain.repositories.UserRepository;
import com.schimer.reportsapp.models.ProductFinishedForm;
import com.schimer.reportsapp.utils.guest.ProductFinishedBindContext;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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

    private ProductFinishedForm context;
    private UserSession userSession ;
    private UserRepository userService = new UserRepository();

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
    public void onClickNext(){
        App.setRoot(
                "views/guest/create-pt-quality-liquid",
                loader -> (ProductFinishedBindContext.bindContext(loader, this.context))
        );
    }
}
