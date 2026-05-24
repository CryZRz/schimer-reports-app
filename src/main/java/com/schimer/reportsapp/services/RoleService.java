package com.schimer.reportsapp.services;

import com.schimer.reportsapp.domain.entities.RoleEntity;
import com.schimer.reportsapp.domain.repositories.RoleRepository;

import java.util.Optional;

public class RoleService {

    private final RoleRepository roleRepository = new RoleRepository();

    public Optional<RoleEntity> getRoleByName(String roleName) {
        return roleRepository.getByName(roleName);
    }

}
