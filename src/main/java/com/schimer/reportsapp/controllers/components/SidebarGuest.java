package com.schimer.reportsapp.controllers.components;

import com.schimer.reportsapp.App;
import com.schimer.reportsapp.auth.UserSession;
import javafx.fxml.FXML;

import java.io.IOException;

public class SidebarGuest {
    @FXML
    public void onClickLogout() throws IOException {
        UserSession.logout();
        App.setRoot("views/auth/login");
    }
}
