package Bonus_Problems;

import java.util.ArrayList;
import java.util.List;

/**
 * Bonus Problem 40: Missing Ranges
 * Question: Given a sorted unique integer array nums and a range [lower, upper], return all missing ranges.
 * 
 * Target: O(N) Time, O(1) Auxiliary Space.
 */
public class Problem40_MissingRanges {

    // Helper to format missing range as "x" or "x->y"
    private static String formatRange(long start, long end) {
        return start == end ? String.valueOf(start) : start + "->" + end;
    }

    // ==========================================
    // OPTIMAL APPROACH (Single Pass Linear Scan)
    // Compare adjacent elements and handle boundaries at lower and upper.
    // Use long to prevent integer overflow when adding/subtracting 1.
    // Time Complexity: O(N)
    // Space Complexity: O(1) auxiliary space (excluding result list)
    // ==========================================
    public static List<String> findMissingRanges(int[] nums, int lower, int upper) {
        List<String> result = new ArrayList<>();
        long prev = (long) lower - 1;

        for (int i = 0; i <= (nums == null ? 0 : nums.length); i++) {
            long curr = (i < (nums == null ? 0 : nums.length)) ? nums[i] : (long) upper + 1;

            if (curr - prev >= 2) {
                result.add(formatRange(prev + 1, curr - 1));
            }
            prev = curr;
        }

        return result;
    }

    // Alternative returning List of List<Integer> range boundaries [start, end]
    public static List<List<Integer>> findMissingRangesList(int[] nums, int lower, int upper) {
        List<List<Integer>> result = new ArrayList<>();
        long prev = (long) lower - 1;
        int n = (nums == null) ? 0 : nums.length;

        for (int i = 0; i <= n; i++) {
            long curr = (i < n) ? nums[i] : (long) upper + 1;

            if (curr - prev >= 2) {
                List<Integer> range = new ArrayList<>();
                range.add((int) (prev + 1));
                range.add((int) (curr - 1));
                result.add(range);
            }
            prev = curr;
        }

        return result;
    }

    // Quick Test / Demo
    public static void main(String[] args) {
        int[] nums1 = {0, 1, 3, 50, 75};
        int lower1 = 0, upper1 = 99;
        // Expected: ["2", "4->49", "51->74", "76->99"]

        int[] nums2 = {-1};
        int lower2 = -1, upper2 = -1;
        // Expected: []

        int[] nums3 = {};
        int lower3 = 1, upper3 = 1;
        // Expected: ["1"]

        int[] nums4 = {};
        int lower4 = -3, upper4 = -1;
        // Expected: ["-3->-1"]

        System.out.println("--- Bonus Problem 40: Missing Ranges ---");

        System.out.println("\nTest 1 (lower=" + lower1 + ", upper=" + upper1 + "):");
        System.out.println("  Ranges: " + findMissingRanges(nums1, lower1, upper1));

        System.out.println("\nTest 2 (lower=" + lower2 + ", upper=" + upper2 + "):");
        System.out.println("  Ranges: " + findMissingRanges(nums2, lower2, upper2));

        System.out.println("\nTest 3 (lower=" + lower3 + ", upper=" + upper3 + "):");
        System.out.println("  Ranges: " + findMissingRanges(nums3, lower3, upper3));

        System.out.println("\nTest 4 (lower=" + lower4 + ", upper=" + upper4 + "):");
        System.out.println("  Ranges: " + findMissingRanges(nums4, lower4, upper4));
    }
}
