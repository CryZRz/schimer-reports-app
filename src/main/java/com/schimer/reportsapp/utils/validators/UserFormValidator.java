package com.schimer.reportsapp.utils.validators;

import com.schimer.reportsapp.controllers.admin.CreateUserController;

import java.util.HashMap;
import java.util.Map;

public class UserFormValidator {

    public static Map<String, String> validate(CreateUserController form) {
        Map<String, String> errors = new HashMap<>();

        if (form.namesProperty.getText().trim().isEmpty()) {
            errors.put("names", "El nombre es obligatorio.");
        }
        if (form.lastNameProperty.getText().trim().isEmpty()) {
            errors.put("lastName", "El nombre es obligatorio.");
        }
        if (form.emailProperty.getText().trim().isEmpty()) {
            errors.put("email", "El correo es obligatorio.");
        } else if (!form.emailProperty.getText().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            errors.put("email", "El formato del correo no es válido.");
        }

        var pass = form.passwordProperty.getText();
        var confirm = form.passwordConfirmProperty.getText();
        if (pass.isEmpty()) {
            errors.put("password", "La contraseña no puede estar vacía.");
        } else if (pass.length() < 6) {
            errors.put("password", "La contraseña debe tener al menos 6 caracteres.");
        } else if (!pass.equals(confirm)) {
            errors.put("passwordConfirm", "Las contraseñas no coinciden.");
        }

        if (form.questionOneProperty.getText().trim().isEmpty()) {
            errors.put("questionOne", "La pregunta es obligatoria.");
        }
        if (form.questionTwoProperty.getText().trim().isEmpty()) {
            errors.put("questionTwo", "La pregunta es obligatoria.");
        }
        if (form.questionTreeProperty.getText().trim().isEmpty()) {
            errors.put("questionThree", "La pregunta es obligatoria.");
        }

        if (form.departmentProperty.getValue() == null) {
            errors.put("department", "Debes seleccionar un departamento.");
        }

        if (form.jobPositionProperty.getText().trim().isEmpty()) {
            errors.put("jobPositon", "El puesto es obligatorio.");
        }

        if (form.signatureProperty.getText().trim().isEmpty()) {
            errors.put("signature", "La forma es obligatoria.");
        }

        if (form.serverNameProperty.getText().trim().isEmpty()) {
            errors.put("serverName", "El nombre del servidor es obligatorio.");
        }

        if (form.serverPasswordProperty.getText().trim().isEmpty()) {
            errors.put("serverPassword", "La contraseña del servidor es obligatoria.");
        }

        if (form.serverPortProperty.getText().trim().isEmpty()) {
            errors.put("serverPort", "El puerto es requerido.");
        } else {
            try {
                Integer.parseInt(form.serverPortProperty.getText().trim());
            } catch (NumberFormatException e) {
                errors.put("serverPort", "El puerto debe ser un número válido.");
            }
        }

        return errors;
    }

}
