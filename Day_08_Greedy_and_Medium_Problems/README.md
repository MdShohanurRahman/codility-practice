# Day 8 --- Greedy & Medium Problems (Interview Study Notes)

> **Focus:** Greedy Choice Property, Range Window Boundaries, Single-Pass Running State Tracking, and Prefix/Suffix Product Accumulation.  
> **Target Platform:** Codility / MoneyLion Technical Assessment

---

## Table of Contents
1. [Problem 29: Maximum Non-Overlapping Activities](#29-maximum-non-overlapping-activities)
2. [Problem 30: Minimum Jumps](#30-minimum-jumps)
3. [Problem 31: Best Time to Buy and Sell Stock](#31-best-time-to-buy-and-sell-stock)
4. [Problem 32: Product of Array Except Self](#32-product-of-array-except-self)
5. [Day 8 Summary & Patterns Cheatsheet](#day-8-summary--patterns-cheatsheet)

---

## 29. Maximum Non-Overlapping Activities

### Question
Given start and end times of activities, select the maximum number of mutually non-overlapping activities.

- **Example 1:** `intervals = [[1, 3], [2, 4], [3, 6], [5, 7], [8, 9], [5, 9]]` $\rightarrow$ Output: `3` (e.g., `[1, 3]`, `[3, 6]`, `[8, 9]`)
- **Example 2:** `intervals = [[1, 2], [2, 3], [3, 4]]` $\rightarrow$ Output: `3` (Activities touching at boundaries are considered non-overlapping)
- **Example 3:** `intervals = [[1, 10], [2, 3], [4, 5], [6, 7]]` $\rightarrow$ Output: `3`

---

### 29.1 Brute Force Approach (Recursive Subsets / Backtracking)

#### Approach & Intuition
- Generate all $2^N$ possible subsets of activities.
- For each subset, check if any two activities overlap.
- Keep track of the maximum size among all valid non-overlapping subsets.

#### Java Code
```java
public static int maxActivitiesBruteForce(int[][] intervals) {
    if (intervals == null || intervals.length == 0) return 0;
    
    Activity[] activities = new Activity[intervals.length];
    for (int i = 0; i < intervals.length; i++) {
        activities[i] = new Activity(intervals[i][0], intervals[i][1]);
    }

    return findMaxHelper(activities, 0, -1);
}

private static int findMaxHelper(Activity[] activities, int index, int lastEndTime) {
    if (index == activities.length) {
        return 0;
    }

    // Option 1: Skip current activity
    int exclude = findMaxHelper(activities, index + 1, lastEndTime);

    // Option 2: Include current activity (if non-overlapping)
    int include = 0;
    if (activities[index].start >= lastEndTime) {
        include = 1 + findMaxHelper(activities, index + 1, activities[index].end);
    }

    return Math.max(include, exclude);
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(2^N)$
  - *Reasoning:* Evaluates include/exclude decisions for all $N$ activities.
- **Space Complexity:** $\mathcal{O}(N)$
  - *Reasoning:* Recursion call stack depth reaches up to $N$.

---

### 29.2 Optimal Approach (Greedy - Sort by Finish Time)

#### Approach & Intuition
- **Greedy Choice Property:** To maximize the total number of activities selected, always pick the activity that **finishes earliest**. Finishing early leaves the maximum possible remaining time for subsequent activities.
- Steps:
  1. Sort all activities by their **end time** in ascending order.
  2. Select the first activity (which finishes earliest).
  3. Iterate through remaining activities: if `next.start >= lastSelected.end`, select the next activity and update `lastSelected.end = next.end`.

#### Java Code
```java
public static int maxActivitiesOptimal(int[][] intervals) {
    if (intervals == null || intervals.length == 0) return 0;

    Activity[] activities = new Activity[intervals.length];
    for (int i = 0; i < intervals.length; i++) {
        activities[i] = new Activity(intervals[i][0], intervals[i][1]);
    }

    // Sort by finish time (end time)
    Arrays.sort(activities, Comparator.comparingInt(a -> a.end));

    int count = 1;
    int lastEndTime = activities[0].end;

    for (int i = 1; i < activities.length; i++) {
        if (activities[i].start >= lastEndTime) {
            count++;
            lastEndTime = activities[i].end;
        }
    }

    return count;
}

public static int maxActivities(int[][] intervals) {
    // 1. Sort by end time
    Arrays.sort(intervals, Comparator.comparingInt(a -> a[1]));

    int count = 0;
    int previousEnd = Integer.MIN_VALUE;

    // 2. Greedily select activities
    for (int[] interval : intervals) {
        int start = interval[0];
        int end = interval[1];
        // Activity doesn't overlap with previous selected activity
        if (start >= previousEnd) {
            count++;
            previousEnd = end;
        }
    }
    return count;
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N \log N)$
  - *Reasoning:* Sorting the $N$ intervals takes $\mathcal{O}(N \log N)$ time. The linear pass takes $\mathcal{O}(N)$ time.
- **Space Complexity:** $\mathcal{O}(N)$
  - *Reasoning:* Storage for the `Activity` object array (or $\mathcal{O}(\log N)$ for primitive sorting).

#### Edge Cases to Mention in Interview
- **Boundary Touching:** `[1, 2]` and `[2, 3]` $\rightarrow$ valid because `start >= end` (`2 >= 2`).
- **Enclosing Intervals:** `[1, 10]` vs `[2, 3]` $\rightarrow$ sorting by end time ensures `[2, 3]` is selected first over `[1, 10]`.
- **Single Activity:** Array of length 1 returns `1`.

---

## 30. Minimum Jumps

### Question
Given an array `nums` of non-negative integers where `nums[i]` represents the maximum jump length from position $i$, return the minimum number of jumps required to reach the last index (`N - 1`).

- **Example 1:** `nums = [2, 3, 1, 1, 4]` $\rightarrow$ Output: `2` (Jump 1 step from index 0 to 1, then 3 steps to index 4)
- **Example 2:** `nums = [2, 3, 0, 1, 4]` $\rightarrow$ Output: `2`
- **Example 3:** `nums = [1, 1, 1, 1]` $\rightarrow$ Output: `3`

---

### 30.1 Brute Force / Dynamic Programming Approach

#### Approach & Intuition
- Use dynamic programming where `dp[i]` represents the minimum jumps to reach `N - 1` from index $i$.
- `dp[N - 1] = 0`. For $i = N - 2$ down to $0$:
  - `dp[i] = 1 + min(dp[i + step])` for all $1 \le \text{step} \le \text{nums}[i]$.

#### Java Code
```java
public static int minJumpsDP(int[] nums) {
    if (nums == null || nums.length <= 1) return 0;

    int n = nums.length;
    int[] dp = new int[n];
    Arrays.fill(dp, Integer.MAX_VALUE - 1);
    dp[n - 1] = 0;

    for (int i = n - 2; i >= 0; i--) {
        int maxJump = nums[i];
        for (int step = 1; step <= maxJump && (i + step) < n; step++) {
            dp[i] = Math.min(dp[i], 1 + dp[i + step]);
        }
    }

    return dp[0] >= Integer.MAX_VALUE - 1 ? -1 : dp[0];
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N^2)$
  - *Reasoning:* For each index $i$, we inspect up to $N$ forward jump targets.
- **Space Complexity:** $\mathcal{O}(N)$
  - *Reasoning:* Requires a 1D DP table of size $N$.

---

### 30.2 Optimal Approach (Greedy Range Window / BFS)

#### Approach & Intuition
- Treat the problem as a BFS traversal where each jump defines a level window `[currentStart, currentEnd]`.
- Maintain three variables:
  - `jumps`: Total jumps taken so far.
  - `currentEnd`: Boundary end index reachable with the current number of jumps.
  - `farthest`: Furthest index reachable from any position in the current range window.
- Traverse index $i$ from $0$ to $N - 2$:
  - Update `farthest = Math.max(farthest, i + nums[i])`.
  - When $i == \text{currentEnd}$, we must make another jump:
    - Increment `jumps++`.
    - Advance `currentEnd = farthest`.
    - If `currentEnd >= N - 1`, we can stop early.

#### Java Code
```java
public static int minJumpsOptimal(int[] nums) {
    if (nums == null || nums.length <= 1) return 0;

    int jumps = 0;
    int currentEnd = 0;
    int farthest = 0;

    for (int i = 0; i < nums.length - 1; i++) {
        farthest = Math.max(farthest, i + nums[i]);

        // Hit boundary of current jump range window
        if (i == currentEnd) {
            jumps++;
            currentEnd = farthest;

            if (currentEnd >= nums.length - 1) {
                break;
            }
        }
    }

    return currentEnd >= nums.length - 1 ? jumps : -1;
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N)$
  - *Reasoning:* Single linear pass over the array of size $N$.
- **Space Complexity:** $\mathcal{O}(1)$ auxiliary space
  - *Reasoning:* Uses constant extra variables (`jumps`, `currentEnd`, `farthest`).

#### Edge Cases to Mention in Interview
- **Already at Destination:** `nums = [0]` $\rightarrow$ loop doesn't execute (`length - 1 = 0`), returns `0` jumps.
- **Single Step Array:** `nums = [1, 1, 1]` $\rightarrow$ correctly computes `2` jumps.
- **Unreachable End:** `nums = [0, 1, 2]` $\rightarrow$ `currentEnd` stays `0`, correctly returns `-1`.

---

## 31. Best Time to Buy and Sell Stock

### Question
Given daily stock prices, calculate the maximum profit achievable from buying one stock on a given day and selling it on a future day. Return `0` if no profit is possible.

- **Example 1:** `prices = [7, 1, 5, 3, 6, 4]` $\rightarrow$ Output: `5` (Buy on day 2 at price 1, sell on day 5 at price 6)
- **Example 2:** `prices = [7, 6, 4, 3, 1]` $\rightarrow$ Output: `0` (Prices decline continuously)
- **Example 3:** `prices = [1, 2, 3, 4, 5]` $\rightarrow$ Output: `4`

---

### 31.1 Brute Force Approach

#### Approach & Intuition
- Check every pair of days $(i, j)$ where $j > i$.
- Compute `profit = prices[j] - prices[i]`.
- Return the maximum profit found across all valid pairs.

#### Java Code
```java
public static int maxProfitBruteForce(int[] prices) {
    if (prices == null || prices.length <= 1) return 0;

    int maxProfit = 0;
    int n = prices.length;

    for (int i = 0; i < n; i++) {
        for (int j = i + 1; j < n; j++) {
            int profit = prices[j] - prices[i];
            if (profit > maxProfit) {
                maxProfit = profit;
            }
        }
    }

    return maxProfit;
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N^2)$
  - *Reasoning:* Evaluates $\frac{N(N-1)}{2}$ price comparison pairs.
- **Space Complexity:** $\mathcal{O}(1)$ auxiliary space.

---

### 31.2 Optimal Approach (Single-Pass Running Minimum Tracking)

#### Approach & Intuition
- Maintain a running minimum `minPrice` encountered so far.
- For each price on day $i$:
  - If `price < minPrice`, update `minPrice = price`.
  - Else, calculate potential profit `price - minPrice` and update `maxProfit = Math.max(maxProfit, price - minPrice)`.

#### Java Code
```java
public static int maxProfitOptimal(int[] prices) {
    if (prices == null || prices.length <= 1) return 0;

    int minPrice = Integer.MAX_VALUE;
    int maxProfit = 0;

    for (int price : prices) {
        if (price < minPrice) {
            minPrice = price; // Update lowest buying price seen so far
        } else if (price - minPrice > maxProfit) {
            maxProfit = price - minPrice; // Update maximum profit
        }
    }

    return maxProfit;
}

public static int maxProfit(int[] prices) {

    if (prices == null || prices.length < 2) {
        return 0;
    }

    int minPrice = prices[0];
    int maxProfit = 0;

    for (int i = 1; i < prices.length; i++) {

        // If we sell today, what is the profit?
        int profit = prices[i] - minPrice;

        // Keep the maximum profit
        maxProfit = Math.max(maxProfit, profit);

        // Keep the lowest buying price
        minPrice = Math.min(minPrice, prices[i]);
    }

    return maxProfit;
}

```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N)$
  - *Reasoning:* Single linear scan through the array of length $N$.
- **Space Complexity:** $\mathcal{O}(1)$ auxiliary space
  - *Reasoning:* Stores only two primitive integer variables (`minPrice` and `maxProfit`).

#### Edge Cases to Mention in Interview
- **Monotonically Decreasing Prices:** `[7, 6, 5, 4]` $\rightarrow$ `maxProfit` remains `0`.
- **Flat Prices:** `[5, 5, 5, 5]` $\rightarrow$ returns `0`.
- **Empty or Single Day Array:** `[]` or `[5]` $\rightarrow$ returns `0`.

---

## 32. Product of Array Except Self

### Question
Given an integer array `nums`, return an array `answer` such that `answer[i]` is equal to the product of all elements of `nums` except `nums[i]`, **without using division**, in $\mathcal{O}(N)$ time.

- **Example 1:** `nums = [1, 2, 3, 4]` $\rightarrow$ Output: `[24, 12, 8, 6]`
- **Example 2:** `nums = [-1, 1, 0, -3, 3]` $\rightarrow$ Output: `[0, 0, 9, 0, 0]`
- **Example 3:** `nums = [5, 2]` $\rightarrow$ Output: `[2, 5]`

---

### 32.1 Brute Force Approach

#### Approach & Intuition
- For each position $i$, iterate over all indices $j \neq i$ and calculate the product $\prod_{j \neq i} \text{nums}[j]$.

#### Java Code
```java
public static int[] productExceptSelfBruteForce(int[] nums) {
    if (nums == null || nums.length == 0) return new int[0];

    int n = nums.length;
    int[] result = new int[n];

    for (int i = 0; i < n; i++) {
        int prod = 1;
        for (int j = 0; j < n; j++) {
            if (i != j) {
                prod *= nums[j];
            }
        }
        result[i] = prod;
    }

    return result;
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N^2)$
- **Space Complexity:** $\mathcal{O}(1)$ auxiliary space (excluding result array).

---

### 32.2 Optimal Approach (Prefix and Suffix Product Pass)

#### Approach & Intuition
- For any index $i$, the product of all elements except `nums[i]` is equal to:
  $$\text{Product Except Self}[i] = (\text{Product of all elements to left of } i) \times (\text{Product of all elements to right of } i)$$
- **Pass 1 (Left Pass):** Build prefix products directly into the `result` array:
  - `result[0] = 1`
  - `result[i] = result[i - 1] * nums[i - 1]` for $i = 1 \dots N - 1$.
- **Pass 2 (Right Pass):** Maintain a running variable `rightProduct`:
  - Traverse from $i = N - 1$ down to $0$:
    - Multiply `result[i] *= rightProduct`.
    - Update `rightProduct *= nums[i]`.

#### Java Code
```java
public static int[] productExceptSelfOptimal(int[] nums) {
    if (nums == null || nums.length == 0) return new int[0];

    int n = nums.length;
    int[] result = new int[n];

    // 1. Left Pass: Populate prefix products directly into result array
    result[0] = 1;
    for (int i = 1; i < n; i++) {
        result[i] = result[i - 1] * nums[i - 1];
    }

    // 2. Right Pass: Multiply by suffix products accumulated in a single variable
    int rightProduct = 1;
    for (int i = n - 1; i >= 0; i--) {
        result[i] *= rightProduct;
        rightProduct *= nums[i];
    }

    return result;
}


public static int[] productExceptSelf(int[] nums) {

    int n = nums.length;
    int[] answer = new int[n];

    // Step 1: Store left/prefix products
    int prefix = 1;

    for (int i = 0; i < n; i++) {
        answer[i] = prefix;
        prefix *= nums[i];
    }

    // Step 2: Multiply with right/suffix products
    int suffix = 1;

    for (int i = n - 1; i >= 0; i--) {
        answer[i] *= suffix;
        suffix *= nums[i];
    }

    return answer;
}

```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N)$
  - *Reasoning:* Two linear passes over array of length $N$.
- **Space Complexity:** $\mathcal{O}(1)$ auxiliary space
  - *Reasoning:* The problem description specifies that the output array `result` does not count toward extra space complexity.

#### Edge Cases to Mention in Interview
- **Array Containing Single Zero:** `[-1, 1, 0, -3, 3]` $\rightarrow$ index with `0` gets product of non-zero elements (`9`), all other indices get `0`.
- **Array Containing Multiple Zeroes:** `[0, 1, 0, 5]` $\rightarrow$ all outputs become `0`.
- **Negative Numbers:** Handles negative products correctly without precision issue.

---

## Day 8 Summary & Patterns Cheatsheet

| Problem | Brute Force Time | Optimal Time | Optimal Space | Key Pattern / Core Concept |
| :--- | :--- | :--- | :--- | :--- |
| **29. Max Non-Overlapping Activities** | $\mathcal{O}(2^N)$ | $\mathcal{O}(N \log N)$ | $\mathcal{O}(N)$ | Greedy choice by sorting finish times (`end`) |
| **30. Minimum Jumps** | $\mathcal{O}(N^2)$ | $\mathcal{O}(N)$ | $\mathcal{O}(1)$ | Greedy Range Window / BFS jump boundary expansion |
| **31. Best Time to Buy and Sell Stock** | $\mathcal{O}(N^2)$ | $\mathcal{O}(N)$ | $\mathcal{O}(1)$ | Single pass running minimum price tracking |
| **32. Product Except Self** | $\mathcal{O}(N^2)$ | $\mathcal{O}(N)$ | $\mathcal{O}(1)$ | Two-pass Prefix and Suffix product accumulation |

---

### Key Takeaways for Codility / Technical Interviews

1. **Greedy Choice Proof by Finish Time:**
   - For activity selection and interval scheduling, sorting by **finish time** guarantees optimal greedy sub-problem choices. Sorting by start time or interval length does *not* work for max non-overlapping activities.
2. **Range Window BFS Boundary Expansion:**
   - Jump Game problems can be modeled as implicit BFS without explicit queue overhead by tracking `currentEnd` and `farthest` reach boundaries in $\mathcal{O}(N)$ time and $\mathcal{O}(1)$ space.
3. **Single-Pass State Accumulation:**
   - Tracking running minimums or maximums (like `minPrice`) eliminates nested comparison loops and reduces time complexity from $\mathcal{O}(N^2)$ to $\mathcal{O}(N)$.
4. **Prefix/Suffix Product Space Optimization:**
   - When division is forbidden or unsafe (due to zeroes), pre-calculating or accumulating left-to-right and right-to-left products allows solving range product queries in $\mathcal{O}(N)$ time and $\mathcal{O}(1)$ auxiliary space.
