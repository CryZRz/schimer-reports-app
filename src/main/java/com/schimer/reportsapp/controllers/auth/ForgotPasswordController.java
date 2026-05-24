package com.schimer.reportsapp.controllers.auth;

import com.schimer.reportsapp.App;
import javafx.fxml.FXML;

import java.io.IOException;

public class ForgotPasswordController {

    @FXML
    private void onClickForgotPassword() throws IOException {
        com.schimer.reportsapp.App.setRoot("views/auth/restore-password");
    }

    @FXML
    private void onClickBackToLogin() throws IOException {
        App.setRoot("views/auth/login");
    }

}
