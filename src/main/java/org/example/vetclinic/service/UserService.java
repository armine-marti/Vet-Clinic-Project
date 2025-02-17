package org.example.vetclinic.service;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.example.vetclinic.entity.User;

import java.util.Optional;

public interface UserService {

    Optional<User> findByEmail(String email);

    User save(User user);

    boolean existsByEmail(String email);
}
