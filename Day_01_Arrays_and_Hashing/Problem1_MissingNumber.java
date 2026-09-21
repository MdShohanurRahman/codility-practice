package Day_01_Arrays_and_Hashing;

import java.util.Arrays;

/**
 * Day 1 - Problem 1: Missing Number
 * Question: Given an array containing N distinct numbers from 0 to N, find the missing number.
 */
public class Problem1_MissingNumber {

    // ==========================================
    // 1. BRUTE FORCE APPROACH (Sorting)
    // Time Complexity: O(N log N)
    // Space Complexity: O(1) or O(log N) due to sorting space
    // ==========================================
    public static int missingNumberBruteForce(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            if (nums[i] != i) {
                return i;
            }
        }
        return n; // If 0 to n-1 are present, missing number is n
    }

    // ==========================================
    // 2. OPTIMAL APPROACH 1 (Math Formula - Sum of N numbers)
    // Time Complexity: O(N)
    // Space Complexity: O(1)
    // ==========================================
    public static int missingNumberOptimalSum(int[] nums) {
        int n = nums.length;
        // Formula for sum of first N numbers: N * (N + 1) / 2
        long expectedSum = (long) n * (n + 1) / 2;
        long actualSum = 0;
        for (int num : nums) {
            actualSum += num;
        }
        return (int) (expectedSum - actualSum);
    }

    // ==========================================
    // 3. OPTIMAL APPROACH 2 (XOR Bit Manipulation)
    // Time Complexity: O(N)
    // Space Complexity: O(1)
    // Note: Avoids any integer overflow risk entirely.
    // ==========================================
    public static int missingNumberOptimalXOR(int[] nums) {
        int n = nums.length;
        int xorResult = n; // Start with N
        for (int i = 0; i < n; i++) {
            xorResult ^= i ^ nums[i];
        }
        return xorResult;
    }

    // Quick Test / Demo
    public static void main(String[] args) {
        int[] test1 = {3, 0, 1}; // N = 3, missing is 2
        int[] test2 = {0, 1};    // N = 2, missing is 2
        int[] test3 = {9,6,4,2,3,5,7,0,1}; // N = 9, missing is 8

        System.out.println("Test 1 (Brute Force): " + missingNumberBruteForce(test1.clone()));
        System.out.println("Test 1 (Optimal Sum): " + missingNumberOptimalSum(test1));
        System.out.println("Test 1 (Optimal XOR): " + missingNumberOptimalXOR(test1));

        System.out.println("Test 2 (Optimal XOR): " + missingNumberOptimalXOR(test2));
        System.out.println("Test 3 (Optimal XOR): " + missingNumberOptimalXOR(test3));
    }
}
