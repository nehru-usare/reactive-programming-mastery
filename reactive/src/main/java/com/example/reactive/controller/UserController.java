package com.example.reactive.controller;

import com.example.reactive.domain.ApiResponse;
import com.example.reactive.domain.User;
import com.example.reactive.service.UserService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/reactive/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/thread-info")
    public Mono<String> getThreadInfo() {
        return Mono.just("Reactive API handled by thread: " + Thread.currentThread().getName() + 
                         "\nTotal Active JVM Threads: " + Thread.activeCount());
    }

    @GetMapping
    public Mono<ApiResponse<java.util.List<User>>> getAll() {
        System.out.println("[Reactive] Request served by thread: " + Thread.currentThread().getName());
        return userService.getAllUsers()
                .collectList() // CRUCIAL: Converts Flux<User> to Mono<List<User>>
                .map(users -> new ApiResponse<>(true, "Success", 200, users, null));
    }

    @GetMapping("/{id}")
    public Mono<ApiResponse<User>> getById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(user -> new ApiResponse<>(true, "User found", 200, user, null))
                // If switchIfEmpty throws an error, we handled it. Here we just fallback natively:
                .onErrorResume(e -> Mono.just(new ApiResponse<>(false, e.getMessage(), 404, null, java.util.List.of("User ID not found"))));
    }

    @PostMapping
    public Mono<ApiResponse<User>> create(@RequestBody User user) {
        return userService.createUser(user)
                .map(savedUser -> new ApiResponse<>(true, "Created", 201, savedUser, null));
    }
}
