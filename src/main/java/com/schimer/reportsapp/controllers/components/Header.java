package com.schimer.reportsapp.controllers.components;

import com.schimer.reportsapp.auth.UserSession;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

public class Header {
    @FXML
    public ImageView profileImage;
    @FXML
    public Label username;

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
}
