package com.schimer.reportsapp.test;

import com.schimer.reportsapp.domain.repositories.UserRepository;
import com.schimer.reportsapp.services.AuthService;
import com.schimer.reportsapp.services.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UnitaryTest {

    private static final Log log = LogFactory.getLog(UnitaryTest.class);

    public static void main(String[] args) {
        var encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("123456"));
    }

}
