package com.schimer.reportsapp.controllers.admin;

import com.schimer.reportsapp.App;
import com.schimer.reportsapp.domain.entities.DepartmentEntity;
import com.schimer.reportsapp.domain.entities.RoleEntity;
import com.schimer.reportsapp.domain.entities.UserEntity;
import com.schimer.reportsapp.models.SecurityQuestionAnswerForm;
import com.schimer.reportsapp.services.DepartmentService;
import com.schimer.reportsapp.services.QuestionService;
import com.schimer.reportsapp.services.RoleService;
import com.schimer.reportsapp.services.UserService;
import com.schimer.reportsapp.utils.Constants;
import com.schimer.reportsapp.utils.user.UserMapper;
import com.schimer.reportsapp.utils.validators.UserFormValidator;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreateUserController {

    private final DepartmentService departmentService = new DepartmentService();
    private final RoleService roleService = new RoleService();
    private final UserService  userService = new UserService();
    public RoleEntity baseRoll;
    private final QuestionService questionService = new QuestionService();
    public final List<SecurityQuestionAnswerForm> questionAnswers = new ArrayList<>();

    private UserEntity userToEdit;

    @FXML
    public TextField namesProperty;
    @FXML
    public TextField lastNameProperty;
    @FXML
    public TextField emailProperty;
    @FXML
    public PasswordField passwordProperty;
    @FXML
    public PasswordField passwordConfirmProperty;
    @FXML
    public Label questionOneLabel;
    @FXML
    public TextField questionOneProperty;
    @FXML
    public Label questionTwoLabel;
    @FXML
    public TextField questionTwoProperty;
    @FXML
    public Label questionThreeLabel;
    @FXML
    public TextField questionTreeProperty;
    @FXML
    public ComboBox<DepartmentEntity> departmentProperty;
    @FXML
    public TextField jobPositionProperty;
    @FXML
    public TextField signatureProperty;
    @FXML
    public TextField serverNameProperty;
    @FXML
    public TextField serverEmailProperty;
    @FXML
    public PasswordField serverPasswordProperty;
    @FXML
    public TextField serverPortProperty;
    @FXML
    public Button buttonCreateUser;
    @FXML
    public Label titleModule;

    public void initialize() {
        this.initializeDepartment();
        this.getDefaultRole();
    }

    private void bindListQuestionsUpdate() {
        var questionsResponse = userToEdit.getQuestions();
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

    private void bindListQuestionsCreate() {
        var questions = this.questionService.getAll();
        if (questions.size() >= 3) {
            questionOneLabel.setText(questions.get(0).getQuestion());
            questionTwoLabel.setText(questions.get(1).getQuestion());
            questionThreeLabel.setText(questions.get(2).getQuestion());

            this.questionAnswers.add(
                    new SecurityQuestionAnswerForm(
                            0L,
                            questions.get(0),
                            new SimpleStringProperty("")
                    ));
            this.questionAnswers.add(
                    new SecurityQuestionAnswerForm(
                            0L,
                            questions.get(1),
                            new SimpleStringProperty("")
                    ));
            this.questionAnswers.add(
                    new SecurityQuestionAnswerForm(
                            0L,
                            questions.get(2),
                            new SimpleStringProperty("")
                    ));

            questionOneProperty.textProperty().bindBidirectional(questionAnswers.get(0).getAnswer());
            questionTwoProperty.textProperty().bindBidirectional(questionAnswers.get(1).getAnswer());
            questionTreeProperty.textProperty().bindBidirectional(questionAnswers.get(2).getAnswer());
        }
    }

    public void setEditableUser(UserEntity userToEdit) {
        this.userToEdit = userToEdit;

        if (userToEdit != null) {
            this.titleModule.setText("Editar Usario");
            this.buttonCreateUser.setText("Editar");
            initializeDataEdit();
            this.bindListQuestionsUpdate();
        }else{
            this.bindListQuestionsCreate();
        }
    }

    private void initializeDataEdit(){
        namesProperty.setText(userToEdit.getName());
        lastNameProperty.setText(userToEdit.getLastName());
        emailProperty.setText(userToEdit.getEmail());
        departmentProperty.getSelectionModel().select(userToEdit.getDepartment());
        jobPositionProperty.setText(userToEdit.getJobPosition());
        serverNameProperty.setText(userToEdit.getEmailAccount().getUrl());
        serverEmailProperty.setText(userToEdit.getEmailAccount().getEmail());
        serverPortProperty.setText(userToEdit.getEmailAccount().getPort().toString());
    }

    private void getDefaultRole() {
        this.baseRoll = this.roleService
                .getRoleByName(Constants.DEFAULT_ROLE)
                .orElseThrow();
    }

    private void initializeDepartment(){
        departmentService.getAll().forEach((department) -> {
          this.departmentProperty.getItems().add(department);
        });
    }

    private boolean validateForm(){
        var validations = UserFormValidator.validate(this);
        //TODO
        return true;
    }

    @FXML
    private void onCreateNewUser(){
        if (userToEdit != null){
            try{
                var userUpdate = UserMapper.toEntity(this, userToEdit);
                this.userService.update(userUpdate);
                goBacK();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }else{
            if(validateForm()){
                try{
                    var newUser = UserMapper.toEntity(this);
                    this.userService.create(newUser);
                    goBacK();
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    private void goBacK()  {
        try{
            App.setRoot("views/admin/list-users");
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

     @FXML
    public void onGoBack() throws IOException {
         this.goBacK();
    }
}
