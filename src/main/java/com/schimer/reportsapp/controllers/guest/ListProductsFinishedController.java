package com.schimer.reportsapp.controllers.guest;

import com.schimer.reportsapp.App;
import com.schimer.reportsapp.controllers.components.SidebarGuest;
import com.schimer.reportsapp.domain.entities.ProductFinishedEntity;
import com.schimer.reportsapp.models.ProductFinishedForm;
import com.schimer.reportsapp.services.ProductFinishedService;
import com.schimer.reportsapp.services.admin.TemplatePTService;
import com.schimer.reportsapp.ui.components.WindowsUtils;
import com.schimer.reportsapp.utils.guest.ProductFinishedBindContext;
import com.schimer.reportsapp.utils.guest.ProductFinishedMapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

public class ListProductsFinishedController {

    @FXML
    public VBox sectionPtInfo;
    @FXML
    public VBox sidebar;
    @FXML
    private SidebarGuest sidebarGuest;
    @FXML
    private SectionPtInfoController sectionPtInfoController;
    @FXML
    public TableView<ProductFinishedEntity> productsFinishedTable;
    @FXML
    public TableColumn<ProductFinishedEntity, String> folioColumn;
    @FXML
    public TableColumn<ProductFinishedEntity, String> transmitterColumn;
    @FXML
    public TableColumn<ProductFinishedEntity, String> productColumn;
    @FXML
    public TableColumn<ProductFinishedEntity, String> batchColumn;
    @FXML
    public TableColumn<ProductFinishedEntity, Void> actionsColumn;

    private final TemplatePTService templatePTService = new TemplatePTService();
    private final ProductFinishedService productFinishedService = new ProductFinishedService();
    private final ObservableList<ProductFinishedEntity> productsFinished = FXCollections.observableArrayList();

    public void initialize(){
        initializeButtonAdd();
        initializeTable();
    }

    private void initializeTable() {
        productsFinishedTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        folioColumn.setCellValueFactory(new PropertyValueFactory<>("batch"));
        transmitterColumn.setCellValueFactory(cellData -> {
            var name = cellData.getValue().getUser().getName();
            return new SimpleStringProperty(name);
        });
        productColumn.setCellValueFactory(new PropertyValueFactory<>("product"));
        batchColumn.setCellValueFactory(new PropertyValueFactory<>("batch"));

        productsFinishedTable.setItems(productsFinished);
        productsFinished.addAll(productFinishedService.getAll());
        setupActionsColumn();
    }

    private void setupActionsColumn() {
        actionsColumn.setCellFactory(param -> new TableCell<>() {

            private final FontIcon deleteIcon = new FontIcon("mdi2p-pencil");
            private final FontIcon viewIcon = new FontIcon("mdi2m-menu");
            private final Button btnEdit = new Button();
            private final Button btnView = new Button();
            private final HBox container = new HBox(8);

            {
                btnEdit.getStyleClass().add("icon-button-primary");
                deleteIcon.getStyleClass().add("icon-primary");
                btnEdit.setGraphic(deleteIcon);

                btnView.getStyleClass().add("icon-button-primary");
                viewIcon.getStyleClass().add("icon-primary");
                btnView.setGraphic(viewIcon);

                btnEdit.setOnAction(event -> {
                    var productFinished = getTableView().getItems().get(getIndex());
                    onClickEditReport(productFinished);
                });

                btnView.setOnAction(event -> {
                    var productFinished = getTableView().getItems().get(getIndex());
                    onClickViewReport(productFinished);
                });

                container.setAlignment(Pos.CENTER);
                container.getChildren().addAll(btnView, btnEdit);
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setAlignment(javafx.geometry.Pos.CENTER);
                    setGraphic(container);
                }
            }
        });
    }

    private void onClickViewReport(ProductFinishedEntity productFinishedEntity){
        try{
            App.setRoot("views/guest/view-report");
        }catch (Exception e){
            WindowsUtils.showAlertErrorSystem();
        }
    }

    private void initializeButtonAdd() {
        var button = sectionPtInfoController.getBtnAdd();
        button.ifPresent(value -> value.setOnAction(this::onClickAddReport));
    }

    private void onClickEditReport(ProductFinishedEntity productFinishedEntity) {
        var form = ProductFinishedMapper.entityToForm(productFinishedEntity);
        App.setRoot(
                "views/guest/product-finished-edit",
                loader -> ((ProductFinishedBindContext.bindContext(loader, form)))
        );
    }

    @FXML
    private void onClickAddReport(ActionEvent event) {
        App.setRoot(
                "views/guest/create-pt-folio-quality-certificate",
                loader -> ((ProductFinishedBindContext.bindContext(loader, new ProductFinishedForm())))
        );
    }

}
