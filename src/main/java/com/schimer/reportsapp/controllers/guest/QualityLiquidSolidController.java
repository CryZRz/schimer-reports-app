package com.schimer.reportsapp.controllers.guest;

import com.schimer.reportsapp.App;
import com.schimer.reportsapp.auth.UserSession;
import com.schimer.reportsapp.controllers.interfaces.WizardStep;
import com.schimer.reportsapp.models.ProductFinishedForm;
import com.schimer.reportsapp.services.ProductFinishedService;
import com.schimer.reportsapp.services.admin.TemplatePTService;
import com.schimer.reportsapp.ui.components.WindowsUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

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
    @FXML
    public VBox sidebar;
    @FXML
    public Button finishButton;
    @FXML
    private SidebarProductFinishedController sidebarController;
    @FXML
    private SectionPtInfoController sectionPtInfoController;

    private ProductFinishedForm context;
    private UserSession userSession ;
    private final ProductFinishedService productFinishedService = new ProductFinishedService();
    private final TemplatePTService templatePTService = new TemplatePTService();

    public void initialize() {
        userSession = UserSession.getInstance();
        initUserInfo();
        removeButton();
    }

    private void removeButton() {
        sectionPtInfoController.getBtnAdd().ifPresent(button -> {
            button.setVisible(false);
        });
    }

    private void initializeSidebar() {
        sidebarController.setFormContext(context);
    }

    @Override
    public void setFormContext(ProductFinishedForm context) {
        this.context = context;
        if (context.getProductFinishedFormId() != null) {
            finishButton.setText("Editar");
        }
        bindPropertiesWithContext();
        initializeSidebar();
    }

    private void bindPropertiesWithContext() {
        productProperty.textProperty().bindBidirectional(context.getProduct());
        batchProperty.textProperty().bindBidirectional(context.getBatch());
        solidsProperty.textProperty().bindBidirectional(context.getSolids());
        phProperty.textProperty().bindBidirectional(context.getPh());
        apparentDensityProperty.textProperty().bindBidirectional(context.getApparentDensity());
        appearanceProperty.textProperty().bindBidirectional(context.getAppearance());
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
    public void onClickReports(){
        try {
            App.setRoot("views/guest/products-finished-list");
        } catch (IOException e) {
            WindowsUtils.showAlertErrorSystem();
        }
    }

    private void saveProductFinished(){
        try {
            var entity = productFinishedService.create(context);
            templatePTService.generateReport(entity);
            onClickReports();
        }catch (Exception e){
            WindowsUtils.showAlertErrorSystem();
        }
    }

    private void updateProductFinished(){
        try {
            productFinishedService.update(context);
            onClickReports();
        }catch (Exception e){
            WindowsUtils.showAlertErrorSystem();
        }
    }

    @FXML
    public void onClickNext(){
        if (context.getQualityIndicatorId() != null){
            updateProductFinished();
        }else{
            saveProductFinished();
        }
    }
}
