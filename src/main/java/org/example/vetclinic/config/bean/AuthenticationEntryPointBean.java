package org.example.vetclinic.config.bean;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Custom implementation of {@link org.springframework.security.web.AuthenticationEntryPoint}
 * to handle authentication entry point errors and redirect unauthorized requests to the login page.
 */
@Component
@RequiredArgsConstructor
public class AuthenticationEntryPointBean implements org.springframework.security.web.AuthenticationEntryPoint {


    /**
     * This method is invoked when an unauthenticated user tries to access a protected resource.
     * It redirects the user to the login page if authentication is required.
     *
     * @param request       the HTTP request that triggered the authentication exception.
     * @param response      the HTTP response to send back to the client.
     * @param authException the authentication exception that was thrown.
     * @throws IOException if an input or output exception occurs during the redirect.
     */
    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {
        response.sendRedirect("/auth/login");
    }
}