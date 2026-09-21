package Day_08_Greedy_and_Medium_Problems;

import java.util.Arrays;

/**
 * Day 8 - Problem 31: Best Time to Buy and Sell Stock
 * Question: Given daily prices, find maximum profit from one buy and one later sell transaction.
 * 
 * Target: O(N) Time, O(1) Space.
 */
public class Problem31_BestTimeToBuyAndSellStock {

    // ==========================================
    // 1. BRUTE FORCE APPROACH
    // Check all pairs (i, j) where j > i and find max(prices[j] - prices[i]).
    // Time Complexity: O(N^2)
    // Space Complexity: O(1)
    // ==========================================
    public static int maxProfitBruteForce(int[] prices) {
        if (prices == null || prices.length <= 1) return 0;

        int maxProfit = 0;
        int n = prices.length;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int profit = prices[j] - prices[i];
                if (profit > maxProfit) {
                    maxProfit = profit;
                }
            }
        }

        return maxProfit;
    }

    // ==========================================
    // 2. OPTIMAL APPROACH (Single Pass Minimum Tracking)
    // Maintain minimum price seen so far and compute profit at each step.
    // Time Complexity: O(N) linear pass
    // Space Complexity: O(1) auxiliary space
    // ==========================================
    public static int maxProfitOptimal(int[] prices) {
        if (prices == null || prices.length <= 1) return 0;

        int minPrice = Integer.MAX_VALUE;
        int maxProfit = 0;

        for (int price : prices) {
            if (price < minPrice) {
                minPrice = price; // Update lowest buying price seen so far
            } else if (price - minPrice > maxProfit) {
                maxProfit = price - minPrice; // Update maximum profit
            }
        }

        return maxProfit;
    }

    // Quick Test / Demo
    public static void main(String[] args) {
        int[] prices1 = {7, 1, 5, 3, 6, 4}; // Expected: 5 (Buy at 1, Sell at 6)
        int[] prices2 = {7, 6, 4, 3, 1};    // Expected: 0 (No profitable trade)
        int[] prices3 = {1, 2, 3, 4, 5};    // Expected: 4 (Buy at 1, Sell at 5)

        System.out.println("Test 1 " + Arrays.toString(prices1));
        System.out.println("  Brute Force: " + maxProfitBruteForce(prices1));
        System.out.println("  Optimal:     " + maxProfitOptimal(prices1));

        System.out.println("\nTest 2 " + Arrays.toString(prices2));
        System.out.println("  Brute Force: " + maxProfitBruteForce(prices2));
        System.out.println("  Optimal:     " + maxProfitOptimal(prices2));

        System.out.println("\nTest 3 " + Arrays.toString(prices3));
        System.out.println("  Optimal:     " + maxProfitOptimal(prices3));
    }
}
