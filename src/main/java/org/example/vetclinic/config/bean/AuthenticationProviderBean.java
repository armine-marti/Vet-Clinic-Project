package org.example.vetclinic.config.bean;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration class to define and expose an {@link AuthenticationProvider} bean.
 * This bean is responsible for authenticating users based on the provided {@link UserDetailsService}
 * and {@link PasswordEncoder}.
 */
@Configuration
@RequiredArgsConstructor
public class AuthenticationProviderBean {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    /**
     * Provides the {@link AuthenticationProvider} bean for authentication.
     * The {@link AuthenticationProvider} is used to authenticate the user
     * with the specified {@link UserDetailsService} and {@link PasswordEncoder}.
     *
     * @return an instance of {@link AuthenticationProvider} for handling authentication logic.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }
}
