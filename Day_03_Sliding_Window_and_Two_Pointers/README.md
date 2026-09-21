# Day 3 --- Sliding Window & Two Pointers (Interview Study Notes)

> **Focus:** Dynamic Sliding Window, Fixed-Size Sliding Window, Two-Pointer (Opposite & Same Direction) Partitioning, and In-Place Array Manipulations.  
> **Target Platform:** Codility / MoneyLion Technical Assessment

---

## Table of Contents
1. [Problem 9: Longest Substring Without Repeating Characters](#9-longest-substring-without-repeating-characters)
2. [Problem 10: Maximum Sum Subarray of Size K](#10-maximum-sum-subarray-of-size-k)
3. [Problem 11: Pair With Given Difference](#11-pair-with-given-difference)
4. [Problem 12: Move Zeroes](#12-move-zeroes)
5. [Day 3 Summary & Patterns Cheatsheet](#day-3-summary--patterns-cheatsheet)

---

## 9. Longest Substring Without Repeating Characters

### Question
Given a string `s`, find the length of the longest substring containing no repeating characters.

- **Example 1:** `s = "abcabcbb"` $\rightarrow$ Output: `3` (The answer is `"abc"`, with length 3)
- **Example 2:** `s = "bbbbb"` $\rightarrow$ Output: `1` (The answer is `"b"`, with length 1)
- **Example 3:** `s = "pwwkew"` $\rightarrow$ Output: `3` (The answer is `"wke"`, with length 3)

---

### 9.1 Brute Force Approach

#### Approach & Intuition
- Generate all possible contiguous substrings of `s` using nested loops `i` and `j`.
- For each substring `s[i..j]`, check whether all characters are unique using a `HashSet`.
- Track the maximum length observed.

#### Java Code
```java
public static int longestSubstringBruteForce(String s) {
    if (s == null || s.isEmpty()) return 0;
    int n = s.length();
    int maxLength = 0;

    for (int i = 0; i < n; i++) {
        for (int j = i; j < n; j++) {
            if (allUnique(s, i, j)) {
                maxLength = Math.max(maxLength, j - i + 1);
            }
        }
    }
    return maxLength;
}

private static boolean allUnique(String s, int start, int end) {
    Set<Character> set = new HashSet<>();
    for (int i = start; i <= end; i++) {
        if (!set.add(s.charAt(i))) return false;
    }
    return true;
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N^3)$ (or $\mathcal{O}(N^2)$ if building set iteratively).
  - *Reasoning:* There are $\mathcal{O}(N^2)$ substrings, and checking uniqueness takes up to $\mathcal{O}(N)$ time.
- **Space Complexity:** $\mathcal{O}(\min(N, M))$ where $M$ is the size of the character set.

---

### 9.2 Optimal Approach 1 (Dynamic Sliding Window with HashSet)

#### Approach & Intuition
- Maintain a dynamic window $[left, right]$ and a `HashSet` of characters present inside the current window.
- Expand `right` one character at a time.
- If `s.charAt(right)` is already in the set, contract the window from the left by removing `s.charAt(left)` and incrementing `left++` until `s.charAt(right)` can be safely added.
- Update `maxLength = Math.max(maxLength, right - left + 1)`.

#### Java Code
```java
public static int longestSubstringSlidingWindow(String s) {
    if (s == null || s.isEmpty()) return 0;
    int n = s.length();
    Set<Character> seen = new HashSet<>();
    int maxLength = 0, left = 0;

    for (int right = 0; right < n; right++) {
        char ch = s.charAt(right);
        while (seen.contains(ch)) {
            seen.remove(s.charAt(left));
            left++;
        }
        seen.add(ch);
        maxLength = Math.max(maxLength, right - left + 1);
    }
    return maxLength;
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N)$
  - *Reasoning:* Each character is visited at most twice (once by `right` pointer and once by `left` pointer).
- **Space Complexity:** $\mathcal{O}(\min(N, M))$
  - *Reasoning:* Set stores unique characters in current window (bounded by alphabet size $M$, e.g., 128 for ASCII).

---

### 9.3 Optimal Approach 2 (Direct Index Jump using ASCII Array)

#### Approach & Intuition
- Instead of shrinking the `left` pointer one step at a time, store the **last seen index** of each character in an array `lastSeen[128]`.
- When encountering a repeated character at `right`, directly jump `left = Math.max(left, lastSeen[ch] + 1)`.

#### Java Code
```java
public static int longestSubstringOptimized(String s) {
    if (s == null || s.isEmpty()) return 0;
    int n = s.length();
    int maxLength = 0, left = 0;
    int[] lastSeen = new int[128];
    Arrays.fill(lastSeen, -1);

    for (int right = 0; right < n; right++) {
        char ch = s.charAt(right);
        if (lastSeen[ch] >= left) {
            left = lastSeen[ch] + 1;
        }
        lastSeen[ch] = right;
        maxLength = Math.max(maxLength, right - left + 1);
    }
    return maxLength;
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N)$ (strict single pass; `left` pointer jumps instantly).
- **Space Complexity:** $\mathcal{O}(M)$ fixed auxiliary space ($\mathcal{O}(128) = \mathcal{O}(1)$ for standard ASCII).

#### Edge Cases to Mention in Interview
- Empty string `""` $\rightarrow$ returns `0`.
- Single character `"a"` or all identical `"aaaaa"` $\rightarrow$ handled cleanly.
- String with spaces, numbers, or symbols $\rightarrow$ handle via size-128 or size-256 array.

---

## 10. Maximum Sum Subarray of Size K

### Question
Given an integer array `nums` and an integer `K`, find the maximum sum of any contiguous subarray of size `K`.

- **Example 1:** `nums = [2, 1, 5, 1, 3, 2], K = 3` $\rightarrow$ Output: `9` (Subarray `[5, 1, 3]`)
- **Example 2:** `nums = [2, 3, 4, 1, 5], K = 2` $\rightarrow$ Output: `7` (Subarray `[3, 4]`)

---

### 10.1 Brute Force Approach

#### Approach & Intuition
- Evaluate all contiguous subarrays of length `K` starting from index `0` up to `N - K`.
- Sum up all elements in each subarray and track the maximum sum.

#### Java Code
```java
public static long maxSumSubarrayBruteForce(int[] nums, int k) {
    if (nums == null || nums.length < k || k <= 0) return 0;
    long maxSum = Long.MIN_VALUE;

    for (int i = 0; i <= nums.length - k; i++) {
        long currentSum = 0;
        for (int j = i; j < i + k; j++) {
            currentSum += nums[j];
        }
        maxSum = Math.max(maxSum, currentSum);
    }
    return maxSum;
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N \times K)$
  - *Reasoning:* $(N - K + 1)$ windows, each taking $K$ steps to sum.
- **Space Complexity:** $\mathcal{O}(1)$

---

### 10.2 Optimal Approach (Fixed-Size Sliding Window)

#### Approach & Intuition
- Compute the sum of the first window of size `K` (indices $0 \dots K-1$).
- Slide the window from $i = K$ to $N-1$:
  - Add incoming element `nums[i]` and subtract outgoing element `nums[i - K]`.
  - Update `maxSum = Math.max(maxSum, windowSum)`.

#### Java Code
```java
public static long maxSumSubarraySlidingWindow(int[] nums, int k) {
    if (nums == null || nums.length < k || k <= 0) {
        throw new IllegalArgumentException("Invalid input array or subarray size K.");
    }
    long windowSum = 0;
    for (int i = 0; i < k; i++) {
        windowSum += nums[i];
    }

    long maxSum = windowSum;
    for (int i = k; i < nums.length; i++) {
        windowSum += nums[i] - nums[i - k];
        maxSum = Math.max(maxSum, windowSum);
    }
    return maxSum;
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N)$
  - *Reasoning:* Compute initial sum in $K$ steps, then $(N-K)$ constant-time updates.
- **Space Complexity:** $\mathcal{O}(1)$

#### Edge Cases to Mention in Interview
- `nums.length < K` or `K <= 0` $\rightarrow$ throw exception or return invalid indicator.
- Array contains negative numbers $\rightarrow$ initialize `maxSum` to `windowSum` (not `0`).
- Sum overflow for large arrays/integers $\rightarrow$ use `long` for `windowSum` and `maxSum`.

---

## 11. Pair With Given Difference

### Question
Determine whether an array contains two distinct indices `i` and `j` ($i \neq j$) such that $|A[j] - A[i]| = K$.

- **Example 1:** `nums = [5, 20, 3, 2, 50, 80], K = 78` $\rightarrow$ Output: `true` ($80 - 2 = 78$)
- **Example 2:** `nums = [90, 70, 20, 80, 50], K = 45` $\rightarrow$ Output: `false`

---

### 11.1 Brute Force Approach

#### Approach & Intuition
- Check every pair $(i, j)$ with $i < j$.
- Return `true` if $|nums[i] - nums[j]| == K$.

#### Java Code
```java
public static boolean hasPairWithDiffBruteForce(int[] nums, int k) {
    if (nums == null || nums.length < 2) return false;
    k = Math.abs(k);
    for (int i = 0; i < nums.length; i++) {
        for (int j = i + 1; j < nums.length; j++) {
            if (Math.abs(nums[i] - nums[j]) == k) return true;
        }
    }
    return false;
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N^2)$
- **Space Complexity:** $\mathcal{O}(1)$

---

### 11.2 Sorting + Two Pointers Approach

#### Approach & Intuition
- Sort the array in ascending order.
- Maintain two pointers `left = 0` and `right = 1` moving in the same direction:
  - Calculate `diff = sorted[right] - sorted[left]`.
  - If `diff == K` and `left != right`: return `true`.
  - If `diff < K` or `left == right`: increment `right++`.
  - If `diff > K`: increment `left++`.

#### Java Code
```java
public static boolean hasPairWithDiffTwoPointers(int[] nums, int k) {
    if (nums == null || nums.length < 2) return false;
    k = Math.abs(k);
    int[] sorted = nums.clone();
    Arrays.sort(sorted);

    int left = 0, right = 1;
    while (left < sorted.length && right < sorted.length) {
        if (left != right) {
            int diff = sorted[right] - sorted[left];
            if (diff == k) return true;
            else if (diff < k) right++;
            else left++;
        } else {
            right++;
        }
    }
    return false;
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N \log N)$ (dominated by `Arrays.sort()`).
- **Space Complexity:** $\mathcal{O}(1)$ auxiliary (or $\mathcal{O}(N)$ if cloning array).

---

### 11.3 Optimal Approach (HashSet Lookup)

#### Approach & Intuition
- As we iterate through each element `x`, we need a previously seen element `y` such that $|x - y| = K \implies y = x - K$ or $y = x + K$.
- For each element `num`, check if `seen.contains(num - K)` or `seen.contains(num + K)`. If found, return `true`. Otherwise, insert `num` into `HashSet`.

#### Java Code
```java
public static boolean hasPairWithDiffHashSet(int[] nums, int k) {
    if (nums == null || nums.length < 2) return false;
    k = Math.abs(k);
    Set<Integer> seen = new HashSet<>();

    for (int num : nums) {
        if (seen.contains(num - k) || seen.contains(num + k)) {
            return true;
        }
        seen.add(num);
    }
    return false;
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N)$ expected time.
- **Space Complexity:** $\mathcal{O}(N)$ for the HashSet.

#### Edge Cases to Mention in Interview
- $K = 0$: Requires two identical values at different indices (`seen.contains(num - 0)` check works automatically!).
- Negative numbers in array (e.g. `nums = [-10, 20, 30, -40], K = 70` $\rightarrow 30 - (-40) = 70 \rightarrow$ `true`).
- $K < 0$: Use `Math.abs(k)` at the start since absolute difference is non-negative.

---

## 12. Move Zeroes

### Question
Given an integer array `nums`, move all `0`s to the end of it while maintaining the relative order of the non-zero elements in-place.

- **Example 1:** `nums = [0, 1, 0, 3, 12]` $\rightarrow$ Output: `[1, 3, 12, 0, 0]`
- **Example 2:** `nums = [0]` $\rightarrow$ Output: `[0]`

---

### 12.1 Auxiliary Array Approach

#### Approach & Intuition
- Create a new array `temp` of size `N`. Copy all non-zero elements into `temp`, leaving remaining positions as `0`. Copy `temp` back into `nums`.

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N)$
- **Space Complexity:** $\mathcal{O}(N)$ auxiliary space (violates in-place requirement if strict).

---

### 12.2 Two-Pass In-Place Overwriting Approach

#### Approach & Intuition
- **Pass 1:** Maintain `writeIndex = 0`. Iterate through `nums`. Whenever `nums[i] != 0`, write `nums[writeIndex++] = nums[i]`.
- **Pass 2:** Fill all remaining indices from `writeIndex` to `N-1` with `0`.

#### Java Code
```java
public static void moveZeroesTwoPass(int[] nums) {
    if (nums == null || nums.length <= 1) return;
    int writeIndex = 0;

    for (int num : nums) {
        if (num != 0) {
            nums[writeIndex++] = num;
        }
    }
    while (writeIndex < nums.length) {
        nums[writeIndex++] = 0;
    }
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N)$
- **Space Complexity:** $\mathcal{O}(1)$ auxiliary space.

---

### 12.3 Optimal Approach (Single-Pass Partitioning Swap)

#### Approach & Intuition
- Maintain a pointer `lastNonZeroFoundAt = 0`.
- Iterate `cur` from `0` to `N-1`.
- When `nums[cur] != 0`, swap `nums[cur]` with `nums[lastNonZeroFoundAt]`, then increment `lastNonZeroFoundAt++`.
- **Advantage:** Minimizes array write operations when array contains mostly zeroes!

#### Java Code
```java
public static void moveZeroesOptimalSwap(int[] nums) {
    if (nums == null || nums.length <= 1) return;
    int lastNonZeroFoundAt = 0;

    for (int cur = 0; cur < nums.length; cur++) {
        if (nums[cur] != 0) {
            if (cur != lastNonZeroFoundAt) {
                int temp = nums[lastNonZeroFoundAt];
                nums[lastNonZeroFoundAt] = nums[cur];
                nums[cur] = temp;
            }
            lastNonZeroFoundAt++;
        }
    }
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N)$
- **Space Complexity:** $\mathcal{O}(1)$

#### Edge Cases to Mention in Interview
- Array with no zeroes (`[1, 2, 3]`) $\rightarrow$ no unnecessary swaps made (`cur == lastNonZeroFoundAt`).
- Array with all zeroes (`[0, 0, 0]`) $\rightarrow$ no swaps performed.
- Zeroes already at the end (`[1, 2, 0, 0]`) $\rightarrow$ handled correctly.

---

## Day 3 Summary & Patterns Cheatsheet

| Problem | Brute Force Time | Optimal Time | Optimal Space | Key Pattern / Core Concept |
| :--- | :--- | :--- | :--- | :--- |
| **9. Longest Substring Without Repeating Characters** | $\mathcal{O}(N^3)$ | $\mathcal{O}(N)$ | $\mathcal{O}(M)$ | Dynamic Sliding Window + HashSet / Direct Index Array |
| **10. Maximum Sum Subarray of Size K** | $\mathcal{O}(N \cdot K)$ | $\mathcal{O}(N)$ | $\mathcal{O}(1)$ | Fixed-Size Sliding Window |
| **11. Pair With Given Difference** | $\mathcal{O}(N^2)$ | $\mathcal{O}(N)$ expected ($\mathcal{O}(N \log N)$ two-pointers) | $\mathcal{O}(N)$ (or $\mathcal{O}(1)$ space for two-pointers) | HashSet Complement ($x \pm K$) OR Same-Direction Two Pointers |
| **12. Move Zeroes** | $\mathcal{O}(N)$ space | $\mathcal{O}(N)$ time | $\mathcal{O}(1)$ space | In-Place Two Pointers / Partitioning Swap |

---

### Key Takeaways for Codility / Technical Interviews
1. **Dynamic vs. Fixed Window:**
   - **Fixed Window (Problem 10):** Window size $K$ is constant. Slide by adding new element and subtracting old element ($+A[i] - A[i-K]$).
   - **Dynamic Window (Problem 9):** Window grows/shrinks dynamically based on validation constraints (e.g. uniqueness).
2. **Two Pointers Directions:**
   - **Opposite Direction:** Used on sorted arrays for Two Sum ($left=0, right=N-1$).
   - **Same Direction (Problem 11 & 12):** Used for differences on sorted arrays or in-place element partitioning ($slow, fast$).
3. **Space-Time Tradeoffs:**
   - Problem 11 can be solved in $\mathcal{O}(N \log N)$ time with $\mathcal{O}(1)$ extra space via Sorting, OR in $\mathcal{O}(N)$ time with $\mathcal{O}(N)$ extra space via HashSet. Always present both options to demonstrate flexibility to the interviewer!
