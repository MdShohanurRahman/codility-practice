# Mock Test 1 --- 60 Minutes (Technical Assessment Report)

> **Time Allotted:** 60 Minutes  
> **Score Target:** 100% Correctness & Optimal Time Complexity

---

## Summary of Problems & Performance

| Problem | Category | Optimal Time | Optimal Space | Solution Strategy |
| :--- | :--- | :--- | :--- | :--- |
| **Problem A** | Arrays / Cyclic Placement | $\mathcal{O}(N)$ | $\mathcal{O}(1)$ | Cyclic sort swapping `nums[i]` with `nums[nums[i]-1]` |
| **Problem B** | Sliding Window | $\mathcal{O}(N)$ | $\mathcal{O}(K)$ | Dynamic window expansion with frequency hashmap |
| **Problem C** | Sorting / Interval Sweep | $\mathcal{O}(N \log N)$ | $\mathcal{O}(N)$ | Parallel array sorting of start & end timestamps |

---

## Problem Breakdown & Explanations

### Problem A: First Missing Positive Integer
- **Question:** Find smallest missing positive integer $> 0$ in unsorted array.
- **Key Insight:** Minimum missing integer lies in range $[1, N + 1]$. Swap elements to their ideal index $A[i] - 1$.
- **Java File:** [`ProblemA_FirstMissingPositive.java`](ProblemA_FirstMissingPositive.java)

### Problem B: Longest Substring with At Most K Distinct Characters
- **Question:** Find max length of substring with $\le K$ unique characters.
- **Key Insight:** Expand `right` pointer, track character counts in map. Shrink `left` pointer when `map.size() > k`.
- **Java File:** [`ProblemB_LongestSubstringKDistinct.java`](ProblemB_LongestSubstringKDistinct.java)

### Problem C: Minimum Machines / Resources Required
- **Question:** Find min machines required for non-overlapping jobs.
- **Key Insight:** Sort start and end arrays independently. Sweep through timestamps: increment machines when `start < end`, decrement when `start >= end`.
- **Java File:** [`ProblemC_MinimumMachinesRequired.java`](ProblemC_MinimumMachinesRequired.java)
