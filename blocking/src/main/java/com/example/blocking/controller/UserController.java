package com.example.blocking.controller;

import com.example.blocking.domain.ApiResponse;
import com.example.blocking.domain.User;
import com.example.blocking.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blocking/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/thread-info")
    public String getThreadInfo() {
        return "Blocking API handled by thread: " + Thread.currentThread().getName() + 
               "\nTotal Active JVM Threads: " + Thread.activeCount();
    }

    @GetMapping
    public ApiResponse<java.util.List<User>> getAll() {
        System.out.println("[Blocking] Request served by thread: " + Thread.currentThread().getName());
        java.util.List<User> users = userService.getAllUsers();
        return new ApiResponse<>(true, "Success", 200, users, null);
    }

    @GetMapping("/{id}")
    public ApiResponse<User> getById(@PathVariable Long id) {
        try {
            User user = userService.getUserById(id);
            return new ApiResponse<>(true, "User found", 200, user, null);
        } catch (Exception e) {
            return new ApiResponse<>(false, e.getMessage(), 404, null, java.util.List.of("User ID not found"));
        }
    }

    @PostMapping
    public ApiResponse<User> create(@RequestBody User user) {
        User savedUser = userService.createUser(user);
        return new ApiResponse<>(true, "Created", 201, savedUser, null);
    }
}
