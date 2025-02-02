package org.example.vetclinic.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.vetclinic.entity.User;
import org.example.vetclinic.repository.UserRepository;
import org.example.vetclinic.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {

        return userRepository.findByEmail(email);
    }
}
