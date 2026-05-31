package com.schimer.reportsapp.models;

import com.schimer.reportsapp.domain.entities.QuestionEntity;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SecurityQuestionAnswerForm{

    private Long id;
    private QuestionEntity question;
    private StringProperty answer = new SimpleStringProperty("");
}
