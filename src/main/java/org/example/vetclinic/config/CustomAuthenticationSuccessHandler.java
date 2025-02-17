package org.example.vetclinic.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        String targetUrl = determineTargetUrl(authentication);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    private String determineTargetUrl(Authentication authentication) {
        authentication.getAuthorities().forEach(auth -> log.info("User has role: {}", auth.getAuthority()));

        if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return "/admins/menu";
        } else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER"))) {
            return "/users/menu";
        }
        return "auth/login";
    }
}