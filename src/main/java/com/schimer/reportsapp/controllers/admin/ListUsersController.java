package com.schimer.reportsapp.controllers.admin;

import com.schimer.reportsapp.App;
import com.schimer.reportsapp.domain.entities.UserEntity;
import com.schimer.reportsapp.services.UserService;
import com.schimer.reportsapp.ui.components.WindowsUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.kordamp.ikonli.javafx.FontIcon;

public class ListUsersController {

    private final UserService userService = new UserService();
    private final ObservableList<UserEntity> users = FXCollections.observableArrayList();
    @FXML
    private TextField textFind;
    @FXML
    public TableView<UserEntity> usersTable;
    public TableColumn<UserEntity, String> nameColumn;
    public TableColumn<UserEntity, String> emailColumn;
    public TableColumn<UserEntity, String> departmentColumn;
    public TableColumn<UserEntity, String> jobPositionColumn;
    public TableColumn<UserEntity, Void> actionsColumn;

    @FXML
    private void  onClickCreateUser()  {
        try{
            App.setRoot("views/admin/create-user");
        }catch (Exception e){
            WindowsUtils.showAlertErrorSystem();
        }
    }

    public void initialize()  {
        this.initListUsers();
    }

    private void initListUsers() {
        usersTable.setPlaceholder(new Label("No hay usuarios con esa coincidencia"));
        usersTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        departmentColumn.setCellValueFactory(new PropertyValueFactory<>("department"));
        jobPositionColumn.setCellValueFactory(new PropertyValueFactory<>("jobPosition"));

        usersTable.setItems(users);

        users.addAll(userService.getAllExceptAdmin());
        this.setupActionsColumn();
    }

    private void setupActionsColumn() {
        actionsColumn.setCellFactory(param -> new TableCell<>() {

            private final FontIcon deleteIcon = new FontIcon("mdi2p-pencil");
            private final Button btnDelete = new Button();

            {
                btnDelete.getStyleClass().add("edit-button");
                deleteIcon.getStyleClass().add("edit-button-icon");
                btnDelete.setGraphic(deleteIcon);

                btnDelete.setOnAction(event -> {
                    var selectedUser = getTableView().getItems().get(getIndex());
                    onEditUser(selectedUser);
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

    public void onFindUsers(){
        users.clear();
        users.addAll(userService.findByName(textFind.getText()));
    }

    public void onEditUser(UserEntity user) {
        try {
            var loader = new FXMLLoader(App.class.getResource("views/admin/create-user.fxml"));
            var root = (Parent) loader.load();
            var controller =  (CreateUserController) loader.getController();
            controller.setEditableUser(user);
            App.setRoot(root);
        }catch (Exception e){
            WindowsUtils.showAlertErrorSystem();
        }
    }

}
