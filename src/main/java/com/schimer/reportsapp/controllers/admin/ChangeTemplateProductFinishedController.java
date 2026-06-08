package com.schimer.reportsapp.controllers.admin;

import com.schimer.reportsapp.App;
import com.schimer.reportsapp.controllers.components.BaseSectionInfo;
import com.schimer.reportsapp.domain.entities.TemplatePTEntity;
import com.schimer.reportsapp.services.admin.TemplatePTService;
import com.schimer.reportsapp.ui.components.WindowsUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class ChangeTemplateProductFinishedController {
    @FXML
    public ListView<TemplatePTEntity> listFiles;
    @FXML
    private Node sectionInfo;
    @FXML
    private BaseSectionInfo sectionInfoController;

    private final TemplatePTService templatePTService =  new TemplatePTService();
    ObservableList<TemplatePTEntity> listView = FXCollections.observableArrayList();

    public void initialize() {
        initializeSectionInfo();
        initializeFiles();
    }

    private void initializeFiles() {
        listFiles.setCellFactory(lv -> new ListCell<TemplatePTEntity>() {
            @Override
            protected void updateItem(TemplatePTEntity item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }
        });
        listView.addAll(templatePTService.getAll());
        templatePTService.getAll().forEach(templatePTEntity -> {
            listFiles.getItems().add(templatePTEntity);
        });
    }

    private void initializeSectionInfo() {
        if (sectionInfoController != null){
            sectionInfoController.getTitleModule().setText("Plantillas PT");
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
            try{
                var destino = Path.of("storage/templates/" + file.getName());
                Files.createDirectories(destino.getParent());
                Files.copy(file.toPath(), destino, StandardCopyOption.REPLACE_EXISTING);
                templatePTService.save(new TemplatePTEntity(null, file.getName(), destino.toAbsolutePath().toString()));
            }catch (Exception e){
                WindowsUtils.showAlertErrorSystem();
            }
        }
    }
}
