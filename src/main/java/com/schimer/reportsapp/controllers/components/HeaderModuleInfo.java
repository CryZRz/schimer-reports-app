package com.schimer.reportsapp.controllers.components;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

public class HeaderModuleInfo {
    public ImageView profileImage;

    @FXML
    public void initialize() {
        var clip = new Circle(20,20,20);
        profileImage.setClip(clip);
    }
}
