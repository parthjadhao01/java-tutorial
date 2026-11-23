# Spring Boot – Building Your First Application

## 1. Client-Server Architecture

Client-server architecture is a communication model where a client sends a request to a server, and the server processes it and returns a response.

Example:
- Client: Browser or Mobile Application.
- Server: Spring Boot Application.
- Communication: HTTP/HTTPS.

### Request Flow

```text
Browser (Client)
       |
       | HTTP Request
       v
Spring Boot Server
       |
       | Process Request
       v
HTTP Response
       |
       v
Browser (Client)
```

## 2. IP Address and DNS

### IP Address
- An IP address uniquely identifies a network interface on a network.
- It helps devices communicate with each other.

Example:
`142.250.183.14`

### DNS (Domain Name System)
- DNS translates human-readable domain names into IP addresses.
- It allows users to access websites using domain names instead of remembering IP addresses.

Example:

```text
google.com
     |
     v
    DNS
     |
     v
142.250.183.14
     |
     v
Google Server
```

**Remember:** DNS resolves domain names to IP addresses.

## 3. Port Numbers

- A port number identifies a particular application or network service on a device.
- Multiple applications can run on the same server using different ports.
- Port numbers range from 0 to 65535.

Example:

| Application | Port |
|---|---|
| HTTP | 80 |
| HTTPS | 443 |
| Spring Boot (default) | 8080 |
| MySQL (default) | 3306 |
| PostgreSQL (default) | 5432 |

### Why are ports required?

An IP address identifies the destination machine, while a port number identifies the destination application or service.

Example:

```text
http://localhost:8080
       |          |
       IP       Port
```

- `localhost` refers to your own machine.
- `8080` is the port where the Spring Boot application is listening.

**Important:** Two applications generally cannot listen on the same IP address and port combination using the same protocol simultaneously.

## 4. Spring Initializr

Spring Initializr is a tool used to generate a pre-configured Spring Boot project.

Website: https://start.spring.io/

### Why use Spring Initializr?

- Creates the project structure automatically.
- Configures the build system.
- Adds required dependencies.
- Generates the main application class.
- Saves time on manual configuration.

### Important Project Configurations

| Configuration | Purpose |
|---|---|
| Project | Maven or Gradle |
| Language | Java, Kotlin or Groovy |
| Spring Boot | Select Spring Boot version |
| Group | Organization or package namespace |
| Artifact | Project name |
| Name | Application name |
| Packaging | JAR or WAR |
| Java | Java version |
| Dependencies | Required libraries |

### Spring Web Dependency

Spring Web is used to build web applications and REST APIs.

It provides:
- Spring MVC.
- HTTP request handling.
- REST controller support.
- Embedded Tomcat through the standard Spring Boot web starter setup.

## 5. First Spring Boot Application

A typical Spring Boot application starts from the main class.

```java
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(
            DemoApplication.class, args
        );
    }
}
```

### Understanding the Annotations

**@SpringBootApplication**

It is a combination of three important annotations:

1. `@SpringBootConfiguration` – Marks the class as a configuration class.
2. `@EnableAutoConfiguration` – Enables Spring Boot auto-configuration.
3. `@ComponentScan` – Scans for Spring-managed components.

**SpringApplication.run()**

- Starts the Spring Boot application.
- Creates and initializes the Spring Application Context.
- Performs auto-configuration.
- Starts the embedded web server when applicable.

## 6. Creating the First REST API

A REST API allows clients to communicate with the backend using HTTP.

Example:

```java
@RestController
public class HelloController {

    @GetMapping("/")
    public String hello() {
        return "Hello World";
    }
}
```

### Understanding the Annotations

**@RestController**
- Marks a class as a REST controller.
- Combines `@Controller` and `@ResponseBody`.
- Return values are written directly into the HTTP response body.

**@GetMapping**
- Maps HTTP GET requests to a particular method.
- Used to handle requests for retrieving information.

Example:

```java
@GetMapping("/bye")
public String bye() {
    return "Good Bye";
}
```

Now the application has two endpoints:

| URL | Response |
|---|---|
| `http://localhost:8080/` | Hello World |
| `http://localhost:8080/bye` | Good Bye |

## 7. Changing the Default Server Port

Spring Boot uses port `8080` by default for a typical web application.

We can change it using `application.properties`.

File location:

`src/main/resources/application.properties`

Configuration:

```properties
server.port=9090
```

Now the application runs on port `9090`.

Example:

```text
http://localhost:9090/
http://localhost:9090/bye
```

**Important:** Changing the port requires restarting the application for the new configuration to take effect.

---

# 8. Complete Application Request Flow

```text
User enters URL in Browser
           |
           v
       DNS Lookup
           |
           v
     IP Address Found
           |
           v
     Port Connection
           |
           v
   Spring Boot Application
           |
           v
      Embedded Tomcat
           |
           v
       Spring MVC
           |
           v
    DispatcherServlet
           |
           v
      Controller
           |
           v
      HTTP Response
           |
           v
        Browser
```

**Remember:** Spring Boot simplifies application setup so developers can focus more on business logic instead of manually configuring infrastructure.

---

# 9. Interview Questions – Must Prepare

## A. Client-Server and Networking

1. What is client-server architecture?
2. How does a browser communicate with a backend server?
3. What is an IP address?
4. What is DNS?
5. Why do we need DNS?
6. What happens when you enter a domain name in a browser?
7. What is a port number?
8. Why do we need port numbers?
9. What is the default port of Spring Boot?
10. Can two applications run on the same port?
11. What is localhost?
12. What is the difference between an IP address and a port number?

## B. Spring Initializr and Project Setup

13. What is Spring Initializr?
14. Why do we use Spring Initializr?
15. What is the difference between Maven and Gradle?
16. What is a dependency in Spring Boot?
17. What is Spring Web?
18. Why do we add Spring Web dependency?
19. What is the difference between JAR and WAR packaging?
20. What is the purpose of the Group and Artifact fields?

## C. Spring Boot Application

21. What is `@SpringBootApplication`?
22. What are the three annotations combined inside `@SpringBootApplication`?
23. What is `SpringApplication.run()`?
24. What happens internally when a Spring Boot application starts?
25. What is an embedded server?
26. Why does Spring Boot use an embedded Tomcat server?
27. Can we run Spring Boot without an embedded server?

## D. REST API and Annotations

28. What is a REST API?
29. What is `@RestController`?
30. What is the difference between `@Controller` and `@RestController`?
31. What is `@GetMapping`?
32. What is the difference between `@GetMapping` and `@RequestMapping`?
33. Can we create multiple endpoints in one controller?
34. What is an endpoint?
35. What happens when a client sends a GET request to `/hello`?

## E. Configuration Questions

36. Where is `application.properties` located?
37. How do you change the default Spring Boot port?
38. What happens if port 8080 is already occupied?
39. Can we use YAML instead of properties files?
40. Why is externalized configuration important?

---

# 10. Scenario-Based Interview Questions

41. Your Spring Boot application is running, but you cannot access it through the browser. What would you check?

42. Your application is running on port 8080, but another application also needs port 8080. How would you solve this?

43. You want to create `/hello`, `/bye` and `/users` endpoints. How would you implement them?

44. If you remove the Spring Web dependency, what happens to your REST controller application?

45. Why can you run a Spring Boot application without installing Tomcat separately?

46. What happens internally when you access `http://localhost:8080/hello`?

47. If DNS is unavailable, can you still access a server using its IP address?

48. What is the difference between accessing `localhost:8080` and a publicly accessible server IP with a port?

---

# 11. Practical Revision Checklist

Before moving to the next lecture, make sure you can:

- [ ] Explain client-server communication.
- [ ] Explain IP address and DNS.
- [ ] Understand why port numbers are required.
- [ ] Generate a project using Spring Initializr.
- [ ] Explain the Spring Web dependency.
- [ ] Understand `@SpringBootApplication`.
- [ ] Explain `SpringApplication.run()`.
- [ ] Create a REST endpoint using `@RestController`.
- [ ] Map GET requests using `@GetMapping`.
- [ ] Change the server port using `application.properties`.

**Most important interview topics:** DNS, port numbers, Spring Initializr, `@SpringBootApplication`, embedded Tomcat, `@RestController`, `@GetMapping` and application startup flow.