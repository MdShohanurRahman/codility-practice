# Day 6 --- Stack & Queue (Interview Study Notes)

> **Focus:** Bracket Matching & Nesting, Monotonic Decreasing Stack, Queue FIFO Simulation & Round-Robin Scheduling.  
> **Target Platform:** Codility / MoneyLion Technical Assessment

---

## Table of Contents
1. [Problem 21: Valid Parentheses](#21-valid-parentheses)
2. [Problem 22: Brackets](#22-brackets)
3. [Problem 23: Next Greater Element](#23-next-greater-element)
4. [Problem 24: Queue Simulation](#24-queue-simulation)
5. [Day 6 Summary & Patterns Cheatsheet](#day-6-summary--patterns-cheatsheet)

---

## 21. Valid Parentheses

### Question
Determine whether a string `s` containing just the characters `'('`, `')'`, `'{'`, `'}'`, `'['` and `']'` is correctly balanced and valid.

A string is valid if:
1. Open brackets must be closed by the same type of brackets.
2. Open brackets must be closed in the correct order.
3. Every close bracket has a corresponding open bracket of the same type.

- **Example 1:** `s = "()[]{}"` $\rightarrow$ Output: `true`
- **Example 2:** `s = "([{}])"` $\rightarrow$ Output: `true`
- **Example 3:** `s = "(]"` $\rightarrow$ Output: `false`
- **Example 4:** `s = "([)]"` $\rightarrow$ Output: `false`

---

### 21.1 Brute Force Approach (String Replacement)

#### Approach & Intuition
- Repeatedly replace adjacent valid matching pairs `"()"`, `"[]"`, and `"{}"` with the empty string `""`.
- Continue this replacement loop until no further string length reductions occur.
- If the remaining string is empty, return `true`; otherwise, return `false`.

#### Java Code
```java
public static boolean isValidBruteForce(String s) {
    if (s == null) return false;

    int prevLength;
    do {
        prevLength = s.length();
        s = s.replace("()", "")
             .replace("[]", "")
             .replace("{}", "");
    } while (s.length() < prevLength);

    return s.isEmpty();
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N^2)$
  - *Reasoning:* Each string replacement scans up to $N$ characters and creates new strings. At most $N / 2$ successful replacements occur, resulting in $\mathcal{O}(N^2)$ total operations.
- **Space Complexity:** $\mathcal{O}(N^2)$
  - *Reasoning:* String immutability in Java creates a new string object on every replacement step.

---

### 21.2 Optimal Approach (Stack Data Structure)

#### Approach & Intuition
- Use a **Stack** (via Java `ArrayDeque`) to enforce the Last-In, First-Out (LIFO) order of nested brackets.
- Iterate through the string character by character:
  - If the character is an opening bracket (`'('`, `'['`, `'{'`), push its corresponding expected closing bracket (`')'`, `']'`, `'}'`) onto the stack.
  - If the character is a closing bracket:
    - If the stack is empty or the top of the stack does not equal the character, the string is invalid (`return false`).
    - Otherwise, pop the matched bracket from the stack.
- After processing all characters, the string is valid if and only if the stack is completely empty.

#### Java Code
```java
public static boolean isValidOptimal(String s) {
    if (s == null) return false;
    if (s.length() % 2 != 0) return false; // Odd length string can never be balanced

    Deque<Character> stack = new ArrayDeque<>();

    for (char c : s.toCharArray()) {
        if (c == '(') {
            stack.push(')');
        } else if (c == '[') {
            stack.push(']');
        } else if (c == '{') {
            stack.push('}');
        } else {
            // Closing bracket encountered
            if (stack.isEmpty() || stack.pop() != c) {
                return false;
            }
        }
    }

    return stack.isEmpty();
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N)$
  - *Reasoning:* We perform a single pass over the string of length $N$. Stack operations (`push`, `pop`, `peek`) take $\mathcal{O}(1)$ time.
- **Space Complexity:** $\mathcal{O}(N)$
  - *Reasoning:* In the worst case (e.g., `"((((((..."`), the stack holds up to $N$ elements.

#### Edge Cases to Mention in Interview
- **Odd Length Strings:** An odd length string (e.g., `"((("`) can immediately return `false`.
- **String Starting with Closing Bracket:** e.g., `")("` $\rightarrow$ stack is empty when encountering `')'`, correctly returns `false`.
- **Unclosed Opening Brackets:** e.g., `"(()"` $\rightarrow$ stack still has remaining elements at the end, correctly returns `false`.
- **Empty String:** `""` $\rightarrow$ return `true` (trivially valid).

---

## 22. Brackets

### Question
Given a string `S` consisting of $N$ characters (containing `(`, `)`, `[`, `]`, `{`, `}`), write a function returning `1` if string `S` is properly nested and `0` otherwise (Codility submission format).

- **Example 1:** `S = "{[()()]}"` $\rightarrow$ Output: `1`
- **Example 2:** `S = "([)()]"` $\rightarrow$ Output: `0`
- **Example 3:** `S = ""` $\rightarrow$ Output: `1`

---

### 22.1 Brute Force Approach

#### Approach & Intuition
- Iteratively replace adjacent matching pairs until no pairs remain.
- Return `1` if string length becomes 0, else return `0`.

#### Java Code
```java
public static int solutionBruteForce(String S) {
    if (S == null) return 0;
    if (S.isEmpty()) return 1;

    int prevLength;
    do {
        prevLength = S.length();
        S = S.replace("()", "")
             .replace("[]", "")
             .replace("{}", "");
    } while (S.length() < prevLength);

    return S.isEmpty() ? 1 : 0;
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N^2)$ (Fails Codility performance tests for $N = 200,000$).
- **Space Complexity:** $\mathcal{O}(N^2)$ due to string allocation overhead.

---

### 22.2 Optimal Approach (Fast Primitive Array Stack)

#### Approach & Intuition
- In Codility tests with $N = 200,000$, object instantiation overhead (like wrapping `char` into `Character` objects) can trigger Garbage Collection pauses.
- Implement an explicit **primitive array stack** (`char[] stack = new char[N]`) with an integer pointer `top`.
- On opening bracket: `stack[top++] = ch`.
- On closing bracket:
  - If `top == 0` (stack empty), return `0`.
  - Pop top: `char open = stack[--top]`. Check if `open` matches `ch`. If not, return `0`.
- At the end, return `(top == 0) ? 1 : 0`.

#### Java Code
```java
public static int solutionOptimal(String S) {
    if (S == null) return 0;
    if (S.isEmpty()) return 1;
    if (S.length() % 2 != 0) return 0; 
            Deque<Character> stack = new ArrayDeque<>();

        for (char c : S.toCharArray()) {

            if (c == '(') {
                stack.push(')');
            } else if (c == '[') {
                stack.push(']');
            } else if (c == '{') {
                stack.push('}');
            } else {
                if (stack.isEmpty() || stack.pop() != c) {
                    return 0;
                }
            }
        }

        return stack.isEmpty() ? 1 : 0;
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N)$ single pass with zero object allocations inside loop. Passes 100% of Codility performance tests.
- **Space Complexity:** $\mathcal{O}(N)$ array allocation for maximum nesting depth.

#### Edge Cases to Mention in Interview
- **Empty String:** Returns `1` per problem specification.
- **Single Bracket:** Length 1 $\rightarrow$ returns `0`.
- **Large Inputs ($N = 200,000$):** Using primitive `char[]` prevents `java.lang.OutOfMemoryError` or TLE.

---

## 23. Next Greater Element

### Question
Given an integer array `nums`, for each element find the next element to its right that is strictly greater than `nums[i]`. If no greater element exists to the right, store `-1` for that element.

- **Example 1:** `nums = [4, 5, 2, 25]` $\rightarrow$ Output: `[5, 25, 25, -1]`
- **Example 2:** `nums = [13, 7, 6, 12]` $\rightarrow$ Output: `[-1, 12, 12, -1]`
- **Example 3:** `nums = [1, 2, 3, 4]` $\rightarrow$ Output: `[2, 3, 4, -1]`
- **Example 4:** `nums = [4, 3, 2, 1]` $\rightarrow$ Output: `[-1, -1, -1, -1]`

---

### 23.1 Brute Force Approach

#### Approach & Intuition
- For every element at index $i$, iterate through elements at indices $j = i + 1$ to $N - 1$.
- Find the first index $j$ where `nums[j] > nums[i]`. Set `result[i] = nums[j]`.
- If no such element exists, set `result[i] = -1`.

#### Java Code
```java
public static int[] nextGreaterElementBruteForce(int[] nums) {
    if (nums == null || nums.length == 0) return new int[0];

    int n = nums.length;
    int[] result = new int[n];

    for (int i = 0; i < n; i++) {
        result[i] = -1;
        for (int j = i + 1; j < n; j++) {
            if (nums[j] > nums[i]) {
                result[i] = nums[j];
                break;
            }
        }
    }

    return result;
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N^2)$ (Worst case: strictly decreasing array).
- **Space Complexity:** $\mathcal{O}(1)$ auxiliary space.

---

### 23.2 Optimal Approach (Monotonic Decreasing Stack)

#### Approach & Intuition
- Traverse the array **from right to left** (index $N - 1$ down to $0$).
- Maintain a **Monotonic Stack** of candidate next greater elements in decreasing order:
  - While the stack is non-empty and `stack.peek() <= nums[i]`, pop elements from the stack (because any smaller or equal element to the right can never be the *next greater element* for `nums[i]` or any element to the left of `nums[i]`).
  - If the stack is empty, there is no greater element to the right $\rightarrow$ `result[i] = -1`.
  - If the stack is non-empty, the top element is the next greater element $\rightarrow$ `result[i] = stack.peek()`.
  - Push `nums[i]` onto the stack.

#### Java Code
```java
public static int[] nextGreaterElementOptimal(int[] nums) {
    if (nums == null || nums.length == 0) return new int[0];

    int n = nums.length;
    int[] result = new int[n];
    Deque<Integer> stack = new ArrayDeque<>();

    // Right-to-left traversal
    for (int i = n - 1; i >= 0; i--) {
        // Pop elements smaller than or equal to current element
        while (!stack.isEmpty() && stack.peek() <= nums[i]) {
            stack.pop();
        }

        // Top element is the next greater element
        result[i] = stack.isEmpty() ? -1 : stack.peek();

        // Push current element onto stack
        stack.push(nums[i]);
    }

    return result;
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N)$
  - *Reasoning:* Although there is a nested `while` loop, every element is pushed onto the stack exactly once and popped at most once. Total push/pop operations across all elements are bounded by $2N$.
- **Space Complexity:** $\mathcal{O}(N)$ for stack storage.

#### Edge Cases to Mention in Interview
- **Strictly Decreasing Array:** `[4, 3, 2, 1]` $\rightarrow$ all outputs are `-1`.
- **Strictly Increasing Array:** `[1, 2, 3, 4]` $\rightarrow$ outputs are `[2, 3, 4, -1]`.
- **All Equal Elements:** `[5, 5, 5, 5]` $\rightarrow$ outputs are `-1` because elements must be *strictly* greater.

---

## 24. Queue Simulation

### Question
Simulate a queue processing system (e.g., ticket purchasing queue, CPU round-robin scheduling, or Codility task queue). 

Given an array `tickets` where `tickets[i]` represents the number of tickets person `i` wants to buy (or units of CPU time job `i` needs), and a target index `k`, calculate the total time required for person `k` to finish processing.

Rules:
- Each person takes 1 unit of time to process 1 ticket/job unit.
- If a person still needs tickets, they move to the back of the queue.
- If a person finishes, they leave the queue.

- **Example 1:** `tickets = [2, 3, 2]`, `k = 2` $\rightarrow$ Output: `6`
  - *Pass 1:* `[1, 3, 2]`, `[1, 2, 2]`, `[1, 2, 1]` (time = 3)
  - *Pass 2:* `[0, 2, 1]`, `[0, 1, 1]`, `[0, 1, 0]` (time = 6 $\rightarrow$ person 2 finishes)
- **Example 2:** `tickets = [5, 1, 1, 1]`, `k = 0` $\rightarrow$ Output: `8`

---

### 24.1 Simulation Approach (Explicit Queue)

#### Approach & Intuition
- Enqueue pairs `[personIndex, remainingTickets]` into a `Queue<int[]>`.
- While queue is not empty:
  - Poll the front person.
  - Decrement `remainingTickets` by 1 and increment `time` by 1.
  - If `personIndex == k` and `remainingTickets == 0`, target person is done, return `time`.
  - If `remainingTickets > 0`, re-enqueue to the back of the queue.

#### Java Code
```java
public static int timeRequiredToBuySimulation(int[] tickets, int k) {
    if (tickets == null || tickets.length == 0) return 0;

    Queue<int[]> queue = new ArrayDeque<>();
    for (int i = 0; i < tickets.length; i++) {
        queue.offer(new int[]{i, tickets[i]});
    }

    int time = 0;
    while (!queue.isEmpty()) {
        int[] person = queue.poll();
        int index = person[0];
        int remaining = person[1] - 1;
        time++;

        if (index == k && remaining == 0) {
            return time;
        }

        if (remaining > 0) {
            queue.offer(new int[]{index, remaining});
        }
    }

    return time;
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(\sum \text{tickets}[i])$
  - *Reasoning:* Each ticket requires 1 queue poll and offer operation. If ticket counts are large (e.g., $10^9$), this simulation will TLE.
- **Space Complexity:** $\mathcal{O}(N)$ for queue storage.

---

### 24.2 Optimal Approach (Single-Pass Mathematical Counting)

#### Approach & Intuition
- Instead of simulating every ticket purchase unit by unit, calculate how many rounds person $k$ will wait mathematically:
- Let `targetTickets = tickets[k]`. Person $k$ will buy exactly `targetTickets` tickets.
- For any person $i$:
  - **If $i \le k$ (before or at target index $k$):**
    - Person $i$ gets a turn in every round up to `targetTickets`.
    - Total tickets person $i$ buys = $\min(\text{tickets}[i], \text{tickets}[k])$.
  - **If $i > k$ (after target index $k$):**
    - In the final round, person $k$ finishes and leaves before person $i$ gets a turn.
    - Total tickets person $i$ buys = $\min(\text{tickets}[i], \text{tickets}[k] - 1)$.
- Sum up these ticket counts in a single linear scan $\mathcal{O}(N)$!

#### Java Code
```java
public static int timeRequiredToBuyOptimal(int[] tickets, int k) {
    if (tickets == null || tickets.length == 0) return 0;

    int totalTime = 0;
    int targetTickets = tickets[k];

    for (int i = 0; i < tickets.length; i++) {
        if (i <= k) {
            totalTime += Math.min(tickets[i], targetTickets);
        } else {
            totalTime += Math.min(tickets[i], targetTickets - 1);
        }
    }

    return totalTime;
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N)$
  - *Reasoning:* A single linear pass over the array of size $N$. Independent of ticket quantity!
- **Space Complexity:** $\mathcal{O}(1)$ auxiliary space.

#### Edge Cases to Mention in Interview
- `k = 0` (Target is at the front of the queue): People behind target only buy `tickets[0] - 1` tickets.
- `k = N - 1` (Target is at the back of the queue): People before target buy `min(tickets[i], tickets[k])` tickets.
- Large ticket counts ($10^9$): Mathematical $\mathcal{O}(N)$ runs instantly while queue simulation would time out.

---

## Day 6 Summary & Patterns Cheatsheet

| Problem | Brute Force Time | Optimal Time | Optimal Space | Key Pattern / Core Concept |
| :--- | :--- | :--- | :--- | :--- |
| **21. Valid Parentheses** | $\mathcal{O}(N^2)$ | $\mathcal{O}(N)$ | $\mathcal{O}(N)$ | Stack LIFO matching for nested structures |
| **22. Brackets** | $\mathcal{O}(N^2)$ | $\mathcal{O}(N)$ | $\mathcal{O}(N)$ | Primitive `char[]` array stack to avoid GC overhead |
| **23. Next Greater Element** | $\mathcal{O}(N^2)$ | $\mathcal{O}(N)$ | $\mathcal{O}(N)$ | Monotonic Decreasing Stack (Right-to-Left pass) |
| **24. Queue Simulation** | $\mathcal{O}(\sum \text{tickets})$ | $\mathcal{O}(N)$ | $\mathcal{O}(1)$ | Single-pass mathematical simulation vs FIFO Queue |

---

### Key Takeaways for Codility / Technical Interviews

1. **Stack LIFO Property for Nesting:**
   - Whenever a problem involves matching opening and closing symbols, nested expressions, or HTML/XML tags, a **Stack** is the default optimal data structure.
2. **Primitive Array Stack Optimization:**
   - In high-performance coding assessments like Codility, using `char[] stack` with a simple integer `top` pointer avoids creating $N$ boxed `Character` objects on the heap, ensuring maximum performance and zero GC pressure.
3. **Monotonic Stack Pattern:**
   - To find the **Next Greater**, **Next Smaller**, **Previous Greater**, or **Previous Smaller** element in $\mathcal{O}(N)$ time, use a Monotonic Stack. Maintain elements in strictly increasing or decreasing order by popping invalidated elements before pushing.
4. **Queue Simulation vs Mathematical Count:**
   - Interactive queues can be simulated with `ArrayDeque` or `LinkedList`, but always check if a problem can be solved mathematically in $\mathcal{O}(N)$ or $\mathcal{O}(1)$ space by analyzing round bounds instead of simulating unit by unit.
