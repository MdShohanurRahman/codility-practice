# Hong Leong Bank --- Senior Java & Distributed Systems Developer

## Interview Preparation Handbook --- Shohanur Rahman

> **Role:** Senior Java & Distributed Systems Developer / Senior Backend
> Engineer\
> **Focus:** Java 21+, Spring Boot 3.x, Microservices, Kafka/KRaft/MM2,
> Resilience4j, Security, SQL/Performance, Testing, Cloud Native,
> AI-assisted engineering, Production Excellence and Senior-level
> leadership.

------------------------------------------------------------------------

# 0. How to Use This Handbook

This handbook is tailored from two sources:

1.  The **Hong Leong Bank Job Description** supplied for this interview.
2.  **Shohanur Rahman's current resume**, including Liberty General
    Insurance, TM R&D, Bitmascot and personal projects.

The JD explicitly emphasizes: - Java 21+ and Virtual Threads - Spring
Boot 3.x, WebClient, MapStruct, Resilience4j - Apache Kafka in KRaft
mode - MirrorMaker 2 for cross-datacenter replication / DR - Quarkus and
GraalVM Native Image - AI-assisted engineering - OWASP/security,
encryption and risk controls - SQL tuning, ORM optimization and
high-throughput performance - JUnit 5, Mockito, Testcontainers - Docker
and Kubernetes - Production RCA, stress testing and zero-downtime
deployment - Mentoring, code review, technical prioritization and
security/compliance review

Your resume gives strong real-world stories around: - Spring Boot
microservices - Legacy modernization - Jenkins/Docker - Microsoft Entra
ID with OAuth2/OIDC - WhatsApp notification microservice with
asynchronous messaging - Oracle SQL/indexing/connection-pool
optimization - RabbitMQ/WebSockets - Payment gateways and webhook
reconciliation - Java 25 / Spring Boot 4 personal projects - Spring AI,
RAG and MCP

**Interview rule:** Do not pretend you have production experience with a
technology if you do not. For a weaker area, say what you know, explain
the architecture correctly, and connect it to adjacent experience.

------------------------------------------------------------------------

# 1. Your 90-Second Introduction

## Q1. Tell me about yourself.

### Recommended answer

> I'm a Senior Software Engineer with over six years of experience
> building enterprise applications and distributed backend systems,
> mainly with Java and Spring Boot.
>
> In my current role at Liberty General Insurance, I work on Spring Boot
> microservices, application modernization, regulatory e-invoicing,
> secure identity integration with Microsoft Entra ID using OAuth2/OIDC,
> CI/CD with Jenkins and Docker, and high-reliability notification
> services.
>
> One project I worked on was a high-throughput WhatsApp notification
> microservice using asynchronous messaging, where reliability and
> failure handling were important. I have also worked on SQL and
> connection-pool optimization, reducing API latency, and on legacy
> modernization.
>
> Before Liberty, I worked with Oracle, Dropwizard, REST APIs and
> Firebase at TM R&D, and at Bitmascot I worked on payment gateways,
> RabbitMQ, WebSockets and real-time transaction synchronization.
>
> Alongside my professional work, I build modern Java projects using
> Java 25, Spring Boot 4, PostgreSQL, Redis, WebSockets, Spring AI, RAG
> and MCP.
>
> My main strengths are backend architecture, distributed systems, API
> design, performance, security and building reliable production
> systems. I'm now looking for a role where I can work more deeply on
> large-scale distributed systems and contribute at a senior engineering
> level.

### Follow-up questions

-   What is your current architecture?
-   What exactly did you design yourself?
-   What was the hardest production problem?
-   Why microservices?
-   Why are you changing jobs?
-   How do you measure reliability?
-   What would you improve in your current system?

------------------------------------------------------------------------

# 2. Resume-Based Questions

## Q2. Tell me about your current project.

Structure the answer:

``` text
Business problem
      ↓
Architecture
      ↓
Your responsibility
      ↓
Technical decisions
      ↓
Challenges
      ↓
Result / metrics
```

For Liberty, mention only technologies and responsibilities you can
confidently explain.

## Q3. You mention legacy modernization. Why modernize a monolith?

### Answer

> Modernization can improve independent deployability, scalability,
> maintainability and team ownership. But I would not split a monolith
> simply because microservices are popular. I would identify business
> boundaries, deployment bottlenecks, scaling requirements and areas
> with high change frequency, then incrementally extract services.

### Follow-up: Why not rewrite everything?

> A big-bang rewrite increases delivery and migration risk. I prefer
> incremental modernization, validating each extracted capability while
> keeping the existing system operational.

------------------------------------------------------------------------

# 3. Core Java --- High Priority

## Q4. What is the difference between a process and a thread?

A process has its own address space and resources. Threads are execution
units within a process and generally share process memory.

## Q5. What is the difference between platform threads and virtual threads?

Platform threads are closely associated with OS threads and are
relatively expensive. Virtual threads are lightweight threads managed by
the JVM and are designed to support very high concurrency, especially
for I/O-bound workloads.

## Q6. Do virtual threads make CPU-bound workloads faster?

No. Virtual threads primarily improve concurrency for workloads that
spend significant time waiting, such as network or database I/O.
CPU-bound work is still constrained by CPU capacity.

## Q7. What is `volatile`?

`volatile` provides visibility guarantees for a variable between threads
and prevents certain compiler/CPU reordering effects. It does not make
compound operations such as `count++` atomic.

## Q8. `synchronized` vs `Lock`?

`synchronized` provides intrinsic locking with simpler syntax and
automatic release. `Lock` implementations such as `ReentrantLock`
provide more control, such as timed lock acquisition, interruptible
locking and multiple condition objects.

## Q9. `HashMap` vs `ConcurrentHashMap`?

`HashMap` is not designed for concurrent mutation. `ConcurrentHashMap`
provides thread-safe concurrent access with much better scalability than
synchronizing an entire map.

## Q10. What is immutability?

An immutable object cannot change its state after creation. Immutable
objects simplify concurrency, reduce accidental state changes and are
easier to reason about.

## Q11. What is `CompletableFuture`?

It represents an asynchronous computation and allows composition of
dependent or independent asynchronous operations.

Example:

``` java
CompletableFuture<User> user = getUser();
CompletableFuture<Account> account = getAccount();

return user.thenCombine(account, this::buildResponse);
```

## Q12. What is a race condition?

A race condition occurs when the correctness of a program depends on the
timing/interleaving of concurrent operations.

## Q13. What is deadlock?

A deadlock occurs when threads wait indefinitely for resources held by
each other.

Typical prevention: - consistent lock ordering - short critical
sections - timeouts - avoiding unnecessary locks

------------------------------------------------------------------------

# 4. Java 21+ and Virtual Threads --- VERY HIGH PRIORITY

## Q14. What are Virtual Threads?

### Interview answer

> Virtual threads are lightweight threads managed by the JVM rather than
> being directly tied one-to-one to OS threads. They allow applications
> to support very large numbers of concurrent tasks while keeping a
> familiar thread-per-request programming model.

## Q15. Why are virtual threads useful for backend services?

For I/O-heavy applications, many requests may spend time waiting for
databases, HTTP calls or other services. Virtual threads allow those
waiting tasks to coexist with much lower thread overhead.

## Q16. Should we replace every executor with virtual threads?

No.

First identify the workload. Virtual threads are particularly useful for
high-concurrency I/O-bound work. They are not a replacement for CPU
parallelism.

## Q17. Virtual Threads vs Reactive Programming?

A strong answer:

> They solve concurrency differently. Reactive programming uses
> non-blocking asynchronous pipelines and is useful when an application
> and its dependencies are designed around non-blocking I/O. Virtual
> threads allow a simpler synchronous-looking programming model while
> still supporting high concurrency for suitable workloads.

## Q18. If a virtual-thread application is slow, what would you investigate?

-   Database connection pool size
-   Downstream service limits
-   Network latency
-   CPU saturation
-   Memory
-   Lock contention
-   Thread pinning/blocking behavior
-   External API timeouts
-   Query performance

**Important:** Increasing the number of threads does not remove
downstream bottlenecks.

------------------------------------------------------------------------

# 5. Spring Boot

## Q19. Why Spring Boot?

Spring Boot simplifies configuration, dependency management, application
startup, embedded servers, production configuration and integration with
the Spring ecosystem.

## Q20. Constructor injection vs field injection?

Prefer constructor injection because dependencies are explicit, fields
can be final, objects are easier to test, and the class cannot be
created without required dependencies.

## Q21. `@Component`, `@Service`, `@Repository`?

They are stereotype annotations used for Spring-managed components.
`@Service` expresses service-layer intent and `@Repository` represents
persistence-layer components and participates in Spring's persistence
exception translation.

## Q22. How does `@Transactional` work?

Spring commonly applies transaction behavior through
proxies/interceptors around the target method. The transaction is
started before the method and committed or rolled back according to
transaction rules.

### Follow-up trap

Why might self-invocation cause transaction behavior not to apply?

Because a direct call within the same object may bypass the Spring
proxy.

## Q23. What is transaction propagation?

It defines how a method participates in an existing transaction or
creates a new one.

Important modes: - REQUIRED - REQUIRES_NEW - SUPPORTS - MANDATORY -
NOT_SUPPORTED - NEVER - NESTED

## Q24. What are isolation levels?

They control how one transaction can observe changes made by other
transactions.

Common levels: - READ UNCOMMITTED - READ COMMITTED - REPEATABLE READ -
SERIALIZABLE

------------------------------------------------------------------------

# 6. REST API Design

## Q25. How do you design a production-ready REST API?

Consider:

``` text
Authentication
Validation
Authorization
Controller
DTO
Service
Repository
Database
```

Plus: - consistent error responses - pagination - idempotency where
needed - timeout - rate limiting - logging - correlation IDs - metrics -
tracing - API versioning - OpenAPI documentation

## Q26. PUT vs PATCH?

PUT generally represents replacement/update of a resource
representation, while PATCH represents partial modification.

## Q27. What is idempotency?

An operation is idempotent if repeating the same request produces the
same intended final state.

This is especially important for payment and financial APIs.

Example:

``` text
POST /payments
Idempotency-Key: abc-123
```

The server stores the key and result so a retry does not create a second
payment.

------------------------------------------------------------------------

# 7. Spring WebClient

## Q28. RestTemplate vs WebClient?

RestTemplate is synchronous/blocking. WebClient supports reactive,
non-blocking HTTP communication and is suitable when the application
benefits from non-blocking I/O.

## Q29. Does using WebClient automatically make an application non-blocking?

No.

If the flow eventually calls:

``` java
.block()
```

the application can become blocking at that point.

## Q30. How would you call multiple downstream services efficiently?

If calls are independent, compose them concurrently rather than
sequentially.

Conceptually:

``` text
          ┌── Service A
Request ──┼── Service B
          └── Service C
              ↓
           Combine
```

Also apply timeouts, retries only where appropriate, circuit breakers
and bulkheads.

------------------------------------------------------------------------

# 8. Microservices Architecture --- VERY HIGH PRIORITY

## Q31. Why microservices?

Benefits: - independent deployment - independent scaling - clearer
ownership - fault isolation - technology flexibility

Costs: - distributed transactions - network failures - observability
complexity - deployment complexity - data consistency challenges

## Q32. When should you NOT use microservices?

If the system is small, the team is small, the domain boundaries are
unclear and independent scaling/deployment is not required, a modular
monolith can be a better choice.

## Q33. REST vs asynchronous messaging?

REST is useful for request/response interactions where the caller needs
an immediate response.

Messaging is useful for asynchronous workflows, decoupling, buffering,
event-driven processing and high-throughput pipelines.

## Q34. What is eventual consistency?

In distributed systems, different services may temporarily have
different views of data, but they converge toward a consistent state.

## Q35. What is the Saga pattern?

Saga manages a distributed business transaction as a sequence of local
transactions. If a later step fails, compensating actions can undo the
business effect of earlier steps.

Two common approaches: - choreography - orchestration

## Q36. What is the Outbox Pattern?

Write the business data and an outgoing event record in the same local
database transaction.

``` text
Application
    |
    +---- Business Table
    |
    +---- Outbox Table
             |
             ↓
       Event Publisher
             |
             ↓
           Kafka
```

This reduces the risk of updating the database successfully while
failing to publish the event.

## Q37. How do you make event processing idempotent?

Use a unique event/message ID or business idempotency key and persist
processed state or enforce uniqueness at the database level.

------------------------------------------------------------------------

# 9. Kafka --- CRITICAL PREPARATION

## Q38. What is Kafka?

Apache Kafka is a distributed event streaming platform designed for
high-throughput, durable event storage and scalable event processing.

## Q39. What is a Kafka topic?

A logical stream of records. Topics are divided into partitions for
scalability.

## Q40. What is a partition?

A partition is an ordered append-only log within a topic. Partitions
allow parallelism across consumers.

## Q41. How is ordering maintained?

Kafka guarantees ordering within a partition, not globally across all
partitions.

If ordering by customer/account is required, use a consistent key so
related events go to the same partition.

## Q42. What is a consumer group?

A consumer group is a set of consumers cooperating to process topic
partitions. Within a group, a partition is normally assigned to one
consumer at a time.

## Q43. What is an offset?

An offset identifies a record's position within a partition.

## Q44. Auto commit vs manual commit?

Auto commit is simpler but gives less precise control. Manual
acknowledgment/commit lets the application coordinate offset progress
with processing semantics.

## Q45. At-most-once vs at-least-once?

At-most-once: a record may be lost but should not be processed more than
once.

At-least-once: records are retried until acknowledged, so duplicates are
possible.

Exactly-once is more complex and depends on the complete processing
architecture, not simply one Kafka setting.

## Q46. What happens if a consumer crashes?

The consumer group can rebalance and another consumer may take ownership
of its partitions. Records that were not safely committed may be
processed again.

Therefore consumers should be designed to tolerate duplicates when using
at-least-once delivery.

## Q47. What is consumer lag?

The difference between the latest available offset and the consumer's
processed/committed position.

High lag can indicate: - slow consumers - insufficient consumer
parallelism - downstream bottlenecks - large traffic spikes - processing
failures

------------------------------------------------------------------------

# 10. Kafka KRaft --- CRITICAL

## Q48. What is KRaft?

KRaft is Kafka's metadata management architecture that removes the
dependency on ZooKeeper and uses Kafka's own controller quorum.

## Q49. Why did Kafka move away from ZooKeeper?

The KRaft architecture simplifies Kafka's operational architecture by
bringing metadata management into Kafka itself and removing the separate
ZooKeeper dependency.

## Q50. What does the controller do?

Controllers manage cluster metadata and coordination responsibilities
such as partition leadership and cluster state.

## Q51. How would you design a high-throughput Kafka pipeline?

``` text
Producers
   |
   ↓
 Kafka Topic
 ┌──────┬──────┬──────┐
 P0     P1     P2 ... Pn
 └──────┴──────┴──────┘
   |
Consumer Groups
   |
   ↓
Processing Services
   |
   ↓
DB / External Systems
```

Tune: - partition count - producer batching - compression - consumer
parallelism - fetch settings - processing time - database throughput -
downstream limits

Do not blindly increase partitions or threads without measuring the
bottleneck.

------------------------------------------------------------------------

# 11. MirrorMaker 2 --- CRITICAL

## Q52. What is MirrorMaker 2?

MirrorMaker 2 is Kafka's mechanism for replicating topics and related
metadata between Kafka clusters.

## Q53. Why would a bank use MM2?

Possible reasons: - cross-datacenter replication - disaster recovery -
migration - geographic resilience - multi-cluster architectures

## Q54. How would you design cross-DC Kafka DR?

``` text
          DC-A
      Kafka Cluster A
            |
            | MM2
            ↓
      Kafka Cluster B
          DC-B
```

Consider: - replication direction - topic selection - replication lag -
offset synchronization - failover - failback - duplicate processing -
consumer restart behavior - network partition - RPO/RTO - monitoring

## Q55. What are RPO and RTO?

RPO = how much data loss is acceptable.

RTO = how quickly the system must be restored.

For a banking system, these requirements should be explicitly defined
before choosing the DR architecture.

------------------------------------------------------------------------

# 12. Kafka Scenario Questions

## Q56. A consumer is processing too slowly. What do you do?

First measure:

``` text
Consumer lag
↓
Processing latency
↓
CPU / memory
↓
DB latency
↓
External API latency
```

Then consider: - increase consumer parallelism - add partitions if
necessary - batch processing - optimize DB queries - reduce downstream
latency - use asynchronous processing - tune polling/fetch settings

## Q57. Kafka has duplicate messages. Is Kafka broken?

Not necessarily. At-least-once processing naturally permits duplicates.
Build consumers to be idempotent.

## Q58. How would you prevent duplicate payment processing?

Use: - idempotency key - unique business transaction ID - database
uniqueness constraint - transactional state changes - safe retry
semantics - reconciliation

------------------------------------------------------------------------

# 13. RabbitMQ vs Kafka

## Q59. Kafka vs RabbitMQ?

### Kafka

Best suited to: - high-throughput event streaming - durable event logs -
replay - partition-based scaling - multiple independent consumers

### RabbitMQ

Strong for: - traditional message brokering - routing patterns -
queues - work distribution - request/task messaging

### Personalized answer

> I have hands-on experience with RabbitMQ for event-driven transaction
> synchronization. For a new system I would choose based on the business
> requirement rather than technology preference. If we need a
> high-throughput event stream, replayability and multiple independent
> consumers, Kafka would be a strong choice. For flexible routing and
> traditional task/message workflows, RabbitMQ can be a better fit.

------------------------------------------------------------------------

# 14. Resilience4j --- VERY HIGH PRIORITY

## Q60. What is a circuit breaker?

It prevents repeated calls to a failing downstream dependency.

States:

``` text
CLOSED
   |
 failures
   ↓
 OPEN
   |
 wait
   ↓
HALF_OPEN
   |
 test successful → CLOSED
 test failed     → OPEN
```

## Q61. Why not retry everything?

Retries can amplify traffic against an already failing dependency.

Use retries only for transient failures and combine them with: -
exponential backoff - jitter - timeout - circuit breaker - bounded
attempts

## Q62. What is a bulkhead?

Bulkhead isolation prevents one dependency or workload from consuming
all resources and affecting unrelated operations.

## Q63. What is rate limiting?

It restricts how many requests can be processed within a defined period.

Useful for: - protecting services - controlling expensive operations -
enforcing downstream limits - preventing abuse

## Q64. Circuit breaker + retry: which comes first?

There is no universal answer; it depends on the architecture. The
important point is to avoid retry storms and make the combination
bounded and observable.

## Q65. Design a resilient external API call.

``` text
Request
  ↓
Timeout
  ↓
Circuit Breaker
  ↓
Rate Limiter / Bulkhead
  ↓
Retry with Backoff
  ↓
External API
```

The exact ordering should be chosen based on the desired failure
semantics and measured behavior.

------------------------------------------------------------------------

# 15. Database & SQL Performance

## Q66. A production API becomes slow. How do you investigate?

Do not immediately add indexes.

Use:

``` text
Metrics
 ↓
API latency
 ↓
Distributed trace
 ↓
DB latency
 ↓
Slow query
 ↓
Execution plan
 ↓
Index / SQL / ORM analysis
 ↓
Load test
```

Also check: - connection pool exhaustion - CPU - memory -
locks/deadlocks - network latency - downstream calls

## Q67. What is an index?

An index is a data structure that helps the database locate rows more
efficiently, reducing unnecessary scanning for suitable queries.

## Q68. What is a composite index?

An index containing multiple columns.

Column order matters because query patterns determine how effectively
the database can use the index.

## Q69. Why can too many indexes be harmful?

Indexes consume storage and increase the cost of INSERT/UPDATE/DELETE
operations because indexes must also be maintained.

## Q70. What is the N+1 problem?

One query loads a set of parent records and then additional queries are
executed for each related record.

Possible solutions: - fetch join - entity graphs - batch fetching -
projection - carefully designed queries

## Q71. Lazy vs eager loading?

Lazy loading retrieves associated data when accessed. Eager loading
retrieves it earlier. Neither is universally better; choose based on
access patterns and query behavior.

## Q72. What is connection pooling?

A pool maintains reusable DB connections so requests do not repeatedly
establish new connections.

Important parameters include: - maximum pool size - minimum idle -
connection timeout - idle timeout - max lifetime

Do not make the pool arbitrarily large; the database itself has finite
capacity.

## Q73. Tell me about your SQL optimization experience.

### Recommended answer

> At TM R&D, I worked on Oracle SQL optimization, indexing and
> connection-pool tuning. The goal was to reduce API latency, and the
> changes reduced latency by around 35%. My approach was to identify the
> slow part using measurements, inspect query behavior and execution
> plans, optimize queries/indexes, review connection-pool behavior and
> then validate the improvement.

------------------------------------------------------------------------

# 16. Hibernate/JPA

## Q74. What is ORM?

Object-Relational Mapping maps application objects to relational
database structures.

## Q75. First-level cache vs second-level cache?

First-level cache is associated with the persistence context/entity
manager and is typically scoped to a session/persistence context.

Second-level cache is shared across sessions and requires explicit
configuration/provider support.

## Q76. Optimistic vs pessimistic locking?

Optimistic locking assumes conflicts are relatively rare and detects
them using versioning.

Pessimistic locking acquires database locks to prevent conflicting
operations while a transaction is working.

------------------------------------------------------------------------

# 17. Redis / Caching

## Q77. Why use Redis?

Typical use cases: - caching - distributed state - rate limiting -
session-like data - counters - temporary data

## Q78. What is cache-aside?

``` text
Read
 ↓
Cache?
 ├─ yes → return
 └─ no
      ↓
    DB
      ↓
  Put cache
      ↓
   return
```

## Q79. What happens when cached data becomes stale?

Choose an invalidation/expiration strategy appropriate to business
requirements.

Common approaches: - TTL - explicit invalidation - write-through -
event-based invalidation

For financial data, do not blindly cache data where stale information
can create incorrect business decisions.

------------------------------------------------------------------------

# 18. Security --- VERY HIGH PRIORITY

## Q80. Authentication vs authorization?

Authentication answers **who are you?**

Authorization answers **what are you allowed to do?**

## Q81. What is OAuth2?

OAuth2 is an authorization framework for delegated access to protected
resources.

## Q82. What is OIDC?

OpenID Connect builds an identity/authentication layer on top of OAuth2.

## Q83. What is JWT?

A JWT is a compact token format commonly used to carry claims between
parties.

Conceptually:

``` text
Header.Payload.Signature
```

## Q84. Access token vs refresh token?

Access tokens are used to access protected resources and are generally
shorter-lived. Refresh tokens can be used to obtain new access tokens
and should be protected carefully.

## Q85. What is RBAC?

Role-Based Access Control assigns permissions through roles.

Example:

``` text
ADMIN
  ├── manage users
  ├── manage configuration

OPERATOR
  ├── view transactions
  └── process operations
```

## Q86. Explain your Entra ID experience.

### Recommended answer

> In the Liberty Portal, I worked with Microsoft Entra ID for secure SSO
> using OAuth2/OIDC. The application validates the authenticated
> identity and applies role-based access control so users can access
> only the functionality they are authorized for.

## Q87. How would you secure a banking REST API?

Consider:

``` text
TLS
 ↓
OAuth2/OIDC
 ↓
Token validation
 ↓
Authorization / RBAC
 ↓
Input validation
 ↓
Business authorization
 ↓
Audit logging
 ↓
Encryption / secret management
 ↓
Monitoring
```

Also consider OWASP controls, secure dependency management, least
privilege and security testing.

------------------------------------------------------------------------

# 19. OWASP / Secure Coding

## Q88. How do you prevent SQL injection?

Use parameterized queries/prepared statements and avoid constructing SQL
from untrusted strings.

## Q89. How do you prevent broken authorization?

Enforce authorization server-side for every protected operation. Never
rely only on UI restrictions.

## Q90. How should secrets be handled?

Do not hard-code secrets or commit them to source control. Use
appropriate secret-management mechanisms and limit access.

## Q91. What is least privilege?

Give users/services only the permissions required to perform their
tasks.

------------------------------------------------------------------------

# 20. Testing --- JUnit 5 / Mockito / Testcontainers

## Q92. Unit test vs integration test?

Unit tests isolate a small unit of behavior and are fast.

Integration tests verify interaction with real or realistic
infrastructure/components.

## Q93. Why Mockito?

To isolate a unit under test from dependencies and control dependency
behavior.

## Q94. Mock vs Spy?

A mock is a test double whose behavior is controlled by the test. A spy
wraps a real object and allows selective stubbing/verification.

## Q95. What is Testcontainers?

Testcontainers runs disposable real infrastructure components in
containers for integration tests.

Examples: - PostgreSQL - Kafka - Redis

## Q96. Why not always use H2 instead of PostgreSQL?

Because H2 may behave differently from the production database in SQL
syntax, transaction behavior, indexing and other database-specific
behavior.

## Q97. How would you test a Kafka consumer?

Test: - successful processing - invalid message - retry - duplicate
message - offset behavior - downstream failure - DLQ behavior

Use realistic Kafka infrastructure where practical.

------------------------------------------------------------------------

# 21. Quarkus + GraalVM

## Q98. Why Quarkus?

Quarkus is designed for cloud-native Java workloads and emphasizes fast
startup and efficient resource usage.

## Q99. What is GraalVM Native Image?

It compiles an application ahead-of-time into a native executable.

Potential benefits: - fast startup - lower memory footprint - attractive
for serverless/short-lived workloads

## Q100. Why does the JD mention serverless?

Serverless and scale-to-zero environments can be sensitive to startup
time and memory consumption. Native executables can reduce startup
overhead.

## Q101. JVM vs Native Image trade-off?

Native image can provide excellent startup/resource characteristics, but
build complexity, compatibility, reflection/configuration requirements
and runtime characteristics need evaluation.

------------------------------------------------------------------------

# 22. Docker & Kubernetes

## Q102. Image vs container?

An image is a packaged immutable artifact/template. A container is a
running instance of an image.

## Q103. What is a Kubernetes Pod?

The smallest deployable unit in Kubernetes, containing one or more
closely related containers sharing networking/storage context.

## Q104. Liveness vs readiness probe?

Liveness indicates whether the container should be restarted.

Readiness indicates whether the application is ready to receive traffic.

## Q105. What is horizontal scaling?

Increasing the number of application instances/pods rather than only
increasing resources on one instance.

## Q106. How do you achieve zero-downtime deployment?

Use mechanisms such as: - readiness probes - rolling deployments -
multiple replicas - graceful shutdown - backward-compatible API/schema
changes - health checks - careful migration strategy

------------------------------------------------------------------------

# 23. CI/CD & Production Deployment

## Q107. Describe a CI/CD pipeline.

Typical flow:

``` text
Git Push
  ↓
Build
  ↓
Unit Tests
  ↓
Static Analysis
  ↓
Integration Tests
  ↓
Security Checks
  ↓
Docker Build
  ↓
Artifact/Image Registry
  ↓
Deploy
  ↓
Smoke Tests
  ↓
Monitoring
```

Your resume specifically mentions Jenkins and Docker, so be ready to
explain what you personally configured and maintained.

## Q108. What makes a deployment safe?

-   automated testing
-   health checks
-   backward compatibility
-   observability
-   rollback plan
-   database migration strategy
-   gradual rollout where appropriate

------------------------------------------------------------------------

# 24. Production RCA

## Q109. Production API latency suddenly increases. What do you do?

### Strong senior-level answer

> First I would confirm the impact and scope using monitoring. I would
> identify whether the issue is isolated to one endpoint/service or is
> system-wide. Then I would use distributed tracing and metrics to
> locate the latency source.
>
> I would check recent deployments/configuration changes, database
> latency, connection-pool utilization, downstream dependencies,
> CPU/memory and traffic volume.
>
> If customer impact is high, I would apply a safe short-term mitigation
> such as rollback, traffic reduction or disabling a problematic feature
> where appropriate. In parallel, I would identify the root cause and
> then implement a long-term fix with regression tests and monitoring.

## Q110. Short-term mitigation vs long-term resolution?

Short-term mitigation restores service safely.

Long-term resolution removes or reduces the underlying cause and adds
safeguards to prevent recurrence.

------------------------------------------------------------------------

# 25. Performance Engineering

## Q111. How do you benchmark a system?

Define: - workload - concurrency - request mix - data volume -
environment - success criteria

Measure: - throughput - p50 latency - p95 latency - p99 latency - error
rate - CPU - memory - DB utilization - queue/consumer lag

Do not rely only on average latency.

## Q112. What is p99 latency?

The latency below which 99% of requests complete. It helps reveal tail
latency.

------------------------------------------------------------------------

# 26. System Design --- Banking Notification System

## Q113. Design a high-throughput notification system.

### Requirements

-   receive notification requests
-   asynchronous processing
-   reliable delivery
-   retry failures
-   avoid duplicates
-   monitor status
-   support provider failure

### Architecture

``` text
                    ┌──────────────┐
Client ────────────→│ Notification │
                    │     API      │
                    └──────┬───────┘
                           │
                           ↓
                     Transaction DB
                           │
                      Outbox Event
                           │
                           ↓
                        Kafka
                           │
              ┌────────────┴────────────┐
              ↓                         ↓
       Notification Worker       Audit/Analytics
              │
              ↓
       Resilience Controls
              │
       ┌──────┼───────┐
       ↓      ↓       ↓
    Retry  Circuit  Rate Limit
              │
              ↓
        External Provider
```

### Senior-level points

-   idempotency
-   outbox
-   at-least-once processing
-   deduplication
-   retry with backoff
-   DLQ
-   provider rate limits
-   circuit breaker
-   observability
-   audit trail
-   data retention
-   security

This is a good story to connect to your Liberty notification experience.

------------------------------------------------------------------------

# 27. System Design --- Payment Processing

## Q114. How would you design a reliable payment API?

``` text
Client
  ↓
API Gateway
  ↓
Payment Service
  ↓
Idempotency Store
  ↓
Payment Provider
  ↓
Webhook
  ↓
Reconciliation
```

Critical concepts: - idempotency key - payment state machine - webhook
verification - retries - reconciliation - audit logs - timeout - circuit
breaker - secure secrets - duplicate event handling

Your Bitmascot payment-gateway and webhook-reconciliation experience is
highly relevant here.

------------------------------------------------------------------------

# 28. System Design --- Multi-DC Kafka

## Q115. Design an active-active event streaming platform.

Discuss:

``` text
             Global Traffic
                 |
        ┌────────┴────────┐
        ↓                 ↓
      DC-A              DC-B
   Kafka A             Kafka B
        ↑                 ↑
        └────── MM2 ──────┘
```

You must discuss: - which topics replicate - how offsets are handled -
how consumers fail over - duplicate events - conflict handling -
RPO/RTO - network partitions - monitoring - failback

**Do not claim hands-on MM2 production experience unless you have it.**
Explain the design and say you are prepared to work with it.

------------------------------------------------------------------------

# 29. AI-Driven Engineering

## Q116. How do you use AI in software development?

### Recommended answer

> I use AI as an engineering accelerator, not as an authority. I use it
> for boilerplate generation, refactoring ideas, test generation,
> documentation, debugging hypotheses and exploring implementation
> approaches.
>
> But generated code still goes through compilation, automated tests,
> static analysis, security checks and peer review. For sensitive
> enterprise or banking code, I also need to consider data privacy,
> dependency risk and organizational policies.

## Q117. Can AI replace code review?

No. AI can assist review, but human engineers remain responsible for
correctness, architecture, security, business rules and risk.

## Q118. How can AI generate useful tests?

Provide the behavior and edge cases, then review generated tests for: -
meaningful assertions - boundary cases - negative cases -
concurrency/failure cases - false positives - maintainability

------------------------------------------------------------------------

# 30. Senior / Leadership Questions

## Q119. How do you conduct code review?

I focus on: 1. correctness 2. security 3. maintainability 4. performance
5. test coverage 6. observability 7. consistency with architecture 8.
operational impact

I try to explain the reason behind important feedback rather than simply
requesting changes.

## Q120. How do you mentor engineers?

Start with context and ownership, then guide through design discussions,
code reviews and debugging rather than solving every problem for them.

## Q121. How do you handle disagreement with another senior engineer?

Clarify the requirement, list trade-offs, use evidence/benchmarks where
possible, and align on the decision based on business and technical
goals rather than personal preference.

## Q122. How do you prioritize technical debt?

Prioritize debt based on: - production risk - security/compliance
impact - developer productivity - operational cost - frequency of
change - business impact

------------------------------------------------------------------------

# 31. Behavioral Questions

## Q123. Why are you looking for a new opportunity?

### Safe answer

> I'm looking for a role where I can take on larger distributed-system
> challenges, work more deeply with modern Java and event-driven
> architecture, and contribute at a senior engineering level. This role
> is particularly interesting because it combines microservices,
> high-throughput streaming, resilience, modernization and engineering
> excellence.

Avoid criticizing your current employer.

## Q124. Why banking?

> Banking systems require reliability, security, auditability and
> correctness, which are areas I enjoy working on. My current insurance
> experience has already exposed me to regulated enterprise systems,
> compliance-driven development and secure integrations, so banking is a
> natural next step.

## Q125. What is your biggest technical strength?

> Designing reliable backend systems and understanding the trade-offs
> between performance, consistency, resilience and maintainability.

## Q126. What is an area you are improving?

> I'm continuously deepening my knowledge of large-scale Kafka
> operations and newer cloud-native Java technologies such as Quarkus
> and GraalVM. I already have strong foundations in Spring Boot,
> microservices, messaging and distributed systems, so I focus on
> transferring those concepts into these technologies.

This is much better than saying "I don't know Kafka."

------------------------------------------------------------------------

# 32. Interviewer Follow-Up Traps

## Trap 1

**"Why microservices?"**

Bad: \> Because microservices are scalable.

Better: \> Scalability is one benefit, but I would choose microservices
when independent deployment, scaling, ownership and domain boundaries
justify the distributed-system complexity.

## Trap 2

**"Kafka guarantees exactly once, right?"**

Don't oversimplify.

Better: \> Kafka provides mechanisms that support exactly-once
processing semantics in appropriate architectures, but end-to-end
exactly-once behavior depends on the whole processing pipeline.

## Trap 3

**"More DB connections means more performance?"**

No. Too many connections can overload the database and increase
contention.

## Trap 4

**"WebClient means non-blocking?"**

Not necessarily. Calling `.block()` makes the flow blocking at that
point.

## Trap 5

**"Virtual threads solve scalability?"**

They improve concurrency for suitable workloads, but database, CPU,
network and downstream capacity remain bottlenecks.

## Trap 6

**"Retry improves reliability?"**

Only when the failure is transient and retries are bounded. Otherwise
retry can make an outage worse.

------------------------------------------------------------------------

# 33. Questions You Should Ask the Interviewer

At the end, ask 2--3 strong questions.

### Option 1

> What are the main distributed-system challenges the team is currently
> trying to solve?

### Option 2

> How is Kafka used today, and what are the main requirements around
> multi-datacenter replication and disaster recovery?

### Option 3

> How much of the platform is currently being modernized from legacy
> systems?

### Option 4

> What would success look like for this role in the first six months?

### Option 5

> How does the team approach architecture decisions, code reviews and
> engineering standards?

------------------------------------------------------------------------

# 34. Your Personal Story Bank

Prepare these stories in STAR format.

## Story A --- Liberty e-Invoicing

**Situation:** Regulatory e-invoicing requirement\
**Task:** Build integration capability\
**Action:** Spring Boot microservices + integration flow + compliance
considerations\
**Result:** Automated invoice processing / compliance

## Story B --- Legacy Modernization

**Situation:** Legacy monolithic services\
**Task:** Improve maintainability/deployment\
**Action:** Modular microservices + Jenkins/Docker\
**Result:** Deployment cycle reduced by around 40%

## Story C --- WhatsApp Notification

**Situation:** Automated transaction notifications\
**Task:** Reliable asynchronous delivery\
**Action:** Messaging + failure handling + service design\
**Result:** Around 99.9% reliability

## Story D --- Oracle Optimization

**Situation:** High API latency\
**Task:** Improve performance\
**Action:** SQL/index/connection-pool optimization\
**Result:** Around 35% latency reduction

## Story E --- Entra ID

**Situation:** Enterprise portal security\
**Task:** Secure SSO\
**Action:** OAuth2/OIDC + Microsoft Entra ID + RBAC\
**Result:** Secure enterprise authentication/authorization

## Story F --- Payment Integration

**Situation:** Multi-currency payments\
**Task:** Integrate gateways and reconcile webhooks\
**Action:** Stripe/Braintree + webhook handling\
**Result:** Automated payment processing/reconciliation

## Story G --- Real-time POS

**Situation:** Multi-location transaction synchronization\
**Task:** Real-time event propagation\
**Action:** WebSockets + RabbitMQ\
**Result:** Event-driven synchronization

## Story H --- AI Projects

**Situation:** Need AI-assisted/AI-enabled workflows\
**Task:** Build practical AI integrations\
**Action:** Spring AI, RAG, embeddings, MCP\
**Result:** Recruiter assistant / AI-ready application capabilities

------------------------------------------------------------------------

# 35. Final Rapid Revision Checklist

Before the interview, make sure you can explain these WITHOUT memorized
textbook wording:

## Java

-   [ ] Virtual Threads
-   [ ] ExecutorService
-   [ ] CompletableFuture
-   [ ] synchronized
-   [ ] volatile
-   [ ] ConcurrentHashMap
-   [ ] Java Memory Model
-   [ ] immutability

## Spring

-   [ ] DI
-   [ ] transactions
-   [ ] propagation
-   [ ] isolation
-   [ ] WebClient
-   [ ] validation
-   [ ] exception handling

## Microservices

-   [ ] API Gateway
-   [ ] service discovery
-   [ ] Saga
-   [ ] Outbox
-   [ ] idempotency
-   [ ] eventual consistency
-   [ ] distributed tracing

## Kafka

-   [ ] topic
-   [ ] partition
-   [ ] consumer group
-   [ ] offset
-   [ ] rebalance
-   [ ] lag
-   [ ] delivery semantics
-   [ ] KRaft
-   [ ] MM2
-   [ ] DR
-   [ ] active-active

## Resilience

-   [ ] timeout
-   [ ] retry
-   [ ] circuit breaker
-   [ ] bulkhead
-   [ ] rate limiter

## Database

-   [ ] index
-   [ ] composite index
-   [ ] execution plan
-   [ ] N+1
-   [ ] connection pool
-   [ ] optimistic locking
-   [ ] pessimistic locking

## Security

-   [ ] OAuth2
-   [ ] OIDC
-   [ ] JWT
-   [ ] RBAC
-   [ ] Keycloak
-   [ ] OWASP
-   [ ] encryption
-   [ ] secrets

## Testing

-   [ ] JUnit 5
-   [ ] Mockito
-   [ ] integration tests
-   [ ] Testcontainers

## Cloud Native

-   [ ] Docker
-   [ ] Kubernetes
-   [ ] readiness/liveness
-   [ ] rolling deployment
-   [ ] Quarkus
-   [ ] GraalVM Native Image

## Production

-   [ ] RCA
-   [ ] observability
-   [ ] p95/p99
-   [ ] stress testing
-   [ ] rollback
-   [ ] zero downtime

## Leadership

-   [ ] code review
-   [ ] mentoring
-   [ ] prioritization
-   [ ] technical trade-offs
-   [ ] security/compliance

------------------------------------------------------------------------

# 36. Final Interview Strategy

Your goal is **not** to prove that you know every technology in the JD
equally deeply.

Your goal is to show:

> **"I have strong production experience with
> Java/Spring/microservices/security/performance, I understand
> distributed-system trade-offs, I can reason through unfamiliar
> technologies, and I can operate at senior level."**

When asked about a technology you have less hands-on experience with:

### Use this pattern

> "I haven't used X extensively in production yet, but my understanding
> is... The reason I would consider it here is... The equivalent problem
> I have solved using Y is... If I joined the team, I would validate the
> decision by..."

That answer is much stronger than bluffing.

------------------------------------------------------------------------

# 37. Highest-Priority Topics for This Specific JD

If interview time is limited, prioritize in this exact order:

### 🔴 Tier 1 --- Must Know

1.  Java 21+ / Virtual Threads
2.  Microservices architecture
3.  Kafka fundamentals
4.  Kafka partitioning / offsets / consumer groups
5.  KRaft
6.  MirrorMaker 2 / DR concepts
7.  Resilience4j
8.  SQL tuning
9.  OAuth2/OIDC/JWT
10. Production RCA / system design

### 🟠 Tier 2 --- Strongly Prepare

11. Spring WebClient
12. Hibernate/JPA
13. Redis
14. JUnit/Mockito/Testcontainers
15. Docker/Kubernetes
16. Zero-downtime deployment
17. Event-driven architecture
18. Outbox / Saga / Idempotency

### 🟡 Tier 3 --- Targeted Preparation

19. Quarkus
20. GraalVM Native Image
21. MapStruct
22. AI-assisted engineering

------------------------------------------------------------------------

# 38. One Final Rule

For every technical answer, think in this sequence:

``` text
Definition
    ↓
Why it matters
    ↓
How it works
    ↓
Trade-offs
    ↓
Failure scenario
    ↓
How I would monitor it
    ↓
My real experience
```

That is the difference between a **mid-level answer** and a **senior
engineer answer**.

------------------------------------------------------------------------

## End of Handbook
