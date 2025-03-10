package org.example.vetclinic.service;

import org.example.vetclinic.entity.StatusUser;
import org.example.vetclinic.entity.User;

import java.util.List;

/**
 * Service interface for managing users.
 * This interface provides methods to perform CRUD operations on users
 * and other functionalities related to users.
 */
public interface UserService {
    /**
     * Saves a new user to the system.
     *
     * @param user The {@link User} entity to save.
     * @return The saved {@link User} entity.
     */
    User save(User user);

    /**
     * Checks if a user exists by their email.
     *
     * @param email The email of the user to check.
     * @return True if a user exists with the specified email, false otherwise.
     */
    boolean existsByEmail(String email);

    /**
     * Retrieves all users from the system.
     *
     * @return A list of all {@link User} entities.
     */
    List<User> getAll();

    /**
     * Retrieves a user by their email.
     *
     * @param email The email of the user to retrieve.
     * @return The {@link User} entity with the specified email.
     * @throws Exception if no user is found with the provided email.
     */
    User getByEmail(String email);

    /**
     * Deletes a user by their ID.
     *
     * @param userId The ID of the user to delete.
     */
    void deleteUser(int userId);

    /**
     * Retrieves a user by their ID.
     *
     * @param userId The ID of the user to retrieve.
     * @return The {@link User} entity with the specified ID.
     * @throws Exception if no user is found with the provided ID.
     */
    User getById(int userId);

    /**
     * Retrieves all users with a specific status.
     *
     * @param statusUser The status of the users to retrieve.
     * @return A list of {@link User} entities with the specified status.
     */
    List<User> getAllByStatusUser(StatusUser statusUser);
}
