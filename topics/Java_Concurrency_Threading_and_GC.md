# Java Concurrency, Threading, and JVM/GC

Interview note for EPAM and Hong Leong. Built so a follow-up can go from a definition to a real system: thread pools, virtual threads, database pools, and GC pauses.

Hong Leong’s JD names virtual threads, so that chapter is the deepest. EPAM also asks GC and troubleshooting, so memory and pause time sit at the same level.

---

## How to use this note

Say the **Interview answer** out loud. Then check the trap under it. The diagrams are the picture to draw if the interviewer goes quiet.

Priority:

| Topic | Priority |
| :--- | :---: |
| Thread fundamentals | High |
| Synchronization and locks | Highest |
| Executor and thread pool | Highest |
| `CompletableFuture` | Highest |
| Concurrent collections | High |
| Java Memory Model | Highest |
| Virtual threads | Highest |
| JVM memory and GC | Highest |
| Thread dump and GC troubleshooting | Highest |
| Practical scenarios | Highest |

One line that connects the whole note:

```
Thread
  -> shared mutable state?        race condition, volatile, synchronized, lock
  -> too many tasks?              thread pool
  -> compose async results?       CompletableFuture
  -> shared data structure?       ConcurrentHashMap, BlockingQueue, atomic types
  -> huge I/O concurrency?        virtual threads
  -> latency or heap pressure?    GC, leak, thread dump
```

---

## 1. Process, thread, and lifecycle

A **process** has its own address space. A **thread** is an execution path inside that process. Threads in one process share the heap and generally see the same objects. Each thread has its own stack.

```
Process
  ├── Heap          shared objects
  ├── Metaspace     class metadata
  ├── Thread A      Stack A
  ├── Thread B      Stack B
  └── Thread C      Stack C
```

### Lifecycle

```
NEW -> RUNNABLE -> TERMINATED

RUNNABLE can also be:
  BLOCKED         waiting to enter a monitor
  WAITING         wait(), join(), park()
  TIMED_WAITING   sleep(), timed wait(), timed join()
```

These names are exactly what a thread dump prints. `BLOCKED` on two threads that hold each other’s locks is the usual deadlock signature.

### `start()` and `run()`

**Interview answer:** `start()` asks the JVM to begin a new thread, which then calls `run()`. Calling `run()` directly is an ordinary method call on the current thread.

```java
thread.start(); // new thread
thread.run();   // current thread
```

A `Thread` object accepts `start()` once. A second call throws `IllegalThreadStateException`.

### `sleep()`, `wait()`, `join()`

| | `sleep` | `wait` | `join` |
| :--- | :--- | :--- | :--- |
| Defined on | `Thread` | `Object` | `Thread` |
| Releases the monitor | No | Yes | No (it waits on the other thread) |
| Purpose | Pause this thread | Coordinate with another thread | Wait until that thread finishes |
| Needs a monitor | No | Yes | No |

```java
synchronized (lock) {
    Thread.sleep(5000); // this thread still holds lock
}

synchronized (lock) {
    lock.wait();        // releases lock, waits for notify
}
```

`wait()` must run while the current thread owns that object’s monitor. Otherwise the JVM throws `IllegalMonitorStateException`.

Wait in a loop. A thread can wake without a matching `notify` (spurious wakeup), and the condition may already be false again by the time it reacquires the monitor.

```java
synchronized (lock) {
    while (!ready) {
        lock.wait();
    }
    // use the shared state
}
```

`notify()` wakes one waiter. `notifyAll()` wakes every waiter on that monitor. Prefer `notifyAll()` unless exactly one waiter is the correct choice.

`join()` means: this thread continues after `worker` has finished.

```java
worker.start();
worker.join();
```

### `interrupt()`

`interrupt()` is a request, not a kill. The target thread checks `Thread.interrupted()` or receives `InterruptedException` from `sleep`, `wait`, or `join`.

If you catch `InterruptedException` and the method cannot finish the cancellation itself, restore the flag so the caller still sees it:

```java
catch (InterruptedException e) {
    Thread.currentThread().interrupt();
}
```

### Daemon threads

A daemon thread does not keep the JVM alive. When every non-daemon thread finishes, the JVM exits and daemon threads stop where they are. Use them for background work that is safe to abandon, such as a housekeeping timer. Do not use them for work that must finish, such as writing a payment.

---

## 2. Race conditions and the memory model

### Race condition

**Interview answer:** A race condition happens when several threads use the same mutable state and the result depends on timing.

`count++` is three steps: read, add one, write. Two threads can both read `5` and both write `6`.

### Three separate problems

| Problem | Question it answers | What fixes it |
| :--- | :--- | :--- |
| Atomicity | Did the whole update happen as one step? | `synchronized`, `Lock`, `AtomicInteger` |
| Visibility | Will another thread see my write? | `volatile`, unlocking a monitor, atomics |
| Ordering | Can the CPU or compiler reorder these actions? | happens-before from `volatile`, locks, thread start/join |

### `volatile`

**Interview answer:** `volatile` makes a write visible to later reads and limits reordering around that variable. It does not make a compound action atomic. `volatile int count` still loses updates on `count++`.

Good use: a flag another thread must notice.

```java
volatile boolean running = true;
```

Line to remember: **visibility, not general atomicity.**

### Happens-before (the rule behind visibility)

If action A happens-before action B, the effects of A are visible to B. The ones to say in an interview:

- A write to a `volatile` field happens-before a later read of that field.
- Unlocking a monitor happens-before the next lock of that same monitor.
- `thread.start()` happens-before any action in that thread.
- Actions in a thread happen-before a successful `join()` on that thread returns.

### `synchronized`

**Interview answer:** For one monitor, `synchronized` gives mutual exclusion and visibility. Only one thread executes that critical section at a time, and what it writes is visible to the next thread that acquires the same monitor.

Instance method locks `this`. Static method locks the `Class` object.

```java
public synchronized void process() {}          // lock: this
public static synchronized void process() {}   // lock: Counter.class
```

Two threads calling `increment()` on the **same** instance take turns. Two threads calling it on **two different** instances do not share a lock, so they run together.

Keep the critical section small. Hold the lock around the shared update, and do the remote call outside it.

---

## 3. Locks, deadlock, livelock, starvation

### `synchronized` vs `ReentrantLock`

**Interview answer:** I use `synchronized` when I only need mutual exclusion. I use `ReentrantLock` when I need `tryLock`, a timeout, interruptible locking, or a fairness policy.

```java
if (lock.tryLock(200, TimeUnit.MILLISECONDS)) {
    try {
        process();
    } finally {
        lock.unlock();
    }
}
```

`unlock()` belongs in `finally`. An exception that skips `unlock()` leaves the lock held.

`ReentrantLock(true)` is fair: waiters tend to acquire in arrival order, with more overhead. The default constructor is unfair and usually faster. Fairness matters when some threads would otherwise wait a very long time.

### `ReadWriteLock` and `StampedLock`

`ReentrantReadWriteLock` allows many readers, or one writer. It fits data that is read far more often than it is written.

`StampedLock` adds an optimistic read: a reader takes a stamp, reads, then checks that no writer arrived. If a writer arrived, the reader retries under a real read lock. Use it when reads dominate and a retry is cheap.

### Deadlock

Two or more threads wait forever for locks the others already hold.

```
Thread A holds Lock 1 and waits for Lock 2
Thread B holds Lock 2 and waits for Lock 1
```

Prevention that is worth saying:

1. Acquire locks in one global order.
2. Keep critical sections short, and avoid nested locks.
3. Use `tryLock` with a timeout and a safe retry or failure path.
4. Prefer designs that do not need two locks at once.

### Livelock and starvation

**Deadlock:** threads are stuck and make no progress.

**Livelock:** threads keep running and reacting to each other, and still make no progress. Example: two threads each back off and retry the same pair of locks forever.

**Starvation:** a thread is repeatedly denied the CPU or a lock, so it does not get a fair chance to finish. A fair lock, or a smaller critical section, is the usual remedy.

---

## 4. Thread pools

Creating a platform thread per request costs memory, OS scheduling, and context switches. A pool caps how many of those threads exist.

### `ThreadPoolExecutor` knobs

```
corePoolSize   = 10     normal worker count
maxPoolSize    = 20     upper worker count
queueCapacity  = 100    tasks that can wait
```

Submission order for the usual `ThreadPoolExecutor`:

```
1. Fewer than corePoolSize workers?   create a worker
2. Queue has room?                     enqueue the task
3. Fewer than maxPoolSize workers?     create another worker
4. Otherwise                           rejection policy
```

**Trap:** with core 10 and a queue that still has space, task 11 is queued. It does not create thread 11. Extra threads up to `maxPoolSize` appear after the queue is full.

When workers are at max and the queue is full, the `RejectedExecutionHandler` runs.

`CallerRunsPolicy` runs the task on the submitting thread. That slows the producer down, which is a simple form of backpressure.

### Pool choice

| Workload | Shape | Pool idea |
| :--- | :--- | :--- |
| CPU-bound | calculation uses a core the whole time | about `availableProcessors()` workers |
| I/O-bound | threads spend time in DB, HTTP, disk | more workers, still bounded by the downstream limit |

More threads do not create more CPU, more database connections, or a higher API rate limit.

### `shutdown` and `shutdownNow`

`shutdown()` refuses new tasks and lets queued and running tasks finish.

`shutdownNow()` refuses new tasks, attempts to interrupt running tasks, and returns the tasks still in the queue. Interruption is still cooperative. A task that ignores the interrupt keeps running.

### `execute` vs `submit`

`execute(Runnable)` returns nothing. `submit` returns a `Future`, and it accepts a `Callable` when the task produces a value.

```java
executor.execute(() -> process());
Future<Result> future = executor.submit(() -> compute());
```

### A batch that must finish before the next batch

This is the “100 records in flight, do not take the next 100 yet” problem.

```java
List<Future<?>> batch = new ArrayList<>();
for (Record record : firstHundred) {
    batch.add(executor.submit(() -> process(record)));
}
for (Future<?> future : batch) {
    future.get();
}
// only then take the next 100
```

`invokeAll` does the same thing: it submits the batch and returns when every task has completed. Bound the pool, and bound how many records you pull, so a slow downstream system cannot pile up unbounded work.

---

## 5. CompletableFuture

A `Future` is a handle for a later result. `future.get()` waits. `CompletableFuture` lets you attach the next step instead of blocking the caller.

| Method | Use it when |
| :--- | :--- |
| `runAsync` | the task returns nothing |
| `supplyAsync` | the task returns a value |
| `thenApply` | turn a result into another value (`A -> B`) |
| `thenCompose` | the next step is itself a `CompletableFuture` (`A -> CompletableFuture<B>`) |
| `thenCombine` | two independent futures both have results to merge |
| `allOf` | wait until every future finishes |
| `anyOf` | continue when the first one finishes |
| `exceptionally` | provide a fallback after a failure |
| `handle` | see both the result and the error |

```java
getUser()
    .thenCompose(user -> getOrders(user.getId()))
    .thenApply(this::calculate);

userFuture.thenCombine(accountFuture, this::buildResponse);
```

`thenApply` vs `thenCompose` is the question interviewers use to see if you flatten. If the next method already returns a `CompletableFuture`, `thenApply` would give you a nested `CompletableFuture<CompletableFuture<T>>`. `thenCompose` flattens it.

`supplyAsync` with no executor uses `ForkJoinPool.commonPool()`. `thenApply` runs on the thread that completes the previous stage (or the caller, if that stage is already complete). `thenApplyAsync` moves the continuation to the common pool, or to an executor you pass in.

```java
future.thenApplyAsync(result -> process(result), ioExecutor);
```

**Interview answer:** I pass a dedicated executor when the work should not sit on the common pool. HTTP and database waits go to an I/O executor. Heavy calculation goes to a small CPU executor. That keeps one slow dependency from starving the others.

`exceptionally` is the failure path. `handle` receives the value and the throwable, so it can cover both success and failure.

---

## 6. Concurrent collections and atomics

### `HashMap` vs `ConcurrentHashMap`

**Interview answer:** `HashMap` has no thread-safety guarantee under concurrent writes. `ConcurrentHashMap` is built so many threads can update it without locking the entire map as one critical section. I still do not treat a check-then-act sequence as atomic unless I use a method that does both, such as `compute` or `putIfAbsent`.

```java
map.compute(key, (k, existing) -> update(existing));
```

### `CopyOnWriteArrayList`

The list copies its array on each write. Readers do not lock. Use it when reads are constant and writes are rare: listener lists, rarely changing config. A frequently updated list pays too much for the copy.

### Queues

| | `BlockingQueue` | `ConcurrentLinkedQueue` |
| :--- | :--- | :--- |
| Typical call | `put` / `take` | `offer` / `poll` |
| Empty queue | `take` waits | `poll` returns immediately |
| Fit | producer-consumer handoff | many threads, no blocking wait |

`ArrayBlockingQueue` is bounded, so a full queue pushes back on the producer. `LinkedBlockingQueue` can be bounded or unbounded. An unbounded queue hides overload until memory runs out.

### `AtomicInteger` vs `LongAdder` vs a lock

`incrementAndGet()` is enough for one counter. It is not enough for a business action made of several steps (check balance, update balance, write the ledger). Those steps need one lock or one atomic method around the whole action.

`LongAdder` spreads a hot increment across cells and sums them on read. It is the better counter when many threads increment the same metric.

---

## 7. Virtual threads

This is the Hong Leong chapter. The interviewer will start with the definition and then ask about carrier threads, CPU-bound work, JDBC, and pinning.

### What a virtual thread is

**Interview answer:**

> A virtual thread is a lightweight thread managed by the JVM. It is not tied one-to-one to an operating-system thread. Many virtual threads run on a smaller set of carrier platform threads. That makes a thread-per-task style practical for high-concurrency I/O. Virtual threads do not add CPU, database connections, or API quota. Those limits still need their own controls.

```
Platform thread
  Java thread -> OS thread

Virtual thread
  Virtual thread -> carrier platform thread -> OS thread
```

### Why they exist

A typical request does a little CPU work and then waits:

```
Request -> database wait -> HTTP wait -> response
```

A platform thread blocked in that wait still occupies an OS thread. A virtual thread blocked in supported I/O can unmount, and the carrier runs something else.

```
10,000 requests
  platform threads:  10,000 OS threads is expensive
  virtual threads:   10,000 lightweight threads, few carriers
```

They were built for that waiting, not to make encryption, image processing, or other CPU-bound work faster.

### Platform vs virtual

| | Platform thread | Virtual thread |
| :--- | :--- | :--- |
| Who schedules it onto the OS | The OS, one thread per platform thread | The JVM, via a carrier |
| Cost | Relatively high | Very low |
| Practical count | Hundreds, sometimes low thousands | Vastly higher, workload permitting |
| Best fit | CPU-bound work, a small pool | High-concurrency blocking I/O |
| Mental model | Task -> thread -> OS | Task -> virtual thread -> carrier -> OS |

### How to create one

```java
Thread.startVirtualThread(() -> handle(request));

Thread.ofVirtual().name("order-", 0).start(() -> handle(request));

try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
    executor.submit(() -> callApi());
    executor.submit(() -> callDatabase());
}
```

`newVirtualThreadPerTaskExecutor()` starts a new virtual thread per task. It is not a pool of 10 reusable virtual threads with a queue. If a resource is scarce, limit that resource (connection pool, semaphore, rate limiter). Do not fake a tiny virtual-thread pool just to copy the platform-thread habit.

### Carrier, mount, unmount

The carrier is the platform thread currently running the virtual thread.

```
Virtual thread runs on a carrier
        |
   blocking I/O the JVM can park
        |
   virtual thread unmounts
        |
   carrier runs another virtual thread
        |
   I/O completes, virtual thread mounts again and continues
```

That unmount is the benefit. A platform thread waiting on I/O holds the OS thread the whole time.

### CPU-bound vs I/O-bound

```
CPU-bound:   request -> heavy calculation -> heavy calculation
I/O-bound:   request -> DB wait -> HTTP wait -> response
```

**Interview answer:** Virtual threads are attractive when waiting is the bottleneck. When the CPU is the bottleneck, I size parallelism to the cores and I profile. A million virtual threads do not add cores.

Shortcut: **CPU-bound means the processor is the limit. I/O-bound means waiting is the limit.**

### They do not remove downstream limits

```
100,000 virtual threads
        |
   connection pool = 50
        |
   at most ~50 queries actually run
```

The rest wait for a connection. The same is true for API rate limits, CPU, and bandwidth.

**Interview answer:** Virtual threads make high concurrency cheaper. They do not raise database capacity or a partner’s rate limit, so I still cap in-flight work, set timeouts, and apply backpressure.

### JDBC and Spring Boot

Blocking JDBC on a virtual thread is a normal design on modern JDKs. The connection pool remains the limit.

Spring Boot 3.2 and later can run supported server and task execution on virtual threads:

```properties
spring.threads.virtual.enabled=true
```

**Interview answer:** I would turn that on for an I/O-heavy Spring service, then check the pool size, downstream timeouts, and CPU before calling it done.

### Pinning

Pinning means the virtual thread cannot unmount, so the carrier stays occupied while that virtual thread is blocked.

```
Normal:   I/O wait -> unmount -> carrier does other work
Pinned:   blocked -> carrier stays occupied
```

On JDK 21 through 23, blocking inside `synchronized` pins the carrier. A long HTTP or JDBC call inside `synchronized` can pin many carriers and erase the scalability win.

JDK 24 fixed that case (JEP 491, *Synchronize Virtual Threads without Pinning*). On JDK 24 and 25, blocking inside `synchronized` no longer pins. Pinning can still happen in native code (JNI and some foreign calls).

**Interview answer:** I keep remote calls outside the critical section. On JDK 21 I also treat `synchronized` around a blocking call as a pinning risk. On JDK 24+ that monitor case is fixed, and I still watch native frames. `ReentrantLock` was the usual workaround on JDK 21 because parking on a `Lock` can unmount.

```java
Result result = callExternalApi(); // outside the lock
synchronized (lock) {
    updateSharedState(result);
}
```

`synchronized` itself is fine. The problem is a long block while the carrier cannot be released.

### Virtual threads, `CompletableFuture`, and reactive

These solve different problems and can sit in the same system.

- **Virtual threads:** straight blocking code at high I/O concurrency.
- **`CompletableFuture`:** compose, combine, and handle errors across async steps.
- **Reactive (`Mono` / `Flux`):** a non-blocking pipeline, useful for streaming and for stacks that are already reactive.

A fair sentence: virtual threads make the simple blocking style scalable. They do not retire reactive programming.

### “Can I create a million?”

Virtual threads are cheap relative to platform threads. They are not free. A million tasks still use memory, and any of them that become runnable use CPU. A million virtual threads are not a million simultaneous database queries.

### Scenarios to practice

**20,000 HTTP requests, each calling a database and an external API.**

> Yes for virtual threads, because the work is mostly waiting. I would still cap concurrency against the connection pool and the API limit, and I would set timeouts, retries, and a circuit breaker.

**Would virtual threads speed up image processing?**

> Only if the current limit is thread overhead, which it usually is not. Image processing is CPU-bound. I would match parallelism to the cores and profile.

**Why not a pool of 100 virtual threads?**

> The useful model is a virtual thread per task. I limit the scarce resource, not the virtual threads, unless I am deliberately bounding a specific workload.

**60-second version** if they only say “explain virtual threads”:

> Virtual threads are lightweight JVM-managed threads for high-concurrency I/O. Many of them run on a few carrier platform threads. On a blocking call the JVM can park the virtual thread and let the carrier run other work, so thread-per-request stays viable. They do not make CPU-bound work faster, and they do not remove connection-pool or rate-limit ceilings.

### Fifteen questions

1. What is a virtual thread?
2. Why were they introduced?
3. Platform thread vs virtual thread?
4. What is a carrier thread?
5. What are mount and unmount?
6. Why are they a good fit for I/O?
7. Are they faster than platform threads?
8. Are they useful for CPU-bound work?
9. How do you create one, and what does `newVirtualThreadPerTaskExecutor` do?
10. Can JDBC run on virtual threads? (Yes. The pool is still the limit.)
11. How does Spring Boot enable them? (`spring.threads.virtual.enabled=true`, Boot 3.2+.)
12. What is pinning, and what changed in JDK 24?
13. Do they remove the need for concurrency limits? (No.)
14. How do they relate to `CompletableFuture`?
15. When would you leave them aside? (CPU-bound work, or a downstream limit that already decides throughput.)

---

## 8. JVM memory

```
JVM
 ├── Heap
 │    ├── Young generation
 │    │    ├── Eden
 │    │    └── Survivor spaces
 │    └── Old generation
 ├── Metaspace          class metadata, native memory
 ├── Stack              one per thread
 └── Other native memory (thread stacks, direct buffers, code cache)
```

**Interview answer:** The heap is shared and holds objects. Each thread has its own stack for frames and local variables. Metaspace holds class metadata in native memory. Heap exhaustion shows up as `OutOfMemoryError`. Deep or infinite recursion shows up as `StackOverflowError`.

```java
public void test() {
    int x = 10;            // lives in the frame
    User user = new User(); // reference in the frame, object on the heap
}
```

```
Stack frame
  x = 10
  user  ──────────►  Heap: User
```

Each thread needs its own stack because each thread has its own call chain. A frame is pushed on the call and popped on the return.

```
Top
┌───────────┐
│ methodC() │
├───────────┤
│ methodB() │
├───────────┤
│ methodA() │
└───────────┘
Bottom
```

When the method returns, the frame is gone. The `User` object remains as long as some GC root can still reach it. If nothing can, it is **eligible** for collection. Eligible means the collector may reclaim it later. It does not mean the memory is freed on that line.

```java
user = null; // eligible if this was the last reference; not an instant free
```

### `StackOverflowError` vs `OutOfMemoryError`

| | Usual cause | Typical trigger |
| :--- | :--- | :--- |
| `StackOverflowError` | That thread’s stack is full | unbounded recursion |
| `OutOfMemoryError: Java heap space` | The heap cannot satisfy an allocation | objects kept alive until the heap fills |
| `OutOfMemoryError: Metaspace` | Too much class metadata | classloader leak, huge generated-class volume |

Java application code does not call `free()` on ordinary objects. The collector reclaims unreachable objects. You still close connections, streams, and sockets yourself. GC does not replace `try-with-resources`.

```java
try (Connection connection = dataSource.getConnection()) {
    // use connection
}
```

---

## 9. Garbage collection

The collector finds objects that are no longer reachable from a GC root and reclaims their memory.

GC roots include references on active thread stacks, static fields, and JNI references. Anything reachable from a root stays. Anything that is not reachable is eligible.

```
GC roots -> reachable objects stay
         -> unreachable objects become eligible
         -> a later GC cycle reclaims them
```

### Generations

Most objects die young. Collectors use that fact.

```
new object -> Eden
     |
  minor GC, still alive -> Survivor
     |
  survives long enough -> promoted to Old
```

| Term | Meaning at interview level |
| :--- | :--- |
| Minor GC | Collects the young generation. Frequent, usually short. |
| Major GC | Collects the old generation. The word is used loosely. |
| Full GC | Collects a much wider part of the heap, often the whole heap, and can pause the application for a long time. |
| Promotion | A surviving object moves from young to old. |
| Stop-the-world | Application threads pause for a GC phase. |

Exact pause behavior depends on the collector. G1 and ZGC do a lot of work while the application runs. Some phases are still stop-the-world. The accurate line is: **some phases pause threads; modern collectors try to keep those pauses short.**

### Collectors worth naming

| Collector | What to say |
| :--- | :--- |
| Serial | One thread collects. Fine for small heaps and tiny apps. |
| Parallel | Many GC threads, high throughput, longer pauses. The old throughput collector. |
| G1 | Default since Java 9. Heap is split into regions. It prefers regions with the most garbage and aims at a pause goal (`MaxGCPauseMillis`). The usual production answer. |
| ZGC | Concurrent, low-pause collector for large heaps and latency-sensitive services. Generational ZGC is the modern form. Choose it when pause time matters more than squeezing out the last bit of throughput. |
| Shenandoah | Also a low-pause concurrent collector. Less common in interviews than G1 vs ZGC. |

**G1 in one sentence:** G1 divides the heap into regions and collects the ones that will return the most garbage, while trying to stay inside a pause-time target.

**G1 vs ZGC:** G1 is the default and a strong general choice. ZGC is the one to discuss when the requirement is very short pauses on a large heap.

### `System.gc()`

`System.gc()` asks the JVM to consider a collection. The JVM may ignore it. Production JVMs often disable explicit GC with `-XX:+DisableExplicitGC`.

### Memory leaks in Java

GC reclaims unreachable objects. A leak in Java is an object the program no longer needs that is still reachable, so the collector must keep it.

```java
static final List<byte[]> CACHE = new ArrayList<>();

void process() {
    CACHE.add(new byte[1024 * 1024]); // reachable forever, heap grows
}
```

Causes that come up in interviews:

1. Static or unbounded caches.
2. A list that only grows.
3. Listeners or callbacks never removed.
4. `ThreadLocal` left on a pooled thread, or a large value stored in one.
5. A classloader that cannot be unloaded because something still references its classes (Metaspace growth).

**Interview answer:** A cache without a size or TTL is the example I give. The objects are useless to the business and still reachable from a static field, so GC cannot take them.

### Troubleshooting a GC or heap problem

Do not stop at “I would check GC.” Walk the evidence.

```
Symptom: latency, CPU, or memory
    -> JVM metrics and GC logs
    -> GC frequency, pause time, heap after each cycle
    -> allocation rate
    -> if the heap never comes back down: heap dump
    -> what retains the objects, and which GC root holds them
```

Signs GC is involved:

- Pause time lines up with latency spikes.
- GC runs constantly and CPU spent in GC is high.
- The old generation climbs and does not drop after collection.
- Allocation rate is huge because the code creates short-lived objects in a tight loop.

Signs of a leak rather than a tuning problem:

- After a full collection, used heap stays high and the floor keeps rising.
- The dump shows one collection, cache, or classloader dominating retained size.

Useful tools:

| Tool | What you open it for |
| :--- | :--- |
| GC logs (`-Xlog:gc*`) | frequency, pause, heap before and after |
| `jcmd <pid> GC.heap_info` / `Thread.print` | live heap summary, thread dump |
| `jstack` | thread dump, including “Found one Java-level deadlock” |
| JFR (Java Flight Recorder) | low-overhead profile of CPU, allocation, locks, and GC over time |
| Heap dump (Eclipse MAT or VisualVM) | which objects are retained, and the path from a GC root |

Thread-dump states to read out loud: `RUNNABLE`, `BLOCKED`, `WAITING`, `TIMED_WAITING`. Many threads `BLOCKED` on one monitor is contention. Two threads `BLOCKED` on each other’s locks is deadlock. A pool whose workers are all stuck in JDBC is a downstream wait, not a reason to add threads.

---

## 10. Scenarios

### 100 threads increment one counter

`AtomicInteger` for a plain counter. `LongAdder` if the increment itself is heavily contended. A lock if the increment is part of a larger check-then-update.

### 10,000 calls waiting on external APIs

Virtual threads fit. Also set the API’s rate limit, timeouts, and a limit on in-flight calls. Extra virtual threads past the rate limit only increase the wait queue.

### The pool has 100 threads and the app is still slow

Adding threads is the last guess. Check, in roughly this order: downstream latency, connection-pool exhaustion, lock contention, a full queue, CPU saturation, GC pauses. The pool is often waiting on something else.

### The queue grows without bound

Arrival rate is above completion rate. Look at processing time and the downstream system. Then choose among more consumers, backpressure, a smaller prefetch, batching, or rate limiting. A bigger unbounded queue only moves the failure into memory.

### `CompletableFuture` work is slow

Ask which executor is running it. Blocking I/O on the common `ForkJoinPool` starves other tasks. Move blocking work to an executor sized for that I/O, and check the remote service’s latency.

### 1,000 virtual threads, 20 database connections

That is a valid shape. About 20 database calls run at once. The other virtual threads wait for a connection. Virtual threads did not expand the database.

### Latency jumped. Is it GC?

It might be. Compare pause times with the latency spike before deciding. Also look at heap occupancy and allocation rate. A slow database produces the same symptom with a quiet GC log.

### Heap grows even though GC runs

Objects are still reachable. Inspect static collections, caches, `ThreadLocal`, listener lists, and classloaders. GC cannot collect a live object.

### Why not synchronize every method?

Every extra monitor is a place threads wait. Synchronize the shared mutable state that actually needs it, and keep that section short.

### Your notification service

The service is a high-throughput WhatsApp notification path: messages come from a broker, and the slow part is the call to the provider (Infobip-style HTTP), plus retries. That is I/O-bound waiting, which is the workload virtual threads are for.

> It depends where time goes. The provider call is mostly waiting, so virtual threads are a good candidate for the consumer workers. I would not flip the executor and stop there. The provider has a rate limit, the HTTP client has a connection pool, and retries can amplify load. I would cap in-flight calls, keep timeouts, a circuit breaker, and idempotent sends, and I would benchmark against the current pool. The broker already gives me a buffer. If the consumer cannot keep up, the queue depth tells me, and I scale consumers only up to what the provider accepts.

Pieces to mention if they keep pulling the thread:

- Why a queue: absorb bursts, decouple the API from the provider.
- How many consumers: enough to fill the provider’s allowed rate, not one per message in the backlog.
- Backpressure: bounded prefetch, or stop pulling when in-flight work hits the cap.
- Retry: limited attempts, backoff, a dead-letter path. Retry storms are how a slow provider becomes an outage.
- Idempotency: a retry must not send the same WhatsApp twice.
- Slow provider: timeout, circuit breaker, queue growth as the signal. Virtual threads wait cheaply; they do not make the provider faster.
- CPU vs I/O: templating and JSON are cheap next to the HTTP wait. If CPU shows up in the profile, that changes the answer.
- What you would watch: queue depth, in-flight calls, provider latency, error rate, connection-pool usage, GC pause.

---

## 11. Traps, short answers

| Question | Answer |
| :--- | :--- |
| Does `run()` start a thread? | No. `start()` does. `run()` is a normal call. |
| Can you `start()` twice? | No. `IllegalThreadStateException`. |
| Does `sleep` release the lock? | No. |
| Does `wait` release the lock? | Yes, and it needs to own the monitor. |
| Does `interrupt` kill a thread? | No. The thread cooperates. |
| Does `volatile` make `count++` safe? | No. Visibility only. Use an atomic or a lock. |
| What does `synchronized` guarantee? | Mutual exclusion and visibility for that monitor. |
| Which lock does a static synchronized method take? | The `Class` object, not `this`. |
| Can two threads run the same synchronized instance method? | Not on the same instance. Yes on two instances. |
| Why is `unlock` in `finally`? | So an exception still releases the lock. |
| Deadlock vs livelock? | Stuck and waiting, vs running and still not progressing. |
| Does task 11 create thread 11? | Not while the queue has space. Threads above core appear after the queue fills. |
| What is `CallerRunsPolicy`? | The caller runs the task. That slows intake. |
| Does `shutdownNow` kill tasks instantly? | No. It interrupts them. |
| `thenApply` vs `thenCompose`? | Value in, value out. Vs value in, future out, flattened. |
| `allOf` vs `anyOf`? | Every future, vs the first one. |
| Which pool does `supplyAsync()` use? | `ForkJoinPool.commonPool()`, unless you pass an executor. |
| Does `ConcurrentHashMap` lock the whole map? | No. Its design lets updates proceed concurrently. Compound actions still need `compute` or equivalent. |
| Does `AtomicInteger` cover a multi-step business update? | No. One atomic variable covers one variable. |
| Are virtual threads always faster? | No. They help when the cost is waiting, not when the CPU is saturated. |
| Does a virtual thread remove the connection pool? | No. |
| Does `null` free the object now? | It drops a reference. Collection happens later, if the object is unreachable. |
| Does GC fix a leak? | It reclaims unreachable objects. A leak is still reachable. |
| Does `System.gc()` force a collection? | It requests one. There is no guarantee. |
| Is every GC a full stop of the application? | Some phases pause threads. G1 and ZGC do much of the work concurrently. |

---

## Night before

If you only revise twenty:

1. `start()` vs `run()`.
2. `sleep()` vs `wait()`.
3. `wait()` in a loop, and `notify` vs `notifyAll`.
4. What a race condition is.
5. What `volatile` guarantees.
6. Why `volatile count++` loses updates.
7. What `synchronized` guarantees, and which object it locks.
8. `synchronized` vs `ReentrantLock`.
9. Deadlock and one way you prevent it (lock order).
10. `corePoolSize`, queue, then `maxPoolSize`.
11. What happens when the queue is full.
12. `execute` vs `submit`.
13. CPU-bound pool vs I/O-bound pool.
14. `thenApply` vs `thenCompose`.
15. `thenCombine`, `allOf`, `anyOf`.
16. Why `ConcurrentHashMap` instead of `HashMap`.
17. `AtomicInteger` vs `LongAdder` vs a lock.
18. Virtual thread, carrier, mount, unmount.
19. Virtual threads plus a connection pool and a rate limit.
20. Heap vs stack, minor vs full GC, leak vs unreachable, `System.gc()`.

The sentence to walk in with:

> I match the tool to the bottleneck. Shared mutable state gets a lock or an atomic. A lot of tasks get a bounded pool. I/O at high concurrency can get a virtual thread per task, with the real limit set on the database or the API. If latency moves, I check pauses and the heap before I add threads.
