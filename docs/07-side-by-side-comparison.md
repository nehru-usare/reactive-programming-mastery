# 07 - Side-By-Side Comparison

Now that we have built both applications, let's compare the code layer by layer.

## Controller Layer

**Blocking (`List<User>` & `User`)**
```java
@GetMapping
public List<User> getAll() {
    return userService.getAllUsers();
}

@GetMapping("/{id}")
public User getById(@PathVariable Long id) {
    return userService.getUserById(id);
}
```

**Reactive (`Flux<User>` & `Mono<User>`)**
```java
@GetMapping
public Flux<User> getAll() {
    return userService.getAllUsers();
}

@GetMapping("/{id}")
public Mono<User> getById(@PathVariable Long id) {
    return userService.getUserById(id);
}
```
*Notice:* The controller logic looks practically identical. Spring WebFlux abstracts away the heavy lifting. The key difference is simply the return types. WebFlux automatically subscribes to the `Mono` or `Flux` and writes the resulting data to the HTTP response continuously (in a non-blocking way).

## Service Layer

**Blocking (Waiting for data)**
```java
public User getUserById(Long id) {
    return userRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
}
```
Here, `userRepository.findById()` executes via JDBC. The JVM halts execution of the thread, goes to sleep, and wakes up when the database responds.

**Reactive (Building a pipeline)**
```java
public Mono<User> getUserById(Long id) {
    return userRepository.findById(id)
            .switchIfEmpty(Mono.error(new RuntimeException("Not found")));
}
```
Here, `userRepository.findById()` returns a `Mono` instantly. We attach an operator (`.switchIfEmpty`) to handle the case where the DB finds nothing. The thread never waits! It just builds the "recipe" (the pipeline calculation) and returns it.

## Repository Layer

**Blocking**
```java
public interface UserRepository extends JpaRepository<User, Long> { }
```
`JpaRepository` uses JDBC. It maintains active, blocking TCP sockets to your database. Connection pools (like HikariCP) are required to manage these threads.

**Reactive**
```java
public interface UserRepository extends ReactiveCrudRepository<User, Long> { }
```
`ReactiveCrudRepository` uses R2DBC. Instead of dedicating one thread per database connection, a single R2DBC Event Loop thread handles multiplexed data streams to the database asynchronously.

## Visualizing the Thread Difference
If 100 requests hit `/api/blocking/users/1` at exactly the same millisecond:
* **Tomcat:** Spawns/uses 100 threads immediately. All 100 threads execute `Thread.sleep(100)` (or DB wait) at the exact same time. Tomcat has 100 sleeping threads.
* **Netty:** 1 (or maybe 4) threads receive all 100 requests in rapid succession. The thread executes the R2DBC query for Request 1, gets the Mono, and moves on to Request 2 in microseconds. It fires off 100 queries without ever stopping. When the database finishes query 1, the thread takes the data and sends it over the network. 

---
**Next Up:** `08-advanced-reactive.md` - Powerful Operators!
