package org.example.vetclinic.config.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

/**
 * Configuration class to define and expose an {@link AuthenticationManager} bean.
 * This is used for handling authentication logic within the Spring Security context.
 */
@Configuration
public class AuthenticationManagerBean {
    /**
     * Provides the {@link AuthenticationManager} bean from the given {@link AuthenticationConfiguration}.
     * The {@link AuthenticationManager} is used to process authentication requests.
     *
     * @param config the {@link AuthenticationConfiguration} used to configure and obtain the {@link AuthenticationManager}.
     * @return an instance of {@link AuthenticationManager}.
     * @throws Exception if there is an error during the creation of the AuthenticationManager.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
