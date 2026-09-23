# Java & Spring Boot Core Interview Notes - Set 01

Comprehensive, interview-ready preparation guide with detailed explanations, code snippets, visual summaries, and performance considerations.

---

## Table of Contents
1. [Java Developer Role - Responsibilities](#1-java-developer-role---responsibilities)
2. [Functional Interfaces vs Lambda Expressions](#2-functional-interfaces-vs-lambda-expressions)
3. [Find Smallest Number After Removing K Characters](#3-find-smallest-number-after-removing-k-characters)
4. [Java Streams - Find Top 3 Max and Min Numbers](#4-java-streams---find-top-3-max-and-min-numbers)
5. [@Profile Annotation in Spring Framework](#5-profile-annotation-in-spring-framework)
6. [Spring Framework vs Spring Boot](#6-spring-framework-vs-spring-boot)
7. [Java & Spring Boot Versions](#7-java--spring-boot-versions)
8. [Synchronized vs Volatile](#8-synchronized-vs-volatile)
9. [ConcurrentHashMap, ArrayList, LinkedList](#9-concurrenthashmap-arraylist-linkedlist)
10. [Inbound & Outbound in Java (Boxing/Unboxing)](#10-inbound--outbound-in-java-boxingunboxing)
11. [Unit Testing, Integration Testing & Mockito](#11-unit-testing-integration-testing--mockito)
12. [PriorityQueue](#12-priorityqueue)
13. [ExecutorService](#13-executorservice)
14. [Comparator vs Comparable](#14-comparator-vs-comparable)
15. [Generic Classes & Generics](#15-generic-classes--generics)
16. [Reflection in Java](#16-reflection-in-java)
17. [JVM Class Loaders](#17-jvm-class-loaders)
18. [Garbage Collection Memory Model](#18-garbage-collection-memory-model)
19. [JVM Algorithms & Collectors](#19-jvm-algorithms--collectors)
20. [Return Largest Palindrome Substring](#20-return-largest-palindrome-substring)

---

## 1. Java Developer Role - Responsibilities

### Overview
A Java Developer is responsible for designing, developing, testing, deploying, and maintaining robust, scalable, and high-performance backend systems and enterprise applications.

```
+-------------------------------------------------------------------------------+
|                        JAVA DEVELOPER RESPONSIBILITIES                        |
+-------------------+-------------------+-------------------+-------------------+
| System Design &   | Microservices &   | Code Quality &    | DevOps &          |
| Architecture      | REST APIs         | Unit Testing      | Cloud Integration |
+-------------------+-------------------+-------------------+-------------------+
```

### Core Responsibilities
1. **Application & Microservice Development**:
   - Building scalable RESTful Web Services and Microservices using **Spring Boot**, **Spring Cloud**, or **Jakarta EE**.
   - Implementing domain logic, transactional boundaries, and exception handling.

2. **Database & ORM Management**:
   - Designing relational schemas (PostgreSQL, MySQL) and NoSQL stores (MongoDB, Redis).
   - Writing optimized SQL queries, indexing strategies, and ORM abstractions using **Hibernate** and **Spring Data JPA**.

3. **Code Quality, Testing & Security**:
   - Writing clean, maintainable code following **SOLID principles** and standard **Design Patterns** (Factory, Singleton, Builder, Strategy).
   - Writing thorough unit and integration tests using **JUnit 5**, **Mockito**, and **Testcontainers**.
   - Securing endpoints using **OAuth2**, **JWT**, and **Spring Security**.

4. **DevOps, CI/CD & Deployment**:
   - Containerizing applications using **Docker** and managing deployments via **Kubernetes**.
   - Setting up build automation with **Maven** / **Gradle** and CI/CD pipelines (Jenkins, GitHub Actions, GitLab CI).

5. **Performance Optimization & Monitoring**:
   - Diagnosing memory leaks, high CPU usage, and thread deadlocks using tools like **JProfiler**, **VisualVM**, **JConsole**, and thread/heap dump analysis.
   - Monitoring production services via **Prometheus**, **Grafana**, and **Spring Boot Actuator**.

### Interview Tip 💡
> When asked about your daily responsibilities, frame your answer using the **STAR method** (Situation, Task, Action, Result). Emphasize business impact (e.g., *"I optimized database queries resulting in a 40% reduction in API response latency"*).

---

## 2. Functional Interfaces vs Lambda Expressions

### Definitions

- **Functional Interface**: An interface that contains **exactly one abstract method** (Single Abstract Method - SAM). It can contain any number of `default` or `static` methods. Annotated with `@FunctionalInterface`.
- **Lambda Expression**: An anonymous function (inline block of code) that provides a short, readable syntax to implement the SAM of a functional interface.

```
Functional Interface (The Contract) <---- Implemented By ---- Lambda Expression (The Implementation)
```

### Core Built-in Functional Interfaces (`java.util.function`)
| Functional Interface | Abstract Method | Signature | Use Case |
| :--- | :--- | :--- | :--- |
| `Predicate<T>` | `boolean test(T t)` | `T -> boolean` | Filtering/Conditions |
| `Function<T, R>` | `R apply(T t)` | `T -> R` | Mapping/Transformation |
| `Consumer<T>` | `void accept(T t)` | `T -> void` | Consuming/Printing/Side-effects |
| `Supplier<T>` | `T get()` | `() -> T` | Factory/Lazy initialization |
| `BiFunction<T, U, R>`| `R apply(T t, U u)`| `(T, U) -> R` | Combining two inputs |

### Key Differences Table

| Feature | Functional Interface | Lambda Expression |
| :--- | :--- | :--- |
| **What is it?** | A type specification/contract (Interface). | An inline anonymous implementation mechanism. |
| **Purpose** | Defines expected input/output signature. | Provides concrete behavior without verbose inner classes. |
| **Bytecode Execution**| Compiled into a standard `.class` file interface. | Compiles via `invokedynamic` bytecode instruction (no separate `.class` file per lambda). |
| **Syntax Example** | `@FunctionalInterface interface MathOp { int op(int a, int b); }` | `MathOp add = (a, b) -> a + b;` |

### Code Comparison

```java
// Custom Functional Interface
@FunctionalInterface
interface Calculator {
    int calculate(int a, int b);
}

public class Main {
    public static void main(String[] args) {
        // 1. Old Way: Anonymous Inner Class
        Calculator oldAdd = new Calculator() {
            @Override
            public int calculate(int a, int b) {
                return a + b;
            }
        };

        // 2. Modern Way: Lambda Expression
        Calculator lambdaAdd = (a, b) -> a + b;

        System.out.println(oldAdd.calculate(5, 3));    // 8
        System.out.println(lambdaAdd.calculate(5, 3)); // 8
    }
}
```

---

## 3. Find Smallest Number After Removing K Characters

### Problem Description
Given a string `num` representing a non-negative integer and an integer `k`, return the **smallest possible integer** after removing `k` digits from `num`. If the result is empty, return `"0"`.

### Algorithmic Strategy (Greedy + Monotonic Stack)
To make a number as small as possible, we want smaller digits at the **highest significant places** (leftmost positions).
1. Traverse the string digit by digit.
2. While `k > 0`, stack is not empty, and current digit is **smaller** than the top of the stack, **pop** the stack (discarding the larger digit).
3. Push current digit to the stack.
4. If `k > 0` remains after processing all digits, pop the remaining `k` digits from the end.
5. Remove leading zeros from the result.

```
Input: num = "1432219", k = 3
Process:
- '1' -> Stack: [1]
- '4' -> Stack: [1, 4]
- '3' -> 3 < 4, pop 4, k=2 -> Stack: [1, 3]
- '2' -> 2 < 3, pop 3, k=1 -> Stack: [1, 2]
- '2' -> Stack: [1, 2, 2]
- '1' -> 1 < 2, pop 2, k=0 -> Stack: [1, 2, 1]
- '9' -> Stack: [1, 2, 1, 9]
Output: "1219"
```

### Complete Java Implementation

```java
import java.util.ArrayDeque;
import java.util.Deque;

public class SmallestNumberAfterKRemovals {

    public static String removeKdigits(String num, int k) {
        if (num == null || k >= num.length()) {
            return "0";
        }

        Deque<Character> stack = new ArrayDeque<>();

        for (char digit : num.toCharArray()) {
            // Greedy pop: remove larger previous digits to minimize leftmost numbers
            while (!stack.isEmpty() && k > 0 && stack.peekLast() > digit) {
                stack.removeLast();
                k--;
            }
            stack.addLast(digit);
        }

        // If k > 0, remove remaining digits from the right
        while (k > 0 && !stack.isEmpty()) {
            stack.removeLast();
            k--;
        }

        // Construct string and skip leading zeros
        StringBuilder sb = new StringBuilder();
        boolean leadingZero = true;
        for (char digit : stack) {
            if (leadingZero && digit == '0') {
                continue;
            }
            leadingZero = false;
            sb.append(digit);
        }

        return sb.length() == 0 ? "0" : sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(removeKdigits("1432219", 3)); // Output: "1219"
        System.out.println(removeKdigits("10200", 1));   // Output: "200"
        System.out.println(removeKdigits("10", 2));      // Output: "0"
    }
}
```

### Complexity Analysis
- **Time Complexity**: $\mathcal{O}(N)$ where $N$ is the length of `num`. Each digit is pushed and popped at most once.
- **Space Complexity**: $\mathcal{O}(N)$ for storing stack characters.

---

## 4. Java Streams - Find Top 3 Max and Min Numbers

### Requirements
Extract top 3 maximum and top 3 minimum numbers from a given `List<Integer>` using Java Streams.

### Implementation

```java
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StreamTopMinMax {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(45, 12, 89, 3, 27, 99, 1, 67, 88, 10);

        // 1. Top 3 Maximum Numbers
        List<Integer> top3Max = numbers.stream()
                .distinct()                             // Optional: remove duplicates
                .sorted(Comparator.reverseOrder())       // Sort descending
                .limit(3)                               // Take first 3
                .collect(Collectors.toList());

        // 2. Top 3 Minimum Numbers
        List<Integer> top3Min = numbers.stream()
                .distinct()                             // Optional: remove duplicates
                .sorted()                               // Sort ascending (natural order)
                .limit(3)                               // Take first 3
                .collect(Collectors.toList());

        System.out.println("Original Numbers: " + numbers);
        System.out.println("Top 3 Max Numbers: " + top3Max); // Output: [99, 89, 88]
        System.out.println("Top 3 Min Numbers: " + top3Min); // Output: [1, 3, 10]
    }
}
```

### Performance Note for Huge Datasets
Sorting an entire stream of size $N$ takes $\mathcal{O}(N \log N)$ time. For massive streams (e.g. 10 million elements), using a `PriorityQueue` (Min-Heap / Max-Heap) keeps memory and processing down to $\mathcal{O}(N \log K)$ where $K = 3$.

---

## 5. @Profile Annotation in Spring Framework

### What is `@Profile`?
`@Profile` is an annotation in Spring/Spring Boot used to conditionally map beans to specific runtime environments (e.g., `dev`, `test`, `qa`, `prod`).

```
                              +---> [Dev Config]  --> H2 Database (In-Memory)
                              |
[Active Profile: "dev"] ----->+
                              |
                              +---> [Prod Config] --> PostgreSQL Cluster
```

### Basic Code Example

```java
public interface DataSourceConfig {
    void setup();
}

// Active during 'dev' profile
@Component
@Profile("dev")
public class DevDataSourceConfig implements DataSourceConfig {
    @Override
    public void setup() {
        System.out.println("Setting up H2 In-Memory Database for DEV.");
    }
}

// Active during 'prod' profile
@Component
@Profile("prod")
public class ProdDataSourceConfig implements DataSourceConfig {
    @Override
    public void setup() {
        System.out.println("Setting up PostgreSQL Production Database.");
    }
}
```

### How to Activate Profiles

1. **`application.properties` / `application.yml`**:
   ```properties
   spring.profiles.active=dev
   ```

2. **JVM System Command Line Argument**:
   ```bash
   java -jar app.jar -Dspring.profiles.active=prod
   ```

3. **Environment Variable**:
   ```bash
   export SPRING_PROFILES_ACTIVE=prod
   ```

4. **Programmatically in Integration Tests**:
   ```java
   @SpringBootTest
   @ActiveProfiles("test")
   class UserServiceTest { ... }
   ```

### Logic Operations in `@Profile`
- `@Profile("dev & !prod")` (Dev AND NOT Prod)
- `@Profile("dev | test")` (Dev OR Test)

---

## 6. Spring Framework vs Spring Boot

### Architectural Difference
- **Spring Framework**: A foundational Java EE framework providing Dependency Injection (IoC), AOP, and MVC primitives. Requires explicit configuration (XML or Java `@Configuration`) and deployment to an external Web Server (e.g. Tomcat WAR deployment).
- **Spring Boot**: An extension on top of Spring Framework designed to provide **rapid, opinionated application bootstrap**. Features auto-configuration and embedded servers to produce standalone executable JAR files.

```
+-------------------------------------------------------------+
|                      SPRING BOOT                            |
| +-------------------------+  +----------------------------+ |
| | Auto-Configuration      |  | Starter Dependencies       | |
| +-------------------------+  +----------------------------+ |
| | Embedded Tomcat Server  |  | Spring Boot Actuator       | |
| +-------------------------+  +----------------------------+ |
| +---------------------------------------------------------+ |
| |                  SPRING FRAMEWORK                       | |
| | (IoC Container, Dependency Injection, Spring MVC, AOP)  | |
| +---------------------------------------------------------+ |
+-------------------------------------------------------------+
```

### Direct Comparison Table

| Aspect | Spring Framework | Spring Boot |
| :--- | :--- | :--- |
| **Primary Focus** | Providing modular core abstractions (IoC, AOP, Data Access). | Providing rapid, convention-over-configuration application delivery. |
| **Configuration** | Manual configuration required (`web.xml`, `@Configuration` beans). | Auto-configuration (`@EnableAutoConfiguration` / `@SpringBootApplication`). |
| **Server Setup** | External servlet container needed (Tomcat/Glassfish WAR deployment). | Embedded Tomcat/Jetty/Undertow servers (Standalone Runnable JAR). |
| **Dependency Management** | Must manage individual compatible dependency versions. | Uses **Starter POMs** (`spring-boot-starter-web`, `spring-boot-starter-data-jpa`). |
| **Production Features**| Needs manual integration for health monitoring. | Ships with **Spring Boot Actuator** (health checks, metrics, tracing out-of-the-box). |

---

## 7. Java & Spring Boot Versions

### Java LTS (Long-Term Support) Roadmap & Major Features

```
Java 8 (2014) -----> Java 11 (2018) -----> Java 17 (2021) -----> Java 21 (2023)
```

| Java Version | Key Features Introduced |
| :--- | :--- |
| **Java 8** | Lambda expressions, Streams API, `Optional`, Date & Time API (`java.time`), Interface `default` and `static` methods. |
| **Java 11** | `var` inside Lambda parameters, native `HttpClient`, String helper methods (`isBlank()`, `lines()`), `Files.readString()`. |
| **Java 17** | **Sealed Classes** (`sealed`, `permits`), **Records**, Pattern Matching for `instanceof`, Text Blocks (`"""..."""`), Switch Expressions. |
| **Java 21** | **Virtual Threads** (Project Loom), **Sequenced Collections**, Record Patterns, Pattern Matching for switch, Unnamed Patterns. |

### Spring Boot Version Baseline & Ecosystem Migration

| Spring Boot Version | Minimum Java Requirement | Spring Framework Baseline | Key Ecosystem Changes |
| :--- | :--- | :--- | :--- |
| **Spring Boot 2.x** | **Java 8** (Supports up to 17) | Spring Framework 5.x | Uses `javax.*` packages (Java EE). |
| **Spring Boot 3.x** | **Java 17** (Supports 21+) | Spring Framework 6.x | **Migrated to `jakarta.*` packages** (Jakarta EE 10). Native image compilation via GraalVM support out of the box. |

---

## 8. Synchronized vs Volatile

### Memory Visibility vs Atomicity
- **Thread Memory Model**: Each thread has a local CPU cache where it reads/writes variables. Writes to local CPU cache are not immediately visible to main memory.

```
Thread A ---> [ CPU Cache ] ---\
                                +---> [ Main Memory (RAM) ]
Thread B ---> [ CPU Cache ] ---/
```

### Detailed Comparison
* volatile = visibility guarantee
* synchronized = visibility + atomicity + mutual exclusion

| Property | `volatile` | `synchronized` |
| :--- | :--- | :--- |
| **Scope** | Variable modifier only. | Method or code block modifier. |
| **Visibility Guarantee**| **YES**: Forces reads/writes directly to Main Memory, flushing CPU cache. | **YES**: Flushes cache upon entry and exit of monitor lock. |
| **Atomicity Guarantee** | **NO**: Does not guarantee compound atomicity (e.g., `count++` is NOT safe). | **YES**: Ensures mutually exclusive single-thread access. |
| **Thread Blocking** | Non-blocking (No thread context switches). | Blocking (Threads wait in thread queue to acquire monitor lock). |
| **Performance Impact** | Very low overhead. | Higher overhead due to locking & context switching. |

### Code Examples

```java
// 1. volatile Example (Flag checking)
public class VolatileFlag implements Runnable {
    // Guarantees visibility across threads when stopRequested changes
    private volatile boolean stopRequested = false;

    public void requestStop() {
        this.stopRequested = true;
    }

    @Override
    public void run() {
        while (!stopRequested) {
            // Do light work
        }
        System.out.println("Thread safely stopped.");
    }
}

// 2. synchronized Example (Thread-safe Counter)
public class SynchronizedCounter {
    private int count = 0;

    // Mutex lock ensures both Atomicity and Visibility
    public synchronized void increment() {
        count++; // Reads, increments, writes atomically
    }

    public synchronized int getCount() {
        return count;
    }
}
```

---

## 9. ConcurrentHashMap, ArrayList, LinkedList

### Feature Comparison Matrix

| Feature | `ConcurrentHashMap` | `ArrayList` | `LinkedList` |
| :--- | :--- | :--- | :--- |
| **Data Structure** | Hash Table + Red-Black Tree (Buckets). | Dynamic Resizable Array. | Doubly-Linked List. |
| **Thread Safety** | **Thread-safe** (Bucket-level locking & CAS). | **Not thread-safe**. | **Not thread-safe**. |
| **Null Keys/Values**| **Disallowed** (Throws `NullPointerException`).| Allowed. | Allowed. |
| **Random Access** | $\mathcal{O}(1)$ average by key hash. | $\mathcal{O}(1)$ by array index. | $\mathcal{O}(N)$ (traversal needed). |
| **Insert/Delete** | $\mathcal{O}(1)$ average. | $\mathcal{O}(N)$ worst-case (shifting elements). | $\mathcal{O}(1)$ if node reference is held. |

### How `ConcurrentHashMap` Achieves Thread Safety
In Java 8+:
- Does **NOT** lock the entire map (unlike `Hashtable` or `Collections.synchronizedMap`).
- Uses **CAS (Compare-And-Swap)** for inserting nodes into empty bucket slots.
- Uses fine-grained `synchronized` locks only on the **head node of a specific bucket slot** when collision occurs.
- Concurrent reads are **lock-free**.

---

## 10. Inbound & Outbound in Java (Boxing/Unboxing)

- Guaranteed cache: -128 to 127

### Autoboxing & Unboxing Concept
- **Autoboxing**: Automatic conversion of primitive data types (`int`, `char`, `double`) into their corresponding Object Wrapper classes (`Integer`, `Character`, `Double`).
- **Unboxing**: Automatic conversion of Object Wrapper instances back to primitive values.

```java
Integer boxedObj = 10;      // Autoboxing: Integer.valueOf(10)
int primitiveNum = boxedObj; // Unboxing: boxedObj.intValue()
```

### Inbound vs Outbound Data Mapping Context
In enterprise Java APIs (Controller - Service - DTO layers):

```
[ HTTP Request JSON ] ---> (Inbound: Unboxing & DTO to Primitive Entity)
                                |
                        [ Business Logic ]
                                |
[ HTTP Response JSON ] <-- (Outbound: Primitive Entity to Boxed DTO)
```

1. **Inbound Path**:
   - External payload (JSON/DTO) contains nullable Object Wrappers (e.g. `Integer userId`).
   - During inbound processing into business code, these wrapper objects are validated and **unboxed** into primitive types for high-performance internal loops/computations.
2. **Outbound Path**:
   - Internal primitive calculation outputs are **boxed** into wrapper fields inside DTO objects for JSON serialization.

### Interview Gotchas & Pitfalls ⚠️

1. **NullPointerException (NPE) on Unboxing**:
   ```java
   Integer value = null;
   int num = value; // Throws NullPointerException at runtime!
   ```

2. **Performance Overhead in Loops**:
   ```java
   // SLOW: Autoboxing occurs inside loop 1,000,000 times!
   Long sum = 0L;
   for (long i = 0; i < 1_000_000; i++) {
       sum += i; // Unboxes sum, adds i, autoboxes new sum into a NEW Long object!
   }

   // FAST: Use primitives
   long fastSum = 0L;
   for (long i = 0; i < 1_000_000; i++) {
       fastSum += i;
   }
   ```

---

## 11. Unit Testing, Integration Testing & Mockito

### Testing Pyramid & Core Concepts
Testing ensures application stability, prevents regressions, and validates business logic.

```
       / \          E2E Tests (End-to-End, Slow, Few)
      /   \         -----------------------------------
     /     \        Integration Tests (Spring Context, DB, Web)
    /       \       -----------------------------------
   /_________\      Unit Tests (JUnit 5 + Mockito, Fast, Isolated, Many)
```

| Feature | Unit Testing | Integration Testing |
| :--- | :--- | :--- |
| **Scope** | Tests a single class/method in isolation. | Tests interaction between multiple components/layers (Controller + Service + DB). |
| **Dependencies** | External dependencies (DB, APIs) are **mocked out**. | Real or embedded dependencies are used (H2 DB, Spring Context, Testcontainers). |
| **Speed** | Extremely fast (milliseconds). | Slower (seconds, context loading overhead). |
| **Frameworks** | **JUnit 5**, **Mockito**. | **Spring Boot Test**, **MockMvc**, **`@DataJpaTest`**, **Testcontainers**. |

---

### Key Annotations: `@Mock` vs `@Spy` vs `@MockBean`

> 💡 **Classic Interview Question**: *"What is the difference between `@Mock`, `@Spy`, and `@MockBean`?"*

| Annotation | Framework | Description | Real Method Calls? |
| :--- | :--- | :--- | :--- |
| **`@Mock`** | Mockito | Creates a complete dummy/fake object. Default return values are `null`/`0`/`false`. | **No** (all methods are stubbed). |
| **`@Spy`** | Mockito | Wraps a real object. Invokes real methods unless explicitly stubbed. | **Yes** (real execution unless overridden). |
| **`@MockBean`** | Spring Boot | Replaces a Spring Bean inside the `ApplicationContext` with a Mockito mock. | **No** (used in `@SpringBootTest` / `@WebMvcTest`). |

---

### Unit Testing with JUnit 5 & Mockito

#### AAA Pattern (Arrange - Act - Assert)
Every clean test follows the AAA structure:
1. **Arrange**: Prepare input data and stub mock behavior (`when(...).thenReturn(...)`).
2. **Act**: Invoke the method under test.
3. **Assert**: Verify return values (`assertEquals`) and verify mock interactions (`verify(...)`).

#### Unit Test Example (Service Layer)

```java
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Initializes Mockito annotations
class UserServiceTest {

    @Mock
    private UserRepository userRepository; // Dependency to mock

    @InjectMocks
    private UserService userService; // Class under test with mocks injected

    private User sampleUser;

    @BeforeEach
    void setUp() {
        // Initialize mocks if @ExtendWith(MockitoExtension.class) is not used
        // MockitoAnnotations.openMocks(this); 
        sampleUser = new User(1L, "Shohan", "ADMIN");
    }

    @Test
    void getUserRole_WhenUserExists_ShouldReturnRole() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(sampleUser));

        // Act
        String role = userService.getUserRole(1L);

        // Assert
        assertEquals("ADMIN", role);
        verify(userRepository, times(1)).findById(1L); // Verifies repository call
    }
}
```

---

### Exception Testing in JUnit 5 & Mockito

> 🎯 **Key Interview Requirement**: How to test expected exceptions cleanly in Java?

#### 1. Testing Exception thrown by Service (`assertThrows`)
Use `assertThrows(ExpectedException.class, () -> executable)` to capture and verify exceptions.

```java
@Test
void getUserRole_WhenUserNotFound_ShouldThrowException() {
    // Arrange: Mock repository returns empty Optional
    when(userRepository.findById(99L)).thenReturn(Optional.empty());

    // Act
    UserNotFoundException exception = assertThrows(
        UserNotFoundException.class,
        () -> userService.getUserRole(99L)
    );

    // Assert & Verify Exception Message
    assertEquals("User not found with ID: 99", exception.getMessage());
    verify(userRepository, times(1)).findById(99L);
}
```

#### 2. Mocking Exception throwing with Mockito (`thenThrow` / `doThrow`)
- **For methods returning a value**: `when(mock.method()).thenThrow(new RuntimeException("Error"));`
- **For `void` methods**: `doThrow(new DatabaseException("DB Down")).when(mock).deleteUser(1L);`

```java
@Test
void deleteUser_WhenDatabaseFails_ShouldThrowCustomException() {
    // Arrange: Stub void method to throw exception
    doThrow(new RuntimeException("Database Connection Error"))
            .when(userRepository).deleteById(1L);

    // Act & Assert
    RuntimeException exception = assertThrows(
        RuntimeException.class,
        () -> userService.deleteUser(1L)
    );

    assertEquals("Database Connection Error", exception.getMessage());
}
```

---

### Integration Testing in Spring Boot

Integration testing verifies that components work together with the Spring `ApplicationContext`.

#### Spring Boot Slice Annotations
- **`@SpringBootTest`**: Loads full application context (used for end-to-end integration tests).
- **`@WebMvcTest(UserController.class)`**: Loads only Web layer (Controllers, Jackson converters). Mocks Service layer with `@MockBean`.
- **`@DataJpaTest`**: Loads only Persistence layer (JPA Repositories, EntityManager). Runs on in-memory DB (H2) and rolls back transactions after each test automatically.

#### Controller Integration Test Example using `MockMvc` (`@WebMvcTest`)

```java
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class) // Tests Controller in isolation
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc; // Simulates HTTP requests

    @MockBean
    private UserService userService; // Injects mock service into Spring Context

    @Test
    void getUser_ShouldReturn200OKAndJsonPayload() throws Exception {
        // Arrange
        given(userService.getUserRole(1L)).willReturn("ADMIN");

        // Act & Assert (HTTP GET Request)
        mockMvc.perform(get("/api/users/1/role")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.role").value("ADMIN"));
    }

    @Test
    void getUser_WhenNotFound_ShouldReturn404NotFound() throws Exception {
        // Arrange: Service throws exception
        given(userService.getUserRole(99L))
                .willThrow(new UserNotFoundException("User not found with ID: 99"));

        // Act & Assert
        mockMvc.perform(get("/api/users/99/role"))
                .andExpect(status().isNotFound());
    }
}
```

---

### Advanced Interview Concepts

#### 1. Parameterized Testing (`@ParameterizedTest`)
Tests a method with multiple inputs without writing duplicate test code.

```java
@ParameterizedTest
@ValueSource(strings = {"racecar", "madam", "radar"})
void shouldReturnTrueForPalindromes(String input) {
    assertTrue(StringUtils.isPalindrome(input));
}

@ParameterizedTest
@CsvSource({
    "1, 2, 3",
    "5, 5, 10",
    "10, -2, 8"
})
void testAddition(int a, int b, int expectedSum) {
    assertEquals(expectedSum, calculator.add(a, b));
}
```

#### 2. ArgumentCaptor in Mockito
Captures argument values passed to a mock method for detailed assertion.

```java
@Test
void saveUser_ShouldPassCorrectUserToRepository() {
    // Act
    userService.registerUser("Shohan", "shohan@example.com");

    // Capture argument
    ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
    verify(userRepository).save(userCaptor.capture());

    User capturedUser = userCaptor.getValue();
    assertEquals("Shohan", capturedUser.getName());
    assertEquals("shohan@example.com", capturedUser.getEmail());
}
```

#### 3. Testcontainers
A Java library that uses Docker containers for running real databases (e.g. PostgreSQL, Redis, Kafka) during integration tests instead of in-memory H2 databases. Ensures test environments match production exactly.

---

## 12. PriorityQueue

### Definition & Internal Structure
`PriorityQueue` is an unbounded queue based on a **Min Binary Heap** (by default). Elements are processed based on their priority (natural ordering or custom `Comparator`) rather than FIFO order.

```
               [ 1 ]            <-- Root (Minimum element always at top)
              /     \
          [ 3 ]     [ 5 ]
         /     \
      [ 7 ]   [ 4 ]
```

### Time Complexities

| Operation | Method | Time Complexity |
| :--- | :--- | :--- |
| **Insert** | `add(e)` / `offer(e)` | $\mathcal{O}(\log N)$ |
| **Remove Min/Max**| `poll()` / `remove()` | $\mathcal{O}(\log N)$ |
| **Inspect Min/Max**| `peek()` | $\mathcal{O}(1)$ |

### Code Example (Min-Heap vs Max-Heap)

```java
import java.util.Comparator;
import java.util.PriorityQueue;

public class PriorityQueueDemo {
    public static void main(String[] args) {
        // 1. Min-Heap (Default: Smallest numbers come out first)
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        minHeap.offer(30);
        minHeap.offer(10);
        minHeap.offer(20);
        System.out.println("Min-Heap poll: " + minHeap.poll()); // Output: 10

        // 2. Max-Heap (Custom Comparator: Largest numbers come out first)
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
        maxHeap.offer(30);
        maxHeap.offer(10);
        maxHeap.offer(20);
        System.out.println("Max-Heap poll: " + maxHeap.poll()); // Output: 30
    }
}
```

---

## 13. ExecutorService

### Purpose
`ExecutorService` (from `java.util.concurrent`) simplifies asynchronous task execution by managing a **pool of worker threads**, avoiding the overhead of manually creating `new Thread()` instances for every task.

```
Tasks Queue ---> [ Task 1, Task 2, Task 3 ]
                      |
              +-------+-------+
              | Thread Pool   |
              | [T1] [T2] [T3]|
              +---------------+
```

### Factory Methods (`Executors`)
- `Executors.newFixedThreadPool(int n)`: Fixed number of threads.
- `Executors.newCachedThreadPool()`: Dynamically creates threads as needed, reuses idle threads.
- `Executors.newSingleThreadExecutor()`: Single background worker thread.
- `Executors.newScheduledThreadPool(n)`: Fixed number of threads that can execute tasks at scheduled times or periodically.

### Code Example

```java
import java.util.concurrent.*;

public class ExecutorServiceExample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // Create pool with 2 threads
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // Submit Callable task returning a value
        Callable<String> task = () -> {
            Thread.sleep(1000);
            return "Task completed by " + Thread.currentThread().getName();
        };

        Future<String> future = executor.submit(task);

        System.out.println("Doing other work in main thread...");

        // Blocking call to retrieve result when ready
        String result = future.get();
        System.out.println("Result: " + result);

        // Graceful shutdown
        executor.shutdown();
    }
}
```

---

## 14. Comparator vs Comparable

### Key Differences Table

| Aspect | `Comparable<T>` | `Comparator<T>` |
| :--- | :--- | :--- |
| **Package** | `java.lang` | `java.util` |
| **Method** | `public int compareTo(T o)` | `public int compare(T o1, T o2)` |
| **Type of Ordering** | **Natural Ordering** (Single default sort strategy inside class). | **Custom Ordering** (Multiple custom sorting strategies). |
| **Modification** | Must modify the actual target domain class code. | External; no need to modify original domain class. |
| **Usage Syntax** | `Collections.sort(list);` | `list.sort(Comparator.comparing(...));` |

### Code Example

```java
import java.util.*;

class Student implements Comparable<Student> {
    int id;
    String name;

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // Natural sorting by ID
    @Override
    public int compareTo(Student other) {
        return Integer.compare(this.id, other.id);
    }
}

public class SortDemo {
    public static void main(String[] args) {
        List<Student> students = Arrays.asList(
            new Student(102, "Shohan"),
            new Student(101, "Rahim")
        );

        // Sort via Comparable (Natural: by ID)
        Collections.sort(students);

        // Sort via Comparator (Custom: by Name ascending)
        students.sort(Comparator.comparing(s -> s.name));
    }
}
```

---

## 15. Generic Classes & Generics

### Why Use Generics?
Generics provide **compile-time type safety**, eliminating runtime `ClassCastException` and manual typecasting.

```java
// Generic Class Syntax
public class Box<T> {
    private T item;

    public void set(T item) { this.item = item; }
    public T get() { return item; }
}
```

### Bounded Wildcards & PECS Rule
- `? extends T` (**Upper Bounded**): Accepts `T` or any subtype of `T`. Used for **reading** objects.
- `? super T` (**Lower Bounded**): Accepts `T` or any supertype of `T`. Used for **writing** objects.

> **PECS Principle**: **P**roducer **E**xtends, **C**onsumer **S**uper.
> - If generic parameter **produces** data to read -> use `<? extends T>`
> - If generic parameter **consumes** data to store -> use `<? super T>`

### Type Erasure
JVM removes all generic type annotations during compilation, replacing generic types with `Object` (or upper bound). This ensures backward compatibility with legacy pre-Java 5 code.

---

## 16. Reflection in Java

### What is Reflection?
Reflection is an API in Java (`java.lang.reflect`) that inspects or modifies class fields, methods, and constructors **at runtime**, even if private or inaccessible at compile-time.

### Framework Use Cases
- **Spring Framework**: Instantiating beans marked with `@Component` and injecting dependencies marked with `@Autowired`.
- **JUnit**: Scanning test classes for methods annotated with `@Test`.
- **Jackson / Gson**: Mapping JSON keys dynamically to private Java object fields.

### Code Example

```java
import java.lang.reflect.Field;
import java.lang.reflect.Method;

class Account {
    private String accountNumber = "ACC-9988";
}

public class ReflectionDemo {
    public static void main(String[] args) throws Exception {
        Account acc = new Account();

        // Inspect private field via reflection
        Field field = Account.class.getDeclaredField("accountNumber");
        field.setAccessible(true); // Bypass private encapsulation

        // Read field value
        String value = (String) field.get(acc);
        System.out.println("Private Field Value: " + value); // ACC-9988
    }
}
```

---

## 17. JVM Class Loaders

### Class Loading Architecture
The JVM loads `.class` files into memory dynamically during execution through three main phases: **Loading -> Linking -> Initialization**.

```
[ Application / System ClassLoader ]
                 | (Delegates Upwards)
                 v
  [ Platform / Extension ClassLoader ]
                 | (Delegates Upwards)
                 v
    [ Bootstrap ClassLoader ]
```

### Parent Delegation Hierarchy Model
When a ClassLoader receives a request to load a class:
1. It delegates the request to its **parent ClassLoader** first.
2. The parent delegates to its parent, up to the **Bootstrap ClassLoader**.
3. If the parent cannot find the class, the child attempts to load it from its classpath.

### The 3 Built-in Class Loaders
1. **Bootstrap ClassLoader**: Written in native C/C++. Loads core Java runtime classes (`java.lang.*`, `java.util.*` from `rt.jar` / JDK base modules).
2. **Platform / Extension ClassLoader**: Loads platform extensions (`lib/ext` or modular JDK extensions).
3. **Application / System ClassLoader**: Loads user-defined application classes and libraries from the application classpath (`CLASSPATH`).

---

## 18. Garbage Collection Memory Model

### JVM Heap Memory Structure (Generational GC)
The JVM Heap is split into generations based on the empirical observation that **most objects die young**.

```
+-------------------------------------------------------+-----------------------+
|                    YOUNG GENERATION                   |    OLD GENERATION     |
| +------------------+ +----------------+ +-----------+ |  (Tenured Space)      |
| |    Eden Space    | | Survivor S0    | | Survivor  | |                       |
| |                  | | (From Space)   | | S1 (To)   | | Long-surviving        |
| +------------------+ +----------------+ +-----------+ | Objects               |
+-------------------------------------------------------+-----------------------+
```

1. **Young Generation**:
   - **Eden Space**: All new objects are allocated here initially.
   - **Survivor Spaces (S0 & S1)**: Objects that survive a **Minor GC** cycle in Eden are copied between S0 and S1.
2. **Old (Tenured) Generation**:
   - Objects that survive multiple Minor GC cycles (based on aging threshold `MaxTenuringThreshold`) are promoted to Old Gen.
3. **Metaspace (Java 8+)**:
   - Replaced old PermGen. Stores class metadata in **native off-heap memory**.

---

## 19. JVM Algorithms & Collectors

### Base GC Collector Algorithms
1. **Mark-Sweep-Compact**:
   - **Mark**: Identify referenced vs unreferenced objects.
   - **Sweep**: Reclaim memory of unreferenced objects.
   - **Compact**: Shift remaining objects to eliminate memory fragmentation.

### Production GC Collectors

| Collector | Algorithm / Mechanism | Best Use Case |
| :--- | :--- | :--- |
| **Parallel GC** | Multi-threaded throughput collector. | Batch processing jobs where pause time is tolerable. |
| **G1 GC** *(Default since Java 9)* | Regions-based collector with target pause time optimization. | General web services with large heap sizes (>4GB). |
| **ZGC / Shenandoah** | Ultra-low-latency, concurrent collectors (pause times < 1 millisecond). | Financial trading systems / real-time high throughput applications. |

### JIT (Just-In-Time) Compiler Optimization
- The JVM interprets bytecode initially.
- **HotSpot Detection**: Identifies "hot code" (frequently executed loops/methods).
- **C1 Compiler**: Quick compilation with basic optimizations.
- **C2 Compiler**: Heavy optimizations (method inlining, dead code elimination, escape analysis) converting bytecode directly to native CPU machine instructions.

---

## 20. Return Largest Palindrome Substring

### Problem Description
Given a string `s`, return the **longest palindromic substring** in `s`.

### Strategy (Expand Around Center)
A palindrome mirrors around its center. There are $2N - 1$ possible centers:
- Single character centers (odd-length palindromes, e.g. `"aba"` center `'b'`).
- Two-character centers (even-length palindromes, e.g. `"abba"` center `'bb'`).

For each center index, expand outwards as long as left and right characters match.

### Complete Java Implementation

```java
public class LongestPalindromicSubstring {

    public static String longestPalindrome(String s) {
        if (s == null || s.length() < 1) return "";

        int start = 0;
        int maxLen = 0;

        for (int i = 0; i < s.length(); i++) {
            // Case 1: Odd length palindrome (centered at i)
            int len1 = expandFromCenter(s, i, i);
            // Case 2: Even length palindrome (centered at i and i+1)
            int len2 = expandFromCenter(s, i, i + 1);

            int currentMaxLen = Math.max(len1, len2);

            if (currentMaxLen > maxLen) {
                maxLen = currentMaxLen;
                // Calculate start index based on center position and total length
                // start = center - distance
                start = i - (currentMaxLen - 1) / 2;
            }
        }

        return s.substring(start, start + maxLen);
    }

    private static int expandFromCenter(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        // Length of palindrome is (right - 1) - (left + 1) + 1 = right - left - 1
        // left and right are one position outside the palindrome
        return right - left - 1;
    }

    public static void main(String[] args) {
        System.out.println(longestPalindrome("babad")); // Output: "bab" or "aba"
        System.out.println(longestPalindrome("cbbd"));  // Output: "bb"
    }
}
```

### Complexity Analysis
- **Time Complexity**: $\mathcal{O}(N^2)$ because expanding around center takes $\mathcal{O}(N)$ for each of the $N$ center positions.
- **Space Complexity**: $\mathcal{O}(1)$ auxiliary space.

---
