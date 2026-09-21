package Day_01_Arrays_and_Hashing;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Day 1 - Problem 4: Contains Duplicate
 * Question: Determine whether an integer array contains any duplicate value.
 */
public class Problem4_ContainsDuplicate {

    // ==========================================
    // 1. BRUTE FORCE APPROACH (Nested Loops)
    // Time Complexity: O(N^2)
    // Space Complexity: O(1)
    // ==========================================
    public static boolean containsDuplicateBruteForce(int[] nums) {
        if (nums == null || nums.length < 2) return false;
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (nums[i] == nums[j]) {
                    return true;
                }
            }
        }
        return false;
    }

    // ==========================================
    // 2. SORTING APPROACH
    // Time Complexity: O(N log N)
    // Space Complexity: O(1) or O(log N) depending on sort
    // ==========================================
    public static boolean containsDuplicateSorting(int[] nums) {
        if (nums == null || nums.length < 2) return false;
        int[] copy = nums.clone();
        Arrays.sort(copy);
        for (int i = 1; i < copy.length; i++) {
            if (copy[i] == copy[i - 1]) {
                return true;
            }
        }
        return false;
    }

    // ==========================================
    // 3. OPTIMAL APPROACH (HashSet)
    // Time Complexity: O(N) expected
    // Space Complexity: O(N)
    // ==========================================
    public static boolean containsDuplicateOptimal(int[] nums) {
        if (nums == null || nums.length < 2) return false;
        Set<Integer> seen = new HashSet<>();
        for (int num : nums) {
            if (!seen.add(num)) { // add returns false if already present
                return true;
            }
        }
        return false;
    }

    // Demo
    public static void main(String[] args) {
        int[] nums1 = {1, 2, 3, 1};
        int[] nums2 = {1, 2, 3, 4};
        int[] nums3 = {1, 1, 1, 3, 3, 4, 3, 2, 4, 2};

        System.out.println("Test 1 (Optimal): " + containsDuplicateOptimal(nums1)); // true
        System.out.println("Test 2 (Optimal): " + containsDuplicateOptimal(nums2)); // false
        System.out.println("Test 3 (Optimal): " + containsDuplicateOptimal(nums3)); // true
    }
}
