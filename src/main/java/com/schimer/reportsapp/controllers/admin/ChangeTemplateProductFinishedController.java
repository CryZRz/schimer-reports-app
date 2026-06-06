package com.schimer.reportsapp.controllers.admin;

import com.schimer.reportsapp.App;
import com.schimer.reportsapp.controllers.components.BaseSectionInfo;
import com.schimer.reportsapp.services.admin.TemplatePTService;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.FileChooser;

import java.io.FileNotFoundException;

public class ChangeTemplateProductFinishedController {
    @FXML
    private Node sectionInfo;
    @FXML
    private BaseSectionInfo sectionInfoController;

    private final TemplatePTService changeTemplatePTService =  new TemplatePTService();

    public void initialize() {
        initializeSectionInfo();
    }

    private void initializeSectionInfo() {
        if (sectionInfoController != null){
            sectionInfoController.getTitleModule().setText("Plantillas MP");
            sectionInfoController.getDescriptionModule().setText("Esta seccion esta destinada a la administracion\nde las plantillas");
        }
    }

    public void onChangeTemplate(){
        var fileChooser = new FileChooser();

        fileChooser.setTitle("Seleccionar plantilla");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Archivos pdf", "*.docx")
        );

        var file = fileChooser.showOpenDialog(App.getScene().getWindow());

        if (file != null) {
            changeTemplatePTService.generateReport(file);
        }
    }
}
