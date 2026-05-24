package com.schimer.reportsapp.controllers.admin;

import com.schimer.reportsapp.domain.entities.DepartmentEntity;
import com.schimer.reportsapp.domain.entities.RoleEntity;
import com.schimer.reportsapp.services.DepartmentService;
import com.schimer.reportsapp.services.RoleService;
import com.schimer.reportsapp.services.UserService;
import com.schimer.reportsapp.utils.Constants;
import com.schimer.reportsapp.utils.validators.UserFormValidator;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class CreateUserController {

    private final DepartmentService departmentService = new DepartmentService();
    private final RoleService roleService = new RoleService();
    private final UserService  userService = new UserService();
    public RoleEntity baseRoll;

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


    public void initialize() {
        this.initializeDepartment();
        this.getDefaultRole();
    }

    private void getDefaultRole() {
        this.baseRoll = this.roleService.getRoleByName(Constants.DEFAULT_ROLE)
                .orElse(null);
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
        if(validateForm()){
            this.userService.createUser(this);
        }
    }
}
