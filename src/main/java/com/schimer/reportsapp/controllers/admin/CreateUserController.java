package com.schimer.reportsapp.controllers.admin;

import com.schimer.reportsapp.App;
import com.schimer.reportsapp.domain.entities.DepartmentEntity;
import com.schimer.reportsapp.domain.entities.RoleEntity;
import com.schimer.reportsapp.domain.entities.UserEntity;
import com.schimer.reportsapp.services.DepartmentService;
import com.schimer.reportsapp.services.RoleService;
import com.schimer.reportsapp.services.UserService;
import com.schimer.reportsapp.utils.Constants;
import com.schimer.reportsapp.utils.user.UserMapper;
import com.schimer.reportsapp.utils.validators.UserFormValidator;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class CreateUserController {

    private final DepartmentService departmentService = new DepartmentService();
    private final RoleService roleService = new RoleService();
    private final UserService  userService = new UserService();
    public RoleEntity baseRoll;

    private UserEntity userToEdit;

    @FXML
    public TextField namesProperty;
    @FXML
    public TextField lastNameProperty;
    @FXML
    public TextField emailProperty;
    @FXML
    public PasswordField passwordProperty;
    @FXML
    public PasswordField passwordConfirmProperty;
    @FXML
    public TextField questionOneProperty;
    @FXML
    public TextField questionTwoProperty;
    @FXML
    public TextField questionTreeProperty;
    @FXML
    public ComboBox<DepartmentEntity> departmentProperty;
    @FXML
    public TextField jobPositionProperty;
    @FXML
    public TextField signatureProperty;
    @FXML
    public TextField serverNameProperty;
    @FXML
    public TextField serverEmailProperty;
    @FXML
    public PasswordField serverPasswordProperty;
    @FXML
    public TextField serverPortProperty;
    @FXML
    public Button buttonCreateUser;

    @FXML
    public Label titleModule;

    public void initialize() {
        this.initializeDepartment();
        this.getDefaultRole();
    }

    public void setEditableUser(UserEntity userToEdit) {
        this.userToEdit = userToEdit;

        if (userToEdit != null) {
            this.titleModule.setText("Editar Userio");
            this.buttonCreateUser.setText("Editar");
            initializeDataEdit();
        }
    }

    private void initializeDataEdit(){
        namesProperty.setText(userToEdit.getName());
        lastNameProperty.setText(userToEdit.getLastName());
        emailProperty.setText(userToEdit.getEmail());
        departmentProperty.getSelectionModel().select(userToEdit.getDepartment());
        jobPositionProperty.setText(userToEdit.getJobPosition());
        serverNameProperty.setText(userToEdit.getEmailAccount().getUrl());
        serverEmailProperty.setText(userToEdit.getEmailAccount().getEmail());
        serverPortProperty.setText(userToEdit.getEmailAccount().getPort().toString());
    }

    private void getDefaultRole() {
        this.baseRoll = this.roleService
                .getRoleByName(Constants.DEFAULT_ROLE)
                .orElseThrow();
    }

    private void initializeDepartment(){
        departmentService.getAll().forEach((department) -> {
          this.departmentProperty.getItems().add(department);
        });
    }

    private boolean validateForm(){
        var validations = UserFormValidator.validate(this);
        //TODO
        return true;
    }

    @FXML
    private void onCreateNewUser(){
        if (userToEdit != null){
            try{
                var userUpdate = UserMapper.toEntity(this, userToEdit);
                this.userService.updateUser(userUpdate);
                goBacK();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }else{
            if(validateForm()){
                try{
                    var newUser = UserMapper.toEntity(this);
                    this.userService.createUser(newUser);
                    goBacK();
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    private void goBacK() throws IOException {
        App.setRoot("views/admin/list-users");
    }

     @FXML
    public void onGoBack() throws IOException {
         this.goBacK();
    }
}
