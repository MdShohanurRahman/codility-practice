package Bonus_Problems;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Bonus Problem 44: Meeting Rooms
 * Question: Determine whether a person can attend all meetings without any overlapping intervals.
 * 
 * Target: O(N log N) Time, O(1) or O(N) Space.
 */
public class Problem44_MeetingRooms {

    public static class Interval {
        public int start;
        public int end;

        public Interval(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public String toString() {
            return "[" + start + ", " + end + "]";
        }
    }

    // ==========================================
    // 1. BRUTE FORCE APPROACH
    // Check all pairs of intervals (i, j) to see if any two overlap.
    // Time Complexity: O(N^2)
    // Space Complexity: O(1)
    // ==========================================
    public static boolean canAttendMeetingsBruteForce(int[][] intervals) {
        if (intervals == null || intervals.length <= 1) return true;

        int n = intervals.length;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int startA = intervals[i][0], endA = intervals[i][1];
                int startB = intervals[j][0], endB = intervals[j][1];

                // Overlap condition: max(startA, startB) < min(endA, endB)
                if (Math.max(startA, startB) < Math.min(endA, endB)) {
                    return false;
                }
            }
        }

        return true;
    }

    // ==========================================
    // 2. OPTIMAL APPROACH (Sort by Start Time)
    // Sort intervals by start time in ascending order.
    // Iterate and verify if next.start < previous.end.
    // Time Complexity: O(N log N) due to sorting
    // Space Complexity: O(1) auxiliary space (or O(N) depending on sort)
    // ==========================================
    public static boolean canAttendMeetingsOptimal(int[][] intervals) {
        if (intervals == null || intervals.length <= 1) return true;

        // Sort by start time ascending
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));

        for (int i = 1; i < intervals.length; i++) {
            // Overlap exists if current start time < previous end time
            if (intervals[i][0] < intervals[i - 1][1]) {
                return false;
            }
        }

        return true;
    }

    // Quick Test / Demo
    public static void main(String[] args) {
        int[][] test1 = {{0, 30}, {5, 10}, {15, 20}}; // Expected: false
        int[][] test2 = {{7, 10}, {2, 4}};             // Expected: true
        int[][] test3 = {{1, 5}, {5, 10}, {10, 15}};   // Expected: true (Back-to-back non-overlapping)
        int[][] test4 = {{1, 5}, {4, 6}};              // Expected: false (Overlap [4,5])

        System.out.println("--- Bonus Problem 44: Meeting Rooms ---");

        System.out.println("\nTest 1 " + Arrays.deepToString(test1));
        System.out.println("  Brute Force: " + canAttendMeetingsBruteForce(test1));
        System.out.println("  Optimal:     " + canAttendMeetingsOptimal(test1));

        System.out.println("\nTest 2 " + Arrays.deepToString(test2));
        System.out.println("  Brute Force: " + canAttendMeetingsBruteForce(test2));
        System.out.println("  Optimal:     " + canAttendMeetingsOptimal(test2));

        System.out.println("\nTest 3 " + Arrays.deepToString(test3));
        System.out.println("  Brute Force: " + canAttendMeetingsBruteForce(test3));
        System.out.println("  Optimal:     " + canAttendMeetingsOptimal(test3));

        System.out.println("\nTest 4 " + Arrays.deepToString(test4));
        System.out.println("  Brute Force: " + canAttendMeetingsBruteForce(test4));
        System.out.println("  Optimal:     " + canAttendMeetingsOptimal(test4));
    }
}
