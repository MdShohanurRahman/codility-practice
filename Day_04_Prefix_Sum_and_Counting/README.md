# Day 4 --- Prefix Sum & Counting (Interview Study Notes)

> **Focus:** Range Precomputation, Running Totals, Subarray Balance Points, Event Counting, and HashSet Uniqueness.  
> **Target Platform:** Codility / MoneyLion Technical Assessment

---

## Table of Contents
1. [Problem 13: Range Sum Queries](#13-range-sum-queries)
2. [Problem 14: Equilibrium Index](#14-equilibrium-index)
3. [Problem 15: Count Passing Cars](#15-count-passing-cars)
4. [Problem 16: Distinct Values](#16-distinct-values)
5. [Day 4 Summary & Patterns Cheatsheet](#day-4-summary--patterns-cheatsheet)

---

## 13. Range Sum Queries

### Question
Given an integer array `nums` and multiple queries `[L, R]`, return the sum of elements from index `L` to `R` inclusive efficiently.

- **Example:** `nums = [-2, 0, 3, -5, 2, -1]`
  - Query `[0, 2]` $\rightarrow$ Output: `1` ($(-2) + 0 + 3 = 1$)
  - Query `[2, 5]` $\rightarrow$ Output: `-1` ($3 + (-5) + 2 + (-1) = -1$)
  - Query `[0, 5]` $\rightarrow$ Output: `-3`

---

### 13.1 Brute Force Approach

#### Approach & Intuition
- For every query `[L, R]`, iterate through the array from index `L` to `R` and compute the sum.

#### Java Code
```java
public static long rangeSumBruteForce(int[] nums, int left, int right) {
    if (nums == null || left < 0 || right >= nums.length || left > right) {
        throw new IllegalArgumentException("Invalid query bounds.");
    }
    long sum = 0;
    for (int i = left; i <= right; i++) {
        sum += nums[i];
    }
    return sum;
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(Q \times N)$ where $Q$ is the number of range queries and $N$ is array length.
- **Space Complexity:** $\mathcal{O}(1)$

---

### 13.2 Optimal Approach (Prefix Sum Array)

#### Approach & Intuition
- Construct a `prefixSum` array of size $N + 1$ where `prefixSum[i]` holds the sum of the first `i` elements:
  - `prefixSum[0] = 0`
  - `prefixSum[i + 1] = prefixSum[i] + nums[i]`
- Any range sum $[L, R]$ can be computed in $\mathcal{O}(1)$ time as:
  $$\text{Sum}(L, R) = \text{prefixSum}[R + 1] - \text{prefixSum}[L]$$

#### Java Code
```java
public static class NumArray {
    private final long[] prefixSum;

    public NumArray(int[] nums) {
        int n = nums.length;
        this.prefixSum = new long[n + 1];
        for (int i = 0; i < n; i++) {
            this.prefixSum[i + 1] = this.prefixSum[i] + nums[i];
        }
    }

    public long sumRange(int left, int right) {
        return prefixSum[right + 1] - prefixSum[left];
    }
}
```

#### Complexity Analysis
- **Time Complexity:** 
  - Precomputation: $\mathcal{O}(N)$
  - Per Query: $\mathcal{O}(1)$
  - Total Time: $\mathcal{O}(N + Q)$
- **Space Complexity:** $\mathcal{O}(N)$ for storing the `prefixSum` array.

#### Edge Cases to Mention in Interview
- `left == 0`: `prefixSum[R + 1] - prefixSum[0]` correctly returns total sum up to $R$.
- `left == right`: Single element query $\rightarrow$ returns `nums[left]`.
- Sum overflow: Use `long` array for `prefixSum` to prevent 32-bit signed integer overflow when summing large elements.

---

## 14. Equilibrium Index

### Question
Find an index `i` in an array such that the sum of elements before index `i` equals the sum of elements after index `i`. Return `-1` if no equilibrium index exists.

- **Example 1:** `A = [-7, 1, 5, 2, -4, 3, 0]` $\rightarrow$ Output: `3`
  - Left sum at index 3: $(-7) + 1 + 5 = -1$
  - Right sum at index 3: $(-4) + 3 + 0 = -1$
- **Example 2:** `A = [1, 2, 3]` $\rightarrow$ Output: `-1`

---

### 14.1 Brute Force Approach

#### Approach & Intuition
- Iterate through each index `i` from `0` to `N-1`.
- Compute left sum ($0 \dots i-1$) and right sum ($i+1 \dots N-1$) using nested loops.
- Return `i` if `leftSum == rightSum`.

#### Java Code
```java
public static int equilibriumIndexBruteForce(int[] nums) {
    if (nums == null || nums.length == 0) return -1;
    int n = nums.length;

    for (int i = 0; i < n; i++) {
        long leftSum = 0;
        for (int j = 0; j < i; j++) leftSum += nums[j];

        long rightSum = 0;
        for (int j = i + 1; j < n; j++) rightSum += nums[j];

        if (leftSum == rightSum) return i;
    }
    return -1;
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N^2)$
- **Space Complexity:** $\mathcal{O}(1)$

---

### 14.2 Optimal Approach (Running Sum in $O(1)$ Space)

#### Approach & Intuition
- First pass: Calculate `totalSum` of the array.
- Maintain a running `leftSum` initialized to `0`.
- At any index `i`:
  $$\text{rightSum} = \text{totalSum} - \text{leftSum} - \text{nums}[i]$$
- If `leftSum == rightSum`, return `i`.
- Otherwise, add `nums[i]` to `leftSum` and move to index `i + 1`.

#### Java Code
```java
public static int equilibriumIndexOptimal(int[] nums) {
    if (nums == null || nums.length == 0) return -1;

    long totalSum = 0;
    for (int num : nums) {
        totalSum += num;
    }

    long leftSum = 0;
    for (int i = 0; i < nums.length; i++) {
        long rightSum = totalSum - leftSum - nums[i];

        if (leftSum == rightSum) {
            return i;
        }
        leftSum += nums[i];
    }
    return -1;
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N)$ (Two linear passes: one for total sum, one for scanning).
- **Space Complexity:** $\mathcal{O}(1)$ auxiliary space.

#### Edge Cases to Mention in Interview
- Equilibrium index at boundary `0` (left sum is `0`).
- Equilibrium index at boundary `N-1` (right sum is `0`).
- Array containing negative numbers or zeroes.
- Empty array or `null` $\rightarrow$ return `-1`.

---

## 15. Count Passing Cars

### Question
An array `A` of `N` integers consists only of `0`s and `1`s:
- `0` represents a car traveling **East**.
- `1` represents a car traveling **West**.

Count pairs of cars $(P, Q)$ such that $0 \le P < Q < N$, where $A[P] = 0$ (traveling East) and $A[Q] = 1$ (traveling West).  
Return `-1` if the number of passing cars exceeds $1,000,000,000$.

- **Example:** `A = [0, 1, 0, 1, 1]` $\rightarrow$ Output: `5`
  - Pairs: $(0,1), (0,3), (0,4), (2,3), (2,4)$

---

### 15.1 Brute Force Approach

#### Approach & Intuition
- For every index `P` where `A[P] == 0`, iterate through all `Q > P` where `A[Q] == 1` and increment the passing car counter.

#### Java Code
```java
public static int countPassingCarsBruteForce(int[] nums) {
    if (nums == null || nums.length < 2) return 0;
    long totalPassing = 0;
    for (int p = 0; p < nums.length; p++) {
        if (nums[p] == 0) {
            for (int q = p + 1; q < nums.length; q++) {
                if (nums[q] == 1) {
                    totalPassing++;
                    if (totalPassing > 1_000_000_000L) return -1;
                }
            }
        }
    }
    return (int) totalPassing;
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N^2)$
- **Space Complexity:** $\mathcal{O}(1)$

---

### 15.2 Optimal Approach (Prefix East-Bound Car Counting)

#### Approach & Intuition
- Traverse the array from left to right while keeping a counter `eastCars`.
- When encountering `0` (East car): increment `eastCars++`.
- When encountering `1` (West car): this car will pass **all** previously seen East cars! Add `eastCars` to `totalPassing`.
- If `totalPassing > 1,000,000,000`, return `-1` immediately.

#### Java Code
```java
public static int countPassingCarsOptimal(int[] nums) {
    if (nums == null || nums.length < 2) return 0;
    int eastCars = 0;
    long totalPassing = 0;

    for (int num : nums) {
        if (num == 0) {
            eastCars++;
        } else if (num == 1) {
            totalPassing += eastCars;
            if (totalPassing > 1_000_000_000L) {
                return -1;
            }
        }
    }
    return (int) totalPassing;
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N)$ (Single linear pass).
- **Space Complexity:** $\mathcal{O}(1)$ constant space.

#### Edge Cases to Mention in Interview
- All East cars (`[0, 0, 0]`) or all West cars (`[1, 1, 1]`) $\rightarrow$ Output `0`.
- West cars come before East cars (`[1, 1, 0, 0]`) $\rightarrow$ Output `0`.
- Total count exceeds $10^9$ limit $\rightarrow$ return `-1` (Codility specification rule!).

---

## 16. Distinct Values

### Question
Count the number of distinct values in an integer array `nums`.

- **Example 1:** `nums = [2, 1, 1, 2, 3, 1]` $\rightarrow$ Output: `3` (Distinct values: `{1, 2, 3}`)
- **Example 2:** `nums = [-5, -5, -5]` $\rightarrow$ Output: `1`

---

### 16.1 Sorting Approach

#### Approach & Intuition
- Sort the array in non-decreasing order.
- Iterate through the sorted array and count how many times `sorted[i] != sorted[i - 1]`.

#### Java Code
```java
public static int countDistinctSorting(int[] nums) {
    if (nums == null || nums.length == 0) return 0;
    int[] sorted = nums.clone();
    Arrays.sort(sorted);

    int count = 1;
    for (int i = 1; i < sorted.length; i++) {
        if (sorted[i] != sorted[i - 1]) {
            count++;
        }
    }
    return count;
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N \log N)$ (due to sorting).
- **Space Complexity:** $\mathcal{O}(1)$ extra space if mutating array in-place, or $\mathcal{O}(N)$ if cloning.

---

### 16.2 Optimal Approach (HashSet Lookup)

#### Approach & Intuition
- Insert all array elements into a `HashSet<Integer>`.
- The size of the `HashSet` (`set.size()`) represents the exact count of distinct values!

#### Java Code
```java
public static int countDistinctHashSet(int[] nums) {
    if (nums == null || nums.length == 0) return 0;
    Set<Integer> set = new HashSet<>();
    for (int num : nums) {
        set.add(num);
    }
    return set.size();
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N)$ expected time.
- **Space Complexity:** $\mathcal{O}(N)$ for the `HashSet`.

#### Edge Cases to Mention in Interview
- Empty array `[]` $\rightarrow$ `0`.
- Negative numbers (`[-5, -10, 5, 10]`) $\rightarrow$ handled seamlessly by `HashSet`.
- Extreme array values (`Integer.MIN_VALUE`, `Integer.MAX_VALUE`).

---

## Day 4 Summary & Patterns Cheatsheet

| Problem | Brute Force Time | Optimal Time | Optimal Space | Key Pattern / Core Concept |
| :--- | :--- | :--- | :--- | :--- |
| **13. Range Sum Queries** | $\mathcal{O}(Q \cdot N)$ | $\mathcal{O}(N + Q)$ | $\mathcal{O}(N)$ | Prefix Sum Array Precomputation |
| **14. Equilibrium Index** | $\mathcal{O}(N^2)$ | $\mathcal{O}(N)$ | $\mathcal{O}(1)$ | Total Sum - Left Sum - A[i] Balance Formula |
| **15. Count Passing Cars** | $\mathcal{O}(N^2)$ | $\mathcal{O}(N)$ | $\mathcal{O}(1)$ | Running Prefix Counter ($0$s accumulate, $1$s trigger addition) |
| **16. Distinct Values** | $\mathcal{O}(N^2)$ | $\mathcal{O}(N)$ expected | $\mathcal{O}(N)$ (or $\mathcal{O}(N \log N)$ sorting) | HashSet uniqueness or Sorting neighbor check |

---

### Key Takeaways for Codility / Technical Interviews
1. **The Power of Prefix Sums:**
   - Precomputing `prefixSum[i + 1] = prefixSum[i] + nums[i]` allows answering ANY range sum query $[L, R]$ in $\mathcal{O}(1)$ constant time.
2. **Space Optimization via Total Sum:**
   - For balance point problems like Equilibrium Index, pre-calculating `totalSum` avoids allocating a separate prefix array, keeping space complexity at $\mathcal{O}(1)$.
3. **Cumulative Event Multipliers:**
   - In passing/pair counting problems (Problem 15), each target event (West car = `1`) interacts with ALL preceding source events (East cars = `0`). Incrementing a prefix accumulator allows answering pair counts in a single pass.
4. **Codility Constraint Guardrails:**
   - Always check problem specifications for upper limits (e.g. return `-1` if count exceeds $10^9$) and sum overflow conditions (use `long`).
