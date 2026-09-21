package Day_04_Prefix_Sum_and_Counting;

import java.util.Arrays;

/**
 * Day 4 - Problem 13: Range Sum Queries
 * Question: Given an integer array and multiple [L, R] queries, return the sum for each range efficiently.
 */
public class Problem13_RangeSumQueries {

    // ==========================================
    // 1. BRUTE FORCE APPROACH
    // Sum elements from index L to R for every query.
    // Time Complexity: O(Q * N) where Q is the number of queries
    // Space Complexity: O(1)
    // ==========================================
    public static long rangeSumBruteForce(int[] nums, int left, int right) {
        if (nums == null || left < 0 || right >= nums.length || left > right) {
            throw new IllegalArgumentException("Invalid query bounds or array.");
        }
        long sum = 0;
        for (int i = left; i <= right; i++) {
            sum += nums[i];
        }
        return sum;
    }

    // ==========================================
    // 2. OPTIMAL APPROACH (Prefix Sum Array)
    // Precompute prefixSum array of size N + 1 where prefixSum[i] stores sum of first i elements.
    // Range sum [L, R] = prefixSum[R + 1] - prefixSum[L]
    // Time Complexity: O(N) precomputation, O(1) per query -> O(N + Q) total
    // Space Complexity: O(N) for prefix sum array
    // ==========================================
    public static class NumArray {
        private final long[] prefixSum;

        public NumArray(int[] nums) {
            if (nums == null) {
                this.prefixSum = new long[1];
                return;
            }
            int n = nums.length;
            this.prefixSum = new long[n + 1];
            for (int i = 0; i < n; i++) {
                this.prefixSum[i + 1] = this.prefixSum[i] + nums[i];
            }
        }

        public long sumRange(int left, int right) {
            if (left < 0 || right >= prefixSum.length - 1 || left > right) {
                throw new IllegalArgumentException("Invalid range query bounds.");
            }
            return prefixSum[right + 1] - prefixSum[left];
        }
    }

    // Quick Test / Demo
    public static void main(String[] args) {
        int[] nums = {-2, 0, 3, -5, 2, -1};

        System.out.println("Input Array: " + Arrays.toString(nums));

        // Brute force testing
        System.out.println("Query [0, 2] (Brute Force): " + rangeSumBruteForce(nums, 0, 2)); // -2 + 0 + 3 = 1
        System.out.println("Query [2, 5] (Brute Force): " + rangeSumBruteForce(nums, 2, 5)); // 3 + (-5) + 2 + (-1) = -1
        System.out.println("Query [0, 5] (Brute Force): " + rangeSumBruteForce(nums, 0, 5)); // sum of all = -3

        // Prefix Sum testing
        NumArray numArray = new NumArray(nums);
        System.out.println("\nQuery [0, 2] (Prefix Sum): " + numArray.sumRange(0, 2));
        System.out.println("Query [2, 5] (Prefix Sum): " + numArray.sumRange(2, 5));
        System.out.println("Query [0, 5] (Prefix Sum): " + numArray.sumRange(0, 5));
        System.out.println("Query [3, 3] (Single Element): " + numArray.sumRange(3, 3)); // -5
    }
}
