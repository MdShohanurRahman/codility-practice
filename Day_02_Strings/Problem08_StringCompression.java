package Day_02_Strings;

/**
 * Day 2 - Problem 8: String Compression
 * Question: Compress consecutive repeated characters.
 * Example: "aaabbc" -> "a3b2c1"
 */
public class Problem08_StringCompression {

    // ==========================================
    // 1. BRUTE FORCE / NAIVE APPROACH (String Concatenation in Loop)
    // Time Complexity: O(N^2) due to immutable String concatenation (+ operator creates new String per step)
    // Space Complexity: O(N^2) due to intermediate garbage strings
    // ==========================================
    public static String compressBruteForce(String s) {
        if (s == null || s.isEmpty()) return "";

        String result = "";
        int n = s.length();
        int count = 1;

        for (int i = 0; i < n; i++) {
            if (i + 1 < n && s.charAt(i) == s.charAt(i + 1)) {
                count++;
            } else {
                result = result + s.charAt(i) + count;
                count = 1;
            }
        }
        return result;
    }

    // ==========================================
    // 2. OPTIMAL APPROACH (StringBuilder & Two-Pointer / Single-Pass)
    // Time Complexity: O(N)
    // Space Complexity: O(N) for output buffer
    // ==========================================
    public static String compressOptimal(String s) {
        if (s == null || s.isEmpty()) return "";

        StringBuilder sb = new StringBuilder();
        int n = s.length();
        int count = 1;

        for (int i = 0; i < n; i++) {
            if (i + 1 < n && s.charAt(i) == s.charAt(i + 1)) {
                count++;
            } else {
                sb.append(s.charAt(i)).append(count);
                count = 1;
            }
        }

        return sb.toString();
    }

    // Alternative Variant: Return original if compressed is not smaller
    public static String compressOrOriginal(String s) {
        String compressed = compressOptimal(s);
        return compressed.length() < s.length() ? compressed : s;
    }

    // Demo
    public static void main(String[] args) {
        String test1 = "aaabbc";
        String test2 = "aabcccccaaa";
        String test3 = "abcd";

        System.out.println("Test 1 (Brute Force): " + compressBruteForce(test1)); // "a3b2c1"
        System.out.println("Test 1 (Optimal): " + compressOptimal(test1));         // "a3b2c1"

        System.out.println("Test 2 (Optimal): " + compressOptimal(test2));         // "a2b1c5a3"

        System.out.println("Test 3 (Compressed): " + compressOptimal(test3));       // "a1b1c1d1"
        System.out.println("Test 3 (Or Original): " + compressOrOriginal(test3));  // "abcd"
    }
}
