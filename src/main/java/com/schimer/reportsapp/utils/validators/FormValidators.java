package com.schimer.reportsapp.utils.validators;

import net.synedra.validatorfx.Check;
import net.synedra.validatorfx.Validator;
import javafx.beans.property.StringProperty;
import javafx.beans.property.ObjectProperty;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.ComboBoxBase;
import java.util.regex.Pattern;

public class FormValidators {

    // Valida que el campo de texto no se quede vacío o con puros espacios en blanco.
    public static void addNotEmptyValidation(Validator validator,
                                             StringProperty textProperty,
                                             TextInputControl field,
                                             String fieldName) {
        validator.createCheck()
                .dependsOn(fieldName, textProperty)
                .withMethod(c -> {
                    String value = c.get(fieldName);
                    if (value == null || value.trim().isEmpty()) {
                        c.error(fieldName + " no puede estar vacío");
                    }
                })
                .decorates(field);
    }

    // Valida que el texto introducido cumpla con una estructura de correo electrónico estándar (opcional si está vacío).
    public static void addEmailValidation(Validator validator,
                                          StringProperty emailProperty,
                                          TextInputControl field) {
        Pattern emailPattern = Pattern.compile(
                "^[A-Za-z0-9+_.-]+@(.+)$"
        );
        String key = field.getId() != null ? field.getId() : "email";

        validator.createCheck()
                .dependsOn(key, emailProperty)
                .withMethod(c -> {
                    String email = c.get(key);
                    if (email != null && !email.isEmpty() && !emailPattern.matcher(email).matches()) {
                        c.error("Ingrese un email válido (ejemplo@dominio.com)");
                    }
                })
                .decorates(field);
    }

    // Valida que el texto cumpla con una cantidad mínima de caracteres requerida.
    public static void addMinLengthValidation(Validator validator,
                                              StringProperty textProperty,
                                              TextInputControl field,
                                              int minLength,
                                              String fieldName) {
        validator.createCheck()
                .dependsOn(fieldName, textProperty)
                .withMethod(c -> {
                    String value = c.get(fieldName);
                    if (value != null && value.length() < minLength) {
                        c.error(fieldName + " debe tener al menos " + minLength + " caracteres");
                    }
                })
                .decorates(field);
    }

    // Valida que el texto no sobrepase un límite máximo de caracteres permitido.
    public static void addMaxLengthValidation(Validator validator,
                                              StringProperty textProperty,
                                              TextInputControl field,
                                              int maxLength,
                                              String fieldName) {
        validator.createCheck()
                .dependsOn(fieldName, textProperty)
                .withMethod(c -> {
                    String value = c.get(fieldName);
                    if (value != null && value.length() > maxLength) {
                        c.error(fieldName + " no puede exceder " + maxLength + " caracteres");
                    }
                })
                .decorates(field);
    }

    // Valida que todos los caracteres alfabéticos del campo estén estrictamente en minúsculas.
    public static void addLowerCaseValidation(Validator validator,
                                              StringProperty textProperty,
                                              TextInputControl field,
                                              String fieldName) {
        validator.createCheck()
                .dependsOn(fieldName, textProperty)
                .withMethod(c -> {
                    String value = c.get(fieldName);
                    if (value != null && !value.isEmpty() && !value.equals(value.toLowerCase())) {
                        c.error(fieldName + " solo permite minúsculas");
                    }
                })
                .decorates(field);
    }

    // Valida que todos los caracteres alfabéticos del campo estén estrictamente en mayúsculas.
    public static void addUpperCaseValidation(Validator validator,
                                              StringProperty textProperty,
                                              TextInputControl field,
                                              String fieldName) {
        validator.createCheck()
                .dependsOn(fieldName, textProperty)
                .withMethod(c -> {
                    String value = c.get(fieldName);
                    if (value != null && !value.isEmpty() && !value.equals(value.toUpperCase())) {
                        c.error(fieldName + " solo permite mayúsculas");
                    }
                })
                .decorates(field);
    }

    // Valida que el campo contenga única y exclusivamente números enteros (no permite puntos ni signos).
    public static void addNumericValidation(Validator validator,
                                            StringProperty textProperty,
                                            TextInputControl field,
                                            String fieldName) {
        validator.createCheck()
                .dependsOn(fieldName, textProperty)
                .withMethod(c -> {
                    String value = c.get(fieldName);
                    if (value != null && !value.isEmpty() && !value.matches("\\d+")) {
                        c.error(fieldName + " solo permite números enteros");
                    }
                })
                .decorates(field);
    }

    // Valida que el número ingresado (entero o decimal) sea estrictamente mayor que cero.
    public static void addPositiveNumberValidation(Validator validator,
                                                   StringProperty textProperty,
                                                   TextInputControl field,
                                                   String fieldName) {
        validator.createCheck()
                .dependsOn(fieldName, textProperty)
                .withMethod(c -> {
                    String value = c.get(fieldName);
                    if (value != null && !value.isEmpty()) {
                        try {
                            double num = Double.parseDouble(value);
                            if (num <= 0) {
                                c.error(fieldName + " debe ser mayor que 0");
                            }
                        } catch (NumberFormatException e) {
                            c.error(fieldName + " debe ser un número válido");
                        }
                    }
                })
                .decorates(field);
    }

    // Valida que el texto cumpla con una expresión regular (Regex) personalizada pasada por parámetro.
    public static void addRegexValidation(Validator validator,
                                          StringProperty textProperty,
                                          TextInputControl field,
                                          String regex,
                                          String errorMessage,
                                          String fieldName) {
        validator.createCheck()
                .dependsOn(fieldName, textProperty)
                .withMethod(c -> {
                    String value = c.get(fieldName);
                    if (value != null && !value.isEmpty() && !value.matches(regex)) {
                        c.error(errorMessage);
                    }
                })
                .decorates(field);
    }

    // Valida que un ComboBox tenga un elemento seleccionado de manera obligatoria (evita valores nulos).
    public static <T> void addRequiredSelectionValidation(Validator validator,
                                                          ObjectProperty<T> selectedProperty,
                                                          ComboBoxBase<T> field,
                                                          String fieldName) {
        validator.createCheck()
                .dependsOn(fieldName, selectedProperty)
                .withMethod(c -> {
                    T value = c.get(fieldName);
                    if (value == null) {
                        c.error("Debe seleccionar " + fieldName);
                    }
                })
                .decorates(field);
    }

    // Valida que los valores de dos StringProperty sean idénticos (ideal para confirmaciones de contraseñas).
    public static void addMatchValidation(Validator validator,
                                          StringProperty firstProperty,
                                          StringProperty secondProperty,
                                          TextInputControl field,
                                          String fieldName) {
        String keyFirst = fieldName + "First";
        String keySecond = fieldName + "Second";

        validator.createCheck()
                .dependsOn(keyFirst, firstProperty)
                .dependsOn(keySecond, secondProperty)
                .withMethod(c -> {
                    String first = c.get(keyFirst);
                    String second = c.get(keySecond);
                    if (first != null && second != null && !first.equals(second)) {
                        c.error(fieldName + " no coinciden");
                    }
                })
                .decorates(field);
    }

    // Validación unificada que exige el llenado del correo electrónico y además verifica que tenga una estructura válida.
    public static void addEmailWithRequiredValidation(Validator validator,
                                                      StringProperty emailProperty,
                                                      TextInputControl field) {
        String key = field.getId() != null ? field.getId() : "Correo";

        validator.createCheck()
                .dependsOn(key, emailProperty)
                .withMethod(c -> {
                    String email = c.get(key);
                    Pattern emailPattern = Pattern.compile(
                            "^[A-Za-z0-9+_.-]+@(.+)$"
                    );

                    if (email == null || email.trim().isEmpty()) {
                        c.error("El email es requerido");
                    } else if (!emailPattern.matcher(email).matches()) {
                        c.error("Ingrese un email válido (ejemplo@dominio.com)");
                    }
                })
                .decorates(field);
    }

    // Valida que el texto represente un número entero o un número decimal con punto flotante (ideal para mapeos seguros a BigDecimal).
    public static void addDecimalValidation(Validator validator,
                                            StringProperty textProperty,
                                            TextInputControl field,
                                            String fieldName) {
        validator.createCheck()
                .dependsOn(fieldName, textProperty)
                .withMethod(c -> {
                    String value = c.get(fieldName);
                    if (value != null && !value.isEmpty()) {
                        // Acepta números como "12", "0.5", "1425.80" pero rechaza letras o comas
                        if (!value.matches("\\d+(\\.\\d+)?")) {
                            c.error(fieldName + " debe ser un número entero o decimal (ej: 14.50)");
                        }
                    }
                })
                .decorates(field);
    }
}