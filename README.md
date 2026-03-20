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

## 🤝 Contributing
Contributions, issues, and feature requests are welcome! Feel free to check the issues page.

## 📝 License
This project is open-source and available under the [MIT License](LICENSE).
