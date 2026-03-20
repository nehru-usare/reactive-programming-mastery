# 04 - Project Setup & Dependencies

In Phase 1, we set up a multi-module Maven project. Let's break down how it is structured and the purpose of the key dependencies in both the Blocking and Reactive implementations.

## Directory Structure
To isolate the configurations cleanly, we have two different Spring Boot applications in one parent project:
* `/blocking` - Contains traditional Spring MVC and Spring Data JPA setup.
* `/reactive` - Contains Spring WebFlux and Spring Data R2DBC setup.

This division ensures that blocking dependencies (like Hibernate) do not accidentally leak into our non-blocking application.

## 1. Blocking Module Dependencies (`/blocking/pom.xml`)

* **spring-boot-starter-web:** Brings in Spring MVC, REST controllers, and importantly, an embedded **Tomcat** server. Tomcat is a traditional servlet container that uses the thread-per-request model.
* **spring-boot-starter-data-jpa:** Brings in Hibernate and the standard Java Persistence API. JPA relies on JDBC. **JDBC is strictly blocking**. When JDBC executes a query, the calling thread is frozen until the query completes.
* **h2:** An in-memory relational database.

Because of `spring-boot-starter-web` and `spring-boot-starter-data-jpa`, the entire request pipeline—from listening to the HTTP socket to querying the database—is synchronous and blocking.

## 2. Reactive Module Dependencies (`/reactive/pom.xml`)

* **spring-boot-starter-webflux:** Replaces `starter-web`. It brings in the Spring WebFlux framework and an embedded **Netty** server. Netty is an asynchronous, event-driven network application framework.
* **spring-boot-starter-data-r2dbc:** The reactive alternative to JPA. **R**eactive **R**elational **D**atabase **C**onnectivity. Unlike JDBC, R2DBC provides fully non-blocking APIs to interact with relational databases.
* **r2dbc-h2:** The non-blocking database driver for H2.

Because of WebFlux and R2DBC, the pipeline is entirely asynchronous. The HTTP request is taken by a non-blocking Netty thread, passed to R2DBC which makes a non-blocking call to the database. At no point is a thread put to sleep waiting for I/O.

## Starting the Applications
Both applications are configured to use an H2 in-memory database to avoid needing external Docker containers. 
* The blocking app runs on `localhost:8080`.
* The reactive app runs on `localhost:8081`.

---
**Next Up:** `05-blocking-implementation.md` - Let's write the Blocking code!
