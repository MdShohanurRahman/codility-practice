package Day_10_Codility_Style_Challenge_Problems;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Day 10 - Problem 38: Leader / Dominant Value (Codility Dominator / Boyer-Moore Majority Vote)
 * Question: Find whether an array contains a value occurring in strictly more than half of the positions (> N/2).
 * Return any index of the dominant value, or -1 if no dominator exists.
 * 
 * Target: O(N) Time, O(1) Auxiliary Space.
 */
public class Problem38_LeaderDominantValue {

    // ==========================================
    // 1. BRUTE FORCE APPROACH
    // Count occurrences of each element in a nested loop.
    // Time Complexity: O(N^2)
    // Space Complexity: O(1)
    // ==========================================
    public static int findLeaderBruteForce(int[] A) {
        if (A == null || A.length == 0) return -1;

        int n = A.length;
        for (int i = 0; i < n; i++) {
            int count = 0;
            for (int j = 0; j < n; j++) {
                if (A[j] == A[i]) {
                    count++;
                }
            }
            if (count > n / 2) {
                return i; // Return index of leader
            }
        }

        return -1; // No leader found
    }

    // ==========================================
    // 2. HASHMAP FREQUENCY APPROACH
    // Store frequencies of elements in a map.
    // Time Complexity: O(N)
    // Space Complexity: O(N) extra space
    // ==========================================
    public static int findLeaderHashMap(int[] A) {
        if (A == null || A.length == 0) return -1;

        int n = A.length;
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < n; i++) {
            int count = map.getOrDefault(A[i], 0) + 1;
            map.put(A[i], count);
            if (count > n / 2) {
                return i;
            }
        }

        return -1;
    }

    // ==========================================
    // 3. OPTIMAL APPROACH (Boyer-Moore Majority Vote Algorithm)
    // Pass 1: Find potential candidate using cancellation logic.
    // Pass 2: Verify candidate frequency strictly exceeds N / 2.
    // Time Complexity: O(N) - Two linear passes
    // Space Complexity: O(1) auxiliary space
    // ==========================================
    public static int findLeaderOptimal(int[] A) {
        if (A == null || A.length == 0) return -1;

        int candidate = -1;
        int count = 0;

        // Pass 1: Find candidate
        for (int num : A) {
            if (count == 0) {
                candidate = num;
                count = 1;
            } else if (num == candidate) {
                count++;
            } else {
                count--;
            }
        }

        // Pass 2: Verify candidate frequency > N / 2
        int candidateCount = 0;
        int leaderIndex = -1;

        for (int i = 0; i < A.length; i++) {
            if (A[i] == candidate) {
                candidateCount++;
                if (leaderIndex == -1) {
                    leaderIndex = i; // Save first matching index
                }
            }
        }

        if (candidateCount > A.length / 2) {
            return leaderIndex; // Valid leader found
        }

        return -1; // No value occurs > N / 2 times
    }

    // Returns actual dominant value instead of index
    public static int getDominantValue(int[] A) {
        int idx = findLeaderOptimal(A);
        return (idx != -1) ? A[idx] : -1;
    }

    // Quick Test / Demo
    public static void main(String[] args) {
        int[][] testCases = {
            {3, 4, 3, 2, 3, -1, 3, 3}, // Length 8, 3 occurs 5 times (> 4) -> Leader = 3
            {1, 2, 3, 4, 5},           // No leader -> -1
            {2, 1, 1, 3, 4},           // 1 occurs 2 times (NOT > 5/2 = 2) -> -1
            {7},                       // Single element -> Leader = 7
            {4, 4, 2, 4, 2, 4},        // 4 occurs 4 times (> 6/2 = 3) -> Leader = 4
            {1, 1, 1, 2, 2, 2}         // Equal split (3 and 3) -> No leader (-1)
        };

        System.out.println("--- Day 10 Problem 38: Leader / Dominant Value ---");
        for (int[] test : testCases) {
            System.out.println("\nArray: " + Arrays.toString(test));
            System.out.println("  Brute Force Index: " + findLeaderBruteForce(test));
            System.out.println("  HashMap Index:     " + findLeaderHashMap(test));
            int leaderIdx = findLeaderOptimal(test);
            System.out.println("  Optimal Index:     " + leaderIdx + (leaderIdx != -1 ? " (Value: " + test[leaderIdx] + ")" : " (No Dominator)"));
        }
    }
}
