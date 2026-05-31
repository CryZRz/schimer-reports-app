package com.schimer.reportsapp.controllers.guest.profile;

import com.schimer.reportsapp.App;
import com.schimer.reportsapp.auth.UserSession;
import com.schimer.reportsapp.models.SecurityQuestionAnswerForm;
import com.schimer.reportsapp.services.AuthService;
import com.schimer.reportsapp.utils.user.UserMapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class EditProfileController {
    @FXML
    public TextField namesProperty;
    @FXML
    public TextField lastNameProperty;
    @FXML
    public TextField emailProperty;
    @FXML
    public PasswordField oldPasswordProperty;
    @FXML
    public PasswordField passwordProperty;
    @FXML
    public PasswordField passwordConfirmProperty;
    @FXML
    public TextField questionOneProperty;
    @FXML
    public TextField questionTwoProperty;
    @FXML
    public TextField questionTreeProperty;
    @FXML
    public TextField jobPositionProperty;
    @FXML
    public TextField departmentProperty;
    @FXML
    public Label questionOneLabel;
    @FXML
    public Label questionTwoLabel;
    @FXML
    public Label questionThreeLabel;

    private UserSession session;
    public final List<SecurityQuestionAnswerForm> questionAnswers = new ArrayList<>();
    private final AuthService authService = new AuthService();

    public void initialize() {
        session = UserSession.getInstance();
        initializeUserInfo();
        bindListQuestionsUpdate();
    }

    private void bindListQuestionsUpdate() {
        var questionsResponse = session.getUser().getQuestions();
        if (questionsResponse.size() >= 3) {
            var questionOne = questionsResponse.get(0).getQuestion();
            var questionTwo = questionsResponse.get(1).getQuestion();
            var questionThree = questionsResponse.get(2).getQuestion();

            questionOneLabel.setText(questionOne.getQuestion());
            questionTwoLabel.setText(questionTwo.getQuestion());
            questionThreeLabel.setText(questionThree.getQuestion());

            this.questionAnswers.add(
                    new SecurityQuestionAnswerForm(
                            questionOne.getId(),
                            questionOne,
                            new SimpleStringProperty("")
                    ));
            this.questionAnswers.add(
                    new SecurityQuestionAnswerForm(
                            questionTwo.getId(),
                            questionTwo,
                            new SimpleStringProperty("")
                    ));
            this.questionAnswers.add(
                    new SecurityQuestionAnswerForm(
                            questionThree.getId(),
                            questionThree,
                            new SimpleStringProperty("")
                    ));

            questionOneProperty.textProperty().bindBidirectional(questionAnswers.get(0).getAnswer());
            questionTwoProperty.textProperty().bindBidirectional(questionAnswers.get(1).getAnswer());
            questionTreeProperty.textProperty().bindBidirectional(questionAnswers.get(2).getAnswer());
        }
    }

    private void initializeUserInfo(){
        namesProperty.setText(session.getUser().getName());
        lastNameProperty.setText(session.getUser().getLastName());
        emailProperty.setText(session.getUser().getEmail());
        jobPositionProperty.setText(session.getUser().getJobPosition());
        departmentProperty.setText(session.getUser().getDepartment().toString());
    }

    @FXML
    public void onGoBack(){
        try{
            App.setRoot("views/guest/products-finished-list");
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onEditInfoUser(){
        var entityToUpdate = UserMapper.guestFormToEntity(this, session.getUser());
        try{
            var newInfoUser = authService.updateInfo(entityToUpdate, oldPasswordProperty.getText());
            session.setUser(newInfoUser);
            onGoBack();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
