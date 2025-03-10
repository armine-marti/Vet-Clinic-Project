package org.example.vetclinic.config.bean;

import lombok.RequiredArgsConstructor;
import org.example.vetclinic.entity.User;
import org.example.vetclinic.repository.UserRepository;
import org.example.vetclinic.security.CurrentUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Configuration class to define and expose a {@link UserDetailsService} bean.
 * This bean is responsible for loading user-specific data for authentication
 * based on the email address, using the {@link UserRepository} to fetch user details.
 */
@Configuration
@RequiredArgsConstructor
public class UserDetailsServiceBean {

    private final UserRepository userRepository;


    /**
     * Provides the {@link UserDetailsService} bean, which loads user-specific data
     * by querying the {@link UserRepository} based on the user's email address.
     * If no user is found with the provided email, a {@link UsernameNotFoundException} is thrown.
     *
     * @return a {@link UserDetailsService} implementation for loading user details by email.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return email -> {
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> {
                        return new UsernameNotFoundException("Cannot find user with email: " + email);
                    });
            return new CurrentUser(user);
        };
    }
}
