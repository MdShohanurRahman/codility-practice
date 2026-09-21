package Mock_Test_3;

/**
 * Mock Test 3 - Problem A: Maximum Difference (A[j] - A[i] where j > i)
 * Question: Find maximum difference between two elements such that larger element appears after smaller element.
 * 
 * Target: O(N) Time, O(1) Space.
 */
public class ProblemA_MaximumDifference {

    // Optimal Single Pass Running Minimum Approach
    public static int maxDiff(int[] nums) {
        if (nums == null || nums.length <= 1) return -1;

        int minVal = nums[0];
        int maxDiff = -1;

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > minVal) {
                maxDiff = Math.max(maxDiff, nums[i] - minVal);
            } else {
                minVal = nums[i];
            }
        }

        return maxDiff;
    }

    public static void main(String[] args) {
        int[] nums1 = {2, 3, 10, 6, 4, 8, 1}; // Expected: 8 (10 - 2)
        int[] nums2 = {7, 9, 5, 6, 3, 2};     // Expected: 2 (9 - 7)

        System.out.println("Mock Test 3 - Problem A");
        System.out.println("Test 1: " + maxDiff(nums1) + " (Expected: 8)");
        System.out.println("Test 2: " + maxDiff(nums2) + " (Expected: 2)");
    }
}
