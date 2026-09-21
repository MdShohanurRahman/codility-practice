package Day_06_Stack_and_Queue;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Day 6 - Problem 22: Brackets (Codility Brackets Variant)
 * Question: Given a string S containing three types of brackets, return 1 if it is properly nested, and 0 otherwise.
 * 
 * Target: O(N) Time, O(N) Space.
 */
public class Problem22_Brackets {

    // ==========================================
    // 1. BRUTE FORCE APPROACH (Iterative String Substring Search)
    // Repeatedly replace balanced adjacent bracket pairs until no progress is made.
    // Time Complexity: O(N^2)
    // Space Complexity: O(N^2)
    // ==========================================
    public static int solutionBruteForce(String S) {
        if (S == null) return 0;
        if (S.isEmpty()) return 1;

        int prevLength;
        do {
            prevLength = S.length();
            S = S.replace("()", "")
                 .replace("[]", "")
                 .replace("{}", "");
        } while (S.length() < prevLength);

        return S.isEmpty() ? 1 : 0;
    }

    // ==========================================
    // 2. OPTIMAL APPROACH (Deque Stack)
    // Push expected closing brackets. On closing bracket, verify non-empty stack and matching top.
    // Time Complexity: O(N)
    // Space Complexity: O(N)
    // ==========================================
    public static int solutionOptimal(String S) {
        if (S == null) return 0;
        if (S.isEmpty()) return 1;
        if (S.length() % 2 != 0) return 0; // Odd length strings cannot be properly nested

        Deque<Character> stack = new ArrayDeque<>();

        for (char c : S.toCharArray()) {
            if (c == '(') {
                stack.push(')');
            } else if (c == '[') {
                stack.push(']');
            } else if (c == '{') {
                stack.push('}');
            } else {
                if (stack.isEmpty() || stack.pop() != c) {
                    return 0;
                }
            }
        }

        return stack.isEmpty() ? 1 : 0;
    }

    // Quick Test / Demo
    public static void main(String[] args) {
        String s1 = "{[()()]}";  // Output: 1
        String s2 = "([)()]";    // Output: 0
        String s3 = "";          // Output: 1
        String s4 = ")(";        // Output: 0
        String s5 = "{{{{";      // Output: 0

        System.out.println("Test 1 \"" + s1 + "\": BruteForce=" + solutionBruteForce(s1) + ", Optimal=" + solutionOptimal(s1));
        System.out.println("Test 2 \"" + s2 + "\": BruteForce=" + solutionBruteForce(s2) + ", Optimal=" + solutionOptimal(s2));
        System.out.println("Test 3 \"" + s3 + "\": BruteForce=" + solutionBruteForce(s3) + ", Optimal=" + solutionOptimal(s3));
        System.out.println("Test 4 \"" + s4 + "\": BruteForce=" + solutionBruteForce(s4) + ", Optimal=" + solutionOptimal(s4));
        System.out.println("Test 5 \"" + s5 + "\": BruteForce=" + solutionBruteForce(s5) + ", Optimal=" + solutionOptimal(s5));
    }
}
