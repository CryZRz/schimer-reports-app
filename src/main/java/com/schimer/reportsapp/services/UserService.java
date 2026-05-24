package com.schimer.reportsapp.services;

import com.schimer.reportsapp.controllers.admin.CreateUserController;
import com.schimer.reportsapp.domain.entities.UserEntity;
import com.schimer.reportsapp.domain.repositories.UserRepository;
import com.schimer.reportsapp.utils.user.UserMapper;

public class UserService {

    private final UserRepository userRepository = new UserRepository();

    /*
    * Se que es raro que el service resiva como argumento el controlador
    * pero para que agregar un dto como capa intermedia para que depues
    * el service tenga que crear un entity apartir del dto medio como
    * inecesario
    * */
    public void createUser(CreateUserController form) {
        var newUser = UserMapper.toEntity(form);
        this.userRepository.save(newUser);
    }

}
