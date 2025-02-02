package org.example.vetclinic.service;

import org.example.vetclinic.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByEmail(String email);
    User save(User user);
}
