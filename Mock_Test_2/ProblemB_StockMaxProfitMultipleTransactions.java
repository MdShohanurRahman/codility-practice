package Mock_Test_2;

/**
 * Mock Test 2 - Problem B: Best Time to Buy and Sell Stock (Multiple Transactions)
 * Question: Maximize profit by making as many buy/sell transactions as desired.
 * 
 * Target: O(N) Time, O(1) Space.
 */
public class ProblemB_StockMaxProfitMultipleTransactions {

    // Optimal Greedy Peak-Valley Approach
    public static int maxProfit(int[] prices) {
        if (prices == null || prices.length <= 1) return 0;

        int totalProfit = 0;
        for (int i = 1; i < prices.length; i++) {
            // Accumulate every positive price difference
            if (prices[i] > prices[i - 1]) {
                totalProfit += prices[i] - prices[i - 1];
            }
        }

        return totalProfit;
    }

    public static void main(String[] args) {
        int[] prices1 = {7, 1, 5, 3, 6, 4}; // Expected: 7 (Buy@1 sell@5 -> 4; buy@3 sell@6 -> 3. Total = 7)
        int[] prices2 = {1, 2, 3, 4, 5};    // Expected: 4
        int[] prices3 = {7, 6, 4, 3, 1};    // Expected: 0

        System.out.println("Mock Test 2 - Problem B");
        System.out.println("Test 1: " + maxProfit(prices1) + " (Expected: 7)");
        System.out.println("Test 2: " + maxProfit(prices2) + " (Expected: 4)");
        System.out.println("Test 3: " + maxProfit(prices3) + " (Expected: 0)");
    }
}
