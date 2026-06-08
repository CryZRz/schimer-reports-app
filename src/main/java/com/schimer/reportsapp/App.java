package com.schimer.reportsapp;

import com.schimer.reportsapp.auth.UserSession;
import com.schimer.reportsapp.services.AuthService;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.function.Function;

public class App extends Application {

    private static Scene scene;
    private static HostServices hostServices;

    @Override
    public void start(Stage stage) throws IOException {
        var authService = new AuthService();
        var user = authService.login("admin@mail.com", "123456");
        UserSession.login(user);
        scene = new Scene(loadFXML("views/admin/list-users"));
        scene.getStylesheets().add(App.class.getResource("styles/globals.css").toExternalForm());
        stage.setTitle("Reportes Schimmer");
        stage.setScene(scene);

        var bounds = Screen.getPrimary().getVisualBounds();
        stage.setX(bounds.getMinX());
        stage.setY(bounds.getMinY());
        stage.setWidth(bounds.getWidth());
        stage.setHeight(bounds.getHeight());

        hostServices = getHostServices();
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static void setRoot(String fxml, Function<FXMLLoader, Parent> predicate)  {
        var loader = new FXMLLoader(
                App.class.getResource(fxml+".fxml")
        );
        scene.setRoot(predicate.apply(loader));
    }

    public static void setRoot(Parent root) throws IOException {
        scene.setRoot(root);
    }

    private static Parent loadFXML(String fxml) throws IOException {
        var fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static Scene getScene() {
        return scene;
    }

    public static HostServices getHost(){
        return hostServices;
    }
}
