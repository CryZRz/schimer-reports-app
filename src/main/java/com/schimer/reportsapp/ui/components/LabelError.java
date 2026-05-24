package com.schimer.reportsapp.ui.components;

import javafx.scene.control.Label;

public class LabelError extends Label {

    LabelError(String message){
        super(message);
        this.setStyle("-fx-font-size: 5;");
    }

}
