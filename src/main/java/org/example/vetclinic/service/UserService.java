package org.example.vetclinic.service;

import org.example.vetclinic.entity.StatusUser;
import org.example.vetclinic.entity.User;

import java.util.List;

public interface UserService {
    User save(User user);

    boolean existsByEmail(String email);

    List<User> getAll();

    User getByEmail(String email);

    void deleteUser(int userId);

    User getById(int userId);

    List<User> getAllByStatusUser(StatusUser statusUser);
}
