# Day 5 --- Sorting & Searching (Interview Study Notes)

> **Focus:** Binary Search Variants, Boundary Indexing, Interval Merging, Event Sweep-Line / Two-Pointer Scheduling.  
> **Target Platform:** Codility / MoneyLion Technical Assessment

---

## Table of Contents
1. [Problem 17: Binary Search](#17-binary-search)
2. [Problem 18: First Occurrence](#18-first-occurrence)
3. [Problem 19: Merge Intervals](#19-merge-intervals)
4. [Problem 20: Minimum Number of Platforms / Resources](#20-minimum-number-of-platforms--resources)
5. [Day 5 Summary & Patterns Cheatsheet](#day-5-summary--patterns-cheatsheet)

---

## 17. Binary Search

### Question
Find the index of a target value in a sorted array `nums`. Return `-1` if the target is not found.

- **Example 1:** `nums = [-1, 0, 3, 5, 9, 12]`, `target = 9` $\rightarrow$ Output: `4`
- **Example 2:** `nums = [-1, 0, 3, 5, 9, 12]`, `target = 2` $\rightarrow$ Output: `-1`

---

### 17.1 Brute Force Approach (Linear Search)

#### Approach & Intuition
- Iterate through the array sequentially from index `0` to `N - 1`.
- Compare each element with `target`. Return the index if a match is found.

#### Java Code
```java
public static int binarySearchBruteForce(int[] nums, int target) {
    if (nums == null || nums.length == 0) return -1;

    for (int i = 0; i < nums.length; i++) {
        if (nums[i] == target) {
            return i;
        }
    }
    return -1;
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N)$
- **Space Complexity:** $\mathcal{O}(1)$

---

### 17.2 Optimal Approach (Iterative Binary Search)

#### Approach & Intuition
- Maintain search boundaries `low = 0` and `high = nums.length - 1`.
- In each iteration:
  - Calculate `mid = low + (high - low) / 2` to prevent potential 32-bit integer overflow.
  - If `nums[mid] == target`, return `mid`.
  - If `nums[mid] < target`, discard the left half by setting `low = mid + 1`.
  - If `nums[mid] > target`, discard the right half by setting `high = mid - 1`.
- If `low > high`, the element does not exist in the sorted array, return `-1`.

#### Java Code
```java
public static int binarySearchOptimal(int[] nums, int target) {
    if (nums == null || nums.length == 0) return -1;

    int low = 0;
    int high = nums.length - 1;

    while (low <= high) {
        int mid = low + (high - low) / 2;

        if (nums[mid] == target) {
            return mid;
        } else if (nums[mid] < target) {
            low = mid + 1;
        } else {
            high = mid - 1;
        }
    }

    return -1;
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(\log N)$ because the search space halves with each iteration.
- **Space Complexity:** $\mathcal{O}(1)$ auxiliary space.

#### Edge Cases to Mention in Interview
- Empty input array or `null` $\rightarrow$ return `-1`.
- Single-element array `[5]`: correctly evaluated when `low == high == 0`.
- Target smaller than all elements or larger than all elements $\rightarrow$ loop terminates safely with `low > high`.
- Avoiding Integer Overflow: use `low + (high - low) / 2` instead of `(low + high) / 2`.

---

## 18. First Occurrence

### Question
Given a sorted array `nums` containing duplicates, find the **first occurrence** index of a `target` value. Return `-1` if the target is not present.

- **Example 1:** `nums = [1, 2, 2, 2, 3, 4, 5]`, `target = 2` $\rightarrow$ Output: `1`
- **Example 2:** `nums = [5, 7, 7, 8, 8, 10]`, `target = 8` $\rightarrow$ Output: `3`

---

### 18.1 Brute Force Approach

#### Approach & Intuition
- Scan the array linearly from index `0` to `N - 1`.
- Return the index of the first element that equals `target`.

#### Java Code
```java
public static int firstOccurrenceBruteForce(int[] nums, int target) {
    if (nums == null || nums.length == 0) return -1;

    for (int i = 0; i < nums.length; i++) {
        if (nums[i] == target) {
            return i;
        }
    }
    return -1;
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N)$
- **Space Complexity:** $\mathcal{O}(1)$

---

### 18.2 Optimal Approach (Modified Binary Search)

#### Approach & Intuition
- Standard binary search returns *any* index matching `target`. To find the **first** occurrence:
  - When `nums[mid] == target`, save `result = mid`.
  - Instead of stopping, continue searching the left sub-array by shifting `high = mid - 1`.
- If `nums[mid] < target`, shift `low = mid + 1`.
- If `nums[mid] > target`, shift `high = mid - 1`.

#### Java Code
```java
public static int firstOccurrenceOptimal(int[] nums, int target) {
    if (nums == null || nums.length == 0) return -1;

    int low = 0;
    int high = nums.length - 1;
    int result = -1;

    while (low <= high) {
        int mid = low + (high - low) / 2;

        if (nums[mid] == target) {
            result = mid;       // Record potential first occurrence index
            high = mid - 1;     // Keep searching left half for an earlier match
        } else if (nums[mid] < target) {
            low = mid + 1;
        } else {
            high = mid - 1;
        }
    }

    return result;
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(\log N)$
- **Space Complexity:** $\mathcal{O}(1)$

#### Edge Cases to Mention in Interview
- Array filled with duplicate targets `[2, 2, 2, 2, 2]`, `target = 2` $\rightarrow$ correctly returns index `0`.
- Target not in array $\rightarrow$ returns `-1`.
- Last occurrence requirement variant: change `high = mid - 1` to `low = mid + 1` to find the last occurrence instead!

---

## 19. Merge Intervals

### Question
Given an array of intervals where `intervals[i] = [start_i, end_i]`, merge all overlapping intervals and return an array of non-overlapping intervals covering all input ranges.

- **Example 1:** `intervals = [[1,3], [2,6], [8,10], [15,18]]` $\rightarrow$ Output: `[[1,6], [8,10], [15,18]]`
- **Example 2:** `intervals = [[1,4], [4,5]]` $\rightarrow$ Output: `[[1,5]]`

---

### 19.1 Brute Force Approach

#### Approach & Intuition
- Check every pair of intervals $(i, j)$ for overlap.
- If two intervals $[s_i, e_i]$ and $[s_j, e_j]$ overlap ($\max(s_i, s_j) \le \min(e_i, e_j)$), merge them into $[\min(s_i, s_j), \max(e_i, e_j)]$ and replace the two intervals with the merged one.
- Repeat until no overlapping pair remains.

#### Java Code
```java
public static int[][] mergeIntervalsBruteForce(int[][] intervals) {
    if (intervals == null || intervals.length <= 1) return intervals;

    List<int[]> list = new ArrayList<>(Arrays.asList(intervals));
    boolean mergedAny = true;

    while (mergedAny) {
        mergedAny = false;
        int n = list.size();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int[] a = list.get(i);
                int[] b = list.get(j);

                if (Math.max(a[0], b[0]) <= Math.min(a[1], b[1])) {
                    int[] merged = new int[]{Math.min(a[0], b[0]), Math.max(a[1], b[1])};
                    list.remove(j);
                    list.remove(i);
                    list.add(merged);
                    mergedAny = true;
                    break;
                }
            }
            if (mergedAny) break;
        }
    }

    return list.toArray(new int[list.size()][]);
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N^2)$ (or $\mathcal{O}(N^3)$ depending on list removals)
- **Space Complexity:** $\mathcal{O}(N)$

---

### 19.2 Optimal Approach (Sorting by Start Time)

#### Approach & Intuition

1. **Sort intervals by start time**
2. Take the first interval as the `current` interval
3. Compare the next interval with the current interval
4. Check:

   `next.start <= current.end`

   - **YES → Overlap → Merge**
     - `end = max(current.end, next.end)`
   - **NO → No overlap**
     - Add current interval to result
     - Make next interval the new current interval
5. After the loop, add the last current interval to result


#### Java Code
```java
public static int[][] mergeIntervalsOptimal(int[][] intervals) {
        if (intervals.length <= 1) {
            return intervals;
        }

        // 1. Sort by start time
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

        List<int[]> result = new ArrayList<>();

        // 2. Start with the first interval
        int start = intervals[0][0];
        int end = intervals[0][1];

        // 3. Check remaining intervals
        for (int i = 1; i < intervals.length; i++) {

            int currentStart = intervals[i][0];
            int currentEnd = intervals[i][1];

            // Overlapping
            if (currentStart <= end) {
                end = Math.max(end, currentEnd);
            }
            // Non-overlapping
            else {
                result.add(new int[]{start, end});

                start = currentStart;
                end = currentEnd;
            }
        }

        // 4. Add the last interval
        result.add(new int[]{start, end});

        return result.toArray(new int[result.size()][]);
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N \log N)$ due to sorting. The linear scan takes $\mathcal{O}(N)$ time.
- **Space Complexity:** $\mathcal{O}(N)$ for output storage / $\mathcal{O}(\log N)$ auxiliary space for `Arrays.sort()`.

#### Edge Cases to Mention in Interview
- Intervals touching at boundaries `[1, 4]` and `[4, 5]` $\rightarrow$ merged to `[1, 5]`.
- Completely enclosed intervals `[1, 10]` and `[2, 5]` $\rightarrow$ merged to `[1, 10]`.
- Input already sorted or reverse sorted.

---

## 20. Minimum Number of Platforms / Resources

### Question
Given arrival and departure times of events/trains, find the minimum number of platforms/resources required so that no train/event is forced to wait due to resource contention.

- **Example 1:**
  - `arrivals = [900, 940, 950, 1100, 1500, 1800]`
  - `departures = [910, 1200, 1120, 1130, 1900, 2000]`
  - Output: `3` (At time `1100`, trains 2, 3, and 4 are at the station concurrently).

---

### 20.1 Brute Force Approach

#### Approach & Intuition
- For every train $i$, count how many other trains $j$ are present at the station simultaneously when train $i$ arrives.
- Train $j$ overlaps with train $i$'s arrival if `arrivals[j] <= arrivals[i]` and `departures[j] >= arrivals[i]`.
- Take the maximum platform count across all trains.

#### Java Code
```java
public static int findPlatformBruteForce(int[] arrivals, int[] departures) {
    if (arrivals == null || departures == null || arrivals.length == 0) return 0;
    int n = arrivals.length;
    int maxPlatforms = 1;

    for (int i = 0; i < n; i++) {
        int neededPlatforms = 1;
        for (int j = 0; j < n; j++) {
            if (i != j) {
                // Train j arrived
                // AND Train j has not left yet
                if (arrivals[j] <= arrivals[i] && departures[j] >= arrivals[i]) {
                    neededPlatforms++;
                }
            }
        }
        maxPlatforms = Math.max(maxPlatforms, neededPlatforms);
    }
    return maxPlatforms;
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N^2)$
- **Space Complexity:** $\mathcal{O}(1)$

---

### 20.2 Optimal Approach (Two-Pointer Event Sweeping)

#### Approach & Intuition
- Sort the `arrivals` and `departures` arrays independently in ascending order.
- Maintain two pointers: `i = 0` (arrivals) and `j = 0` (departures).
- Maintain `neededPlatforms` and `maxPlatforms`:
  - If `arr[i] <= dep[j]`: A train arrives before (or at) the current departure $\rightarrow$ increment `neededPlatforms++`, `i++`.
  - Else (`arr[i] > dep[j]`): A train departs, freeing up a platform $\rightarrow$ decrement `neededPlatforms--`, `j++`.
  - Track peak demand: `maxPlatforms = Math.max(maxPlatforms, neededPlatforms)`.

#### Java Code
```java
public static int findPlatformOptimal(int[] arrivals, int[] departures) {
    if (arrivals == null || departures == null || arrivals.length == 0) return 0;

    int n = arrivals.length;
    int[] arr = arrivals.clone();
    int[] dep = departures.clone();

    Arrays.sort(arr);
    Arrays.sort(dep);

    int neededPlatforms = 0;
    int maxPlatforms = 0;
    int i = 0;
    int j = 0;

    while (i < n && j < n) {
        if (arr[i] <= dep[j]) {
            neededPlatforms++;
            i++;
        } else {
            neededPlatforms--;
            j++;
        }
        maxPlatforms = Math.max(maxPlatforms, neededPlatforms);
    }

    return maxPlatforms;
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N \log N)$ due to sorting arrival and departure arrays. Two-pointer sweep runs in $\mathcal{O}(N)$.
- **Space Complexity:** $\mathcal{O}(N)$ for cloned primitive arrays (or $\mathcal{O}(1)$ extra space if sorted in-place).

#### Edge Cases to Mention in Interview
- Arrival and departure at exact same time (`arr[i] == dep[j]`): Arrival needs a platform before departure frees one (or per problem spec constraint).
- All events non-overlapping $\rightarrow$ returns `1`.
- All events overlapping simultaneously $\rightarrow$ returns `N`.

---

## Day 5 Summary & Patterns Cheatsheet

| Problem | Brute Force Time | Optimal Time | Optimal Space | Key Pattern / Core Concept |
| :--- | :--- | :--- | :--- | :--- |
| **17. Binary Search** | $\mathcal{O}(N)$ | $\mathcal{O}(\log N)$ | $\mathcal{O}(1)$ | Divide & Conquer search space halving |
| **18. First Occurrence** | $\mathcal{O}(N)$ | $\mathcal{O}(\log N)$ | $\mathcal{O}(1)$ | Binary Search with result recording & left-shift |
| **19. Merge Intervals** | $\mathcal{O}(N^2)$ | $\mathcal{O}(N \log N)$ | $\mathcal{O}(N)$ | Sorting by start time + linear interval merging |
| **20. Minimum Platforms** | $\mathcal{O}(N^2)$ | $\mathcal{O}(N \log N)$ | $\mathcal{O}(N)$ | Two-pointer chronological sweep / event line |

---

### Key Takeaways for Codility / Technical Interviews
1. **Binary Search Beyond Exact Matches:**
   - Binary Search isn't just for exact values. By altering pointer updating logic (`high = mid - 1` vs `low = mid + 1`), it efficiently finds boundary elements like first occurrence, last occurrence, lower bound, or upper bound.
2. **Prevent Integer Overflow in Midpoint Calculation:**
   - Always calculate `mid` as `low + (high - low) / 2` to avoid 32-bit signed integer overflow when `low + high > Integer.MAX_VALUE`.
3. **Interval Sorting Strategy:**
   - Sorting intervals by start time turns an $\mathcal{O}(N^2)$ graph problem into a clean $\mathcal{O}(N \log N)$ single-pass greedy comparison.
4. **Chronological Event Sweeping (Sweep-Line):**
   - When calculating concurrent resource utilization (platforms, room bookings, concurrent user sessions), sorting start and end times independently allows tracking active overlaps with a simple two-pointer pass.
