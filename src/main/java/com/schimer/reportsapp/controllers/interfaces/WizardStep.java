package com.schimer.reportsapp.controllers.interfaces;

import com.schimer.reportsapp.models.ProductFinishedForm;

public interface WizardStep {
   public void setFormContext(ProductFinishedForm context);
}
