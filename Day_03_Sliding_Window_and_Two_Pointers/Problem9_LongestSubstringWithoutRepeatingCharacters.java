package Day_03_Sliding_Window_and_Two_Pointers;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Day 3 - Problem 9: Longest Substring Without Repeating Characters
 * Question: Given a string s, find the length of the longest substring containing no repeating characters.
 */
public class Problem9_LongestSubstringWithoutRepeatingCharacters {

    // ==========================================
    // 1. BRUTE FORCE APPROACH
    // Check all possible substrings and verify if all characters are unique.
    // Time Complexity: O(N^3) or O(N^2) depending on substring check
    // Space Complexity: O(min(N, M)) where M is character set size
    // ==========================================
    public static int longestSubstringBruteForce(String s) {
        if (s == null || s.isEmpty()) return 0;
        int n = s.length();
        int maxLength = 0;

        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                if (allUnique(s, i, j)) {
                    maxLength = Math.max(maxLength, j - i + 1);
                }
            }
        }
        return maxLength;
    }

    private static boolean allUnique(String s, int start, int end) {
        Set<Character> set = new HashSet<>();
        for (int i = start; i <= end; i++) {
            char ch = s.charAt(i);
            if (set.contains(ch)) {
                return false;
            }
            set.add(ch);
        }
        return true;
    }

    // ==========================================
    // 2. OPTIMAL APPROACH 1 (Sliding Window with HashSet)
    // Expand right pointer, shrink left pointer when duplicate is found.
    // Time Complexity: O(N) - each character is visited at most twice (left & right)
    // Space Complexity: O(min(N, M)) where M is character set size (e.g. 128 for ASCII)
    // ==========================================
    public static int longestSubstringSlidingWindow(String s) {
        if (s == null || s.isEmpty()) return 0;
        int n = s.length();
        Set<Character> seen = new HashSet<>();
        int maxLength = 0;
        int left = 0;

        for (int right = 0; right < n; right++) {
            char currentChar = s.charAt(right);
            // Shrink window until currentChar is removed
            while (seen.contains(currentChar)) {
                seen.remove(s.charAt(left));
                left++;
            }
            seen.add(currentChar);
            maxLength = Math.max(maxLength, right - left + 1);
        }
        return maxLength;
    }

    // ==========================================
    // 3. OPTIMAL APPROACH 2 (Optimized Sliding Window with Direct Index Jump)
    // Store the last seen index of each character to skip left pointer directly.
    // Time Complexity: O(N) - single pass over string
    // Space Complexity: O(M) where M is alphabet size (128 for standard ASCII)
    // ==========================================
    public static int longestSubstringOptimized(String s) {
        if (s == null || s.isEmpty()) return 0;
        int n = s.length();
        int maxLength = 0;
        int left = 0;

        // Array to store last seen index of ASCII characters (initialized to -1)
        int[] lastSeen = new int[128];
        Arrays.fill(lastSeen, -1);

        for (int right = 0; right < n; right++) {
            char currentChar = s.charAt(right);

            // If character was seen within current window, jump left pointer
            if (lastSeen[currentChar] >= left) {
                left = lastSeen[currentChar] + 1;
            }

            lastSeen[currentChar] = right;
            maxLength = Math.max(maxLength, right - left + 1);
        }

        return maxLength;
    }

    // Quick Test / Demo
    public static void main(String[] args) {
        String test1 = "abcabcbb"; // "abc" -> 3
        String test2 = "bbbbb";    // "b" -> 1
        String test3 = "pwwkew";   // "wke" -> 3
        String test4 = "";         // 0
        String test5 = " ";        // " " -> 1
        String test6 = "au";       // "au" -> 2
        String test7 = "dvdf";     // "vdf" -> 3

        System.out.println("Test 1 (\"abcabcbb\"):");
        System.out.println("  Brute Force: " + longestSubstringBruteForce(test1));
        System.out.println("  Sliding Window (HashSet): " + longestSubstringSlidingWindow(test1));
        System.out.println("  Optimized (Direct Jump): " + longestSubstringOptimized(test1));

        System.out.println("\nTest 2 (\"bbbbb\"): " + longestSubstringOptimized(test2));
        System.out.println("Test 3 (\"pwwkew\"): " + longestSubstringOptimized(test3));
        System.out.println("Test 4 (\"\"): " + longestSubstringOptimized(test4));
        System.out.println("Test 5 (\" \"): " + longestSubstringOptimized(test5));
        System.out.println("Test 6 (\"au\"): " + longestSubstringOptimized(test6));
        System.out.println("Test 7 (\"dvdf\"): " + longestSubstringOptimized(test7));
    }
}
