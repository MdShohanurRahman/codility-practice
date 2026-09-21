package Day_05_Sorting_and_Searching;

import java.util.Arrays;

/**
 * Day 5 - Problem 18: First Occurrence
 * Question: In a sorted array with duplicates, find the first occurrence index of a target.
 * Return -1 if the target is not present.
 * 
 * Target: O(log N) Time, O(1) Extra Space.
 */
public class Problem18_FirstOccurrence {

    // ==========================================
    // 1. BRUTE FORCE APPROACH (Linear Search)
    // Scan from left to right; return index of first matching target.
    // Time Complexity: O(N)
    // Space Complexity: O(1)
    // ==========================================
    public static int firstOccurrenceBruteForce(int[] nums, int target) {
        if (nums == null || nums.length == 0) return -1;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target) {
                return i;
            }
        }
        return -1;
    }

    // ==========================================
    // 2. OPTIMAL APPROACH (Modified Binary Search)
    // When target is found at mid, save mid as candidate result and search LEFT half.
    // Time Complexity: O(log N)
    // Space Complexity: O(1)
    // ==========================================
    public static int firstOccurrenceOptimal(int[] nums, int target) {
        if (nums == null || nums.length == 0) return -1;

        int low = 0;
        int high = nums.length - 1;
        int result = -1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (nums[mid] == target) {
                result = mid;       // Record potential first occurrence
                high = mid - 1;     // Keep searching on the left half to find an earlier index
            } else if (nums[mid] < target) {
                low = mid + 1;      // Target must be on the right half
            } else {
                high = mid - 1;     // Target must be on the left half
            }
        }

        return result;
    }

    // Quick Test / Demo
    public static void main(String[] args) {
        int[] nums1 = {1, 2, 2, 2, 3, 4, 5};
        int target1 = 2; // Expected first occurrence at index 1

        int[] nums2 = {5, 7, 7, 8, 8, 10};
        int target2 = 8; // Expected first occurrence at index 3
        int target3 = 6; // Not found -> -1

        System.out.println("Array 1: " + Arrays.toString(nums1));
        System.out.println("First occurrence of " + target1 + " (Brute Force): " + firstOccurrenceBruteForce(nums1, target1));
        System.out.println("First occurrence of " + target1 + " (Optimal):     " + firstOccurrenceOptimal(nums1, target1));

        System.out.println("\nArray 2: " + Arrays.toString(nums2));
        System.out.println("First occurrence of " + target2 + " (Optimal):     " + firstOccurrenceOptimal(nums2, target2));
        System.out.println("First occurrence of " + target3 + " (Optimal):     " + firstOccurrenceOptimal(nums2, target3));

        // Edge case: All elements are identical
        int[] allSame = {2, 2, 2, 2, 2};
        System.out.println("\nAll elements identical [2, 2, 2, 2, 2] target 2: " + firstOccurrenceOptimal(allSame, 2));
    }
}
