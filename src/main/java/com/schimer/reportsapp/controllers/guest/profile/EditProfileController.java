package com.schimer.reportsapp.controllers.guest.profile;

import com.schimer.reportsapp.App;
import com.schimer.reportsapp.auth.UserSession;
import com.schimer.reportsapp.controllers.guest.SectionPtInfoController;
import com.schimer.reportsapp.models.SecurityQuestionAnswerForm;
import com.schimer.reportsapp.services.AuthService;
import com.schimer.reportsapp.ui.components.WindowsUtils;
import com.schimer.reportsapp.utils.user.UserMapper;
import com.schimer.reportsapp.utils.validators.FormValidators;
import javafx.animation.PauseTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import net.synedra.validatorfx.Validator;

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
    @FXML
    private SectionPtInfoController sectionPtInfoController;

    private int attempts = 0;
    private Validator validator = new Validator();
    private UserSession session;
    public final List<SecurityQuestionAnswerForm> questionAnswers = new ArrayList<>();
    private final AuthService authService = new AuthService();

    public void initialize() {
        session = UserSession.getInstance();
        initializeUserInfo();
        bindListQuestionsUpdate();
        removeButton();
        initializeValidations();
    }

    private void initializeValidations() {
        FormValidators.addNotEmptyValidation(validator, namesProperty.textProperty(), namesProperty, "Nombre");
        FormValidators.addNotEmptyValidation(validator, lastNameProperty.textProperty(), lastNameProperty, "Apellidos");
        FormValidators.addNotEmptyValidation(validator, oldPasswordProperty.textProperty(), oldPasswordProperty, "Contraseña anterior");
        FormValidators.addNotEmptyValidation(validator, emailProperty.textProperty(), emailProperty, "Correo");
        FormValidators.addEmailValidation(validator, emailProperty.textProperty(), emailProperty);
    }

    private void dynamicValidations() {
        if(!passwordProperty.getText().isEmpty()){
            FormValidators.addMatchValidation(validator, passwordProperty.textProperty(), passwordConfirmProperty.textProperty(), passwordConfirmProperty, "Contraseña");
        }
        if(!questionOneProperty.getText().isEmpty()){
            FormValidators.addMaxLengthValidation(validator, questionOneProperty.textProperty(), questionOneProperty, 30,"Pregunta 1");
        }
        if(!questionTwoProperty.getText().isEmpty()){
            FormValidators.addMaxLengthValidation(validator, questionTwoProperty.textProperty(), questionTwoProperty, 30,"Pregunta 2");
        }
        if(!questionTreeProperty.getText().isEmpty()){
            FormValidators.addMaxLengthValidation(validator, questionTreeProperty.textProperty(), questionTreeProperty, 30,"Pregunta 3");
        }
    }

    private void removeButton() {
        if(sectionPtInfoController != null){
            sectionPtInfoController.getBtnAdd().ifPresent(button -> {
                button.setVisible(false);
            });
        }
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
            WindowsUtils.showAlertErrorSystem();
        }
    }

    private void verifyAttempts(){
        attempts++;
        if (attempts > 5) {
            var pause = new PauseTransition(Duration.seconds(30));
            var window = WindowsUtils.showAlertBlock("Superaste el limite de intentos debes esperar 30 segundos");

            pause.setOnFinished(event -> {
                attempts=0;
                window.setOnCloseRequest(null);
                window.hide();
            });
            pause.play();
        }
    }

    public void editInfoUserHandler(){
        var entityToUpdate = UserMapper.guestFormToEntity(this, session.getUser());
        try{
            var newInfoUser = authService.updateInfo(entityToUpdate, oldPasswordProperty.getText());
            session.setUser(newInfoUser);
            onGoBack();
        }
        catch (RuntimeException e){
            verifyAttempts();
            WindowsUtils.showWindowError("La contraseña es  incorrecta");
        }
        catch (Exception e){
            WindowsUtils.showAlertErrorSystem();
        }

    }

    @FXML
    public void onEditInfoUser(){
        dynamicValidations();
        if (validator.validate()) {
            editInfoUserHandler();
        }
    }

}
