package Day_07_Linked_List_and_Trees;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Day 7 - Problem 27: Binary Tree Maximum Depth
 * Question: Given root of a binary tree, find its maximum depth (number of nodes along longest path from root to leaf).
 * 
 * Target: O(N) Time, O(H) Call Stack Space.
 */
public class Problem27_BinaryTreeMaximumDepth {

    // Definition for a binary tree node
    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int val) {
            this.val = val;
        }

        public TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    // ==========================================
    // 1. ITERATIVE BFS APPROACH (Level-by-Level Queue Traversal)
    // Process tree level by level using Queue, incrementing depth per level.
    // Time Complexity: O(N)
    // Space Complexity: O(W) where W is maximum width of tree
    // ==========================================
    public static int maxDepthBFS(TreeNode root) {
        if (root == null) return 0;

        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        int depth = 0;

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            depth++;

            for (int i = 0; i < levelSize; i++) {
                TreeNode current = queue.poll();
                if (current.left != null) queue.offer(current.left);
                if (current.right != null) queue.offer(current.right);
            }
        }

        return depth;
    }

    // ==========================================
    // 2. OPTIMAL RECURSIVE DFS APPROACH (Post-Order Traversal)
    // Recursively calculate depth of left and right subtrees, return 1 + max(left, right).
    // Time Complexity: O(N)
    // Space Complexity: O(H) call stack space where H is height of tree
    // ==========================================
    public static int maxDepthOptimal(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int leftDepth = maxDepthOptimal(root.left);
        int rightDepth = maxDepthOptimal(root.right);

        return 1 + Math.max(leftDepth, rightDepth);
    }

    // Quick Test / Demo
    public static void main(String[] args) {
        // Tree 1: [3, 9, 20, null, null, 15, 7] -> Depth: 3
        //        3
        //       / \
        //      9  20
        //        /  \
        //       15   7
        TreeNode root1 = new TreeNode(3,
            new TreeNode(9),
            new TreeNode(20, new TreeNode(15), new TreeNode(7))
        );

        System.out.println("Tree 1 Max Depth:");
        System.out.println("  BFS Iterative: " + maxDepthBFS(root1));
        System.out.println("  DFS Optimal:   " + maxDepthOptimal(root1));

        // Tree 2: [1, null, 2] -> Depth: 2 (Skewed tree)
        TreeNode root2 = new TreeNode(1, null, new TreeNode(2));
        System.out.println("\nTree 2 (Skewed) Max Depth:");
        System.out.println("  DFS Optimal:   " + maxDepthOptimal(root2));

        // Tree 3: Empty tree
        System.out.println("\nTree 3 (Empty) Max Depth:");
        System.out.println("  DFS Optimal:   " + maxDepthOptimal(null));
    }
}
