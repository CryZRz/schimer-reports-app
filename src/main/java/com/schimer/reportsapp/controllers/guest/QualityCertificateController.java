package com.schimer.reportsapp.controllers.guest;

import com.schimer.reportsapp.App;
import com.schimer.reportsapp.auth.UserSession;
import com.schimer.reportsapp.controllers.interfaces.WizardStep;
import com.schimer.reportsapp.domain.repositories.UserRepository;
import com.schimer.reportsapp.models.ProductFinishedForm;
import com.schimer.reportsapp.models.QualityFormRow;
import com.schimer.reportsapp.utils.guest.ProductFinishedBindContext;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;


public class QualityCertificateController implements WizardStep {

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
    public TextField amountProperty;
    public TableView<QualityFormRow> qualityTable;
    public TableColumn<QualityFormRow, String> specificationColumn;
    public TableColumn<QualityFormRow, String> parameterColumn;
    public TableColumn<QualityFormRow, String> resultColumn;
    public TableColumn<QualityFormRow, String> unitsColumn;
    public TableColumn<QualityFormRow, String> methodologyColumn;

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
        initializeTable();
    }

    private void bindPropertiesWithContext(){
        folioProperty.textProperty().bindBidirectional(context.getFolio());
        batchProperty.textProperty().bindBidirectional(context.getBatch());
        expirationDateProperty.valueProperty().bindBidirectional(context.getExpirationDate());
        amountProperty.textProperty().bindBidirectional(context.getAmount());
    }

    private void initUserInfo() {
        var user = userSession.getUser();
        transmitterProperty.setText(user.getName()+" " +user.getLastName());
        transmitterJobPositionProperty.setText(user.getJobPosition());
    }

    private void initializeTable(){
        qualityTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        qualityTable.setEditable(true);

        specificationColumn.setCellValueFactory(new PropertyValueFactory<>("specification"));
        parameterColumn.setCellValueFactory(new PropertyValueFactory<>("parameter"));
        unitsColumn.setCellValueFactory(new PropertyValueFactory<>("units"));
        methodologyColumn.setCellValueFactory(new PropertyValueFactory<>("methodology"));
        resultColumn.setCellValueFactory(new PropertyValueFactory<>("result"));

        parameterColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        resultColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        parameterColumn.setOnEditCommit(event -> {
            var row = event.getRowValue();
            row.setParameter(event.getNewValue());
        });

        resultColumn.setOnEditCommit(event -> {
            var row = event.getRowValue();
            row.setResult(event.getNewValue());
        });

        setContextToTable();
    }

    private void setContextToTable() {
        if (context != null && context.getQualityFormRows().isEmpty()) {
            this.context.getQualityFormRows().addAll(
                    new QualityFormRow("Apariencia", "", "", "-", "Interno"),
                    new QualityFormRow("Apariencia", "", "", "-", "Interno"),
                    new QualityFormRow("Olor", "", "", "-", "ASTM D1296"),
                    new QualityFormRow("Densidad", "", "", "g/cm3", "ASTM B527-15 S) ISO 7581976 I)"),
                    new QualityFormRow("pH", "", "", "-", "ASTM E70"),
                    new QualityFormRow("%Solidos@100°C", "", "", "%", "ASTM D2974"),
                    new QualityFormRow("%Humedad@100°C", "", "", "%", "ASTM D2974"),
                    new QualityFormRow("Solubilidad", "", "", "-", "ASTM D1722"),
                    new QualityFormRow("Punto de fusion", "", "", "°C", "ASTM E324"),
                    new QualityFormRow("Concentracion", "", "", "%", "ISO 1388"),
                    new QualityFormRow("Viscosidad", "", "", "cP", "ISO D2196"),
                    new QualityFormRow("°Brix", "", "", "°Brix", "ISO E108"),
                    new QualityFormRow("Pellet/gramo", "", "", "psz", "Interno")
            );
        }

        qualityTable.setItems(this.context.getQualityFormRows());
    }

    @FXML
    public void onClickNext(){
        App.setRoot(
                "views/guest/create-pt-quality-indicators",
                loader -> (ProductFinishedBindContext.bindContext(loader, this.context))
        );
    }
}
