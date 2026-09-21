package Bonus_Problems;

import java.util.Arrays;

/**
 * Bonus Problem 39: Rotate Array
 * Question: Rotate an array to the right by K positions.
 * 
 * Target: O(N) Time, O(1) Extra Space.
 */
public class Problem39_RotateArray {

    // ==========================================
    // 1. EXTRA ARRAY APPROACH
    // Copy elements to new array at index (i + k) % n.
    // Time Complexity: O(N)
    // Space Complexity: O(N) extra space
    // ==========================================
    public static void rotateExtraArray(int[] nums, int k) {
        if (nums == null || nums.length <= 1) return;

        int n = nums.length;
        k = k % n;
        if (k == 0) return;

        int[] temp = new int[n];
        for (int i = 0; i < n; i++) {
            temp[(i + k) % n] = nums[i];
        }

        for (int i = 0; i < n; i++) {
            nums[i] = temp[i];
        }
    }

    // ==========================================
    // 2. OPTIMAL APPROACH (Array Reversal Algorithm)
    // Step 1: Reverse the entire array.
    // Step 2: Reverse the first k elements.
    // Step 3: Reverse the remaining n - k elements.
    // Time Complexity: O(N) linear time (three linear passes)
    // Space Complexity: O(1) auxiliary space
    // ==========================================
    public static void rotateOptimal(int[] nums, int k) {
        if (nums == null || nums.length <= 1) return;

        int n = nums.length;
        k = k % n;
        if (k == 0) return;

        // 1. Reverse the entire array
        reverse(nums, 0, n - 1);
        // 2. Reverse the first k elements
        reverse(nums, 0, k - 1);
        // 3. Reverse the remaining n - k elements
        reverse(nums, k, n - 1);
    }

    private static void reverse(int[] nums, int start, int end) {
        while (start < end) {
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            start++;
            end--;
        }
    }

    // Quick Test / Demo
    public static void main(String[] args) {
        int[] arr1 = {1, 2, 3, 4, 5, 6, 7};
        int k1 = 3; // Expected: [5, 6, 7, 1, 2, 3, 4]

        int[] arr2 = {-1, -100, 3, 99};
        int k2 = 2; // Expected: [3, 99, -1, -100]

        int[] arr3 = {1, 2};
        int k3 = 5; // k = 5 % 2 = 1 -> Expected: [2, 1]

        System.out.println("--- Bonus Problem 39: Rotate Array ---");
        
        System.out.println("\nTest 1 (k = " + k1 + "): " + Arrays.toString(arr1));
        rotateOptimal(arr1, k1);
        System.out.println("  Rotated: " + Arrays.toString(arr1));

        System.out.println("\nTest 2 (k = " + k2 + "): " + Arrays.toString(arr2));
        rotateOptimal(arr2, k2);
        System.out.println("  Rotated: " + Arrays.toString(arr2));

        System.out.println("\nTest 3 (k = " + k3 + "): " + Arrays.toString(arr3));
        rotateOptimal(arr3, k3);
        System.out.println("  Rotated: " + Arrays.toString(arr3));
    }
}
