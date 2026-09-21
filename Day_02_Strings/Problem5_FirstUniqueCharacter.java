package Day_02_Strings;

import java.util.HashMap;
import java.util.Map;

/**
 * Day 2 - Problem 5: First Unique Character
 * Question: Find the first character that occurs exactly once in a string.
 * Return index (0-indexed) or character. Returns -1 / '\0' if no unique
 * character exists.
 */
public class Problem5_FirstUniqueCharacter {

    // ==========================================
    // 1. BRUTE FORCE APPROACH (Nested Loops)
    // Time Complexity: O(N^2)
    // Space Complexity: O(1)
    // ==========================================
    public static int firstUniqCharBruteForce(String s) {
        if (s == null || s.isEmpty())
            return -1;
        int n = s.length();
        for (int i = 0; i < n; i++) {
            boolean isUnique = true;
            for (int j = 0; j < n; j++) {
                if (i != j && s.charAt(i) == s.charAt(j)) {
                    isUnique = false;
                    break;
                }
            }
            if (isUnique)
                return i;
        }
        return -1;
    }

    // ==========================================
    // 2. OPTIMAL APPROACH (Frequency Array - Two Pass)
    // Time Complexity: O(N)
    // Space Complexity: O(1) - constant array of size 256 for ASCII
    // ==========================================
    public static int firstUniqCharOptimal(String s) {
        if (s == null || s.isEmpty())
            return -1;

        int[] count = new int[256]; // Extended ASCII coverage

        // Pass 1: Build frequency map
        for (int i = 0; i < s.length(); i++) {
            count[s.charAt(i)]++;
        }

        // Pass 2: Find first character with count == 1
        for (int i = 0; i < s.length(); i++) {
            if (count[s.charAt(i)] == 1) {
                return i;
            }
        }

        return -1;
    }

    // Demo
    public static void main(String[] args) {
        String test1 = "leetcode"; // 'l' at index 0
        String test2 = "loveleetcode"; // 'v' at index 2
        String test3 = "aabb"; // none -> -1

        System.out.println("Test 1 (Brute Force): " + firstUniqCharBruteForce(test1));
        System.out.println("Test 1 (Optimal): " + firstUniqCharOptimal(test1));

        System.out.println("Test 2 (Optimal): " + firstUniqCharOptimal(test2));
        System.out.println("Test 3 (Optimal): " + firstUniqCharOptimal(test3));
    }
}
