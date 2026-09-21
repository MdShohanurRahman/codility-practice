package Day_08_Greedy_and_Medium_Problems;

import java.util.Arrays;

/**
 * Day 8 - Problem 30: Minimum Jumps (Jump Game II)
 * Question: Given an array nums where nums[i] represents maximum jump length from position i,
 * find the minimum number of jumps to reach the last index.
 * 
 * Target: O(N) Time, O(1) Space.
 */
public class Problem30_MinimumJumps {

    // ==========================================
    // 1. BRUTE FORCE / DP APPROACH
    // dp[i] represents minimum jumps needed to reach end from index i.
    // Time Complexity: O(N^2)
    // Space Complexity: O(N) for DP table
    // ==========================================
    public static int minJumpsDP(int[] nums) {
        if (nums == null || nums.length <= 1) return 0;

        int n = nums.length;
        int[] dp = new int[n];
        Arrays.fill(dp, Integer.MAX_VALUE - 1); // Avoid overflow when adding 1
        dp[n - 1] = 0; // 0 jumps needed from destination to destination

        for (int i = n - 2; i >= 0; i--) {
            int maxJump = nums[i];
            for (int step = 1; step <= maxJump && (i + step) < n; step++) {
                dp[i] = Math.min(dp[i], 1 + dp[i + step]);
            }
        }

        return dp[0] >= Integer.MAX_VALUE - 1 ? -1 : dp[0];
    }

    // ==========================================
    // 2. OPTIMAL APPROACH (Greedy Range Window / BFS)
    // Track current jump reach boundary (currentEnd) and max reachable index (farthest).
    // Increment jumps whenever we hit currentEnd.
    // Time Complexity: O(N) single linear scan
    // Space Complexity: O(1) auxiliary space
    // ==========================================
    public static int minJumpsOptimal(int[] nums) {
        if (nums == null || nums.length <= 1) return 0;

        int jumps = 0;
        int currentEnd = 0;
        int farthest = 0;

        // Loop up to nums.length - 1 because when we reach N - 1, we don't need to jump again
        for (int i = 0; i < nums.length - 1; i++) {
            farthest = Math.max(farthest, i + nums[i]);

            // If we've reached the end of the current jump range window
            if (i == currentEnd) {
                jumps++;
                currentEnd = farthest;

                // Optimization: if we can already reach the last index, break early
                if (currentEnd >= nums.length - 1) {
                    break;
                }
            }
        }

        return currentEnd >= nums.length - 1 ? jumps : -1;
    }

    // Quick Test / Demo
    public static void main(String[] args) {
        int[] test1 = {2, 3, 1, 1, 4}; // Expected: 2 (Jump 0->1->4)
        int[] test2 = {2, 3, 0, 1, 4}; // Expected: 2 (Jump 0->1->4)
        int[] test3 = {1, 1, 1, 1};    // Expected: 3 (Jump 0->1->2->3)
        int[] test4 = {0};             // Expected: 0 (Already at destination)

        System.out.println("Test 1 " + Arrays.toString(test1));
        System.out.println("  DP Approach:      " + minJumpsDP(test1));
        System.out.println("  Greedy Optimal:   " + minJumpsOptimal(test1));

        System.out.println("\nTest 2 " + Arrays.toString(test2));
        System.out.println("  DP Approach:      " + minJumpsDP(test2));
        System.out.println("  Greedy Optimal:   " + minJumpsOptimal(test2));

        System.out.println("\nTest 3 " + Arrays.toString(test3));
        System.out.println("  Greedy Optimal:   " + minJumpsOptimal(test3));

        System.out.println("\nTest 4 " + Arrays.toString(test4));
        System.out.println("  Greedy Optimal:   " + minJumpsOptimal(test4));
    }
}
