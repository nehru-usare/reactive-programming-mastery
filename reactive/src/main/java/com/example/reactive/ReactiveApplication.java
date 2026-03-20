package com.example.reactive;

import com.example.reactive.domain.User;
import com.example.reactive.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class ReactiveApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReactiveApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(UserRepository userRepository) {
        return args -> {
            userRepository.count().flatMap(count -> {
                if (count == 0) {
                    return Flux.range(1, 100)
                            .flatMap(i -> userRepository.save(new User("User " + i, "user" + i + "@example.com")))
                            .then();
                }
                return reactor.core.publisher.Mono.empty();
            }).block();
            System.out.println("Checked/Inserted 100 users into Reactive DB");
        };
    }
}
