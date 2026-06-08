package com.schimer.reportsapp.controllers.guest;

import com.schimer.reportsapp.App;
import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.awt.*;
import java.io.File;

public class ViewReportController {
    @FXML
    public WebView pdfView;

    public void initialize() {
        try{
            var engine = pdfView.getEngine();

            var url = new File("C:\\Users\\chris\\OneDrive\\Documentos\\proyecto-ingenieria\\schimer-reports-app\\storage\\templates\\1269.pdf");
            Desktop.getDesktop().open(url);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
