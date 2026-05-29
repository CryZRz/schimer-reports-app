package com.schimer.reportsapp.controllers.guest;

import javafx.scene.control.Button;

import java.util.Optional;

public class SectionPtInfoController {
    public Button btnAdd;

    public Optional<Button> getBtnAdd() {
        return Optional.ofNullable(btnAdd);
    };

}
