package Day_04_Prefix_Sum_and_Counting;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Day 4 - Problem 16: Distinct Values (Codility Distinct)
 * Question: Count the number of distinct values in an integer array.
 */
public class Problem16_DistinctValues {

    // ==========================================
    // 1. BRUTE FORCE APPROACH
    // Check if each element appeared earlier in the array.
    // Time Complexity: O(N^2)
    // Space Complexity: O(1)
    // ==========================================
    public static int countDistinctBruteForce(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int n = nums.length;
        int count = 0;

        for (int i = 0; i < n; i++) {
            boolean isFirstOccurrence = true;
            for (int j = 0; j < i; j++) {
                if (nums[i] == nums[j]) {
                    isFirstOccurrence = false;
                    break;
                }
            }
            if (isFirstOccurrence) {
                count++;
            }
        }
        return count;
    }

    // ==========================================
    // 2. ALTERNATIVE OPTIMAL APPROACH (Sorting)
    // Sort array, then count adjacent unique values.
    // Time Complexity: O(N log N)
    // Space Complexity: O(1) auxiliary (or O(N) if cloning array)
    // ==========================================
    public static int countDistinctSorting(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int[] sorted = nums.clone();
        Arrays.sort(sorted);

        int count = 1; // At least one distinct element if array is non-empty
        for (int i = 1; i < sorted.length; i++) {
            if (sorted[i] != sorted[i - 1]) {
                count++;
            }
        }
        return count;
    }

    // ==========================================
    // 3. OPTIMAL APPROACH (HashSet)
    // Add all elements to a HashSet, return size of set.
    // Time Complexity: O(N) expected time
    // Space Complexity: O(N) for HashSet
    // ==========================================
    public static int countDistinctHashSet(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }
        return set.size();
    }

    // Quick Test / Demo
    public static void main(String[] args) {
        int[] test1 = {2, 1, 1, 2, 3, 1}; // Distinct: 1, 2, 3 -> 3
        int[] test2 = {-5, -5, -5, -5};    // Distinct: -5 -> 1
        int[] test3 = {};                  // Empty array -> 0
        int[] test4 = {10, -10, 20, -20, 10}; // Distinct: -20, -10, 10, 20 -> 4

        System.out.println("Test 1 " + Arrays.toString(test1));
        System.out.println("  Brute Force: " + countDistinctBruteForce(test1));
        System.out.println("  Sorting:     " + countDistinctSorting(test1));
        System.out.println("  HashSet:     " + countDistinctHashSet(test1));

        System.out.println("\nTest 2 " + Arrays.toString(test2) + " -> HashSet: " + countDistinctHashSet(test2));
        System.out.println("Test 3 " + Arrays.toString(test3) + " -> HashSet: " + countDistinctHashSet(test3));
        System.out.println("Test 4 " + Arrays.toString(test4) + " -> HashSet: " + countDistinctHashSet(test4));
    }
}
