package com.schimer.reportsapp.controllers.guest.profile;

import com.dropbox.core.DbxException;
import com.schimer.reportsapp.App;
import com.schimer.reportsapp.auth.UserSession;
import com.schimer.reportsapp.controllers.components.BaseSectionInfo;
import com.schimer.reportsapp.services.AuthService;
import com.schimer.reportsapp.ui.components.WindowsUtils;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

public class DropboxProfileController {
    @FXML
    private Node sectionInfo;
    @FXML
    private BaseSectionInfo sectionInfoController;
    @FXML
    private Label accountName;
    @FXML
    private ImageView profileImage;

    private final UserSession session = UserSession.getInstance();
    private final AuthService authService = new AuthService();

    public void initialize() {
        initializeSectionInfo();
        initializeDropboxInfo();
        initializeStyleProfile();
    }

    private void initializeStyleProfile() {
        var clip = new Circle(20,20,20);
        profileImage.setClip(clip);
    }

    private void initializeDropboxInfo() {
        try {
            var client = session.getClientDropbox().users().getCurrentAccount();
            accountName.setText(client.getName().getDisplayName());
            if (client.getProfilePhotoUrl() != null) {
                profileImage.setImage(new Image(client.getProfilePhotoUrl()));
            }
        } catch (DbxException e) {
            throw new RuntimeException(e);
        }

    }

    private void initializeSectionInfo() {
        if (sectionInfoController != null){
            sectionInfoController.getTitleModule().setText("Dropbox");
            sectionInfoController.getDescriptionModule().setText("Esta seccion esta destinada a la administracion\n de la cuenta de dropbox");
        }
    }

    @FXML
    public void onClickLogout() {
        var dropboxAccount = session.getUser().getDropboxAccount();
        dropboxAccount.setToken("");
        session.getUser().getDropboxAccount().setPath("");
        authService.updateDropboxInfo(session.getUser(),dropboxAccount);

        session.setClientDropbox(null);
        session.setDropboxSession(null);
        onGoToHome();
    }

    private void onGoToHome(){
        try{
            App.setRoot("views/guest/products-finished-list");
        }catch(Exception e){
            WindowsUtils.showAlertErrorSystem();
        }
    }

    public void onSelectFolder(){
        try{
            App.setRoot("views/guest/profile/select-dropbox-folder");
        }catch (Exception e){
            e.printStackTrace();
            WindowsUtils.showAlertErrorSystem();
        }
    }
}
