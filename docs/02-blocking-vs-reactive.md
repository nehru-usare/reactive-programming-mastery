# 02 - Blocking vs Reactive: The Thread Model

To truly master reactive programming, you need a deep understanding of *what* blocks and *why* it matters. This chapter compares the thread models of traditional Spring MVC (Blocking) and Spring WebFlux (Reactive).

## 1. The Traditional (Blocking) Model
Spring MVC applications typically run on a servlet container like **Tomcat**. It operates on a **Thread-per-Request** model.

### How it works:
1. Tomcat maintains a pool of worker threads (default `server.tomcat.threads.max=200`).
2. Every time a new HTTP request arrives, Tomcat assigns one dedicated thread from the pool to handle it.
3. This thread executes the entire request: routing, business logic, database queries, and sending the response.

### The Problem (`Thread.sleep()` or `I/O Blocking`)
If your application needs to fetch data from a database, it calls a repository method (e.g., `userRepository.findById(1)`). 
The database might take 500 milliseconds to find the data and return it over the network. 

During those 500ms, the Tomcat thread **does absolutely nothing**. It is put into a `WAITING` state by the operating system. It cannot be used to serve any other HTTP request. If you get 200 concurrent requests that all query this slow database, your entire thread pool gets exhausted, and request #201 will either be queued or dropped with a Timeout/Connection Refused error.

## 2. The Reactive (Non-Blocking) Model
Spring WebFlux runs on a non-blocking server like **Netty**. It operates on an **Event-Loop** model.

### How it works:
1. Netty uses a very small number of worker threads, usually equivalent to the number of CPU cores (e.g., 4 or 8 threads). This is called the Event Loop group.
2. A single thread can handle thousands of concurrent requests because it relies on non-blocking I/O (NIO) provided by the OS (like `epoll` on Linux). 

### Overcoming the Problem (`Non-Blocking I/O`)
Let's look at the database example again. A request comes in, and the Netty thread tells the database (via a non-blocking driver like R2DBC): *"Hey, get me user ID 1. Let me know when you're done."*
**Crucially, the thread does NOT wait.** 

The thread immediately returns to the Event Loop to handle request #2, #3, and so on. 
500ms later, the database finishes retrieving the data and sends an event (a signal) back to the application. The Event Loop picks up this event, assigns an available thread to take the data, format it into JSON, and send it back to the client.

## 3. Performance Differences
* **CPU & Memory:** Threads consume memory (usually ~1MB stack size) and context switching between 200 threads is CPU intensive. Reactive apps use very few threads, drastically reducing memory footprint and context switching overhead.
* **Latency vs Throughput:** Under light load, Blocking and Reactive perform identically. Reactive programming doesn't make a single request faster (in fact, due to the overhead of the reactive framework, a single request might be milliseconds slower). **However, under heavy load, Reactive shines.** Reactive drastically increases *Throughput* (requests per second) and prevents your application from crashing when external dependencies are slow.

## When to Use Which?
* **Use Blocking (Spring MVC + JPA) when:**
  * Your application does not face extreme concurrent load.
  * You are writing simple CRUD applications where developer productivity is the absolute highest priority.
  * You rely heavily on libraries that do not have non-blocking alternatives (e.g., JPA/Hibernate is strictly blocking).
* **Use Reactive (Spring WebFlux + R2DBC) when:**
  * You are building high-traffic microservices handling thousands of requests per second.
  * Your app makes many network calls to other slow services or APIs (e.g., API Gateway, BFF).
  * Streaming live data (e.g., Server-Sent Events, WebSockets).

---
**Next Up:** `03-reactive-basics.md` - Publishers, Subscribers, Mono, and Flux!
