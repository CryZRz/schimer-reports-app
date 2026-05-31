package com.schimer.reportsapp.models;

import com.schimer.reportsapp.domain.entities.QuestionResponseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class GuestInfoForm {
    private String oldPassword;
    private String newPassword;
    private String newPasswordConfirm;
    private List<QuestionResponseEntity> responses;
}
