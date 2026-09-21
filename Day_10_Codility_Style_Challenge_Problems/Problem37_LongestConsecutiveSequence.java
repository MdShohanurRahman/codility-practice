package Day_10_Codility_Style_Challenge_Problems;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Day 10 - Problem 37: Longest Consecutive Sequence
 * Question: Find the length of the longest sequence of consecutive integers in an unsorted array.
 * Example: [100, 4, 200, 1, 3, 2] -> 4 ([1, 2, 3, 4])
 * 
 * Target: O(N) Expected Time, O(N) Space.
 */
public class Problem37_LongestConsecutiveSequence {

    // ==========================================
    // 1. BRUTE FORCE APPROACH
    // For each element x, search if x+1, x+2... exist in array linearly.
    // Time Complexity: O(N^3)
    // Space Complexity: O(1)
    // ==========================================
    public static int longestConsecutiveBruteForce(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        int maxStreak = 0;
        for (int num : nums) {
            int currentNum = num;
            int currentStreak = 1;

            while (arrayContains(nums, currentNum + 1)) {
                currentNum += 1;
                currentStreak += 1;
            }

            maxStreak = Math.max(maxStreak, currentStreak);
        }

        return maxStreak;
    }

    private static boolean arrayContains(int[] nums, int target) {
        for (int num : nums) {
            if (num == target) return true;
        }
        return false;
    }

    // ==========================================
    // 2. SORTING APPROACH
    // Sort array, then iterate while counting consecutive streaks (skipping duplicates).
    // Time Complexity: O(N log N)
    // Space Complexity: O(1) auxiliary (or O(N) for cloned array)
    // ==========================================
    public static int longestConsecutiveSorting(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        int[] arr = nums.clone();
        Arrays.sort(arr);

        int maxStreak = 1;
        int currentStreak = 1;

        for (int i = 1; i < arr.length; i++) {
            if (arr[i] == arr[i - 1]) {
                continue; // Skip duplicates
            }
            if (arr[i] == arr[i - 1] + 1) {
                currentStreak++;
            } else {
                maxStreak = Math.max(maxStreak, currentStreak);
                currentStreak = 1;
            }
        }

        return Math.max(maxStreak, currentStreak);
    }

    // ==========================================
    // 3. OPTIMAL APPROACH (HashSet - Sequence Start Lookup)
    // Add all elements to a HashSet for O(1) lookup.
    // Only start counting a sequence if num - 1 is NOT in the set (guarantees num is sequence start).
    // Time Complexity: O(N) expected - each element is visited at most twice
    // Space Complexity: O(N) extra space for HashSet
    // ==========================================
    public static int longestConsecutiveOptimal(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        Set<Integer> numSet = new HashSet<>();
        for (int num : nums) {
            numSet.add(num);
        }

        int maxStreak = 0;

        for (int num : numSet) {
            // Key optimization: Only initiate count if num is the START of a sequence
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

    // Quick Test / Demo
    public static void main(String[] args) {
        int[][] testCases = {
            {100, 4, 200, 1, 3, 2},         // Expected: 4 ([1, 2, 3, 4])
            {0, 3, 7, 2, 5, 8, 4, 6, 0, 1}, // Expected: 9 ([0, 1, 2, 3, 4, 5, 6, 7, 8])
            {1, 2, 0, 1},                   // Expected: 3 ([0, 1, 2])
            {10},                           // Expected: 1
            {},                             // Expected: 0
            {9, 1, 4, 7, 3, -1, 0, 2, 8, 5, 6} // Expected: 11 ([-1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9])
        };

        System.out.println("--- Day 10 Problem 37: Longest Consecutive Sequence ---");
        for (int[] test : testCases) {
            System.out.println("\nArray: " + Arrays.toString(test));
            if (test.length <= 10) {
                System.out.println("  Brute Force: " + longestConsecutiveBruteForce(test));
            }
            System.out.println("  Sorting:     " + longestConsecutiveSorting(test));
            System.out.println("  Optimal Set: " + longestConsecutiveOptimal(test));
        }
    }
}
