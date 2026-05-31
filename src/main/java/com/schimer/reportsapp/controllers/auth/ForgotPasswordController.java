package com.schimer.reportsapp.controllers.auth;

import com.schimer.reportsapp.App;
import com.schimer.reportsapp.domain.entities.UserEntity;
import com.schimer.reportsapp.models.SecurityQuestionAnswerForm;
import com.schimer.reportsapp.services.QuestionService;
import com.schimer.reportsapp.services.UserService;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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

    private final UserService userService = new UserService();
    private final QuestionService questionService = new QuestionService();
    public final List<SecurityQuestionAnswerForm> questionAnswers = new ArrayList<>();

    public void initialize() {
        bindListResponses();
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

    @FXML
    private void onClickForgotPassword()  {
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
                }
            });
            if (countAsserts.get() == questionAnswers.size()) {
                onGoRestorePassword(userFound.get());
            }
        }else {
            System.out.println("User not found");
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
                        throw new RuntimeException(e);
                    }
                }
        );
    }

    @FXML
    private void onClickBackToLogin() throws IOException {
        App.setRoot("views/auth/login");
    }

}
