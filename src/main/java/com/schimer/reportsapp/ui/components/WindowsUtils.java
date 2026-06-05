package com.schimer.reportsapp.ui.components;

import com.schimer.reportsapp.App;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

public class WindowsUtils {

    public static void showWindowError(String message) {
        var window = new Alert(Alert.AlertType.ERROR);
        window.setTitle("Error");
        window.setHeaderText(null);
        window.setContentText(message);
        window.initOwner(App.getScene().getWindow());
        window.showAndWait();
    }

    public static void showAlertErrorSystem(){
        WindowsUtils.showWindowError("Error desconocido comunicate con el departamento de TI");
    }

    public static Window showAlertBlock(String message){
        var alert = new Alert(Alert.AlertType.NONE);

        alert.setTitle("Acceso Bloqueado");
        alert.setContentText(message);

        alert.show();

        var window = alert.getDialogPane().getScene().getWindow();
        window.setOnCloseRequest(WindowEvent::consume);

        return window;
    }
}
