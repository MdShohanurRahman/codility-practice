package Day_10_Codility_Style_Challenge_Problems;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Day 10 - Problem 36: Smallest Missing Positive (Codility MissingInteger / LeetCode 41)
 * Question: Find the smallest positive integer (> 0) that does not occur in an unsorted integer array.
 * 
 * Target: O(N) Time, O(1) Extra Space.
 */
public class Problem36_SmallestMissingPositive {

    // ==========================================
    // 1. BRUTE FORCE APPROACH
    // Check numbers 1, 2, 3... one by one by searching through the array.
    // Time Complexity: O(N^2)
    // Space Complexity: O(1)
    // ==========================================
    public static int firstMissingPositiveBruteForce(int[] nums) {
        if (nums == null || nums.length == 0) return 1;

        int target = 1;
        while (true) {
            boolean found = false;
            for (int num : nums) {
                if (num == target) {
                    found = true;
                    break;
                }
            }
            if (!found) return target;
            target++;
        }
    }

    // ==========================================
    // 2. HASHSET APPROACH
    // Insert all positive numbers into a set, then query 1, 2, 3...
    // Time Complexity: O(N)
    // Space Complexity: O(N) extra space
    // ==========================================
    public static int firstMissingPositiveSet(int[] nums) {
        if (nums == null || nums.length == 0) return 1;

        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            if (num > 0) {
                set.add(num);
            }
        }

        int candidate = 1;
        while (set.contains(candidate)) {
            candidate++;
        }

        return candidate;
    }

    // ==========================================
    // 3. OPTIMAL APPROACH (Cyclic Sort / Index Placement)
    // Place each number x in range [1, N] at index x - 1 using in-place swapping.
    // After placement, scan array: first index i where nums[i] != i + 1 gives answer i + 1.
    // If all indices 0..N-1 contain 1..N, answer is N + 1.
    // Time Complexity: O(N) - each element is swapped at most once into its correct position
    // Space Complexity: O(1) auxiliary space
    // ==========================================
    public static int firstMissingPositiveOptimal(int[] A) {
        if (A == null || A.length == 0) return 1;

        int n = A.length;

        // Step 1: Place each number in its correct position if 1 <= A[i] <= n
        for (int i = 0; i < n; i++) {
            while (A[i] > 0 && A[i] <= n && A[A[i] - 1] != A[i]) {
                // Swap A[i] with A[A[i] - 1]
                int targetIndex = A[i] - 1;
                int temp = A[i];
                A[i] = A[targetIndex];
                A[targetIndex] = temp;
            }
        }

        // Step 2: Find the first index where A[i] != i + 1
        for (int i = 0; i < n; i++) {
            if (A[i] != i + 1) {
                return i + 1;
            }
        }

        // Step 3: If all numbers 1..N are present, answer is N + 1
        return n + 1;
    }

    // Quick Test / Demo
    public static void main(String[] args) {
        int[][] testCases = {
            {1, 3, 6, 4, 1, 2},     // Expected: 5
            {1, 2, 3},              // Expected: 4
            {-1, -3},               // Expected: 1
            {7, 8, 9, 11, 12},      // Expected: 1
            {3, 4, -1, 1},          // Expected: 2
            {2},                    // Expected: 1
            {1}                     // Expected: 2
        };

        System.out.println("--- Day 10 Problem 36: Smallest Missing Positive ---");
        for (int[] test : testCases) {
            System.out.println("\nArray: " + Arrays.toString(test));
            System.out.println("  Brute Force: " + firstMissingPositiveBruteForce(test.clone()));
            System.out.println("  HashSet:     " + firstMissingPositiveSet(test.clone()));
            System.out.println("  Optimal:     " + firstMissingPositiveOptimal(test.clone()));
        }
    }
}
