package com.schimer.reportsapp.domain.repositories;

import com.schimer.reportsapp.domain.entities.QuestionEntity;

public class QuestionRepository extends BaseRepository<QuestionEntity> {

    public QuestionRepository() {
        super(QuestionEntity.class);
    }
}
