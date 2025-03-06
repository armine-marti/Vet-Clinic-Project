package org.example.vetclinic.repository;

import org.example.vetclinic.entity.StatusUser;
import org.example.vetclinic.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    Optional<User> findById(int id);

    @Modifying
    @Query("UPDATE User u SET u.statusUser = 'DELETED' WHERE u.id = :userId")
    void softUserDelete(@Param("userId") int userId);

    List<User> findAllByStatusUser(StatusUser statusUser);
}
