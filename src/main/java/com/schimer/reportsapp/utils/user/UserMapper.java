package com.schimer.reportsapp.utils.user;

import com.schimer.reportsapp.controllers.admin.CreateUserController;
import com.schimer.reportsapp.controllers.guest.profile.EditProfileController;
import com.schimer.reportsapp.domain.entities.EmailAccountEntity;
import com.schimer.reportsapp.domain.entities.QuestionResponseEntity;
import com.schimer.reportsapp.domain.entities.UserEntity;
import com.schimer.reportsapp.models.SecurityQuestionAnswerForm;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {

    public static UserEntity toEntity(CreateUserController form) {
        var user = new UserEntity();

        user.setName(form.namesProperty.getText().trim());
        user.setLastName(form.lastNameProperty.getText().trim());
        user.setEmail(form.emailProperty.getText().trim());

        user.setPassword(form.passwordProperty.getText());

        user.setJobPosition(form.jobPositionProperty.getText().trim());
        user.setSignature(form.signatureProperty.getText().trim());


        var responses = form.questionAnswers.stream().map(question -> {
                var questionResponseEntity = new QuestionResponseEntity();
                questionResponseEntity.setUser(user);
                questionResponseEntity.setQuestion(question.getQuestion());
                questionResponseEntity.setResponse(question.getAnswer().get());
                return questionResponseEntity;
            }
        ).toList();

        user.setQuestions(responses);
        user.setDepartment(form.departmentProperty.getValue());
        user.setRole(form.baseRoll);

        var emailConfig = new EmailAccountEntity();
        emailConfig.setUrl(form.serverNameProperty.getText().trim());
        emailConfig.setEmail(form.serverEmailProperty.getText().trim());
        emailConfig.setPassword(form.serverPasswordProperty.getText().trim());

        var portStr = form.serverPortProperty.getText().trim();
        emailConfig.setPort(portStr.isEmpty() ? null : Integer.parseInt(portStr));

        user.setEmailAccount(emailConfig);

        return user;
    }

    public static UserEntity toEntity(CreateUserController form, UserEntity user) {
        user.setName(form.namesProperty.getText().trim());
        user.setLastName(form.lastNameProperty.getText().trim());
        user.setEmail(form.emailProperty.getText().trim());

        if (!form.passwordProperty.getText().trim().isEmpty()) {
            user.setPassword(form.passwordProperty.getText());
        }

        user.setJobPosition(form.jobPositionProperty.getText().trim());

        if (!form.signatureProperty.getText().trim().isEmpty()) {
            user.setSignature(form.signatureProperty.getText().trim());
        }

        var responses = bindResponses(form.questionAnswers, user);
        user.setQuestions(responses);
        user.setDepartment(form.departmentProperty.getValue());

        user.getEmailAccount().setUrl(form.serverNameProperty.getText().trim());
        user.getEmailAccount().setEmail(form.serverEmailProperty.getText().trim());
        if (!form.serverPasswordProperty.getText().trim().isEmpty()) {
            user.getEmailAccount().setPassword(form.serverPasswordProperty.getText().trim());

        }
        var portStr = form.serverPortProperty.getText().trim();
        user.getEmailAccount().setPort(portStr.isEmpty() ? null : Integer.parseInt(portStr));

        return user;
    }

    public static List<QuestionResponseEntity> bindResponses(List<SecurityQuestionAnswerForm> questions, UserEntity user) {
        var responses = new ArrayList<QuestionResponseEntity>();
        questions.forEach(question -> {
                    if (!question.getAnswer().get().isEmpty()) {
                        var questionResponseEntity = new QuestionResponseEntity();
                        questionResponseEntity.setId(question.getId());
                        questionResponseEntity.setUser(user);
                        questionResponseEntity.setQuestion(question.getQuestion());
                        questionResponseEntity.setResponse(question.getAnswer().get());
                        responses.add(questionResponseEntity);
                    }
                }
        );

        return responses;
    }

    public static UserEntity guestFormToEntity(EditProfileController form, UserEntity user){
        var entity = new UserEntity();
        var responses = bindResponses(form.questionAnswers, entity);
        entity.setId(user.getId());
        entity.setName(form.namesProperty.getText());
        entity.setLastName(form.lastNameProperty.getText());
        entity.setEmail(form.emailProperty.getText());
        entity.setDepartment(form.departmentProperty.getValue());
        entity.setRole(user.getRole());
        entity.setJobPosition(form.jobPositionProperty.getText());
        entity.setSignature(user.getSignature());
        entity.setDropboxAccount(user.getDropboxAccount());
        entity.setEmailAccount(user.getEmailAccount());
        entity.setPassword(user.getPassword());
        entity.setActive(user.isActive());
        if (!form.passwordProperty.getText().trim().isEmpty()) {
            var encoder = new BCryptPasswordEncoder();
            entity.setPassword(encoder.encode(form.passwordProperty.getText()));
        }

        entity.setQuestions(responses);

        return entity;
    }

}
