# Spring Framework & Spring Boot – Introduction

## 1. Client-Server Architecture

Client-server architecture is a communication model where:

- **Client:** Sends requests (Browser, Mobile App).
- **Server:** Processes requests and returns responses.

Communication follows a **Request-Response Model**.

Example: A browser requests products from an e-commerce server, and the server returns product information.

## 2. HTTP / HTTPS Protocol

HTTP (Hypertext Transfer Protocol) is used for communication between clients and servers.

- **HTTP:** Transfers data between client and server.
- **HTTPS:** Secure version of HTTP that encrypts communication using TLS.

Common HTTP methods:
- GET – Retrieve data.
- POST – Create data.
- PUT – Update/replace data.
- PATCH – Partially update data.
- DELETE – Delete data.

## 3. Servlet & Servlet Container

### Servlet
A Servlet is a Java class that handles HTTP requests and generates HTTP responses.

### Servlet Container
A Servlet Container manages Servlets, their lifecycle and request processing.

Responsibilities:
- Receives HTTP requests.
- Creates and manages Servlet instances.
- Calls the appropriate Servlet methods.
- Manages Servlet lifecycle.
- Sends HTTP responses.

Examples: Apache Tomcat, Jetty, Undertow.

## 4. Why Spring Framework?

Traditional Servlet-based development had several problems:

1. **Tight Coupling:** Classes were highly dependent on each other.
2. **Manual Dependency Management:** Developers had to create and connect objects manually.
3. **Boilerplate Code:** Repetitive code for request handling and other operations.
4. **Difficult Testing:** Tightly coupled classes were difficult to test independently.
5. **Complex Maintenance:** Changes in one component could affect other components.

### How Spring solves these problems

Spring Framework simplifies Java application development using:

- **IoC (Inversion of Control):** Transfers object creation and management responsibility from application code to the Spring Container.
- **DI (Dependency Injection):** Provides required dependencies to objects instead of having them create dependencies themselves.
- **Spring Bean Management:** Spring creates, configures and manages application objects called Beans.

## 5. Spring Architecture

Spring Framework provides different modules, while Spring Boot simplifies the development and configuration of Spring applications.

### Spring Framework Modules

| Module | Responsibility |
|---|---|
| Spring Core | IoC, DI and Bean Management |
| Spring MVC | Handles web requests and responses |
| Spring AOP | Cross-cutting concerns like logging and transactions |
| Spring Data | Simplifies database operations |
| Spring Security | Authentication and authorization |

### Other Spring Ecosystem Projects

- **Spring Boot:** Simplifies configuration, application startup and auto-configuration.
- **Spring Cloud:** Provides tools for distributed systems and microservices.
- **Spring AI:** Integrates AI models and AI capabilities into applications.
- **Spring Batch:** Supports large-scale batch processing.

### Architecture Diagram

```text
                 SPRING ECOSYSTEM
                        |
          +-------------+-------------+
          |                           |
    SPRING FRAMEWORK              SPRING BOOT
          |                           |
     +----+----------------+          |
     |    |       |        |          |
   Core  MVC     AOP     Other        |
     |                     Modules    |
     |                               |
     +-------------------------------+
             Works Together
                  
     OTHER SPRING ECOSYSTEM PROJECTS
          |
     +----+-------+---------+
     |            |         |
 Spring Cloud  Spring AI  Spring Batch
```

**Important relationships:**
- Spring Core provides the foundation for dependency injection and bean management.
- Spring MVC, Spring AOP and other modules provide different capabilities.
- Spring Boot is built on Spring Framework; it does not replace it.
- Spring Security is a separate project that integrates with Spring applications.
- Spring AI and Spring Cloud are separate projects in the broader Spring ecosystem.

## 6. Why Spring Boot?

Spring Framework applications traditionally required considerable manual configuration.

Spring Boot simplifies development through:

1. **Auto-Configuration:** Automatically configures components based on dependencies and application settings.
2. **Starter Dependencies:** Provides convenient dependency bundles.
3. **Embedded Servers:** Supports servers like Tomcat without requiring separate deployment.
4. **Production-Ready Features:** Provides monitoring and management capabilities through Spring Boot Actuator.

Example:

```java
@SpringBootApplication
public class MyApplication {

    public static void main(String[] args) {
        SpringApplication.run(
            MyApplication.class, args
        );
    }
}
```

This is the entry point of a typical Spring Boot application.

---

# Interview Questions – Must Prepare

## Basic Questions

1. What is Client-Server Architecture?
2. What is the difference between HTTP and HTTPS?
3. What happens when you enter a URL in a browser?
4. What is a Servlet in Java?
5. What is a Servlet Container?
6. What is the difference between a Servlet and a Servlet Container?
7. What is the Servlet lifecycle?
8. What is the difference between GET and POST?

## Spring Framework Questions

9. Why was Spring Framework introduced?
10. What problems does Spring Framework solve?
11. What is Inversion of Control (IoC)?
12. What is Dependency Injection (DI)?
13. What is the difference between IoC and DI?
14. What is a Spring Bean?
15. What is the responsibility of the Spring IoC Container?
16. What is tight coupling and loose coupling?
17. How does Spring achieve loose coupling?

## Spring Ecosystem Questions

18. What is the difference between Spring Framework and Spring Boot?
19. What is Spring MVC?
20. What is Spring Data?
21. What is Spring Security?
22. What is Spring AOP?
23. What is Spring Cloud?
24. What is Spring AI?
25. Does Spring Boot replace Spring Framework?
26. How does Spring Boot auto-configuration work?

## Scenario-Based Questions

27. Why would you use Spring instead of traditional Servlets?
28. If Spring Boot provides auto-configuration, why should we learn Spring Framework internals?
29. How does Spring manage object dependencies?
30. Can we use Spring Framework without Spring Boot?
31. Can we use Spring Boot without Spring MVC?
32. Why is loose coupling important in large applications?

**Priority:** Understand questions 9–18 and 25–30 particularly well. They establish the foundation for almost every upcoming Spring Boot topic.