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

/**
 * Implementation of the {@link UserService} interface that handles the business logic related to users.
 * This service provides methods for saving, retrieving, updating, and deleting user entities.
 * It interacts with the {@link UserRepository} for database operations and throws exceptions if certain
 * operations fail (e.g., user not found).
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    /**
     * Saves a user entity to the database. The email address must be non-empty.
     * If the email is null or empty, an {@link IllegalArgumentException} is thrown.
     * Logs the process of saving the user and throws an exception if saving fails.
     *
     * @param user The user entity to be saved.
     * @return The saved {@link User} entity.
     * @throws IllegalArgumentException if the email address is null or empty.
     * @throws Exception                if an error occurs while saving the user.
     */
    @Override
    public User save(User user) {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Please enter an email address");
        }
        log.info("Saving user with email: {}", user.getEmail());
        try {
            user.setStatusUser(StatusUser.ACTIVE);
            return userRepository.save(user);
        } catch (Exception e) {
            log.error("Failed to save user", e);
            throw e;
        }
    }

    /**
     * Checks if a user with the specified email already exists in the database.
     *
     * @param email The email address to check.
     * @return True if a user with the specified email exists, false otherwise.
     */
    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * Retrieves all user entities from the database.
     *
     * @return A list of all {@link User} entities.
     */
    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    /**
     * Retrieves a user entity by its email address. If no user is found, a {@link UserNotFoundException} is thrown.
     *
     * @param email The email address of the user to retrieve.
     * @return The {@link User} entity associated with the specified email.
     * @throws UserNotFoundException if no user with the specified email exists.
     */
    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with email " + email + " not found"));

    }

    /**
     * Retrieves a user entity by its ID. If no user is found, a {@link UserNotFoundException} is thrown.
     *
     * @param id The ID of the user to retrieve.
     * @return The {@link User} entity associated with the specified ID.
     * @throws UserNotFoundException if no user with the specified ID exists.
     */
    @Override
    public User getById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));
    }

    /**
     * Soft deletes a user by marking their status as deleted. The user must first be fetched using their ID.
     * This operation does not remove the user from the database.
     *
     * @param userId The ID of the user to delete.
     */
    @Override
    @Transactional
    public void deleteUser(int userId) {
        User user = getById(userId);

        userRepository.softUserDelete(user.getId());
    }

    /**
     * Retrieves all users with the specified status.
     *
     * @param statusUser The status of the users to retrieve.
     * @return A list of {@link User} entities with the specified status.
     */
    @Override
    public List<User> getAllByStatusUser(StatusUser statusUser) {
        return userRepository.findAllByStatusUser(statusUser);
    }
}
