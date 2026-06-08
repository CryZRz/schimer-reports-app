package com.schimer.reportsapp.controllers.guest.rawMaterial;

import com.schimer.reportsapp.App;
import com.schimer.reportsapp.auth.UserSession;
import com.schimer.reportsapp.controllers.guest.SectionPtInfoController;
import com.schimer.reportsapp.controllers.guest.SendAndUploadReportController;
import com.schimer.reportsapp.controllers.interfaces.WizardStepRawMaterial;
import com.schimer.reportsapp.domain.entities.rawMaterial.RawMaterialEntity;
import com.schimer.reportsapp.models.QualityFormRowMaterialRelease;
import com.schimer.reportsapp.models.RawMaterialForm;
import com.schimer.reportsapp.services.DropboxService;
import com.schimer.reportsapp.services.rawMaterial.RawMaterialService;
import com.schimer.reportsapp.ui.components.WindowsUtils;
import com.schimer.reportsapp.utils.validators.FormValidators;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import net.synedra.validatorfx.Validator;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.File;
import java.io.IOException;

public class ReleaseRawMaterialController implements WizardStepRawMaterial {
    @FXML
    public TextField transmitterProperty;
    @FXML
    public TextField transmitterJobPositionProperty;
    @FXML
    public TextField folioProperty;
    @FXML
    public TextField batchProperty;
    @FXML
    public DatePicker expirationDateProperty;
    @FXML
    public TextField productProperty;
    @FXML
    public VBox sidebar;
    @FXML
    public CheckBox acceptedProperty;
    @FXML
    public DatePicker createdAtProperty;
    @FXML
    public TextField amountProperty;
    @FXML
    public TextArea noteProperty;
    @FXML
    public TableView<QualityFormRowMaterialRelease> parametersTable;
    @FXML
    public TableColumn<QualityFormRowMaterialRelease, String> parameterColumn;
    @FXML
    public TableColumn<QualityFormRowMaterialRelease, String> specificationColumn;
    @FXML
    public TableColumn<QualityFormRowMaterialRelease, String> resultColumn;
    @FXML
    public TableColumn<QualityFormRowMaterialRelease, Void> optionsColumn;
    public Button persistButton;
    @FXML
    private SidebarRawMaterialController sidebarController;
    @FXML
    private SectionPtInfoController sectionPtInfoController;

    private RawMaterialForm context;
    private final UserSession userSession = UserSession.getInstance();
    private final RawMaterialService rawMaterialService = new RawMaterialService();
    private final Validator validator = new Validator();
    private final DropboxService dropboxService = new DropboxService();

    public void initialize() {
        initUserInfo();
        sidebarController.setFormContext(context);
        removeButton();
        initializeValidations();
    }

    private void initializeValidations() {
        FormValidators.addNotEmptyValidation(validator, amountProperty.textProperty(), amountProperty, "Cantidad");
        FormValidators.addNumericValidation(validator, amountProperty.textProperty(), amountProperty, "Cantidad");
        FormValidators.addNotEmptyValidation(validator, noteProperty.textProperty(), noteProperty, "Nota");
        FormValidators.addNotEmptyValidation(validator, acceptedProperty.textProperty(), acceptedProperty, "Aceptada");
    }

    private void removeButton() {
        sectionPtInfoController.getBtnAdd().ifPresent(button -> {
            button.setVisible(false);
        });
    }

    private void initializeTable() {
        parametersTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        parametersTable.setEditable(true);

        parameterColumn.setCellValueFactory(new PropertyValueFactory<>("parameter"));
        specificationColumn.setCellValueFactory(new PropertyValueFactory<>("specification"));
        resultColumn.setCellValueFactory(new PropertyValueFactory<>("result"));

        parameterColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        specificationColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        resultColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        parameterColumn.setOnEditCommit(event -> {
            var row = event.getRowValue();
            row.setParameter(event.getNewValue());
        });

        specificationColumn.setOnEditCommit(event -> {
            var row = event.getRowValue();
            row.setSpecification(event.getNewValue());
        });

        resultColumn.setOnEditCommit(event -> {
            var row = event.getRowValue();
            row.setResult(event.getNewValue());
        });

        setContextToTable();
        setupActionsColumn();
    }

    private void setupActionsColumn() {
        optionsColumn.setCellFactory(param -> new TableCell<>() {

            private final FontIcon deleteIcon = new FontIcon("mdi2t-trash-can");
            private final Button btnDelete = new Button();

            {
                btnDelete.getStyleClass().add("icon-button-primary");
                deleteIcon.getStyleClass().add("icon-primary");
                btnDelete.setGraphic(deleteIcon);

                btnDelete.setOnAction(event -> {
                    var parameterSelected = getTableView().getItems().get(getIndex());
                    onDeleteParameter(parameterSelected);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setAlignment(javafx.geometry.Pos.CENTER);
                    setGraphic(btnDelete);
                }
            }
        });
    }

    private void onDeleteParameter(QualityFormRowMaterialRelease row) {
        this.context.getQualityFormRows().remove(row);
    }

    private void setContextToTable() {
        parametersTable.setItems(this.context.getQualityFormRows());
    }

    public void onClickAddParameter() {
        this.context.getQualityFormRows().add(
                new QualityFormRowMaterialRelease(
                        0L,
                        "",
                        "",
                        ""
                )
        );
    }

    public void setFormContext(RawMaterialForm context) {
        this.context = context;
        bindPropertiesWithContext();
        initializeSidebar();
        initializeTable();
        initializeButton();
    }

    private void initializeButton() {
        if (context.getRawMaterialId() != null){
            persistButton.setText("Editar");
        }else {
            persistButton.setText("Crear");
        }
    }

    private void initializeSidebar() {
        sidebarController.setFormContext(context);
    }

    private void bindPropertiesWithContext(){
        folioProperty.textProperty().bindBidirectional(context.getFolio());
        batchProperty.textProperty().bindBidirectional(context.getBatch());
        createdAtProperty.valueProperty().bindBidirectional(context.getCreatedAt());
        productProperty.textProperty().bindBidirectional(context.getProduct());
        expirationDateProperty.valueProperty().bindBidirectional(context.getExpirationDate());
        amountProperty.textProperty().bindBidirectional(context.getAmount());
        noteProperty.textProperty().bindBidirectional(context.getNote());
        acceptedProperty.selectedProperty().bindBidirectional(context.getAccepted());
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

    private void onSave(){
        try{
            var entity = rawMaterialService.save(context);
            uploadFile(entity);
            App.setRoot("views/guest/send-upload-report", loader -> goToSendEmails(loader, entity));
        }catch (Exception e){
            WindowsUtils.showAlertErrorSystem();
        }
    }

    private void uploadFile(RawMaterialEntity entity){
        var path = entity.getReportPath();
        var dropboxPath = userSession.getUser().getDropboxAccount().getPath();
        var client = userSession.getClientDropbox();
        try{
            dropboxService.uploadFileToDropbox(new File(path), dropboxPath, client);
        }catch (Exception e){
            WindowsUtils.showAlertErrorSystem();
        }
    }

    private Parent goToSendEmails(FXMLLoader loader, RawMaterialEntity entity) {
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

    private void onUpdate(){
        try{
            rawMaterialService.update(context);
            onClickReports();
        }catch (Exception e){
            WindowsUtils.showAlertErrorSystem();
        }
    }

    @FXML
    public void onClickNext(){
        if (validator.validate()){
            if (context.getRawMaterialId() != null){
                onUpdate();
            }else{
                onSave();
            }
        }
    }
}
