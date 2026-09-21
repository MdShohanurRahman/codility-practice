package Day_03_Sliding_Window_and_Two_Pointers;

/**
 * Day 3 - Problem 10: Maximum Sum Subarray of Size K
 * Question: Find the maximum sum of any contiguous subarray of size K.
 */
public class Problem10_MaximumSumSubarraySizeK {

    // ==========================================
    // 1. BRUTE FORCE APPROACH
    // For every possible starting index i, sum K elements.
    // Time Complexity: O(N * K)
    // Space Complexity: O(1)
    // ==========================================
    public static long maxSumSubarrayBruteForce(int[] nums, int k) {
        if (nums == null || nums.length < k || k <= 0) {
            throw new IllegalArgumentException("Invalid input array or subarray size K.");
        }

        int n = nums.length;
        long maxSum = Long.MIN_VALUE;

        for (int i = 0; i <= n - k; i++) {
            long currentSum = 0;
            for (int j = i; j < i + k; j++) {
                currentSum += nums[j];
            }
            maxSum = Math.max(maxSum, currentSum);
        }

        return maxSum;
    }

    // ==========================================
    // 2. OPTIMAL APPROACH (Fixed-Size Sliding Window)
    // Compute initial window sum of first K elements, then slide window by adding 
    // the incoming element and subtracting the outgoing element.
    // Time Complexity: O(N) - single pass over array
    // Space Complexity: O(1) - constant extra space
    // ==========================================
    public static long maxSumSubarraySlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length < k || k <= 0) {
            throw new IllegalArgumentException("Invalid input array or subarray size K.");
        }

        int n = nums.length;
        long windowSum = 0;

        // Compute sum of the first window of size K
        for (int i = 0; i < k; i++) {
            windowSum += nums[i];
        }

        long maxSum = windowSum;

        // Slide the window from index K to N-1
        for (int i = k; i < n; i++) {
            windowSum += nums[i] - nums[i - k];
            maxSum = Math.max(maxSum, windowSum);
        }

        return maxSum;
    }

    // Quick Test / Demo
    public static void main(String[] args) {
        int[] test1 = {2, 1, 5, 1, 3, 2}; // K = 3 -> Subarray [5, 1, 3] sum = 9
        int k1 = 3;

        int[] test2 = {2, 3, 4, 1, 5};    // K = 2 -> Subarray [3, 4] sum = 7
        int k2 = 2;

        int[] test3 = {-2, -5, -3, -1, -2}; // K = 2 -> Subarray [-3, -1] sum = -4 (negative numbers)
        int k3 = 2;

        int[] test4 = {5, 2, -1, 0, 3};   // K = 5 -> Entire array sum = 9
        int k4 = 5;

        System.out.println("Test 1 (Brute Force): " + maxSumSubarrayBruteForce(test1, k1));
        System.out.println("Test 1 (Sliding Window): " + maxSumSubarraySlidingWindow(test1, k1));

        System.out.println("Test 2 (Sliding Window): " + maxSumSubarraySlidingWindow(test2, k2));
        System.out.println("Test 3 (Sliding Window, Negatives): " + maxSumSubarraySlidingWindow(test3, k3));
        System.out.println("Test 4 (Sliding Window, K = N): " + maxSumSubarraySlidingWindow(test4, k4));
    }
}
