package com.schimer.reportsapp.controllers.auth;

import com.schimer.reportsapp.App;
import com.schimer.reportsapp.domain.entities.UserEntity;
import com.schimer.reportsapp.services.AuthService;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import lombok.Setter;

public class RestorePasswordController {
    @FXML
    public PasswordField passwordProperty;
    @FXML
    public PasswordField passwordConfirmProperty;
    @Setter
    private UserEntity userToUpdate;
    private final AuthService authService = new AuthService();


    public void onRestorePassword() {
        try{
            authService.updatePassword(userToUpdate, passwordProperty.getText());
            goBackLogin();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private void goBackLogin() {
        try{
            App.setRoot("views/auth/login");
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
