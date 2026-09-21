package Day_03_Sliding_Window_and_Two_Pointers;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Day 3 - Problem 11: Pair With Given Difference
 * Question: Determine whether the array contains two distinct elements whose absolute difference equals K.
 */
public class Problem11_PairWithGivenDifference {

    // ==========================================
    // 1. BRUTE FORCE APPROACH
    // Check all pairs (i, j) where i != j.
    // Time Complexity: O(N^2)
    // Space Complexity: O(1)
    // ==========================================
    public static boolean hasPairWithDiffBruteForce(int[] nums, int k) {
        if (nums == null || nums.length < 2) return false;
        k = Math.abs(k); // Difference is non-negative
        int n = nums.length;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (Math.abs(nums[i] - nums[j]) == k) {
                    return true;
                }
            }
        }
        return false;
    }

    // ==========================================
    // 2. ALTERNATIVE OPTIMAL APPROACH (Sorting + Two Pointers)
    // Sort array, then use two pointers left=0, right=1 moving in same direction.
    // Time Complexity: O(N log N) due to sorting, O(N) two-pointer scan
    // Space Complexity: O(1) extra space (or O(log N) for sort stack)
    // ==========================================
    public static boolean hasPairWithDiffTwoPointers(int[] nums, int k) {
        if (nums == null || nums.length < 2) return false;
        k = Math.abs(k);

        int[] sorted = nums.clone();
        Arrays.sort(sorted);
        int n = sorted.length;

        int left = 0;
        int right = 1;

        while (left < n && right < n) {
            if (left != right) {
                int currentDiff = sorted[right] - sorted[left];
                if (currentDiff == k) {
                    return true;
                } else if (currentDiff < k) {
                    right++;
                } else {
                    left++;
                }
            } else {
                right++; // Ensure two pointers are at distinct indices
            }
        }

        return false;
    }

    // ==========================================
    // 3. OPTIMAL APPROACH (HashSet)
    // For each element x, check if (x - k) or (x + k) exists in set.
    // Time Complexity: O(N) expected time
    // Space Complexity: O(N) for HashSet
    // ==========================================
    public static boolean hasPairWithDiffHashSet(int[] nums, int k) {
        if (nums == null || nums.length < 2) return false;
        k = Math.abs(k);

        Set<Integer> seen = new HashSet<>();

        for (int num : nums) {
            // Check if complement (num - k) or (num + k) has already been seen
            if (seen.contains(num - k) || seen.contains(num + k)) {
                return true;
            }
            seen.add(num);
        }

        return false;
    }

    // Quick Test / Demo
    public static void main(String[] args) {
        int[] test1 = {5, 20, 3, 2, 50, 80}; // K = 78 (80 - 2 = 78) -> true
        int k1 = 78;

        int[] test2 = {90, 70, 20, 80, 50};  // K = 45 -> false
        int k2 = 45;

        int[] test3 = {-10, 20, 30, -40};    // K = 50 (30 - (-20) or 20 - (-30) -> 20 - (-30)? wait: 30 - (-20) doesn't exist, but 10 - (-40) = 50? wait: no 10, 20 - (-30)? wait: 20 - (-40) = 60, -10 - (-40) = 30, 30 - (-10) = 40. Wait! 30 - (-20)? No. Wait: |-10 - 40|? No.)
        // Let's test with test3 = {-10, 20, 30, -40}, k = 70 (30 - (-40) = 70) -> true
        int k3 = 70;

        int[] test4 = {1, 2, 3, 4, 5};       // K = 0 -> false (no duplicate)
        int k4 = 0;

        int[] test5 = {1, 2, 3, 2, 5};       // K = 0 -> true (duplicate '2')
        int k5 = 0;

        System.out.println("Test 1 (Brute Force): " + hasPairWithDiffBruteForce(test1, k1));
        System.out.println("Test 1 (Two Pointers): " + hasPairWithDiffTwoPointers(test1, k1));
        System.out.println("Test 1 (HashSet): " + hasPairWithDiffHashSet(test1, k1));

        System.out.println("Test 2 (HashSet): " + hasPairWithDiffHashSet(test2, k2));
        System.out.println("Test 3 (HashSet, Negatives): " + hasPairWithDiffHashSet(test3, k3));
        System.out.println("Test 4 (K=0, No Duplicates): " + hasPairWithDiffHashSet(test4, k4));
        System.out.println("Test 5 (K=0, With Duplicates): " + hasPairWithDiffHashSet(test5, k5));
    }
}
