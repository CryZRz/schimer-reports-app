package com.schimer.reportsapp.domain.repositories;

import com.schimer.reportsapp.domain.entities.DepartmentEntity;

public class DepartmentRespository extends BaseRepository<DepartmentEntity> {

    public DepartmentRespository() {
        super(DepartmentEntity.class);
    }

}
