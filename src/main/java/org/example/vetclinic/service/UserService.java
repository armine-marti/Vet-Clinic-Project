package org.example.vetclinic.service;

import org.example.vetclinic.entity.User;

import java.util.List;

public interface UserService {
    User save(User user);

    boolean existsByEmail(String email);

    List<User> getAll();
}
