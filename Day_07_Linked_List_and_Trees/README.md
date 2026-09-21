# Day 7 --- Linked List & Trees (Interview Study Notes)

> **Focus:** In-Place Pointer Manipulation, Floyd's Cycle Detection (Tortoise and Hare), Tree Recursion (DFS), and BFS Level Order Traversal.  
> **Target Platform:** Codility / MoneyLion Technical Assessment

---

## Table of Contents
1. [Problem 25: Reverse Linked List](#25-reverse-linked-list)
2. [Problem 26: Detect Linked List Cycle](#26-detect-linked-list-cycle)
3. [Problem 27: Binary Tree Maximum Depth](#27-binary-tree-maximum-depth)
4. [Problem 28: Binary Tree Level Order Traversal](#28-binary-tree-level-order-traversal)
5. [Day 7 Summary & Patterns Cheatsheet](#day-7-summary--patterns-cheatsheet)

---

## 25. Reverse Linked List

### Question
Given the head of a singly linked list, reverse the list in-place and return its new head.

- **Example 1:** `head = [1, 2, 3, 4, 5]` $\rightarrow$ Output: `[5, 4, 3, 2, 1]`
- **Example 2:** `head = [1, 2]` $\rightarrow$ Output: `[2, 1]`
- **Example 3:** `head = []` $\rightarrow$ Output: `[]`

---

### 25.1 Brute Force Approach (Value Extraction & List Reconstruction)

#### Approach & Intuition
- Traverse the linked list and collect all node values into an auxiliary `List<Integer>` or stack.
- Create a new linked list by iterating over the collected values in reverse order.
- Return the head of the newly created list.

#### Java Code
```java
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
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N)$
  - *Reasoning:* Two linear passes over the list of $N$ nodes.
- **Space Complexity:** $\mathcal{O}(N)$
  - *Reasoning:* Creates $N$ new nodes and allocates a list of $N$ integers in heap memory.

---

### 25.2 Optimal Approach (Three-Pointer In-Place Reversal)

#### Approach & Intuition
- Reverse node direction links in-place during a single pass using three pointers: `prev`, `curr`, and `nextTemp`.
- Initialize `prev = null` and `curr = head`.
- In each iteration:
  1. Save next node reference: `nextTemp = curr.next`.
  2. Reverse pointer: `curr.next = prev`.
  3. Advance `prev` pointer: `prev = curr`.
  4. Advance `curr` pointer: `curr = nextTemp`.
- When `curr` becomes `null`, `prev` points to the new head of the reversed linked list.

#### Java Code
```java
public static ListNode reverseListOptimal(ListNode head) {
    ListNode prev = null;
    ListNode curr = head;

    while (curr != null) {
        ListNode nextTemp = curr.next; // 1. Save next node
        curr.next = prev;             // 2. Reverse link pointer
        prev = curr;                  // 3. Move prev forward
        curr = nextTemp;              // 4. Move curr forward
    }

    return prev; // New head of reversed list
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N)$
  - *Reasoning:* Single traversal over all $N$ nodes in the linked list.
- **Space Complexity:** $\mathcal{O}(1)$ auxiliary space
  - *Reasoning:* Reverses pointers in-place using constant auxiliary pointer variables.

#### Edge Cases to Mention in Interview
- **Empty List:** `head == null` $\rightarrow$ returns `null` safely.
- **Single Node List:** `head.next == null` $\rightarrow$ loop runs once, `prev` becomes `head`, returns original node safely.
- **Two Nodes List:** `1 -> 2 -> null` $\rightarrow$ reverses to `2 -> 1 -> null`.

---

## 26. Detect Linked List Cycle

### Question
Given the head of a singly linked list, determine if the list contains a cycle.

A cycle exists if there is some node in the list that can be reached again by continuously following the `next` pointer.

- **Example 1:** `head = [3, 2, 0, -4]`, cycle back to node with value `2` $\rightarrow$ Output: `true`
- **Example 2:** `head = [1, 2]`, no cycle $\rightarrow$ Output: `false`
- **Example 3:** `head = [1]`, cycle back to itself $\rightarrow$ Output: `true`

---

### 26.1 Brute Force Approach (HashSet of Node References)

#### Approach & Intuition
- Traverse the linked list node by node.
- Store object references of each visited node in a `HashSet<ListNode>`.
- If a node reference already exists in the set, a cycle has been detected (`return true`).
- If `curr` reaches `null`, the list has a finite end and no cycle (`return false`).

#### Java Code
```java
public static boolean hasCycleBruteForce(ListNode head) {
    if (head == null) return false;

    Set<ListNode> visited = new HashSet<>();
    ListNode curr = head;

    while (curr != null) {
        if (visited.contains(curr)) {
            return true; // Re-visited node reference -> Cycle detected!
        }
        visited.add(curr);
        curr = curr.next;
    }

    return false;
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N)$
  - *Reasoning:* Each node is visited once; HashSet lookup and insert run in $\mathcal{O}(1)$ average time.
- **Space Complexity:** $\mathcal{O}(N)$
  - *Reasoning:* Stores up to $N$ node object references in memory.

---

### 26.2 Optimal Approach (Floyd's Tortoise and Hare Algorithm)

#### Approach & Intuition
- Use two pointers moving at different speeds:
  - `slow` pointer advances **1 step** at a time (`slow = slow.next`).
  - `fast` pointer advances **2 steps** at a time (`fast = fast.next.next`).
- If no cycle exists, `fast` will reach `null` or `fast.next == null`.
- If a cycle exists, `fast` enters the loop first, and `slow` follows. Because `fast` gains 1 node per iteration relative to `slow`, `fast` will eventually overlap with `slow` (`slow == fast`).

#### Java Code
```java
public static boolean hasCycleOptimal(ListNode head) {
    if (head == null || head.next == null) return false;

    ListNode slow = head;
    ListNode fast = head;

    while (fast != null && fast.next != null) {
        slow = slow.next;         // Move slow pointer 1 step
        fast = fast.next.next;    // Move fast pointer 2 steps

        if (slow == fast) {
            return true; // Fast caught up with slow -> Cycle exists!
        }
    }

    return false; // Reached end of list -> No cycle
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N)$
  - *Reasoning:* If no cycle, fast reaches end in $N / 2$ steps. If cycle exists, distance between slow and fast decreases by 1 each step; fast catches slow within $K$ steps (where $K \le N$).
- **Space Complexity:** $\mathcal{O}(1)$ auxiliary space
  - *Reasoning:* Uses only two pointer variables regardless of list length.

#### Edge Cases to Mention in Interview
- **Null / Single Node List without cycle:** `head == null` or `head.next == null` $\rightarrow$ returns `false`.
- **Single Node with Self-Cycle:** `1 -> 1` $\rightarrow$ `slow` and `fast` meet immediately at node `1`, returns `true`.
- **Fast Null Guard:** Must check both `fast != null && fast.next != null` inside loop condition to avoid `NullPointerException`.

---

## 27. Binary Tree Maximum Depth

### Question
Given the root of a binary tree, return its maximum depth.

The maximum depth is the number of nodes along the longest path from the root node down to the farthest leaf node.

- **Example 1:** `root = [3, 9, 20, null, null, 15, 7]` $\rightarrow$ Output: `3`
- **Example 2:** `root = [1, null, 2]` $\rightarrow$ Output: `2`
- **Example 3:** `root = []` $\rightarrow$ Output: `0`

---

### 27.1 Iterative BFS Approach (Queue Level Processing)

#### Approach & Intuition
- Perform a Breadth-First Search (BFS) level-by-level traversal using a `Queue<TreeNode>`.
- Count how many levels exist in the binary tree by snapshotting `levelSize = queue.size()` at the beginning of each level.

#### Java Code
```java
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
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N)$
  - *Reasoning:* Visits each node in the tree exactly once.
- **Space Complexity:** $\mathcal{O}(W)$ where $W$ is the maximum width of the tree (in a full binary tree, $W = \lceil N / 2 \rceil$).

---

### 27.2 Optimal Recursive DFS Approach (Post-Order Traversal)

#### Approach & Intuition
- Use Depth-First Search (DFS) post-order recursion.
- Base Case: If `root == null`, the depth is `0`.
- Recursive Case: The depth of current node is `1 + Math.max(maxDepth(root.left), maxDepth(root.right))`.

#### Java Code
```java
public static int maxDepthOptimal(TreeNode root) {
    if (root == null) {
        return 0;
    }

    int leftDepth = maxDepthOptimal(root.left);
    int rightDepth = maxDepthOptimal(root.right);

    return 1 + Math.max(leftDepth, rightDepth);
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N)$
  - *Reasoning:* Every node is visited once during the recursive post-order traversal.
- **Space Complexity:** $\mathcal{O}(H)$ call stack space
  - *Reasoning:* $H$ is the height of the binary tree. For a balanced tree, $H = \mathcal{O}(\log N)$; for a skewed line tree, $H = \mathcal{O}(N)$.

#### Edge Cases to Mention in Interview
- **Empty Tree:** `root == null` $\rightarrow$ returns `0`.
- **Single Node Tree:** `root.left == null && root.right == null` $\rightarrow$ returns `1`.
- **Completely Skewed Tree:** All left or right children $\rightarrow$ recursion depth equals $N$, mention potential call stack overflow risk for deep trees ($N > 10,000$).

---

## 28. Binary Tree Level Order Traversal

### Question
Given the root of a binary tree, return the level order traversal of its nodes' values (i.e., level by level, left to right).

- **Example 1:** `root = [3, 9, 20, null, null, 15, 7]` $\rightarrow$ Output: `[[3], [9, 20], [15, 7]]`
- **Example 2:** `root = [1]` $\rightarrow$ Output: `[[1]]`
- **Example 3:** `root = []` $\rightarrow$ Output: `[]`

---

### 28.1 Recursive DFS Approach (Depth-Indexed Traversal)

#### Approach & Intuition
- Perform pre-order DFS passing current level index `depth`.
- If `result.size() == depth`, initialize a new list for that depth level.
- Append node value to `result.get(depth)` list and recurse left and right.

#### Java Code
```java
public static List<List<Integer>> levelOrderDFS(TreeNode root) {
    List<List<Integer>> result = new ArrayList<>();
    dfsHelper(root, 0, result);
    return result;
}

private static void dfsHelper(TreeNode node, int depth, List<List<Integer>> result) {
    if (node == null) return;

    if (result.size() == depth) {
        result.add(new ArrayList<>());
    }

    result.get(depth).add(node.val);

    dfsHelper(node.left, depth + 1, result);
    dfsHelper(node.right, depth + 1, result);
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N)$
- **Space Complexity:** $\mathcal{O}(H)$ recursion call stack + $\mathcal{O}(N)$ for result storage.

---

### 28.2 Optimal Iterative BFS Approach (Queue Processing)

#### Approach & Intuition
- Standard BFS using `Queue<TreeNode>`.
- At each iteration of outer loop:
  1. Capture current `levelSize = queue.size()`.
  2. Create a level list `currentLevel` of size `levelSize`.
  3. Loop `levelSize` times: poll node from queue, add value to `currentLevel`, enqueue non-null `left` and `right` children.
  4. Append `currentLevel` to `result`.

#### Java Code
```java
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
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N)$
  - *Reasoning:* Each node is enqueued once and dequeued once.
- **Space Complexity:** $\mathcal{O}(W)$ queue space
  - *Reasoning:* $W$ is maximum width of tree ($\mathcal{O}(N)$ for full binary tree).

#### Edge Cases to Mention in Interview
- **Empty Tree:** Returns empty list `[]`.
- **Single Node Tree:** Returns `[[1]]`.
- **Asymmetric / Unbalanced Tree:** `queue.size()` snapshot guarantees correct separation of depth levels.

---

## Day 7 Summary & Patterns Cheatsheet

| Problem | Brute Force Time | Optimal Time | Optimal Space | Key Pattern / Core Concept |
| :--- | :--- | :--- | :--- | :--- |
| **25. Reverse Linked List** | $\mathcal{O}(N)$ | $\mathcal{O}(N)$ | $\mathcal{O}(1)$ | Three-pointer in-place link reversal (`prev`, `curr`, `nextTemp`) |
| **26. Detect Linked List Cycle** | $\mathcal{O}(N)$ | $\mathcal{O}(N)$ | $\mathcal{O}(1)$ | Floyd's Cycle Finding / Tortoise & Hare (`slow` 1x, `fast` 2x) |
| **27. Binary Tree Max Depth** | $\mathcal{O}(N)$ | $\mathcal{O}(N)$ | $\mathcal{O}(H)$ | Recursive Post-Order DFS (`1 + Math.max(left, right)`) |
| **28. Level Order Traversal** | $\mathcal{O}(N)$ | $\mathcal{O}(N)$ | $\mathcal{O}(W)$ | Iterative BFS with Queue level snapshotting (`levelSize`) |

---

### Key Takeaways for Codility / Technical Interviews

1. **Pointer Reversal Safety:**
   - Always save `curr.next` to a temporary variable *before* overwriting `curr.next = prev`, or you will break the link and lose access to the remainder of the linked list.
2. **Floyd's Fast-Pointer Guards:**
   - In any two-pointer fast/slow algorithm, always guard against `NullPointerException` by checking `while (fast != null && fast.next != null)`.
3. **Tree Recursion Stack vs Queue Profile:**
   - Recursive DFS requires $\mathcal{O}(H)$ call stack space (ideal for deep/narrow trees).
   - Iterative BFS requires $\mathcal{O}(W)$ queue memory (ideal for level-wise operations, but can consume $\mathcal{O}(N/2)$ memory for wide balanced trees).
4. **Level Order Queue Snapshotting:**
   - Taking `int levelSize = queue.size()` at the start of each outer BFS loop is the standard pattern to cleanly partition nodes by level without needing explicit depth-wrapper objects.
