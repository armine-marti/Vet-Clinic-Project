package org.example.vetclinic.config;

public class SecurityConstants {
    public static final String LOGOUT_PAGE = "/logout";
    public static final String[] PERMITTED_PAGES = {
            "/auth/**","/register/**", "/login/**", "/index", "/","/css/**", "/js/**", "/images/**"
    };
}
