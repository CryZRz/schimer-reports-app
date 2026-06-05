package com.schimer.reportsapp.controllers.auth;

import com.schimer.reportsapp.App;
import com.schimer.reportsapp.domain.entities.UserEntity;
import com.schimer.reportsapp.models.SecurityQuestionAnswerForm;
import com.schimer.reportsapp.services.QuestionService;
import com.schimer.reportsapp.services.UserService;
import com.schimer.reportsapp.ui.components.WindowsUtils;
import com.schimer.reportsapp.utils.validators.FormValidators;
import javafx.animation.PauseTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import net.synedra.validatorfx.Validator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ForgotPasswordController {
    @FXML
    public Label questionOneLabel;
    @FXML
    public TextField questionOne;
    @FXML
    public Label questionTwoLabel;
    @FXML
    public TextField questionTwo;
    @FXML
    public Label questionThreeLabel;
    @FXML
    public TextField questionThree;
    @FXML
    public TextField emailProperty;
    @FXML
    private Button restorePasswordButton;
    @FXML
    private Button cancelButton;

    private final UserService userService = new UserService();
    private final QuestionService questionService = new QuestionService();
    public final List<SecurityQuestionAnswerForm> questionAnswers = new ArrayList<>();
    private final Validator validator = new Validator();
    private int attempts = 0;

    public void initialize() {
        bindListResponses();
        initializeValidator();
    }

    private void initializeValidator() {
        FormValidators.addNotEmptyValidation(validator, emailProperty.textProperty(), emailProperty, "Correo");
        FormValidators.addEmailValidation(validator, emailProperty.textProperty(), emailProperty);

        FormValidators.addNotEmptyValidation(validator, questionOne.textProperty(), questionOne, "Pregunta 1");
        FormValidators.addNotEmptyValidation(validator, questionTwo.textProperty(), questionTwo, "Pregunta 2");
        FormValidators.addNotEmptyValidation(validator, questionThree.textProperty(), questionThree, "Pregunta 3");
    }

    private void bindListResponses() {
        var questions = this.questionService.getAll();
        if (questions.size() >= 3) {
            questionOneLabel.setText(questions.get(0).getQuestion());
            questionTwoLabel.setText(questions.get(1).getQuestion());
            questionThreeLabel.setText(questions.get(2).getQuestion());

            this.questionAnswers.add(
                    new SecurityQuestionAnswerForm(
                            questions.get(0).getId(),
                            questions.get(0),
                            new SimpleStringProperty("")
                    ));
            this.questionAnswers.add(
                    new SecurityQuestionAnswerForm(
                            questions.get(1).getId(),
                            questions.get(1),
                            new SimpleStringProperty("")
                    ));
            this.questionAnswers.add(
                    new SecurityQuestionAnswerForm(
                            questions.get(2).getId(),
                            questions.get(2),
                            new SimpleStringProperty("")
                    ));

            questionOne.textProperty().bindBidirectional(questionAnswers.get(0).getAnswer());
            questionTwo.textProperty().bindBidirectional(questionAnswers.get(1).getAnswer());
            questionThree.textProperty().bindBidirectional(questionAnswers.get(2).getAnswer());
        }
    }

    private void verifyAttempts(){
        attempts++;
        if (attempts > 5) {
            WindowsUtils.showWindowError("Superaste el limite de intentos debes esperar 30 segundos");
            var pause = new PauseTransition(Duration.seconds(30));
            restorePasswordButton.setDisable(true);
            cancelButton.setDisable(true);

            pause.setOnFinished(event -> {
                attempts=0;
                restorePasswordButton.setDisable(false);
                cancelButton.setDisable(false);
            });
            pause.play();
        }
    }

    @FXML
    private void onClickForgotPassword()  {
        verifyAttempts();
        var userFound = userService.getByEmail(emailProperty.getText());
        var countAsserts = new AtomicInteger();

        if (userFound.isPresent()) {
            var user = userFound.get();
            user.getQuestions().forEach(question -> {
               var questionResponse = questionAnswers.stream().filter(questionAnswer -> questionAnswer.getId().equals(question.getId())).findFirst();
                if (questionResponse.isPresent()) {
                    if (questionResponse.get().getAnswer().get().equals(question.getResponse())){
                        countAsserts.getAndIncrement();
                    }
                }else{
                    WindowsUtils.showAlertErrorSystem();
                }
            });

            if (countAsserts.get() == questionAnswers.size()) {
                onGoRestorePassword(userFound.get());
            }else {
                WindowsUtils.showWindowError("Las respuestas proporcionadas no son correctas");
            }

        }else {
           WindowsUtils.showWindowError("El correo proporcionado no esta registrado");
        }
    }

    public void onGoRestorePassword(UserEntity userEntity){
        App.setRoot(
                "views/auth/restore-password",
                loader -> {
                    try{
                        var parent = (Parent)loader.load();
                        var controller = (RestorePasswordController)loader.getController();
                        controller.setUserToUpdate(userEntity);
                        return parent;
                    }catch (Exception e){
                        WindowsUtils.showAlertErrorSystem();
                        return null;
                    }
                }
        );
    }

    @FXML
    private void onClickBackToLogin()  {
        try{
            App.setRoot("views/auth/login");
        }catch (Exception e){
            WindowsUtils.showAlertErrorSystem();
        }
    }

}
