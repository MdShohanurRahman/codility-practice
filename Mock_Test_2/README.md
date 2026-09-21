# Mock Test 2 --- 75 Minutes (Technical Assessment Report)

> **Time Allotted:** 75 Minutes  
> **Score Target:** 100% Correctness & Optimal Time Complexity

---

## Summary of Problems & Performance

| Problem | Category | Optimal Time | Optimal Space | Solution Strategy |
| :--- | :--- | :--- | :--- | :--- |
| **Problem A** | HashMap / Frequency | $\mathcal{O}(N)$ | $\mathcal{O}(N)$ | Single-pass HashMap complement frequency count |
| **Problem B** | Greedy Accumulation | $\mathcal{O}(N)$ | $\mathcal{O}(1)$ | Single-pass peak-valley positive difference sum |
| **Problem C** | Sliding Window | $\mathcal{O}(N)$ | $\mathcal{O}(1)$ | Dynamic two pointers window contraction when $\text{sum} \ge K$ |

---

## Problem Breakdown & Explanations

### Problem A: Pair Sum Count
- **Question:** Count pairs $(i, j)$ with $i < j$ such that $A[i] + A[j] == \text{target}$.
- **Key Insight:** Store element counts in HashMap. For each $x$, add `map.get(target - x)` to total pair count.
- **Java File:** [`ProblemA_PairSumCount.java`](ProblemA_PairSumCount.java)

### Problem B: Best Time to Buy & Sell Stock II (Multiple Transactions)
- **Question:** Maximize profit with unlimited transactions.
- **Key Insight:** Greedy choice. Sum all positive differences `prices[i] - prices[i-1]`.
- **Java File:** [`ProblemB_StockMaxProfitMultipleTransactions.java`](ProblemB_StockMaxProfitMultipleTransactions.java)

### Problem C: Smallest Subarray Sum $\ge K$
- **Question:** Find minimum length contiguous subarray with sum $\ge K$.
- **Key Insight:** Expand `right` pointer to accumulate sum, shrink `left` pointer as long as window condition is satisfied.
- **Java File:** [`ProblemC_SmallestSubarraySumK.java`](ProblemC_SmallestSubarraySumK.java)
