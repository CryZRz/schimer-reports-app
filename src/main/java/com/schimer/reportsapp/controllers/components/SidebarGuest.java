package com.schimer.reportsapp.controllers.components;

import com.schimer.reportsapp.App;
import com.schimer.reportsapp.auth.UserSession;
import com.schimer.reportsapp.ui.components.WindowsUtils;
import javafx.fxml.FXML;

import java.io.IOException;

public class SidebarGuest {
    private final UserSession userSession = UserSession.getInstance();

    @FXML
    public void onClickLogout() {
        try{
            UserSession.logout();
            App.setRoot("views/auth/login");
        }catch (Exception e){
            WindowsUtils.showAlertErrorSystem();

        }
    }

    public void onClickProductsFinished(){
        try {
            App.setRoot("views/guest/products-finished-list");
        } catch (IOException e) {
            WindowsUtils.showAlertErrorSystem();
        }
    }

    public void onClickRawMaterials(){
        try {
            App.setRoot("views/guest/rawMaterial/index");
        } catch (IOException e) {
            WindowsUtils.showAlertErrorSystem();
        }
    }

    private void verifyDropbox(){
        var client = userSession.getDropboxAccount();

        try {
            if (client != null){
                App.setRoot("views/guest/profile/dropbox-profile");
            }else{
                App.setRoot("views/guest/profile/dropbox-login");
            }
        } catch (IOException e) {
            WindowsUtils.showAlertErrorSystem();
        }
    }

    public void onClickDropbox(){
        verifyDropbox();
    }

}
