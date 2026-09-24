# EPAM Interview Preparation — Part 1: Core Java (20 Coding Problems)

> **Focus Areas:** Java Collections, Java 8+ Streams API, Concurrency, OOP Concepts, and High-Performance Design Patterns.

---

## 01. First Non-Repeating Character

### Problem Statement
Given a String, find the first character that occurs only once.

- **Input:** `"swiss"` -> **Output:** `'w'`
- **Input:** `"racecar"` -> **Output:** `'e'`

### 1. Traditional / Loops Approach (Using LinkedHashMap or Array)
```java
public static Character findFirstNonRepeatingBrute(String str) {
    if (str == null || str.isEmpty()) return null;
    
    // LinkedHashMap preserves insertion order
    Map<Character, Integer> counts = new LinkedHashMap<>();
    
    for (char ch : str.toCharArray()) {
        counts.put(ch, counts.getOrDefault(ch, 0) + 1);
    }
    
    for (Map.Entry<Character, Integer> entry : counts.entrySet()) {
        if (entry.getValue() == 1) {
            return entry.getKey();
        }
    }
    return null;
}
```

### 2. Modern Java Streams Approach
```java
public static Character findFirstNonRepeatingStream(String str) {
    if (str == null || str.isEmpty()) return null;

    return str.chars()
            .mapToObj(c -> (char) c)
            .collect(Collectors.groupingBy(
                    Function.identity(),
                    LinkedHashMap::new, // Preserves character order
                    Collectors.counting()
            ))
            .entrySet().stream()
            .filter(entry -> entry.getValue() == 1)
            .map(Map.Entry::getKey)
            .findFirst()
            .orElse(null);
}
```

- **Time Complexity:** $O(n)$ where $n$ is string length.
- **Space Complexity:** $O(k)$ where $k$ is unique characters ($O(1)$ for ASCII/fixed alphabet).
- **Key Concepts:** `LinkedHashMap`, `Collectors.groupingBy`, `Function.identity()`.

---

## 02. Character Frequency Count

### Problem Statement
Count the frequency of every character in a String.

- **Input:** `"banana"` -> **Output:** `{b=1, a=3, n=2}`

### Solution (For-Loop vs Java Stream)
```java
// Option A: For-Loop with Map
public static Map<Character, Integer> countFrequencyLoop(String str) {
    Map<Character, Integer> freqMap = new HashMap<>();
    for (char c : str.toCharArray()) {
        freqMap.put(c, freqMap.getOrDefault(c, 0) + 1);
    }
    return freqMap;
}

// Option B: Java 8 Streams
public static Map<Character, Long> countFrequencyStream(String str) {
    return str.chars()
            .mapToObj(c -> (char) c)
            .collect(Collectors.groupingBy(
                    Function.identity(),
                    Collectors.counting()
            ));
}
```

---

## 03. Remove Duplicate Elements from List

### Problem Statement
Remove duplicates from a list while maintaining or ignoring order.

- **Input:** `[1, 2, 2, 3, 3, 4]` -> **Output:** `[1, 2, 3, 4]`

### Solutions
```java
// 1. LinkedHashSet (Preserves Insertion Order)
List<Integer> list = List.of(1, 2, 2, 3, 3, 4);
List<Integer> uniqueListOrder = new ArrayList<>(new LinkedHashSet<>(list));

// 2. Streams distinct()
List<Integer> uniqueListStream = list.stream()
        .distinct()
        .collect(Collectors.toList());
```

- **Difference:** `HashSet` ignores order ($O(n)$ space), `LinkedHashSet` preserves order ($O(n)$ space), `Stream.distinct()` uses `HashSet` internally.

---

## 04. Find Duplicate Elements in a List

### Problem Statement
Identify elements that appear more than once.

- **Input:** `[1, 2, 3, 2, 4, 3, 5]` -> **Output:** `[2, 3]`

### Solutions
```java
// Option 1: HashSet add() trick (Set.add returns false if element already present)
public static Set<Integer> findDuplicatesHashSet(List<Integer> list) {
    Set<Integer> seen = new HashSet<>();
    Set<Integer> duplicates = new HashSet<>();
    for (Integer num : list) {
        if (!seen.add(num)) {
            duplicates.add(num);
        }
    }
    return duplicates;
}

// Option 2: Streams + Set.add
public static Set<Integer> findDuplicatesStream(List<Integer> list) {
    Set<Integer> seen = new HashSet<>();
    return list.stream()
            .filter(n -> !seen.add(n))
            .collect(Collectors.toSet());
}
```

---

## 05. Sort Employees by Salary

### Model Class
```java
class Employee {
    private String name;
    private double salary;

    public Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }
    public String getName() { return name; }
    public double getSalary() { return salary; }
}
```

### Solutions
```java
List<Employee> employees = getEmployees();

// Ascending Order by Salary
List<Employee> sortedAsc = employees.stream()
        .sorted(Comparator.comparingDouble(Employee::getSalary))
        .collect(Collectors.toList());

// Descending Order by Salary (Then by Name if salaries equal)
List<Employee> sortedDesc = employees.stream()
        .sorted(Comparator.comparingDouble(Employee::getSalary).reversed()
                .thenComparing(Employee::getName))
        .collect(Collectors.toList());
```

---

## 06. Find Second Highest Salary

### Problem Statement
Find the 2nd highest **distinct** salary from an employee list.

### Solution
```java
public static Optional<Double> getSecondHighestSalary(List<Employee> employees) {
    return employees.stream()
            .map(Employee::getSalary)
            .distinct() // Ensure distinct salaries
            .sorted(Comparator.reverseOrder())
            .skip(1) // Skip highest salary
            .findFirst();
}
```

---

## 07. Group Employees by Department

### Problem Statement
Group employees by department name into a `Map<String, List<Employee>>`.

### Solution
```java
class EmployeeWithDept {
    String name;
    String department;
    double salary;
    // Getters & Constructors...
}

Map<String, List<EmployeeWithDept>> empByDept = employees.stream()
        .collect(Collectors.groupingBy(EmployeeWithDept::getDepartment));
```

---

## 08. Count Employees by Department

### Solution
```java
Map<String, Long> countByDept = employees.stream()
        .collect(Collectors.groupingBy(
                EmployeeWithDept::getDepartment,
                Collectors.counting()
        ));
```

---

## 09. Find Highest Paid Employee per Department

### Solution
```java
Map<String, Optional<EmployeeWithDept>> topPaidPerDept = employees.stream()
        .collect(Collectors.groupingBy(
                EmployeeWithDept::getDepartment,
                Collectors.maxBy(Comparator.comparingDouble(EmployeeWithDept::getSalary))
        ));

// Unwrap Optional using collectingAndThen
Map<String, EmployeeWithDept> topPaidClean = employees.stream()
        .collect(Collectors.groupingBy(
                EmployeeWithDept::getDepartment,
                Collectors.collectingAndThen(
                        Collectors.maxBy(Comparator.comparingDouble(EmployeeWithDept::getSalary)),
                        Optional::get
                )
        ));
```

---

## 10. Partition Employees by Salary Threshold

### Solution
```java
// Split into two groups: High earners (>= 5000) and Normal earners (< 5000)
Map<Boolean, List<EmployeeWithDept>> partitioned = employees.stream()
        .collect(Collectors.partitioningBy(e -> e.getSalary() >= 5000));

List<EmployeeWithDept> highEarners = partitioned.get(true);
List<EmployeeWithDept> lowEarners = partitioned.get(false);
```

---

## 11. Flatten Nested Lists (`flatMap`)

### Problem Statement
Flatten `List<List<Integer>>` into `List<Integer>`.

### Solution
```java
List<List<Integer>> nestedList = List.of(
    List.of(1, 2, 3),
    List.of(4, 5),
    List.of(6, 7)
);

List<Integer> flatList = nestedList.stream()
        .flatMap(List::stream)
        .collect(Collectors.toList());
// Result: [1, 2, 3, 4, 5, 6, 7]
```

---

## 12. Find Common Elements Between Two Lists

### Solution
```java
List<Integer> listA = List.of(1, 2, 3, 4);
List<Integer> listB = List.of(3, 4, 5, 6);

// Option 1: HashSet filter (O(N + M) - Optimal)
Set<Integer> setB = new HashSet<>(listB);
List<Integer> commonStream = listA.stream()
        .filter(setB::contains)
        .collect(Collectors.toList());

// Option 2: Collection retainAll
List<Integer> commonRetain = new ArrayList<>(listA);
commonRetain.retainAll(listB);
```

---

## 13. Create an Immutable Class

### Guidelines for Java Immutability
1. Class must be `final` (prevents subclassing).
2. Fields must be `private final`.
3. No setter methods.
4. Perform **defensive copies** for mutable fields (e.g., `Date`, `List`, custom objects) in constructor and getters.

### Implementation
```java
public final class ImmutableEmployee {
    private final int id;
    private final String name;
    private final List<String> skills; // Mutable object!

    public ImmutableEmployee(int id, String name, List<String> skills) {
        this.id = id;
        this.name = name;
        // Defensive Copy in Constructor
        this.skills = (skills == null) ? Collections.emptyList() : new ArrayList<>(skills);
    }

    public int getId() { return id; }
    public String getName() { return name; }

    // Defensive Copy in Getter
    public List<String> getSkills() {
        return new ArrayList<>(skills); // Return copy, not internal reference
    }
}
```

---

## 14. Custom Class as HashMap Key (`equals` & `hashCode`)

### Requirements
- Override both `equals(Object o)` and `hashCode()`.
- Contract: If `a.equals(b)` is true, `a.hashCode() == b.hashCode()` MUST be true.
- Keys should preferably be **immutable**.

### Implementation
```java
public final class EmployeeKey {
    private final int id;
    private final String department;

    public EmployeeKey(int id, String department) {
        this.id = id;
        this.department = department;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeKey that = (EmployeeKey) o;
        return id == that.id && Objects.equals(department, that.department);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, department);
    }
}
```

---

## 15. Thread-Safe Counter

### Implementations Comparison
```java
// 1. AtomicInteger (Lock-Free / CAS - Best Performance for single variable)
class AtomicCounter {
    private final AtomicInteger count = new AtomicInteger(0);
    public void increment() { count.incrementAndGet(); }
    public int getCount() { return count.get(); }
}

// 2. Synchronized Block/Method (Intrinsic Locking)
class SyncCounter {
    private int count = 0;
    public synchronized void increment() { count++; }
    public synchronized int getCount() { return count; }
}

// 3. ReentrantLock (Explicit Lock)
class LockCounter {
    private int count = 0;
    private final ReentrantLock lock = new ReentrantLock();
    public void increment() {
        lock.lock();
        try { count++; }
        finally { lock.unlock(); }
    }
}
```

---

## 16. Producer-Consumer Problem

### Solution using `ArrayBlockingQueue`
```java
class ProducerConsumerDemo {
    private static final BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(5);

    public static void main(String[] args) {
        // Producer Thread
        Thread producer = new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    queue.put(i); // Blocks if queue is full
                    System.out.println("Produced: " + i);
                    Thread.sleep(100);
                } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            }
        });

        // Consumer Thread
        Thread consumer = new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    int val = queue.take(); // Blocks if queue is empty
                    System.out.println("Consumed: " + val);
                    Thread.sleep(200);
                } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            }
        });

        producer.start();
        consumer.start();
    }
}
```

---

## 17. Concurrent API Calls with `CompletableFuture`

### Scenario
Fetch user profile (2s) and order history (3s) concurrently, then combine results.

```java
public class CompletableFutureDemo {
    public static void main(String[] args) {
        CompletableFuture<String> userTask = CompletableFuture.supplyAsync(() -> {
            simulateDelay(2000);
            return "User Profile";
        });

        CompletableFuture<String> orderTask = CompletableFuture.supplyAsync(() -> {
            simulateDelay(3000);
            return "Order History";
        });

        // Combine both concurrent tasks
        CompletableFuture<String> combined = userTask.thenCombine(orderTask, 
                (user, order) -> user + " + " + order);

        System.out.println("Result: " + combined.join()); // Total time ~3s, not 5s!
    }

    private static void simulateDelay(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException e) { }
    }
}
```

---

## 18. Retry Mechanism with Exponential Backoff

```java
public class RetryUtils {
    public static <T> T executeWithRetry(Supplier<T> supplier, int maxRetries, long initialDelayMs) {
        int attempts = 0;
        long delay = initialDelayMs;

        while (attempts < maxRetries) {
            try {
                return supplier.get(); // Attempt call
            } catch (Exception e) {
                attempts++;
                if (attempts >= maxRetries) {
                    throw new RuntimeException("Failed after " + maxRetries + " attempts", e);
                }
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(ie);
                }
                delay *= 2; // Exponential Backoff
            }
        }
        throw new RuntimeException("Execution failed.");
    }
}
```

---

## 19. Simple LRU Cache

### Solution using `LinkedHashMap`
```java
public class LRUCache<K, V> extends LinkedHashMap<K, V> {
    private final int capacity;

    public LRUCache(int capacity) {
        // accessOrder = true means ordered by last access, false = insertion order
        super(capacity, 0.75f, true);
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > capacity; // Remove least recently used element when full
    }
}
```

---

## 20. Thread-Safe Singleton Implementations

```java
// 1. Bill Pugh Singleton (Lazy + Thread-Safe - Recommended)
public class SingletonBillPugh {
    private SingletonBillPugh() {}

    private static class Holder {
        private static final SingletonBillPugh INSTANCE = new SingletonBillPugh();
    }

    public static SingletonBillPugh getInstance() {
        return Holder.INSTANCE;
    }
}

// 2. Double-Checked Locking (Requires volatile)
public class SingletonDoubleChecked {
    private static volatile SingletonDoubleChecked instance;

    private SingletonDoubleChecked() {}

    public static SingletonDoubleChecked getInstance() {
        if (instance == null) {
            synchronized (SingletonDoubleChecked.class) {
                if (instance == null) {
                    instance = new SingletonDoubleChecked();
                }
            }
        }
        return instance;
    }
}

// 3. Enum Singleton (Safest against Reflection & Serialization)
public enum EnumSingleton {
    INSTANCE;
    public void doSomething() {}
}
```
