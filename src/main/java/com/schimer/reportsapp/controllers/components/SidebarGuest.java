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

    public void onClickProductsFinished(){
        try {
            App.setRoot("views/guest/products-finished-list");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onClickRawMaterials(){
        try {
            App.setRoot("views/guest/rawMaterial/index");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
