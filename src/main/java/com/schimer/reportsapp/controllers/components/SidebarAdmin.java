package com.schimer.reportsapp.controllers.components;

import com.schimer.reportsapp.App;
import com.schimer.reportsapp.auth.UserSession;
import javafx.fxml.FXML;

import java.io.IOException;

public class SidebarAdmin {

    @FXML
    public void onClickLogout() {
        try{
            UserSession.logout();
            App.setRoot("views/auth/login");
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onClickUsers(){
        try{
            App.setRoot("views/admin/list-users");
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

}
