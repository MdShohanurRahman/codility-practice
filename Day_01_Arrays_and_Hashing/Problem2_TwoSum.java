package Day_01_Arrays_and_Hashing;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Day 1 - Problem 2: Two Sum
 * Question: Given an integer array and a target, determine whether two different elements sum to the target.
 * (Returns indices or boolean indicator)
 */
public class Problem2_TwoSum {

    // ==========================================
    // 1. BRUTE FORCE APPROACH (Nested Loops)
    // Time Complexity: O(N^2)
    // Space Complexity: O(1)
    // ==========================================
    public static boolean hasTwoSumBruteForce(int[] nums, int target) {
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (nums[i] + nums[j] == target) {
                    return true;
                }
            }
        }
        return false;
    }

    // ==========================================
    // 2. OPTIMAL APPROACH (HashMap / HashSet - Single Pass)
    // Time Complexity: O(N)
    // Space Complexity: O(N)
    // ==========================================
    public static boolean hasTwoSumOptimal(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                return true;
            }
            map.put(nums[i], i);
        }
        return false;
    }

    // Returning Indices version (Common Codility/LeetCode interview variant)
    public static int[] twoSumIndices(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                return new int[] { map.get(complement), i };
            }
            map.put(nums[i], i);
        }
        return new int[] {}; // empty if no pair exists
    }

    // Demo
    public static void main(String[] args) {
        int[] nums = {2, 7, 11, 15};
        int target = 9;

        System.out.println("Brute Force Result: " + hasTwoSumBruteForce(nums, target));
        System.out.println("Optimal Result: " + hasTwoSumOptimal(nums, target));
        System.out.println("Indices: " + Arrays.toString(twoSumIndices(nums, target)));
    }
}
