# Spring Bean Lifecycle — Revision Notes

## 1. What Is the Spring Bean Lifecycle?

The **Spring Bean Lifecycle** is the sequence of steps Spring follows to create, configure, initialize, use, and eventually destroy a bean.

The Spring IoC container manages this lifecycle for beans it creates.

### Lifecycle at a glance

```text
Read bean definitions
        ↓
Instantiate bean
        ↓
Inject dependencies
        ↓
Aware callbacks
        ↓
BeanPostProcessor: before initialization
        ↓
Initialization callbacks
        ↓
BeanPostProcessor: after initialization
        ↓
Bean is ready for use
        ↓
Container shuts down
        ↓
Destruction callbacks
```

This is the general lifecycle for a typical Spring-managed singleton bean. Some steps differ by scope, configuration, and framework version.

---

## 2. Bean Definition and Instantiation

### Bean Definition

A **bean definition** tells Spring how to create and configure a bean. It can contain information such as:

- Bean class
- Dependencies
- Scope
- Initialization and destruction methods
- Other configuration

Bean definitions can come from component scanning, Java configuration, or XML configuration.

### Instantiation

Spring creates the bean object, typically by invoking a constructor or factory method.

```java
@Component
public class OrderService {

    public OrderService() {
        System.out.println("Object created");
    }
}
```

The constructor runs during object creation. At this point, Spring has created the object, but dependency injection and initialization callbacks may still need to happen.

---

## 3. Dependency Injection

After instantiation, Spring supplies the bean's required dependencies.

For example:

```java
@Component
public class OrderService {

    private final PaymentService paymentService;

    public OrderService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
}
```

Spring creates or obtains the `PaymentService` bean and passes it to the `OrderService` constructor.

**Remember:** With constructor injection, dependencies are provided during construction. With field or setter injection, Spring injects them after the object has been instantiated.

---

## 4. Aware Interfaces

Spring provides several `Aware` interfaces that let a bean receive information about its container or environment.

Common examples:

| Interface | What Spring provides |
|---|---|
| `BeanNameAware` | The bean's name in the container |
| `BeanFactoryAware` | A reference to the `BeanFactory` |
| `ApplicationContextAware` | A reference to the `ApplicationContext` |

Example:

```java
@Component
public class OrderService implements BeanNameAware {

    @Override
    public void setBeanName(String name) {
        System.out.println("Bean name: " + name);
    }
}
```

Spring calls `setBeanName()` with the bean's name.

**Interview point:** `ApplicationContextAware` can expose the container to a bean, but directly looking up dependencies from the context is generally less desirable than using dependency injection.

---

## 5. Initialization Callbacks

Initialization callbacks run after the bean has been instantiated and its dependencies have been supplied.

They allow a bean to perform setup work before it is used.

### A. `@PostConstruct`

A method annotated with `@PostConstruct` is called during initialization.

```java
@Component
public class OrderService {

    @PostConstruct
    public void init() {
        System.out.println("PostConstruct called");
    }
}
```

Typical use cases:
- Validate injected configuration
- Prepare internal data
- Perform setup that requires injected dependencies

### B. `InitializingBean`

A bean can implement `InitializingBean` and define `afterPropertiesSet()`.

```java
@Component
public class OrderService implements InitializingBean {

    @Override
    public void afterPropertiesSet() {
        System.out.println("afterPropertiesSet called");
    }
}
```

This callback runs after Spring has set the bean's properties.

### C. Custom `initMethod`

You can specify a custom initialization method in Java configuration:

```java
@Configuration
public class AppConfig {

    @Bean(initMethod = "init")
    public OrderService orderService() {
        return new OrderService();
    }
}
```

The class must provide the matching `init()` method.

### Typical order of initialization callbacks

For a typical Spring-managed bean:

1. `@PostConstruct`
2. `InitializingBean.afterPropertiesSet()`
3. Custom `initMethod`

Spring's `BeanPostProcessor` also provides callbacks before and after initialization. These processors can modify or wrap a bean, for example by creating a proxy.

---

## 6. BeanPostProcessor

A `BeanPostProcessor` lets Spring or application code perform work on beans before and after initialization callbacks.

Its two main methods are:

```java
postProcessBeforeInitialization(...)
postProcessAfterInitialization(...)
```

A simplified order is:

```text
Dependency injection
        ↓
Aware callbacks
        ↓
postProcessBeforeInitialization
        ↓
@PostConstruct
        ↓
afterPropertiesSet()
        ↓
custom initMethod
        ↓
postProcessAfterInitialization
        ↓
Bean ready for use
```

**Important:** `BeanPostProcessor` is an extension point used by Spring features. For example, some processors help apply annotations or create proxies.

---

## 7. Bean Usage

After initialization and post-processing, the bean is ready for use.

For a singleton bean, Spring normally keeps the instance in the container and supplies that same instance when it is requested.

The bean can then be injected into other beans or retrieved from the container.

---

## 8. Destruction Callbacks

When the Spring container shuts down, it can invoke destruction callbacks for beans whose lifecycle it manages.

### A. `@PreDestroy`

```java
@Component
public class OrderService {

    @PreDestroy
    public void cleanup() {
        System.out.println("Cleaning up");
    }
}
```

Use it for cleanup such as releasing resources owned by the bean.

### B. `DisposableBean`

A bean can implement `DisposableBean` and define `destroy()`.

```java
@Component
public class OrderService implements DisposableBean {

    @Override
    public void destroy() {
        System.out.println("Destroy called");
    }
}
```

### C. Custom `destroyMethod`

```java
@Configuration
public class AppConfig {

    @Bean(destroyMethod = "cleanup")
    public OrderService orderService() {
        return new OrderService();
    }
}
```

The bean must provide a matching `cleanup()` method.

### Typical order of destruction callbacks

For a typical Spring-managed bean:

1. `@PreDestroy`
2. `DisposableBean.destroy()`
3. Custom `destroyMethod`

Spring also supports destruction-aware post-processors.

**Important:** These callbacks are normally invoked when the container is closed cleanly. They are not a guarantee that cleanup will run after a sudden process crash or forced termination.

---

## 9. Singleton vs. Prototype Lifecycle

### Singleton

- Spring creates one instance per bean definition per IoC container.
- Spring manages the bean's lifecycle, including destruction callbacks when the container shuts down.

### Prototype

- Spring creates a new instance each time the prototype bean is requested from the container.
- Spring performs creation, dependency injection, and initialization.
- Spring does **not** automatically invoke destruction callbacks for prototype beans when the container shuts down.
- The application is responsible for cleaning up resources held by prototype beans when necessary.

---

## 10. What Does `@Lazy` Do?

`@Lazy` tells Spring to delay creating a bean until it is needed, rather than eagerly creating it during normal context initialization.

```java
@Component
@Lazy
public class ReportService {
}
```

A lazy bean is generally created when it is first requested or needed by another bean.

**Important:** `@Lazy` changes when a bean is initialized; it does not change the bean's scope. A lazy singleton is still a singleton.

A lazy bean injected into an eagerly created bean may be created when that dependency is resolved, unless lazy resolution is explicitly arranged at the injection point.

---

## 11. Circular Dependencies and `@PostConstruct`

A circular dependency occurs when beans depend on each other, directly or indirectly.

```text
OrderService → PaymentService
      ↑              ↓
      └──────────────┘
```

### Why can it be a problem?

With constructor injection, Spring cannot normally construct either bean first because each constructor requires the other bean to exist.

### Does `@PostConstruct` solve circular dependencies?

**Not generally.** `@PostConstruct` runs after a bean has been instantiated and its dependencies have been injected. It cannot make an impossible constructor dependency disappear.

In some setter/field-injection designs, delaying access to a dependency until initialization may avoid an early access problem. However, using `@PostConstruct` as a circular-dependency workaround is fragile and does not fix the underlying design.

**Preferred solution:** Refactor the design to remove the cycle. For example, move shared responsibilities into a third service or introduce an appropriate abstraction.

---

## 12. Quick Revision Table

| Concept | Remember |
|---|---|
| Bean definition | Instructions Spring uses to create and configure a bean |
| Instantiation | Spring creates the object |
| Dependency injection | Spring supplies required dependencies |
| `BeanNameAware` | Receives the bean's name |
| `ApplicationContextAware` | Receives the application context |
| `@PostConstruct` | Initialization callback |
| `InitializingBean` | Uses `afterPropertiesSet()` |
| Custom `initMethod` | User-defined initialization method |
| `BeanPostProcessor` | Processes beans before and after initialization |
| `@PreDestroy` | Destruction callback |
| `DisposableBean` | Uses `destroy()` |
| Custom `destroyMethod` | User-defined cleanup method |
| `@Lazy` | Delays bean creation until needed |
| Prototype destruction | Not automatically managed by Spring |

---

# Interview Questions — Spring Bean Lifecycle

## Beginner Questions

**1. What is the Spring Bean Lifecycle?**  
The sequence Spring follows to create, configure, initialize, use, and destroy a bean.

**2. What are the main stages of the bean lifecycle?**  
Bean definition, instantiation, dependency injection, aware callbacks, initialization callbacks, post-processing, use, and destruction.

**3. What is a bean definition?**  
Configuration metadata that tells Spring how to create and configure a bean.

**4. When does dependency injection happen?**  
After instantiation for field and setter injection. Constructor dependencies are supplied as the object is constructed.

**5. What is the purpose of `BeanNameAware`?**  
It allows a bean to receive its name in the Spring container.

**6. What is the purpose of `ApplicationContextAware`?**  
It allows a bean to receive a reference to the application context.

## Initialization and Destruction Questions

**7. What is `@PostConstruct` used for?**  
It marks a method to run during bean initialization, after dependency injection.

**8. What is `InitializingBean`?**  
An interface with the `afterPropertiesSet()` callback, which Spring invokes during initialization.

**9. How do you define a custom initialization method?**  
Specify it using `@Bean(initMethod = "methodName")` or the equivalent bean configuration.

**10. What is the usual order of initialization callbacks?**  
`@PostConstruct`, `afterPropertiesSet()`, then the custom `initMethod`, with `BeanPostProcessor` callbacks before and after initialization.

**11. What is `BeanPostProcessor`?**  
An extension point that lets Spring process beans before and after initialization callbacks. It can modify or wrap beans.

**12. What is `@PreDestroy` used for?**  
It marks a method to run during bean destruction, usually for cleanup.

**13. What is `DisposableBean`?**  
An interface that provides the `destroy()` destruction callback.

**14. What is a custom `destroyMethod`?**  
A user-defined cleanup method configured for a bean, such as with `@Bean(destroyMethod = "cleanup")`.

**15. Does Spring always run destruction callbacks?**  
They normally run when the container is shut down cleanly for beans whose destruction lifecycle Spring manages. They are not guaranteed after a crash or forced termination.

## Scenario-Based Questions

**16. A bean's `@PostConstruct` method runs before its constructor. Is that correct?**  
No. The constructor runs during instantiation. `@PostConstruct` runs later, after dependency injection and during initialization.

**17. Can you access injected dependencies inside `@PostConstruct`?**  
Yes. For ordinary dependency injection, the dependencies have been supplied before the callback runs.

**18. What happens if a bean implements `InitializingBean` and also defines a custom init method?**  
Both callbacks run. Typically, `afterPropertiesSet()` runs before the custom init method.

**19. What happens to a prototype bean when the application context closes?**  
Spring does not automatically invoke its destruction callbacks. The application is responsible for cleanup when required.

**20. What happens if a lazy singleton is never requested?**  
It may never be instantiated during the application's lifetime.

**21. Does `@Lazy` change a bean from singleton to prototype?**  
No. Lazy initialization and bean scope are separate concepts.

**22. Can `@PostConstruct` fix a constructor-based circular dependency?**  
No. The constructor dependency must be resolved before the bean can be instantiated, so the callback cannot break that cycle.

**23. How should you normally resolve circular dependencies?**  
Refactor the design to remove the cycle, such as by extracting shared logic into another service or introducing an appropriate abstraction.

## Advanced / Tricky Questions

**24. What is the difference between `@PostConstruct` and `InitializingBean`?**  
Both provide initialization callbacks. `@PostConstruct` is annotation-based, while `InitializingBean` requires implementing Spring's interface and its `afterPropertiesSet()` method.

**25. What is the difference between `@PreDestroy` and `DisposableBean`?**  
Both provide destruction callbacks. `@PreDestroy` is annotation-based, while `DisposableBean` requires implementing Spring's `destroy()` method.

**26. Can a `BeanPostProcessor` change or wrap a bean?**  
Yes. A post-processor can return a modified object or proxy, which is one way Spring applies features such as proxy-based behavior.

**27. Does every object created by application code go through the Spring bean lifecycle?**  
No. The lifecycle applies to objects managed as Spring beans. An object created directly with `new` is not automatically managed by Spring.

**28. Why should business logic generally not be placed in `@PostConstruct`?**  
The callback is intended for initialization. Keeping business operations in normal methods makes the bean easier to test and its lifecycle behavior easier to understand.

---

## Strong Interview Summary

A good concise answer to **“Explain the Spring Bean Lifecycle”** is:

"Spring reads bean definitions and creates the bean. It supplies dependencies, invokes applicable aware callbacks, and runs initialization processing such as `@PostConstruct`, `InitializingBean.afterPropertiesSet()`, and a configured init method. Bean post-processors can process the object before and after initialization. The bean is then ready for use. During a clean container shutdown, Spring invokes destruction callbacks such as `@PreDestroy`, `DisposableBean.destroy()`, and a configured destroy method for beans whose destruction lifecycle it manages. Prototype beans are an important exception because Spring does not automatically manage their destruction callbacks."

