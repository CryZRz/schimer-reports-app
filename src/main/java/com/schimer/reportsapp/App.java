package com.schimer.reportsapp;

import com.schimer.reportsapp.auth.UserSession;
import com.schimer.reportsapp.services.AuthService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.function.Function;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        var authService = new AuthService();
        var user = authService.login("blinzzia@mail.com", "blinzzia");
        UserSession.login(user);
        scene = new Scene(loadFXML("views/auth/login"), 320, 240);
        scene.getStylesheets().add(App.class.getResource("styles/globals.css").toExternalForm());
        stage.setTitle("Reportes Schimmer");
        stage.setScene(scene);
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
}
