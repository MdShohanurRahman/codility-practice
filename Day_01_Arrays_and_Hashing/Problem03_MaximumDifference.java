package Day_01_Arrays_and_Hashing;

/**
 * Day 1 - Problem 3: Maximum Difference
 * Question: Find the maximum value of A[j] - A[i] where j > i.
 * (If array is non-increasing or length < 2, handles accordingly)
 */
public class Problem03_MaximumDifference {

    // ==========================================
    // 1. BRUTE FORCE APPROACH (Nested Loops)
    // Time Complexity: O(N^2)
    // Space Complexity: O(1)
    // ==========================================
    public static int maxDiffBruteForce(int[] nums) {
        if (nums == null || nums.length < 2) {
            return 0; // or Integer.MIN_VALUE depending on constraint
        }

        int maxDiff = Integer.MIN_VALUE;
        int n = nums.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                int diff = nums[j] - nums[i];
                if (diff > maxDiff) {
                    maxDiff = diff;
                }
            }
        }
        return maxDiff;
    }

    // ==========================================
    // 2. OPTIMAL APPROACH (Single Pass Tracking Minimum Element)
    // Time Complexity: O(N)
    // Space Complexity: O(1)
    // ==========================================
    public static int maxDiffOptimal(int[] nums) {
        if (nums == null || nums.length < 2) {
            return 0;
        }

        int minElement = nums[0];
        int maxDiff = nums[1] - nums[0]; // initialize with first valid pair

        for (int j = 1; j < nums.length; j++) {
            int currentDiff = nums[j] - minElement;
            if (currentDiff > maxDiff) {
                maxDiff = currentDiff;
            }
            if (nums[j] < minElement) {
                minElement = nums[j];
            }
        }

        return maxDiff;
    }

    // Demo
    public static void main(String[] args) {
        int[] nums1 = {7, 1, 5, 3, 6, 4}; // max diff is 6 - 1 = 5
        int[] nums2 = {9, 7, 4, 1};       // max diff is 4 - 7 = -3 (or max valid j > i)

        System.out.println("Test 1 (Brute Force): " + maxDiffBruteForce(nums1));
        System.out.println("Test 1 (Optimal): " + maxDiffOptimal(nums1));

        System.out.println("Test 2 (Brute Force): " + maxDiffBruteForce(nums2));
        System.out.println("Test 2 (Optimal): " + maxDiffOptimal(nums2));
    }
}
