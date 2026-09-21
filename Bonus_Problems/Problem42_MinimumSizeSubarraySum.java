package Bonus_Problems;

import java.util.Arrays;

/**
 * Bonus Problem 42: Minimum Size Subarray Sum
 * Question: Find the minimum length of a contiguous subarray whose sum is at least target (K).
 * 
 * Target: O(N) Time (for positive numbers), O(1) Auxiliary Space.
 */
public class Problem42_MinimumSizeSubarraySum {

    // ==========================================
    // 1. BRUTE FORCE APPROACH
    // Check all possible subarrays (i, j) and find min length where sum >= target.
    // Time Complexity: O(N^2)
    // Space Complexity: O(1)
    // ==========================================
    public static int minSubArrayLenBruteForce(int target, int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        int minLen = Integer.MAX_VALUE;
        int n = nums.length;

        for (int i = 0; i < n; i++) {
            int sum = 0;
            for (int j = i; j < n; j++) {
                sum += nums[j];
                if (sum >= target) {
                    minLen = Math.min(minLen, j - i + 1);
                    break; // Early exit since nums contain positive numbers
                }
            }
        }

        return minLen == Integer.MAX_VALUE ? 0 : minLen;
    }

    // ==========================================
    // 2. OPTIMAL APPROACH (Sliding Window / Dynamic Two Pointers)
    // Expand right boundary to add elements.
    // Shrink left boundary as long as window sum >= target to minimize window length.
    // Time Complexity: O(N) - each element is processed at most twice (by left and right pointers)
    // Space Complexity: O(1) auxiliary space
    // ==========================================
    public static int minSubArrayLenOptimal(int target, int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        int left = 0;
        int sum = 0;
        int minLen = Integer.MAX_VALUE;

        for (int right = 0; right < nums.length; right++) {
            sum += nums[right];

            // Contract window from the left as long as target condition is satisfied
            while (sum >= target) {
                minLen = Math.min(minLen, right - left + 1);
                sum -= nums[left];
                left++;
            }
        }

        return minLen == Integer.MAX_VALUE ? 0 : minLen;
    }

    // Quick Test / Demo
    public static void main(String[] args) {
        int target1 = 7;
        int[] nums1 = {2, 3, 1, 2, 4, 3}; // Expected: 2 ([4, 3])

        int target2 = 4;
        int[] nums2 = {1, 4, 4}; // Expected: 1 ([4])

        int target3 = 11;
        int[] nums3 = {1, 1, 1, 1, 1, 1, 1, 1}; // Expected: 0 (No valid subarray)

        System.out.println("--- Bonus Problem 42: Minimum Size Subarray Sum ---");

        System.out.println("\nTest 1 (target=" + target1 + "): " + Arrays.toString(nums1));
        System.out.println("  Brute Force: " + minSubArrayLenBruteForce(target1, nums1));
        System.out.println("  Sliding Window: " + minSubArrayLenOptimal(target1, nums1));

        System.out.println("\nTest 2 (target=" + target2 + "): " + Arrays.toString(nums2));
        System.out.println("  Brute Force: " + minSubArrayLenBruteForce(target2, nums2));
        System.out.println("  Sliding Window: " + minSubArrayLenOptimal(target2, nums2));

        System.out.println("\nTest 3 (target=" + target3 + "): " + Arrays.toString(nums3));
        System.out.println("  Brute Force: " + minSubArrayLenBruteForce(target3, nums3));
        System.out.println("  Sliding Window: " + minSubArrayLenOptimal(target3, nums3));
    }
}
