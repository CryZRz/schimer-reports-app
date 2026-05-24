package com.schimer.reportsapp.services;

import com.schimer.reportsapp.domain.entities.UserEntity;
import com.schimer.reportsapp.domain.repositories.UserRepository;

public class AuthService {
    private final UserRepository userRepository = new UserRepository();

    public UserEntity login(String email, String password) {
        var user = userRepository.getUserByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        return user;
    }

}
