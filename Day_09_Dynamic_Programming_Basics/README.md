# Day 9 --- Dynamic Programming Basics (Interview Study Notes)

> **Focus:** Overlapping Subproblems, Optimal Substructure, State Transitions, Tabulation vs Memoization, and $O(1)$ Auxiliary Space Optimization.  
> **Target Platform:** Codility / MoneyLion Technical Assessment

---

## Table of Contents
1. [Problem 33: Climbing Stairs](#33-climbing-stairs)
2. [Problem 34: House Robber](#34-house-robber)
3. [Day 9 Summary & Patterns Cheatsheet](#day-9-summary--patterns-cheatsheet)

---

## 33. Climbing Stairs

### Question
You are climbing a staircase. It takes `N` steps to reach the top. Each time you can either climb `1` or `2` steps. In how many distinct ways can you climb to the top?

- **Example 1:** `n = 2` $\rightarrow$ Output: `2` (Ways: `1 step + 1 step`, `2 steps`)
- **Example 2:** `n = 3` $\rightarrow$ Output: `3` (Ways: `1+1+1`, `1+2`, `2+1`)
- **Example 3:** `n = 5` $\rightarrow$ Output: `8` (Ways: `1+1+1+1+1`, `1+1+1+2`, `1+1+2+1`, `1+2+1+1`, `2+1+1+1`, `1+2+2`, `2+1+2`, `2+2+1`)

---

### 33.1 Brute Force Approach (Naive Recursion)

#### Approach & Intuition
- To reach step $N$, your final jump was either from step $N-1$ (a 1-step jump) or from step $N-2$ (a 2-step jump).
- Therefore, the number of total ways to reach step $N$ is the sum of ways to reach step $N-1$ and step $N-2$:
  $$\text{ways}(N) = \text{ways}(N-1) + \text{ways}(N-2)$$
- This directly forms the Fibonacci recurrence relation with base cases:
  - $\text{ways}(1) = 1$
  - $\text{ways}(2) = 2$

#### Java Code
```java
public static int climbStairsRecursion(int n) {
    if (n <= 0) return 0;
    if (n == 1) return 1;
    if (n == 2) return 2;
    return climbStairsRecursion(n - 1) + climbStairsRecursion(n - 2);
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(2^N)$
  - *Reasoning:* A binary recursion tree is constructed of depth $N$. The number of recursive calls doubles at each level without subproblem reuse.
- **Space Complexity:** $\mathcal{O}(N)$
  - *Reasoning:* Recursion stack depth reaches up to $N$.

---

### 33.2 Memoization Approach (Top-Down DP)

#### Approach & Intuition
- Store the results of already calculated steps in a `memo` array of size $N + 1$.
- Before making recursive calls for step $k$, check if `memo[k]` is already populated. If so, return it instantly in $\mathcal{O}(1)$ time.

#### Java Code
```java
public static int climbStairsMemoization(int n) {
    if (n <= 0) return 0;
    int[] memo = new int[n + 1];
    Arrays.fill(memo, -1);
    return memoHelper(n, memo);
}

private static int memoHelper(int n, int[] memo) {
    if (n == 1) return 1;
    if (n == 2) return 2;
    if (memo[n] != -1) return memo[n];

    memo[n] = memoHelper(n - 1, memo) + memoHelper(n - 2, memo);
    return memo[n];
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N)$
  - *Reasoning:* Each subproblem from $1 \dots N$ is computed exactly once.
- **Space Complexity:** $\mathcal{O}(N)$
  - *Reasoning:* Requires $\mathcal{O}(N)$ space for the `memo` array plus $\mathcal{O}(N)$ recursion call stack.

---

### 33.3 Bottom-Up Tabulation (DP Array)

#### Approach & Intuition
- Instead of starting from top step $N$ and working down, build the solution iteratively from step $1$ up to $N$.
- Allocate `dp[N + 1]`:
  - `dp[1] = 1`
  - `dp[2] = 2`
  - For $i = 3 \dots N$: `dp[i] = dp[i-1] + dp[i-2]`.

#### Java Code
```java
public static int climbStairsTabulation(int n) {
    if (n <= 0) return 0;
    if (n == 1) return 1;
    if (n == 2) return 2;

    int[] dp = new int[n + 1];
    dp[1] = 1;
    dp[2] = 2;

    for (int i = 3; i <= n; i++) {
        dp[i] = dp[i - 1] + dp[i - 2];
    }

    return dp[n];
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N)$
  - *Reasoning:* Single linear loop from $3$ to $N$.
- **Space Complexity:** $\mathcal{O}(N)$
  - *Reasoning:* Requires an array of size $N + 1$ to store DP state.

---

### 33.4 Optimal Space-Optimized DP ($O(1)$ Space)

#### Approach & Intuition
- Notice that computing `dp[i]` only ever requires `dp[i - 1]` and `dp[i - 2]`.
- We can eliminate the full array and maintain only two sliding state variables: `first` (representing `dp[i-2]`) and `second` (representing `dp[i-1]`).

#### Step-by-Step State Transition Table ($N = 5$)

| Iteration ($i$) | `first` (ways to $i-2$) | `second` (ways to $i-1$) | `third` (`first + second`) | New (`first`, `second`) |
| :--- | :--- | :--- | :--- | :--- |
| **Initial** ($N=1, 2$) | $1$ | $2$ | -- | -- |
| **$i = 3$** | $1$ | $2$ | $1 + 2 = 3$ | ($2$, $3$) |
| **$i = 4$** | $2$ | $3$ | $2 + 3 = 5$ | ($3$, $5$) |
| **$i = 5$** | $3$ | $5$ | $3 + 5 = 8$ | ($5$, $8$) |

#### Java Code
```java
public static int climbStairsOptimal(int n) {
    if (n <= 0) return 0;
    if (n == 1) return 1;
    if (n == 2) return 2;

    int first = 1;  // dp[1]
    int second = 2; // dp[2]

    for (int i = 3; i <= n; i++) {
        int third = first + second;
        first = second;
        second = third;
    }

    return second;
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N)$
  - *Reasoning:* Single pass loop of size $N - 2$.
- **Space Complexity:** $\mathcal{O}(1)$ auxiliary space
  - *Reasoning:* Only uses two scalar integer variables.

#### Edge Cases to Mention in Interview
- **$N = 1$:** Should return `1` directly without array index out-of-bounds error.
- **$N = 2$:** Should return `2`.
- **Large $N$ ($N \ge 46$):** Fibonacci numbers exceed standard 32-bit signed `int` limits (`Integer.MAX_VALUE = 2,147,483,647`). For $N > 45$, standard `int` overflows, so `long` or `BigInteger` would be required.

---

## 34. House Robber

### Question
You are a professional robber planning to rob houses along a street. Each house has a certain amount of money stashed. The only constraint stopping you from robbing each of them is that adjacent houses have security systems connected, and **it will automatically contact the police if two adjacent houses were broken into on the same night**.

Given an integer array `nums` representing the amount of money of each house, return the **maximum amount of money you can rob tonight without alerting the police**.

- **Example 1:** `nums = [1, 2, 3, 1]` $\rightarrow$ Output: `4` (Rob house 1 (`money=1`) and house 3 (`money=3`). Total = `1 + 3 = 4`)
- **Example 2:** `nums = [2, 7, 9, 3, 1]` $\rightarrow$ Output: `12` (Rob house 1 (`2`), house 3 (`9`), and house 5 (`1`). Total = `2 + 9 + 1 = 12`)
- **Example 3:** `nums = [2, 1, 1, 2]` $\rightarrow$ Output: `4` (Rob house 1 (`2`) and house 4 (`2`). Total = `2 + 2 = 4`)

---

### 34.1 Brute Force Approach (Naive Recursion)

#### Approach & Intuition
- At any house index $i$, you have two mutually exclusive choices:
  1. **Rob house $i$:** Gain `nums[i]` plus the maximum money robbed from subproblem ending at index $i - 2$ (since house $i - 1$ cannot be robbed).
  2. **Skip house $i$:** Gain $0$ plus the maximum money robbed from subproblem ending at index $i - 1$.
- Decision recurrence:
  $$\text{rob}(i) = \max(\text{nums}[i] + \text{rob}(i - 2),\ \text{rob}(i - 1))$$

#### Java Code
```java
public static int robBruteForce(int[] nums) {
    if (nums == null || nums.length == 0) return 0;
    return robHelper(nums, nums.length - 1);
}

private static int robHelper(int[] nums, int i) {
    if (i < 0) return 0;
    if (i == 0) return nums[0];

    int robCurrent = nums[i] + robHelper(nums, i - 2);
    int skipCurrent = robHelper(nums, i - 1);

    return Math.max(robCurrent, skipCurrent);
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(2^N)$
  - *Reasoning:* Evaluates both rob and skip paths recursively for every house.
- **Space Complexity:** $\mathcal{O}(N)$
  - *Reasoning:* Maximum recursion call stack depth equal to $N$.

---

### 34.2 Memoization Approach (Top-Down DP)

#### Approach & Intuition
- Store the optimal decision for house index $i$ in a `memo[i]` array initialized to `-1`.
- If `memo[i] != -1`, return the precomputed result immediately.

#### Java Code
```java
public static int robMemoization(int[] nums) {
    if (nums == null || nums.length == 0) return 0;
    int[] memo = new int[nums.length];
    Arrays.fill(memo, -1);
    return memoRobHelper(nums, nums.length - 1, memo);
}

private static int memoRobHelper(int[] nums, int i, int[] memo) {
    if (i < 0) return 0;
    if (i == 0) return nums[0];
    if (memo[i] != -1) return memo[i];

    int robCurrent = nums[i] + memoRobHelper(nums, i - 2, memo);
    int skipCurrent = memoRobHelper(nums, i - 1, memo);

    memo[i] = Math.max(robCurrent, skipCurrent);
    return memo[i];
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N)$
  - *Reasoning:* Computes each subproblem index $i$ once.
- **Space Complexity:** $\mathcal{O}(N)$
  - *Reasoning:* Requires $\mathcal{O}(N)$ space for memo array + $\mathcal{O}(N)$ recursion stack.

---

### 34.3 Bottom-Up Tabulation (DP Array)

#### Approach & Intuition
- Allocate an array `dp` of size $N$ where `dp[i]` represents the maximum money robbed from the first $i + 1$ houses (`nums[0 ... i]`).
- **Base cases:**
  - `dp[0] = nums[0]`
  - `dp[1] = Math.max(nums[0], nums[1])`
- **Transition equation for $i \ge 2$:**
  $$\text{dp}[i] = \max(\text{dp}[i - 1],\ \text{nums}[i] + \text{dp}[i - 2])$$

#### Java Code
```java
public static int robTabulation(int[] nums) {
    if (nums == null || nums.length == 0) return 0;
    if (nums.length == 1) return nums[0];

    int n = nums.length;
    int[] dp = new int[n];
    dp[0] = nums[0];
    dp[1] = Math.max(nums[0], nums[1]);

    for (int i = 2; i < n; i++) {
        dp[i] = Math.max(dp[i - 1], nums[i] + dp[i - 2]);
    }

    return dp[n - 1];
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N)$
  - *Reasoning:* Single linear iteration through array `nums`.
- **Space Complexity:** $\mathcal{O}(N)$
  - *Reasoning:* Requires an array `dp` of size $N$.

---

### 34.4 Optimal Space-Optimized DP ($O(1)$ Auxiliary Space)

#### Approach & Intuition
- Notice that calculating `dp[i]` only ever references the previous state `dp[i - 1]` and the state before that `dp[i - 2]`.
- Maintain two scalar variables:
  - `prev2`: represents optimal result up to $i - 2$
  - `prev1`: represents optimal result up to $i - 1$
- In each step:
  $$\text{current} = \max(\text{prev1},\ \text{nums}[i] + \text{prev2})$$
  $$\text{prev2} = \text{prev1},\ \ \text{prev1} = \text{current}$$

#### Step-by-Step State Transition Trace (`nums = [2, 7, 9, 3, 1]`)

| House Index ($i$) | House Value (`nums[i]`) | `prev2` ($\text{dp}[i-2]$) | `prev1` ($\text{dp}[i-1]$) | `current` ($\max(\text{prev1}, \text{nums}[i] + \text{prev2})$) | Selected Action |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **$i = 0$** | `2` | -- | `2` | `2` | Rob house 0 (`2`) |
| **$i = 1$** | `7` | `2` | `7` | $\max(2, 7) = 7$ | Rob house 1 (`7`) |
| **$i = 2$** | `9` | `2` | `7` | $\max(7, 9 + 2) = 11$ | Rob house 0 & 2 (`2+9=11`) |
| **$i = 3$** | `3` | `7` | `11` | $\max(11, 3 + 7) = 11$ | Skip house 3 |
| **$i = 4$** | `1` | `11` | `11` | $\max(11, 1 + 11) = 12$ | Rob house 0, 2 & 4 (`2+9+1=12`) |

#### Java Code
```java
public static int robOptimal(int[] nums) {
    if (nums == null || nums.length == 0) return 0;
    if (nums.length == 1) return nums[0];

    int prev2 = nums[0];                     // dp[0]
    int prev1 = Math.max(nums[0], nums[1]);  // dp[1]

    for (int i = 2; i < nums.length; i++) {
        int current = Math.max(prev1, nums[i] + prev2);
        prev2 = prev1;
        prev1 = current;
    }

    return prev1;
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N)$
  - *Reasoning:* Single pass over array of length $N$.
- **Space Complexity:** $\mathcal{O}(1)$ auxiliary space
  - *Reasoning:* Operates entirely with two integer variables.

#### Edge Cases to Mention in Interview
- **Empty Array (`null` or length `0`):** Return `0`.
- **Single House (`[5]`):** Return `nums[0]` immediately without checking `nums[1]`.
- **Two Houses (`[2, 7]`):** Return `Math.max(nums[0], nums[1])`.
- **Array with All Zeroes (`[0, 0, 0]`):** Result should be `0`.

---

## Day 9 Summary & Patterns Cheatsheet

| Problem | Brute Force Time | Optimal Time | Optimal Space | Key Pattern / State Recurrence |
| :--- | :--- | :--- | :--- | :--- |
| **33. Climbing Stairs** | $\mathcal{O}(2^N)$ | $\mathcal{O}(N)$ | $\mathcal{O}(1)$ | Fibonacci State Transition: $\text{dp}[i] = \text{dp}[i-1] + \text{dp}[i-2]$ |
| **34. House Robber** | $\mathcal{O}(2^N)$ | $\mathcal{O}(N)$ | $\mathcal{O}(1)$ | Include/Exclude Choice: $\text{dp}[i] = \max(\text{dp}[i-1],\ \text{nums}[i] + \text{dp}[i-2])$ |

---

### The 5-Step Dynamic Programming Framework

Whenever faced with a DP problem in a technical interview or Codility assessment, follow these 5 steps:

```
1. Recognize Choice & Overlapping Subproblems
   └── Can the decision at step i be broken down into sub-choices?

2. Define State Representation
   └── What does dp[i] represent? (e.g., max profit up to index i)

3. Formulate State Transition Recurrence
   └── Express dp[i] in terms of smaller subproblems (dp[i-1], dp[i-2], etc.)

4. Identify Base Cases & Boundary Conditions
   └── Define initial values (dp[0], dp[1]) to prevent out-of-bounds access.

5. Optimize Space Complexity
   └── If dp[i] only depends on constant k previous states, replace array with k variables.
```

### Key Takeaways for Codility / Technical Assessments

1. **Space Optimization Pattern:**
   - Both *Climbing Stairs* and *House Robber* demonstrate how an $\mathcal{O}(N)$ memory bottom-up tabulation array can be reduced to $\mathcal{O}(1)$ auxiliary space using two sliding variables (`prev2`, `prev1`).
2. **Edge Case Guarding:**
   - Always validate `n == 0`, `n == 1`, and `n == 2` or array length checks at the very top of your function before allocating memory or indexing into arrays.
3. **Choice-Based Transitions:**
   - House Robber is a classic template for **Include vs Exclude** dynamic programming. Master this recurrence, as it generalizes directly to 0/1 Knapsack, Target Sum, and Maximum Subarray variants.
