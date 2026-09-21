package Mock_Test_1;

import java.util.Arrays;

/**
 * Mock Test 1 - Problem A: First Missing Positive Integer
 * Question: Find the smallest positive integer (> 0) that does not occur in an unsorted integer array.
 * 
 * Target: O(N) Time, O(1) Extra Space.
 */
public class ProblemA_FirstMissingPositive {

    // Optimal Approach: Cyclic Sort / In-Place Swapping
    public static int firstMissingPositive(int[] nums) {
        if (nums == null || nums.length == 0) return 1;

        int n = nums.length;
        for (int i = 0; i < n; i++) {
            while (nums[i] > 0 && nums[i] <= n && nums[nums[i] - 1] != nums[i]) {
                int targetIdx = nums[i] - 1;
                int temp = nums[i];
                nums[i] = nums[targetIdx];
                nums[targetIdx] = temp;
            }
        }

        for (int i = 0; i < n; i++) {
            if (nums[i] != i + 1) {
                return i + 1;
            }
        }

        return n + 1;
    }

    public static void main(String[] args) {
        int[] test1 = {3, 4, -1, 1}; // Expected: 2
        int[] test2 = {1, 2, 0};     // Expected: 3
        int[] test3 = {7, 8, 9, 11}; // Expected: 1

        System.out.println("Mock Test 1 - Problem A");
        System.out.println("Test 1: " + firstMissingPositive(test1.clone()) + " (Expected: 2)");
        System.out.println("Test 2: " + firstMissingPositive(test2.clone()) + " (Expected: 3)");
        System.out.println("Test 3: " + firstMissingPositive(test3.clone()) + " (Expected: 1)");
    }
}
