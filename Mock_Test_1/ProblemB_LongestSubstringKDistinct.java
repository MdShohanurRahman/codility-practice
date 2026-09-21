package Mock_Test_1;

import java.util.HashMap;
import java.util.Map;

/**
 * Mock Test 1 - Problem B: Longest Substring with At Most K Distinct Characters
 * Question: Find the length of the longest substring containing at most K distinct characters.
 * 
 * Target: O(N) Time, O(K) Auxiliary Space.
 */
public class ProblemB_LongestSubstringKDistinct {

    // Optimal Sliding Window Approach
    public static int lengthOfLongestSubstringKDistinct(String s, int k) {
        if (s == null || s.length() == 0 || k <= 0) return 0;

        Map<Character, Integer> freqMap = new HashMap<>();
        int left = 0;
        int maxLen = 0;

        for (int right = 0; right < s.length(); right++) {
            char rightChar = s.charAt(right);
            freqMap.put(rightChar, freqMap.getOrDefault(rightChar, 0) + 1);

            // Shrink window if distinct character count > k
            while (freqMap.size() > k) {
                char leftChar = s.charAt(left);
                freqMap.put(leftChar, freqMap.get(leftChar) - 1);
                if (freqMap.get(leftChar) == 0) {
                    freqMap.remove(leftChar);
                }
                left++;
            }

            maxLen = Math.max(maxLen, right - left + 1);
        }

        return maxLen;
    }

    public static void main(String[] args) {
        String s1 = "eceba"; int k1 = 2; // Expected: 3 ("ece")
        String s2 = "aa";    int k2 = 1; // Expected: 2 ("aa")
        String s3 = "a@b$c"; int k3 = 3; // Expected: 3 ("a@b")

        System.out.println("Mock Test 1 - Problem B");
        System.out.println("Test 1: " + lengthOfLongestSubstringKDistinct(s1, k1) + " (Expected: 3)");
        System.out.println("Test 2: " + lengthOfLongestSubstringKDistinct(s2, k2) + " (Expected: 2)");
        System.out.println("Test 3: " + lengthOfLongestSubstringKDistinct(s3, k3) + " (Expected: 3)");
    }
}
