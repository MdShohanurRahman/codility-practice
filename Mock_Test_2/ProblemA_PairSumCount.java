package Mock_Test_2;

import java.util.HashMap;
import java.util.Map;

/**
 * Mock Test 2 - Problem A: Pair Sum Count
 * Question: Count the number of pairs (i, j) where i < j and A[i] + A[j] == target.
 * 
 * Target: O(N) Time, O(N) Space.
 */
public class ProblemA_PairSumCount {

    // Optimal HashMap Approach
    public static int countPairSum(int[] nums, int target) {
        if (nums == null || nums.length < 2) return 0;

        Map<Integer, Integer> freqMap = new HashMap<>();
        int pairCount = 0;

        for (int num : nums) {
            int complement = target - num;
            if (freqMap.containsKey(complement)) {
                pairCount += freqMap.get(complement);
            }
            freqMap.put(num, freqMap.getOrDefault(num, 0) + 1);
        }

        return pairCount;
    }

    public static void main(String[] args) {
        int[] nums1 = {1, 5, 7, -1, 5}; int target1 = 6; // Pairs: (1,5), (7,-1), (1,5) -> 3
        int[] nums2 = {1, 1, 1, 1};    int target2 = 2; // Pairs: C(4,2) = 6

        System.out.println("Mock Test 2 - Problem A");
        System.out.println("Test 1: " + countPairSum(nums1, target1) + " (Expected: 3)");
        System.out.println("Test 2: " + countPairSum(nums2, target2) + " (Expected: 6)");
    }
}
