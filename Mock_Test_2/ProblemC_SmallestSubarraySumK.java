package Mock_Test_2;

/**
 * Mock Test 2 - Problem C: Smallest Subarray Sum at Least K
 * Question: Find minimum length of a contiguous subarray whose sum is >= K.
 * 
 * Target: O(N) Time, O(1) Space.
 */
public class ProblemC_SmallestSubarraySumK {

    // Optimal Sliding Window Approach
    public static int minSubArrayLen(int target, int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        int left = 0;
        int sum = 0;
        int minLen = Integer.MAX_VALUE;

        for (int right = 0; right < nums.length; right++) {
            sum += nums[right];

            while (sum >= target) {
                minLen = Math.min(minLen, right - left + 1);
                sum -= nums[left];
                left++;
            }
        }

        return minLen == Integer.MAX_VALUE ? 0 : minLen;
    }

    public static void main(String[] args) {
        int[] nums1 = {2, 3, 1, 2, 4, 3}; int target1 = 7; // Expected: 2 ([4,3])
        int[] nums2 = {1, 4, 4};          int target2 = 4; // Expected: 1 ([4])

        System.out.println("Mock Test 2 - Problem C");
        System.out.println("Test 1: " + minSubArrayLen(target1, nums1) + " (Expected: 2)");
        System.out.println("Test 2: " + minSubArrayLen(target2, nums2) + " (Expected: 1)");
    }
}
