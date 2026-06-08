package com.schimer.reportsapp.controllers.guest;

import com.schimer.reportsapp.App;
import com.schimer.reportsapp.auth.UserSession;
import com.schimer.reportsapp.models.EmailUploadForm;
import com.schimer.reportsapp.services.email.EmailService;
import com.schimer.reportsapp.ui.components.WindowsUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import lombok.Setter;
import net.synedra.validatorfx.Validator;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.File;
import java.io.IOException;

public class SendAndUploadReportController {
    @FXML
    public VBox sectionPtInfo;
    @FXML
    public TableView<EmailUploadForm> emailsTable;
    @FXML
    public TableColumn<EmailUploadForm, String> emailColumn;
    @FXML
    public TableColumn<EmailUploadForm, String> affairColumn;
    @FXML
    public TableColumn<EmailUploadForm, String> bodyColumn;
    @FXML
    public TableColumn<EmailUploadForm, Void> actionsColumn;

    private final UserSession session =  UserSession.getInstance();
    private final EmailService emailService = new EmailService();
    private final ObservableList<EmailUploadForm> emailsList = FXCollections.observableArrayList();
    private final Validator validator = new Validator();

    @Setter
    private String reportPath;

    public void initialize() {
        initializeTable();
        initializeValidations();
    }

    private void initializeValidations() {
        validator.createCheck()
                .dependsOn("list", emailsTable.itemsProperty())
                .withMethod(context -> {
                    ObservableList<EmailUploadForm> list = context.get("list");


                    if (list == null || list.isEmpty()) {
                        context.error("La lista de envíos no puede estar vacía.");
                        return;
                    }

                    for (int i = 0; i < list.size(); i++) {
                        EmailUploadForm form = list.get(i);
                        int fila = i + 1;


                        if (form.getEmail() == null || form.getEmail().get().isEmpty()) {
                            context.error("Fila " + fila + ": El correo electrónico es obligatorio.");
                        } else if (!form.getEmail().get().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                            context.error("Fila " + fila + ": El formato del correo '" + form.getEmail() + "' no es válido.");
                        }

                        if (form.getAffair() == null || form.getAffair().get().isEmpty()) {
                            context.error("Fila " + fila + ": El asunto (affair) no puede estar vacío.");
                        }

                        if (form.getBody() == null || form.getBody().get().isEmpty()) {
                            context.error("Fila " + fila + ": El cuerpo del mensaje no puede estar vacío.");
                        }
                    }
                })
                .decorates(emailsTable);
    }

    private void initializeTable() {
        emailsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        emailsTable.setEditable(true);

        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        affairColumn.setCellValueFactory(new PropertyValueFactory<>("affair"));
        bodyColumn.setCellValueFactory(new PropertyValueFactory<>("body"));

        emailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        affairColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        bodyColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        emailColumn.setOnEditCommit(event -> {
            var row = event.getRowValue();
            row.setEmail(new SimpleStringProperty(event.getNewValue()));
        });

        affairColumn.setOnEditCommit(event -> {
            var row = event.getRowValue();
            row.setAffair(new SimpleStringProperty(event.getNewValue()));
        });

        bodyColumn.setOnEditCommit(event -> {
            var row = event.getRowValue();
            row.setBody(new SimpleStringProperty(event.getNewValue()));
        });

        emailsTable.setItems(emailsList);
        setupActionsColumn();
    }

    private void setupActionsColumn() {
        actionsColumn.setCellFactory(param -> new TableCell<>() {

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

    private void onDeleteParameter(EmailUploadForm parameterSelected) {
        this.emailsList.remove(parameterSelected);
    }

    public void onClickAddEmail(){
        this.emailsList.add(new EmailUploadForm());
    }

    public void onSendEmails() {
        if (!validator.validate()) return;

        try{
            for (var email : this.emailsList) {
                emailService.sendReport(
                        email.getEmail().get(),
                        email.getAffair().get(),
                        email.getBody().get(),
                        session.getUser().getEmailAccount(),
                        new File(reportPath)
                );
            }
            onCancel();
        }catch (Exception e){
            WindowsUtils.showWindowError(e.getMessage());
        }
    }

    public void onCancel() {
        try {
            App.setRoot("views/guest/rawMaterial/index");
        } catch (IOException e) {
            WindowsUtils.showAlertErrorSystem();
        }
    }
}
