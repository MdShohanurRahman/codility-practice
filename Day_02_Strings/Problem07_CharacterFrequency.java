package Day_02_Strings;

/**
 * Day 2 - Problem 7: Character Frequency
 * Question: Return the character with the highest frequency in a string.
 * Tie-breaking rule: Return the first character in the string that achieves the maximum frequency.
 */
public class Problem07_CharacterFrequency {

    // ==========================================
    // 1. BRUTE FORCE APPROACH (Nested Loops)
    // Time Complexity: O(N^2)
    // Space Complexity: O(1)
    // ==========================================
    public static char highestFrequencyBruteForce(String s) {
        if (s == null || s.isEmpty()) return '\0';

        char maxChar = s.charAt(0);
        int maxFreq = 0;

        for (int i = 0; i < s.length(); i++) {
            char curr = s.charAt(i);
            int freq = 0;
            for (int j = 0; j < s.length(); j++) {
                if (s.charAt(j) == curr) {
                    freq++;
                }
            }
            if (freq > maxFreq) {
                maxFreq = freq;
                maxChar = curr;
            }
        }
        return maxChar;
    }

    // ==========================================
    // 2. OPTIMAL APPROACH (Frequency Count Map/Array)
    // Time Complexity: O(N)
    // Space Complexity: O(1) - fixed array of size 256
    // Tie-breaking: Preserves first-occurrence order in original string
    // ==========================================
    public static char highestFrequencyOptimal(String s) {
        if (s == null || s.isEmpty()) return '\0';

        int[] count = new int[256];

        // Pass 1: Build frequency table
        for (int i = 0; i < s.length(); i++) {
            count[s.charAt(i)]++;
        }

        // Pass 2: Find max freq char maintaining string order for tie-breaking
        char maxChar = s.charAt(0);
        int maxFreq = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (count[c] > maxFreq) {
                maxFreq = count[c];
                maxChar = c;
            }
        }

        return maxChar;
    }

    // Demo
    public static void main(String[] args) {
        String test1 = "moneyllion"; // 'n' -> 2, 'o' -> 2, 'l' -> 2; 'o' or 'm' first depending on tie break
        String test2 = "sample text with repeated characters"; // 'e' or ' '

        System.out.println("Test 1 (Brute Force): " + highestFrequencyBruteForce(test1));
        System.out.println("Test 1 (Optimal): " + highestFrequencyOptimal(test1));

        System.out.println("Test 2 (Optimal): " + highestFrequencyOptimal(test2));
    }
}
