package com.schimer.reportsapp.services;

import com.schimer.reportsapp.domain.entities.DepartmentEntity;
import com.schimer.reportsapp.domain.repositories.DepartmentRespository;

import java.util.List;

public class DepartmentService {

    private final DepartmentRespository departmentRespository = new DepartmentRespository();

    public List<DepartmentEntity> getAll(){
        return this.departmentRespository.getAll();
    }

}
