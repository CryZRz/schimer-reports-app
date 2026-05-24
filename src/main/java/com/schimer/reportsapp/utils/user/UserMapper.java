package com.schimer.reportsapp.utils.user;

import com.schimer.reportsapp.controllers.admin.CreateUserController;
import com.schimer.reportsapp.domain.entities.EmailAccountEntity;
import com.schimer.reportsapp.domain.entities.QuestionEntity;
import com.schimer.reportsapp.domain.entities.UserEntity;

public class UserMapper {

    public static UserEntity toEntity(CreateUserController form) {
        var user = new UserEntity();

        user.setName(form.namesProperty.getText().trim());
        user.setLastName(form.lastNameProperty.getText().trim());
        user.setEmail(form.emailProperty.getText().trim());

        user.setPassword(form.passwordProperty.getText());

        user.setJobPosition(form.jobPositionProperty.getText().trim());
        user.setSignature(form.signatureProperty.getText().trim());

        /*
        todo
        var questionEntity = new QuestionEntity();
        user.setQuestionOne(form.questionOneProperty.getText().trim());
        user.setQuestionTwo(form.questionTwoProperty.getText().trim());
        user.setQuestionThree(form.questionTreeProperty.getText().trim());
        */


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

}
