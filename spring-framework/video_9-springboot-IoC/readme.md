# Spring Boot – Revision Notes & Interview Questions

## 1. Introduction to Spring Boot

### What is Spring Boot?

Spring Boot is a Java-based framework built on top of the Spring Framework that simplifies the development of production-ready applications.

It reduces the configuration and setup required when developing applications using traditional Spring Core.

### Why Spring Boot?

In traditional Spring applications, developers need to configure several things manually, such as:

- Creating and configuring beans.
- Managing dependencies.
- Configuring application servers.
- Setting up the application context.
- Configuring database connections.
- Managing XML or Java-based configuration.

Spring Boot simplifies these tasks through:

1. **Auto-Configuration:** Automatically configures beans based on dependencies available in the project.
2. **Starter Dependencies:** Provides pre-configured dependency groups.
3. **Embedded Servers:** Provides embedded servers like Tomcat.
4. **Convention over Configuration:** Provides sensible default configurations.
5. **Production-Ready Features:** Supports monitoring, health checks, and application metrics through Spring Boot Actuator.

### Spring Core vs Spring Boot

| Spring Core | Spring Boot |
|---|---|
| Requires more manual configuration. | Reduces manual configuration. |
| Dependencies are managed manually. | Provides starter dependencies. |
| External server configuration is commonly required. | Supports embedded servers. |
| More initial setup. | Faster application setup. |
| Auto-configuration is not provided by default. | Provides auto-configuration. |

**Important:** Spring Boot does not replace Spring Framework. It simplifies the development of Spring applications.

---

## 2. Project Setup Using Maven and Spring Initializr

### What is Spring Initializr?

Spring Initializr is a web-based tool used to generate Spring Boot project structures.

Website: https://start.spring.io/

It allows developers to configure:

- Project type: Maven or Gradle.
- Programming language: Java, Kotlin, or Groovy.
- Spring Boot version.
- Project metadata.
- Java version.
- Dependencies.

It generates a ready-to-use project structure.

### What is Maven?

Maven is a build automation and dependency management tool used in Java projects.

It helps developers:

- Download and manage dependencies.
- Compile Java source code.
- Run tests.
- Package applications.
- Manage project builds.

Maven uses the `pom.xml` file to manage project configuration.

### What is `spring-boot-starter`?

A Spring Boot starter is a dependency that groups commonly required libraries for a particular functionality.

Instead of adding multiple dependencies individually, developers can add a single starter dependency.

For example:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

The `spring-boot-starter-web` dependency provides libraries required to build web applications, including:

- Spring MVC.
- Embedded Tomcat.
- JSON processing support.
- Other required dependencies.

### What is `spring-boot-starter-parent`?

`spring-boot-starter-parent` is a Maven parent POM provided by Spring Boot.

It provides:

1. Default dependency versions.
2. Dependency management.
3. Maven plugin management.
4. Default build configurations.
5. Compatible dependency versions.

Example:

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.5.0</version>
    <relativePath/>
</parent>
```

**Important:** The version above is an example. Use a compatible version for your project.

### Difference Between Starter and Starter Parent

| spring-boot-starter | spring-boot-starter-parent |
|---|---|
| A dependency starter. | A Maven parent POM. |
| Provides libraries for specific functionality. | Manages dependency and plugin versions. |
| Example: starter-web. | Example: starter-parent. |
| Added under `<dependencies>`. | Configured under `<parent>`. |

---

## 3. @SpringBootApplication Annotation

### What is @SpringBootApplication?

`@SpringBootApplication` is the main annotation used to mark the entry point of a Spring Boot application.

It combines three important annotations:

1. `@SpringBootConfiguration`
2. `@EnableAutoConfiguration`
3. `@ComponentScan`

Example:

```java
@SpringBootApplication
public class MyApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }
}
```

### 3.1 @SpringBootConfiguration

- Indicates that the class provides Spring configuration.
- It is a specialized version of `@Configuration`.
- Allows the class to define Spring beans.

Example:

```java
@Configuration
public class AppConfig {

    @Bean
    public MyService myService() {
        return new MyService();
    }
}
```

### 3.2 @EnableAutoConfiguration

- Enables Spring Boot's auto-configuration mechanism.
- Automatically configures beans based on the dependencies available in the classpath.
- Uses conditional annotations to decide which configurations should be applied.

Example:

If `spring-boot-starter-web` is present, Spring Boot can automatically configure the web application infrastructure.

### 3.3 @ComponentScan

- Scans packages to identify Spring-managed components.
- Registers discovered components as beans in the Spring IoC container.

Common annotations detected during component scanning:

- `@Component`
- `@Service`
- `@Repository`
- `@Controller`
- `@RestController`

**Important:** By default, component scanning starts from the package containing the main application class and includes its subpackages.

### Relationship Between the Three Annotations

| Annotation | Responsibility |
|---|---|
| @SpringBootConfiguration | Marks the class as a configuration class. |
| @EnableAutoConfiguration | Enables automatic configuration. |
| @ComponentScan | Discovers application components. |

---

## 4. Spring Boot Auto-Configuration

### What is Auto-Configuration?

Auto-configuration is a Spring Boot feature that automatically configures application components based on:

- Dependencies available in the classpath.
- Existing beans in the application context.
- Application properties.
- Other configuration conditions.

It reduces the need for developers to manually configure commonly used components.

### Example: Automatic Tomcat Configuration

Suppose we add the following dependency:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

What happens?

1. Maven downloads the required dependencies.
2. The Spring MVC libraries become available in the classpath.
3. Embedded Tomcat becomes available in the classpath.
4. Spring Boot detects the relevant classes.
5. Auto-configuration configures the web application infrastructure.
6. Tomcat starts when the Spring Boot application runs.

This is an example of dependency-driven auto-configuration.

### How Does Auto-Configuration Work?

Spring Boot uses conditional annotations to determine whether a particular configuration should be applied.

Two important conditional annotations are:

### 4.1 @ConditionalOnClass

This annotation enables a configuration only when a specified class is available in the classpath.

Example:

```java
@Configuration
@ConditionalOnClass(DataSource.class)
public class DatabaseConfiguration {

}
```

**Meaning:** This configuration is eligible only if `DataSource.class` is available.

### 4.2 @ConditionalOnMissingBean

This annotation enables a configuration only when a particular bean does not already exist in the Spring application context.

Example:

```java
@Configuration
public class MyConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public MyService myService() {
        return new MyService();
    }
}
```

**Meaning:** Spring creates this bean only if a matching bean is not already present.

This allows developers to override Spring Boot's default configurations with their own implementations.

### How Spring Boot Decides What to Configure

Consider the following scenario:

- A project contains `spring-boot-starter-web`.
- Spring Boot detects web-related classes.
- Required conditions are satisfied.
- Spring Boot configures the web application infrastructure.
- If the developer provides a custom bean that replaces a default bean, the missing-bean condition may prevent the default bean from being created.

**Important:** Auto-configuration is not unconditional. It depends on whether the required conditions are satisfied.

---

## 5. Dependency Management and Embedded Tomcat

### Why Does Tomcat Start Automatically?

When we add `spring-boot-starter-web`, it brings the necessary web dependencies, including an embedded Tomcat server by default.

Spring Boot detects the required classes and applies relevant auto-configurations.

When the application starts:

1. `SpringApplication.run()` is executed.
2. Spring Boot creates the application context.
3. Auto-configuration is applied.
4. The web application infrastructure is initialized.
5. The embedded Tomcat server starts.
6. The application begins accepting HTTP requests.

### What is an Embedded Server?

An embedded server is a server packaged as part of the application rather than requiring a separately installed and configured application server.

Spring Boot supports embedded servers such as:

- Tomcat.
- Jetty.
- Undertow (with the appropriate dependency).

### Advantages of Embedded Servers

- No separate server installation is required.
- Simplifies application deployment.
- Makes applications easier to run.
- Supports executable JAR packaging.
- Simplifies containerization using Docker.

**Important:** Tomcat is the default embedded servlet container for Spring Boot web applications using the standard servlet stack, but it can be replaced with another supported server.

---

# 6. Important Concepts – Quick Revision

| Concept | Explanation |
|---|---|
| Spring Boot | Framework built on Spring that simplifies application development. |
| Spring Initializr | Tool for generating Spring Boot project structures. |
| Maven | Build automation and dependency management tool. |
| pom.xml | Maven project configuration file. |
| Starter Dependency | Groups commonly required libraries. |
| Starter Parent | Provides dependency and plugin management. |
| @SpringBootApplication | Combines three important Spring Boot annotations. |
| @SpringBootConfiguration | Marks a Spring Boot configuration class. |
| @EnableAutoConfiguration | Enables conditional automatic configuration. |
| @ComponentScan | Discovers Spring-managed components. |
| Auto-Configuration | Automatically configures components based on project conditions. |
| @ConditionalOnClass | Checks whether a class is available in the classpath. |
| @ConditionalOnMissingBean | Checks whether a matching bean is missing. |
| Embedded Tomcat | Web server packaged within the application. |
| Classpath | Locations from which Java loads classes and resources. |

---

# 7. Interview Questions and Answers

## Basic Level

### Q1. What is Spring Boot?

**Answer:**

Spring Boot is a framework built on top of Spring that simplifies Java application development by providing auto-configuration, starter dependencies, embedded servers, and production-ready features.

### Q2. Why do we use Spring Boot instead of traditional Spring?

**Answer:**

Spring Boot reduces manual configuration, simplifies dependency management, provides embedded servers, and allows developers to build and deploy applications quickly.

### Q3. Does Spring Boot replace the Spring Framework?

**Answer:**

No. Spring Boot is built on top of the Spring Framework. It simplifies the configuration and development of Spring-based applications.

### Q4. What is Spring Initializr?

**Answer:**

Spring Initializr is a project generation tool that allows developers to create Spring Boot projects by selecting project metadata, build tools, Java versions, and dependencies.

### Q5. What is Maven?

**Answer:**

Maven is a build automation and dependency management tool used in Java projects. It uses `pom.xml` to manage dependencies, plugins, and project build configurations.

### Q6. What is a Spring Boot starter dependency?

**Answer:**

A starter dependency is a predefined collection of dependencies required for a particular functionality.

For example, `spring-boot-starter-web` provides dependencies required to develop web applications.

### Q7. What is spring-boot-starter-parent?

**Answer:**

It is a Maven parent POM that provides default dependency versions, plugin management, and build configurations for Spring Boot projects.

### Q8. What is the difference between spring-boot-starter-web and spring-boot-starter-parent?

**Answer:**

`spring-boot-starter-web` provides libraries required to build web applications, whereas `spring-boot-starter-parent` provides Maven dependency and plugin management.

### Q9. What is an embedded server?

**Answer:**

An embedded server is packaged inside the application, eliminating the need to install and configure an external server separately.

Examples include Tomcat, Jetty, and Undertow.

### Q10. Which embedded server does Spring Boot use by default?

**Answer:**

Tomcat is the default embedded servlet container for standard Spring Boot web applications.

---

## Intermediate Level

### Q11. What is @SpringBootApplication?

**Answer:**

It is the primary annotation used to mark the main Spring Boot application class.

It combines:

1. `@SpringBootConfiguration`
2. `@EnableAutoConfiguration`
3. `@ComponentScan`

### Q12. What is the purpose of @EnableAutoConfiguration?

**Answer:**

It enables Spring Boot's auto-configuration mechanism, which configures application components based on available dependencies, existing beans, and configuration conditions.

### Q13. What is the purpose of @ComponentScan?

**Answer:**

It scans specified packages to identify Spring-managed components and registers them as beans in the application context.

### Q14. What is the difference between @Configuration and @SpringBootConfiguration?

**Answer:**

`@Configuration` marks a class as a source of bean definitions.

`@SpringBootConfiguration` is a specialized version of `@Configuration` intended to identify the primary Spring Boot configuration class.

### Q15. What is Spring Boot auto-configuration?

**Answer:**

Auto-configuration is a mechanism that automatically configures Spring application components based on classpath dependencies, existing beans, and configuration properties.

### Q16. How does Spring Boot know which configurations to apply?

**Answer:**

Spring Boot uses conditional annotations and auto-configuration classes to determine which configurations should be applied based on the current application environment.

### Q17. What is @ConditionalOnClass?

**Answer:**

It is a conditional annotation that applies a configuration only when a specified class is present in the classpath.

### Q18. What is @ConditionalOnMissingBean?

**Answer:**

It applies a configuration or creates a bean only when a matching bean does not already exist in the application context.

### Q19. Can we override Spring Boot's default auto-configuration?

**Answer:**

Yes. We can define our own beans or provide custom configuration. For configurations using `@ConditionalOnMissingBean`, Spring Boot's default bean creation is skipped when an appropriate custom bean already exists.

### Q20. Why does Tomcat start automatically after adding spring-boot-starter-web?

**Answer:**

The starter brings embedded Tomcat and Spring MVC dependencies into the classpath. Spring Boot detects these classes and applies the relevant web auto-configurations. When the application starts, the embedded Tomcat server is initialized automatically.

---

## Advanced / Scenario-Based Questions

### Q21. What happens if we remove @EnableAutoConfiguration from the main application class?

**Answer:**

Spring Boot's automatic configuration mechanism will not be enabled through that annotation. Other mechanisms may still register beans, but the usual Spring Boot auto-configuration behavior will be missing.

### Q22. What happens if our main application class is placed in a different package from our components?

**Answer:**

By default, `@ComponentScan` scans the package containing the main application class and its subpackages.

Components outside this package hierarchy may not be discovered unless we explicitly configure component scanning.

### Q23. Can we use Spring Boot without Maven?

**Answer:**

Yes. Spring Boot supports other build tools, including Gradle.

### Q24. Can we use Spring Boot without an embedded Tomcat server?

**Answer:**

Yes. We can exclude the default Tomcat dependency and use another supported embedded server, or package the application as a WAR and deploy it to an external servlet container.

### Q25. Does adding a dependency always mean Spring Boot will automatically configure it?

**Answer:**

No. Adding a dependency only makes its classes available in the classpath. Auto-configuration is applied only when the relevant configuration conditions are satisfied.

### Q26. What happens if a developer creates a custom bean that Spring Boot also provides by default?

**Answer:**

If the default auto-configuration uses `@ConditionalOnMissingBean`, Spring Boot will back off and not create its default bean when the matching custom bean already exists.

### Q27. What is the relationship between Maven dependencies and Spring Boot auto-configuration?

**Answer:**

Maven resolves and downloads dependencies. These dependencies become available in the application's classpath. Spring Boot's auto-configuration mechanism detects relevant classes and conditionally configures the required beans.

### Q28. Is Spring Boot auto-configuration the same as component scanning?

**Answer:**

No.

- Component scanning discovers application components such as `@Service`, `@Repository`, and `@Controller`.
- Auto-configuration configures application infrastructure and other beans based on classpath dependencies and conditions.

---

# 8. Interview Preparation – Important Questions to Master

Prioritize these questions before an interview:

1. What is Spring Boot, and why do we need it?
2. Difference between Spring Core and Spring Boot.
3. What is Maven, and what is pom.xml?
4. Difference between spring-boot-starter and spring-boot-starter-parent.
5. What is @SpringBootApplication?
6. Explain the three annotations inside @SpringBootApplication.
7. What is auto-configuration, and how does it work?
8. Explain @ConditionalOnClass.
9. Explain @ConditionalOnMissingBean.
10. How does Spring Boot start Tomcat automatically?
11. What is the difference between component scanning and auto-configuration?
12. How can we override Spring Boot's default configuration?
13. What happens if a component is outside the main application package?
14. What is an embedded server, and why is it useful?
15. What happens internally when SpringApplication.run() executes?

---

# 9. Quick Self-Assessment

Before moving to the next lecture, make sure you can explain these concepts without looking at your notes.

- [ ] Explain Spring Boot in your own words.
- [ ] Explain why Spring Boot is preferred over traditional Spring configuration.
- [ ] Explain Maven and the role of pom.xml.
- [ ] Differentiate starter dependencies and starter parent.
- [ ] Explain all three annotations inside @SpringBootApplication.
- [ ] Explain how auto-configuration works.
- [ ] Explain @ConditionalOnClass with an example.
- [ ] Explain @ConditionalOnMissingBean with an example.
- [ ] Explain why Tomcat starts automatically.
- [ ] Explain what happens when SpringApplication.run() executes.

**Revision Goal:** You should be able to explain not only what each annotation does, but also why Spring Boot needs it and how the different components work together.