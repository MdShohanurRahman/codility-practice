# Day 10 --- Codility-Style Challenge Problems (Interview Study Notes)

> **Focus:** In-Place Index Swapping (Cyclic Sort), HashSet Sequence Start Identification, Boyer-Moore Majority Vote Algorithm, and Permutation Invariant Testing.  
> **Target Platform:** Codility / MoneyLion Technical Assessment

---

## Table of Contents
1. [Problem 35: Permutation Check](#35-permutation-check)
2. [Problem 36: Smallest Missing Positive](#36-smallest-missing-positive)
3. [Problem 37: Longest Consecutive Sequence](#37-longest-consecutive-sequence)
4. [Problem 38: Leader / Dominant Value](#38-leader--dominant-value)
5. [Day 10 Summary & Patterns Cheatsheet](#day-10-summary--patterns-cheatsheet)

---

## 35. Permutation Check

### Question
A non-empty array `A` consisting of `N` integers is given. A *permutation* is a sequence containing each element from `1` to `N` exactly once, and no other values.

Determine whether array `A` is a valid permutation. Return `true` (or `1`) if `A` is a permutation, and `false` (or `0`) otherwise.

- **Example 1:** `A = [4, 1, 3, 2]` $\rightarrow$ Output: `true` (Length 4, contains numbers 1, 2, 3, 4)
- **Example 2:** `A = [4, 1, 3]` $\rightarrow$ Output: `false` (Length 3, contains 4 which is $> 3$, missing 2)
- **Example 3:** `A = [1, 1, 3]` $\rightarrow$ Output: `false` (Duplicate element `1`)

---

### 35.1 Sorting Approach

#### Approach & Intuition
- Clone array and sort it in ascending order.
- Iterate through index $i = 0 \dots N - 1$ and verify that $A[i] == i + 1$.

#### Java Code
```java
public static boolean isPermutationSorting(int[] A) {
    if (A == null || A.length == 0) return false;

    int[] arr = A.clone();
    Arrays.sort(arr);

    for (int i = 0; i < arr.length; i++) {
        if (arr[i] != i + 1) {
            return false;
        }
    }

    return true;
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N \log N)$ due to dual-pivot Quicksort.
- **Space Complexity:** $\mathcal{O}(N)$ for array cloning.

---

### 35.2 Optimal Boolean Seen Tracking Approach

#### Approach & Intuition
- Allocation of a boolean array `seen` of size $N + 1$.
- Validate every element $x \in A$:
  1. If $x < 1$ or $x > N$, return `false` immediately (value out of permutation range).
  2. If `seen[x] == true`, return `false` immediately (duplicate element).
  3. Mark `seen[x] = true`.
- If loop completes without violations, return `true`.

#### Java Code
```java
public static boolean isPermutationOptimal(int[] A) {
    if (A == null || A.length == 0) return false;

    int n = A.length;
    boolean[] seen = new boolean[n + 1];

    for (int num : A) {
        if (num < 1 || num > n) {
            return false; // Out of range [1, N]
        }
        if (seen[num]) {
            return false; // Duplicate found
        }
        seen[num] = true;
    }

    return true;
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N)$
  - *Reasoning:* Single pass over the array of size $N$.
- **Space Complexity:** $\mathcal{O}(N)$
  - *Reasoning:* Boolean array of size $N + 1$.

#### Edge Cases to Mention in Interview
- **Array containing values $> N$:** `[4, 1, 3]` with $N = 3$ $\rightarrow$ $4 > 3$, correctly returns `false`.
- **Duplicates:** `[1, 1, 3]` with $N = 3$ $\rightarrow$ duplicate `1`, returns `false`.
- **Negative or Zero values:** `[-1, 2, 3]` $\rightarrow$ returns `false`.
- **Single Element Array:** `[1]` returns `true`, `[2]` returns `false`.

---

## 36. Smallest Missing Positive

### Question
Given an unsorted integer array `A`, find the **smallest positive integer** ($> 0$) that does not occur in `A`.

- **Example 1:** `A = [1, 3, 6, 4, 1, 2]` $\rightarrow$ Output: `5`
- **Example 2:** `A = [1, 2, 3]` $\rightarrow$ Output: `4`
- **Example 3:** `A = [-1, -3]` $\rightarrow$ Output: `1`
- **Example 4:** `A = [7, 8, 9, 11, 12]` $\rightarrow$ Output: `1`

---

### 36.1 HashSet Approach

#### Approach & Intuition
- Add all numbers $> 0$ into a `HashSet`.
- Check numbers `candidate = 1, 2, 3...` sequentially using `set.contains(candidate)`.
- Return the first `candidate` not present in the set.

#### Java Code
```java
public static int firstMissingPositiveSet(int[] nums) {
    if (nums == null || nums.length == 0) return 1;

    Set<Integer> set = new HashSet<>();
    for (int num : nums) {
        if (num > 0) set.add(num);
    }

    int candidate = 1;
    while (set.contains(candidate)) {
        candidate++;
    }

    return candidate;
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N)$ expected time.
- **Space Complexity:** $\mathcal{O}(N)$ extra space for HashSet.

---

### 36.2 Optimal Cyclic Sort / Index Placement ($O(1)$ Extra Space)

#### Approach & Intuition
- **Core Observation:** For an array of size $N$, the smallest missing positive integer **must** lie in the range $[1, N + 1]$.
- **In-Place Swap Strategy (Cyclic Placement):**
  - Place each number $x$ in range $[1, N]$ at its ideal index $x - 1$.
  - While $1 \le A[i] \le N$ and $A[i] \ne A[A[i] - 1]$, swap $A[i]$ with $A[A[i] - 1]$.
- **Verification Pass:**
  - Iterate through array $i = 0 \dots N - 1$: if $A[i] \ne i + 1$, then $i + 1$ is the first missing positive!
  - If all positions $i$ hold $i + 1$, return $N + 1$.

#### Step-by-Step Swapping Trace (`A = [3, 4, -1, 1]`, $N = 4$)

| Index ($i$) | Initial $A[i]$ | Condition $1 \le A[i] \le 4 \land A[i] \ne A[A[i]-1]$ | Action / Swap | Array State |
| :--- | :--- | :--- | :--- | :--- |
| **$i = 0$** | `3` | True ($3 \ne A[2] = -1$) | Swap $A[0]$ & $A[2]$ | `[-1, 4, 3, 1]` |
| **$i = 0$** | `-1` | False ($A[0] \le 0$) | Advance to $i = 1$ | `[-1, 4, 3, 1]` |
| **$i = 1$** | `4` | True ($4 \ne A[3] = 1$) | Swap $A[1]$ & $A[3]$ | `[-1, 1, 3, 4]` |
| **$i = 1$** | `1` | True ($1 \ne A[0] = -1$) | Swap $A[1]$ & $A[0]$ | `[1, -1, 3, 4]` |
| **$i = 1$** | `-1` | False ($A[1] \le 0$) | Advance to $i = 2$ | `[1, -1, 3, 4]` |
| **$i = 2$** | `3` | False ($A[2] == 3$, already correct) | Advance to $i = 3$ | `[1, -1, 3, 4]` |
| **$i = 3$** | `4` | False ($A[3] == 4$, already correct) | Done Swapping | `[1, -1, 3, 4]` |

**Scan Phase:**
- Index $0$: $A[0] == 1$ (Match)
- Index $1$: $A[1] == -1 \ne 2$ $\rightarrow$ **Missing integer is $1 + 1 = 2$!**

#### Java Code
```java
public static int firstMissingPositiveOptimal(int[] A) {
    if (A == null || A.length == 0) return 1;

    int n = A.length;

    // Step 1: In-place cyclic placement
    for (int i = 0; i < n; i++) {
        while (A[i] > 0 && A[i] <= n && A[A[i] - 1] != A[i]) {
            int targetIndex = A[i] - 1;
            int temp = A[i];
            A[i] = A[targetIndex];
            A[targetIndex] = temp;
        }
    }

    // Step 2: Find first mismatch
    for (int i = 0; i < n; i++) {
        if (A[i] != i + 1) {
            return i + 1;
        }
    }

    // Step 3: All 1..N present
    return n + 1;
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N)$
  - *Reasoning:* Each number is swapped into its correct index at most once. Total swaps across entire loop $\le N$.
- **Space Complexity:** $\mathcal{O}(1)$ auxiliary space
  - *Reasoning:* Modifies input array directly without extra memory structures.

---

## 37. Longest Consecutive Sequence

### Question
Given an unsorted array of integers `nums`, find the length of the **longest consecutive elements sequence**.

- **Example 1:** `nums = [100, 4, 200, 1, 3, 2]` $\rightarrow$ Output: `4` (Consecutive sequence: `[1, 2, 3, 4]`)
- **Example 2:** `nums = [0, 3, 7, 2, 5, 8, 4, 6, 0, 1]` $\rightarrow$ Output: `9` (`[0, 1, 2, 3, 4, 5, 6, 7, 8]`)
- **Example 3:** `nums = [1, 2, 0, 1]` $\rightarrow$ Output: `3` (`[0, 1, 2]`)

---

### 37.1 Sorting Approach

#### Approach & Intuition
- Sort the array. Iterate through sorted elements, tracking current streak length while skipping identical duplicates (`arr[i] == arr[i - 1]`).

#### Java Code
```java
public static int longestConsecutiveSorting(int[] nums) {
    if (nums == null || nums.length == 0) return 0;

    int[] arr = nums.clone();
    Arrays.sort(arr);

    int maxStreak = 1;
    int currentStreak = 1;

    for (int i = 1; i < arr.length; i++) {
        if (arr[i] == arr[i - 1]) continue; // Skip duplicate
        
        if (arr[i] == arr[i - 1] + 1) {
            currentStreak++;
        } else {
            currentStreak = 1;
        }
        maxStreak = Math.max(maxStreak, currentStreak);
    }

    return maxStreak;
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N \log N)$ due to sorting.
- **Space Complexity:** $\mathcal{O}(N)$ for array cloning.

---

### 37.2 Optimal HashSet Sequence Start Lookup ($O(N)$ Time)

#### Approach & Intuition
- Insert all elements into a `HashSet` for $\mathcal{O}(1)$ lookups.
- **Key Insight to achieve $\mathcal{O}(N)$ Time:**
  - An element `x` is the **start of a sequence** if and only if `x - 1` is **NOT** present in the `HashSet`.
  - If `x - 1` exists, skip `x` (because it will be counted as part of the sequence initiated when visiting `x - 1` or earlier).
  - If `x - 1` is missing, start a `while (set.contains(current + 1))` loop to measure the sequence length.

#### Why is this strictly $\mathcal{O}(N)$?
Each number is visited at most twice: once in the outer loop, and at most once inside the `while` loop across all sequence expansions.

#### Java Code
```java
public static int longestConsecutiveOptimal(int[] nums) {
    if (nums == null || nums.length == 0) return 0;

    Set<Integer> numSet = new HashSet<>();
    for (int num : nums) {
        numSet.add(num);
    }

    int maxStreak = 0;

    for (int num : numSet) {
        // Only start sequence expansion if num is the FIRST element in sequence
        if (!numSet.contains(num - 1)) {
            int currentNum = num;
            int currentStreak = 1;

            while (numSet.contains(currentNum + 1)) {
                currentNum += 1;
                currentStreak += 1;
            }

            maxStreak = Math.max(maxStreak, currentStreak);
        }
    }

    return maxStreak;
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N)$ expected time.
- **Space Complexity:** $\mathcal{O}(N)$ extra space for HashSet.

---

## 38. Leader / Dominant Value

### Question
An array `A` consisting of `N` integers is given. The *leader* (or *dominator*) of array `A` is the value that occurs in **more than half** of the positions of `A` (i.e. strictly $> \lfloor N / 2 \rfloor$).

Return any index of the array at which the dominator occurs. If array `A` does not have a dominator, return `-1`.

- **Example 1:** `A = [3, 4, 3, 2, 3, -1, 3, 3]` $\rightarrow$ Output: `0` (Length 8, `3` occurs 5 times. $5 > 8 / 2 = 4$. Dominant value is `3`)
- **Example 2:** `A = [1, 2, 3, 4, 5]` $\rightarrow$ Output: `-1` (No value occurs $> 2.5$ times)
- **Example 3:** `A = [2, 1, 1, 3, 4]` $\rightarrow$ Output: `-1` (`1` occurs 2 times, which is NOT $> 5/2 = 2$)

---

### 38.1 HashMap Frequency Approach

#### Approach & Intuition
- Use a `HashMap<Integer, Integer>` to count frequencies of each element in a single pass.
- If any element's count exceeds $N / 2$, return its index.

#### Java Code
```java
public static int findLeaderHashMap(int[] A) {
    if (A == null || A.length == 0) return -1;

    int n = A.length;
    Map<Integer, Integer> map = new HashMap<>();

    for (int i = 0; i < n; i++) {
        int count = map.getOrDefault(A[i], 0) + 1;
        map.put(A[i], count);
        if (count > n / 2) {
            return i;
        }
    }

    return -1;
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N)$ expected time.
- **Space Complexity:** $\mathcal{O}(N)$ space for HashMap.

---

### 38.2 Optimal Boyer-Moore Majority Vote Algorithm ($O(1)$ Space)

#### Approach & Intuition
- **Algorithm Concept (Pairwise Cancellation):**
  - If we pair up different elements and remove them, a dominant value (which occurs $> N / 2$ times) will remain as the final candidate.
- **Phase 1 (Candidate Election):**
  - Maintain a `candidate` variable and a `count` integer.
  - Traverse array:
    - If `count == 0`, set `candidate = num` and `count = 1`.
    - If `num == candidate`, increment `count++`.
    - Else, decrement `count--`.
- **Phase 2 (Candidate Verification):**
  - Count actual occurrences of `candidate` in array `A`.
  - If `candidateCount > N / 2`, return the index of the candidate. Otherwise, return `-1`.

#### Step-by-Step Cancellation Trace (`A = [3, 4, 3, 2, 3, -1, 3, 3]`, $N = 8$)

| Index ($i$) | Element (`A[i]`) | Current `candidate` | `count` Before | Action | `count` After |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **$0$** | `3` | `3` | `0` | Set Candidate = `3` | `1` |
| **$1$** | `4` | `3` | `1` | Match Fail $\rightarrow$ Decrement | `0` |
| **$2$** | `3` | `3` | `0` | Set Candidate = `3` | `1` |
| **$3$** | `2` | `3` | `1` | Match Fail $\rightarrow$ Decrement | `0` |
| **$4$** | `3` | `3` | `0` | Set Candidate = `3` | `1` |
| **$5$** | `-1` | `3` | `1` | Match Fail $\rightarrow$ Decrement | `0` |
| **$6$** | `3` | `3` | `0` | Set Candidate = `3` | `1` |
| **$7$** | `3` | `3` | `1` | Match Success $\rightarrow$ Increment | `2` |

**Verification Phase:**
- Count of `3` in `A` is $5$. $5 > 8 / 2 = 4$ $\rightarrow$ Valid leader index `0` (Value: `3`).

#### Java Code
```java
public static int findLeaderOptimal(int[] A) {
    if (A == null || A.length == 0) return -1;

    int candidate = -1;
    int count = 0;

    // Phase 1: Candidate selection via pairwise cancellation
    for (int num : A) {
        if (count == 0) {
            candidate = num;
            count = 1;
        } else if (num == candidate) {
            count++;
        } else {
            count--;
        }
    }

    // Phase 2: Candidate verification
    int candidateCount = 0;
    int firstIndex = -1;

    for (int i = 0; i < A.length; i++) {
        if (A[i] == candidate) {
            candidateCount++;
            if (firstIndex == -1) {
                firstIndex = i;
            }
        }
    }

    if (candidateCount > A.length / 2) {
        return firstIndex;
    }

    return -1;
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N)$
  - *Reasoning:* Two linear passes over array of size $N$.
- **Space Complexity:** $\mathcal{O}(1)$ auxiliary space
  - *Reasoning:* Uses scalar integer variables.

---

## Day 10 Summary & Patterns Cheatsheet

| Problem | Brute Force Time | Optimal Time | Optimal Space | Key Pattern / Core Algorithm |
| :--- | :--- | :--- | :--- | :--- |
| **35. Permutation Check** | $\mathcal{O}(N \log N)$ | $\mathcal{O}(N)$ | $\mathcal{O}(N)$ or $\mathcal{O}(1)$ | Range Validation $[1, N]$ & Frequency / Seen Set |
| **36. Smallest Missing Positive** | $\mathcal{O}(N^2)$ | $\mathcal{O}(N)$ | $\mathcal{O}(1)$ | Cyclic Sort / In-Place Swapping (`nums[i]` $\rightarrow$ `nums[nums[i]-1]`) |
| **37. Longest Consecutive Sequence** | $\mathcal{O}(N^3)$ / $\mathcal{O}(N \log N)$ | $\mathcal{O}(N)$ | $\mathcal{O}(N)$ | Sequence Start Filtering (`!set.contains(x - 1)`) |
| **38. Leader / Dominant Value** | $\mathcal{O}(N^2)$ | $\mathcal{O}(N)$ | $\mathcal{O}(1)$ | Boyer-Moore Majority Vote Algorithm (2-Pass Election) |

---

### Essential Codility Assessment Patterns

1. **In-Place Index Placement (Cyclic Sort):**
   - For problems involving missing numbers, duplicate numbers, or permutations within range $[1, N]$, swap $A[i]$ with $A[A[i] - 1]$ to achieve $\mathcal{O}(N)$ time and $\mathcal{O}(1)$ auxiliary space.
2. **Avoiding Nested Redundant Scans in HashSets:**
   - In sequence problems, check `!set.contains(x - 1)` before expanding sequence loops. This reduces time complexity from $\mathcal{O}(N^2)$ down to linear $\mathcal{O}(N)$.
3. **Boyer-Moore Voting Verification:**
   - Always remember that Phase 1 of Boyer-Moore only finds a *potential* candidate. Phase 2 verification ($count > N/2$) is mandatory because arrays without any majority element will still yield a false candidate during Phase 1.
