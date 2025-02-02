package org.example.vetclinic.repository;

import org.example.vetclinic.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository  extends JpaRepository<User, Integer>  {

    Optional<User> findByEmail(String email);
}
