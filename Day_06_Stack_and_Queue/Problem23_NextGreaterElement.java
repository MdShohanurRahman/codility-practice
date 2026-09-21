package Day_06_Stack_and_Queue;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * Day 6 - Problem 23: Next Greater Element
 * Question: For every element in an array, find the next element to its right that is greater.
 * If no such element exists, output -1 for that position.
 * 
 * Target: O(N) Time, O(N) Space.
 */
public class Problem23_NextGreaterElement {

    // ==========================================
    // 1. BRUTE FORCE APPROACH
    // For each element at index i, scan rightward (j = i + 1 to N - 1)
    // to find the first nums[j] > nums[i].
    // Time Complexity: O(N^2)
    // Space Complexity: O(1) auxiliary space (excluding output array)
    // ==========================================
    public static int[] nextGreaterElementBruteForce(int[] nums) {
        if (nums == null || nums.length == 0) return new int[0];

        int n = nums.length;
        int[] result = new int[n];

        for (int i = 0; i < n; i++) {
            result[i] = -1; // Default if no greater element exists
            for (int j = i + 1; j < n; j++) {
                if (nums[j] > nums[i]) {
                    result[i] = nums[j];
                    break;
                }
            }
        }

        return result;
    }

    // ==========================================
    // 2. OPTIMAL APPROACH (Monotonic Decreasing Stack)
    // Traverse from right to left (index N-1 down to 0).
    // Maintain a stack of potential next greater elements.
    // Pop elements from stack that are <= nums[i].
    // If stack is not empty, stack.peek() is the next greater element.
    // Push nums[i] onto stack.
    // Time Complexity: O(N) because each element is pushed and popped at most once
    // Space Complexity: O(N) for stack storage
    // ==========================================
    public static int[] nextGreaterElementOptimal(int[] nums) {
        if (nums == null || nums.length == 0) return new int[0];

        int n = nums.length;
        int[] result = new int[n];
        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = n - 1; i >= 0; i--) {
            // Pop smaller or equal elements as they cannot be the next greater element
            while (!stack.isEmpty() && stack.peek() <= nums[i]) {
                stack.pop();
            }

            // If stack is not empty, top element is the next greater element
            result[i] = stack.isEmpty() ? -1 : stack.peek();

            // Push current element onto stack
            stack.push(nums[i]);
        }

        return result;
    }

    // Quick Test / Demo
    public static void main(String[] args) {
        int[] test1 = {4, 5, 2, 25};      // Expected: [5, 25, 25, -1]
        int[] test2 = {13, 7, 6, 12};     // Expected: [-1, 12, 12, -1]
        int[] test3 = {1, 2, 3, 4};       // Expected: [2, 3, 4, -1]
        int[] test4 = {4, 3, 2, 1};       // Expected: [-1, -1, -1, -1]
        int[] test5 = {5, 5, 5, 5};       // Expected: [-1, -1, -1, -1]

        System.out.println("Test 1 " + Arrays.toString(test1));
        System.out.println("  BruteForce: " + Arrays.toString(nextGreaterElementBruteForce(test1)));
        System.out.println("  Optimal:    " + Arrays.toString(nextGreaterElementOptimal(test1)));

        System.out.println("\nTest 2 " + Arrays.toString(test2));
        System.out.println("  BruteForce: " + Arrays.toString(nextGreaterElementBruteForce(test2)));
        System.out.println("  Optimal:    " + Arrays.toString(nextGreaterElementOptimal(test2)));

        System.out.println("\nTest 3 " + Arrays.toString(test3));
        System.out.println("  Optimal:    " + Arrays.toString(nextGreaterElementOptimal(test3)));

        System.out.println("\nTest 4 " + Arrays.toString(test4));
        System.out.println("  Optimal:    " + Arrays.toString(nextGreaterElementOptimal(test4)));

        System.out.println("\nTest 5 " + Arrays.toString(test5));
        System.out.println("  Optimal:    " + Arrays.toString(nextGreaterElementOptimal(test5)));
    }
}
