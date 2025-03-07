package org.example.vetclinic.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.vetclinic.entity.StatusUser;
import org.example.vetclinic.entity.User;
import org.example.vetclinic.exception.UserNotFoundException;
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

    @Override
    public User getByEmail(String email) {
        try {
            return userRepository.findByEmail(email)
                    .orElseThrow(() -> new UserNotFoundException("User with email " + email + " not found"));
        } catch (Exception e) {
            throw new UserNotFoundException("Error while fetching user by email: " + email, e);
        }
    }

    @Override
    public User getById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));
    }

    @Override
    @Transactional
    public void deleteUser(int userId) {
        User user = getById(userId);
        if (user == null) {
            throw new UserNotFoundException("User with this ID is not found");
        }
        userRepository.softUserDelete(user.getId());
    }

    @Override
    public List<User> getAllByStatusUser(StatusUser statusUser) {
        return userRepository.findAllByStatusUser(statusUser);
    }
}
