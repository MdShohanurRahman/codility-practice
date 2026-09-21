# Bonus Problems (Interview Study Notes)

> **Focus:** Array Manipulation, 3-Reversal Technique, Kadane's Dynamic Programming Algorithm, Sliding Window Shrinking, Bucket Sort Frequency Counting, and Interval Sorting.  
> **Target Platform:** Codility / MoneyLion Technical Assessment

---

## Table of Contents
1. [Problem 39: Rotate Array](#39-rotate-array)
2. [Problem 40: Missing Ranges](#40-missing-ranges)
3. [Problem 41: Maximum Subarray](#41-maximum-subarray)
4. [Problem 42: Minimum Size Subarray Sum](#42-minimum-size-subarray-sum)
5. [Problem 43: Top K Frequent Elements](#43-top-k-frequent-elements)
6. [Problem 44: Meeting Rooms](#44-meeting-rooms)
7. [Bonus Problems Summary & Patterns Cheatsheet](#bonus-problems-summary--patterns-cheatsheet)

---

## 39. Rotate Array

### Question
Given an integer array `nums`, rotate the array to the right by `k` steps, where `k` is non-negative.

- **Example 1:** `nums = [1, 2, 3, 4, 5, 6, 7]`, `k = 3` $\rightarrow$ Output: `[5, 6, 7, 1, 2, 3, 4]`
- **Example 2:** `nums = [-1, -100, 3, 99]`, `k = 2` $\rightarrow$ Output: `[3, 99, -1, -100]`
- **Example 3:** `nums = [1, 2]`, `k = 5` $\rightarrow$ Output: `[2, 1]` ($k = 5 \pmod 2 = 1$)

---

### 39.1 Extra Array Approach

#### Approach & Intuition
- Create a temporary array of length $N$.
- Copy `nums[i]` to `temp[(i + k) % N]`.
- Copy `temp` back to `nums`.

#### Java Code
```java
public static void rotateExtraArray(int[] nums, int k) {
    if (nums == null || nums.length <= 1) return;

    int n = nums.length;
    k = k % n;
    if (k == 0) return;

    int[] temp = new int[n];
    for (int i = 0; i < n; i++) {
        temp[(i + k) % n] = nums[i];
    }

    for (int i = 0; i < n; i++) {
        nums[i] = temp[i];
    }
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N)$
- **Space Complexity:** $\mathcal{O}(N)$ extra space

---

### 39.2 Optimal 3-Pass Reversal Algorithm ($O(1)$ Auxiliary Space)

#### Approach & Intuition
- Normalize $k = k \pmod N$.
- **Step 1:** Reverse the entire array $0 \dots N - 1$.
- **Step 2:** Reverse the first $k$ elements $0 \dots k - 1$.
- **Step 3:** Reverse the remaining $N - k$ elements $k \dots N - 1$.

#### Step-by-Step State Trace (`nums = [1, 2, 3, 4, 5, 6, 7]`, $k = 3$)

| Action | Index Range | Input Array State | Output Array State |
| :--- | :--- | :--- | :--- |
| **1. Reverse All** | $[0, 6]$ | `[1, 2, 3, 4, 5, 6, 7]` | `[7, 6, 5, 4, 3, 2, 1]` |
| **2. Reverse First $k$** | $[0, 2]$ | `[7, 6, 5, 4, 3, 2, 1]` | `[5, 6, 7, 4, 3, 2, 1]` |
| **3. Reverse Remaining** | $[3, 6]$ | `[5, 6, 7, 4, 3, 2, 1]` | `[5, 6, 7, 1, 2, 3, 4]` |

#### Java Code
```java
public static void rotateOptimal(int[] nums, int k) {
    if (nums == null || nums.length <= 1) return;

    int n = nums.length;
    k = k % n;
    if (k == 0) return;

    // 1. Reverse entire array
    reverse(nums, 0, n - 1);
    // 2. Reverse first k elements
    reverse(nums, 0, k - 1);
    // 3. Reverse remaining n - k elements
    reverse(nums, k, n - 1);
}

private static void reverse(int[] nums, int start, int end) {
    while (start < end) {
        int temp = nums[start];
        nums[start] = nums[end];
        nums[end] = temp;
        start++;
        end--;
    }
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N)$ linear time.
- **Space Complexity:** $\mathcal{O}(1)$ auxiliary space.

---

## 40. Missing Ranges

### Question
Given a sorted unique integer array `nums` and a range `[lower, upper]`, return all missing ranges such that every number in `[lower, upper]` is either in `nums` or covered by a missing range.

- **Example 1:** `nums = [0, 1, 3, 50, 75]`, `lower = 0`, `upper = 99` $\rightarrow$ Output: `["2", "4->49", "51->74", "76->99"]`
- **Example 2:** `nums = []`, `lower = 1`, `upper = 1` $\rightarrow$ Output: `["1"]`
- **Example 3:** `nums = [-1]`, `lower = -1`, `upper = -1` $\rightarrow$ Output: `[]`

---

### 40.1 Optimal Single-Pass Linear Scan

#### Approach & Intuition
- Maintain a `prev` pointer initialized to `(long) lower - 1`.
- Iterate from $i = 0 \dots N$:
  - Set `curr = (i < N) ? nums[i] : (long) upper + 1`.
  - If `curr - prev >= 2`, a gap exists between `prev + 1` and `curr - 1`.
    - If `prev + 1 == curr - 1`, add `"prev+1"`.
    - Else, add `"prev+1 -> curr-1"`.
  - Update `prev = curr`.

#### Java Code
```java
public static List<String> findMissingRanges(int[] nums, int lower, int upper) {
    List<String> result = new ArrayList<>();
    long prev = (long) lower - 1;

    for (int i = 0; i <= (nums == null ? 0 : nums.length); i++) {
        long curr = (i < (nums == null ? 0 : nums.length)) ? nums[i] : (long) upper + 1;

        if (curr - prev >= 2) {
            result.add(formatRange(prev + 1, curr - 1));
        }
        prev = curr;
    }

    return result;
}

private static String formatRange(long start, long end) {
    return start == end ? String.valueOf(start) : start + "->" + end;
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N)$ single pass.
- **Space Complexity:** $\mathcal{O}(1)$ auxiliary space (excluding result list).

---

## 41. Maximum Subarray

### Question
Given an integer array `nums`, find the contiguous subarray (containing at least one number) which has the largest sum and return its sum.

- **Example 1:** `nums = [-2, 1, -3, 4, -1, 2, 1, -5, 4]` $\rightarrow$ Output: `6` (Subarray `[4, -1, 2, 1]`)
- **Example 2:** `nums = [1]` $\rightarrow$ Output: `1`
- **Example 3:** `nums = [5, 4, -1, 7, 8]` $\rightarrow$ Output: `23`

---

### 41.1 Optimal Approach (Kadane's Algorithm)

#### Approach & Intuition
- At element `nums[i]`, decide whether to:
  1. Extend the existing running sum (`currentSum + nums[i]`).
  2. Start a fresh subarray at `nums[i]`.
- Recurrence:
  $$\text{currentSum} = \max(\text{nums}[i],\ \text{currentSum} + \text{nums}[i])$$
  $$\text{maxSoFar} = \max(\text{maxSoFar},\ \text{currentSum})$$

#### Java Code
```java
public static int maxSubArrayOptimal(int[] nums) {
    if (nums == null || nums.length == 0) return 0;

    int maxSoFar = nums[0];
    int currentSum = nums[0];

    for (int i = 1; i < nums.length; i++) {
        currentSum = Math.max(nums[i], currentSum + nums[i]);
        maxSoFar = Math.max(maxSoFar, currentSum);
    }

    return maxSoFar;
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N)$ linear time.
- **Space Complexity:** $\mathcal{O}(1)$ auxiliary space.

---

## 42. Minimum Size Subarray Sum

### Question
Given an array of positive integers `nums` and a positive integer `target`, return the **minimal length of a contiguous subarray** of which the sum is greater than or equal to `target`. If there is no such subarray, return `0`.

- **Example 1:** `target = 7`, `nums = [2, 3, 1, 2, 4, 3]` $\rightarrow$ Output: `2` (`[4, 3]`)
- **Example 2:** `target = 4`, `nums = [1, 4, 4]` $\rightarrow$ Output: `1` (`[4]`)
- **Example 3:** `target = 11`, `nums = [1, 1, 1, 1, 1, 1, 1, 1]` $\rightarrow$ Output: `0`

---

### 42.1 Optimal Sliding Window (Dynamic Two Pointers)

#### Approach & Intuition
- Expand `right` pointer to accumulate `sum += nums[right]`.
- While `sum >= target`:
  - Update `minLen = Math.min(minLen, right - left + 1)`.
  - Shrink window from the left: `sum -= nums[left++]`.

#### Java Code
```java
public static int minSubArrayLenOptimal(int target, int[] nums) {
    if (nums == null || nums.length == 0) return 0;

    int left = 0;
    int sum = 0;
    int minLen = Integer.MAX_VALUE;

    for (int right = 0; right < nums.length; right++) {
        sum += nums[right];

        while (sum >= target) {
            minLen = Math.min(minLen, right - left + 1);
            sum -= nums[left];
            left++;
        }
    }

    return minLen == Integer.MAX_VALUE ? 0 : minLen;
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N)$ - each element is added and removed from window at most once.
- **Space Complexity:** $\mathcal{O}(1)$ auxiliary space.

---

## 43. Top K Frequent Elements

### Question
Given an integer array `nums` and an integer `k`, return the `k` **most frequent elements**. You may return the answer in any order.

- **Example 1:** `nums = [1, 1, 1, 2, 2, 3]`, `k = 2` $\rightarrow$ Output: `[1, 2]`
- **Example 2:** `nums = [1]`, `k = 1` $\rightarrow$ Output: `[1]`

---

### 43.1 Optimal Bucket Sort Approach ($O(N)$ Time)

#### Approach & Intuition
- Count element frequencies in a `HashMap`.
- Use an array of buckets `List<Integer>[] buckets` of size $N + 1$, where `buckets[freq]` stores all numbers with frequency `freq`.
- Iterate from frequency $N$ down to $1$ and collect elements until $k$ elements are gathered.

#### Java Code
```java
public static int[] topKFrequentBucketSort(int[] nums, int k) {
    if (nums == null || nums.length == 0 || k <= 0) return new int[0];

    int n = nums.length;
    Map<Integer, Integer> countMap = new HashMap<>();
    for (int num : nums) {
        countMap.put(num, countMap.getOrDefault(num, 0) + 1);
    }

    @SuppressWarnings("unchecked")
    List<Integer>[] buckets = new List[n + 1];
    for (int i = 0; i <= n; i++) {
        buckets[i] = new ArrayList<>();
    }

    for (Map.Entry<Integer, Integer> entry : countMap.entrySet()) {
        buckets[entry.getValue()].add(entry.getKey());
    }

    int[] result = new int[k];
    int idx = 0;

    for (int freq = n; freq >= 1 && idx < k; freq--) {
        for (int num : buckets[freq]) {
            result[idx++] = num;
            if (idx == k) break;
        }
    }

    return result;
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N)$ linear time.
- **Space Complexity:** $\mathcal{O}(N)$ space for HashMap and bucket array.

---

## 44. Meeting Rooms

### Question
Given an array of meeting time intervals consisting of start and end times `[[s1,e1],[s2,e2],...]`, determine if a person could attend all meetings without overlap.

- **Example 1:** `intervals = [[0,30],[5,10],[15,20]]` $\rightarrow$ Output: `false` (Overlap between `[0,30]` and `[5,10]`)
- **Example 2:** `intervals = [[7,10],[2,4]]` $\rightarrow$ Output: `true` (No overlaps)

---

### 44.1 Optimal Sorting Approach

#### Approach & Intuition
- Sort intervals by start time ascending.
- Iterate from $i = 1 \dots N - 1$: if `intervals[i][0] < intervals[i - 1][1]`, return `false`.

#### Java Code
```java
public static boolean canAttendMeetingsOptimal(int[][] intervals) {
    if (intervals == null || intervals.length <= 1) return true;

    Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));

    for (int i = 1; i < intervals.length; i++) {
        if (intervals[i][0] < intervals[i - 1][1]) {
            return false;
        }
    }

    return true;
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N \log N)$ due to sorting.
- **Space Complexity:** $\mathcal{O}(1)$ or $\mathcal{O}(N)$ depending on sorting algorithm.

---

## Bonus Problems Summary & Patterns Cheatsheet

| Problem | Brute Force Time | Optimal Time | Optimal Space | Key Pattern / Core Algorithm |
| :--- | :--- | :--- | :--- | :--- |
| **39. Rotate Array** | $\mathcal{O}(N)$ (Extra array) | $\mathcal{O}(N)$ | $\mathcal{O}(1)$ | 3-Pass Reversal Technique (`reverse(0, N-1)`, `reverse(0, k-1)`, `reverse(k, N-1)`) |
| **40. Missing Ranges** | $\mathcal{O}(N^2)$ | $\mathcal{O}(N)$ | $\mathcal{O}(1)$ | Single-Pass Linear Pointer Scan with Overflow Guard (`long`) |
| **41. Maximum Subarray** | $\mathcal{O}(N^2)$ | $\mathcal{O}(N)$ | $\mathcal{O}(1)$ | Kadane's DP Recurrence ($\max(\text{nums}[i],\ \text{currentSum} + \text{nums}[i])$) |
| **42. Min Size Subarray Sum** | $\mathcal{O}(N^2)$ | $\mathcal{O}(N)$ | $\mathcal{O}(1)$ | Dynamic Sliding Window (Expand right, shrink left when $\ge \text{target}$) |
| **43. Top K Frequent** | $\mathcal{O}(N \log N)$ | $\mathcal{O}(N)$ | $\mathcal{O}(N)$ | Frequency Bucket Sort Array (Index = Frequency) |
| **44. Meeting Rooms** | $\mathcal{O}(N^2)$ | $\mathcal{O}(N \log N)$ | $\mathcal{O}(1)$ | Interval Sorting by Start Time + Overlap Check (`curr.start < prev.end`) |
