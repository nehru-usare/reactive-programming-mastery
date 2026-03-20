package com.example.blocking;

import com.example.blocking.domain.User;
import com.example.blocking.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.stream.IntStream;

@SpringBootApplication
public class BlockingApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlockingApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(UserRepository userRepository) {
        return args -> {
            if (userRepository.count() == 0) {
                IntStream.rangeClosed(1, 100).forEach(i -> {
                    userRepository.save(new User("User " + i, "user" + i + "@example.com"));
                });
                System.out.println("Inserted 100 users into Blocking DB");
            }
        };
    }
}
