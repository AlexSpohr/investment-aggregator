package com.projects.investmentaggregator.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordUtil {

    private final BCryptPasswordEncoder encoderPassword;

    public PasswordUtil() {
        this.encoderPassword = new BCryptPasswordEncoder();
    }

    public String encode(String password) {
        return encoderPassword.encode(password);
    }
}
