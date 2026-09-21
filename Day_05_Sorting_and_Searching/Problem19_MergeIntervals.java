package Day_05_Sorting_and_Searching;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Day 5 - Problem 19: Merge Intervals
 * Question: Given intervals [start, end], merge all overlapping intervals and return an array of non-overlapping intervals.
 * 
 * Target: O(N log N) Time, O(N) Space.
 */
public class Problem19_MergeIntervals {

    // ==========================================
    // 1. BRUTE FORCE APPROACH (Graph / Repeated Merging)
    // Compare pairs of intervals, merge overlapping pairs repeatedly until no overlaps remain.
    // Time Complexity: O(N^2)
    // Space Complexity: O(N)
    // ==========================================
    public static int[][] mergeIntervalsBruteForce(int[][] intervals) {
        if (intervals == null || intervals.length <= 1) return intervals;

        List<int[]> list = new ArrayList<>(Arrays.asList(intervals));
        boolean mergedAny = true;

        while (mergedAny) {
            mergedAny = false;
            int n = list.size();
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    int[] a = list.get(i);
                    int[] b = list.get(j);

                    // Check overlap: start <= end of other and end >= start of other
                    if (Math.max(a[0], b[0]) <= Math.min(a[1], b[1])) {
                        int[] merged = new int[]{Math.min(a[0], b[0]), Math.max(a[1], b[1])};
                        list.remove(j);
                        list.remove(i);
                        list.add(merged);
                        mergedAny = true;
                        break;
                    }
                }
                if (mergedAny) break;
            }
        }

        return list.toArray(new int[list.size()][]);
    }

    // ==========================================
    // 2. OPTIMAL APPROACH (Sorting by Start Time + Linear Scan)
    // Sort intervals by start time. Iterate through sorted intervals and merge adjacent overlaps.
    // Time Complexity: O(N log N) due to sorting
    // Space Complexity: O(N) for storing output list / extra space
    // ==========================================
    public static int[][] mergeIntervalsOptimal(int[][] intervals) {
        if (intervals == null || intervals.length <= 1) {
            return intervals;
        }

        // 1. Sort intervals by start time in ascending order
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

        List<int[]> merged = new ArrayList<>();

        for (int[] interval : intervals) {
            // If merged list is empty or current interval does not overlap with the last merged interval
            if (merged.isEmpty() || merged.get(merged.size() - 1)[1] < interval[0]) {
                merged.add(interval);
            } else {
                // Overlap exists: merge by extending the end time of the last interval
                merged.get(merged.size() - 1)[1] = Math.max(merged.get(merged.size() - 1)[1], interval[1]);
            }
        }

        return merged.toArray(new int[merged.size()][]);
    }

    // Helper method to print 2D interval array
    private static String intervalsToString(int[][] intervals) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < intervals.length; i++) {
            sb.append(Arrays.toString(intervals[i]));
            if (i < intervals.length - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    // Quick Test / Demo
    public static void main(String[] args) {
        int[][] test1 = {{1, 3}, {2, 6}, {8, 10}, {15, 18}};
        // Expected: [[1, 6], [8, 10], [15, 18]]

        int[][] test2 = {{1, 4}, {4, 5}};
        // Expected: [[1, 5]]

        int[][] test3 = {{1, 4}, {2, 3}};
        // Expected: [[1, 4]] (completely contained)

        System.out.println("Test 1 Input:  " + intervalsToString(test1));
        System.out.println("Brute Force:   " + intervalsToString(mergeIntervalsBruteForce(test1)));
        System.out.println("Optimal:       " + intervalsToString(mergeIntervalsOptimal(test1)));

        System.out.println("\nTest 2 Input:  " + intervalsToString(test2));
        System.out.println("Optimal:       " + intervalsToString(mergeIntervalsOptimal(test2)));

        System.out.println("\nTest 3 Input:  " + intervalsToString(test3));
        System.out.println("Optimal:       " + intervalsToString(mergeIntervalsOptimal(test3)));
    }
}
