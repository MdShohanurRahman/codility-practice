package Day_02_Strings;

import java.util.Arrays;

/**
 * Day 2 - Problem 6: Valid Anagram
 * Question: Determine whether two strings are anagrams of each other.
 */
public class Problem06_ValidAnagram {

    // ==========================================
    // 1. BRUTE FORCE / SORTING APPROACH
    // Time Complexity: O(N log N)
    // Space Complexity: O(N)
    // ==========================================
    public static boolean isAnagramSorting(String s, String t) {
        if (s == null || t == null || s.length() != t.length()) {
            return false;
        }

        char[] sArr = s.toCharArray();
        char[] tArr = t.toCharArray();

        Arrays.sort(sArr);
        Arrays.sort(tArr);

        return Arrays.equals(sArr, tArr);
    }

    // ==========================================
    // 2. OPTIMAL APPROACH (Frequency Counting)
    // Time Complexity: O(N)
    // Space Complexity: O(1) - fixed array of size 256
    // ==========================================
    public static boolean isAnagramOptimal(String s, String t) {
        if (s == null || t == null || s.length() != t.length()) {
            return false;
        }

        int[] count = new int[256];

        for (int i = 0; i < s.length(); i++) {
            count[s.charAt(i)]++;
            count[t.charAt(i)]--;
        }

        for (int c : count) {
            if (c != 0) {
                return false;
            }
        }

        return true;
    }

    // Demo
    public static void main(String[] args) {
        String s1 = "anagram", t1 = "nagaram";
        String s2 = "rat", t2 = "car";

        System.out.println("Test 1 (Sorting): " + isAnagramSorting(s1, t1)); // true
        System.out.println("Test 1 (Optimal): " + isAnagramOptimal(s1, t1)); // true

        System.out.println("Test 2 (Sorting): " + isAnagramSorting(s2, t2)); // false
        System.out.println("Test 2 (Optimal): " + isAnagramOptimal(s2, t2)); // false
    }
}
