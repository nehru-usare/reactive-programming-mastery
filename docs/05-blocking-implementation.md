# 05 - Blocking Implementation

Let's look at the standard approach of building an API in Spring Boot. This is what you see in 95% of Java applications today: Spring Web + Spring Data JPA.

## 1. The Domain Entity
We map a Java class to a database table using JPA annotations.
```java
@Entity
@Table(name = "users")
public class User { ... }
```

## 2. The Repository
We extend `JpaRepository`. Doing this gives us pre-built, synchronous methods like `findById()` and `save()`.
```java
public interface UserRepository extends JpaRepository<User, Long> { }
```

## 3. The Service
The service injects the repository and handles business logic. Notice the return types: `List<User>` and `User`.
When `userRepository.findById(id)` is called, the current HTTP thread **STOPS** and waits for the database to return the user before it can proceed to the `return` statement.

```java
public User getUserById(Long id) {
    // Because JPA uses JDBC, this makes the web thread block until DB responds.
    return userRepository.findById(id).orElseThrow();
}
```

## 4. The Controller
The `RestController` maps HTTP requests to service calls. Again, entirely synchronous.
```java
@GetMapping("/{id}")
public User getById(@PathVariable Long id) {
    return userService.getUserById(id);
}
```

### Observation
While this code is extremely easy to write and read (step 1 completes, step 2 starts), it relies on an abundance of idle threads when dealing with high latency, causing scalability issues under extreme load. 

---
**Next Up:** `06-reactive-implementation.md` - Doing the exact same thing, but Reactively!
