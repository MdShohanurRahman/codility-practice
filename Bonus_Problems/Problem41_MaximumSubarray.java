package Bonus_Problems;

import java.util.Arrays;

/**
 * Bonus Problem 41: Maximum Subarray (Kadane's Algorithm)
 * Question: Find the contiguous subarray with the maximum sum.
 * 
 * Target: O(N) Time, O(1) Extra Space.
 */
public class Problem41_MaximumSubarray {

    // ==========================================
    // 1. BRUTE FORCE APPROACH
    // Evaluate sum of all possible contiguous subarrays.
    // Time Complexity: O(N^2)
    // Space Complexity: O(1)
    // ==========================================
    public static int maxSubArrayBruteForce(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        int maxSum = Integer.MIN_VALUE;
        int n = nums.length;

        for (int i = 0; i < n; i++) {
            int currentSum = 0;
            for (int j = i; j < n; j++) {
                currentSum += nums[j];
                maxSum = Math.max(maxSum, currentSum);
            }
        }

        return maxSum;
    }

    // ==========================================
    // 2. OPTIMAL APPROACH (Kadane's Algorithm)
    // At each element nums[i], decide whether to:
    //   Option A: Extend the previous running subarray (currentSum + nums[i])
    //   Option B: Start a fresh new subarray starting at nums[i]
    // Recurrence: currentSum = max(nums[i], currentSum + nums[i])
    // Time Complexity: O(N) linear pass
    // Space Complexity: O(1) auxiliary space
    // ==========================================
    public static int maxSubArrayOptimal(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        int maxSoFar = nums[0];
        int currentSum = nums[0];

        for (int i = 1; i < nums.length; i++) {
            // Decide to extend or reset running subarray
            currentSum = Math.max(nums[i], currentSum + nums[i]);
            // Track globally maximum sum encountered so far
            maxSoFar = Math.max(maxSoFar, currentSum);
        }

        return maxSoFar;
    }

    // Kadane's Algorithm returning the start and end indices of the max subarray
    public static int[] maxSubArrayWithIndices(int[] nums) {
        if (nums == null || nums.length == 0) return new int[]{0, -1, -1};

        int maxSoFar = nums[0];
        int currentSum = nums[0];

        int bestStart = 0, bestEnd = 0, tempStart = 0;

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > currentSum + nums[i]) {
                currentSum = nums[i];
                tempStart = i;
            } else {
                currentSum += nums[i];
            }

            if (currentSum > maxSoFar) {
                maxSoFar = currentSum;
                bestStart = tempStart;
                bestEnd = i;
            }
        }

        return new int[]{maxSoFar, bestStart, bestEnd};
    }

    // Quick Test / Demo
    public static void main(String[] args) {
        int[][] testCases = {
            {-2, 1, -3, 4, -1, 2, 1, -5, 4}, // Expected: 6 ([4, -1, 2, 1])
            {1},                             // Expected: 1 ([1])
            {5, 4, -1, 7, 8},                // Expected: 23 ([5, 4, -1, 7, 8])
            {-1, -2, -3, -4},                // Expected: -1 ([-1])
            {-2, -1}                         // Expected: -1 ([-1])
        };

        System.out.println("--- Bonus Problem 41: Maximum Subarray (Kadane's) ---");
        for (int[] nums : testCases) {
            System.out.println("\nArray: " + Arrays.toString(nums));
            System.out.println("  Brute Force Max Sum: " + maxSubArrayBruteForce(nums));
            System.out.println("  Kadane's Max Sum:    " + maxSubArrayOptimal(nums));
            int[] details = maxSubArrayWithIndices(nums);
            System.out.println("  Subarray Indices:    Range [" + details[1] + ", " + details[2] + "]");
        }
    }
}
