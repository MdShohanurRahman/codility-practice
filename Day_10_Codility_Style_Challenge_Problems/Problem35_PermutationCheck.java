package Day_10_Codility_Style_Challenge_Problems;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Day 10 - Problem 35: Permutation Check
 * Question: Given an array of integers, determine whether it contains every value from 1 to N exactly once.
 * 
 * Target: O(N) Time, O(1) or O(N) Space.
 */
public class Problem35_PermutationCheck {

    // ==========================================
    // 1. BRUTE FORCE APPROACH (Sorting)
    // Sort array and verify A[i] == i + 1 for all i from 0 to N-1.
    // Time Complexity: O(N log N) due to sorting
    // Space Complexity: O(1) auxiliary (or O(N) if modifying input is disallowed)
    // ==========================================
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

    // ==========================================
    // 2. FREQUENCY / HASHSET APPROACH
    // Check if array contains duplicates or elements outside range [1, N].
    // Time Complexity: O(N)
    // Space Complexity: O(N) extra space
    // ==========================================
    public static boolean isPermutationSet(int[] A) {
        if (A == null || A.length == 0) return false;

        int n = A.length;
        Set<Integer> seen = new HashSet<>();

        for (int num : A) {
            if (num < 1 || num > n) {
                return false; // Out of valid permutation range
            }
            if (!seen.add(num)) {
                return false; // Duplicate detected
            }
        }

        return seen.size() == n;
    }

    // ==========================================
    // 3. OPTIMAL APPROACH (Boolean Seen Array / In-Place Indexing)
    // Use a boolean tracking array or modify array in-place.
    // Time Complexity: O(N) linear pass
    // Space Complexity: O(N) boolean array (or O(1) in-place cycle placement)
    // ==========================================
    public static boolean isPermutationOptimal(int[] A) {
        if (A == null || A.length == 0) return false;

        int n = A.length;
        boolean[] seen = new boolean[n + 1];

        for (int num : A) {
            if (num < 1 || num > n) {
                return false;
            }
            if (seen[num]) {
                return false; // Duplicate element
            }
            seen[num] = true;
        }

        return true;
    }

    // Quick Test / Demo
    public static void main(String[] args) {
        int[][] testCases = {
            {4, 1, 3, 2},        // True (Permutation of 1..4)
            {4, 1, 3},           // False (Missing 2, length 3 but contains 4)
            {1, 1, 3},           // False (Duplicate 1)
            {1},                 // True (Permutation of 1..1)
            {2},                 // False (Length 1, contains 2)
            {1, 2, 3, 4, 5},     // True
            {1, 2, 3, 5, 6}      // False (Length 5, contains 6)
        };

        System.out.println("--- Day 10 Problem 35: Permutation Check ---");
        for (int[] test : testCases) {
            System.out.println("\nArray: " + Arrays.toString(test));
            System.out.println("  Sorting Approach: " + isPermutationSorting(test));
            System.out.println("  HashSet Approach: " + isPermutationSet(test));
            System.out.println("  Optimal Approach: " + isPermutationOptimal(test));
        }
    }
}
