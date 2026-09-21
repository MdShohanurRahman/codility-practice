package Day_07_Linked_List_and_Trees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * Day 7 - Problem 28: Binary Tree Level Order Traversal
 * Question: Given the root of a binary tree, return the level order traversal of its nodes' values (i.e. level by level, left to right).
 * 
 * Target: O(N) Time, O(N) Space.
 */
public class Problem28_BinaryTreeLevelOrderTraversal {

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
    // 1. RECURSIVE DFS APPROACH (Depth-Indexed Pre-Order Traversal)
    // Traverse nodes passing depth index, appending values to depth-corresponding list in result.
    // Time Complexity: O(N)
    // Space Complexity: O(H) recursion stack space
    // ==========================================
    public static List<List<Integer>> levelOrderDFS(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        dfsHelper(root, 0, result);
        return result;
    }

    private static void dfsHelper(TreeNode node, int depth, List<List<Integer>> result) {
        if (node == null) return;

        // If visiting this depth level for the first time, initialize its list
        if (result.size() == depth) {
            result.add(new ArrayList<>());
        }

        result.get(depth).add(node.val);

        dfsHelper(node.left, depth + 1, result);
        dfsHelper(node.right, depth + 1, result);
    }

    // ==========================================
    // 2. OPTIMAL ITERATIVE BFS APPROACH (Queue Level Processing)
    // Process tree level-by-level using Queue snapshot of queue size at each level start.
    // Time Complexity: O(N)
    // Space Complexity: O(W) where W is max width of tree (up to N/2 for full binary tree)
    // ==========================================
    public static List<List<Integer>> levelOrderBFS(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;

        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> currentLevel = new ArrayList<>(levelSize);

            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                currentLevel.add(node.val);

                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }

            result.add(currentLevel);
        }

        return result;
    }

    // Quick Test / Demo
    public static void main(String[] args) {
        // Tree 1: [3, 9, 20, null, null, 15, 7] -> [[3], [9, 20], [15, 7]]
        //        3
        //       / \
        //      9  20
        //        /  \
        //       15   7
        TreeNode root1 = new TreeNode(3,
            new TreeNode(9),
            new TreeNode(20, new TreeNode(15), new TreeNode(7))
        );

        System.out.println("Tree 1 Level Order Traversal:");
        System.out.println("  BFS Iterative: " + levelOrderBFS(root1));
        System.out.println("  DFS Recursive: " + levelOrderDFS(root1));

        // Tree 2: [1] -> [[1]]
        TreeNode root2 = new TreeNode(1);
        System.out.println("\nTree 2 Level Order Traversal:");
        System.out.println("  BFS Iterative: " + levelOrderBFS(root2));

        // Tree 3: Empty tree -> []
        System.out.println("\nTree 3 (Empty) Level Order Traversal:");
        System.out.println("  BFS Iterative: " + levelOrderBFS(null));
    }
}
