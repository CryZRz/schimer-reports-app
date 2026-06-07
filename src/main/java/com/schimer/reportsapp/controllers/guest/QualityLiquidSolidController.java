package com.schimer.reportsapp.controllers.guest;

import com.schimer.reportsapp.App;
import com.schimer.reportsapp.auth.UserSession;
import com.schimer.reportsapp.controllers.interfaces.WizardStep;
import com.schimer.reportsapp.domain.entities.ProductFinishedEntity;
import com.schimer.reportsapp.domain.entities.rawMaterial.RawMaterialEntity;
import com.schimer.reportsapp.models.ProductFinishedForm;
import com.schimer.reportsapp.services.ProductFinishedService;
import com.schimer.reportsapp.services.admin.TemplatePTService;
import com.schimer.reportsapp.ui.components.WindowsUtils;
import com.schimer.reportsapp.utils.validators.FormValidators;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import net.synedra.validatorfx.Validator;

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
    private final Validator validator = new Validator();

    public void initialize() {
        userSession = UserSession.getInstance();
        initUserInfo();
        removeButton();
        initializeValidations();
    }

    private void initializeValidations() {
        FormValidators.addNotEmptyValidation(validator, solidsProperty.textProperty(), solidsProperty, "Solidos");
        FormValidators.addNumericValidation(validator, solidsProperty.textProperty(), solidsProperty, "Solidos");

        FormValidators.addNotEmptyValidation(validator, solidsProperty.textProperty(), solidsProperty, "Ph");
        FormValidators.addNumericValidation(validator, phProperty.textProperty(), phProperty, "Ph");

        FormValidators.addNotEmptyValidation(validator, apparentDensityProperty.textProperty(), apparentDensityProperty, "Densidad aparente");
        FormValidators.addNumericValidation(validator, apparentDensityProperty.textProperty(), apparentDensityProperty, "Densidad aparente");

        FormValidators.addNotEmptyValidation(validator, appearanceProperty.textProperty(), appearanceProperty, "Apariencia");

        FormValidators.addNotEmptyValidation(validator, znoProperty.textProperty(), znoProperty, "%Zno");
        FormValidators.addNumericValidation(validator, znoProperty.textProperty(), znoProperty, "%Zno");

        FormValidators.addNotEmptyValidation(validator, kilogramsProperty.textProperty(), kilogramsProperty, "Kilogramos");
        FormValidators.addNumericValidation(validator, kilogramsProperty.textProperty(), kilogramsProperty, "Kilogramos");

        FormValidators.addNotEmptyValidation(validator, packagingReviewProperty.textProperty(), packagingReviewProperty, "Revision de indentificacion");
        FormValidators.addNotEmptyValidation(validator, identificationReviewProperty.textProperty(), identificationReviewProperty, "Revision de emapque");

        FormValidators.addNotEmptyValidation(validator, certificateProperty.textProperty(), certificateProperty, "Revision de emapque");
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
            App.setRoot("views/guest/send-upload-report", loader -> goToSendEmails(loader, entity));
        }catch (Exception e){
            e.printStackTrace();
            WindowsUtils.showAlertErrorSystem();
        }
    }

    private Parent goToSendEmails(FXMLLoader loader, ProductFinishedEntity entity) {
        try{
            var parent =  (Parent)loader.load();
            var controller = (SendAndUploadReportController)loader.getController();
            controller.setReportPath(entity.getReportPath());
            return parent;
        }catch (Exception e){
            WindowsUtils.showAlertErrorSystem();
        }
        return null;
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
        if (validator.validate()) {
            if (context.getQualityIndicatorId() != null){
                updateProductFinished();
            }else{
                saveProductFinished();
            }
        }
    }
}
