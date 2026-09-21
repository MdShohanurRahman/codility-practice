package Day_03_Sliding_Window_and_Two_Pointers;

import java.util.Arrays;

/**
 * Day 3 - Problem 12: Move Zeroes
 * Question: Move all zeroes to the end of an array while preserving the relative order of non-zero elements in-place.
 */
public class Problem12_MoveZeroes {

    // ==========================================
    // 1. NAIVE APPROACH (Auxiliary Array)
    // Copy non-zero elements into a new array and write back.
    // Time Complexity: O(N)
    // Space Complexity: O(N) auxiliary space
    // ==========================================
    public static void moveZeroesExtraSpace(int[] nums) {
        if (nums == null || nums.length <= 1) return;
        int n = nums.length;
        int[] temp = new int[n];
        int writeIndex = 0;

        for (int num : nums) {
            if (num != 0) {
                temp[writeIndex++] = num;
            }
        }

        // Copy back to original array
        System.arraycopy(temp, 0, nums, 0, n);
    }

    // ==========================================
    // 2. OPTIMAL APPROACH 1 (Two-Pass In-Place Overwriting)
    // First pass: shift non-zero elements to front.
    // Second pass: fill remaining array elements with zero.
    // Time Complexity: O(N) - two passes
    // Space Complexity: O(1) - strictly in-place
    // ==========================================
    public static void moveZeroesTwoPass(int[] nums) {
        if (nums == null || nums.length <= 1) return;
        int n = nums.length;
        int writeIndex = 0;

        // Pass 1: Write non-zero elements to front
        for (int i = 0; i < n; i++) {
            if (nums[i] != 0) {
                nums[writeIndex++] = nums[i];
            }
        }

        // Pass 2: Fill remaining positions with zeroes
        while (writeIndex < n) {
            nums[writeIndex++] = 0;
        }
    }

    // ==========================================
    // 3. OPTIMAL APPROACH 2 (Single-Pass Partitioning Swap)
    // Maintain lastNonZeroFoundAt index and swap non-zero elements forward.
    // Minimizes write operations when array contains many zeroes!
    // Time Complexity: O(N) - single pass
    // Space Complexity: O(1) - strictly in-place
    // ==========================================
    public static void moveZeroesOptimalSwap(int[] nums) {
        if (nums == null || nums.length <= 1) return;
        int n = nums.length;
        int lastNonZeroFoundAt = 0;

        for (int cur = 0; cur < n; cur++) {
            if (nums[cur] != 0) {
                // Swap non-zero element with the boundary index
                if (cur != lastNonZeroFoundAt) {
                    int temp = nums[lastNonZeroFoundAt];
                    nums[lastNonZeroFoundAt] = nums[cur];
                    nums[cur] = temp;
                }
                lastNonZeroFoundAt++;
            }
        }
    }

    // Quick Test / Demo
    public static void main(String[] args) {
        int[] test1 = {0, 1, 0, 3, 12}; // Expected: [1, 3, 12, 0, 0]
        int[] test2 = {0};               // Expected: [0]
        int[] test3 = {1, 2, 3, 4};      // Expected: [1, 2, 3, 4] (no zeroes)
        int[] test4 = {0, 0, 0, 0};      // Expected: [0, 0, 0, 0] (all zeroes)
        int[] test5 = {4, 2, 4, 0, 0, 3, 0, 5, 1, 0}; // Expected: [4, 2, 4, 3, 5, 1, 0, 0, 0, 0]

        System.out.println("Test 1 Original: " + Arrays.toString(test1));
        
        int[] copy1 = test1.clone();
        moveZeroesExtraSpace(copy1);
        System.out.println("Test 1 (Extra Space): " + Arrays.toString(copy1));

        int[] copy2 = test1.clone();
        moveZeroesTwoPass(copy2);
        System.out.println("Test 1 (Two Pass):    " + Arrays.toString(copy2));

        int[] copy3 = test1.clone();
        moveZeroesOptimalSwap(copy3);
        System.out.println("Test 1 (Optimal Swap): " + Arrays.toString(copy3));

        int[] copy5 = test5.clone();
        moveZeroesOptimalSwap(copy5);
        System.out.println("\nTest 5 Original: " + Arrays.toString(test5));
        System.out.println("Test 5 Result:   " + Arrays.toString(copy5));
    }
}
