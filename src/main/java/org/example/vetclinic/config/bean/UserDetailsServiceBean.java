package org.example.vetclinic.config.bean;

import lombok.RequiredArgsConstructor;
import org.example.vetclinic.entity.User;
import org.example.vetclinic.repository.UserRepository;
import org.example.vetclinic.security.CurrentUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@RequiredArgsConstructor
public class UserDetailsServiceBean {

    private final UserRepository userRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return email -> {
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("Cannot find user with email: " + email));
            return new CurrentUser(user);
        };
    }
}
