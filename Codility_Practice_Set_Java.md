# Codility Practice Set --- Java

**Goal:** Prepare for a Codility-style assessment in 10--11 days with
2--3 hours of practice per day.

**Recommended target:** Solve all Core problems, then complete the Mock
Tests under timed conditions.

## How to Practice

For every problem, try to write down:

1.  Approach
2.  Time Complexity
3.  Space Complexity
4.  Edge Cases
5.  Java implementation

### Rules

-   Spend 15--25 minutes trying independently before looking for help.
-   Prefer `O(N)` or `O(N log N)` when possible.
-   Test empty input, one element, duplicates, negative values, and
    large inputs where applicable.
-   For the Mock Tests, use a timer and do not look at solutions.

------------------------------------------------------------------------

# Day 1 --- Arrays & Hashing

## 1. Missing Number

Given an array containing `N` distinct numbers from `0` to `N`, find the
missing number.

**Target:** O(N) time, O(1) extra space.

## 2. Two Sum

Given an integer array and a target, determine whether two different
elements sum to the target.

**Target:** O(N) time.

## 3. Maximum Difference

Find the maximum value of `A[j] - A[i]` where `j > i`.

**Target:** O(N) time.

## 4. Contains Duplicate

Determine whether an integer array contains any duplicate value.

**Target:** O(N) expected time.

------------------------------------------------------------------------

# Day 2 --- Strings

## 5. First Unique Character

Find the first character that occurs exactly once in a string.

**Target:** O(N).

## 6. Valid Anagram

Determine whether two strings are anagrams of each other.

**Target:** O(N).

## 7. Character Frequency

Return the character with the highest frequency in a string. Define a
deterministic tie-breaking rule.

**Target:** O(N).

## 8. String Compression

Compress consecutive repeated characters.

Example:

`aaabbc` → `a3b2c1`

**Target:** O(N).

------------------------------------------------------------------------

# Day 3 --- Sliding Window & Two Pointers

## 9. Longest Substring Without Repeating Characters

Find the length of the longest substring containing no repeated
characters.

**Target:** O(N).

## 10. Maximum Sum Subarray of Size K

Find the maximum sum of any contiguous subarray of size `K`.

**Target:** O(N).

## 11. Pair With Given Difference

Determine whether the array contains two values whose absolute
difference equals `K`.

**Target:** O(N) expected time.

## 12. Move Zeroes

Move all zeroes to the end while preserving the relative order of
non-zero elements.

**Target:** O(N), preferably O(1) extra space.

------------------------------------------------------------------------

# Day 4 --- Prefix Sum & Counting

## 13. Range Sum Queries

Given an integer array and multiple `[L, R]` queries, return the sum for
each range efficiently.

**Target:** O(N + Q).

## 14. Equilibrium Index

Find an index where the sum of elements before it equals the sum after
it.

**Target:** O(N).

## 15. Count Passing Cars

Given an array containing `0` and `1`, count pairs `(P, Q)` where
`P < Q`, `A[P] = 0`, and `A[Q] = 1`.

**Target:** O(N).

## 16. Distinct Values

Count the number of distinct values in an integer array.

**Target:** O(N) expected time.

------------------------------------------------------------------------

# Day 5 --- Sorting & Searching

## 17. Binary Search

Find the index of a target value in a sorted array.

**Target:** O(log N).

## 18. First Occurrence

In a sorted array with duplicates, find the first occurrence of a
target.

**Target:** O(log N).

## 19. Merge Intervals

Given intervals `[start, end]`, merge all overlapping intervals.

**Target:** O(N log N).

## 20. Minimum Number of Platforms / Resources

Given start and end times of events, find the minimum number of
resources required so that no overlapping events conflict.

**Target:** O(N log N).

------------------------------------------------------------------------

# Day 6 --- Stack & Queue

## 21. Valid Parentheses

Determine whether a string containing `()[]{}` is correctly balanced.

**Target:** O(N).

## 22. Brackets

Given a string containing three types of brackets, return whether it is
properly nested.

**Target:** O(N).

## 23. Next Greater Element

For every element, find the next element to its right that is greater.

**Target:** O(N).

## 24. Queue Simulation

Simulate a queue where people/jobs are processed according to a
specified priority rule and determine the processing order or waiting
time.

**Target:** Aim for O(N log N) or better depending on the rule.

------------------------------------------------------------------------

# Day 7 --- Linked List & Trees

## 25. Reverse Linked List

Reverse a singly linked list.

**Target:** O(N) time, O(1) extra space.

## 26. Detect Linked List Cycle

Determine whether a linked list contains a cycle.

**Target:** O(N) time, O(1) space.

## 27. Binary Tree Maximum Depth

Find the maximum depth of a binary tree.

**Target:** O(N).

## 28. Binary Tree Level Order Traversal

Return the values of a binary tree level by level.

**Target:** O(N).

------------------------------------------------------------------------

# Day 8 --- Greedy & Medium Problems

## 29. Maximum Non-Overlapping Activities

Given activity start/end times, select the maximum number of
non-overlapping activities.

**Target:** O(N log N).

## 30. Minimum Jumps

Given an array where each element represents the maximum jump length
from that position, determine the minimum number of jumps needed to
reach the end.

**Target:** Prefer O(N).

## 31. Best Time to Buy and Sell Stock

Given daily prices, find the maximum profit from one buy and one later
sell.

**Target:** O(N).

## 32. Product of Array Except Self

For each position, return the product of all other elements without
using division.

**Target:** O(N).

------------------------------------------------------------------------

# Day 9 --- Dynamic Programming Basics

## 33. Climbing Stairs

You can climb either 1 or 2 steps at a time. Find the number of distinct
ways to reach step `N`.

**Target:** O(N) time, O(1) extra space if possible.

## 34. House Robber

Given amounts of money in houses arranged in a line, maximize the amount
stolen without choosing adjacent houses.

**Target:** O(N).

------------------------------------------------------------------------

# Day 10 --- Codility-Style Challenge Problems

## 35. Permutation Check

Given an array of integers, determine whether it contains every value
from `1` to `N` exactly once.

**Target:** O(N) expected time or O(N log N).

## 36. Smallest Missing Positive

Find the smallest positive integer that does not occur in an unsorted
integer array.

**Target:** O(N) time and O(1) extra space if possible.

## 37. Longest Consecutive Sequence

Find the length of the longest sequence of consecutive integers.

Example:

`[100, 4, 200, 1, 3, 2]` → `4`

**Target:** O(N) expected time.

## 38. Leader / Dominant Value

Find whether an array contains a value occurring in more than half of
the positions, and return an appropriate result.

**Target:** O(N), O(1) extra space preferred.

------------------------------------------------------------------------

# Bonus Problems

These are useful if the core set becomes comfortable.

## 39. Rotate Array

Rotate an array to the right by `K` positions.

**Target:** O(N), O(1) extra space preferred.

## 40. Missing Ranges

Given a sorted array and a range `[lower, upper]`, return all missing
ranges.

**Target:** O(N).

## 41. Maximum Subarray

Find the contiguous subarray with the maximum sum.

**Target:** O(N).

## 42. Minimum Size Subarray Sum

Find the minimum-length contiguous subarray whose sum is at least `K`.

**Target:** O(N) for positive numbers.

## 43. Top K Frequent Elements

Return the `K` most frequent values.

**Target:** Better than O(N log N) if possible.

## 44. Meeting Rooms

Determine whether all meetings can be attended without overlap.

**Target:** O(N log N).

------------------------------------------------------------------------

# Mock Test 1 --- 60 Minutes

- [x] **Completed & Verified** --- See [Mock_Test_1 README](Mock_Test_1/README.md)

### [Problem A --- Easy/Medium](Mock_Test_1/ProblemA_FirstMissingPositive.java)

Find the first missing positive integer in an unsorted array.

### [Problem B --- Medium](Mock_Test_1/ProblemB_LongestSubstringKDistinct.java)

Find the length of the longest substring containing at most `K` distinct
characters.

### [Problem C --- Medium](Mock_Test_1/ProblemC_MinimumMachinesRequired.java)

Given intervals representing jobs, find the minimum number of machines
required.

**Time:** 60 minutes.

------------------------------------------------------------------------

# Mock Test 2 --- 75 Minutes

- [x] **Completed & Verified** --- See [Mock_Test_2 README](Mock_Test_2/README.md)

### [Problem A](Mock_Test_2/ProblemA_PairSumCount.java)

Count the number of pairs whose sum equals a target.

### [Problem B](Mock_Test_2/ProblemB_StockMaxProfitMultipleTransactions.java)

Given an array of daily prices, find the maximum profit when you may buy
and sell multiple times, subject to the stated transaction rule.

### [Problem C](Mock_Test_2/ProblemC_SmallestSubarraySumK.java)

Find the smallest subarray whose sum is at least `K` for positive
integers.

**Time:** 75 minutes.

------------------------------------------------------------------------

# Mock Test 3 --- 90 Minutes

- [x] **Completed & Verified** --- See [Mock_Test_3 README](Mock_Test_3/README.md)

### [Problem A --- Arrays](Mock_Test_3/ProblemA_MaximumDifference.java)

Given an integer array, find the maximum difference `A[j] - A[i]` where
`j > i`.

### [Problem B --- Stack](Mock_Test_3/ProblemB_NextGreaterElement.java)

For every element, find the nearest greater element on its right.

### [Problem C --- Prefix Sum](Mock_Test_3/ProblemC_RangeSumQueries.java)

Given an array and many range queries, return the sum for every query
efficiently.

### [Problem D --- Mixed](Mock_Test_3/ProblemD_LongestConsecutiveSequence.java)

Find the longest consecutive sequence in an unsorted array.

**Time:** 90 minutes.

------------------------------------------------------------------------

# Final Revision Checklist

Before taking the Codility assessment, make sure you can recognize these
patterns quickly:

-   [x] [**HashMap lookup**](Day_01_Arrays_and_Hashing/Problem02_TwoSum.java) --- [Problem02_TwoSum.java](Day_01_Arrays_and_Hashing/Problem02_TwoSum.java) | [Day 01 Notes](Day_01_Arrays_and_Hashing/README.md)
-   [x] [**HashSet for uniqueness**](Day_01_Arrays_and_Hashing/Problem04_ContainsDuplicate.java) --- [Problem04_ContainsDuplicate.java](Day_01_Arrays_and_Hashing/Problem04_ContainsDuplicate.java) | [Problem37_LongestConsecutiveSequence.java](Day_10_Codility_Style_Challenge_Problems/Problem37_LongestConsecutiveSequence.java)
-   [x] [**Frequency counting**](Day_02_Strings/Problem05_FirstUniqueCharacter.java) --- [Problem05_FirstUniqueCharacter.java](Day_02_Strings/Problem05_FirstUniqueCharacter.java) | [Problem43_TopKFrequentElements.java](Bonus_Problems/Problem43_TopKFrequentElements.java)
-   [x] [**Two pointers**](Day_03_Sliding_Window_and_Two_Pointers/Problem11_PairWithGivenDifference.java) --- [Problem11_PairWithGivenDifference.java](Day_03_Sliding_Window_and_Two_Pointers/Problem11_PairWithGivenDifference.java) | [Problem12_MoveZeroes.java](Day_03_Sliding_Window_and_Two_Pointers/Problem12_MoveZeroes.java)
-   [x] [**Sliding window**](Day_03_Sliding_Window_and_Two_Pointers/Problem09_LongestSubstringWithoutRepeatingCharacters.java) --- [Problem09_LongestSubstringWithoutRepeatingCharacters.java](Day_03_Sliding_Window_and_Two_Pointers/Problem09_LongestSubstringWithoutRepeatingCharacters.java) | [Problem42_MinimumSizeSubarraySum.java](Bonus_Problems/Problem42_MinimumSizeSubarraySum.java)
-   [x] [**Prefix sum**](Day_04_Prefix_Sum_and_Counting/Problem13_RangeSumQueries.java) --- [Problem13_RangeSumQueries.java](Day_04_Prefix_Sum_and_Counting/Problem13_RangeSumQueries.java) | [Problem14_EquilibriumIndex.java](Day_04_Prefix_Sum_and_Counting/Problem14_EquilibriumIndex.java)
-   [x] [**Kadane's algorithm**](Bonus_Problems/Problem41_MaximumSubarray.java) --- [Problem41_MaximumSubarray.java](Bonus_Problems/Problem41_MaximumSubarray.java) | [Bonus Problems Notes](Bonus_Problems/README.md)
-   [x] [**Sorting + greedy**](Day_05_Sorting_and_Searching/Problem19_MergeIntervals.java) --- [Problem19_MergeIntervals.java](Day_05_Sorting_and_Searching/Problem19_MergeIntervals.java) | [Problem29_MaximumNonOverlappingActivities.java](Day_08_Greedy_and_Medium_Problems/Problem29_MaximumNonOverlappingActivities.java)
-   [x] [**Binary search**](Day_05_Sorting_and_Searching/Problem17_BinarySearch.java) --- [Problem17_BinarySearch.java](Day_05_Sorting_and_Searching/Problem17_BinarySearch.java) | [Problem18_FirstOccurrence.java](Day_05_Sorting_and_Searching/Problem18_FirstOccurrence.java)
-   [x] [**Stack / monotonic stack**](Day_06_Stack_and_Queue/Problem21_ValidParentheses.java) --- [Problem21_ValidParentheses.java](Day_06_Stack_and_Queue/Problem21_ValidParentheses.java) | [Problem23_NextGreaterElement.java](Day_06_Stack_and_Queue/Problem23_NextGreaterElement.java)
-   [x] [**Queue / BFS**](Day_06_Stack_and_Queue/Problem24_QueueSimulation.java) --- [Problem24_QueueSimulation.java](Day_06_Stack_and_Queue/Problem24_QueueSimulation.java) | [Problem28_BinaryTreeLevelOrderTraversal.java](Day_07_Linked_List_and_Trees/Problem28_BinaryTreeLevelOrderTraversal.java)
-   [x] [**DFS**](Day_07_Linked_List_and_Trees/Problem27_BinaryTreeMaximumDepth.java) --- [Problem27_BinaryTreeMaximumDepth.java](Day_07_Linked_List_and_Trees/Problem27_BinaryTreeMaximumDepth.java)
-   [x] [**Linked-list fast/slow pointers**](Day_07_Linked_List_and_Trees/Problem25_ReverseLinkedList.java) --- [Problem25_ReverseLinkedList.java](Day_07_Linked_List_and_Trees/Problem25_ReverseLinkedList.java) | [Problem26_DetectLinkedListCycle.java](Day_07_Linked_List_and_Trees/Problem26_DetectLinkedListCycle.java)
-   [x] [**Basic dynamic programming**](Day_09_Dynamic_Programming_Basics/Problem33_ClimbingStairs.java) --- [Problem33_ClimbingStairs.java](Day_09_Dynamic_Programming_Basics/Problem33_ClimbingStairs.java) | [Problem34_HouseRobber.java](Day_09_Dynamic_Programming_Basics/Problem34_HouseRobber.java)
-   [x] **Time complexity analysis** --- Detailed O(N), O(N log N), O(2^N) analyses across all Day 1--10 & Bonus README notes
-   [x] **Space complexity analysis** --- Detailed auxiliary space analyses across all Day 1--10 & Bonus README notes
-   [x] **Edge-case testing** --- Built into unit test suites (null, empty, single element, negative values, duplicates)

# Java Checklist

Be comfortable with:

-   [x] [HashMap](Day_01_Arrays_and_Hashing/Problem02_TwoSum.java) --- [Problem02_TwoSum.java](Day_01_Arrays_and_Hashing/Problem02_TwoSum.java) | [Problem38_LeaderDominantValue.java](Day_10_Codility_Style_Challenge_Problems/Problem38_LeaderDominantValue.java)
-   [x] [HashSet](Day_01_Arrays_and_Hashing/Problem04_ContainsDuplicate.java) --- [Problem04_ContainsDuplicate.java](Day_01_Arrays_and_Hashing/Problem04_ContainsDuplicate.java) | [Problem36_SmallestMissingPositive.java](Day_10_Codility_Style_Challenge_Problems/Problem36_SmallestMissingPositive.java)
-   [x] [ArrayList](Day_05_Sorting_and_Searching/Problem19_MergeIntervals.java) --- [Problem19_MergeIntervals.java](Day_05_Sorting_and_Searching/Problem19_MergeIntervals.java) | [Problem40_MissingRanges.java](Bonus_Problems/Problem40_MissingRanges.java)
-   [x] [Arrays.sort()](Day_05_Sorting_and_Searching/Problem19_MergeIntervals.java) --- [Problem19_MergeIntervals.java](Day_05_Sorting_and_Searching/Problem19_MergeIntervals.java) | [Problem44_MeetingRooms.java](Bonus_Problems/Problem44_MeetingRooms.java)
-   [x] [Collections.sort()](Day_05_Sorting_and_Searching/README.md) --- [Day 05 README Notes](Day_05_Sorting_and_Searching/README.md)
-   [x] [Deque](Day_06_Stack_and_Queue/Problem21_ValidParentheses.java) --- [Problem21_ValidParentheses.java](Day_06_Stack_and_Queue/Problem21_ValidParentheses.java) | [Problem23_NextGreaterElement.java](Day_06_Stack_and_Queue/Problem23_NextGreaterElement.java)
-   [x] [PriorityQueue](Bonus_Problems/Problem43_TopKFrequentElements.java) --- [Problem43_TopKFrequentElements.java](Bonus_Problems/Problem43_TopKFrequentElements.java)
-   [x] [StringBuilder](Day_02_Strings/Problem08_StringCompression.java) --- [Problem08_StringCompression.java](Day_02_Strings/Problem08_StringCompression.java)
-   [x] [char[]](Day_02_Strings/Problem05_FirstUniqueCharacter.java) --- [Problem05_FirstUniqueCharacter.java](Day_02_Strings/Problem05_FirstUniqueCharacter.java) | [Problem06_ValidAnagram.java](Day_02_Strings/Problem06_ValidAnagram.java)
-   [x] [Arrays.binarySearch()](Day_05_Sorting_and_Searching/Problem17_BinarySearch.java) --- and implementing binary search manually: [Problem17_BinarySearch.java](Day_05_Sorting_and_Searching/Problem17_BinarySearch.java)
-   [x] [Custom Comparator](Day_05_Sorting_and_Searching/Problem20_MinimumNumberOfPlatforms.java) --- [Problem20_MinimumNumberOfPlatforms.java](Day_05_Sorting_and_Searching/Problem20_MinimumNumberOfPlatforms.java) | [Problem29_MaximumNonOverlappingActivities.java](Day_08_Greedy_and_Medium_Problems/Problem29_MaximumNonOverlappingActivities.java)
-   [x] Primitive arrays for performance --- Applied across all 44 Java problem implementations
-   [x] Avoiding unnecessary nested loops --- Demonstrated in all O(N) optimized solutions

# Assessment Strategy

1.  Read every problem before coding.
2.  Start with the easiest problem.
3.  Identify constraints before choosing the algorithm.
4.  Write the simplest correct solution first.
5.  Improve complexity if constraints require it.
6.  Test boundary cases manually.
7.  Do not spend too long stuck on one problem.
8.  Keep enough time for compilation and final testing.
9.  Prefer readable Java over unnecessarily clever code.
10. Always ask: **Can this O(N²) solution become O(N log N) or O(N)?**

## Target Before Assessment

**Minimum:** 30 problems solved independently\
**Good:** 38+ problems solved\
**Excellent:** 38+ problems + all 3 mock tests completed under time
