package org.example.vetclinic.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Custom handler for successful authentication.
 * This handler determines the target URL to redirect the user to based on their roles after a successful login.
 */
@Slf4j
@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    /**
     * Handles the successful authentication of a user by redirecting them to the appropriate target URL
     * based on their roles.
     *
     * @param request        the HTTP request.
     * @param response       the HTTP response.
     * @param authentication the authentication object containing user details and authorities.
     * @throws IOException if an I/O error occurs during redirection.
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        String targetUrl = determineTargetUrl(authentication);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    /**
     * Determines the target URL to redirect the user to based on their roles.
     * If the user has the "ROLE_ADMIN", they are redirected to the admin menu.
     * If the user has the "ROLE_USER", they are redirected to the user menu.
     * If the user has neither of these roles, they are redirected to the login page.
     *
     * @param authentication the authentication object containing user details and authorities.
     * @return the target URL for redirection.
     */
    private String determineTargetUrl(Authentication authentication) {
        authentication.getAuthorities().forEach(auth -> log.info("User has role: {}",
                auth.getAuthority()));

        if (authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals("ROLE_ADMIN"))) {
            return "/admins/menu";
        } else if (authentication.getAuthorities().stream().anyMatch(a ->
                a.getAuthority().equals("ROLE_USER"))) {
            return "/users/menu";
        }
        return "auth/login";
    }
}