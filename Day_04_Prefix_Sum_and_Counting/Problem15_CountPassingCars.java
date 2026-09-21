package Day_04_Prefix_Sum_and_Counting;

import java.util.Arrays;

/**
 * Day 4 - Problem 15: Count Passing Cars (Codility PassingCars)
 * Question: Given an array containing 0 (east) and 1 (west), count pairs (P, Q) where P < Q, A[P] = 0, and A[Q] = 1.
 * Return -1 if the number of passing cars exceeds 1,000,000,000.
 */
public class Problem15_CountPassingCars {

    private static final long MAX_PASSING_CARS = 1_000_000_000L;

    // ==========================================
    // 1. BRUTE FORCE APPROACH
    // For every 0 at index P, count all 1s at index Q > P.
    // Time Complexity: O(N^2)
    // Space Complexity: O(1)
    // ==========================================
    public static int countPassingCarsBruteForce(int[] nums) {
        if (nums == null || nums.length < 2) return 0;
        int n = nums.length;
        long totalPassing = 0;

        for (int p = 0; p < n; p++) {
            if (nums[p] == 0) {
                for (int q = p + 1; q < n; q++) {
                    if (nums[q] == 1) {
                        totalPassing++;
                        if (totalPassing > MAX_PASSING_CARS) {
                            return -1;
                        }
                    }
                }
            }
        }

        return (int) totalPassing;
    }

    // ==========================================
    // 2. OPTIMAL APPROACH (Prefix East-Bound Car Counter)
    // Iterate from left to right. Maintain eastCars count.
    // Whenever a west-bound car (1) is seen, it will pass all previous east-bound cars (0)!
    // Time Complexity: O(N) - single pass
    // Space Complexity: O(1) - constant extra space
    // ==========================================
    public static int countPassingCarsOptimal(int[] nums) {
        if (nums == null || nums.length < 2) return 0;

        int eastCars = 0;
        long totalPassing = 0;

        for (int num : nums) {
            if (num == 0) {
                eastCars++;
            } else if (num == 1) {
                totalPassing += eastCars;
                if (totalPassing > MAX_PASSING_CARS) {
                    return -1;
                }
            }
        }

        return (int) totalPassing;
    }

    // Quick Test / Demo
    public static void main(String[] args) {
        int[] test1 = {0, 1, 0, 1, 1}; // Expected pairs: (0,1), (0,3), (0,4), (2,3), (2,4) -> 5
        int[] test2 = {1, 1, 1};       // No east-bound cars -> 0
        int[] test3 = {0, 0, 0};       // No west-bound cars -> 0
        int[] test4 = {1, 0};          // East-bound car is after west-bound -> 0
        int[] test5 = {0, 1};          // Single passing pair -> 1

        System.out.println("Test 1 " + Arrays.toString(test1));
        System.out.println("  Brute Force: " + countPassingCarsBruteForce(test1));
        System.out.println("  Optimal:     " + countPassingCarsOptimal(test1));

        System.out.println("\nTest 2 " + Arrays.toString(test2) + " -> Optimal: " + countPassingCarsOptimal(test2));
        System.out.println("Test 3 " + Arrays.toString(test3) + " -> Optimal: " + countPassingCarsOptimal(test3));
        System.out.println("Test 4 " + Arrays.toString(test4) + " -> Optimal: " + countPassingCarsOptimal(test4));
        System.out.println("Test 5 " + Arrays.toString(test5) + " -> Optimal: " + countPassingCarsOptimal(test5));
    }
}
