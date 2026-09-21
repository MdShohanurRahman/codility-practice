package Day_09_Dynamic_Programming_Basics;

import java.util.Arrays;

/**
 * Day 9 - Problem 34: House Robber
 * Question: Given amounts of money in houses arranged in a line, maximize the amount stolen without choosing adjacent houses.
 * 
 * Target: O(N) Time, O(1) Space.
 */
public class Problem34_HouseRobber {

    // ==========================================
    // 1. BRUTE FORCE APPROACH (Naive Recursion)
    // At house i, choose either:
    //   Option 1: Rob house i + recurse on i-2
    //   Option 2: Skip house i + recurse on i-1
    // Time Complexity: O(2^N) - Exponential tree search.
    // Space Complexity: O(N) - Recursion stack depth.
    // ==========================================
    public static int robBruteForce(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        return robHelper(nums, nums.length - 1);
    }

    private static int robHelper(int[] nums, int i) {
        if (i < 0) return 0;
        if (i == 0) return nums[0];

        int robCurrent = nums[i] + robHelper(nums, i - 2);
        int skipCurrent = robHelper(nums, i - 1);

        return Math.max(robCurrent, skipCurrent);
    }

    // ==========================================
    // 2. TOP-DOWN DYNAMIC PROGRAMMING (Memoization)
    // Store intermediate max robbery values for index i in memo array.
    // Time Complexity: O(N)
    // Space Complexity: O(N) memo array + O(N) recursion stack
    // ==========================================
    public static int robMemoization(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int[] memo = new int[nums.length];
        Arrays.fill(memo, -1);
        return memoRobHelper(nums, nums.length - 1, memo);
    }

    private static int memoRobHelper(int[] nums, int i, int[] memo) {
        if (i < 0) return 0;
        if (i == 0) return nums[0];
        if (memo[i] != -1) return memo[i];

        int robCurrent = nums[i] + memoRobHelper(nums, i - 2, memo);
        int skipCurrent = memoRobHelper(nums, i - 1, memo);

        memo[i] = Math.max(robCurrent, skipCurrent);
        return memo[i];
    }

    // ==========================================
    // 3. BOTTOM-UP DYNAMIC PROGRAMMING (Tabulation)
    // Build dp array where dp[i] = max money stolen from first i+1 houses.
    // Transition: dp[i] = max(dp[i-1], nums[i] + dp[i-2])
    // Time Complexity: O(N) linear pass
    // Space Complexity: O(N) DP table
    // ==========================================
    public static int robTabulation(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        if (nums.length == 1) return nums[0];

        int n = nums.length;
        int[] dp = new int[n];
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);

        for (int i = 2; i < n; i++) {
            dp[i] = Math.max(dp[i - 1], nums[i] + dp[i - 2]);
        }

        return dp[n - 1];
    }

    // ==========================================
    // 4. OPTIMAL APPROACH (Space-Optimized DP - Two Pointers / Variables)
    // Since dp[i] only depends on dp[i-1] (prev1) and dp[i-2] (prev2), replace array with two variables.
    // Time Complexity: O(N) linear pass
    // Space Complexity: O(1) auxiliary space
    // ==========================================
    public static int robOptimal(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        if (nums.length == 1) return nums[0];

        int prev2 = nums[0];                     // Represents dp[i-2]
        int prev1 = Math.max(nums[0], nums[1]);  // Represents dp[i-1]

        for (int i = 2; i < nums.length; i++) {
            int current = Math.max(prev1, nums[i] + prev2);
            prev2 = prev1;
            prev1 = current;
        }

        return prev1;
    }

    // Alternative compact representation of O(1) space DP
    public static int robOptimalCompact(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        int rob = 0;  // Max profit if robbing current house
        int skip = 0; // Max profit if skipping current house

        for (int n : nums) {
            int newRob = skip + n;
            int newSkip = Math.max(skip, rob);
            rob = newRob;
            skip = newSkip;
        }

        return Math.max(rob, skip);
    }

    // Quick Test / Demo
    public static void main(String[] args) {
        int[][] testCases = {
            {1, 2, 3, 1},          // Expected: 4 (Rob house 1 and 3: 1 + 3 = 4)
            {2, 7, 9, 3, 1},       // Expected: 12 (Rob house 1, 3, 5: 2 + 9 + 1 = 12)
            {5},                   // Expected: 5 (Single house)
            {2, 1},                // Expected: 2 (Two houses, pick max)
            {2, 1, 1, 2},          // Expected: 4 (Rob 1st and 4th: 2 + 2 = 4)
            {10, 2, 2, 10},        // Expected: 20 (Rob 1st and 4th: 10 + 10 = 20)
            {0, 0, 0, 0}           // Expected: 0 (All zero values)
        };

        System.out.println("--- Day 9 Problem 34: House Robber ---");
        for (int[] nums : testCases) {
            System.out.println("\nArray: " + Arrays.toString(nums));
            if (nums.length <= 6) {
                System.out.println("  Naive Recursion: " + robBruteForce(nums));
            }
            System.out.println("  Memoization:     " + robMemoization(nums));
            System.out.println("  Tabulation:      " + robTabulation(nums));
            System.out.println("  Optimal O(1):    " + robOptimal(nums));
            System.out.println("  Compact O(1):    " + robOptimalCompact(nums));
        }
    }
}
