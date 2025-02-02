package org.example.vetclinic.security;

import lombok.extern.slf4j.Slf4j;
import org.example.vetclinic.entity.User;
import org.springframework.security.core.authority.AuthorityUtils;

@Slf4j
public class CurrentUser extends org.springframework.security.core.userdetails.User {

    private User user;

    public CurrentUser(User user) {
        super(user.getEmail(), user.getPassword(),
                AuthorityUtils.createAuthorityList(user.getUserType().name()));
        this.user = user;

        log.info("Creating CurrentUser for: {}, with role: ROLE_{}", user.getEmail(), user.getUserType().name());
    }

    public User getUser() {
        return user;
    }
}