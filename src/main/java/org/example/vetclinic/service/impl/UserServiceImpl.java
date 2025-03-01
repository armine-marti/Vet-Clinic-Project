package org.example.vetclinic.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vetclinic.entity.User;
import org.example.vetclinic.repository.UserRepository;
import org.example.vetclinic.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User save(User user) {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Please enter an email address");
        }
        log.info("Saving user with email: {}", user.getEmail());
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            log.error("Failed to save user", e);
            throw e;
        }
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }
}
