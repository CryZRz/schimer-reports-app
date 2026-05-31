package com.schimer.reportsapp.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "questions_response")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QuestionResponseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private QuestionEntity question;

    private String response;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
