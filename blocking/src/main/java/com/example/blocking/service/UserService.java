package com.example.blocking.service;

import com.example.blocking.domain.User;
import com.example.blocking.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        // Simulating artificial delay to show blocking impact in tests
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }
}
