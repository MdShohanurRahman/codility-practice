package Mock_Test_1;

import java.util.Arrays;

/**
 * Mock Test 1 - Problem C: Minimum Machines / Platforms Required
 * Question: Given job start and end times, find minimum number of machines required so no jobs overlap.
 * 
 * Target: O(N log N) Time, O(N) Space.
 */
public class ProblemC_MinimumMachinesRequired {

    // Optimal Sweep Line / Sorting Approach
    public static int minMachines(int[][] jobs) {
        if (jobs == null || jobs.length == 0) return 0;

        int n = jobs.length;
        int[] starts = new int[n];
        int[] ends = new int[n];

        for (int i = 0; i < n; i++) {
            starts[i] = jobs[i][0];
            ends[i] = jobs[i][1];
        }

        Arrays.sort(starts);
        Arrays.sort(ends);

        int machinesNeeded = 0;
        int maxMachines = 0;

        int i = 0, j = 0;
        while (i < n && j < n) {
            // If next job starts before or at the end of current job ending
            if (starts[i] < ends[j]) {
                machinesNeeded++;
                maxMachines = Math.max(maxMachines, machinesNeeded);
                i++;
            } else {
                machinesNeeded--;
                j++;
            }
        }

        return maxMachines;
    }

    public static void main(String[] args) {
        int[][] jobs1 = {{1, 4}, {2, 5}, {7, 9}, {3, 6}}; // Expected: 3
        int[][] jobs2 = {{900, 910}, {940, 1200}, {950, 1120}, {1100, 1130}, {1500, 1900}, {1800, 2000}}; // Expected: 3

        System.out.println("Mock Test 1 - Problem C");
        System.out.println("Test 1: " + minMachines(jobs1) + " (Expected: 3)");
        System.out.println("Test 2: " + minMachines(jobs2) + " (Expected: 3)");
    }
}
