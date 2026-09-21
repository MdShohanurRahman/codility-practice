package Day_05_Sorting_and_Searching;

import java.util.Arrays;

/**
 * Day 5 - Problem 17: Binary Search
 * Question: Find the index of a target value in a sorted array. Return -1 if target is not found.
 * 
 * Target: O(log N) Time, O(1) Extra Space.
 */
public class Problem17_BinarySearch {

    // ==========================================
    // 1. BRUTE FORCE APPROACH (Linear Search)
    // Scan every element sequentially from index 0 to N-1.
    // Time Complexity: O(N)
    // Space Complexity: O(1)
    // ==========================================
    public static int binarySearchBruteForce(int[] nums, int target) {
        if (nums == null || nums.length == 0) return -1;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target) {
                return i;
            }
        }
        return -1;
    }

    // ==========================================
    // 2. OPTIMAL APPROACH (Iterative Binary Search)
    // Divide search space in half at each step using low and high pointers.
    // Time Complexity: O(log N)
    // Space Complexity: O(1)
    // ==========================================
    public static int binarySearchOptimal(int[] nums, int target) {
        if (nums == null || nums.length == 0) return -1;

        int low = 0;
        int high = nums.length - 1;

        while (low <= high) {
            // Prevent potential integer overflow compared to (low + high) / 2
            int mid = low + (high - low) / 2;

            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                low = mid + 1; // Search right half
            } else {
                high = mid - 1; // Search left half
            }
        }

        return -1; // Target not present in array
    }

    // Quick Test / Demo
    public static void main(String[] args) {
        int[] nums1 = {-1, 0, 3, 5, 9, 12};
        int target1 = 9; // Index 4
        int target2 = 2; // Not found -> -1

        System.out.println("Array: " + Arrays.toString(nums1));
        System.out.println("Search target " + target1 + " (Brute Force): " + binarySearchBruteForce(nums1, target1));
        System.out.println("Search target " + target1 + " (Optimal):     " + binarySearchOptimal(nums1, target1));

        System.out.println("\nSearch target " + target2 + " (Brute Force): " + binarySearchBruteForce(nums1, target2));
        System.out.println("Search target " + target2 + " (Optimal):     " + binarySearchOptimal(nums1, target2));

        // Edge Cases
        int[] empty = {};
        int[] single = {5};
        System.out.println("\nEmpty Array target 5: " + binarySearchOptimal(empty, 5));
        System.out.println("Single Element [5] target 5: " + binarySearchOptimal(single, 5));
        System.out.println("Single Element [5] target 1: " + binarySearchOptimal(single, 1));
    }
}
