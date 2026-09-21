package Day_06_Stack_and_Queue;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

/**
 * Day 6 - Problem 24: Queue Simulation (Time Needed to Process Jobs / Buy Tickets)
 * Question: Simulate a queue where jobs/people are processed 1 unit at a time in round-robin order.
 * Given an array 'tickets' where tickets[i] is the tickets person i wants to buy,
 * and a target index 'k', return the total time needed for person k to finish buying all tickets.
 * 
 * Target: Aim for O(N) Time, O(1) Space optimal approach.
 */
public class Problem24_QueueSimulation {

    // ==========================================
    // 1. SIMULATION APPROACH (Queue Simulation)
    // Enqueue all persons as [index, remainingTickets].
    // Process front person for 1 unit of time.
    // If remainingTickets > 0, re-enqueue to the back.
    // Stop when person k has 0 remaining tickets.
    // Time Complexity: O(Sum of tickets) - can be large if tickets[i] is big!
    // Space Complexity: O(N) for Queue data structure
    // ==========================================
    public static int timeRequiredToBuySimulation(int[] tickets, int k) {
        if (tickets == null || tickets.length == 0 || k < 0 || k >= tickets.length) {
            return 0;
        }

        Queue<int[]> queue = new ArrayDeque<>();
        for (int i = 0; i < tickets.length; i++) {
            queue.offer(new int[]{i, tickets[i]});
        }

        int time = 0;
        while (!queue.isEmpty()) {
            int[] person = queue.poll();
            int index = person[0];
            int remaining = person[1] - 1;
            time++;

            if (index == k && remaining == 0) {
                return time; // Target person k has finished all tickets
            }

            if (remaining > 0) {
                queue.offer(new int[]{index, remaining});
            }
        }

        return time;
    }

    // ==========================================
    // 2. OPTIMAL APPROACH (Single-Pass Mathematical Counting)
    // Calculate total rounds directly without explicit queue simulation.
    // For person i <= k: they process at most min(tickets[i], tickets[k]) times.
    // For person i > k:  they process at most min(tickets[i], tickets[k] - 1) times.
    // Time Complexity: O(N) single linear scan
    // Space Complexity: O(1) auxiliary space
    // ==========================================
    public static int timeRequiredToBuyOptimal(int[] tickets, int k) {
        if (tickets == null || tickets.length == 0 || k < 0 || k >= tickets.length) {
            return 0;
        }

        int totalTime = 0;
        int targetTickets = tickets[k];

        for (int i = 0; i < tickets.length; i++) {
            if (i <= k) {
                totalTime += Math.min(tickets[i], targetTickets);
            } else {
                totalTime += Math.min(tickets[i], targetTickets - 1);
            }
        }

        return totalTime;
    }

    // Quick Test / Demo
    public static void main(String[] args) {
        int[] tickets1 = {2, 3, 2}; int k1 = 2; // Expected: 6
        int[] tickets2 = {5, 1, 1, 1}; int k2 = 0; // Expected: 8
        int[] tickets3 = {84, 80, 43, 8, 80, 88, 43, 14, 100, 88}; int k3 = 3; // Expected: 60

        System.out.println("Test 1 " + Arrays.toString(tickets1) + ", k=" + k1);
        System.out.println("  Simulation: " + timeRequiredToBuySimulation(tickets1, k1));
        System.out.println("  Optimal:    " + timeRequiredToBuyOptimal(tickets1, k1));

        System.out.println("\nTest 2 " + Arrays.toString(tickets2) + ", k=" + k2);
        System.out.println("  Simulation: " + timeRequiredToBuySimulation(tickets2, k2));
        System.out.println("  Optimal:    " + timeRequiredToBuyOptimal(tickets2, k2));

        System.out.println("\nTest 3 " + Arrays.toString(tickets3) + ", k=" + k3);
        System.out.println("  Simulation: " + timeRequiredToBuySimulation(tickets3, k3));
        System.out.println("  Optimal:    " + timeRequiredToBuyOptimal(tickets3, k3));
    }
}
