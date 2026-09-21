package Day_06_Stack_and_Queue;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Day 6 - Problem 21: Valid Parentheses
 * Question: Determine whether a string containing '()[]{}' is correctly balanced.
 * 
 * Target: O(N) Time, O(N) Space.
 */
public class Problem21_ValidParentheses {

    // ==========================================
    // 1. BRUTE FORCE APPROACH (String Replacement)
    // Repeatedly replace "()", "[]", "{}" with empty string "".
    // If string becomes empty, it's valid.
    // Time Complexity: O(N^2)
    // Space Complexity: O(N^2) due to repeated string allocations
    // ==========================================
    public static boolean isValidBruteForce(String s) {
        if (s == null) return false;

        int prevLength;
        do {
            prevLength = s.length();
            s = s.replace("()", "")
                 .replace("[]", "")
                 .replace("{}", "");
        } while (s.length() < prevLength);

        return s.isEmpty();
    }

    // ==========================================
    // 2. OPTIMAL APPROACH (Stack Data Structure)
    // Push expected closing brackets or push open brackets and check match.
    // Time Complexity: O(N)
    // Space Complexity: O(N)
    // ==========================================
    public static boolean isValidOptimal(String s) {
        if (s == null) return false;
        if (s.length() % 2 != 0) return false; // Odd length string cannot be balanced

        Deque<Character> stack = new ArrayDeque<>();

        for (char c : s.toCharArray()) {
            if (c == '(') {
                stack.push(')');
            } else if (c == '[') {
                stack.push(']');
            } else if (c == '{') {
                stack.push('}');
            } else {
                // c is a closing bracket
                if (stack.isEmpty() || stack.pop() != c) {
                    return false;
                }
            }
        }

        return stack.isEmpty();
    }

    // Quick Test / Demo
    public static void main(String[] args) {
        String test1 = "()[]{}";    // true
        String test2 = "([{}])";    // true
        String test3 = "(]";        // false
        String test4 = "([)]";      // false
        String test5 = "{[]}";      // true
        String test6 = "(((";       // false

        System.out.println("Test 1 \"" + test1 + "\": BruteForce=" + isValidBruteForce(test1) + ", Optimal=" + isValidOptimal(test1));
        System.out.println("Test 2 \"" + test2 + "\": BruteForce=" + isValidBruteForce(test2) + ", Optimal=" + isValidOptimal(test2));
        System.out.println("Test 3 \"" + test3 + "\": BruteForce=" + isValidBruteForce(test3) + ", Optimal=" + isValidOptimal(test3));
        System.out.println("Test 4 \"" + test4 + "\": BruteForce=" + isValidBruteForce(test4) + ", Optimal=" + isValidOptimal(test4));
        System.out.println("Test 5 \"" + test5 + "\": BruteForce=" + isValidBruteForce(test5) + ", Optimal=" + isValidOptimal(test5));
        System.out.println("Test 6 \"" + test6 + "\": BruteForce=" + isValidBruteForce(test6) + ", Optimal=" + isValidOptimal(test6));
    }
}
