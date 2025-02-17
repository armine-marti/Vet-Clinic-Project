package org.example.vetclinic.config.bean;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Component
@RequiredArgsConstructor
public class AuthenticationEntryPointBean implements org.springframework.security.web.AuthenticationEntryPoint {

    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) {
        handlerExceptionResolver.resolveException(request, response, null, authException);
    }
}