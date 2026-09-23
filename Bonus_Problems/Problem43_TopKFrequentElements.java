package Bonus_Problems;

import java.util.*;

/**
 * Bonus Problem 43: Top K Frequent Elements
 * Question: Return the K most frequent values in an integer array.
 * 
 * Target: Better than O(N log N) time, ideally O(N) or O(N log K).
 */
public class Problem43_TopKFrequentElements {

    // ==========================================
    // 1. MIN-HEAP APPROACH
    // Count frequencies using HashMap, then push to PriorityQueue of size K.
    // Time Complexity: O(N log K)
    // Space Complexity: O(N + K)
    // ==========================================
    public static int[] topKFrequentMinHeap(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0)
            return new int[0];

        Map<Integer, Integer> countMap = new HashMap<>();
        for (int num : nums) {
            countMap.put(num, countMap.getOrDefault(num, 0) + 1);
        }

        // Min-Heap ordered by frequency (ascending)
        PriorityQueue<Map.Entry<Integer, Integer>> minHeap = new PriorityQueue<>(
                Comparator.comparingInt(Map.Entry::getValue));

        for (Map.Entry<Integer, Integer> entry : countMap.entrySet()) {
            minHeap.offer(entry);
            if (minHeap.size() > k) {
                minHeap.poll(); // Evict smallest frequency
            }
        }

        int[] result = new int[k];
        int index = 0;
        while (!minHeap.isEmpty()) {
            result[index++] = minHeap.poll().getKey();
        }

        return result;
    }

    // ==========================================
    // 2. OPTIMAL BUCKET SORT APPROACH
    // Group numbers into buckets based on frequency (frequency ranges from 1 to N).
    // Collect elements starting from frequency N down to 1 until K elements are
    // selected.
    // Time Complexity: O(N) linear time
    // Space Complexity: O(N) auxiliary space
    // ==========================================
    public static int[] topKFrequentBucketSort(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0)
            return new int[0];

        int n = nums.length;
        Map<Integer, Integer> countMap = new HashMap<>();
        for (int num : nums) {
            countMap.put(num, countMap.getOrDefault(num, 0) + 1);
        }

        // Bucket array where index represents frequency
        @SuppressWarnings("unchecked")
        List<Integer>[] buckets = new List[n + 1];
        for (int i = 0; i <= n; i++) {
            buckets[i] = new ArrayList<>();
        }

        for (Map.Entry<Integer, Integer> entry : countMap.entrySet()) {
            int num = entry.getKey();
            int freq = entry.getValue();
            buckets[freq].add(num);
        }

        int[] result = new int[k];
        int idx = 0;

        // Traverse buckets from maximum frequency N down to 1
        for (int freq = n; freq >= 1 && idx < k; freq--) {
            for (int num : buckets[freq]) {
                result[idx++] = num;
                if (idx == k)
                    break;
            }
        }

        return result;
    }

    public static int[] topKFrequent(int[] nums, int k) {

        // 1. Count frequency
        Map<Integer, Integer> freq = new HashMap<>();

        for (int num : nums) {
            freq.put(num, freq.getOrDefault(num, 0) + 1);
        }

        // 2. Sort numbers by frequency
        List<Integer> numbers = new ArrayList<>(freq.keySet());

        numbers.sort((a, b) -> freq.get(b) - freq.get(a));

        // 3. Take first k
        int[] result = new int[k];

        for (int i = 0; i < k; i++) {
            result[i] = numbers.get(i);
        }

        return result;
    }

    // Quick Test / Demo
    public static void main(String[] args) {
        int[] nums1 = { 1, 1, 1, 2, 2, 3 };
        int k1 = 2; // Expected: [1, 2]

        int[] nums2 = { 1 };
        int k2 = 1; // Expected: [1]

        int[] nums3 = { 4, 4, 4, 6, 6, 7, 7, 7, 7, 9 };
        int k3 = 2; // Expected: [7, 4]

        System.out.println("--- Bonus Problem 43: Top K Frequent Elements ---");

        System.out.println("\nTest 1 (k=" + k1 + "): " + Arrays.toString(nums1));
        System.out.println("  Min-Heap O(N log K):   " + Arrays.toString(topKFrequentMinHeap(nums1, k1)));
        System.out.println("  Bucket Sort O(N):      " + Arrays.toString(topKFrequentBucketSort(nums1, k1)));

        System.out.println("\nTest 2 (k=" + k2 + "): " + Arrays.toString(nums2));
        System.out.println("  Min-Heap O(N log K):   " + Arrays.toString(topKFrequentMinHeap(nums2, k2)));
        System.out.println("  Bucket Sort O(N):      " + Arrays.toString(topKFrequentBucketSort(nums2, k2)));

        System.out.println("\nTest 3 (k=" + k3 + "): " + Arrays.toString(nums3));
        System.out.println("  Min-Heap O(N log K):   " + Arrays.toString(topKFrequentMinHeap(nums3, k3)));
        System.out.println("  Bucket Sort O(N):      " + Arrays.toString(topKFrequentBucketSort(nums3, k3)));
    }
}
