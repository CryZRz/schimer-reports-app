package com.schimer.reportsapp.services;

import com.schimer.reportsapp.domain.entities.QuestionEntity;
import com.schimer.reportsapp.domain.repositories.QuestionRepository;

import java.util.List;

public class QuestionService {
    private QuestionRepository questionRepository = new QuestionRepository();

    public List<QuestionEntity> getAll() {
        return  questionRepository.getAll();
    }

}
