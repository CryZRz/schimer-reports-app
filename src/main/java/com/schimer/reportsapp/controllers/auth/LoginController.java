package com.schimer.reportsapp.controllers.auth;

import com.schimer.reportsapp.App;
import com.schimer.reportsapp.auth.UserSession;
import com.schimer.reportsapp.services.AuthService;
import com.schimer.reportsapp.ui.components.WindowsUtils;
import com.schimer.reportsapp.utils.Constants;
import com.schimer.reportsapp.utils.validators.FormValidators;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import net.synedra.validatorfx.Validator;

import java.io.IOException;

public class LoginController {

    private final AuthService authService = new AuthService();
    private final Validator validator = new Validator();

    @FXML
    private TextField email;
    @FXML
    private PasswordField password;
    @FXML
    private Button loginButton;
    @FXML
    private Label forgotPasswordLabel;
    private int attempts = 0;

    public void initialize() {
        initializeValidations();
    }

    private void initializeValidations() {
        FormValidators.addNotEmptyValidation(validator, email.textProperty(), email, "Correo");
        FormValidators.addEmailValidation(validator, email.textProperty(), email);
        FormValidators.addNotEmptyValidation(validator, password.textProperty(), password, "Contraseña");
    }

    @FXML
    private void onClickGoForgotPassword() {
        try{
            App.setRoot("views/auth/forgot-password");
        }catch (Exception e){
            WindowsUtils.showAlertErrorSystem();
        }
    }

    private void verifyAttempts(){
        attempts++;
        if (attempts > 5) {
            WindowsUtils.showWindowError("Superaste el limite de intentos debes esperar 30 segundos");
            var pause = new PauseTransition(Duration.seconds(30));
            loginButton.setDisable(true);
            forgotPasswordLabel.setDisable(true);

            pause.setOnFinished(event -> {
                attempts=0;
                loginButton.setDisable(false);
                forgotPasswordLabel.setDisable(false);
            });
            pause.play();
        }
    }

    private void handleLogin() {
        try{
            var user = authService.login(email.getText(), password.getText());
            UserSession.login(user);

            if (user.getRole().getName().equals(Constants.ADMIN_ROLE)) {
                App.setRoot("views/admin/list-users");
            }else{
                App.setRoot("views/guest/products-finished-list");
            }

        }catch (RuntimeException e){
            WindowsUtils.showWindowError(e.getMessage());
        }catch (Exception e){
            WindowsUtils.showAlertErrorSystem();
        }
    }

    @FXML
    private void onClickLogin() throws IOException {
        verifyAttempts();
        if (validator.validate()) {
            handleLogin();
        }
    }
}
