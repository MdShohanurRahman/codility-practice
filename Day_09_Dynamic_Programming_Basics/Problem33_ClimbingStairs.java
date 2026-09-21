package Day_09_Dynamic_Programming_Basics;

import java.util.Arrays;

/**
 * Day 9 - Problem 33: Climbing Stairs
 * Question: You can climb either 1 or 2 steps at a time. Find the number of distinct ways to reach step N.
 * 
 * Target: O(N) Time, O(1) Extra Space.
 */
public class Problem33_ClimbingStairs {

    // ==========================================
    // 1. BRUTE FORCE APPROACH (Naive Recursion)
    // At each step i, try climbing 1 step or 2 steps: ways(n) = ways(n-1) + ways(n-2).
    // Time Complexity: O(2^N) - Exponential due to redundant calculations.
    // Space Complexity: O(N) - Recursion stack depth.
    // ==========================================
    public static int climbStairsRecursion(int n) {
        if (n <= 0) return 0;
        if (n == 1) return 1;
        if (n == 2) return 2;
        return climbStairsRecursion(n - 1) + climbStairsRecursion(n - 2);
    }

    // ==========================================
    // 2. TOP-DOWN DYNAMIC PROGRAMMING (Memoization)
    // Store results of subproblems in an array/memo table to avoid recalculation.
    // Time Complexity: O(N)
    // Space Complexity: O(N) memo array + O(N) recursion stack
    // ==========================================
    public static int climbStairsMemoization(int n) {
        if (n <= 0) return 0;
        int[] memo = new int[n + 1];
        Arrays.fill(memo, -1);
        return memoHelper(n, memo);
    }

    private static int memoHelper(int n, int[] memo) {
        if (n == 1) return 1;
        if (n == 2) return 2;
        if (memo[n] != -1) return memo[n];

        memo[n] = memoHelper(n - 1, memo) + memoHelper(n - 2, memo);
        return memo[n];
    }

    // ==========================================
    // 3. BOTTOM-UP DYNAMIC PROGRAMMING (Tabulation)
    // Iteratively build the solution from base cases up to n using a DP table.
    // Time Complexity: O(N)
    // Space Complexity: O(N) DP table
    // ==========================================
    public static int climbStairsTabulation(int n) {
        if (n <= 0) return 0;
        if (n == 1) return 1;
        if (n == 2) return 2;

        int[] dp = new int[n + 1];
        dp[1] = 1;
        dp[2] = 2;

        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }

        return dp[n];
    }

    // ==========================================
    // 4. OPTIMAL APPROACH (Space-Optimized DP - Fibonacci Pattern)
    // Notice dp[i] only depends on dp[i-1] and dp[i-2]. We only need two variables.
    // Time Complexity: O(N) linear pass
    // Space Complexity: O(1) auxiliary space
    // ==========================================
    public static int climbStairsOptimal(int n) {
        if (n <= 0) return 0;
        if (n == 1) return 1;
        if (n == 2) return 2;

        int first = 1;  // Ways to reach step 1
        int second = 2; // Ways to reach step 2

        for (int i = 3; i <= n; i++) {
            int third = first + second;
            first = second;
            second = third;
        }

        return second;
    }

    // Quick Test / Demo
    public static void main(String[] args) {
        int[] testCases = {1, 2, 3, 4, 5, 6, 10, 45};

        System.out.println("--- Day 9 Problem 33: Climbing Stairs ---");
        for (int n : testCases) {
            System.out.println("\nStep N = " + n);
            if (n <= 10) {
                System.out.println("  Naive Recursion: " + climbStairsRecursion(n));
            }
            System.out.println("  Memoization:     " + climbStairsMemoization(n));
            System.out.println("  Tabulation:      " + climbStairsTabulation(n));
            System.out.println("  Optimal O(1):    " + climbStairsOptimal(n));
        }
    }
}
