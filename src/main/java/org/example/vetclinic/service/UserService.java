package org.example.vetclinic.service;

import org.example.vetclinic.entity.User;

public interface UserService {
    User save(User user);

    boolean existsByEmail(String email);
}
