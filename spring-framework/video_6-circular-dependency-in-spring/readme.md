# Spring Framework – Circular Dependency

## 1. What is Circular Dependency?

Circular Dependency occurs when two or more Spring Beans depend on each other, creating a dependency cycle.

### Example

- `OrderService` depends on `PaymentService`.
- `PaymentService` depends on `OrderService`.

```text
OrderService
     |
     v
PaymentService
     |
     v
OrderService
```

The dependency never reaches an independent starting point.

## 2. Why is Circular Dependency a Problem?

Spring IoC Container is responsible for creating and managing beans and injecting their dependencies.

With constructor injection, Spring cannot create either bean first.

### Example

```java
@Service
public class OrderService {

    private final PaymentService paymentService;

    public OrderService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
}
```

```java
@Service
public class PaymentService {

    private final OrderService orderService;

    public PaymentService(OrderService orderService) {
        this.orderService = orderService;
    }
}
```

### What happens internally?

1. Spring tries to create `OrderService`.
2. It needs `PaymentService` to complete its constructor.
3. Spring tries to create `PaymentService`.
4. It needs `OrderService` to complete its constructor.
5. But `OrderService` is not created yet.

**Result:** Spring cannot resolve the circular dependency and throws a `BeanCurrentlyInCreationException` (typically reported as a circular reference error).

## 3. Ways to Handle Circular Dependency

### A. Constructor Injection

Constructor injection is recommended for mandatory dependencies.

However, direct circular dependencies through constructors cannot be resolved normally.

### B. Setter Injection

Setter injection provides dependencies after object creation.

```java
@Service
public class OrderService {

    private PaymentService paymentService;

    @Autowired
    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
}
```

```java
@Service
public class PaymentService {

    private OrderService orderService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }
}
```

Setter injection can sometimes allow Spring to resolve circular references for eligible singleton beans, subject to Spring configuration and lifecycle constraints.

### C. Field Injection

Dependencies are injected directly into fields using `@Autowired`.

```java
@Service
public class OrderService {

    @Autowired
    private PaymentService paymentService;
}
```

Field injection may also allow certain circular references to be resolved for eligible singleton beans.

**Important:** Setter and field injection are not recommended as general solutions to circular dependencies.

## 4. Best Practice – Refactor the Design

Circular dependency often indicates that responsibilities are not properly separated.

Instead of forcing Spring to resolve the cycle, redesign the classes.

### Before Refactoring

```text
OrderService <------> PaymentService
```

### After Refactoring

Introduce a separate service to handle their shared responsibility.

```text
          OrderService
               |
               v
       OrderPaymentService
               |
               v
          PaymentService
```

The exact design depends on the business requirements. The goal is to remove unnecessary bidirectional dependencies.

### Why Refactoring is Better?

- Reduces tight coupling.
- Improves maintainability.
- Makes unit testing easier.
- Separates responsibilities.
- Improves code reusability.

## 5. Connection with SOLID Principles

Circular dependencies can indicate a violation of good object-oriented design principles.

**Single Responsibility Principle (SRP):**
- A class should have one reason to change.
- If two services depend on each other because they handle overlapping responsibilities, those responsibilities may need to be separated.

**Dependency Inversion Principle (DIP):**
- High-level modules should depend on abstractions rather than concrete implementations.
- Introducing an appropriate abstraction can sometimes help break dependency cycles.

## 6. Important Interview Takeaways

1. Circular dependency occurs when beans depend on each other directly or indirectly.
2. Constructor-based circular dependencies cannot normally be resolved by Spring.
3. Setter or field injection can sometimes resolve circular references for eligible singleton beans.
4. Spring Boot generally discourages circular references by default.
5. `spring.main.allow-circular-references=true` can enable certain circular-reference resolution scenarios, but it is not a recommended design solution.
6. Refactoring is the preferred solution.
7. Circular dependencies can indicate poor separation of responsibilities.

---

# Interview Questions – Circular Dependency

## A. Basic Questions

1. What is circular dependency in Spring?
2. Why does circular dependency occur?
3. What is an example of circular dependency between two Spring Beans?
4. What is the role of the Spring IoC Container in circular dependency?
5. Why is circular dependency problematic?
6. What exception can Spring throw when circular dependency occurs?

## B. Dependency Injection Questions

7. Why does constructor injection fail with circular dependencies?
8. Can setter injection resolve circular dependencies?
9. Can field injection resolve circular dependencies?
10. What is the difference between constructor injection and setter injection?
11. Why is constructor injection preferred over field injection?
12. Does Spring support circular dependencies by default?
13. What is the difference between constructor-based and setter-based circular dependencies?

## C. Scenario-Based Questions

14. Suppose `UserService` depends on `OrderService`, and `OrderService` depends on `UserService`. What happens when Spring creates these beans?

15. If constructor injection fails due to circular dependency, how would you solve it?

16. Can two singleton beans have circular dependencies through setter injection?

17. What happens if you enable `spring.main.allow-circular-references=true`?

18. Is using `@Lazy` a solution to circular dependency? How does it help?

19. If three services depend on each other in a circular chain, how would you identify and eliminate the cycle?

20. How would you redesign two services that unnecessarily depend on each other?

## D. Design and SOLID Questions

21. Why is circular dependency considered a design problem?
22. How can circular dependency indicate an SRP violation?
23. How does refactoring help eliminate circular dependencies?
24. How can introducing an interface or abstraction help break a dependency cycle?
25. Why should we prefer fixing the design instead of using setter or field injection to bypass circular dependency?

---

# Quick Revision – Remember These

| Concept | Remember |
|---|---|
| Circular Dependency | Beans depend on each other in a cycle |
| Constructor Injection | Cannot normally resolve direct circular dependencies |
| Setter Injection | Can resolve certain eligible singleton cycles |
| Field Injection | Can sometimes resolve eligible singleton cycles |
| `@Lazy` | Delays dependency resolution using a lazy proxy |
| `allow-circular-references` | Enables certain circular-reference scenarios |
| Best Practice | Refactor and eliminate the dependency cycle |
| SRP | A class should have one reason to change |

## Most Important Interview Question

**Q. Why is circular dependency considered a bad design even if Spring can resolve it using setter injection?**

**Answer:** Because it creates tight coupling between classes, makes testing and maintenance difficult, and often indicates that responsibilities are not properly separated. Even if Spring can resolve the dependency technically, refactoring the design to eliminate the cycle is the better approach.