# EPAM Systems — Interview Practice & Revision Dashboard

Welcome to your EPAM Technical Interview Practice Dashboard! Use this checklist to track your practice progress, perform quick revisions, and navigate directly to detailed solutions.

---

## 📂 Quick File Navigation

- ☕ **[Part 1: Core Java Guide (20 Problems)](01_core_java.md)** — Streams, Collections, Concurrency, & Design Patterns.
- ⚡ **[Part 2: DSA Master Guide (15 Problems)](02_dsa.md)** — Detailed Explanations with Brute Force ➔ Optimal Solutions.
- 🗄️ **[Part 3: SQL Queries Guide (15 Problems)](03_sql.md)** — Window Functions, JOINs, CTEs, & Grouping.

---

## 🎯 5-Step Interview Coding Rule
Whenever you practice or explain a problem in an interview, speak in these 5 steps:
1. **Clarify Requirements & Edge Cases** (Null, Empty, Negative, Duplicates, Boundary values).
2. **Propose Brute Force Approach** (Baseline working solution + Time/Space complexity).
3. **Identify the Bottleneck** (Explain why it's slow e.g., $O(N^2)$ nested loop scanning).
4. **Present Optimal Approach** (Apply pattern: HashMap, Two Pointers, Sliding Window, Kadane's, BFS/DFS, Stack).
5. **Analyze Complexity & Write Code** (Verify Time/Space complexity).

---

## ☕ Core Java Practice Checklist (20 Problems)

| Status | # | Problem | Key Concept | Priority |
| :---: | :---: | :--- | :--- | :---: |
| [ ] | 01 | [First Non-Repeating Character](01_core_java.md#01-first-non-repeating-character) | `LinkedHashMap`, `groupingBy` | 🔴 High |
| [ ] | 02 | [Character Frequency Count](01_core_java.md#02-character-frequency-count) | Streams, `Collectors.counting()` | 🟡 Med |
| [ ] | 03 | [Remove Duplicate Elements](01_core_java.md#03-remove-duplicate-elements-from-list) | `LinkedHashSet`, `distinct()` | 🔴 High |
| [ ] | 04 | [Find Duplicate Elements](01_core_java.md#04-find-duplicate-elements-in-a-list) | `HashSet.add()` filtering | 🔴 High |
| [ ] | 05 | [Sort Employees by Salary](01_core_java.md#05-sort-employees-by-salary) | `Comparator.comparingDouble` | 🔴 High |
| [ ] | 06 | [Find Second Highest Salary](01_core_java.md#06-find-second-highest-salary) | `distinct()`, `skip(1)` | 🔴 High |
| [ ] | 07 | [Group Employees by Dept](01_core_java.md#07-group-employees-by-department) | `Collectors.groupingBy` | 🔴 High |
| [ ] | 08 | [Count Employees by Dept](01_core_java.md#08-count-employees-by-department) | `groupingBy` + `counting` | 🟡 Med |
| [ ] | 09 | [Highest Paid Employee per Dept](01_core_java.md#09-find-highest-paid-employee-per-department) | `groupingBy` + `maxBy` | 🔴 High |
| [ ] | 10 | [Partition Employees by Salary](01_core_java.md#10-partition-employees-by-salary-threshold) | `Collectors.partitioningBy` | 🟡 Med |
| [ ] | 11 | [Flatten Nested Lists](01_core_java.md#11-flatten-nested-lists-flatmap) | `Stream.flatMap()` | 🔴 High |
| [ ] | 12 | [Find Common Elements](01_core_java.md#12-find-common-elements-between-two-lists) | `HashSet` filter / `retainAll` | 🟡 Med |
| [ ] | 13 | [Create Immutable Class](01_core_java.md#13-create-an-immutable-class) | Defensive Copies, `final` class | 🔴 High |
| [ ] | 14 | [Custom Key in HashMap](01_core_java.md#14-custom-class-as-hashmap-key-equals--hashcode) | `equals()` & `hashCode()` contract | 🔴 High |
| [ ] | 15 | [Thread-Safe Counter](01_core_java.md#15-thread-safe-counter) | `AtomicInteger`, `synchronized` | 🔴 High |
| [ ] | 16 | [Producer-Consumer Pattern](01_core_java.md#16-producer-consumer-problem) | `ArrayBlockingQueue` | 🔴 High |
| [ ] | 17 | [CompletableFuture API Calls](01_core_java.md#17-concurrent-api-calls-with-completablefuture) | `thenCombine`, `allOf` | 🔴 High |
| [ ] | 18 | [Retry Mechanism](01_core_java.md#18-retry-mechanism-with-exponential-backoff) | Exponential Backoff | 🔴 High |
| [ ] | 19 | [Simple LRU Cache](01_core_java.md#19-simple-lru-cache) | `LinkedHashMap(accessOrder=true)` | 🟡 Med |
| [ ] | 20 | [Thread-Safe Singleton](01_core_java.md#20-thread-safe-singleton-implementations) | Bill Pugh Helper Class / Enum | 🔴 High |

---

## ⚡ Data Structures & Algorithms Practice Checklist (15 Problems)

| Status | # | Problem | Pattern Applied | Time | Space | Priority |
| :---: | :---: | :--- | :--- | :---: | :---: | :---: |
| [ ] | 01 | [Two Sum](02_dsa.md#01-two-sum) | HashMap Lookup | $O(N)$ | $O(N)$ | 🔴 High |
| [ ] | 02 | [Valid Anagram](02_dsa.md#02-valid-anagram) | Frequency Array | $O(N)$ | $O(1)$ | 🔴 High |
| [ ] | 03 | [First Unique Character](02_dsa.md#03-first-unique-character-in-a-string) | Two Pass Frequency | $O(N)$ | $O(1)$ | 🟡 Med |
| [ ] | 04 | [Valid Palindrome](02_dsa.md#04-valid-palindrome) | Two Pointers | $O(N)$ | $O(1)$ | 🔴 High |
| [ ] | 05 | [Move Zeroes](02_dsa.md#05-move-zeroes) | Two Pointers (In-place) | $O(N)$ | $O(1)$ | 🔴 High |
| [ ] | 06 | [Best Time to Buy/Sell Stock](02_dsa.md#06-best-time-to-buy-and-sell-stock) | Single Pass Min-Tracking | $O(N)$ | $O(1)$ | 🔴 High |
| [ ] | 07 | [Merge Two Sorted Arrays](02_dsa.md#07-merge-two-sorted-arrays) | Two Pointers Merge | $O(N+M)$ | $O(N+M)$ | 🔴 High |
| [ ] | 08 | [Valid Parentheses](02_dsa.md#08-valid-parentheses) | Stack Matching | $O(N)$ | $O(N)$ | 🔴 High |
| [ ] | 09 | [Binary Search](02_dsa.md#09-binary-search) | Divide and Conquer | $O(\log N)$ | $O(1)$ | 🔴 High |
| [ ] | 10 | [Longest Substring Without Repeating](02_dsa.md#10-longest-substring-without-repeating-characters) | Sliding Window + Map | $O(N)$ | $O(K)$ | 🔴 High |
| [ ] | 11 | [Maximum Subarray](02_dsa.md#11-maximum-subarray-kadanes-algorithm) | Kadane's Algorithm | $O(N)$ | $O(1)$ | 🔴 High |
| [ ] | 12 | [Merge Intervals](02_dsa.md#12-merge-intervals) | Sort + Linear Merge | $O(N \log N)$ | $O(N)$ | 🔴 High |
| [ ] | 13 | [Reverse Linked List](02_dsa.md#13-reverse-linked-list) | Iterative Pointer Swap | $O(N)$ | $O(1)$ | 🔴 High |
| [ ] | 14 | [Binary Tree Level Order](02_dsa.md#14-binary-tree-level-order-traversal-bfs) | Queue BFS | $O(N)$ | $O(W)$ | 🔴 High |
| [ ] | 15 | [Number of Islands](02_dsa.md#15-number-of-islands-grid-dfs--bfs) | Grid DFS (Sink Land) | $O(M \times N)$ | $O(M \times N)$ | 🔴 High |

---

## 🗄️ SQL Queries Practice Checklist (15 Problems)

| Status | # | Problem / Query | Key SQL Concept | Priority |
| :---: | :---: | :--- | :--- | :---: |
| [ ] | 01 | [Find Employees Salary > 5000](03_sql.md#01-find-employees-with-salary--5000) | `SELECT`, `WHERE` | 🟢 Easy |
| [ ] | 02 | [Find Second Highest Salary](03_sql.md#02-find-second-highest-salary) | `DENSE_RANK()`, Subquery | 🔴 High |
| [ ] | 03 | [Find Nth Highest Salary](03_sql.md#03-find-nth-highest-salary) | `DENSE_RANK() OVER (...)` | 🔴 High |
| [ ] | 04 | [Salary Above Average](03_sql.md#04-employees-with-salary-above-average) | Subquery with `AVG()` | 🟡 Med |
| [ ] | 05 | [Count Employees Per Dept](03_sql.md#05-count-employees-per-department) | `GROUP BY` | 🟢 Easy |
| [ ] | 06 | [Depts Having > 5 Employees](03_sql.md#06-departments-having-more-than-5-employees) | `HAVING` vs `WHERE` | 🔴 High |
| [ ] | 07 | [Employee + Dept Name](03_sql.md#07-employee-name--department-name-inner-join) | `INNER JOIN` | 🟢 Easy |
| [ ] | 08 | [Employees Without Dept](03_sql.md#08-employees-without-department-left-join) | `LEFT JOIN` + `IS NULL` / `NOT EXISTS` | 🔴 High |
| [ ] | 09 | [Highest Paid Employee in Dept](03_sql.md#09-highest-paid-employee-in-each-department) | `ROW_NUMBER() OVER (PARTITION BY ...)` | 🔴 High |
| [ ] | 10 | [Find Duplicate Emails](03_sql.md#10-find-duplicate-emails) | `GROUP BY` + `HAVING COUNT(*) > 1` | 🟡 Med |
| [ ] | 11 | [Customers Who Never Ordered](03_sql.md#11-customers-who-never-placed-an-order) | `LEFT JOIN` / `NOT EXISTS` | 🔴 High |
| [ ] | 12 | [Total Order Amount Per Customer](03_sql.md#12-total-order-amount-per-customer) | `LEFT JOIN` + `COALESCE(SUM(), 0)` | 🟡 Med |
| [ ] | 13 | [Top 3 Customers by Spending](03_sql.md#13-top-3-customers-by-spending) | `ORDER BY ... DESC LIMIT 3` | 🟡 Med |
| [ ] | 14 | [Monthly Sales Report](03_sql.md#14-monthly-sales-report) | `DATE_TRUNC('month', order_date)` | 🔴 High |
| [ ] | 15 | [Running Total of Sales](03_sql.md#15-running-total-of-sales) | `SUM() OVER (ORDER BY date)` | 🔴 High |

---

## 💡 Quick Recall Mind Maps

### 🧠 1. DSA Pattern Recognition Guide
- Need fast lookup ($O(1)$)? ➔ **HashMap / HashSet**
- Sorted array / left-right boundaries? ➔ **Two Pointers**
- Contiguous substring / subsegment? ➔ **Sliding Window**
- Nested brackets / evaluation order? ➔ **Stack**
- Searching in sorted array? ➔ **Binary Search**
- Tree level-by-level? ➔ **Queue BFS**
- Grid matrix component scanning? ➔ **Grid DFS / BFS**

---

### 🧠 2. SQL Window Functions Difference
- **`ROW_NUMBER()`**: Unique sequential rank (`1, 2, 3, 4`). No duplicates.
- **`RANK()`**: Rank with gaps on ties (`1, 1, 3, 4`).
- **`DENSE_RANK()`**: Rank without gaps on ties (`1, 1, 2, 3`). *(Always use for Nth highest salary!)*

---
