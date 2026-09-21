package Day_05_Sorting_and_Searching;

import java.util.Arrays;

/**
 * Day 5 - Problem 20: Minimum Number of Platforms / Resources
 * Question: Given arrival and departure times of events/trains, find the
 * minimum number of resources
 * required so that no overlapping events conflict.
 * 
 * Target: O(N log N) Time, O(1) or O(N) Extra Space.
 */
public class Problem20_MinimumPlatforms {

    // ==========================================
    // 1. BRUTE FORCE APPROACH
    // For each train i, count how many other trains j overlap with it
    // simultaneously.
    // Overlap condition: arrival[j] <= arrival[i] and departure[j] >= arrival[i].
    // Return the maximum overlap count over all trains.
    // Time Complexity: O(N^2)
    // Space Complexity: O(1)
    // ==========================================
    public static int findPlatformBruteForce(int[] arrivals, int[] departures) {
        if (arrivals == null || departures == null || arrivals.length == 0)
            return 0;
        int n = arrivals.length;
        int maxPlatforms = 1;

        for (int i = 0; i < n; i++) {
            int neededPlatforms = 1;
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    // Check if train j is present when train i arrives
                    if (arrivals[j] <= arrivals[i] && departures[j] >= arrivals[i]) {
                        neededPlatforms++;
                    }
                }
            }
            maxPlatforms = Math.max(maxPlatforms, neededPlatforms);
        }
        return maxPlatforms;
    }

    // ==========================================
    // 2. OPTIMAL APPROACH (Chronological Event Sweeping / Two Pointers)
    // Sort arrivals and departures independently. Use two pointers to track active
    // overlaps.
    // Time Complexity: O(N log N) due to sorting arrays
    // Space Complexity: O(N) auxiliary space (or O(1) if primitive arrays are
    // sorted in-place)
    // ==========================================
    public static int findPlatformOptimal(int[] arrivals, int[] departures) {
        if (arrivals == null || departures == null || arrivals.length == 0)
            return 0;

        int n = arrivals.length;

        // Clone to avoid mutating caller's original arrays
        int[] arr = arrivals.clone();
        int[] dep = departures.clone();

        // 1. Sort arrival and departure arrays independently
        Arrays.sort(arr);
        Arrays.sort(dep);

        // 2. Two pointer sweep
        int neededPlatforms = 0;
        int maxPlatforms = 0;

        int i = 0; // Pointer for arrivals
        int j = 0; // Pointer for departures

        while (i < n) {
            // A train arrives before (or at the same time) the current train departs
            if (arr[i] <= dep[j]) {
                neededPlatforms++;
                i++;
            } else {
                // A train departs, freeing up a platform
                neededPlatforms--;
                j++;
            }
            maxPlatforms = Math.max(maxPlatforms, neededPlatforms);
        }

        return maxPlatforms;
    }

    // Quick Test / Demo
    public static void main(String[] args) {
        // Example: 24-hour time represented as integers (e.g. 900 = 09:00, 1100 =
        // 11:00)
        int[] arrivals1 = { 900, 940, 950, 1100, 1500, 1800 };
        int[] departures1 = { 910, 1200, 1120, 1130, 1900, 2000 };
        // Expected platforms: 3 (At 9:50, trains 2, 3 are at platform, train 1 departed
        // at 9:10 -> max 3 trains overlap)

        int[] arrivals2 = { 900, 1100, 1230 };
        int[] departures2 = { 1000, 1200, 1300 };
        // Expected platforms: 1 (No overlaps)

        System.out.println("Test 1 Arrivals:   " + Arrays.toString(arrivals1));
        System.out.println("Test 1 Departures: " + Arrays.toString(departures1));
        System.out.println("Brute Force: " + findPlatformBruteForce(arrivals1, departures1));
        System.out.println("Optimal:     " + findPlatformOptimal(arrivals1, departures1));

        System.out.println("\nTest 2 Arrivals:   " + Arrays.toString(arrivals2));
        System.out.println("Test 2 Departures: " + Arrays.toString(departures2));
        System.out.println("Optimal:     " + findPlatformOptimal(arrivals2, departures2));
    }
}
