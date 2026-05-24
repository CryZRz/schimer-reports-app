package com.schimer.reportsapp.controllers.admin;

import com.schimer.reportsapp.App;
import javafx.fxml.FXML;

import java.io.IOException;

public class ListUsersController {

    @FXML
    private void  onClickCreateUser() throws IOException {
        App.setRoot("views/admin/create-user");
    }
}
