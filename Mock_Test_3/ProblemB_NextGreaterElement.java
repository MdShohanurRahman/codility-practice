package Mock_Test_3;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * Mock Test 3 - Problem B: Next Greater Element
 * Question: For every element, find the nearest element to its right that is strictly greater.
 * 
 * Target: O(N) Time, O(N) Space.
 */
public class ProblemB_NextGreaterElement {

    // Optimal Monotonic Stack Approach
    public static int[] nextGreaterElement(int[] nums) {
        if (nums == null || nums.length == 0) return new int[0];

        int n = nums.length;
        int[] result = new int[n];
        Arrays.fill(result, -1);
        Deque<Integer> stack = new ArrayDeque<>(); // Stores indices

        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && nums[i] > nums[stack.peek()]) {
                int poppedIndex = stack.pop();
                result[poppedIndex] = nums[i];
            }
            stack.push(i);
        }

        return result;
    }

    public static void main(String[] args) {
        int[] nums1 = {4, 5, 2, 25};   // Expected: [5, 25, 25, -1]
        int[] nums2 = {13, 7, 6, 12};  // Expected: [-1, 12, 12, -1]

        System.out.println("Mock Test 3 - Problem B");
        System.out.println("Test 1: " + Arrays.toString(nextGreaterElement(nums1)) + " (Expected: [5, 25, 25, -1])");
        System.out.println("Test 2: " + Arrays.toString(nextGreaterElement(nums2)) + " (Expected: [-1, 12, 12, -1])");
    }
}
