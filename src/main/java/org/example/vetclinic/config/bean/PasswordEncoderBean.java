package org.example.vetclinic.config.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration class to define and expose a {@link PasswordEncoder} bean.
 * This bean is responsible for encoding and decoding passwords using BCrypt algorithm.
 */
@Configuration
public class PasswordEncoderBean {

    /**
     * Provides the {@link PasswordEncoder} bean configured to use BCrypt algorithm for password hashing.
     * BCrypt is a secure hashing function used to store and compare passwords.
     *
     * @return an instance of {@link PasswordEncoder} configured with BCrypt.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}