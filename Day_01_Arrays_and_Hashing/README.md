# Day 1 --- Arrays & Hashing (Interview Study Notes)

> **Focus:** Fundamental Array Manipulations, Hash Maps / Sets for lookup, and Mathematical/Bitwise tricks.
> **Target Platform:** Codility / MoneyLion Technical Assessment

---

## Table of Contents
1. [Problem 1: Missing Number](#1-missing-number)
2. [Problem 2: Two Sum](#2-two-sum)
3. [Problem 3: Maximum Difference](#3-maximum-difference)
4. [Problem 4: Contains Duplicate](#4-contains-duplicate)
5. [Day 1 Summary & Patterns Cheatsheet](#day-1-summary--patterns-cheatsheet)

---

## 1. Missing Number

### Question
Given an array `nums` containing `N` distinct numbers from `0` to `N`, find the missing number that does not appear in the array.

- **Example 1:** `nums = [3, 0, 1]` $\rightarrow$ Output: `2` (Length $N = 3$, numbers are from $0..3$)
- **Example 2:** `nums = [0, 1]` $\rightarrow$ Output: `2` (Length $N = 2$, numbers are from $0..2$)

---

### 1.1 Brute Force Approach (Sorting)

#### Approach & Intuition
- Sort the array in ascending order.
- Iterate from index `0` to `N-1`. If `nums[i] != i`, then `i` is the missing number.
- If all elements match their index, the missing number is `N`.

#### Java Code
```java
public static int missingNumberBruteForce(int[] nums) {
    Arrays.sort(nums);
    for (int i = 0; i < nums.length; i++) {
        if (nums[i] != i) {
            return i;
        }
    }
    return nums.length;
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N \log N)$
  - *Reasoning:* Sorting takes $\mathcal{O}(N \log N)$ time, and the linear scan takes $\mathcal{O}(N)$. Overall dominates by $\mathcal{O}(N \log N)$.
- **Space Complexity:** $\mathcal{O}(1)$ or $\mathcal{O}(\log N)$
  - *Reasoning:* Arrays.sort() in Java uses Dual-Pivot Quicksort for primitives, requiring $\mathcal{O}(\log N)$ stack space.

---

### 1.2 Optimal Approach 1 (Mathematical Sum Formula)

#### Approach & Intuition
- The sum of numbers from `0` to `N` is given by Gauss' formula: $\text{Expected Sum} = \frac{N \times (N + 1)}{2}$.
- Calculate the actual sum of elements in `nums`.
- $\text{Missing Number} = \text{Expected Sum} - \text{Actual Sum}$.

#### Java Code
```java
public static int missingNumberOptimalSum(int[] nums) {
    int n = nums.length;
    long expectedSum = (long) n * (n + 1) / 2; // cast to long to avoid overflow
    long actualSum = 0;
    for (int num : nums) {
        actualSum += num;
    }
    return (int) (expectedSum - actualSum);
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N)$
  - *Reasoning:* Single loop over array of size $N$.
- **Space Complexity:** $\mathcal{O}(1)$
  - *Reasoning:* Only uses a few primitive variables (`n`, `expectedSum`, `actualSum`).

---

### 1.3 Optimal Approach 2 (Bitwise XOR)

#### Approach & Intuition
- **Property of XOR ($\oplus$):** $x \oplus x = 0$ and $x \oplus 0 = x$.
- If we XOR all indices $0 \dots N$ together with all numbers present in `nums`, every number present twice cancels out to $0$, leaving ONLY the missing number!

#### Java Code
```java
public static int missingNumberOptimalXOR(int[] nums) {
    int n = nums.length;
    int xor = n;
    for (int i = 0; i < n; i++) {
        xor ^= i ^ nums[i];
    }
    return xor;
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N)$
- **Space Complexity:** $\mathcal{O}(1)$
- **Advantage over Sum:** No integer overflow risk!

#### Edge Cases to Mention in Interview
- `N = 1` (e.g. `[0]` $\rightarrow$ returns `1`).
- Missing number is `0` (e.g. `[1]` $\rightarrow$ returns `0`).
- Large $N$ causing Integer Overflow in Sum approach (cast to `long` or use XOR approach).

---

## 2. Two Sum

### Question
Given an integer array `nums` and a target integer `target`, determine whether two different elements sum up to `target` (or return their indices).

- **Example:** `nums = [2, 7, 11, 15], target = 9` $\rightarrow$ Output: `true` (Indices `[0, 1]`)

---

### 2.1 Brute Force Approach (Nested Loops)

#### Approach & Intuition
- Check every possible pair $(i, j)$ where $i < j$.
- Return `true` if `nums[i] + nums[j] == target`.

#### Java Code
```java
public static boolean hasTwoSumBruteForce(int[] nums, int target) {
    int n = nums.length;
    for (int i = 0; i < n; i++) {
        for (int j = i + 1; j < n; j++) {
            if (nums[i] + nums[j] == target) {
                return true;
            }
        }
    }
    return false;
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N^2)$
  - *Reasoning:* Total pairs evaluated: $\frac{N(N-1)}{2} = \mathcal{O}(N^2)$.
- **Space Complexity:** $\mathcal{O}(1)$

---

### 2.2 Optimal Approach (HashMap / HashSet)

#### Approach & Intuition
- For every element `x` at index `i`, we need a complement value $y = \text{target} - x$.
- Maintain a `HashMap` (or `HashSet`). For each element `nums[i]`:
  1. Check if `target - nums[i]` exists in our set/map.
  2. If yes, we found a valid pair!
  3. If no, insert `nums[i]` into set/map and continue.

#### Java Code
```java
public static boolean hasTwoSumOptimal(int[] nums, int target) {
    Set<Integer> seen = new HashSet<>();
    for (int num : nums) {
        int complement = target - num;
        if (seen.contains(complement)) {
            return true;
        }
        seen.add(num);
    }
    return false;
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N)$
  - *Reasoning:* Single pass through the array. HashSet `contains()` and `add()` operate in average $\mathcal{O}(1)$ time.
- **Space Complexity:** $\mathcal{O}(N)$
  - *Reasoning:* In worst case (no solution), we store $N$ elements in the HashSet.

#### Edge Cases to Mention in Interview
- Duplicate numbers (e.g. `nums = [3, 3], target = 6` $\rightarrow$ must return true).
- Negative numbers (e.g. `nums = [-3, 4, 3, 90], target = 0`).
- No matching pair exists $\rightarrow$ return `false` / empty array.

---

## 3. Maximum Difference

### Question
Find the maximum value of $A[j] - A[i]$ such that $j > i$.

- **Example 1:** `A = [7, 1, 5, 3, 6, 4]` $\rightarrow$ Output: `5` (Buy at $A[1]=1$, sell at $A[4]=6$, diff $= 6 - 1 = 5$)
- **Example 2:** `A = [9, 7, 4, 1]` $\rightarrow$ Output: `-2` or max negative difference depending on specification.

---

### 3.1 Brute Force Approach (Nested Loops)

#### Approach & Intuition
- For every index `i`, check all subsequent indices `j > i`.
- Compute `A[j] - A[i]` and keep track of the maximum value seen.

#### Java Code
```java
public static int maxDiffBruteForce(int[] nums) {
    if (nums == null || nums.length < 2) return 0;
    int maxDiff = Integer.MIN_VALUE;
    for (int i = 0; i < nums.length - 1; i++) {
        for (int j = i + 1; j < nums.length; j++) {
            maxDiff = Math.max(maxDiff, nums[j] - nums[i]);
        }
    }
    return maxDiff;
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N^2)$
- **Space Complexity:** $\mathcal{O}(1)$

---

### 3.2 Optimal Approach (Single Pass - Tracking Minimum Element)

#### Approach & Intuition
- To maximize $A[j] - A[i]$ at any index $j$, we should subtract the **smallest element seen so far** prior to index $j$.
- Maintain `minElement` (initialized to `nums[0]`).
- Iterate $j$ from $1$ to $N-1$:
  1. Calculate current candidate difference: $\text{diff} = \text{nums}[j] - \text{minElement}$.
  2. Update `maxDiff = Math.max(maxDiff, diff)`.
  3. Update `minElement = Math.min(minElement, nums[j])`.

#### Java Code
```java
public static int maxDiffOptimal(int[] nums) {
    if (nums == null || nums.length < 2) return 0;
    int minElement = nums[0];
    int maxDiff = nums[1] - nums[0];

    for (int j = 1; j < nums.length; j++) {
        maxDiff = Math.max(maxDiff, nums[j] - minElement);
        minElement = Math.min(minElement, nums[j]);
    }
    return maxDiff;
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N)$
  - *Reasoning:* One linear scan from left to right.
- **Space Complexity:** $\mathcal{O}(1)$
  - *Reasoning:* Constant extra variables (`minElement`, `maxDiff`).

#### Edge Cases to Mention in Interview
- Array size $< 2$ (Invalid input $\rightarrow$ return default like `0`).
- Strictly decreasing values (e.g. `[10, 8, 5, 2]` $\rightarrow$ maximum difference is negative, e.g. `-2`). Clarify with interviewer if negative profit is allowed or if 0 should be returned.

---

## 4. Contains Duplicate

### Question
Determine whether an integer array contains any duplicate value.

- **Example 1:** `nums = [1, 2, 3, 1]` $\rightarrow$ Output: `true`
- **Example 2:** `nums = [1, 2, 3, 4]` $\rightarrow$ Output: `false`

---

### 4.1 Brute Force Approach (Nested Loops)

#### Approach & Intuition
- Compare every pair $(i, j)$ where $i \neq j$.
- If `nums[i] == nums[j]`, return `true`.

#### Java Code
```java
public static boolean containsDuplicateBruteForce(int[] nums) {
    int n = nums.length;
    for (int i = 0; i < n; i++) {
        for (int j = i + 1; j < n; j++) {
            if (nums[i] == nums[j]) return true;
        }
    }
    return false;
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N^2)$
- **Space Complexity:** $\mathcal{O}(1)$

---

### 4.2 Alternative Sorting Approach

#### Approach & Intuition
- Sort the array. Duplicate elements will become adjacent.
- Scan array once and check if `nums[i] == nums[i-1]`.

#### Java Code
```java
public static boolean containsDuplicateSorting(int[] nums) {
    Arrays.sort(nums);
    for (int i = 1; i < nums.length; i++) {
        if (nums[i] == nums[i - 1]) return true;
    }
    return false;
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N \log N)$
- **Space Complexity:** $\mathcal{O}(1)$ extra space (if mutating array) or $\mathcal{O}(N)$ if copying.

---

### 4.3 Optimal Approach (HashSet)

#### Approach & Intuition
- Add elements one by one into a `HashSet`.
- The `set.add(num)` method returns `false` if the element is already present in the set.

#### Java Code
```java
public static boolean containsDuplicateOptimal(int[] nums) {
    Set<Integer> seen = new HashSet<>();
    for (int num : nums) {
        if (!seen.add(num)) {
            return true; // Duplicate found!
        }
    }
    return false;
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N)$ expected time
  - *Reasoning:* Inserting into and querying a HashSet takes average $\mathcal{O}(1)$ time.
- **Space Complexity:** $\mathcal{O}(N)$
  - *Reasoning:* In worst case, storing all unique elements requires $\mathcal{O}(N)$ space.

---

## Day 1 Summary & Patterns Cheatsheet

| Problem | Brute Force Time | Optimal Time | Optimal Space | Key Technique / Pattern |
| :--- | :--- | :--- | :--- | :--- |
| **1. Missing Number** | $\mathcal{O}(N \log N)$ (Sort) | $\mathcal{O}(N)$ | $\mathcal{O}(1)$ | Gauss Sum / Bitwise XOR |
| **2. Two Sum** | $\mathcal{O}(N^2)$ (Nested) | $\mathcal{O}(N)$ | $\mathcal{O}(N)$ | HashSet complement lookup |
| **3. Max Difference** | $\mathcal{O}(N^2)$ (Nested) | $\mathcal{O}(N)$ | $\mathcal{O}(1)$ | Single pass min-element tracking |
| **4. Contains Duplicate** | $\mathcal{O}(N^2)$ (Nested) | $\mathcal{O}(N)$ | $\mathcal{O}(N)$ | HashSet for uniqueness check |

---

### Key Takeaways for Codility / Technical Interviews
1. **Always mention Brute Force first:** Shows structured thinking before diving into optimal code.
2. **Space-Time Tradeoff:** Using extra space (HashSet $\mathcal{O}(N)$) often reduces time complexity from $\mathcal{O}(N^2)$ to $\mathcal{O}(N)$.
3. **Beware of Integer Overflow:** When summing elements or computing formula $\frac{N(N+1)}{2}$, cast variables to `long`.
4. **XOR Trick:** $x \oplus x = 0$ is a classic pattern for single/missing element problems!
