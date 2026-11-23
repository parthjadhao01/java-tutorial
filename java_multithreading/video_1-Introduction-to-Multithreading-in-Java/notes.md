# Java Multithreading – Lecture 1: Introduction

## 1. Program vs Process

### Program
- A program is a static set of instructions stored on disk.
- It is not executing.
- Examples: `demo.java`, `demo.class`.

### Process
- A process is an instance of a program currently executing.
- It occupies RAM and uses CPU and operating system resources.
- Each process has its own memory space and resources.

| Program | Process |
|---|---|
| Static instructions | Running instance |
| Stored on disk | Executed in memory |
| Passive | Active |
| No execution state | Has execution state and resources |

**Example:** When you open Chrome, the operating system creates one or more processes to run it.

---

## 2. Role of CPU and Memory

### CPU
- CPU executes program instructions.
- The operating system schedules threads for CPU execution.
- A thread is the basic unit of CPU scheduling.

### Memory
Each process gets its own virtual address space and resources.

A process requires:
- RAM (memory).
- CPU execution time.
- Operating system resources.

**Important:** Processes are isolated from each other. One process generally cannot directly access another process's memory.

---

## 3. What is a Thread?

- A thread is the smallest unit of execution within a process.
- Multiple threads can exist inside a single process.
- Threads within the same process share resources such as heap memory.
- Each thread has its own execution stack.

**Example:** A browser can use different threads for rendering, JavaScript execution and background tasks.

### Main Thread in Java

Every normal Java application starts execution from the `main()` method, which runs on the Main Thread.

```java
public class Main {
    public static void main(String[] args) {
        System.out.println(
            Thread.currentThread().getName()
        );
    }
}
```

Output:
```text
main
```

---

## 4. JVM Memory Management and Threads

The JVM manages memory within a Java process.

Important JVM runtime memory areas:

| Memory Area | Shared or Private? | Purpose |
|---|---|---|
| Heap | Shared | Stores objects and arrays |
| Java Stack | Per-thread | Stores method calls and local variables |
| Method Area | Shared | Stores class-level structures and runtime data |
| PC Register | Per-thread | Tracks the current JVM instruction for each thread |

### Important Concepts

- **Heap:** Objects are allocated here and can be accessed by multiple threads.
- **Stack:** Every thread has its own stack containing method frames and local variables.
- **Method Area:** Shared runtime area containing class metadata and other class-level information.
- **PC Register:** Each thread maintains its own program counter.

**Remember:** Shared heap memory can create thread-safety problems when multiple threads modify the same object.

---

## 5. Context Switching

Context switching is the process of saving the execution state of one thread and restoring another thread's state so that the CPU can execute it.

It is managed by the operating system scheduler.

Example:

```text
CPU executes Thread 1
        |
        v
Save Thread 1 state
        |
        v
Load Thread 2 state
        |
        v
CPU executes Thread 2
        |
        v
Switch back to Thread 1
```

Context switching allows multiple threads to share CPU time.

It also introduces overhead because saving and restoring execution state takes time.

---

## 6. Concurrency vs Parallelism

### Concurrency
- Multiple tasks make progress during overlapping periods.
- On a single CPU core, the CPU can switch between threads.
- They do not necessarily execute at the exact same time.

### Parallelism
- Multiple tasks execute at the exact same time.
- Requires multiple processing units or CPU cores for true simultaneous execution.

| Concurrency | Parallelism |
|---|---|
| Tasks make overlapping progress | Tasks execute simultaneously |
| Can happen on one CPU core | Requires multiple execution units |
| Uses scheduling and context switching | Uses multiple cores or processors |

**Remember:** Concurrency is about dealing with multiple tasks; parallelism is about executing multiple tasks simultaneously.

---

## 7. Multitasking vs Multithreading

### Multitasking
- Operating system concept.
- Running multiple independent processes.
- Each process has its own memory space.

Example: Running Spotify, Chrome and VS Code simultaneously.

### Multithreading
- Multiple threads executing within a single process.
- Threads share process resources.
- Improves responsiveness and can improve CPU utilization.

Example: A Java application handling multiple user requests using multiple threads.

| Multitasking | Multithreading |
|---|---|
| Multiple processes | Multiple threads |
| Process-level execution | Thread-level execution |
| Separate process memory spaces | Shared process resources |
| More isolation | Easier resource sharing |

---

# 8. Quick Revision Flow

```text
PROGRAM
  |
  v
PROCESS
  |
  +---- Memory (RAM)
  |
  +---- Resources
  |
  +---- Multiple Threads
             |
             v
       CPU Scheduling
             |
       +-----+------+
       |            |
  Concurrency   Parallelism
       |            |
  Time sharing  Multiple cores
```

## 9. Key Takeaways

1. Program = Instructions stored on disk.
2. Process = Running instance of a program.
3. Thread = Unit of execution within a process.
4. CPU executes instructions scheduled through threads.
5. Threads share heap memory but have separate stacks and PC registers.
6. Context switching allows the CPU to switch between threads.
7. Concurrency does not necessarily mean simultaneous execution.
8. Parallelism means actual simultaneous execution.
9. Multitasking deals with multiple processes.
10. Multithreading deals with multiple threads within a process.

---

# 10. Interview Questions – Java Multithreading Introduction

## A. Basic Conceptual Questions

1. What is a program?
2. What is a process?
3. What is the difference between a program and a process?
4. What is a thread?
5. Why do we need threads?
6. What is the difference between a process and a thread?
7. What is the main thread in Java?
8. How can you get the current thread name in Java?
9. Can a process have multiple threads?
10. Can multiple processes run the same program?

## B. JVM and Memory Questions

11. What are the different memory areas managed by the JVM?
12. What is the difference between Heap and Stack memory?
13. Is Heap memory shared between threads?
14. Does every thread have its own stack?
15. What is a PC Register in JVM?
16. What is the Method Area?
17. Where are Java objects stored?
18. Why can shared heap memory create problems in multithreading?

## C. CPU and Execution Questions

19. What is context switching?
20. Who manages context switching?
21. Why does context switching introduce overhead?
22. What is concurrency?
23. What is parallelism?
24. What is the difference between concurrency and parallelism?
25. Can concurrency happen on a single-core CPU?
26. Can two threads execute simultaneously on a single-core CPU?

## D. Multitasking vs Multithreading

27. What is multitasking?
28. What is multithreading?
29. What is the difference between multitasking and multithreading?
30. Can multiple threads share data?
31. Why is multithreading useful in Java applications?

## E. Scenario-Based Interview Questions

32. If you open Chrome and VS Code, how many processes are created?
33. If a Java application has five threads, do they share the same heap?
34. If two threads access the same object, can they modify it simultaneously?
35. If a CPU has four cores, can it execute four threads in parallel?
36. If a computer has one CPU core but multiple threads, how does it manage execution?
37. Why does every Java application have a Main Thread?
38. If one thread gets blocked, can other threads continue executing?

---

# 11. Practical Knowledge Check

Make sure you can answer these without looking at the notes:

1. What is the difference between a program and a process?
2. Which memory area is shared between Java threads?
3. Who generally schedules threads for CPU execution?
4. Can concurrency happen on a single-core CPU?
5. What is true parallelism?
6. What is context switching?

---

# 12. Important Concepts to Master Before the Next Lecture

Before moving forward, make sure you understand these four concepts:

1. **Process vs Thread:** Understand how processes and threads differ.
2. **JVM Memory Areas:** Understand Heap, Stack, Method Area and PC Register.
3. **Context Switching:** Understand how the CPU switches between threads.
4. **Concurrency vs Parallelism:** Understand the difference between overlapping progress and simultaneous execution.

These concepts are the foundation for understanding:
- Java Thread class.
- Runnable interface.
- Thread lifecycle.
- Synchronization.
- Thread safety.
- Multithreading in real-world Java applications.