package com.schimer.reportsapp.services;

import com.schimer.reportsapp.domain.entities.DepartmentEntity;
import com.schimer.reportsapp.domain.repositories.DepartmentRepository;

import java.util.List;

public class DepartmentService {

    private final DepartmentRepository departmentRespository = new DepartmentRepository();

    public List<DepartmentEntity> getAll(){
        return this.departmentRespository.getAll();
    }

}
