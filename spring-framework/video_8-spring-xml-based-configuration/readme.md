# Spring XML-Based Configuration --- Revision Notes & Interview Questions

## 1. Quick Revision Notes

### Why XML Configuration?

-   Spring needs **configuration metadata** to know which objects
    (beans) to create and how to connect them.
-   XML is an alternative to annotation-based configuration
    (`@Component`, `@Autowired`, etc.).
-   It is still useful when working with legacy Spring applications and
    for understanding the IoC container.

### Create the IoC Container

``` java
ApplicationContext context =
    new ClassPathXmlApplicationContext("beans.xml");
```

-   `ClassPathXmlApplicationContext` loads Spring configuration from the
    classpath.
-   The container reads the XML bean definitions, creates and manages
    beans, and performs dependency injection.

### Define and Retrieve Beans

Example `beans.xml`:

``` xml
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="
         http://www.springframework.org/schema/beans
         https://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="engine" class="com.example.Engine"/>
</beans>
```

Retrieve a bean:

``` java
Engine engine = context.getBean("engine", Engine.class);
```

-   `<bean>` declares an object managed by Spring.
-   `id` gives the bean a name; `class` specifies the implementation
    class.
-   Beans can be retrieved by name or by type.
-   Prefer the type-safe overload: `getBean("engine", Engine.class)`.

### Dependency Injection (DI)

DI means an object receives its dependencies from an external container
instead of creating them itself.

**Constructor injection**

``` xml
<bean id="car" class="com.example.Car">
    <constructor-arg ref="engine"/>
</bean>
```

-   Use `ref` to inject another Spring bean.
-   Use `value` to inject a literal value.
-   Constructor injection makes required dependencies explicit.

**Setter injection**

``` xml
<bean id="car" class="com.example.Car">
    <property name="engine" ref="engine"/>
</bean>
```

-   `<property>` invokes the matching setter, such as `setEngine(...)`.
-   The `name` must match the JavaBean property/setter name.

### Bean Scopes

Set scope using the `scope` attribute:

``` xml
<bean id="service" class="com.example.Service" scope="singleton"/>
<bean id="task" class="com.example.Task" scope="prototype"/>
```

  -----------------------------------------------------------------------
  Scope                               Meaning
  ----------------------------------- -----------------------------------
  `singleton`                         One bean instance per Spring IoC
                                      container; this is the default.

  `prototype`                         A new instance is created each time
                                      the bean is requested from the
                                      container.
  -----------------------------------------------------------------------

**Important:** Spring does not manage the complete destruction lifecycle
of prototype beans after handing them to the caller.

### Inject Collections

**List**

``` xml
<property name="names">
    <list>
        <value>Alice</value>
        <value>Bob</value>
    </list>
</property>
```

**Map**

``` xml
<property name="scores">
    <map>
        <entry key="Alice" value="90"/>
        <entry key="Bob" value="85"/>
    </map>
</property>
```

Spring XML also supports collection elements such as `<set>` and
`<props>`.

### Initialization and Destruction Methods

``` xml
<bean id="service"
      class="com.example.Service"
      init-method="init"
      destroy-method="cleanup"/>
```

-   `init-method` is called after the bean is created and its
    dependencies are injected.
-   `destroy-method` is called when the container is shut down for
    applicable bean scopes, notably singleton beans.
-   These methods are configured manually; they do not require Spring
    lifecycle annotations.

### Split Configuration Across XML Files

Use `<import>` to include another XML configuration file:

``` xml
<beans>
    <import resource="database-config.xml"/>
    <import resource="service-config.xml"/>
</beans>
```

This keeps configuration modular and easier to maintain.

------------------------------------------------------------------------

## 2. Interview Questions

### Basic

1.  What is Spring XML-based configuration?
2.  Why do we need configuration metadata in Spring?
3.  What is the role of `ClassPathXmlApplicationContext`?
4.  What is a Spring bean?
5.  What is the purpose of the `<bean>` tag?
6.  What is the difference between a bean's `id` and `class` attributes?
7.  How can you retrieve a bean by name and by type?

### Dependency Injection

8.  What is Dependency Injection, and how does it relate to IoC?
9.  How do you perform constructor injection using XML?
10. How do you perform setter injection using XML?
11. What is the difference between `ref` and `value` in XML
    configuration?
12. When would you prefer constructor injection over setter injection?
13. What happens if a constructor requires multiple arguments?

### Scope and Lifecycle

14. What is the default scope of a Spring bean?
15. What is the difference between singleton and prototype scope?
16. Does singleton mean one object for the entire JVM?
17. When is a prototype bean created?
18. Does Spring call the destruction callback for prototype beans?
19. What are `init-method` and `destroy-method`?
20. At what point in the bean lifecycle is the initialization method
    called?

### Collections and Configuration

21. How can you inject a List or Map using XML?
22. Which XML elements can be used to inject collections?
23. How can you split Spring XML configuration into multiple files?
24. What is the purpose of the `<import>` tag?
25. What are the advantages and disadvantages of XML configuration
    compared with annotation-based configuration?

------------------------------------------------------------------------

## 3. Important Concepts to Remember

-   **IoC:** Spring takes responsibility for creating and managing
    objects.
-   **DI:** Spring supplies an object's required dependencies.
-   **Bean:** An object managed by the Spring IoC container.
-   **Constructor injection:** Dependencies are supplied through a
    constructor.
-   **Setter injection:** Dependencies are supplied through setter
    methods.
-   **Singleton:** One instance per container, not one per JVM.
-   **Prototype:** A new instance for each request to the container.
-   **`init-method`:** Custom initialization callback.
-   **`destroy-method`:** Custom destruction callback for supported bean
    lifecycles.
-   **`<import>`:** Combines multiple XML configuration files.

## 4. Quick Self-Test

Try answering these without looking at the notes:

1.  Which class loads XML configuration from the classpath?
2.  Which XML attribute selects a bean's scope?
3.  Which XML tag is used for constructor injection?
4.  What is the difference between `ref` and `value`?
5.  Does Spring automatically destroy prototype beans?
6.  Which XML tag imports another configuration file?
