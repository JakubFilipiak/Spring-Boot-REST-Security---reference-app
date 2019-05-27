package com.jakubfilipiak.restthymeleaf;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by Jakub Filipiak on 10.05.2019.
 */
public class TestPass {

    @Test
    public void getPass() {
        getPassHash();
    }

    private PasswordEncoder getPassHash() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("user"));
        return new BCryptPasswordEncoder();
    }
}
