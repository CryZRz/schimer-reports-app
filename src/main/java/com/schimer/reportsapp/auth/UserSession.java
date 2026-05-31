package com.schimer.reportsapp.auth;

import com.schimer.reportsapp.domain.entities.UserEntity;
import lombok.Getter;

@Getter
public class UserSession {

    private static UserSession instance;
    private UserEntity userEntity;

    private UserSession(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public static void login(UserEntity userEntity) {
        instance = new UserSession(userEntity);
    }

    public static void logout() {
        instance = null;
    }

    public static UserSession getInstance() {
        if (instance == null) return  null;
        return instance;
    }

    public UserEntity getUser() {
        return userEntity;
    }

    public void setUser(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}
