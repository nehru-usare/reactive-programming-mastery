# 06 - Reactive Implementation

Now, let's look at how the exact same CRUD operations are built using Spring WebFlux and R2DBC.

## 1. The Domain Entity
Unlike JPA, Spring Data R2DBC does not use standard `@Entity` annotations. It uses its own lightweight `@Table` and `@Id` from `org.springframework.data.annotation`.

```java
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("users")
public class User {
    @Id
    private Long id;
    private String name;
    private String email;
}
```
*Note: R2DBC does not have `ddl-auto=update` like Hibernate. You typically need a `schema.sql` to create the table at startup.*

## 2. The Repository
Instead of `JpaRepository`, we extend `ReactiveCrudRepository`. 
Notice the return types! They are no longer `User` or `List<User>`. They are `Mono<User>` and `Flux<User>`.
```java
public interface UserRepository extends ReactiveCrudRepository<User, Long> { }
```

## 3. The Service
The service methods now return standard Project Reactor types.

```java
public Flux<User> getAllUsers() {
    return userRepository.findAll();
}

public Mono<User> getUserById(Long id) {
    return userRepository.findById(id)
        .switchIfEmpty(Mono.error(new RuntimeException("User not found")));
}
```
**The vital difference:** When `userRepository.findById(id)` is called, a network request is sent to the db, but the Netty thread **IMMEDIATELY** returns the `Mono<User>` to the caller. The thread goes back to the connection pool to serve someone else. When the DB is ready, the Event Loop drops the data into the Mono, and it gets pushed to the client.

## 4. The Controller
The controller maps the methods exactly like Spring MVC. The only difference is the return types. WebFlux handles the subscription to these publishers automatically.

```java
@GetMapping("/{id}")
public Mono<User> getById(@PathVariable Long id) {
    return userService.getUserById(id);
}
```

## Side-by-Side Comparison Next
We've now built two fully functioning microservices that do the exact same thing but with completely different underlying thread models. In the next section, we will review the differences side-by-side.

---
**Next Up:** `07-side-by-side-comparison.md`
