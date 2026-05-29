package com.schimer.reportsapp.utils.guest;

import com.schimer.reportsapp.controllers.interfaces.WizardStep;
import com.schimer.reportsapp.models.ProductFinishedForm;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class ProductFinishedBindContext {

    public static Parent bindContext(FXMLLoader loader, ProductFinishedForm context) {
        try{
            var parent =  (Parent)loader.load();
            var controller = (WizardStep)loader.getController();
            controller.setFormContext(context);
            return parent;
        }catch (IOException e){
            e.printStackTrace();
            throw new RuntimeException("Error al cargar la vista");
        }
    }

}
