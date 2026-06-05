package com.schimer.reportsapp.controllers.guest.profile;

import com.schimer.reportsapp.App;
import com.schimer.reportsapp.controllers.components.BaseSectionInfo;
import com.schimer.reportsapp.services.DropboxAuthService;
import com.schimer.reportsapp.ui.components.WindowsUtils;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;

public class DropboxLoginController {
    @FXML
    private Node sectionInfo;
    private BaseSectionInfo sectionInfoController;

    private final DropboxAuthService dropboxAuthService = new DropboxAuthService();

    public void initialize() {
        initializeSectionInfo();
    }

    private void initializeSectionInfo() {
        if (sectionInfoController != null){
            sectionInfoController.getTitleModule().setText("Dropbox");
            sectionInfoController.getDescriptionModule().setText("Esta seccion esta destinada a la administracion\n de la cuenta de dropbox");
        }
    }

    public void onClickLogin(){
        try {
            dropboxAuthService.handleDropboxLogin();
            onGoProfileDropbox();
        } catch (Exception e) {
            WindowsUtils.showAlertErrorSystem();
        }
    }

    private void onGoProfileDropbox() {
        try {
            App.setRoot("views/guest/profile/dropbox-profile");
        } catch (Exception e) {
            WindowsUtils.showAlertErrorSystem();
        }
    }

}
