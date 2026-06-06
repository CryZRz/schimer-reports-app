package com.schimer.reportsapp.controllers.components;

import com.schimer.reportsapp.App;
import com.schimer.reportsapp.auth.UserSession;
import com.schimer.reportsapp.ui.components.WindowsUtils;
import javafx.fxml.FXML;

import java.io.IOException;

public class SidebarAdmin {

    @FXML
    public void onClickLogout() {
        try{
            UserSession.logout();
            App.setRoot("views/auth/login");
        }catch (Exception e){
            WindowsUtils.showAlertErrorSystem();
        }
    }

    @FXML
    public void onClickUsers(){
        try{
            App.setRoot("views/admin/list-users");
        }catch (Exception e){
            WindowsUtils.showAlertErrorSystem();
        }
    }

    @FXML
    public void onClickTemplateMp(){

    }

    @FXML
    public void onClickTemplatePt(){
        try{
            App.setRoot("views/admin/change-template-product-finished");
        }catch (Exception e){
            WindowsUtils.showAlertErrorSystem();
        }
    }
}
