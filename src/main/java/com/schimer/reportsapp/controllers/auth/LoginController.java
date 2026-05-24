package com.schimer.reportsapp.controllers.auth;

import com.schimer.reportsapp.App;
import com.schimer.reportsapp.auth.UserSession;
import com.schimer.reportsapp.services.AuthService;
import com.schimer.reportsapp.utils.Constants;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController {

    private final AuthService authService = new AuthService();

    @FXML
    private TextField email;
    @FXML
    private PasswordField password;

    @FXML
    private void onClickGoForgotPassword() throws IOException {
        App.setRoot("views/auth/forgot-password");
    }

    @FXML
    private void onClickLogin() throws IOException {
        try{
            var user = authService.login(email.getText(), password.getText());
            UserSession.login(user);

            if (user.getRole().getName().equals(Constants.ADMIN_ROLE)) {
                App.setRoot("views/admin/list-users");
            }else{
                System.out.println("Hola");
            }

        }catch (Exception e){
            System.out.println("Error al iniciar el login");
        }
    }
}
