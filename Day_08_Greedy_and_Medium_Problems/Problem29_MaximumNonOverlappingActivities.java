package Day_08_Greedy_and_Medium_Problems;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Day 8 - Problem 29: Maximum Non-Overlapping Activities (Activity Selection)
 * Question: Given start and end times of activities, select the maximum number of mutually non-overlapping activities.
 * 
 * Target: O(N log N) Time, O(1) or O(N) Space.
 */
public class Problem29_MaximumNonOverlappingActivities {

    public static class Activity {
        public int start;
        public int end;

        public Activity(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public String toString() {
            return "[" + start + ", " + end + "]";
        }
    }

    // ==========================================
    // 1. BRUTE FORCE APPROACH (Recursive Backtracking / Power Set)
    // Try all 2^N subsets of activities and check for valid non-overlapping combinations.
    // Time Complexity: O(2^N * N)
    // Space Complexity: O(N) recursion stack
    // ==========================================
    public static int maxActivitiesBruteForce(int[][] intervals) {
        if (intervals == null || intervals.length == 0) return 0;
        
        Activity[] activities = new Activity[intervals.length];
        for (int i = 0; i < intervals.length; i++) {
            activities[i] = new Activity(intervals[i][0], intervals[i][1]);
        }

        return findMaxHelper(activities, 0, -1);
    }

    private static int findMaxHelper(Activity[] activities, int index, int lastEndTime) {
        if (index == activities.length) {
            return 0;
        }

        // Option 1: Skip current activity
        int exclude = findMaxHelper(activities, index + 1, lastEndTime);

        // Option 2: Include current activity (if non-overlapping)
        int include = 0;
        if (activities[index].start >= lastEndTime) {
            include = 1 + findMaxHelper(activities, index + 1, activities[index].end);
        }

        return Math.max(include, exclude);
    }

    // ==========================================
    // 2. OPTIMAL APPROACH (Greedy - Sort by Finish Time)
    // Sort activities by end time in ascending order.
    // Always pick the next activity whose start time >= end time of last selected activity.
    // Time Complexity: O(N log N) due to sorting
    // Space Complexity: O(N) for array storage (or O(1) auxiliary)
    // ==========================================
    public static int maxActivitiesOptimal(int[][] intervals) {
        if (intervals == null || intervals.length == 0) return 0;

        Activity[] activities = new Activity[intervals.length];
        for (int i = 0; i < intervals.length; i++) {
            activities[i] = new Activity(intervals[i][0], intervals[i][1]);
        }

        // Sort activities by finish time (end time)
        Arrays.sort(activities, Comparator.comparingInt(a -> a.end));

        int count = 1;
        int lastEndTime = activities[0].end;

        for (int i = 1; i < activities.length; i++) {
            // Non-overlapping condition: next start time >= current end time
            if (activities[i].start >= lastEndTime) {
                count++;
                lastEndTime = activities[i].end;
            }
        }

        return count;
    }

    // Quick Test / Demo
    public static void main(String[] args) {
        int[][] test1 = {{1, 3}, {2, 4}, {3, 6}, {5, 7}, {8, 9}, {5, 9}};
        // Sorted by end time: [1,3], [2,4], [3,6], [5,7], [8,9], [5,9]
        // Selected: [1,3], [3,6], [8,9] -> count 3 (or [1,3], [5,7], [8,9] -> count 4: [1,3], [3,6], [6,7] etc.)

        int[][] test2 = {{1, 2}, {2, 3}, {3, 4}};
        // Selected: [1,2], [2,3], [3,4] -> count 3

        int[][] test3 = {{1, 10}, {2, 3}, {4, 5}, {6, 7}};
        // Selected: [2,3], [4,5], [6,7] -> count 3

        System.out.println("Test 1 max activities:");
        System.out.println("  BruteForce: " + maxActivitiesBruteForce(test1));
        System.out.println("  Optimal:    " + maxActivitiesOptimal(test1));

        System.out.println("\nTest 2 max activities:");
        System.out.println("  Optimal:    " + maxActivitiesOptimal(test2));

        System.out.println("\nTest 3 max activities:");
        System.out.println("  Optimal:    " + maxActivitiesOptimal(test3));
    }
}
