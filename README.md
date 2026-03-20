# Reactive Programming Mastery

Welcome to **Reactive Programming Mastery**! This project serves as a comprehensive guide and demonstration to learn and compare traditional blocking programming with modern reactive (non-blocking) programming using Spring Boot.

## 📖 Overview

The goal of this project is to showcase the differences in architecture, performance, and programming models between a typical synchronous Spring Boot application and an asynchronous, reactive one. It includes two distinct modules that implement similar functionality using different paradigms.

## 🏗️ Project Structure

This is a multi-module Maven project consisting of the following sub-modules:

### 1. `blocking`
A traditional Spring Boot application built using blocking I/O.
- **Web Layer:** Spring Web MVC (`spring-boot-starter-web`)
- **Data Layer:** Spring Data JPA (`spring-boot-starter-data-jpa`)
- **Database:** H2 Database
- **Execution Model:** One thread per request (synchronous and blocking).

### 2. `reactive`
A modern Spring Boot application built using non-blocking I/O.
- **Web Layer:** Spring WebFlux (Reactive Web)
- **Data Layer:** Spring Data R2DBC / Reactive NoSQL
- **Execution Model:** Event-loop based, non-blocking execution using Project Reactor (`Mono` and `Flux`).

## 🛠️ Technologies Used

- **Java 17**
- **Spring Boot 3.2.4**
- **Maven** (Multi-module setup)
- **Spring Web MVC & Spring WebFlux**
- **Spring Data JPA & Spring Data R2DBC**

## 🚀 Getting Started

### Prerequisites
- JDK 17 or higher
- Maven 3.8+ 

### Building the Project
To build the entire project and install dependencies, run the following command from the root directory:

```bash
mvn clean install
```

### Running the Applications

#### Running the Blocking App
Navigate to the `blocking` directory and use the Spring Boot Maven plugin to run the application:
```bash
cd blocking
mvn spring-boot:run
```

#### Running the Reactive App
Navigate to the `reactive` directory and use the Spring Boot Maven plugin:
```bash
cd reactive
mvn spring-boot:run
```

## 💡 Interview Questions (Reactive vs Blocking)

Here are some common interview questions related to the concepts demonstrated in this project:

1. **What is Reactive Programming, and how does it differ from traditional blocking programming?**
   - *Hint:* Discuss asynchronous, non-blocking, event-driven, and data stream concepts versus the thread-per-request model.
2. **What is the difference between `Mono` and `Flux` in Project Reactor?**
   - *Hint:* `Mono` emits 0 or 1 element, `Flux` emits 0 to N elements.
3. **What is backpressure in Reactive Streams?**
   - *Hint:* It's a mechanism that allows a subscriber to control the rate at which a publisher emits data, preventing the subscriber from being overwhelmed.
4. **Why is Spring WebFlux not always faster than Spring Web MVC?**
   - *Hint:* Reactive programming doesn't make code run faster; it allows an application to handle a higher volume of concurrent requests with fewer threads (better resource utilization under load).
5. **Can you use JPA/Hibernate in a fully reactive Spring Boot application?**
   - *Hint:* No, typical JDBC and JPA are inherently blocking. You need to use R2DBC for relational databases, or reactive drivers for NoSQL (MongoDB, Cassandra, Redis, etc.) to achieve a truly non-blocking stack end-to-end.

## 🤝 Contributing
Contributions, issues, and feature requests are welcome! Feel free to check the issues page.

## 📝 License
This project is open-source and available under the [MIT License](LICENSE).
