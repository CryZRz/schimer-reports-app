package com.schimer.reportsapp.controllers.guest.rawMaterial;

import com.schimer.reportsapp.App;
import com.schimer.reportsapp.controllers.components.SidebarGuest;
import com.schimer.reportsapp.controllers.guest.SectionPtInfoController;
import com.schimer.reportsapp.domain.entities.rawMaterial.RawMaterialEntity;
import com.schimer.reportsapp.models.RawMaterialForm;
import com.schimer.reportsapp.services.rawMaterial.RawMaterialService;
import com.schimer.reportsapp.utils.guest.RawMaterialBindContext;
import com.schimer.reportsapp.utils.guest.RawMaterialMapper;
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

public class ListRawMaterialsController {

    @FXML
    public VBox sectionPtInfo;
    @FXML
    public VBox sidebar;
    @FXML
    private SidebarGuest sidebarGuest;
    @FXML
    private SectionPtInfoController sectionPtInfoController;
    @FXML
    public TableView<RawMaterialEntity> productsFinishedTable;
    @FXML
    public TableColumn<RawMaterialEntity, String> folioColumn;
    @FXML
    public TableColumn<RawMaterialEntity, String> transmitterColumn;
    @FXML
    public TableColumn<RawMaterialEntity, String> productColumn;
    @FXML
    public TableColumn<RawMaterialEntity, String> batchColumn;
    @FXML
    public TableColumn<RawMaterialEntity, Void> actionsColumn;

    private final RawMaterialService rawMaterialService = new RawMaterialService();
    private final ObservableList<RawMaterialEntity> productsFinished = FXCollections.observableArrayList();

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
        productsFinished.addAll(rawMaterialService.getAll());
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
                    var rawMaterial = getTableView().getItems().get(getIndex());
                    onClickEditReport(rawMaterial);
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
                    setAlignment(Pos.CENTER);
                    setGraphic(container);
                }
            }
        });
    }

    private void initializeButtonAdd() {
        var button = sectionPtInfoController.getBtnAdd();
        button.ifPresent(value -> value.setOnAction(this::onClickAddReport));
    }

    private void onClickEditReport(RawMaterialEntity productFinishedEntity) {
        var form = RawMaterialMapper.entityToForm(productFinishedEntity);
        App.setRoot(
                "views/guest/rawMaterial/edit",
                loader -> ((RawMaterialBindContext.bindContext(loader, form)))
        );
    }

    @FXML
    private void onClickAddReport(ActionEvent event) {
        App.setRoot(
                "views/guest/rawMaterial/create_release_concentrate",
                loader -> ((RawMaterialBindContext.bindContext(loader, new RawMaterialForm())))
        );
    }

}
