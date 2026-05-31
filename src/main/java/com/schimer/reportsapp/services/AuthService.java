package com.schimer.reportsapp.services;

import com.schimer.reportsapp.auth.UserSession;
import com.schimer.reportsapp.domain.entities.UserEntity;
import com.schimer.reportsapp.domain.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthService {
    private final UserRepository userRepository = new UserRepository();
    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserEntity login(String email, String password) {
        var user = userRepository.getUserByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!encoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        return user;
    }

    public UserEntity updateInfo(UserEntity user, String password) {
        var actualUser = UserSession.getInstance().getUser();

        if (encoder.matches(password, actualUser.getPassword())) {
            return userRepository.update(user);
        }

        throw new RuntimeException("Contraseña incorrecta");
    }

    public UserEntity updatePassword(UserEntity user, String password) {
        user.setPassword(encoder.encode(password));
        return userRepository.update(user);
    }

}
