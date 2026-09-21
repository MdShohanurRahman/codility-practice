package Day_08_Greedy_and_Medium_Problems;

import java.util.Arrays;

/**
 * Day 8 - Problem 32: Product of Array Except Self
 * Question: Return an array where answer[i] is equal to product of all elements of nums except nums[i],
 * WITHOUT using division, in O(N) time.
 * 
 * Target: O(N) Time, O(1) Extra Space.
 */
public class Problem32_ProductOfArrayExceptSelf {

    // ==========================================
    // 1. BRUTE FORCE APPROACH
    // For each index i, iterate over all j != i and multiply.
    // Time Complexity: O(N^2)
    // Space Complexity: O(1) auxiliary space (excluding result array)
    // ==========================================
    public static int[] productExceptSelfBruteForce(int[] nums) {
        if (nums == null || nums.length == 0) return new int[0];

        int n = nums.length;
        int[] result = new int[n];

        for (int i = 0; i < n; i++) {
            int prod = 1;
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    prod *= nums[j];
                }
            }
            result[i] = prod;
        }

        return result;
    }

    // ==========================================
    // 2. OPTIMAL APPROACH (Prefix & Suffix Product Pass)
    // Left pass builds prefix products directly into output array.
    // Right pass accumulates suffix products into output array using a single variable.
    // Time Complexity: O(N) linear pass
    // Space Complexity: O(1) extra space (result array does not count toward auxiliary space)
    // ==========================================
    public static int[] productExceptSelfOptimal(int[] nums) {
        if (nums == null || nums.length == 0) return new int[0];

        int n = nums.length;
        int[] result = new int[n];

        // 1. Left Pass: result[i] contains product of all elements to the left of index i
        result[0] = 1;
        for (int i = 1; i < n; i++) {
            result[i] = result[i - 1] * nums[i - 1];
        }

        // 2. Right Pass: Multiply result[i] by product of all elements to the right of index i
        int rightProduct = 1;
        for (int i = n - 1; i >= 0; i--) {
            result[i] *= rightProduct;
            rightProduct *= nums[i]; // Accumulate right suffix product
        }

        return result;
    }

    // Quick Test / Demo
    public static void main(String[] args) {
        int[] test1 = {1, 2, 3, 4};         // Expected: [24, 12, 8, 6]
        int[] test2 = {-1, 1, 0, -3, 3};    // Expected: [0, 0, 9, 0, 0]
        int[] test3 = {5, 2};               // Expected: [2, 5]

        System.out.println("Test 1 " + Arrays.toString(test1));
        System.out.println("  Brute Force: " + Arrays.toString(productExceptSelfBruteForce(test1)));
        System.out.println("  Optimal:     " + Arrays.toString(productExceptSelfOptimal(test1)));

        System.out.println("\nTest 2 " + Arrays.toString(test2));
        System.out.println("  Brute Force: " + Arrays.toString(productExceptSelfBruteForce(test2)));
        System.out.println("  Optimal:     " + Arrays.toString(productExceptSelfOptimal(test2)));

        System.out.println("\nTest 3 " + Arrays.toString(test3));
        System.out.println("  Optimal:     " + Arrays.toString(productExceptSelfOptimal(test3)));
    }
}
