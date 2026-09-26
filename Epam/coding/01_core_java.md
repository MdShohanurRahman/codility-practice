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
    private String department;
    private double salary;

    public Employee(String name, String department, double salary) {
        this.name = name;
        this.department = department;
        this.salary = salary;
    }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public double getSalary() { return salary; }

    @Override
    public String toString() {
        return name + " (" + department + ", $" + salary + ")";
    }
}
```

### Sample Input Data
```java
List<Employee> employees = List.of(
    new Employee("Alice", "IT", 75000.0),
    new Employee("Bob", "HR", 50000.0),
    new Employee("Charlie", "IT", 90000.0),
    new Employee("David", "Finance", 50000.0),
    new Employee("Eve", "HR", 65000.0)
);
```

### Complete Code & Solutions
```java
// 1. Ascending Order by Salary (Tie-breaker by Name)
List<Employee> sortedAsc = employees.stream()
        .sorted(Comparator.comparingDouble(Employee::getSalary)
                .thenComparing(Employee::getName))
        .collect(Collectors.toList());

// 2. Descending Order by Salary (Tie-breaker by Name)
List<Employee> sortedDesc = employees.stream()
        .sorted(Comparator.comparingDouble(Employee::getSalary).reversed()
                .thenComparing(Employee::getName))
        .collect(Collectors.toList());
```

### Sample Output

**Ascending Order Output:**
```text
[Bob (HR, $50000.0), David (Finance, $50000.0), Eve (HR, $65000.0), Alice (IT, $75000.0), Charlie (IT, $90000.0)]
```

**Descending Order Output:**
```text
[Charlie (IT, $90000.0), Alice (IT, $75000.0), Eve (HR, $65000.0), Bob (HR, $50000.0), David (Finance, $50000.0)]
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

**Sample Output:**
```text
Optional[75000.0]
```

---

## 07. Group Employees by Department

### Problem Statement
Group employees by department name into a `Map<String, List<Employee>>`.

### Solution
```java
Map<String, List<Employee>> empByDept = employees.stream()
        .collect(Collectors.groupingBy(Employee::getDepartment));
```

**Sample Output:**
```text
{
  IT=[Alice (IT, $75000.0), Charlie (IT, $90000.0)], 
  HR=[Bob (HR, $50000.0), Eve (HR, $65000.0)], 
  Finance=[David (Finance, $50000.0)]
}
```

---

## 08. Count Employees by Department

### Solution
```java
Map<String, Long> countByDept = employees.stream()
        .collect(Collectors.groupingBy(
                Employee::getDepartment,
                Collectors.counting()
        ));
```

**Sample Output:**
```text
{IT=2, HR=2, Finance=1}
```

---

## 09. Find Highest Paid Employee per Department

### Solution
```java
Map<String, Employee> topPaidPerDept = employees.stream()
        .collect(Collectors.groupingBy(
                Employee::getDepartment,
                Collectors.collectingAndThen(
                        Collectors.maxBy(Comparator.comparingDouble(Employee::getSalary)), // what 
                        Optional::get // then
                )
        ));
```

**Sample Output:**
```text
{
  IT=Charlie (IT, $90000.0), 
  HR=Eve (HR, $65000.0), 
  Finance=David (Finance, $50000.0)
}
```

---

## 10. Partition Employees by Salary Threshold

### Solution
```java
// Split into two groups: High earners (>= 60000) and Normal earners (< 60000)
Map<Boolean, List<Employee>> partitioned = employees.stream()
        .collect(Collectors.partitioningBy(e -> e.getSalary() >= 60000.0));

List<Employee> highEarners = partitioned.get(true);
List<Employee> lowEarners = partitioned.get(false);
```

**Sample Output:**
```text
High Earners (true)  : [Alice (IT, $75000.0), Charlie (IT, $90000.0), Eve (HR, $65000.0)]
Normal Earners (false): [Bob (HR, $50000.0), David (Finance, $50000.0)]
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
        return Collections.unmodifiableList(skills); 
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
**Least Recently Used**

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

---

## 21. Builder Design Pattern (Fluent API & Immutability)

### Problem Statement
Implement a `User` class using the Builder Pattern to create complex immutable objects safely without telescoping constructors.

### Solution
```java
public final class User {
    private final String firstName; // Required
    private final String lastName;  // Required
    private final String email;     // Optional
    private final int age;          // Optional

    private User(UserBuilder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.email = builder.email;
        this.age = builder.age;
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public int getAge() { return age; }

    @Override
    public String toString() {
        return "User{" + firstName + " " + lastName + ", email='" + email + "', age=" + age + '}';
    }

    // Static Inner Builder Class
    public static class UserBuilder {
        private final String firstName; // Required
        private final String lastName;  // Required
        private String email;           // Optional
        private int age;                // Optional

        public UserBuilder(String firstName, String lastName) {
            if (firstName == null || lastName == null) {
                throw new IllegalArgumentException("First name and Last name are required!");
            }
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public UserBuilder email(String email) {
            this.email = email;
            return this; // Fluent API
        }

        public UserBuilder age(int age) {
            this.age = age;
            return this; // Fluent API
        }

        public User build() {
            return new User(this);
        }
    }
}
```

### Usage & Output
```java
User user = new User.UserBuilder("John", "Doe")
        .email("john.doe@example.com")
        .age(30)
        .build();

System.out.println(user);
// Output: User{John Doe, email='john.doe@example.com', age=30}
```

---

## 22. Shallow Copy vs Deep Copy

### Problem Statement
Demonstrate the difference between Shallow Copy and Deep Copy when an object contains a mutable reference (e.g. `Address`).

### Solution
```java
class Address implements Cloneable {
    String city;
    public Address(String city) { this.city = city; }
    
    // Copy Constructor for Address
    public Address(Address other) { this.city = other.city; }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

class Person implements Cloneable {
    String name;
    Address address;

    public Person(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    // 1. Shallow Copy Constructor
    public Person shallowCopy() {
        return new Person(this.name, this.address); // Shares address reference!
    }

    // 2. Deep Copy Constructor (Recommended over clone())
    public Person deepCopy() {
        return new Person(this.name, new Address(this.address)); // Creates new Address object!
    }
}
```

### Demo & Explanation
```java
Address addr = new Address("Dhaka");
Person p1 = new Person("Rahim", addr);

// Shallow Copy Demo
Person shallowP = p1.shallowCopy();
shallowP.address.city = "Chittagong"; 
// p1.address.city also becomes "Chittagong"! (SHARED REFERENCE)

// Deep Copy Demo
Person deepP = p1.deepCopy();
deepP.address.city = "Sylhet"; 
// p1.address.city remains "Chittagong"! (INDEPENDENT OBJECT)
```

---

## 23. Strategy & Factory Pattern (Polymorphism & SOLID)

### Problem Statement
Implement a flexible Payment System where new payment strategies (CreditCard, PayPal, Crypto) can be added without modifying existing processing logic (Open/Closed Principle).

### Solution
```java
// 1. Strategy Interface
public interface PaymentStrategy {
    void pay(double amount);
}

// 2. Concrete Strategies
public class CreditCardPayment implements PaymentStrategy {
    private String cardNumber;
    public CreditCardPayment(String cardNumber) { this.cardNumber = cardNumber; }

    @Override
    public void pay(double amount) {
        System.out.println("Paid $" + amount + " using Credit Card ending in " + cardNumber.substring(cardNumber.length() - 4));
    }
}

public class PaypalPayment implements PaymentStrategy {
    private String email;
    public PaypalPayment(String email) { this.email = email; }

    @Override
    public void pay(double amount) {
        System.out.println("Paid $" + amount + " using PayPal account: " + email);
    }
}

// 3. Factory Class
public class PaymentFactory {
    public static PaymentStrategy getPaymentMethod(String type, String detail) {
        if ("CREDIT".equalsIgnoreCase(type)) {
            return new CreditCardPayment(detail);
        } else if ("PAYPAL".equalsIgnoreCase(type)) {
            return new PaypalPayment(detail);
        }
        throw new IllegalArgumentException("Unknown payment type: " + type);
    }
}
```

### Usage
```java
PaymentStrategy strategy = PaymentFactory.getPaymentMethod("PAYPAL", "user@example.com");
strategy.pay(150.0);
// Output: Paid $150.0 using PayPal account: user@example.com
```

---

## 24. Abstract Class vs Interface (with Java 8+ Default/Static Methods)

### Key Differences Comparison Table

| Feature | Abstract Class | Interface (Java 8+) |
| :--- | :--- | :--- |
| **State / Fields** | Can have instance fields (`private int id;`) | Only `public static final` constants |
| **Constructors** | Can have constructors | No constructors |
| **Multiple Inheritance**| Single class inheritance (`extends`) | Multiple interface implementation (`implements`) |
| **Default Methods** | Regular non-abstract methods | Supported via `default` keyword |

### Demonstration
```java
// Interface with Default & Static Methods
interface Loggable {
    void log(String message); // Abstract

    default void logInfo(String info) {
        log("[INFO]: " + info); // Default method
    }

    static void logGlobal(String sysMsg) {
        System.out.println("[SYSTEM GLOBAL]: " + sysMsg); // Static method
    }
}

// Abstract Class holding Shared State
abstract class BaseEntity {
    private final String id = UUID.randomUUID().toString();
    private final long createdAt = System.currentTimeMillis();

    public String getId() { return id; }
    public long getCreatedAt() { return createdAt; }

    public abstract void process();
}

// Concrete Implementation extending Abstract Class & implementing Interface
class OrderProcessor extends BaseEntity implements Loggable {
    @Override
    public void process() {
        logInfo("Processing order ID: " + getId());
    }

    @Override
    public void log(String message) {
        System.out.println(message);
    }
}
```

---

## 25. Tricky OOP Pitfalls & Edge Cases

This problem covers the 5 most confusing OOP traps that interviewers use to test deep Java runtime knowledge.

---

### 1. Method Hiding vs Method Overriding (`static` Methods)
**Question:** What happens when a subclass defines a `static` method with the exact same signature as a `static` method in the superclass?

```java
class Parent {
    public static void display() { System.out.println("Parent static display"); }
    public void print() { System.out.println("Parent instance print"); }
}

class Child extends Parent {
    public static void display() { System.out.println("Child static display"); } // Method Hiding!
    @Override
    public void print() { System.out.println("Child instance print"); }         // Method Overriding!
}

// Test Code:
Parent p = new Child();
p.display(); // Prints: "Parent static display" (Static Binding based on Reference Type!)
p.print();   // Prints: "Child instance print"  (Dynamic Binding based on Object Type!)
```
- **Rule:** `static` methods **cannot** be overridden; they are **hidden**. Resolution is determined at compile-time by the reference variable type (`Parent`), not the actual instance object (`Child`).

---

### 2. Overloading Ambiguity with `null` Arguments
**Question:** Which overloaded method is called when passing `null`?

```java
public class OverloadTest {
    public static void test(Object o) { System.out.println("Object overload"); }
    public static void test(String s) { System.out.println("String overload"); }
    public static void test(Integer i) { System.out.println("Integer overload"); }

    public static void main(String[] args) {
        // test(null); // COMPILE ERROR! Ambiguous method call (String vs Integer are equally specific)
    }
}

// Fixed Case (Unambiguous inheritance hierarchy):
public class UnambiguousTest {
    public static void show(Object o) { System.out.println("Object"); }
    public static void show(String s) { System.out.println("String"); }

    public static void main(String[] args) {
        show(null); // Prints: "String" (Java chooses the MOST SPECIFIC subtype in hierarchy!)
    }
}
```
- **Rule:** Compiler picks the **most specific subtype** matching the argument. `String` is a subtype of `Object`, so `String` wins. But if two siblings (`String` vs `Integer`) exist, it results in a compile-time ambiguity error.

---

### 3. Fields are NOT Polymorphic (Field Shadowing / Hiding)
**Question:** Does variable access use dynamic method dispatch?

```java
class SuperClass {
    int x = 10;
}

class SubClass extends SuperClass {
    int x = 20; // Shadowing superclass field
}

// Test Code:
SuperClass obj = new SubClass();
System.out.println(obj.x); // Prints: 10 ! (NOT 20)
```
- **Rule:** Polymorphism applies **ONLY to non-static instance methods**, NOT to instance fields or static fields. Field access is resolved at compile time based on the reference type.

---

### 4. Covariant Return Types in Overriding
**Question:** Can an overriding method return a different type than the superclass method?

```java
class A {}
class B extends A {}

class ParentClass {
    public A getObject() { return new A(); }
}

class ChildClass extends ParentClass {
    @Override
    public B getObject() { return new B(); } // Valid! Covariant Return Type
}
```
- **Rule:** Since Java 5, an overriding method is allowed to return a **subtype** (covariant type) of the return type declared in the superclass method.

---

### 5. Overriding & Checked Exception Constraints
**Question:** What exception rules apply when overriding a method?

```java
class ParentService {
    public void execute() throws IOException { }
}

class ChildService1 extends ParentService {
    @Override
    public void execute() throws FileNotFoundException { } // VALID: Subclass exception of IOException
}

class ChildService2 extends ParentService {
    @Override
    public void execute() { } // VALID: Throwing NO exception is allowed
}

/* 
class ChildService3 extends ParentService {
    @Override
    public void execute() throws Exception { } // COMPILE ERROR! Broader checked exception not allowed!
}
*/
```
- **Rule:** An overridden method **cannot** declare new or broader Checked Exceptions than the superclass method. It can only declare the same, narrower (subclass), or no Checked Exceptions. (Unchecked exceptions like `RuntimeException` are ignored by this rule).


