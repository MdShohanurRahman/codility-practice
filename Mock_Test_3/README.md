# Mock Test 3 --- 90 Minutes (Technical Assessment Report)

> **Time Allotted:** 90 Minutes  
> **Score Target:** 100% Correctness & Optimal Time Complexity

---

## Summary of Problems & Performance

| Problem | Category | Optimal Time | Optimal Space | Solution Strategy |
| :--- | :--- | :--- | :--- | :--- |
| **Problem A** | Running State Track | $\mathcal{O}(N)$ | $\mathcal{O}(1)$ | Single-pass minimum value tracking |
| **Problem B** | Monotonic Stack | $\mathcal{O}(N)$ | $\mathcal{O}(N)$ | Monotonic decreasing stack storing indices |
| **Problem C** | Prefix Sum | $\mathcal{O}(N)$ Prep, $\mathcal{O}(1)$ Query | $\mathcal{O}(N)$ | Prefix sum array $P[R+1] - P[L]$ |
| **Problem D** | HashSet Lookup | $\mathcal{O}(N)$ | $\mathcal{O}(N)$ | Sequence start filtering (`!set.contains(x - 1)`) |

---

## Problem Breakdown & Explanations

### Problem A: Maximum Difference ($A[j] - A[i]$ with $j > i$)
- **Question:** Find max difference between $A[j]$ and $A[i]$ where $j > i$.
- **Key Insight:** Maintain running minimum element seen so far, calculate profit candidate $A[j] - \text{minVal}$.
- **Java File:** [`ProblemA_MaximumDifference.java`](ProblemA_MaximumDifference.java)

### Problem B: Next Greater Element
- **Question:** Find next element to the right strictly greater than $A[i]$.
- **Key Insight:** Maintain a stack of indices in decreasing order of values. Pop elements when candidate $> \text{stack.peek()}$.
- **Java File:** [`ProblemB_NextGreaterElement.java`](ProblemB_NextGreaterElement.java)

### Problem C: Range Sum Queries
- **Question:** Perform multiple range sum queries $[L, R]$ efficiently.
- **Key Insight:** Construct prefix sum array `P` in $\mathcal{O}(N)$. Range sum = $P[R+1] - P[L]$.
- **Java File:** [`ProblemC_RangeSumQueries.java`](ProblemC_RangeSumQueries.java)

### Problem D: Longest Consecutive Sequence
- **Question:** Find length of longest consecutive integer sequence in unsorted array.
- **Key Insight:** Place numbers in HashSet. Only initiate sequence count if `num - 1` is NOT in set.
- **Java File:** [`ProblemD_LongestConsecutiveSequence.java`](ProblemD_LongestConsecutiveSequence.java)
