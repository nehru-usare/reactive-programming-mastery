package com.example.reactive.service;

import com.example.reactive.domain.User;
import com.example.reactive.repository.UserRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.Duration;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Flux<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Mono<User> getUserById(Long id) {
        // Simulating artificial delay WITHOUT blocking the thread!
        // delayElement puts the processing on hold but frees the underlying thread.
        return userRepository.findById(id)
                .delayElement(Duration.ofMillis(100))
                .switchIfEmpty(Mono.error(new RuntimeException("User not found")));
    }

    public Mono<User> createUser(User user) {
        return userRepository.save(user);
    }
}
