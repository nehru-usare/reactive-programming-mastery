# 08 - Advanced Reactive Operators

Once you have a `Mono` or `Flux`, you rarely just return it. You usually want to transform, filter, or combine the data asynchronously. Project Reactor has hundreds of operators to modify the data stream.

## Transforming Data: `.map()` vs `.flatMap()`

### `map(Function<T, U>)`
Use `map` for simple, **synchronous** transformations. 
```java
// Convert Mono<User> to Mono<String> (User's name)
Mono<String> nameMono = userRepository.findById(id)
    .map(user -> user.getName().toUpperCase());
```

### `flatMap(Function<T, Mono<U>>)`
Use `flatMap` for **asynchronous** transformations. If your transformation requires calling *another* reactive method (like a 3rd party API), you must use `flatMap` instead of `map`.

```java
// WRONG: Using map() for async calls
Mono<Mono<Order>> orders = userRepository.findById(id)
    .map(user -> orderService.getOrdersForUser(user.getId())); 
// Returns a nested Mono<Mono<...>>. Unusable!

// CORRECT: Using flatMap()
Mono<Order> orders = userRepository.findById(id)
    .flatMap(user -> orderService.getOrdersForUser(user.getId())); 
// Flattens the nested Mono back into a single stream.
```

## Error Handling

In blocking code, we use `try-catch`. In reactive code, errors are just another type of event in the stream! 

### `onErrorReturn()`
Provide a fallback value if an error occurs.
```java
userService.getUserById(id)
    .onErrorReturn(new User("Anonymous", "fallback@email.com"));
```

### `onErrorResume()`
Provide a fallback *Publisher* if an error occurs (e.g., query a backup database).
```java
userRepository.findById(id)
    .onErrorResume(error -> backupUserRepository.findById(id));
```

## Combining Streams
Often, you need to execute two queries in parallel and combine their results. In blocking code, this requires complex `CompletableFuture` setups. In WebFlux, it's trivial:

```java
Mono<User> userMono = userRepository.findById(1L);
Mono<Account> accountMono = accountRepository.findByUserId(1L);

// Run them both in parallel, wait for both to finish, and combine them!
Mono<UserProfile> profile = Mono.zip(userMono, accountMono)
    .map(tuple -> new UserProfile(tuple.getT1(), tuple.getT2()));
```

---
**Next Up:** `09-real-world-use-cases.md` - When to actually use this stuff.
