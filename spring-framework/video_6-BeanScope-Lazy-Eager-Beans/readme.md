# Spring Bean Scopes – Revision Notes

## 1. What is Bean Scope in Spring?

Bean scope defines **how many instances of a Spring bean are created and how long those instances are managed by the Spring IoC container.**

In simple words, bean scope answers two questions:

1. How many objects of a bean should Spring create?
2. How should Spring manage those objects?

Spring provides several bean scopes. The two most commonly used are:

- Singleton
- Prototype

Web applications also support request, session, and application scopes.

---

## 2. Singleton Scope

### What is Singleton Scope?

Singleton is the **default scope** in Spring.

It means Spring creates only **one instance of a particular bean per IoC container** and returns that same instance whenever the bean is requested from that container.

### Example

```java
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class OrderService {

    public OrderService() {
        System.out.println("OrderService object created");
    }
}
```

Request the bean multiple times:

```java
ApplicationContext context =
    new AnnotationConfigApplicationContext(AppConfig.class);

OrderService obj1 = context.getBean(OrderService.class);
OrderService obj2 = context.getBean(OrderService.class);

System.out.println(obj1 == obj2);
```

Output:

```text
OrderService object created
true
```

### Why is the output true?

- Spring creates the `OrderService` object when the singleton bean is initialized.
- The first `getBean()` returns that instance.
- The second `getBean()` returns the same instance.
- Both references point to the same object in memory.

### Important Points

- Singleton is the default bean scope.
- One instance per Spring IoC container, not necessarily one instance for the entire JVM.
- The same instance is shared wherever the bean is injected or requested from that container.
- Singleton beans are commonly used for service classes, repositories, and controllers.
- Singleton scope does not automatically make a bean thread-safe.

### When should you use Singleton?

Use singleton for **stateless services**.

For example:

```java
@Service
public class OrderService {

    public void createOrder() {
        System.out.println("Order created");
    }
}
```

This service does not store user-specific or request-specific data in instance fields. Multiple requests can safely use the same service, assuming its dependencies are also safe.

**Important:** Avoid storing mutable, request-specific data in shared singleton fields.

---

## 3. Prototype Scope

### What is Prototype Scope?

In prototype scope, Spring creates a **new bean instance every time the bean is requested from the IoC container**.

Unlike singleton, repeated requests do not return the same object.

### Example

```java
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class OrderService {

    public OrderService() {
        System.out.println("OrderService object created");
    }
}
```

Request the bean multiple times:

```java
OrderService obj1 = context.getBean(OrderService.class);
OrderService obj2 = context.getBean(OrderService.class);

System.out.println(obj1 == obj2);
```

Output:

```text
OrderService object created
OrderService object created
false
```

### Why is the output false?

- The first `getBean()` creates a new `OrderService` object.
- The second `getBean()` creates another new `OrderService` object.
- Both references point to different objects in memory.

### Important Points

- A new instance is created every time Spring resolves a request for that prototype bean.
- Prototype beans are useful when independent object instances are required.
- Spring manages their creation and dependency injection.
- Spring does not manage their complete lifecycle: it does not automatically invoke their configured destruction callbacks when the application context closes.
- The application is responsible for cleaning up resources held by prototype beans when necessary.

### When should you use Prototype?

Use prototype when you need independent instances with separate state.

Example:

```java
@Component
@Scope("prototype")
public class ShoppingCart {

    private int itemCount;

    public void addItem() {
        itemCount++;
    }

    public int getItemCount() {
        return itemCount;
    }
}
```

Each prototype instance has its own `itemCount`.

---

## 4. Singleton vs Prototype

| Feature | Singleton | Prototype |
|---|---|---|
| Object creation | One instance per IoC container | New instance per bean request |
| Object sharing | Same instance is shared | Different instances are returned |
| Default scope | Yes | No |
| Lifecycle management | Spring manages creation and destruction callbacks | Spring creates and configures it, but does not automatically manage destruction |
| Memory usage | Usually lower for repeated access | Can be higher because multiple objects are created |
| Common use case | Stateless services | Independent stateful objects |
| Thread safety | Must be considered because the instance is shared | Separate instances reduce shared mutable state between those instances |

### Quick Example

```java
SingletonService a = context.getBean(SingletonService.class);
SingletonService b = context.getBean(SingletonService.class);

System.out.println(a == b); // true
```

```java
PrototypeService a = context.getBean(PrototypeService.class);
PrototypeService b = context.getBean(PrototypeService.class);

System.out.println(a == b); // false
```

---

## 5. Web-Specific Bean Scopes

Spring provides additional scopes for web applications.

These scopes require a web-aware Spring application context.

### A. Request Scope

A new bean instance is created for each HTTP request.

Example:

```java
@Component
@RequestScope
public class RequestData {

    private String requestId;
}
```

Use case:
- Store data associated with one HTTP request.
- Each incoming request gets its own instance.

### B. Session Scope

A bean instance is created for each HTTP session.

Example:

```java
@Component
@SessionScope
public class UserSession {

    private String username;
}
```

Use case:
- Store user-specific session data.
- The same user session can access the same bean instance across multiple requests.
- Different sessions get different instances.

### C. Application Scope

A bean instance is created for the ServletContext of a web application.

Example:

```java
@Component
@ApplicationScope
public class ApplicationData {

    private int totalVisitors;
}
```

Use case:
- Store data shared across the web application.
- The instance is associated with the ServletContext.

### Web Scope Comparison

| Scope | Instance created for | Shared across |
|---|---|---|
| Request | Each HTTP request | One request |
| Session | Each HTTP session | Requests in one session |
| Application | Each ServletContext | The web application |

**Remember:**

- Request = one HTTP request.
- Session = one user's HTTP session.
- Application = one web application's ServletContext.

---

## 6. Important Interview Concepts

### Does Singleton mean only one object in the entire application?

Not exactly. Spring singleton means one instance per bean definition per IoC container.

If you create two independent Spring IoC containers, each can have its own singleton instance of the same bean.

### Are Singleton Beans Thread-Safe?

No. Spring does not automatically make singleton beans thread-safe.

Because multiple threads may access the same singleton instance, mutable shared state can cause race conditions.

Best practice:
- Keep service beans stateless.
- Avoid request-specific mutable instance variables.
- Use appropriate synchronization or concurrency-safe structures when shared mutable state is genuinely required.

### Does Spring Manage the Complete Lifecycle of Prototype Beans?

No.

Spring creates the prototype bean and performs dependency injection and initialization callbacks. However, it does not automatically invoke destruction callbacks for prototype beans.

The application is responsible for cleanup when necessary.

### What happens when a Prototype Bean is Injected into a Singleton Bean?

This is a common interview question.

Consider:

```java
@Component
@Scope("prototype")
public class PrototypeBean {
}
```

```java
@Component
@Scope("singleton")
public class SingletonBean {

    private final PrototypeBean prototypeBean;

    public SingletonBean(PrototypeBean prototypeBean) {
        this.prototypeBean = prototypeBean;
    }

    public PrototypeBean getPrototypeBean() {
        return prototypeBean;
    }
}
```

Although `PrototypeBean` has prototype scope, Spring normally resolves it when creating `SingletonBean`.

Since `SingletonBean` is created once, it receives one prototype instance. Repeated calls to `getPrototypeBean()` return that same injected instance.

**Important:** Prototype scope does not mean a new instance is automatically injected every time a singleton method is called.

If you need a fresh prototype instance on demand, use `ObjectProvider`:

```java
@Component
public class SingletonBean {

    private final ObjectProvider<PrototypeBean> provider;

    public SingletonBean(ObjectProvider<PrototypeBean> provider) {
        this.provider = provider;
    }

    public PrototypeBean getPrototypeBean() {
        return provider.getObject();
    }
}
```

Each call to `getObject()` resolves a new prototype instance.

---

## 7. Quick Revision

| Question | Answer |
|---|---|
| Default Spring bean scope? | Singleton |
| Singleton creates how many instances? | One per IoC container per bean definition |
| Prototype creates how many instances? | One new instance per request to the container |
| Is Singleton thread-safe automatically? | No |
| Which scope is suitable for stateless services? | Singleton |
| Which scope is useful for independent object state? | Prototype |
| Does Spring automatically destroy prototype beans? | No |
| One bean per HTTP request? | Request |
| One bean per HTTP session? | Session |
| One bean per web application ServletContext? | Application |
| How to obtain a fresh prototype inside a singleton? | `ObjectProvider` or another lookup mechanism |

---

# Interview Questions – Spring Bean Scopes

## Beginner-Level Questions

**1. What is bean scope in Spring?**

Bean scope defines how many instances of a bean Spring creates and how those instances are managed.

**2. What is the default bean scope in Spring?**

Singleton.

**3. What is Singleton scope?**

Spring creates one instance per bean definition per IoC container and returns the same instance for subsequent requests.

**4. What is Prototype scope?**

Spring creates a new instance each time the prototype bean is requested from the container.

**5. What is the difference between Singleton and Prototype?**

Singleton reuses one instance, whereas prototype creates a new instance for each bean request.

**6. How can you define a bean as Prototype?**

Using:

```java
@Scope("prototype")
```

**7. How can you define a bean as Singleton?**

Using:

```java
@Scope("singleton")
```

Or simply omit `@Scope`, because singleton is the default.

## Intermediate-Level Questions

**8. Is a Singleton bean thread-safe?**

No. Multiple threads can access the same instance. Thread safety depends on how the bean is implemented.

**9. Why are service classes generally Singleton?**

Most service classes are stateless and can be shared between requests, avoiding unnecessary object creation.

**10. Does Spring manage the complete lifecycle of Prototype beans?**

No. Spring creates and initializes prototype beans, but does not automatically invoke their destruction callbacks.

**11. What happens if you call `getBean()` twice for a Singleton bean?**

Both calls return the same object reference.

**12. What happens if you call `getBean()` twice for a Prototype bean?**

Each call returns a new object reference.

**13. Is Singleton scope the same as the Singleton Design Pattern?**

They are related but not identical.

- Singleton Design Pattern restricts a class to a single instance through its implementation.
- Spring Singleton scope guarantees one bean instance per bean definition per IoC container.

**14. What are Request, Session, and Application scopes?**

They are web-specific scopes that create and share beans according to an HTTP request, HTTP session, or ServletContext respectively.

**15. Can we use Request scope in a non-web Spring application?**

Not normally. Request scope requires an active web request context.

## Scenario-Based Questions

**16. Suppose two users access the same Singleton service simultaneously. Will they share the same object?**

Yes. Both requests can access the same singleton service instance. Its implementation must be safe for concurrent access.

**17. Suppose you need a new ShoppingCart object for every request. Which scope would you use?**

Request scope, if the cart should exist for one HTTP request. If it must persist across multiple requests for the user, session scope may be more appropriate.

**18. Suppose you inject a Prototype bean into a Singleton bean. Will every method call get a new Prototype object?**

No. The prototype dependency is normally resolved once when the singleton is created. Use `ObjectProvider` to request fresh instances when needed.

**19. Can two Spring applications have different Singleton instances of the same class?**

Yes. Each independent IoC container can maintain its own singleton instance.

**20. When would you choose Prototype instead of Singleton?**

When you need independently created objects that maintain separate state and have a lifecycle controlled by the application.

**21. Does Prototype scope automatically make a class thread-safe?**

No. It can reduce shared state between instances, but an individual prototype instance can still be accessed by multiple threads.

**22. What happens to a Prototype bean when the Spring application context closes?**

Spring does not automatically invoke its destruction callbacks. The application must handle necessary cleanup.

## Advanced / Tricky Questions

**23. What is the difference between Spring Singleton and Java Singleton?**

Spring Singleton is scoped to a bean definition and IoC container. Java Singleton is a class-level design pattern intended to restrict instance creation.

**24. Can a Singleton bean contain a Prototype bean?**

Yes, but the prototype dependency is normally injected only when the singleton is created. Use `ObjectProvider` or a lookup mechanism for a fresh instance each time.

**25. Which scope is appropriate for user-specific information across multiple HTTP requests?**

Session scope, provided the information belongs to the HTTP session. For durable user data, use persistent storage instead of relying on session scope.

---

## Final Interview Tip

If an interviewer asks:

**"Why is Singleton the default scope in Spring?"**

A strong answer:

"Most Spring-managed components, such as services and repositories, are designed to be stateless. Sharing one instance avoids unnecessary object creation and makes resource usage more efficient. However, singleton beans must be designed carefully for concurrent access because multiple threads can use the same instance."