package com.schimer.reportsapp.services;

import com.schimer.reportsapp.auth.UserSession;
import com.schimer.reportsapp.domain.entities.UserEntity;
import com.schimer.reportsapp.domain.repositories.UserRepository;
import com.schimer.reportsapp.utils.Constants;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public class UserService {

    private final UserRepository userRepository = new UserRepository();
    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

    public void createUser(UserEntity newUser) {
        if (UserSession.getInstance().getUser().getRole().getName().equals(Constants.ADMIN_ROLE)) {
            newUser.setPassword(encoder.encode(newUser.getPassword()));
            this.userRepository.save(newUser);
        }
    }

    public void updateUser(UserEntity updatedUser) {
        if (UserSession.getInstance().getUser().getRole().getName().equals(Constants.ADMIN_ROLE)) {
            if (!updatedUser.getPassword().isEmpty()) {
                updatedUser.setPassword(encoder.encode(updatedUser.getPassword()));
            }
            this.userRepository.update(updatedUser);
        }
    }

    public List<UserEntity> getAllExceptAdmin(){
        return userRepository.getAllExceptAdmin();
    }

}
