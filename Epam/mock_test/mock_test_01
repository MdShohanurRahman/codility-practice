# EPAM API Integration Engineer – 90 Minute Technical Interview Preparation Pack

## Target Role

**API Integration Engineer – Java**

## Expected Interview

**Duration:** 90 minutes
**Platform:** EPAM Interview Platform
**Possible format:** Technical discussion + live coding/practical task + screen sharing

---

# 1. Interview Strategy

এই interview-এ শুধু মুখস্থ answer দিলে হবে না।

প্রতিটা technical answer এই structure-এ দেওয়ার চেষ্টা করবে:

> **Definition → Why → Example → Trade-off**

Example:

**Question:** Why use asynchronous messaging?

**Weak answer:**

> Async communication is faster.

**Better answer:**

> Asynchronous messaging decouples the producer from the consumer. The producer doesn't have to wait for the downstream service to finish processing. This is useful for long-running or non-critical real-time operations such as notifications. The trade-off is that the system becomes eventually consistent and requires retry, DLQ and idempotency handling.

এভাবে answer দিলে Senior-level impression তৈরি হবে।

---

# 2. 90-Minute Expected Flow

একটা possible structure:

| Time      | Area                          |
| --------- | ----------------------------- |
| 0–10 min  | Introduction + Project        |
| 10–25 min | Java Core                     |
| 25–40 min | REST/API                      |
| 40–55 min | Integration + Troubleshooting |
| 55–70 min | Coding                        |
| 70–82 min | Spring Boot/Microservices     |
| 82–87 min | Testing/System Design         |
| 87–90 min | Your Questions                |

এটা exact interview structure না; preparation-এর জন্য model হিসেবে ব্যবহার করবে।

---

# 3. Self Introduction

প্রথম 2–3 মিনিট খুব important।

### Sample Answer

> I'm a Senior Software Engineer with around six years of experience, primarily working with Java, Spring Boot and distributed backend systems.
>
> My main experience is in building REST APIs, microservices, third-party integrations and asynchronous processing using technologies such as RabbitMQ.
>
> I've worked with different databases including PostgreSQL, Oracle and MySQL, and I've also worked with authentication and authorization using JWT, OAuth2 and Keycloak.
>
> In my recent projects, I've worked on areas such as payment integrations, webhook processing, API security and integration with external services.
>
> I'm particularly interested in this role because it combines Java development with API integration, troubleshooting and distributed system concepts, which closely matches my experience.

---

# 4. Project Deep Dive

এই section-এর জন্য নিজের 2–3টা project খুব ভালোভাবে prepare করবে।

## Project #1 – Payment Integration

Architecture explain করতে পারো:

```text
Client
   |
   v
Spring Boot API
   |
   v
Payment Provider
   |
   v
Webhook
   |
   v
Webhook API
   |
   v
Verify Payment
   |
   v
Update Transaction
```

### Possible Questions

### Q1. How did you integrate with the payment provider?

বলবে:

* REST API
* authentication
* request/response DTO
* timeout
* error handling
* webhook
* signature verification
* idempotency
* transaction state management

---

### Q2. What happens if payment succeeds but your application doesn't receive the webhook?

Good answer:

> I wouldn't assume the payment failed. The payment provider should be treated as the source of truth for payment status. Depending on the provider, I would implement reconciliation or status polling. I would also keep the transaction in an intermediate state until it is confirmed.

---

### Q3. What if the same webhook arrives twice?

Answer:

> Webhooks should be processed idempotently. I would use a unique event ID or transaction ID and maintain a processed-event record or enforce a database uniqueness constraint. If the event has already been processed, I would safely ignore the duplicate.

---

# 5. Java Core – Must Know

## Q1. What are the main OOP principles?

### Answer

```text
Encapsulation
Inheritance
Polymorphism
Abstraction
```

### Easy memory trick

> **EIPA**

---

# 6. Interface vs Abstract Class

### Interface

Use when defining a contract/capability.

### Abstract class

Use when you need shared state or common implementation.

Example:

```java
interface PaymentProcessor {
    void process();
}
```

```java
abstract class BasePaymentProcessor {

    protected String merchantId;

    abstract void process();

    void validate() {
        // common logic
    }
}
```

---

# 7. == vs equals()

`==`

* primitive → value
* object → reference

`equals()`

* logical equality

Example:

```java
String a = new String("Java");
String b = new String("Java");

a == b       // false
a.equals(b)  // true
```

---

# 8. equals() and hashCode()

Rule:

> If two objects are equal according to equals(), they must have the same hashCode().

Otherwise collections such as `HashMap` and `HashSet` can behave incorrectly.

---

# 9. How does HashMap work?

Core concept:

```text
key
 ↓
hashCode()
 ↓
hash
 ↓
bucket
 ↓
compare keys using equals()
```

Modern Java HashMap uses buckets and can transform heavily-colliding buckets into tree structures under certain conditions.

Important points:

* average lookup: O(1)
* hashCode determines bucket
* equals determines actual key equality
* allows one null key
* not thread-safe

---

# 10. HashMap vs ConcurrentHashMap

### HashMap

* not thread-safe
* suitable for single-threaded/general use

### ConcurrentHashMap

* designed for concurrent access
* better than synchronizing an entire HashMap
* does not allow null keys/values

---

# 11. ArrayList vs LinkedList

### ArrayList

Good for:

* random access
* read-heavy operations

`get(index)` → O(1)

### LinkedList

Good for:

* insertion/removal when node position is already known

But in real-world Java applications, `ArrayList` is usually preferred unless there is a specific reason to use LinkedList.

---

# 12. HashSet vs TreeSet

### HashSet

* unordered
* average O(1)
* uses hashing

### TreeSet

* sorted
* O(log n)
* tree-based

---

# 13. String vs StringBuilder vs StringBuffer

### String

Immutable.

### StringBuilder

Mutable and not synchronized.

Use for normal single-threaded string manipulation.

### StringBuffer

Mutable and synchronized.

Usually less preferred unless synchronization is specifically required.

---

# 14. Checked vs Unchecked Exception

### Checked

Compiler forces handling/declaration.

Example:

```java
IOException
SQLException
```

### Unchecked

Subclass of RuntimeException.

```java
NullPointerException
IllegalArgumentException
```

---

# 15. final vs finally vs finalize

### final

Keyword.

```java
final variable
final method
final class
```

### finally

Exception handling block.

### finalize

Old JVM cleanup mechanism; deprecated/should not be relied upon.

---

# 16. Java Stream Questions

Must know:

```java
filter()
map()
flatMap()
distinct()
sorted()
limit()
skip()
collect()
reduce()
groupingBy()
partitioningBy()
```

Example:

```java
List<String> names = users.stream()
        .filter(User::isActive)
        .map(User::getName)
        .sorted()
        .toList();
```

---

# 17. map vs flatMap

`map`

```text
A → B
```

`flatMap`

```text
A → multiple B
then flatten
```

Example:

```java
List<List<String>> names;

names.stream()
     .flatMap(List::stream)
     .toList();
```

---

# 18. Optional

Good:

```java
userRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException(id));
```

Avoid using Optional everywhere, especially as entity fields or method parameters without a clear reason.

---

# 19. Multithreading

Know:

```text
Thread
Runnable
Callable
ExecutorService
Future
CompletableFuture
synchronized
Lock
volatile
AtomicInteger
ConcurrentHashMap
```

---

# 20. synchronized vs volatile

### synchronized

Provides:

* mutual exclusion
* visibility

### volatile

Provides visibility guarantees but does not make compound operations atomic.

Example:

```java
count++;
```

is not made atomic just because `count` is volatile.

---

# 21. Race Condition

A race condition occurs when multiple threads access shared state and the result depends on execution timing.

Example:

```java
counter++;
```

Two threads can read the same value and overwrite each other's update.

Solutions:

* synchronized
* Lock
* AtomicInteger
* concurrent data structures
* redesign to avoid shared mutable state

---

# 22. CompletableFuture

Example:

```java
CompletableFuture
    .supplyAsync(() -> callExternalApi(), executor)
    .thenApply(this::transform)
    .thenAccept(this::save);
```

Know:

```text
thenApply
thenCompose
thenCombine
exceptionally
handle
whenComplete
allOf
```

---

# 23. Thread Pool Question

### Q: Why shouldn't we create unlimited threads?

Because threads consume:

* memory
* CPU
* scheduling overhead

Too many threads can cause:

* context switching
* resource exhaustion
* poor performance

Use bounded executors and proper backpressure.

---

# 24. REST API – MUST KNOW

## HTTP Methods

| Method | Typical use    |
| ------ | -------------- |
| GET    | Retrieve       |
| POST   | Create/action  |
| PUT    | Replace        |
| PATCH  | Partial update |
| DELETE | Delete         |

---

# 25. Idempotency

An operation is idempotent when performing it multiple times has the same intended effect as performing it once.

Examples:

```text
GET
PUT
DELETE
```

are generally considered idempotent.

POST is generally not inherently idempotent.

For payment APIs, we can make POST operations idempotent using an idempotency key.

---

# 26. HTTP Status Codes

### Success

```text
200 OK
201 Created
204 No Content
```

### Client errors

```text
400 Bad Request
401 Unauthorized
403 Forbidden
404 Not Found
409 Conflict
422 Unprocessable Content
429 Too Many Requests
```

### Server/infrastructure errors

```text
500 Internal Server Error
502 Bad Gateway
503 Service Unavailable
504 Gateway Timeout
```

---

# 27. 401 vs 403

### 401

Authentication problem.

> Who are you?

### 403

Authenticated but not authorized.

> I know who you are, but you don't have permission.

---

# 28. 502 vs 503 vs 504

### 502

Gateway received an invalid response from upstream.

### 503

Service temporarily unavailable.

### 504

Gateway/proxy timed out waiting for upstream.

This is particularly important for integration troubleshooting.

---

# 29. PUT vs PATCH

### PUT

Generally represents replacement of a resource.

### PATCH

Partial modification.

Example:

```http
PATCH /users/10
```

```json
{
  "email": "new@example.com"
}
```

---

# 30. Authentication vs Authorization

### Authentication

Who are you?

### Authorization

What are you allowed to do?

Memory trick:

> **AuthN = Authentication**
> **AuthZ = Authorization**

---

# 31. OAuth2 vs JWT

They are not alternatives.

### OAuth2

Authorization framework.

### JWT

Token format.

OAuth2 can use JWT access tokens, but OAuth2 itself is not JWT.

---

# 32. API Security

Know:

```text
HTTPS
OAuth2
JWT
API keys
Input validation
Rate limiting
CORS
CSRF
Authentication
Authorization
Secret management
Audit logging
```

Never log:

```text
password
access token
refresh token
card details
secret keys
```

---

# 33. Integration Patterns

This is one of the most important sections for this role.

Know:

```text
Request/Response
Request/Reply
Publish/Subscribe
Message Queue
Event-driven architecture
Retry
Timeout
Circuit Breaker
Dead Letter Queue
Outbox Pattern
Idempotency
API Gateway
Rate Limiting
```

---

# 34. Synchronous vs Asynchronous

### Synchronous

```text
Client
  |
  v
Service A
  |
  v
Service B
  |
  v
Response
```

Service A waits.

### Asynchronous

```text
Service A
   |
   v
Queue
   |
   v
Service B
```

Service A does not need to wait for Service B.

---

# 35. When should you use async?

Good examples:

* notifications
* email
* report generation
* background processing
* event propagation
* long-running external integrations

Avoid blindly making everything asynchronous.

---

# 36. Retry Strategy

Never simply retry forever.

A good retry mechanism considers:

```text
maximum attempts
backoff
jitter
retryable errors
timeout
DLQ
idempotency
```

Example:

```text
Attempt 1
   ↓
1 sec
   ↓
Attempt 2
   ↓
2 sec
   ↓
Attempt 3
   ↓
DLQ
```

---

# 37. Exponential Backoff

Instead of:

```text
1s
1s
1s
1s
```

use something like:

```text
1s
2s
4s
8s
```

Add jitter to avoid many clients retrying simultaneously.

---

# 38. Circuit Breaker

States:

```text
CLOSED
   ↓ failures
OPEN
   ↓ wait
HALF_OPEN
   ↓ success
CLOSED
```

Purpose:

> Prevent repeatedly calling an unhealthy downstream service.

Spring ecosystem:

```text
Resilience4j
```

---

# 39. Timeout

Every external API call should have a sensible timeout.

Without timeout:

```text
Request
   ↓
External API hangs
   ↓
Thread waits
   ↓
Threads exhausted
   ↓
Application becomes unhealthy
```

Timeout protects resources.

---

# 40. Idempotency in Integration

Imagine:

```text
POST /payment
```

Request reaches payment provider.

Provider processes it.

Network fails before response reaches us.

Our application doesn't know whether payment succeeded.

If we retry blindly:

```text
Payment #1
Payment #2
```

could happen.

Use an idempotency key:

```text
Idempotency-Key: PAYMENT-12345
```

The provider can safely identify duplicate requests.

---

# 41. Webhook

Webhook = server-to-server notification.

Example:

```text
Payment Provider
       |
       | POST /webhook
       v
Your Application
```

Important:

* signature verification
* idempotency
* authentication
* replay protection
* fast acknowledgement
* asynchronous processing where appropriate

---

# 42. Webhook Best Practice

Don't perform heavy processing before responding to the provider.

Better:

```text
Webhook
   |
   v
Validate
   |
   v
Persist event
   |
   v
Return 200
   |
   v
Queue
   |
   v
Worker
```

---

# 43. API Troubleshooting Scenario

### Question

> An external API works in Postman but fails from your Spring Boot application. What would you check?

Answer structure:

```text
1. HTTP method
2. URL
3. Headers
4. Authentication
5. Token
6. Request payload
7. Content-Type
8. Accept header
9. Proxy
10. DNS
11. TLS/SSL
12. Timeout
13. Network connectivity
14. Application logs
15. Correlation ID
16. External service logs if available
```

Most important:

> First compare the actual request generated by the application with the successful Postman request.

---

# 44. API Returns 500

Don't immediately assume your application is broken.

Check:

```text
Who generated the 500?
Your service?
Gateway?
Downstream?
```

Use:

```text
correlation ID
timestamp
request ID
logs
distributed tracing
```

to identify the actual source.

---

# 45. API Returns 503

Possible reasons:

* service unavailable
* deployment
* overload
* health check failure
* dependency failure

Possible actions:

```text
Check monitoring
Check logs
Check downstream health
Check deployment
Check retry policy
Check circuit breaker
```

---

# 46. API Returns 504

Think:

> Timeout.

Check:

```text
Application timeout
Gateway timeout
Load balancer timeout
Downstream timeout
Database latency
External API latency
```

---

# 47. Correlation ID

Example:

```text
X-Correlation-ID: abc-123
```

Same ID can follow:

```text
Client
 ↓
API Gateway
 ↓
Service A
 ↓
Service B
 ↓
External API
```

Useful for debugging distributed systems.

---

# 48. Outbox Pattern

Problem:

You need to:

```text
Update DB
+
Publish message
```

If DB succeeds but message publishing fails:

```text
DB = SUCCESS
Message = FAILED
```

Inconsistent state.

Solution:

```text
Transaction
   |
   +--> Business data
   |
   +--> Outbox event
```

Then:

```text
Outbox
   ↓
Publisher
   ↓
RabbitMQ/Kafka
```

---

# 49. RabbitMQ vs Kafka

### RabbitMQ

Good for:

* task queues
* routing
* work distribution
* request/reply
* traditional messaging

### Kafka

Good for:

* event streaming
* high throughput
* replay
* event history
* analytics/event pipelines

Don't say:

> Kafka is always better.

Choose based on requirements.

---

# 50. Dead Letter Queue

If a message repeatedly fails:

```text
Queue
 ↓
Retry
 ↓
Retry
 ↓
Retry
 ↓
DLQ
```

DLQ allows:

* investigation
* manual replay
* preventing poison messages from blocking normal processing

---

# 51. Spring Boot Questions

## Q: @RestController vs @Controller

`@RestController` effectively combines:

```java
@Controller
@ResponseBody
```

and is normally used for REST APIs.

---

# 52. @Component vs @Service vs @Repository

All are Spring stereotypes.

`@Component`

Generic component.

`@Service`

Service/business layer.

`@Repository`

Persistence layer and exception translation semantics.

---

# 53. Dependency Injection

Prefer constructor injection.

```java
@Service
public class PaymentService {

    private final PaymentClient paymentClient;

    public PaymentService(PaymentClient paymentClient) {
        this.paymentClient = paymentClient;
    }
}
```

Advantages:

* immutable dependencies
* easier testing
* explicit dependencies
* no field injection problems

---

# 54. @Transactional

Defines transaction boundaries.

Important concepts:

```text
commit
rollback
propagation
isolation
readOnly
```

Common trap:

> `@Transactional` doesn't magically make every operation transactional across external services.

For example:

```text
DB transaction
+
External REST API
```

cannot automatically become one ACID transaction.

---

# 55. Global Exception Handling

Use:

```java
@RestControllerAdvice
```

Example:

```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    ResponseEntity<?> handle(UserNotFoundException ex) {
        ...
    }
}
```

Return consistent error responses.

---

# 56. DTO vs Entity

Don't blindly expose JPA entities as API responses.

Use DTOs for:

* API contract
* security
* decoupling
* versioning
* controlling response shape

---

# 57. Microservices

Know:

```text
Service ownership
Database per service
API Gateway
Service discovery
Centralized configuration
Observability
Resilience
Async messaging
Distributed transactions
Eventual consistency
```

---

# 58. Why Microservices?

Potential benefits:

* independent deployment
* team autonomy
* scalability
* fault isolation
* independent technology choices

Costs:

* network failures
* distributed transactions
* observability complexity
* deployment complexity
* data consistency challenges

Never say:

> Microservices are always better than monoliths.

---

# 59. How would you convert a monolith to microservices?

Good answer:

```text
Understand domains
      ↓
Identify bounded contexts
      ↓
Extract low-coupling capability
      ↓
Define API contract
      ↓
Separate data ownership
      ↓
Introduce observability
      ↓
Deploy independently
      ↓
Gradually migrate traffic
```

Avoid doing a "big bang" rewrite.

---

# 60. Database Per Service

Each service owns its data.

Instead of:

```text
Service A ──┐
Service B ──┼── Shared DB
Service C ──┘
```

prefer:

```text
Service A → DB A
Service B → DB B
Service C → DB C
```

Cross-service data consistency is then handled using events, APIs or patterns such as Saga.

---

# 61. Distributed Transaction

Problem:

```text
Order Service
 ↓
Payment Service
 ↓
Inventory Service
```

One global DB transaction isn't practical across independent services.

Options:

* Saga
* compensation
* eventual consistency
* orchestration/choreography

---

# 62. Coding Preparation

For EPAM, don't only practice LeetCode hard problems.

Focus on **Easy → Medium**.

## Priority 1

### Two Sum

```text
HashMap
O(n)
```

### First Non-Repeating Character

```text
Map frequency
O(n)
```

### Valid Anagram

```text
Frequency counting
```

### Duplicate Detection

```text
HashSet
```

### Palindrome

```text
Two pointers
```

### Valid Parentheses

```text
Stack
```

---

# 63. Priority 2

### Move Zeroes

Know both:

```text
two-pointer
```

and simple extra-array approach.

Prefer O(n), O(1) extra space where appropriate.

---

### Merge Two Sorted Arrays

Think:

```text
two pointers
```

---

### Binary Search

Know:

```java
while (left <= right) {
    int mid = left + (right - left) / 2;
}
```

---

# 64. Sliding Window

Classic:

> Longest substring without repeating characters.

Pattern:

```text
left
right
Set/Map
```

Time:

```text
O(n)
```

---

# 65. Java Streams Coding

Practice:

### Find active users

```java
users.stream()
     .filter(User::isActive)
     .toList();
```

### Extract names

```java
users.stream()
     .map(User::getName)
     .toList();
```

### Group by department

```java
users.stream()
     .collect(Collectors.groupingBy(User::getDepartment));
```

### Count by department

```java
users.stream()
     .collect(Collectors.groupingBy(
         User::getDepartment,
         Collectors.counting()
     ));
```

---

# 66. Coding Interview Technique

When interviewer gives you a problem:

### Step 1

Clarify requirements.

### Step 2

Give brute-force approach.

### Step 3

Explain optimization.

### Step 4

Discuss complexity.

### Step 5

Code.

### Step 6

Test with examples.

### Step 7

Discuss edge cases.

Never immediately start typing.

---

# 67. Complexity

Must know:

```text
O(1)
O(log n)
O(n)
O(n log n)
O(n²)
```

Examples:

```text
HashMap lookup → average O(1)
Binary search → O(log n)
Merge sort → O(n log n)
Nested loops → often O(n²)
```

---

# 68. Testing

Know:

```text
Unit Test
Integration Test
Contract Test
End-to-End Test
```

### Unit Test

Tests one unit in isolation.

### Integration Test

Tests interaction between components.

### Contract Test

Tests whether service/provider and consumer agree on an API contract.

---

# 69. Mockito

Know:

```java
when()
verify()
given()
then()
```

Example:

```java
when(paymentClient.pay(any()))
    .thenReturn(response);
```

Then:

```java
verify(paymentClient).pay(any());
```

---

# 70. Testcontainers

Useful for realistic integration tests using real infrastructure such as:

```text
PostgreSQL
Kafka
RabbitMQ
Redis
```

instead of relying entirely on mocks.

---

# 71. TDD

Remember:

```text
RED
 ↓
GREEN
 ↓
REFACTOR
```

### Red

Write failing test.

### Green

Implement minimum code to pass.

### Refactor

Improve implementation while keeping tests green.

---

# 72. API Versioning

Possible strategies:

```text
/v1/users
/v2/users
```

or headers/media types.

Important:

> Avoid breaking existing clients unexpectedly.

Prefer backward-compatible changes where possible.

---

# 73. Rate Limiting

Protect API from excessive traffic.

Strategies:

```text
Token Bucket
Leaky Bucket
Fixed Window
Sliding Window
```

Use cases:

* abuse prevention
* protecting downstream
* fair usage
* controlling traffic spikes

---

# 74. API Gateway

Responsibilities can include:

```text
Routing
Authentication
Authorization
Rate limiting
TLS termination
Logging
Request transformation
Aggregation
```

But avoid putting excessive business logic into the gateway.

---

# 75. Observability

Three pillars:

```text
Logs
Metrics
Traces
```

### Logs

What happened?

### Metrics

How much/how often?

### Traces

Where did the request spend time?

---

# 76. ELK vs Grafana Stack

### ELK

```text
Elasticsearch
Logstash
Kibana
```

Mainly log aggregation/search/visualization.

### Grafana ecosystem

Can work with:

```text
Prometheus
Loki
Tempo
Grafana
```

Typical:

```text
Prometheus → Metrics
Loki → Logs
Tempo → Traces
Grafana → Visualization
```

---

# 77. Production Incident Scenario

### Question

> Production API latency suddenly increases. What do you do?

Answer:

```text
1. Confirm incident
2. Check metrics
3. Check error rate
4. Check latency
5. Check CPU/memory
6. Check DB
7. Check downstream APIs
8. Check recent deployment
9. Check logs/traces
10. Identify bottleneck
11. Mitigate
12. RCA
13. Prevent recurrence
```

Don't immediately restart everything.

---

# 78. External API Is Slow

Possible solutions:

```text
Timeout
Connection pooling
Async processing
Caching where appropriate
Circuit breaker
Retry with backoff
Bulkhead
Rate limiting
```

But:

> Never add retries without considering idempotency.

---

# 79. API Dependency Is Down

Possible architecture:

```text
Your Service
     |
     v
Circuit Breaker
     |
     v
External API
```

If dependency is down:

```text
Fail fast
or
Fallback
or
Queue request
```

depending on business requirements.

---

# 80. MuleSoft / Boomi – Nice to Have

If asked:

> Have you worked with MuleSoft/Boomi?

Don't pretend.

Use:

> I haven't worked extensively with those platforms directly, but I have experience building API and integration solutions using Java and Spring Boot. I'm familiar with the underlying integration concepts such as API orchestration, transformation, authentication, retries, error handling and asynchronous messaging, so I believe I can adapt to an integration platform relatively quickly.

---

# 81. API Management

Know the basic responsibilities:

```text
API publishing
Authentication
Authorization
Rate limiting
Traffic management
Analytics
Versioning
Developer access
```

---

# 82. Scenario: External API Changes Response

Question:

> A third-party API changes its response format. What do you do?

Answer:

```text
Detect contract change
 ↓
Check impact
 ↓
Version/adapter layer
 ↓
Update integration
 ↓
Regression tests
 ↓
Contract tests
 ↓
Deploy safely
```

Avoid spreading external API models throughout your domain.

---

# 83. Scenario: Third-party API Has Rate Limit

Suppose:

```text
100 requests/minute
```

Your application suddenly needs:

```text
1000 requests/minute
```

Solutions:

```text
Queue requests
Rate limiter
Batching
Caching
Backoff
Multiple workers with controlled concurrency
```

Never simply increase concurrency blindly.

---

# 84. Scenario: Duplicate Message

Consumer receives:

```text
OrderCreated
```

twice.

Use:

```text
event ID
+
idempotency
+
unique constraint
```

Example:

```text
processed_events

event_id UNIQUE
```

---

# 85. Scenario: Message Processing Fails

Architecture:

```text
Queue
 ↓
Consumer
 ↓
Failure
 ↓
Retry
 ↓
Retry
 ↓
DLQ
```

Then monitor DLQ and support replay.

---

# 86. System Design Question

### Design an API Integration Service

Possible architecture:

```text
                  ┌───────────────┐
Client ─────────> │ API Gateway   │
                  └───────┬───────┘
                          |
                          v
                  ┌───────────────┐
                  │ Integration   │
                  │ Service       │
                  └───────┬───────┘
                          |
             ┌────────────┼────────────┐
             |            |            |
             v            v            v
          REST API     Queue        Database
             |
             v
      External Provider
```

Add:

```text
Redis
Resilience4j
RabbitMQ/Kafka
Prometheus
Grafana
Centralized logging
```

where requirements justify them.

---

# 87. Design Principles to Mention

When explaining architecture:

```text
Loose coupling
High cohesion
Separation of concerns
Fault isolation
Observability
Security
Scalability
Idempotency
Resilience
Backward compatibility
```

---

# 88. AI / GitHub Copilot Question

### Q: How do you use AI as an engineer?

Answer:

> I use AI mainly as a productivity and engineering assistance tool. For example, I can use it to explore implementation approaches, generate boilerplate, create test cases, explain unfamiliar code, or review possible edge cases. However, I don't blindly trust generated code. I validate the implementation, security, performance and maintainability, run tests, and take responsibility for the final solution.

---

# 89. AI Coding During Interview

Your email explicitly says:

> Don't use AI tools or search engines unless the interviewer allows it.

Therefore:

### Interview rule

**Don't use ChatGPT during the interview unless they explicitly say it's allowed.**

If they give you a coding problem:

```text
Think
→ Explain
→ Code
→ Test
→ Optimize
```

---

# 90. Behavioral Questions

Prepare these:

### Q1. Tell me about yourself.

### Q2. Tell me about a difficult production issue.

### Q3. Tell me about a technical decision you disagreed with.

### Q4. How do you handle tight deadlines?

### Q5. How do you debug an unfamiliar codebase?

### Q6. Tell me about a failure.

### Q7. How do you ensure code quality?

### Q8. How do you review another engineer's code?

---

# 91. Strong Answer: Difficult Production Issue

Use STAR:

```text
Situation
Task
Action
Result
```

Example structure:

> We had an integration where external processing was significantly slower than expected. The scheduled job was fetching another batch before the previous batch completed. I analyzed the execution flow, identified the concurrency issue, and redesigned the processing using a controlled executor and synchronization strategy. I also added proper timeout and monitoring. As a result, overlapping processing was prevented and the system became more predictable under load.

---

# 92. Questions You Should Ask EPAM

শেষে যদি interviewer বলে:

> Do you have any questions for us?

এইগুলোর মধ্যে 2–3টা জিজ্ঞেস করবে।

### Question 1

> What kind of API integrations would this role work on most frequently—internal services, third-party APIs, or enterprise integration platforms?

### Question 2

> What does the typical architecture look like for the integration solutions this team builds?

### Question 3

> What are the biggest technical challenges the team is currently trying to solve?

### Question 4

> How much of the role is new development versus production support and troubleshooting?

### Question 5

> What would success look like for someone in this role during the first six months?

---

# 93. EPAM Interview – Rapid Revision Sheet

Interview-এর আগের রাতে এগুলো অবশ্যই revise করবে:

```text
JAVA
├── OOP
├── Collections
├── HashMap
├── equals/hashCode
├── String
├── Exceptions
├── Streams
├── Optional
├── Multithreading
├── CompletableFuture
└── JVM basics

REST/API
├── HTTP methods
├── Status codes
├── Idempotency
├── Authentication
├── Authorization
├── OAuth2
├── JWT
├── API versioning
├── Rate limiting
└── API security

INTEGRATION
├── Sync vs Async
├── Retry
├── Timeout
├── Circuit Breaker
├── DLQ
├── Outbox
├── Webhook
├── Idempotency
├── Correlation ID
└── RabbitMQ/Kafka

SPRING
├── DI
├── REST Controller
├── Exception Handling
├── Validation
├── Transaction
├── DTO
└── Testing

MICROSERVICES
├── API Gateway
├── Database per service
├── Service communication
├── Eventual consistency
├── Saga
└── Observability

CODING
├── HashMap
├── HashSet
├── Two Pointer
├── Sliding Window
├── Stack
├── Binary Search
├── Sorting
└── Java Streams
```

---

# 94. Last 24-Hour Preparation

## 3 hours available

### Hour 1

Java:

```text
Collections
HashMap
equals/hashCode
Streams
Concurrency
CompletableFuture
```

### Hour 2

API:

```text
REST
HTTP
OAuth2/JWT
Webhook
Idempotency
Retry
Circuit Breaker
Timeout
DLQ
Outbox
```

### Hour 3

Coding:

```text
2 Array problems
2 String problems
1 HashMap
1 Stack
1 Sliding Window
1 Java Stream
```

---

# 95. Interview Day Strategy

### Before coding

Say:

> Let me clarify the requirements first.

Then:

> I'll start with a straightforward approach and then see whether we can optimize it.

After coding:

> Let me walk through a couple of edge cases.

This sounds much better than silently typing.

---

# 96. Golden Rules

### Rule 1

Don't bluff technologies.

If you haven't used MuleSoft:

> “I haven't used it directly, but…”

---

### Rule 2

Don't say:

> “Microservices are better.”

Say:

> “It depends on the requirements and organizational context.”

---

### Rule 3

Don't say:

> “Retry solves the problem.”

Say:

> “Retry can help with transient failures, but we need timeout, backoff, retry limits and idempotency.”

---

### Rule 4

Don't say:

> “Kafka is faster than RabbitMQ.”

Say:

> “They solve somewhat different messaging/streaming requirements, so I choose based on throughput, ordering, replay, routing and delivery requirements.”

---

### Rule 5

For every external integration think:

```text
Timeout
Retry
Idempotency
Authentication
Error handling
Observability
```

This is your **Integration Engineer mental checklist**.

---

# 97. The 20 Questions I Would Prioritize Most

If you have very little time, prepare these first:

1. How does HashMap work internally?
2. equals() vs hashCode()?
3. HashMap vs ConcurrentHashMap?
4. ArrayList vs LinkedList?
5. How does CompletableFuture work?
6. What is a race condition?
7. GET vs POST vs PUT vs PATCH?
8. What is idempotency?
9. 401 vs 403?
10. 502 vs 503 vs 504?
11. How would you troubleshoot an API failure?
12. How do you handle third-party API timeout?
13. How do you implement retry?
14. What is Circuit Breaker?
15. How do you handle duplicate webhook?
16. RabbitMQ vs Kafka?
17. What is Outbox Pattern?
18. How would you design a resilient API integration?
19. How do you handle distributed transactions?
20. Write a Java coding problem using HashMap/Streams.

---

# 98. Final Mental Model

For this interview, think like this:

```text
             JAVA
               |
               v
          SPRING BOOT
               |
               v
          REST / API
               |
               v
       INTEGRATION LAYER
               |
      ┌────────┼─────────┐
      ↓        ↓         ↓
   REST API  Queue    Webhook
      |        |         |
      └────────┼─────────┘
               ↓
        External Systems
               |
               v
        RESILIENCE
   ┌──────┬──────┬──────┐
   ↓      ↓      ↓      ↓
Timeout Retry Circuit Idempotency
Breaker
               |
               v
         OBSERVABILITY
       Logs / Metrics / Trace
```

এই পুরো flow explain করতে পারলে তুমি শুধু "Java developer" হিসেবে না, বরং **API Integration Engineer** হিসেবে answer করতে পারবে।

---

# Final Preparation Priority

## 🔴 Must Master

```text
Java Core
HashMap / Collections
Streams
Concurrency
REST API
HTTP status codes
Authentication / Authorization
OAuth2 / JWT
Idempotency
Webhook
Retry / Timeout
Circuit Breaker
RabbitMQ
API troubleshooting
Java coding
Spring Boot REST
```

## 🟡 Strong Understanding

```text
Kafka
Outbox
DLQ
API Gateway
Microservices
Distributed transactions
Saga
Observability
Testing
TDD
API versioning
Rate limiting
```

## 🟢 Nice to Have

```text
MuleSoft
Boomi
API Management platforms
AI/Copilot
SDD
```

**সবচেয়ে গুরুত্বপূর্ণ:** এই interview-এর জন্য তোমার preparation-এর কেন্দ্র হবে **“একটা external system-এর সাথে Java application কীভাবে reliably integrate করবে এবং production-এ failure হলে কীভাবে troubleshoot করবে”**।

তোমার existing experience-এর মধ্যে payment integration, webhook, RabbitMQ, SOAP processing, Spring Boot, microservices, security—এসবকে examples হিসেবে ব্যবহার করবে। এতে theoretical answer-এর বদলে বাস্তব engineering experience দেখাতে পারবে।
