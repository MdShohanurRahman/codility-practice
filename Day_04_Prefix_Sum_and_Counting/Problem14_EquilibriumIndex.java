package Day_04_Prefix_Sum_and_Counting;

import java.util.Arrays;

/**
 * Day 4 - Problem 14: Equilibrium Index
 * Question: Find an index where the sum of elements before it equals the sum after it.
 */
public class Problem14_EquilibriumIndex {

    // ==========================================
    // 1. BRUTE FORCE APPROACH
    // For every index i, calculate sum of elements to the left and right.
    // Time Complexity: O(N^2)
    // Space Complexity: O(1)
    // ==========================================
    public static int equilibriumIndexBruteForce(int[] nums) {
        if (nums == null || nums.length == 0) return -1;
        int n = nums.length;

        for (int i = 0; i < n; i++) {
            long leftSum = 0;
            for (int j = 0; j < i; j++) {
                leftSum += nums[j];
            }

            long rightSum = 0;
            for (int j = i + 1; j < n; j++) {
                rightSum += nums[j];
            }

            if (leftSum == rightSum) {
                return i;
            }
        }
        return -1;
    }

    // ==========================================
    // 2. OPTIMAL APPROACH (Total Sum + Single Pass Running Left Sum)
    // Total sum = Left Sum + A[i] + Right Sum => Right Sum = Total Sum - Left Sum - A[i].
    // Time Complexity: O(N) - two passes (one for total sum, one for equilibrium check)
    // Space Complexity: O(1) - constant extra space
    // ==========================================
    public static int equilibriumIndexOptimal(int[] nums) {
        if (nums == null || nums.length == 0) return -1;
        int n = nums.length;

        // Calculate total sum of the array
        long totalSum = 0;
        for (int num : nums) {
            totalSum += num;
        }

        long leftSum = 0;
        for (int i = 0; i < n; i++) {
            long rightSum = totalSum - leftSum - nums[i];

            if (leftSum == rightSum) {
                return i; // Found equilibrium index!
            }

            leftSum += nums[i];
        }

        return -1; // No equilibrium index found
    }

    // Quick Test / Demo
    public static void main(String[] args) {
        int[] test1 = {-7, 1, 5, 2, -4, 3, 0}; // Equilibrium index at 3 (-7+1+5 = -1, -4+3+0 = -1)
        int[] test2 = {1, 2, 3};                // No equilibrium index -> -1
        int[] test3 = {0, 1, -1};               // Equilibrium index at 0 (left = 0, right = 1 + (-1) = 0)
        int[] test4 = {1, 2, 0, 3};             // Equilibrium index at 1 (left = 1, right = 0 + 3 = 3 -> wait: left=1, right=3 not equal; index 2: left=3, right=3 -> index 2!)

        System.out.println("Test 1 " + Arrays.toString(test1));
        System.out.println("  Brute Force: " + equilibriumIndexBruteForce(test1));
        System.out.println("  Optimal:     " + equilibriumIndexOptimal(test1));

        System.out.println("\nTest 2 " + Arrays.toString(test2) + " -> Optimal: " + equilibriumIndexOptimal(test2));
        System.out.println("Test 3 " + Arrays.toString(test3) + " -> Optimal: " + equilibriumIndexOptimal(test3));
        System.out.println("Test 4 " + Arrays.toString(test4) + " -> Optimal: " + equilibriumIndexOptimal(test4));
    }
}
