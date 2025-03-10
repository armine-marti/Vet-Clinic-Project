package org.example.vetclinic.security;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.example.vetclinic.entity.User;
import org.example.vetclinic.entity.UserType;
import org.springframework.security.core.authority.AuthorityUtils;

/**
 * Custom representation of the currently authenticated user in the system.
 * This class extends Spring Security's `User` class and holds additional details
 * related to the user such as their `User` object, `UserType`, and `name`.
 * It is used to populate the security context with custom user information.
 */
@Slf4j
@Getter
public class CurrentUser extends org.springframework.security.core.userdetails.User {

    private final User user;
    private final UserType userType;
    private final String name;

    /**
     * Constructs a new `CurrentUser` object.
     *
     * @param user The `User` entity that contains information about the authenticated user.
     *             This will be used to populate the user details, including email, password,
     *             user type, etc.
     */
    public CurrentUser(User user) {

        super(user.getEmail(), user.getPassword(),
                AuthorityUtils.createAuthorityList("ROLE_" + user.getUserType().name()));

        this.user = user;
        this.userType = user.getUserType();
        this.name = user.getName();

        log.info("CurrentUser created for: {}", user.getEmail());
        log.info("User roles: {}", AuthorityUtils.createAuthorityList("ROLE_" + user.getUserType().name()));
    }
}
