package Mock_Test_3;

import java.util.HashSet;
import java.util.Set;

/**
 * Mock Test 3 - Problem D: Longest Consecutive Sequence
 * Question: Find the length of the longest consecutive sequence in an unsorted array.
 * 
 * Target: O(N) Expected Time, O(N) Space.
 */
public class ProblemD_LongestConsecutiveSequence {

    // Optimal HashSet Approach
    public static int longestConsecutive(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        Set<Integer> numSet = new HashSet<>();
        for (int num : nums) {
            numSet.add(num);
        }

        int maxStreak = 0;

        for (int num : numSet) {
            // Sequence start filtering optimization
            if (!numSet.contains(num - 1)) {
                int currentNum = num;
                int currentStreak = 1;

                while (numSet.contains(currentNum + 1)) {
                    currentNum += 1;
                    currentStreak += 1;
                }

                maxStreak = Math.max(maxStreak, currentStreak);
            }
        }

        return maxStreak;
    }

    public static void main(String[] args) {
        int[] nums1 = {100, 4, 200, 1, 3, 2};         // Expected: 4 ([1, 2, 3, 4])
        int[] nums2 = {0, 3, 7, 2, 5, 8, 4, 6, 0, 1}; // Expected: 9 ([0..8])

        System.out.println("Mock Test 3 - Problem D");
        System.out.println("Test 1: " + longestConsecutive(nums1) + " (Expected: 4)");
        System.out.println("Test 2: " + longestConsecutive(nums2) + " (Expected: 9)");
    }
}
