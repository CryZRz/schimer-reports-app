package com.schimer.reportsapp.utils.guest;

import com.schimer.reportsapp.controllers.interfaces.WizardStep;
import com.schimer.reportsapp.controllers.interfaces.WizardStepRawMaterial;
import com.schimer.reportsapp.models.ProductFinishedForm;
import com.schimer.reportsapp.models.RawMaterialForm;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class RawMaterialBindContext {

    public static Parent bindContext(FXMLLoader loader, RawMaterialForm context) {
        try{
            var parent =  (Parent)loader.load();
            var controller = (WizardStepRawMaterial)loader.getController();
            controller.setFormContext(context);
            return parent;
        }catch (IOException e){
            e.printStackTrace();
            throw new RuntimeException("Error al cargar la vista");
        }
    }
}
