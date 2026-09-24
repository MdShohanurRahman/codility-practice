# System Design, Microservices, SQL & DevOps Interview Notes - Set 02

Comprehensive, interview-ready guide covering Microservices Architecture, Distributed Systems, Design Patterns, SOLID Principles, System Design (Payment Wallet), SQL, Kafka, and CI/CD.

---

## Table of Contents
1. [Benefits of Microservices Architecture](#1-benefits-of-microservices-architecture)
2. [Monolithic vs Microservices Architecture](#2-monolithic-vs-microservices-architecture)
3. [API Gateway & Service Discovery](#3-api-gateway--service-discovery)
4. [Circuit Breaker Pattern in Microservices](#4-circuit-breaker-pattern-in-microservices)
5. [SAGA Pattern for Distributed Transactions](#5-saga-pattern-for-distributed-transactions)
6. [How to Scale Microservices (Real-World Approach)](#6-how-to-scale-microservices-real-world-approach)
7. [Idempotency in Distributed & Payment Systems](#7-idempotency-in-distributed--payment-systems)
8. [Types of Design Patterns (Overview)](#8-types-of-design-patterns-overview)
9. [Behavioral Design Patterns Explained (With Examples)](#9-behavioral-design-patterns-explained-with-examples)
10. [SOLID Principles in Backend Development](#10-solid-principles-in-backend-development)
11. [CAP Theorem & PACELC Theorem Explained](#11-cap-theorem--pacelc-theorem-explained)
12. [Polyglot Persistence in Microservices](#12-polyglot-persistence-in-microservices)
13. [System Design: Paytm / Payment Wallet Architecture](#13-system-design-paytm--payment-wallet-architecture)
14. [Distributed Caching & Redis Strategies](#14-distributed-caching--redis-strategies)
15. [SQL Interview Question: 3rd Highest Salary](#15-sql-interview-question-3rd-highest-salary)
16. [SQL Concept: Meaning of "1" in Queries](#16-sql-concept-meaning-of-1-in-queries)
17. [MongoDB vs MySQL (When to Use What?)](#17-mongodb-vs-mysql-when-to-use-what)
18. [Apache Kafka in Microservices Architecture](#18-apache-kafka-in-microservices-architecture)
19. [Jenkins & CI/CD Pipeline for Backend](#19-jenkins--cicd-pipeline-for-backend)
20. [Database Indexing & Query Optimization](#20-database-indexing--query-optimization)

---

## 1. Benefits of Microservices Architecture

### Overview
Microservices Architecture structures an application as a collection of small, loosely coupled, independently deployable services organized around specific business capabilities.

```
+-------------------------------------------------------------------------------+
|                      MICROSERVICES ARCHITECTURE BENEFITS                      |
+-------------------+-------------------+-------------------+-------------------+
| Autonomous        | Independent       | Technology        | Fault             |
| Scalability       | Deployability     | Flexibility       | Isolation         |
+-------------------+-------------------+-------------------+-------------------+
```

### Key Benefits
1. **Autonomous & Targeted Scalability**:
   - Individual services can be scaled independently based on load. For example, during a flash sale, the **Payment Service** and **Catalog Service** can scale out without wasting resources scaling the static **Review Service**.

2. **Independent Deployments & Faster Time-to-Market**:
   - Teams can build, test, and deploy microservices independently without redeploying the entire application stack or coordinating massive release cycles.

3. **Technology & Polyglot Flexibility**:
   - Each service can use the best tech stack for its domain. E.g., High-throughput Payment Service in Java/Spring Boot, ML Recommendation Engine in Python, Real-time Chat Service in Node.js/Go.

4. **Fault Isolation & Resilience**:
   - A failure in one non-critical service (e.g., Recommendation Service crashing due to out-of-memory) does not bring down the entire system (User login and Checkout remain operational).

5. **Organizational Alignment (Conway's Law)**:
   - Small, cross-functional engineering teams own specific microservices end-to-end (DevOps, DB, Backend).

---

## 2. Monolithic vs Microservices Architecture

### Architecture Comparison

```
MONOLITHIC ARCHITECTURE:
+-----------------------------------------------------------------+
|  [ User UI ] --> [ Single Process: Auth + Orders + Payments ]  |
|                               |                                 |
|                      [ Single Shared DB ]                       |
+-----------------------------------------------------------------+

MICROSERVICES ARCHITECTURE:
+-----------------------------------------------------------------+
|  [ User UI ] --> [ API Gateway ]                                |
|                        |-- /auth    --> [ Auth Service ] -> DB1 |
|                        |-- /orders  --> [ Order Service ] -> DB2|
|                        |-- /pay     --> [ Payment Svc ] -> DB3  |
+-----------------------------------------------------------------+
```

### Detailed Comparison Matrix

| Dimension | Monolithic Architecture | Microservices Architecture |
| :--- | :--- | :--- |
| **Complexity** | Low initial complexity; becomes a "Big Ball of Mud" as code grows. | High operational complexity (Distributed tracing, networking, deployment overhead). |
| **Deployment** | Single unified deployment unit (WAR/JAR). | Hundreds of independently deployed services/containers. |
| **Database** | Single centralized relational database. | Database-per-Service pattern (Polyglot persistence). |
| **Data Consistency**| Strong ACID consistency via local DB transactions. | Eventual consistency via SAGA / Event-Driven architecture. |
| **Testing** | Easy local end-to-end testing. | Requires mock services, contract testing, and integration environments. |
| **Debugging** | Easy stack-trace debugging in a single IDE process. | Requires distributed tracing tools (Zipkin, Jaeger, OpenTelemetry). |

### When to Choose What? 💡
- **Choose Monolith**: For early-stage startups, MVPs, domain boundaries not clearly defined, small engineering team (< 10 devs).
- **Choose Microservices**: Large domain, high transaction traffic, multiple independent teams (> 20+ devs), need independent scaling and zero-downtime deployments.

---

## 3. API Gateway & Service Discovery

### 1. API Gateway Pattern
An **API Gateway** acts as the single entry point for all client requests in a microservices ecosystem.

```
Clients (Mobile/Web) 
       |
       v
+---------------------------------------------------------------+
|                       API GATEWAY                             |
|  - Routing & Reverse Proxy    - Rate Limiting (Token Bucket)  |
|  - Auth (JWT / OAuth2)        - SSL Termination               |
|  - Request Transformation     - Response Aggregation          |
+---------------------------------------------------------------+
       |                   |                   |
       v                   v                   v
[ Order Service ]   [ User Service ]   [ Payment Service ]
```

#### Primary Responsibilities:
- **Routing**: Directing client paths (`/api/v1/orders`) to target microservices.
- **Authentication & Authorization**: Validating JWT tokens at the edge so downstream services don't repeat auth checks.
- **Rate Limiting**: Preventing DDoS or API abuse using Redis Token Bucket filters.
- **Circuit Breaking & Fallbacks**: Returning graceful fallback responses if downstream services time out.

---

### 2. Service Discovery Pattern (Eureka / Consul)
In containerized environments (Kubernetes, AWS ECS), service instances are dynamic with auto-assigned IP addresses. **Service Discovery** maintains a real-time registry of active instances.

```
1. Register IP/Port             +--------------------+
+-----------------------------> | Service Registry   |
|                               | (Netflix Eureka)   |
| 2. Fetch Active Instances     +--------------------+
| +------------------------------------^
| |                                    |
[ Order Service ] -- 3. REST Call --> [ Inventory Service ]
```

- **Client-Side Discovery** (e.g. Spring Cloud LoadBalancer + Eureka): Client queries registry, gets IPs, and load-balances locally.
- **Server-Side Discovery** (e.g. AWS ALB / Kubernetes ClusterIP DNS): Client calls load balancer, which resolves IP via internal DNS.

---

## 4. Circuit Breaker Pattern in Microservices

### Problem Addressed
In microservices, if Service A synchronously calls Service B, and Service B experiences slow response times or database lockup, requests stack up in Service A's thread pool. This leads to **cascading failures** across the entire application.

```
[ Client ] ---> [ Service A (Threads Blocked) ] ---> [ Service B (Failing/Timeout) ]
```

### Circuit Breaker State Machine

```
              +-----------------------------+
              |           CLOSED            | (Normal Operation: All calls allowed)
              +-----------------------------+
                |                         ^
                | Failure Rate > Threshold| Success Rate > Threshold
                v                         |
              +-----------------------------+
              |            OPEN             | (Calls fail immediately with fallback)
              +-----------------------------+
                |                         ^
                | Sleep Window Expires    | Failure Occurs
                v                         |
              +-----------------------------+
              |          HALF-OPEN          | (Trial calls allowed to test health)
              +-----------------------------+
```

1. **CLOSED**: Normal state. Requests flow to the target service. If failure percentage exceeds a threshold (e.g. 50%), circuit flips to **OPEN**.
2. **OPEN**: Circuit trips. Calls to target service fail fast **without attempting execution**, returning a fallback response instantly.
3. **HALF-OPEN**: After a configured wait duration (e.g., 10 seconds), the circuit permits a limited number of test requests. If successful, state reverts to **CLOSED**; if failed, it flips back to **OPEN**.

### Resilience4j Implementation in Spring Boot

```java
@Service
public class OrderService {

    @Autowired
    private PaymentClient paymentClient;

    // Resilience4j Circuit Breaker with Fallback
    @CircuitBreaker(name = "paymentService", fallbackMethod = "paymentFallback")
    public String processPayment(PaymentRequest request) {
        return paymentClient.execute(request);
    }

    // Fallback method must have matching arguments + Exception parameter
    public String paymentFallback(PaymentRequest request, Throwable t) {
        return "Payment service is currently unavailable. Order queued for offline processing.";
    }
}
```

---

## 5. SAGA Pattern for Distributed Transactions

### Why Traditional 2PC (Two-Phase Commit) Fails in Microservices
In microservices, each service has its own database. Traditional ACID transactions (Two-Phase Commit - 2PC) across network boundaries cause severe thread locking, low throughput, and tight coupling.

### The SAGA Solution
A **SAGA** is a sequence of local transactions. Each local transaction updates data in a single service's database. If a local transaction fails, SAGA executes a series of **Compensating Transactions** to undo the previous changes.

```
Happy Path SAGA Flow:
[ Create Order ] ---> [ Reserve Inventory ] ---> [ Process Payment ] ---> [ Order Complete ]

Rollback (Compensating) SAGA Flow on Payment Failure:
[ Create Order ] ---> [ Reserve Inventory ] ---> [ Payment FAILS ❌ ]
  (Status: Pending)      (Stock Deducted)            |
        |                      |                     |
        v                      v                     v
[ Cancel Order ] <--- [ Refund Inventory ] <---------+
  (Compensating)       (Compensating)
```

### SAGA Implementation Approaches

| Approach | Mechanism | Pros | Cons |
| :--- | :--- | :--- | :--- |
| **Choreography** | Services publish/listen to domain events asynchronously (via Kafka/RabbitMQ). | Simple for small workflows, highly decoupled. | Hard to track workflow state as services grow; risk of cyclic dependencies. |
| **Orchestration** | A centralized **Saga Orchestrator** instructs each service what local transaction to execute. | Clear central workflow management, easier debugging and monitoring. | Risk of orchestrator becoming a bottleneck or smart-coordinator logic hub. |

---

## 6. How to Scale Microservices (Real-World Approach)

### The Scale Cube (3D Scaling)

```
                    Y-Axis: Functional Decomposition (Microservices)
                                  ^
                                  |   / Z-Axis: Data Partitioning (Sharding)
                                  |  /
                                  | /
  X-Axis: Horizontal Cloning -----+------------------>
  (Multiple Container Replicas)
```

1. **X-Axis Scaling (Horizontal Pod Autoscaling - HPA)**:
   - Run multiple stateless container replicas behind a Load Balancer (Kubernetes HPA scales based on CPU/RAM usage or custom metrics).

2. **Y-Axis Scaling (Microservices Decomposition)**:
   - Split a monolithic codebase into decoupled services based on domain boundaries (DDD).

3. **Z-Axis Scaling (Database Partitioning / Sharding)**:
   - Split database rows across multiple database servers based on a key (e.g. `user_id % 4`).

### Real-World System Scaling Techniques
- **Stateless Application Tier**: Store session state in external fast memory stores (Redis) so any container instance can handle any user request.
- **Database Read-Replicas**: Split DB traffic into Read (Replicas) vs Write (Primary/Master).
- **Asynchronous Event Queues**: Offload heavy background work (email sending, report generation) to Kafka/RabbitMQ.
- **Edge Caching & Content Delivery Network (CDN)**: Cache static assets and REST API endpoints at global CDN locations (Cloudflare/CloudFront).

---

## 7. Idempotency in Distributed & Payment Systems

### What is Idempotency?
An API operation is **idempotent** if making multiple identical requests has the **exact same effect** on the server state as making a single request.

- **Idempotent HTTP Methods**: `GET`, `PUT`, `DELETE`, `HEAD`, `OPTIONS`.
- **Non-Idempotent HTTP Methods**: `POST` (Creating resources, e.g. charging a credit card).

### Why is Idempotency Critical in Payments?
If a network timeout occurs during a `/api/v1/payments/charge` call, the client/mobile app retries the request. Without idempotency, the customer will be charged twice!

```
Client                             Payment API                     Distributed Redis Lock
  |                                     |                                   |
  | -- POST /charge (Key: IDEM-101) --> |                                   |
  |                                     | -- SetNX("IDEM-101", IN_PROGRESS)->| (Acquired)
  |                                     | -- Process Credit Card Charge --> |
  |<-- Network Timeout ❌ -------------- | -- Store Result in Redis --------->|
  |                                     |                                   |
  | -- RETRY POST /charge (Key: IDEM-101)-> |                                   |
  |                                     | -- SetNX("IDEM-101") ------------>| (Fails: Exists!)
  |                                     | -- Fetch Cached Result ---------->|
  |<-- 200 OK (Original Charge Result) -|                                   |
```

### Idempotency Key Header Pattern Implementation
1. Client generates a unique UUID `Idempotency-Key: e8a3d12b-45c1...` for the payment transaction.
2. Server checks Redis for the key:
   - If **Key Exists & Processed**: Immediately return the cached response without re-charging.
   - If **Key Exists & In-Progress**: Return HTTP `409 Conflict` (Processing).
   - If **Key Does Not Exist**: Acquire a distributed lock (`SETNX`), execute payment, record response in Redis with TTL (e.g. 24 hours), and release lock.

---

## 8. Types of Design Patterns (Overview)

Design patterns are reusable, battle-tested solutions to common software design problems. Classified into **3 Gang of Four (GoF) Categories**:

```
+-------------------------------------------------------------------------------+
|                             DESIGN PATTERN CATEGORIES                         |
+-------------------+-------------------+-------------------+-------------------+
| Creational        | Structural        | Behavioral        |                   |
| (Object Creation) | (Class Structure) | (Communication)   |                   |
+-------------------+-------------------+-------------------+-------------------+
```

### Summary Matrix

| Category | Purpose | Patterns Included | Real-world Use Case |
| :--- | :--- | :--- | :--- |
| **Creational** | Controls how objects are instantiated safely and flexibly. | Singleton, Factory Method, Abstract Factory, Builder, Prototype. | `DbContext`, `StringBuilder`, Spring `@Bean` singletons. |
| **Structural** | Assembles classes and objects into larger structures while keeping them flexible. | Adapter, Decorator, Proxy, Facade, Composite, Bridge. | Spring AOP Proxies, Java I/O Streams (`BufferedReader(FileReader)`). |
| **Behavioral** | Manages algorithms, relationships, and communication between objects. | Strategy, Observer, Command, Chain of Responsibility, State, Template Method. | Spring Security Filter Chain, Event Listeners, Payment Gateways. |

---

## 9. Behavioral Design Patterns Explained (With Examples)

### 1. Strategy Pattern
Defines a family of algorithms, encapsulates each one, and makes them interchangeable at runtime based on context.

```java
// Strategy Interface
public interface PaymentStrategy {
    void pay(double amount);
}

// Concrete Strategies
@Component("CREDIT_CARD")
public class CreditCardPayment implements PaymentStrategy {
    public void pay(double amount) { System.out.println("Paid $" + amount + " via Credit Card."); }
}

@Component("BKASH")
public class BkashPayment implements PaymentStrategy {
    public void pay(double amount) { System.out.println("Paid $" + amount + " via bKash."); }
}

// Context Service using Spring Dependency Injection Map
@Service
public class PaymentContextService {
    @Autowired
    private Map<String, PaymentStrategy> paymentStrategies; // Injects strategies by bean name

    public void executePayment(String paymentMethod, double amount) {
        PaymentStrategy strategy = paymentStrategies.get(paymentMethod);
        if (strategy == null) throw new IllegalArgumentException("Invalid Payment Method");
        strategy.pay(amount);
    }
}
```

---

### 2. Observer Pattern
Defines a one-to-many dependency so that when one object (Subject) changes state, all registered dependents (Observers) are notified automatically.

```java
// Spring Application Event Listener (Observer Pattern in Spring)
public class OrderCreatedEvent {
    private final Long orderId;
    public OrderCreatedEvent(Long orderId) { this.orderId = orderId; }
    public Long getOrderId() { return orderId; }
}

// Observer 1: Email Notification
@Component
public class EmailNotificationListener {
    @EventListener
    public void handleOrderCreated(OrderCreatedEvent event) {
        System.out.println("Sending confirmation email for order: " + event.getOrderId());
    }
}

// Observer 2: Inventory Deductor
@Component
public class InventoryListener {
    @EventListener
    public void handleOrderCreated(OrderCreatedEvent event) {
        System.out.println("Deducting inventory for order: " + event.getOrderId());
    }
}
```

---

### 3. Chain of Responsibility Pattern
Passes a request along a chain of handlers. Each handler decides either to process the request or pass it to the next handler in the chain.

- **Real World Example**: **Spring Security Filter Chain** (`JwtAuthenticationFilter` -> `UsernamePasswordAuthenticationFilter` -> `CorsFilter`).

---

## 10. SOLID Principles in Backend Development

SOLID principles are five design guidelines that make software designs understandable, flexible, and maintainable.

```
S - Single Responsibility Principle (SRP)
O - Open/Closed Principle (OCP)
L - Liskov Substitution Principle (LSP)
I - Interface Segregation Principle (ISP)
D - Dependency Inversion Principle (DIP)
```

### Detailed Breakdown with Code Examples

#### 1. S - Single Responsibility Principle (SRP)
> *"A class should have one, and only one, reason to change."*

```java
// VIOLATION: UserInvoiceService handles business logic AND DB persistence AND PDF generation.
class UserInvoiceServiceBad {
    public void generateInvoice() { /* logic */ }
    public void saveToDatabase() { /* DB logic */ }
    public void sendPdfEmail() { /* Email logic */ }
}

// GOOD (SRP): Split into focused single-responsibility classes
class InvoiceCalculator { public Invoice calculate() { return new Invoice(); } }
class InvoiceRepository { public void save(Invoice inv) { /* DB logic */ } }
class EmailService { public void sendInvoice(Invoice inv) { /* Email */ } }
```

---

#### 2. O - Open/Closed Principle (OCP)
> *"Software entities should be open for extension, but closed for modification."*

```java
// GOOD: Extend behavior by adding new implementations of DiscountStrategy without touching existing code.
interface DiscountStrategy { double apply(double price); }

class VIPDiscount implements DiscountStrategy {
    public double apply(double price) { return price * 0.80; }
}

class RegularDiscount implements DiscountStrategy {
    public double apply(double price) { return price * 0.95; }
}
```

---

#### 3. L - Liskov Substitution Principle (LSP)
> *"Subtypes must be substitutable for their base types without breaking application correctness."*

```java
// VIOLATION: Ostrich inherits Bird but throws Exception on fly()!
class Bird { public void fly() { System.out.println("Flying"); } }
class Ostrich extends Bird {
    @Override public void fly() { throw new UnsupportedOperationException("Can't fly!"); }
}

// GOOD: Separate interfaces for specific capabilities
interface Flyable { void fly(); }
class Sparrow implements Flyable { public void fly() { System.out.println("Flying"); } }
class Ostrich { /* Does not implement Flyable */ }
```

---

#### 4. I - Interface Segregation Principle (ISP)
> *"Clients should not be forced to depend on methods they do not use."*

```java
// VIOLATION: Fat Interface
interface MultiFunctionDevice {
    void print();
    void scan();
    void fax();
}

// GOOD: Segregated interfaces
interface Printer { void print(); }
interface Scanner { void scan(); }

class SimplePrinter implements Printer {
    public void print() { System.out.println("Printing document..."); }
}
```

---

#### 5. D - Dependency Inversion Principle (DIP)
> *"High-level modules should not depend on low-level modules. Both should depend on abstractions."*

```java
// GOOD: UserService depends on NotificationService interface, NOT concrete EmailSender
interface NotificationService { void send(String msg); }

@Service
public class UserService {
    private final NotificationService notificationService;

    @Autowired // Constructor Dependency Injection
    public UserService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
}
```

---

## 11. CAP Theorem & PACELC Theorem Explained

### CAP Theorem Definition
In any distributed data store, you can choose **at most two out of three** guarantees:

```
                  Consistency (C)
                     /       \
                    /         \
                   /   CAP     \
                  /  THEOREM    \
                 /               \
   Availability (A) -------------- Partition Tolerance (P)
```

1. **Consistency (C)**: Every read receives the most recent write or an error.
2. **Availability (A)**: Every non-failing node returns a non-error response (without guarantee that it contains the most recent write).
3. **Partition Tolerance (P)**: The system continues operating despite network packet loss or node failures.

> ⚠️ **Reality Check**: In distributed networks, **Partition Tolerance (P) is mandatory** because network cuts happen. Thus, the real choice is between **CP** and **AP**:
> - **CP Systems** (e.g. HBase, MongoDB master-node, PostgreSQL cluster): Prioritizes absolute consistency over availability during network partition (fails requests rather than returning stale data).
> - **AP Systems** (e.g. Cassandra, DynamoDB, CouchDB): Prioritizes availability over consistency (returns potentially stale data during partition; syncs later via eventual consistency).

---

### PACELC Theorem Extension
CAP theorem only describes system behavior **during network partitions (P)**. What about normal operation? **PACELC** expands CAP:

```
If there is a Partition (P):
    Choose between Availability (A) and Consistency (C)
Else (E):
    Choose between Latency (L) and Consistency (C)
```

- **Example**: DynamoDB/Cassandra are **PA/EL** (In partition: Available; Else: Low Latency).
- **Example**: MongoDB/PostgreSQL are **PC/EC** (In partition: Consistent; Else: High Consistency at cost of latency).

---

## 12. Polyglot Persistence in Microservices

### Concept
**Polyglot Persistence** means utilizing different database engines for different microservices based on their specific data access patterns, storage needs, and query performance.

```
                             +-----------------------------------+
                             |     MICROSERVICES SYSTEM          |
                             +-----------------------------------+
                               /           |           |       \
                              /            |           |        \
                             v             v           v         v
                     [ Payment Svc ]  [ Catalog Svc ] [ Session ] [ Social Graph ]
                           |               |           |            |
                           v               v           v            v
                     +-----------+   +-----------+  +-------+   +-----------+
                     | PostgreSQL|   |  MongoDB  |  | Redis |   |   Neo4j   |
                     | (RDBMS)   |   | (Document)|  |(Cache)|   |  (Graph)  |
                     +-----------+   +-----------+  +-------+   +-----------+
```

### Database Selection Guide

| Data Pattern | Recommended Storage Engine | Reason / Advantage |
| :--- | :--- | :--- |
| **Financial / Orders / Wallet** | **Relational RDBMS** (PostgreSQL, MySQL). | Strict ACID compliance, relational foreign keys, transactional integrity. |
| **Product Catalog / Content** | **Document NoSQL** (MongoDB). | Flexible schema, fast JSON nested structure reads. |
| **Sessions / Caching / Leaderboards** | **In-Memory Key-Value** (Redis, Memcached). | Sub-millisecond latency operations. |
| **Search / Full-Text Logs** | **Search Engine** (Elasticsearch, OpenSearch). | Inverted index, fuzzy search, log aggregation. |
| **Social Connections / Recommendations** | **Graph Database** (Neo4j, Amazon Neptune). | Fast traverse of multi-hop node relationships. |

---

## 13. System Design: Paytm / Payment Wallet Architecture

### Requirements
- High Availability (99.999%).
- Zero data loss, zero double-spending.
- Low-latency balance checks and peer-to-peer transfers.

### High-Level Architecture Diagram

```
[ Mobile / Web Client ]
          |
          v
  [ API Gateway ] (Rate Limiting, Auth Validation, Idempotency Check)
          |
          v
  [ Wallet Service ] <-------> [ Distributed Redis Cache ] (Balance Caching)
          |
          +---> [ Idempotency Lock ] (Redis SETNX)
          |
          +---> [ Double-Entry Ledger Service ] 
                      |
                      v
             +-------------------+
             | PostgreSQL DB     | (Master DB with Row-Level Locking)
             | (ACID Compliant)  |
             +-------------------+
                      |
           (Kafka Transaction Event)
                      |
                      v
             [ Notification Service ] ---> [ SMS / Email / Push ]
```

### Core Architectural Decisions

1. **Idempotency Execution**:
   - Every transaction payload contains a client-generated UUID `transaction_id`.
   - Redis enforces atomic distributed locking using `SETNX transaction_id EX 30`.

2. **Double-Entry Bookkeeping Ledger**:
   - Money is never modified in-place with a simple `UPDATE user SET balance = balance - 100`.
   - Every financial operation records **two immutable ledger entries**: one **Debit** and one **Credit**.
   ```sql
   -- Transaction 5001: User A sends $100 to User B
   INSERT INTO ledger (tx_id, account_id, type, amount) VALUES ('TX5001', 'USER_A', 'DEBIT', 100.00);
   INSERT INTO ledger (tx_id, account_id, type, amount) VALUES ('TX5001', 'USER_B', 'CREDIT', 100.00);
   ```
   - Total account balance = `SUM(CREDIT) - SUM(DEBIT)`.

3. **Database Concurrency Control**:
   - Use **Pessimistic Locking** (`SELECT ... FOR UPDATE`) during high-concurrency balance deductions to prevent race conditions:
   ```sql
   BEGIN TRANSACTION;
   SELECT balance FROM wallet WHERE user_id = 'USER_A' FOR UPDATE;
   -- Perform balance check, then insert ledger entry
   COMMIT;
   ```

---

## 14. Distributed Caching & Redis Strategies

### Caching Strategies

```
1. Cache-Aside (Lazy Loading):
   App ---> Check Cache ---> Miss ---> Fetch DB ---> Write Cache ---> Return

2. Write-Through:
   App ---> Write Cache ---> Cache Writes DB Synchronously ---> Return

3. Write-Behind (Write-Back):
   App ---> Write Cache ---> Async Worker Batch Writes DB (Fastest writes, risk of data loss)
```

### Handling Production Cache Issues ⚠️

1. **Cache Stampede / Thundering Herd**:
   - **Problem**: A hot key (e.g. viral product details) expires. Thousands of concurrent requests hit the DB at once, causing DB crash.
   - **Solution**: Use **Mutual Exclusion Lock (Mutex)** in Redis. The first thread acquires a lock to query DB and update cache; other threads wait or receive stale data.

2. **Cache Penetration**:
   - **Problem**: Requests query non-existent IDs (e.g., `id = -9999`) continuously, bypassing cache and hitting DB.
   - **Solution**: Cache null values with short TTL OR use a **Bloom Filter**.

3. **Cache Avalanche**:
   - **Problem**: Thousands of cache keys expire at the exact same timestamp.
   - **Solution**: Add random jitter/variance to TTLs (e.g. `TTL = 3600 + random(0, 300)` seconds).

---

## 15. SQL Interview Question: 3rd Highest Salary

### Problem
Find the 3rd highest salary from an `Employee` table without hardcoding values.

```sql
-- Schema
CREATE TABLE Employee (
    id INT PRIMARY KEY,
    name VARCHAR(50),
    salary DECIMAL(10,2)
);
```

### Approach 1: Modern Window Function (`DENSE_RANK()`) ⭐ (Recommended)
`DENSE_RANK()` handles duplicate salaries correctly (unlike `ROW_NUMBER()`).

```sql
WITH RankedSalaries AS (
    SELECT name, salary,
           DENSE_RANK() OVER (ORDER BY salary DESC) as salary_rank
    FROM Employee
)
SELECT name, salary
FROM RankedSalaries
WHERE salary_rank = 3;
```

### Approach 2: Standard `LIMIT` and `OFFSET` (MySQL / PostgreSQL)

```sql
SELECT DISTINCT salary 
FROM Employee
ORDER BY salary DESC
LIMIT 1 OFFSET 2; -- Skip top 2 salaries, pick the 3rd
```

### Approach 3: Correlated Subquery (ANSI SQL Compatible across all databases)

```sql
SELECT DISTINCT salary
FROM Employee e1
WHERE 2 = (
    SELECT COUNT(DISTINCT e2.salary)
    FROM Employee e2
    WHERE e2.salary > e1.salary
);
```

---

## 16. SQL Concept: Meaning of "1" in Queries

### 1. `SELECT 1 FROM Table`
Used in existence checks (e.g., `EXISTS` subqueries). It returns the literal constant `1` for every matching row instead of reading actual column data from disk, optimizing performance.

```sql
-- Checks if user exists without pulling column data
IF EXISTS (SELECT 1 FROM Users WHERE email = 'shohan@example.com') THEN
    -- User exists
END IF;
```

---

### 2. `WHERE 1=1`
Used in dynamic SQL query builders (like Hibernate Criteria API or MyBatis). `1=1` is a dummy condition that is always `TRUE`. It allows appending subsequent conditional `AND condition` clauses cleanly without checking if it's the first `WHERE` clause.

```sql
-- Dynamic SQL Construction
String sql = "SELECT * FROM Products WHERE 1=1";
if (name != null) sql += " AND name = '" + name + "'";
if (category != null) sql += " AND category = '" + category + "'";
```

---

### 3. `COUNT(1)` vs `COUNT(*)` vs `COUNT(column)`
- **`COUNT(*)` & `COUNT(1)`**: Identical in performance across modern query optimizers (PostgreSQL, MySQL, Oracle). Both count total rows including `NULL` values.
- **`COUNT(column)`**: Counts only rows where the specified `column` is **NOT NULL**.

---

## 17. MongoDB vs MySQL (When to Use What?)

### Comparison Matrix

| Feature | MySQL (RDBMS) | MongoDB (NoSQL Document) |
| :--- | :--- | :--- |
| **Data Model** | Relational Tables, Rows, Columns. | JSON-like BSON Documents / Collections. |
| **Schema** | Rigid, predefined schema (`CREATE TABLE`). | Dynamic, flexible, schema-less. |
| **Transactions** | Full ACID compliance out of the box. | Multi-document ACID support (since v4.0), but designed for BASE. |
| **Joins & Relations** | Native SQL `JOIN` support. | No native joins; embedded documents or `$lookup`. |
| **Scaling** | Vertical scaling (Scale-up); Horizontal via Read-Replicas. | Horizontal scaling out-of-the-box (Native Sharding). |

### Decision Framework
- **Use MySQL**: When data structures are highly relational, require strict transaction integrity, and queries rely heavily on multi-table joins (e.g. Banking, ERP, E-commerce Checkout).
- **Use MongoDB**: When handling unstructured/semi-structured data, rapidly evolving schemas, real-time analytics logs, product catalog with variable attributes, or high-write volume requiring horizontal scaling.

---

## 18. Apache Kafka in Microservices Architecture

### Core Concepts

```
[ Producer ] ---> [ Topic: order-events ]
                     |-- Partition 0 [ Offset 0, 1, 2... ] ---> Consumer 1 (Group A)
                     |-- Partition 1 [ Offset 0, 1, 2... ] ---> Consumer 2 (Group A)
```

- **Topic**: A logical stream category to which messages are published.
- **Partition**: Topics are divided into parallel partitions across brokers for scalability and ordering guarantees within a single partition.
- **Offset**: Unique sequential ID assigned to each message inside a partition.
- **Consumer Group**: Group of consumers sharing the workload of reading a topic. Each partition is assigned to exactly one consumer in a group.

### Message Delivery Guarantees
1. **At-most-once**: Messages may be lost, but never re-delivered.
2. **At-least-once**: Messages are never lost, but may be duplicated (requires consumer idempotency!).
3. **Exactly-once (EOS)**: Messages are delivered and processed exactly once end-to-end (uses Kafka transactional producer & consumer API).

---

## 19. Jenkins & CI/CD Pipeline for Backend

### What is Jenkins?
Jenkins is an open-source automation CI/CD server that automates the building, testing, code quality scanning, containerization, and deployment stages of software development.

### Complete CI/CD Pipeline Stages (`Jenkinsfile`)

```
+-----------+    +-----------+    +---------------+    +--------------+    +------------+
| Checkout  | -> | Build &   | -> | SonarQube     | -> | Docker Build | -> | K8s        |
| Source    |    | Unit Test |    | Code Analysis |    | & Push       |    | Deployment |
+-----------+    +-----------+    +---------------+    +--------------+    +------------+
```

```groovy
pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'shohan/payment-service:latest'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/org/repo.git'
            }
        }

        stage('Build & Test') {
            steps {
                sh './mvnw clean test'
            }
        }

        stage('SonarQube Quality Gate') {
            steps {
                script {
                    sh './mvnw sonar:sonar'
                }
            }
        }

        stage('Docker Build & Push') {
            steps {
                sh 'docker build -t $DOCKER_IMAGE .'
                sh 'docker push $DOCKER_IMAGE'
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                sh 'kubectl apply -f k8s/deployment.yaml'
            }
        }
    }
}
```

---

## 20. Database Indexing & Query Optimization

### How Database Indexing Works (B-Tree Index)
A Database Index is a data structure (typically a **Self-Balancing B-Tree**) that speeds up data retrieval on a table at the cost of slower writes (`INSERT`, `UPDATE`, `DELETE`) and extra disk space.

```
                    [ Root: 50 ]
                   /            \
          [ Node: 20 ]        [ Node: 80 ]
         /           \       /           \
     [10, 15]     [30, 40] [60, 70]     [90, 100]  <-- Leaf Nodes (Points to Row Disk Location)
```

### Essential Query Optimization Checklist ⚡
1. **Avoid `SELECT *`**: Fetch only necessary columns to reduce I/O and memory overhead.
2. **Indexing High-Cardinality Columns**: Index columns frequently used in `WHERE`, `JOIN`, and `ORDER BY` clauses.
3. **Composite Index Order Matters (Leftmost Prefix Rule)**:
   - Index on `(country, city)` speeds up `WHERE country = 'BD'` and `WHERE country = 'BD' AND city = 'Dhaka'`, but **NOT** `WHERE city = 'Dhaka'` alone!
4. **Avoid Functions on Indexed Columns**:
   ```sql
   -- BAD (Disables Index! Full Table Scan):
   SELECT * FROM Users WHERE UPPER(name) = 'SHOHAN';

   -- GOOD (Uses Index):
   SELECT * FROM Users WHERE name = 'Shohan';
   ```
5. **Fix N+1 Query Problem in JPA/Hibernate**: Use `JOIN FETCH` or `@EntityGraph` instead of `FetchType.EAGER` default loops.

---
