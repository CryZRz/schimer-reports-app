package com.schimer.reportsapp.controllers.components;

import com.schimer.reportsapp.App;
import com.schimer.reportsapp.auth.UserSession;
import com.schimer.reportsapp.controllers.guest.profile.EditProfileController;
import com.schimer.reportsapp.controllers.interfaces.WizardStep;
import com.schimer.reportsapp.ui.components.WindowsUtils;
import com.schimer.reportsapp.utils.Constants;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

public class Header {
    @FXML
    public ImageView profileImage;
    @FXML
    public Label username;
    private final UserSession userSession = UserSession.getInstance();

    @FXML
    public void initialize() {
        var clip = new Circle(20,20,20);
        profileImage.setClip(clip);
        initializeUsername();
    }

    private void initializeUsername(){
        var session = UserSession.getInstance();
        if(session != null){
            username.setText("Hola " + session.getUser().getName());
        }
    }

    private void goToGuest(){
        try{
            App.setRoot("views/guest/profile/edit-profile");
        }catch(Exception e){
            WindowsUtils.showAlertErrorSystem();
        }
    }

    private void goToAdmin(){
        App.setRoot("views/admin/edit-profile", loader -> {
            try{
                var parent =  (Parent)loader.load();
                var controller = (EditProfileController)loader.getController();
                controller.setAdmin(true);
                return parent;
            }catch(Exception e){
                WindowsUtils.showAlertErrorSystem();
                return null;
            }
        });
    }

    public void onClickEditProfile(){
        if (userSession.getUser().getRole().getName().equals(Constants.ADMIN_ROLE)){
            goToAdmin();
        }else{
            goToGuest();
        }
    }
}
