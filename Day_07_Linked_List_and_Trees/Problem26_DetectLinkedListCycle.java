package Day_07_Linked_List_and_Trees;

import java.util.HashSet;
import java.util.Set;

/**
 * Day 7 - Problem 26: Detect Linked List Cycle
 * Question: Given head of a singly linked list, determine if the list has a cycle in it.
 * 
 * Target: O(N) Time, O(1) Space.
 */
public class Problem26_DetectLinkedListCycle {

    // Definition for singly-linked list node
    public static class ListNode {
        public int val;
        public ListNode next;

        public ListNode(int val) {
            this.val = val;
        }

        public ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    // ==========================================
    // 1. BRUTE FORCE APPROACH (HashSet of Visited Nodes)
    // Traverse list and store node references in a Set.
    // If a node is encountered that is already in the set, a cycle exists.
    // Time Complexity: O(N)
    // Space Complexity: O(N) auxiliary space
    // ==========================================
    public static boolean hasCycleBruteForce(ListNode head) {
        if (head == null) return false;

        Set<ListNode> visited = new HashSet<>();
        ListNode curr = head;

        while (curr != null) {
            if (visited.contains(curr)) {
                return true; // Cycle detected
            }
            visited.add(curr);
            curr = curr.next;
        }

        return false;
    }

    // ==========================================
    // 2. OPTIMAL APPROACH (Floyd's Tortoise and Hare / Two Pointers)
    // Use slow pointer moving 1 step and fast pointer moving 2 steps.
    // If a cycle exists, fast pointer will eventually catch up and equal slow pointer.
    // Time Complexity: O(N)
    // Space Complexity: O(1) auxiliary space
    // ==========================================
    public static boolean hasCycleOptimal(ListNode head) {
        if (head == null || head.next == null) return false;

        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;         // Move slow pointer 1 step
            fast = fast.next.next;    // Move fast pointer 2 steps

            if (slow == fast) {
                return true; // Fast pointer met slow pointer -> Cycle exists!
            }
        }

        return false; // Reached end of list -> No cycle
    }

    // Quick Test / Demo
    public static void main(String[] args) {
        // Test Case 1: List with cycle 3 -> 2 -> 0 -> -4 -> 2...
        ListNode n4 = new ListNode(-4);
        ListNode n3 = new ListNode(0, n4);
        ListNode n2 = new ListNode(2, n3);
        ListNode n1 = new ListNode(3, n2);
        n4.next = n2; // Creates cycle back to n2

        System.out.println("Test 1 (Cycle at pos 1):");
        System.out.println("  BruteForce: " + hasCycleBruteForce(n1));
        System.out.println("  Optimal:    " + hasCycleOptimal(n1));

        // Test Case 2: List without cycle 1 -> 2 -> null
        ListNode head2 = new ListNode(1, new ListNode(2));
        System.out.println("\nTest 2 (No Cycle):");
        System.out.println("  BruteForce: " + hasCycleBruteForce(head2));
        System.out.println("  Optimal:    " + hasCycleOptimal(head2));

        // Test Case 3: Single node with cycle 1 -> 1...
        ListNode head3 = new ListNode(1);
        head3.next = head3;
        System.out.println("\nTest 3 (Single Node Cycle):");
        System.out.println("  BruteForce: " + hasCycleBruteForce(head3));
        System.out.println("  Optimal:    " + hasCycleOptimal(head3));
    }
}
