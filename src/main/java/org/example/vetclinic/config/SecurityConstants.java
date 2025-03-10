package org.example.vetclinic.config;

/**
 * Class that holds security-related constants used in the application.
 * This class is used to define URLs for logout, as well as a list of permitted pages
 * that can be accessed without authentication.
 */
public class SecurityConstants {
    /**
     * The URL path for the logout page.
     * This is typically used for the logout functionality in the application.
     */
    public static final String LOGOUT_PAGE = "/logout";

    /**
     * An array of permitted URL patterns that can be accessed without authentication.
     * These pages are publicly accessible, such as authentication-related pages and static resources.
     */
    public static final String[] PERMITTED_PAGES = {
            "/auth/**", "/register/**", "/login/**", "/index", "/", "/css/**", "/js/**", "/images/**",
            "/navbar/**"
    };
}
