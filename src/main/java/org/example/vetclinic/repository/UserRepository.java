package org.example.vetclinic.repository;

import org.example.vetclinic.entity.StatusUser;
import org.example.vetclinic.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing `User` entities.
 * This interface extends `JpaRepository` and provides CRUD operations along with custom queries for `User` entities.
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Checks if a user exists by their email.
     *
     * @param email The email to check.
     * @return `true` if a user exists with the given email, otherwise `false`.
     */
    boolean existsByEmail(String email);

    /**
     * Finds a user by their email.
     *
     * @param email The email of the user to find.
     * @return An `Optional` containing the user if found, otherwise empty.
     */
    Optional<User> findByEmail(String email);

    /**
     * Finds a user by their ID.
     *
     * @param id The ID of the user to find.
     * @return An `Optional` containing the user if found, otherwise empty.
     */
    Optional<User> findById(int id);

    /**
     * Soft deletes a user by setting their `statusUser` to 'DELETED'.
     * This marks the user as deleted without actually removing the record from the database.
     *
     * @param userId The ID of the user to soft delete.
     */
    @Modifying
    @Query("UPDATE User u SET u.statusUser = 'DELETED' WHERE u.id = :userId")
    void softUserDelete(@Param("userId") int userId);

    /**
     * Finds all users with a specific status.
     *
     * @param statusUser The status of the users to find (e.g., ACTIVE, DELETED).
     * @return A list of users with the specified status.
     */
    List<User> findAllByStatusUser(StatusUser statusUser);
}
