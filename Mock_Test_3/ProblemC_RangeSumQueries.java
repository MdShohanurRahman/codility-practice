package Mock_Test_3;

import java.util.Arrays;

/**
 * Mock Test 3 - Problem C: Range Sum Queries
 * Question: Perform multiple range sum queries [L, R] on an array efficiently.
 * 
 * Target: O(N) Precomputation, O(1) Query Time.
 */
public class ProblemC_RangeSumQueries {

    public static class NumArray {
        private final long[] prefixSum;

        public NumArray(int[] nums) {
            int n = (nums == null) ? 0 : nums.length;
            prefixSum = new long[n + 1];
            for (int i = 0; i < n; i++) {
                prefixSum[i + 1] = prefixSum[i] + nums[i];
            }
        }

        public long sumRange(int left, int right) {
            if (left < 0 || right >= prefixSum.length - 1 || left > right) return 0;
            return prefixSum[right + 1] - prefixSum[left];
        }
    }

    public static void main(String[] args) {
        int[] nums = {-2, 0, 3, -5, 2, -1};
        NumArray numArray = new NumArray(nums);

        System.out.println("Mock Test 3 - Problem C");
        System.out.println("Query [0, 2]: " + numArray.sumRange(0, 2) + " (Expected: 1)");
        System.out.println("Query [2, 5]: " + numArray.sumRange(2, 5) + " (Expected: -1)");
        System.out.println("Query [0, 5]: " + numArray.sumRange(0, 5) + " (Expected: -3)");
    }
}
