package org.example.vetclinic.security;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.example.vetclinic.entity.User;
import org.example.vetclinic.entity.UserType;
import org.springframework.security.core.authority.AuthorityUtils;

@Slf4j
@Getter
public class CurrentUser extends org.springframework.security.core.userdetails.User {

    private final User user;
    private final UserType userType;
    private final String name;

    public CurrentUser(User user) {
        super(user.getEmail(), user.getPassword(),
                AuthorityUtils.createAuthorityList(user.getUserType().name()));

        log.info("Creating User for email: {}", user.getEmail());
        log.debug("User details: name={}, userType={}", user.getName(), user.getUserType());

        this.user = user;
        this.userType = user.getUserType();
        this.name = user.getName();
        log.info("User created successfully for email: {}", user.getEmail());
    }

}