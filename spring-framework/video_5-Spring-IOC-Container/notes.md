# Spring Framework Fundamentals: IoC, Dependency Injection, and Beans

> Beginner-friendly interview notes based on the topics covered in the
> Coder Army video.
>
> **Learning goal:** Understand how Spring creates objects, manages
> their lifecycle, and supplies the dependencies they need.

------------------------------------------------------------------------

## 1. The problem Spring solves

A Java application is made of objects. These objects often depend on
other objects to do their work.

For example:

``` java
class Engine {
    void start() {
        System.out.println("Engine started");
    }
}

class Car {
    private Engine engine = new Engine();

    void drive() {
        engine.start();
        System.out.println("Car is moving");
    }
}
```

This works, but `Car` creates its own `Engine`. That means:

-   `Car` controls which `Engine` implementation it uses.
-   Replacing the engine can require changing `Car`.
-   Testing `Car` with a fake or mock engine is harder.
-   Object creation and business logic become tightly coupled.

As an application grows, manually creating and connecting every object
becomes difficult to maintain.

Spring helps by taking responsibility for creating objects and
connecting their dependencies.

## 2. Inversion of Control (IoC)

**Inversion of Control** means that control over some part of an
application's execution or object management is transferred from
application code to a framework or container.

In ordinary Java code, we might create dependencies ourselves:

``` java
Engine engine = new Engine();
Car car = new Car(engine);
```

With Spring, we describe the objects and their relationships. The Spring
IoC container creates and manages them.

The important inversion is:

-   Without a container: our code decides when and how to create
    dependencies.
-   With Spring: the container creates and supplies managed objects
    according to configuration and metadata.

### What is the IoC container?

The **IoC container** is the Spring component responsible for:

1.  Reading configuration and component metadata.
2.  Identifying which objects should be managed as Spring beans.
3.  Creating bean instances when required.
4.  Resolving and injecting dependencies.
5.  Managing bean initialization and destruction callbacks.

The two central container interfaces are `BeanFactory` and
`ApplicationContext`.

### Interview distinction

IoC is the broader principle. Dependency Injection is one way to
implement IoC. They are related, but they are not synonyms.

## 3. Dependency Injection (DI)

A **dependency** is an object that another object needs to perform its
work.

**Dependency Injection** means providing an object's dependencies from
outside the object instead of having the object construct them itself.

Consider this version:

``` java
class Engine {
    void start() {
        System.out.println("Engine started");
    }
}

class Car {
    private final Engine engine;

    Car(Engine engine) {
        this.engine = engine;
    }

    void drive() {
        engine.start();
        System.out.println("Car is moving");
    }
}
```

Now `Car` does not create the `Engine`. Someone else supplies it. That
is dependency injection.

### Why use DI?

-   **Loose coupling:** A class depends less on concrete implementation
    details.
-   **Testability:** A test can pass a fake or mock dependency.
-   **Maintainability:** Implementations can be changed with fewer code
    changes.
-   **Separation of concerns:** Business classes focus on their work;
    object wiring is handled separately.

### Types of dependency injection

#### A. Constructor injection --- recommended for required dependencies

``` java
@Component
class Engine {
    void start() {
        System.out.println("Engine started");
    }
}

@Component
class Car {
    private final Engine engine;

    public Car(Engine engine) {
        this.engine = engine;
    }

    void drive() {
        engine.start();
    }
}
```

Spring resolves the `Engine` bean and passes it to the `Car`
constructor.

Advantages:

-   Required dependencies are explicit.
-   Fields can be `final`.
-   The object cannot be constructed without its required dependencies.
-   Easy to instantiate and test without starting Spring.
-   Helps avoid partially initialized objects.

If a class has exactly one constructor, Spring can use it for injection
without requiring `@Autowired` on that constructor.

#### B. Setter injection

``` java
@Component
class Car {
    private Engine engine;

    @Autowired
    public void setEngine(Engine engine) {
        this.engine = engine;
    }
}
```

Useful when a dependency is optional or needs to be changeable after
construction. The object can exist before the dependency is set, so
required dependencies are generally better expressed through
constructors.

#### C. Field injection

``` java
@Component
class Car {
    @Autowired
    private Engine engine;
}
```

Spring injects the dependency directly into the field.

It is concise, but generally discouraged for application code because
dependencies are less visible, fields cannot be `final`, and plain unit
testing becomes less convenient.

**Interview answer:** Constructor injection is generally preferred for
mandatory dependencies. Setter injection can be appropriate for optional
dependencies. Field injection is usually avoided in maintainable
production code.

## 4. Spring beans

A **Spring bean** is an object that is instantiated, assembled, and
managed by the Spring IoC container.

Not every Java object is automatically a bean. An object becomes a
Spring bean when it is registered with the container, for example
through component scanning, a `@Bean` method, or programmatic
registration.

Examples of common stereotype annotations:

-   `@Component`: Generic Spring-managed component.
-   `@Service`: Indicates a service/business-logic component.
-   `@Repository`: Indicates a persistence/data-access component; also
    participates in persistence exception translation when applicable.
-   `@Controller`: Spring MVC controller.
-   `@RestController`: Controller whose handler methods typically write
    response bodies, commonly JSON.

`@Service`, `@Repository`, and `@Controller` are specialized forms of
`@Component`.

## 5. `@Component` and `@ComponentScan`

### `@Component`

Marks a class as a candidate for Spring component scanning.

``` java
import org.springframework.stereotype.Component;

@Component
public class Engine {
    public void start() {
        System.out.println("Engine started");
    }
}
```

### `@ComponentScan`

Tells Spring which package locations to scan for component classes.

``` java
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.example")
public class AppConfig {
}
```

Spring scans the specified package and its subpackages for eligible
components, subject to scan filters.

If no package is specified, component scanning from a configuration
class commonly starts at the package of that class.

### Important package rule

If your main configuration class is in `com.example`, placing
application components in `com.example` or its subpackages makes them
easy to discover with the default scan arrangement. A component outside
the scanned packages may not be discovered unless you configure scanning
for it.

## 6. `@Configuration` and `@Bean`

### `@Configuration`

Marks a class as a source of bean definitions.

``` java
@Configuration
public class AppConfig {
}
```

### `@Bean`

Marks a method whose returned object should be registered as a Spring
bean.

``` java
@Configuration
public class AppConfig {

    @Bean
    public Engine engine() {
        return new Engine();
    }
}
```

The method name (`engine`) is the default bean name in this example. You
can provide an explicit name:

``` java
@Bean("petrolEngine")
public Engine engine() {
    return new Engine();
}
```

### When should you use `@Bean`?

Use `@Bean` when you want explicit control over object construction,
especially when:

-   The class comes from a third-party library and cannot be annotated
    with `@Component`.
-   Construction requires custom setup or arguments.
-   You want to register an object from an existing factory method.
-   You need configuration that is clearer when written in Java code.

### `@Component` vs `@Bean`

  -----------------------------------------------------------------------
`@Component`                        `@Bean`
  ----------------------------------- -----------------------------------
Put on a class                      Put on a method

Spring discovers the class through  Spring invokes the method from
component scanning                  configuration to obtain the bean

Convenient for classes you own      Convenient for third-party classes
or custom construction

Class is marked as a component      Method's returned object is
registered as a bean
  -----------------------------------------------------------------------

Both approaches register objects with the Spring container. They are not
competing dependency-injection systems.

## 7. Starting a Spring container with `AnnotationConfigApplicationContext`

For a small annotation-based example, you can create an application
context directly:

``` java
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        var context =
            new AnnotationConfigApplicationContext(AppConfig.class);

        Car car = context.getBean(Car.class);
        car.drive();

        context.close();
    }
}
```

`AnnotationConfigApplicationContext` is an implementation of
`ApplicationContext` that supports configuration classes and
annotation-based registration.

A minimal configuration might be:

``` java
@Configuration
@ComponentScan("com.example")
public class AppConfig {
}
```

In a Spring Boot application, Boot normally creates and configures the
application context for you. You usually do not manually instantiate
`AnnotationConfigApplicationContext` in a Boot application's main
method.

## 8. What happens internally when Spring starts?

A useful high-level mental model is:

1.  **Read configuration:** Spring receives configuration classes,
    component-scan instructions, and other sources of bean definitions.
2.  **Discover components:** Component scanning identifies eligible
    classes annotated with component stereotypes.
3.  **Register bean definitions:** Spring stores metadata describing how
    each bean should be created. A bean definition is not the same thing
    as the bean instance.
4.  **Prepare the container:** The context runs relevant factory
    post-processors and other startup infrastructure.
5.  **Create beans:** Singleton beans are normally created during
    context refresh unless configured for lazy initialization or
    otherwise deferred.
6.  **Resolve dependencies:** Spring finds the required beans and
    injects them through constructors, setters, or fields.
7.  **Initialize beans:** Relevant aware callbacks, bean
    post-processors, and initialization callbacks are applied.
8.  **Make beans available:** The initialized beans can be retrieved
    from the context and used by the application.
9.  **Shutdown:** The context can invoke destruction callbacks for
    managed beans when it closes, subject to scope and lifecycle rules.

### Bean definition vs bean

-   **Bean definition:** Metadata describing a bean, such as its class,
    scope, dependencies, and creation details.
-   **Bean instance:** The actual Java object created from that
    metadata.

A simple analogy: a bean definition is like a recipe; a bean instance is
the prepared dish.

### Is every bean created immediately?

No. By default, non-lazy singleton beans are generally created during
application-context startup. Lazy beans are created when first requested
or needed. Other scopes and framework-specific behavior also affect
creation timing.

## 9. `@Autowired` in more detail

`@Autowired` tells Spring to resolve and inject a dependency.

It can be used on:

-   Constructors
-   Setter methods
-   Other methods
-   Fields

Example:

``` java
@Component
class Car {
    private final Engine engine;

    public Car(Engine engine) {
        this.engine = engine;
    }
}
```

For a single constructor, `@Autowired` is optional. With multiple
constructors, explicitly indicate which constructor Spring should use
when necessary.

Spring primarily resolves an autowired dependency by type. If more than
one candidate of that type is available, you may need to disambiguate
it.

## 10. Resolving multiple beans: `@Primary` and `@Qualifier`

Suppose two beans implement the same interface:

``` java
public interface PaymentService {
    void pay();
}

@Component
class UpiPaymentService implements PaymentService {
    public void pay() {
        System.out.println("Paid using UPI");
    }
}

@Component
class CardPaymentService implements PaymentService {
    public void pay() {
        System.out.println("Paid using card");
    }
}
```

Now this constructor is ambiguous:

``` java
@Component
class CheckoutService {
    private final PaymentService paymentService;

    public CheckoutService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
}
```

Spring finds two candidates for `PaymentService`.

### Option A: `@Primary`

Mark one candidate as the preferred default:

``` java
@Component
@Primary
class UpiPaymentService implements PaymentService {
    public void pay() {
        System.out.println("Paid using UPI");
    }
}
```

When a single `PaymentService` is required and no more specific
qualifier selects another bean, Spring prefers the primary candidate.

### Option B: `@Qualifier`

Select a particular bean at the injection point:

``` java
@Component
class CheckoutService {
    private final PaymentService paymentService;

    public CheckoutService(
        @Qualifier("cardPaymentService") PaymentService paymentService
    ) {
        this.paymentService = paymentService;
    }
}
```

By default, the component bean name is commonly the class name with its
first letter lowercased, so `CardPaymentService` becomes
`cardPaymentService`.

You can also give a component an explicit name:

``` java
@Component("card")
class CardPaymentService implements PaymentService {
    public void pay() {
        System.out.println("Paid using card");
    }
}
```

Then use `@Qualifier("card")`.

### `@Primary` vs `@Qualifier`

-   `@Primary` expresses the preferred default among candidates.
-   `@Qualifier` narrows the candidates at a particular injection point.
-   A matching qualifier can select a bean other than the primary bean.

## 11. `ApplicationContext` vs `BeanFactory`

Both are Spring IoC container interfaces.

  -----------------------------------------------------------------------
`BeanFactory`                       `ApplicationContext`
  ----------------------------------- -----------------------------------
Core container interface            Builds on `BeanFactory`

Provides basic bean creation and    Provides the core bean-factory
retrieval                           capabilities plus application-level
features

More minimal                        Commonly used in modern Spring
applications

Does not provide the full           Supports features such as events,
application-context feature set     message-source/i18n integration,
and resource loading
  -----------------------------------------------------------------------

`ApplicationContext` also supports automatic registration and processing
of many container infrastructure components, such as bean
post-processors, in the usual application-context setup.

**Interview answer:** `BeanFactory` provides the fundamental IoC
container functionality. `ApplicationContext` extends it with additional
enterprise/application features and is the standard choice for most
Spring applications.

## 12. Important related concepts

### Bean scope

Scope determines how many instances Spring creates and how long they are
associated with the container.

Common scopes:

-   `singleton` (default): One shared bean instance per bean definition
    per Spring IoC container.
-   `prototype`: A new instance is created each time the bean is
    requested from the container.
-   `request`: One bean instance per HTTP request in a web-aware
    context.
-   `session`: One bean instance per HTTP session in a web-aware
    context.
-   `application`: One bean instance per `ServletContext` in a web-aware
    context.
-   `websocket`: One bean instance per WebSocket lifecycle in a
    web-aware context.

Note: Spring's singleton is not the same as the Gang of Four singleton
pattern. It is scoped to a bean definition within a particular
container.

### Bean lifecycle (high-level)

A typical singleton bean lifecycle includes:

1.  Instantiate the bean.
2.  Populate dependencies.
3.  Apply relevant aware callbacks.
4.  Run bean post-processors before initialization.
5.  Run initialization callbacks, such as `@PostConstruct`.
6.  Run bean post-processors after initialization.
7.  Bean is ready for use.
8.  On container shutdown, run destruction callbacks such as
    `@PreDestroy`, where applicable.

The exact lifecycle has additional extension points, but this is a
useful interview-level overview.

### Circular dependencies

A circular dependency occurs when A depends on B while B also depends on
A.

Constructor-based circular dependencies cannot be resolved by Spring and
typically cause a `BeanCurrentlyInCreationException`. Setter/field-based
singleton cycles may sometimes be resolved through early references, but
relying on this is discouraged. The better design is usually to refactor
the classes so the dependency cycle disappears.

------------------------------------------------------------------------

# Interview Questions and Answers

Try answering each question aloud before reading the answer.
Interviewers may ask follow-up questions, so understand the reasoning
rather than memorizing definitions.

## Beginner questions

### 1. What is the Spring Framework?

Spring is a Java framework that provides infrastructure for building
applications. Its core container manages objects and their dependencies.
Other Spring projects provide capabilities such as web development, data
access, transactions, and security.

### 2. What is IoC?

Inversion of Control is a design principle in which control over object
creation or other responsibilities is delegated to a framework or
container instead of being handled directly by application code.

### 3. What is Dependency Injection?

DI is a technique in which an object receives the dependencies it needs
from an external source rather than creating those dependencies itself.

### 4. What is the difference between IoC and DI?

IoC is the broader principle of transferring control to a framework or
container. DI is one technique used to achieve IoC by supplying
dependencies from outside an object.

### 5. What is a Spring bean?

A Spring bean is an object managed by the Spring IoC container. Spring
can create it, inject its dependencies, and manage relevant lifecycle
callbacks.

### 6. What is the IoC container?

It is the part of Spring responsible for reading configuration metadata,
creating and configuring beans, resolving dependencies, and managing
their lifecycle.

### 7. Why do we need Spring if Java already supports object creation with `new`?

Java's `new` operator is sufficient for simple programs. In larger
applications, manually creating and wiring a large object graph becomes
difficult. Spring centralizes this work and makes dependencies easier to
replace, configure, and test.

### 8. What is loose coupling?

Loose coupling means that classes have fewer direct dependencies on
specific implementations. For example, depending on a `PaymentService`
interface rather than directly constructing `CardPaymentService` makes
it easier to change implementations.

### 9. What is `@Component`?

`@Component` marks a class as a candidate for component scanning so
Spring can register it as a bean.

### 10. What is `@ComponentScan`?

It instructs Spring to scan specified packages for eligible components
and register them as beans.

### 11. What is `@Configuration`?

It marks a class as a source of bean definitions and configuration for
the Spring container.

### 12. What is `@Bean`?

It marks a method whose returned object should be registered as a Spring
bean.

## Dependency-injection questions

### 13. What are the types of dependency injection in Spring?

The common forms are constructor injection, setter injection, and field
injection.

### 14. Which type of dependency injection is recommended and why?

Constructor injection is generally recommended for required dependencies
because it makes dependencies explicit, supports `final` fields,
prevents construction without required dependencies, and simplifies unit
testing.

### 15. Is `@Autowired` required on a constructor?

Not when the class has only one constructor. Spring can use that
constructor automatically. With multiple constructors, use appropriate
annotations or configuration to make the intended constructor clear.

### 16. Why is field injection generally discouraged?

It hides dependencies from the constructor, prevents the field from
being `final`, and makes testing with ordinary Java construction harder.
Constructor injection makes required dependencies explicit.

### 17. Can Spring inject dependencies into private fields?

Yes. Spring can use reflection to inject an autowired private field.
However, constructor injection is generally preferred for required
dependencies.

### 18. What happens if Spring cannot find a bean for an autowired dependency?

Context creation or dependency resolution fails, typically with an
exception such as `NoSuchBeanDefinitionException` (often surfaced
through an `UnsatisfiedDependencyException`).

### 19. What happens if multiple beans of the same type exist?

Spring may not know which candidate to inject and can report a
`NoUniqueBeanDefinitionException`. Use `@Primary` or `@Qualifier` to
resolve the ambiguity.

### 20. Can a class have more than one constructor?

Yes. If there are multiple constructors, Spring needs a clear way to
determine which one to use for constructor injection. Explicitly
annotate/configure the intended constructor as appropriate.

## Bean-configuration questions

### 21. What is the difference between `@Component` and `@Bean`?

`@Component` marks a class for component scanning. `@Bean` marks a
method that explicitly creates or supplies an object to register with
the container.

### 22. When should you use `@Bean` instead of `@Component`?

Use `@Bean` when configuring a class you cannot modify, such as a
third-party library class, or when object creation requires custom
logic.

### 23. Can we use `@Component` and `@Bean` together?

Yes, but avoid unintentionally registering the same logical object
twice. A configuration class can itself be a Spring component and can
declare `@Bean` methods.

### 24. What is the purpose of `@Primary`?

It marks one bean as the preferred candidate when Spring must choose
among multiple beans of the same type and no more specific qualifier
resolves the choice.

### 25. What is the purpose of `@Qualifier`?

It narrows bean candidates at an injection point and identifies which
bean should be injected.

### 26. What is the difference between `@Primary` and `@Qualifier`?

`@Primary` establishes a preferred default bean. `@Qualifier` explicitly
selects or narrows candidates at a particular injection point.

### 27. What is the default bean name for a component class?

Usually, it is the class name with the first character lowercased. For
example, `PaymentServiceImpl` commonly has the bean name
`paymentServiceImpl`. An explicit name can be supplied in the
annotation.

## Container and internals questions

### 28. What is `ApplicationContext`?

It is a central Spring container interface. It includes bean-factory
functionality and additional application features such as event
publication, message resolution, and resource loading.

### 29. What is `BeanFactory`?

It is the foundational Spring IoC container interface. It provides basic
bean management and retrieval functionality.

### 30. What is the difference between `BeanFactory` and `ApplicationContext`?

`BeanFactory` provides basic IoC functionality. `ApplicationContext`
builds on it and provides additional application-level features and
infrastructure processing. Most modern Spring applications use
`ApplicationContext`.

### 31. What is a BeanDefinition?

It is metadata that describes how Spring should create and configure a
bean. It is not the actual Java object.

### 32. Does Spring create bean objects while scanning classes?

Scanning discovers candidate components and registers bean definitions.
Object instantiation happens later in the container's bean-creation
process; for ordinary non-lazy singleton beans, this is usually during
context startup.

### 33. Are all Spring beans created at startup?

No. Non-lazy singleton beans are generally created during context
startup. Lazy beans and other scopes can be created later, depending on
when they are needed.

### 34. What is `AnnotationConfigApplicationContext`?

It is an `ApplicationContext` implementation that supports Java
configuration classes and annotation-based bean registration. It is
useful in standalone annotation-configured Spring examples.

### 35. What happens when the Spring application context is closed?

Spring invokes destruction callbacks for managed beans that support
them, such as applicable `@PreDestroy` methods. This is important for
releasing resources.

## Scenario-based questions

### 36. You have a third-party class that you cannot annotate with `@Component`. How will you register it as a bean?

Declare a method in a `@Configuration` class and annotate that method
with `@Bean`. Return the configured third-party object.

### 37. You have two implementations of an interface. One should be the default everywhere unless a specific class asks for the other. What will you use?

Mark the default implementation with `@Primary`, then use `@Qualifier`
at injection points that need the other implementation.

### 38. You want to unit-test a service without starting the Spring container. Which injection style helps?

Constructor injection. The test can directly instantiate the service and
pass a mock or fake dependency.

### 39. A component is not being detected by Spring. What would you check?

Check that: - It has a component stereotype such as `@Component` (or a
specialized stereotype). - Its package is included in component
scanning. - The configuration class is actually loaded. - Scan filters
or profile/conditional configuration are not excluding it.

### 40. A constructor requires an interface, but Spring reports multiple matching beans. How would you fix it?

Use `@Qualifier` to select the intended implementation, or mark a
preferred default implementation with `@Primary`.

### 41. Two classes depend on each other through constructors. What is the problem?

This is a constructor circular dependency. Spring cannot construct
either object first, so application-context creation fails. Refactor the
design to remove the cycle rather than relying on a workaround.

### 42. Why should business classes generally not call `new` for their Spring-managed dependencies?

Doing so bypasses the container for that object, so Spring cannot
automatically manage its injection, scope, proxies, or lifecycle as a
bean. Let Spring supply managed dependencies where appropriate.

------------------------------------------------------------------------

# Quick Revision Sheet

  --------------------------------------------------------------------------
Term                                   Remember this
  -------------------------------------- -----------------------------------
IoC                                    Control is delegated to a
framework/container

DI                                     Dependencies are supplied from
outside an object

IoC container                          Creates, wires, and manages beans

Bean                                   Object managed by Spring

`@Component`                           Marks a class for component
scanning

`@ComponentScan`                       Tells Spring where to scan

`@Configuration`                       Declares a configuration class

`@Bean`                                Registers the object returned by a
method

`@Autowired`                           Requests dependency injection

Constructor injection                  Best default for required
dependencies

`@Primary`                             Preferred default candidate

`@Qualifier`                           Selects/narrows a candidate at an
injection point

`BeanDefinition`                       Metadata describing a bean

`BeanFactory`                          Basic IoC container interface

`ApplicationContext`                   BeanFactory plus application-level
features

`AnnotationConfigApplicationContext`   Annotation/Java-config based
context implementation
  --------------------------------------------------------------------------

## Practice tasks

Build these small exercises after revising the notes:

1.  Create an `Engine` class and a `Car` class. Register both as
    components and inject `Engine` into `Car` using constructor
    injection.
2.  Move `Engine` registration from `@Component` to a `@Bean` method.
    Confirm that `Car` still receives it.
3.  Create `PetrolEngine` and `DieselEngine`, both implementing
    `Engine`. Resolve the ambiguity once with `@Primary` and once with
    `@Qualifier`.
4.  Move a component outside the scanned package. Observe the
    missing-bean error, then correct the scan configuration.
5.  Add a constructor print statement to a singleton bean and retrieve
    it multiple times. Observe that the same singleton instance is
    returned by the context.
6.  Add `@PostConstruct` and `@PreDestroy` methods to a bean. Close the
    application context and observe the lifecycle callbacks.

## Interview preparation checklist

-   [ ] Explain IoC without using only a memorized definition.
-   [ ] Explain DI with a small Java example.
-   [ ] Explain why constructor injection is preferred.
-   [ ] Explain the difference between `@Component` and `@Bean`.
-   [ ] Explain how component scanning discovers beans.
-   [ ] Explain what a BeanDefinition is.
-   [ ] Resolve multiple implementations using `@Primary` and
    `@Qualifier`.
-   [ ] Compare `BeanFactory` and `ApplicationContext`.
-   [ ] Describe the high-level bean creation and lifecycle process.
-   [ ] Build the practice tasks without copying the examples.
