package Day_07_Linked_List_and_Trees;

import java.util.ArrayList;
import java.util.List;

/**
 * Day 7 - Problem 25: Reverse Linked List
 * Question: Given the head of a singly linked list, reverse the list and return its new head.
 * 
 * Target: O(N) Time, O(1) Extra Space.
 */
public class Problem25_ReverseLinkedList {

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
    // 1. BRUTE FORCE APPROACH (Extra Space / Value List)
    // Extract node values into a List, reverse values, and rebuild list.
    // Time Complexity: O(N)
    // Space Complexity: O(N) extra auxiliary memory
    // ==========================================
    public static ListNode reverseListBruteForce(ListNode head) {
        if (head == null || head.next == null) return head;

        List<Integer> values = new ArrayList<>();
        ListNode curr = head;
        while (curr != null) {
            values.add(curr.val);
            curr = curr.next;
        }

        ListNode dummy = new ListNode(0);
        curr = dummy;
        for (int i = values.size() - 1; i >= 0; i--) {
            curr.next = new ListNode(values.get(i));
            curr = curr.next;
        }

        return dummy.next;
    }

    // ==========================================
    // 2. OPTIMAL APPROACH (Three-Pointer In-Place Reversal)
    // Use prev, curr, and next pointers to flip pointers in a single pass.
    // Time Complexity: O(N)
    // Space Complexity: O(1) auxiliary space
    // ==========================================
    public static ListNode reverseListOptimal(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;

        while (curr != null) {
            ListNode nextTemp = curr.next; // Save next node
            curr.next = prev;             // Reverse link pointer
            prev = curr;                  // Advance prev pointer
            curr = nextTemp;              // Advance curr pointer
        }

        return prev; // New head of reversed list
    }

    // Utility helper to print list
    public static String printList(ListNode head) {
        StringBuilder sb = new StringBuilder();
        ListNode curr = head;
        while (curr != null) {
            sb.append(curr.val);
            if (curr.next != null) sb.append(" -> ");
            curr = curr.next;
        }
        return sb.length() == 0 ? "null" : sb.toString();
    }

    // Utility helper to create list from array
    public static ListNode createList(int[] arr) {
        if (arr == null || arr.length == 0) return null;
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        for (int val : arr) {
            curr.next = new ListNode(val);
            curr = curr.next;
        }
        return dummy.next;
    }

    // Quick Test / Demo
    public static void main(String[] args) {
        ListNode list1 = createList(new int[]{1, 2, 3, 4, 5});
        System.out.println("Original List 1: " + printList(list1));
        System.out.println("BruteForce Reversed: " + printList(reverseListBruteForce(createList(new int[]{1, 2, 3, 4, 5}))));
        System.out.println("Optimal Reversed:    " + printList(reverseListOptimal(list1)));

        ListNode list2 = createList(new int[]{1, 2});
        System.out.println("\nOriginal List 2: " + printList(list2));
        System.out.println("Optimal Reversed:    " + printList(reverseListOptimal(list2)));

        ListNode list3 = createList(new int[]{});
        System.out.println("\nOriginal List 3: " + printList(list3));
        System.out.println("Optimal Reversed:    " + printList(reverseListOptimal(list3)));
    }
}
