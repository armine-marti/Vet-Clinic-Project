package org.example.vetclinic.config;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.example.vetclinic.config.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

import static org.example.vetclinic.config.SecurityConstants.LOGOUT_PAGE;
import static org.example.vetclinic.config.SecurityConstants.PERMITTED_PAGES;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private static final List<String> ALL = List.of("*");

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .logout(logout -> logout
                        .logoutUrl(SecurityConstants.LOGOUT_PAGE)
                        .logoutSuccessHandler((request, response, authentication) -> {
                                    SecurityContextHolder.clearContext();
                                    response.setStatus(HttpServletResponse.SC_OK);
                                }
                        )
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(SecurityConstants.PERMITTED_PAGES).permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/user/**").hasRole("USER")
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exceptionHandler -> exceptionHandler.authenticationEntryPoint(authenticationEntryPoint))
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .cors(SecurityConfig::getCorsConfigurer);

        return httpSecurity.build();
    }

    private static void getCorsConfigurer(CorsConfigurer<HttpSecurity> cors) {
        cors.configurationSource(request -> {
            var configuration = new CorsConfiguration();
            configuration.setAllowedOrigins(ALL);
            configuration.setAllowedMethods(ALL);
            configuration.setAllowedHeaders(ALL);
            return configuration;
        });
    }
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(authenticationProvider).build();
    }
}