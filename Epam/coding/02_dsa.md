# EPAM Interview Preparation — Part 2: Data Structures & Algorithms (15 Problems)

> **Strategy for Coding Interviews:**
> 1. **Understand & Clarify**: Restate the problem, check edge cases (null, empty, negative, duplicates).
> 2. **Propose Brute Force First**: Show that you can solve the problem baseline without getting stuck.
> 3. **Identify the Bottleneck**: Explain *why* Brute Force is slow ($O(N^2)$ time, nested loops, repeated scans).
> 4. **Propose & Implement Optimal Solution**: Use proper DSA patterns (HashMap, Two Pointers, Sliding Window, Kadane's, BFS/DFS, Stack, Binary Search).
> 5. **Analyze Complexity**: State Time & Space complexity for both approaches.

---

## 01. Two Sum

### Problem Statement
Given an array of integers `nums` and an integer `target`, return indices of the two numbers such that they add up to `target`.

- **Input:** `nums = [2, 7, 11, 15], target = 9` -> **Output:** `[0, 1]`

### Intuition & Logic
- **Brute Force Idea:** Try every pair of numbers using two nested loops. Check if `nums[i] + nums[j] == target`.
- **Optimal Idea:** When looking at `nums[i]`, we are searching for `complement = target - nums[i]`. Instead of scanning the rest of the array ($O(N)$ lookup), we can store numbers we've seen in a **HashMap** for $O(1)$ constant time lookup.

### 1. Brute Force Approach
```java
public static int[] twoSumBrute(int[] nums, int target) {
    for (int i = 0; i < nums.length; i++) {
        for (int j = i + 1; j < nums.length; j++) {
            if (nums[i] + nums[j] == target) {
                return new int[] { i, j };
            }
        }
    }
    return new int[] {};
}
```
- **Time Complexity:** $O(N^2)$ — Two nested loops.
- **Space Complexity:** $O(1)$ — No extra space used.
- **Bottleneck:** Repeatedly searching for `target - nums[i]` by scanning the array.

### 2. Optimal Approach (HashMap)
```java
public static int[] twoSumOptimal(int[] nums, int target) {
    // Map stores: value -> index
    Map<Integer, Integer> map = new HashMap<>();
    
    for (int i = 0; i < nums.length; i++) {
        int complement = target - nums[i];
        if (map.containsKey(complement)) {
            return new int[] { map.get(complement), i };
        }
        map.put(nums[i], i);
    }
    return new int[] {};
}
```
- **Time Complexity:** $O(N)$ — Single pass through the array.
- **Space Complexity:** $O(N)$ — Storing elements in the HashMap.

### Interview Pitch
> *"First, I can check all pairs using two loops in $O(N^2)$ time. But to optimize, notice that for each element `x`, we need `target - x`. Using a HashMap, we can store each element's index as we iterate and check if its complement exists in $O(1)$ average time, reducing overall runtime to $O(N)$ with $O(N)$ space."*

---

## 02. Valid Anagram

### Problem Statement
Given two strings `s` and `t`, return `true` if `t` is an anagram of `s`, and `false` otherwise.

- **Input:** `s = "listen", t = "silent"` -> **Output:** `true`

### 1. Brute Force Approach (Sorting)
Convert both strings to character arrays, sort them, and compare equality.
```java
public static boolean isAnagramBrute(String s, String t) {
    if (s.length() != t.length()) return false;
    
    char[] sArr = s.toCharArray();
    char[] tArr = t.toCharArray();
    Arrays.sort(sArr);
    Arrays.sort(tArr);
    
    return Arrays.equals(sArr, tArr);
}
```
- **Time Complexity:** $O(N \log N)$ due to sorting.
- **Space Complexity:** $O(N)$ to store character arrays.

### 2. Optimal Approach (Frequency Array / HashMap)
Count character occurrences using a fixed-size frequency array of size 26 (assuming lower-case English letters).
```java
public static boolean isAnagramOptimal(String s, String t) {
    if (s.length() != t.length()) return false;
    
    int[] count = new int[26];
    for (int i = 0; i < s.length(); i++) {
        count[s.charAt(i) - 'a']++;
        count[t.charAt(i) - 'a']--;
    }
    
    for (int c : count) {
        if (c != 0) return false;
    }
    return true;
}
```
- **Time Complexity:** $O(N)$ single pass.
- **Space Complexity:** $O(1)$ constant space (26 integers).

---

## 03. First Unique Character in a String

### Problem Statement
Find the first non-repeating character in a string and return its index. If it does not exist, return `-1`.

- **Input:** `s = "leetcode"` -> **Output:** `0` ('l')
- **Input:** `s = "loveleetcode"` -> **Output:** `2` ('v')

### 1. Brute Force Approach
For each character, check if it appears anywhere else in the string.
```java
public static int firstUniqCharBrute(String s) {
    for (int i = 0; i < s.length(); i++) {
        boolean isUnique = true;
        for (int j = 0; j < s.length(); j++) {
            if (i != j && s.charAt(i) == s.charAt(j)) {
                isUnique = false;
                break;
            }
        }
        if (isUnique) return i;
    }
    return -1;
}
```
- **Time Complexity:** $O(N^2)$ — Double loop checking every character.
- **Space Complexity:** $O(1)$.

### 2. Optimal Approach (Two Pass Frequency Count)
Pass 1: Build frequency map/array. Pass 2: Find the first character with frequency = 1.
```java
public static int firstUniqCharOptimal(String s) {
    int[] freq = new int[26];
    for (int i = 0; i < s.length(); i++) {
        freq[s.charAt(i) - 'a']++;
    }
    for (int i = 0; i < s.length(); i++) {
        if (freq[s.charAt(i) - 'a'] == 1) {
            return i;
        }
    }
    return -1;
}
```
- **Time Complexity:** $O(N)$ — Two linear scans.
- **Space Complexity:** $O(1)$ — Array of size 26.

---

## 04. Valid Palindrome

### Problem Statement
Determine if a string is a palindrome, considering only alphanumeric characters and ignoring cases.

- **Input:** `"A man, a plan, a canal: Panama"` -> **Output:** `true`

### 1. Brute Force Approach
Filter alphanumeric characters, convert to lowercase, reverse the string, and compare with original.
```java
public static boolean isPalindromeBrute(String s) {
    StringBuilder filtered = new StringBuilder();
    for (char c : s.toCharArray()) {
        if (Character.isLetterOrDigit(c)) {
            filtered.append(Character.toLowerCase(c));
        }
    }
    String orig = filtered.toString();
    String rev = filtered.reverse().toString();
    return orig.equals(rev);
}
```
- **Time Complexity:** $O(N)$.
- **Space Complexity:** $O(N)$ extra memory for new filtered/reversed strings.

### 2. Optimal Approach (Two Pointers - In-Place)
Use two pointers (`left` and `right`) moving towards the center, skipping non-alphanumeric characters.
```java
public static boolean isPalindromeOptimal(String s) {
    int left = 0, right = s.length() - 1;
    
    while (left < right) {
        while (left < right && !Character.isLetterOrDigit(s.charAt(left))) left++;
        while (left < right && !Character.isLetterOrDigit(s.charAt(right))) right--;
        
        if (Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right))) {
            return false;
        }
        left++;
        right--;
    }
    return true;
}
```
- **Time Complexity:** $O(N)$ — Single pass across pointers.
- **Space Complexity:** $O(1)$ — Zero extra memory allocation.

---

## 05. Move Zeroes

### Problem Statement
Given an integer array `nums`, move all `0`s to the end of it while maintaining the relative order of the non-zero elements in-place.

- **Input:** `[0, 1, 0, 3, 12]` -> **Output:** `[1, 3, 12, 0, 0]`

### 1. Brute Force Approach (Extra Memory)
Copy non-zero elements into a temporary array, then fill remaining slots with zeros.
```java
public static void moveZeroesBrute(int[] nums) {
    int[] temp = new int[nums.length];
    int tIdx = 0;
    for (int num : nums) {
        if (num != 0) temp[tIdx++] = num;
    }
    System.arraycopy(temp, 0, nums, 0, nums.length);
}
```
- **Time Complexity:** $O(N)$.
- **Space Complexity:** $O(N)$ extra space (Violates in-place requirement).

### 2. Optimal Approach (Two Pointers / Reader & Writer)
Maintain a `lastNonZeroFoundAt` pointer. Iterate through array; whenever a non-zero is found, swap it to `lastNonZeroFoundAt` index.
```java
public static void moveZeroesOptimal(int[] nums) {
    int lastNonZeroAt = 0;
    for (int i = 0; i < nums.length; i++) {
        if (nums[i] != 0) {
            int temp = nums[lastNonZeroAt];
            nums[lastNonZeroAt] = nums[i];
            nums[i] = temp;
            lastNonZeroAt++;
        }
    }
}
```
- **Time Complexity:** $O(N)$.
- **Space Complexity:** $O(1)$ — In-place.

---

## 06. Best Time to Buy and Sell Stock

### Problem Statement
Find the maximum profit you can achieve from buying stock on one day and selling on a future day.

- **Input:** `prices = [7, 1, 5, 3, 6, 4]` -> **Output:** `5` (Buy at 1, sell at 6)

### 1. Brute Force Approach
Try all possible buying days `i` and selling days `j` where `j > i`.
```java
public static int maxProfitBrute(int[] prices) {
    int maxProfit = 0;
    for (int i = 0; i < prices.length; i++) {
        for (int j = i + 1; j < prices.length; j++) {
            int profit = prices[j] - prices[i];
            maxProfit = Math.max(maxProfit, profit);
        }
    }
    return maxProfit;
}
```
- **Time Complexity:** $O(N^2)$.
- **Space Complexity:** $O(1)$.

### 2. Optimal Approach (Single Pass Min-Tracking)
Keep track of the minimum buy price seen so far, and calculate potential profit at each day.
```java
public static int maxProfitOptimal(int[] prices) {
    int minPrice = Integer.MAX_VALUE;
    int maxProfit = 0;
    
    for (int price : prices) {
        if (price < minPrice) {
            minPrice = price; // Found cheaper buying day
        } else if (price - minPrice > maxProfit) {
            maxProfit = price - minPrice; // Found higher profit
        }
    }
    return maxProfit;
}
```
- **Time Complexity:** $O(N)$ — Single scan.
- **Space Complexity:** $O(1)$.

---

## 07. Merge Two Sorted Arrays

### Problem Statement
Merge two sorted arrays `A` and `B` into a single sorted array.

- **Input:** `A = [1, 3, 5], B = [2, 4, 6]` -> **Output:** `[1, 2, 3, 4, 5, 6]`

### 1. Brute Force Approach
Combine both arrays into one, then sort.
```java
public static int[] mergeBrute(int[] A, int[] B) {
    int[] res = new int[A.length + B.length];
    System.arraycopy(A, 0, res, 0, A.length);
    System.arraycopy(B, 0, res, A.length, B.length);
    Arrays.sort(res);
    return res;
}
```
- **Time Complexity:** $O((N+M) \log (N+M))$ due to sorting.
- **Space Complexity:** $O(N+M)$.

### 2. Optimal Approach (Two Pointers)
Compare elements from both arrays using two pointers and pick the smaller one.
```java
public static int[] mergeOptimal(int[] A, int[] B) {
    int[] res = new int[A.length + B.length];
    int i = 0, j = 0, k = 0;
    
    while (i < A.length && j < B.length) {
        if (A[i] <= B[j]) res[k++] = A[i++];
        else res[k++] = B[j++];
    }
    while (i < A.length) res[k++] = A[i++];
    while (j < B.length) res[k++] = B[j++];
    
    return res;
}
```
- **Time Complexity:** $O(N + M)$ linear time.
- **Space Complexity:** $O(N + M)$.

---

## 08. Valid Parentheses

### Problem Statement
Determine if input string containing brackets `'()', '{}', '[]'` is valid (closed in correct order).

- **Input:** `"{[()]}"` -> **Output:** `true`
- **Input:** `"(]"` -> **Output:** `false`

### 1. Brute Force Approach
Repeatedly replace string occurrences of `"()"`, `"{}"`, `"[]"` with empty string until no matches remain.
```java
public static boolean isValidBrute(String s) {
    while (s.contains("()") || s.contains("{}") || s.contains("[]")) {
        s = s.replace("()", "").replace("{}", "").replace("[]", "");
    }
    return s.isEmpty();
}
```
- **Time Complexity:** $O(N^2)$ due to multiple string allocations and regex replacements.
- **Space Complexity:** $O(N^2)$ string overhead.

### 2. Optimal Approach (Stack)
Push matching closing brackets onto a Stack when encountering opening brackets. Pop and verify when encountering closing brackets.
```java
public static boolean isValidOptimal(String s) {
    Stack<Character> stack = new Stack<>();
    for (char c : s.toCharArray()) {
        if (c == '(') stack.push(')');
        else if (c == '{') stack.push('}');
        else if (c == '[') stack.push(']');
        else if (stack.isEmpty() || stack.pop() != c) return false;
    }
    return stack.isEmpty();
}
```
- **Time Complexity:** $O(N)$ single pass.
- **Space Complexity:** $O(N)$ stack memory.

---

## 09. Binary Search

### Problem Statement
Given a sorted array of integers `nums` and a `target`, return target index if found, else `-1`.

- **Input:** `nums = [1, 3, 5, 7, 9], target = 7` -> **Output:** `3`

### 1. Brute Force Approach (Linear Search)
Scan elements one by one.
```java
public static int searchLinear(int[] nums, int target) {
    for (int i = 0; i < nums.length; i++) {
        if (nums[i] == target) return i;
    }
    return -1;
}
```
- **Time Complexity:** $O(N)$.
- **Space Complexity:** $O(1)$.

### 2. Optimal Approach (Binary Search)
Divide search space in half at each step.
```java
public static int searchBinary(int[] nums, int target) {
    int left = 0, right = nums.length - 1;
    while (left <= right) {
        int mid = left + (right - left) / 2; // Prevents integer overflow
        if (nums[mid] == target) return mid;
        if (nums[mid] < target) left = mid + 1;
        else right = mid - 1;
    }
    return -1;
}
```
- **Time Complexity:** $O(\log N)$.
- **Space Complexity:** $O(1)$.

---

## 10. Longest Substring Without Repeating Characters

### Problem Statement
Find the length of the longest substring without repeating characters.

- **Input:** `"abcabcbb"` -> **Output:** `3` ("abc")

### 1. Brute Force Approach
Generate all substrings, check if each contains unique characters using a Set.
```java
public static int lengthOfLongestSubstringBrute(String s) {
    int maxLen = 0;
    for (int i = 0; i < s.length(); i++) {
        for (int j = i; j < s.length(); j++) {
            if (allUnique(s, i, j)) {
                maxLen = Math.max(maxLen, j - i + 1);
            }
        }
    }
    return maxLen;
}
private static boolean allUnique(String s, int start, int end) {
    Set<Character> set = new HashSet<>();
    for (int k = start; k <= end; k++) {
        if (!set.add(s.charAt(k))) return false;
    }
    return true;
}
```
- **Time Complexity:** $O(N^3)$ — Generating $O(N^2)$ substrings, checking each in $O(N)$.
- **Space Complexity:** $O(\min(N, M))$ for set.

### 2. Optimal Approach (Sliding Window with HashMap)
Maintain a window `[left, right]`. Use HashMap to store `char -> last_seen_index`. If character seen inside current window, jump `left` pointer to `map.get(c) + 1`.
```java
public static int lengthOfLongestSubstringOptimal(String s) {
    Map<Character, Integer> map = new HashMap<>();
    int maxLen = 0, left = 0;
    
    for (int right = 0; right < s.length(); right++) {
        char ch = s.charAt(right);
        if (map.containsKey(ch)) {
            // Jump left pointer past duplicate index
            left = Math.max(left, map.get(ch) + 1);
        }
        map.put(ch, right);
        maxLen = Math.max(maxLen, right - left + 1);
    }
    return maxLen;
}
```
- **Time Complexity:** $O(N)$ single pass.
- **Space Complexity:** $O(\min(N, M))$ character set size.

---

## 11. Maximum Subarray (Kadane's Algorithm)

### Problem Statement
Find the contiguous subarray with the largest sum and return its sum.

- **Input:** `[-2, 1, -3, 4, -1, 2, 1, -5, 4]` -> **Output:** `6` (Subarray `[4, -1, 2, 1]`)

### 1. Brute Force Approach
Evaluate sums of all possible subarrays.
```java
public static int maxSubArrayBrute(int[] nums) {
    int maxSum = Integer.MIN_VALUE;
    for (int i = 0; i < nums.length; i++) {
        int currentSum = 0;
        for (int j = i; j < nums.length; j++) {
            currentSum += nums[j];
            maxSum = Math.max(maxSum, currentSum);
        }
    }
    return maxSum;
}
```
- **Time Complexity:** $O(N^2)$.
- **Space Complexity:** $O(1)$.

### 2. Optimal Approach (Kadane's Algorithm)
If current running sum becomes negative, reset it to 0 (because adding a negative sum to next elements will only decrease their potential sum).
```java
public static int maxSubArrayOptimal(int[] nums) {
    int maxSoFar = nums[0];
    int currentSum = nums[0];
    
    for (int i = 1; i < nums.length; i++) {
        // Either join existing subarray sum or start fresh from nums[i]
        currentSum = Math.max(nums[i], currentSum + nums[i]);
        maxSoFar = Math.max(maxSoFar, currentSum);
    }
    return maxSoFar;
}
```
- **Time Complexity:** $O(N)$ single pass.
- **Space Complexity:** $O(1)$.

---

## 12. Merge Intervals

### Problem Statement
Merge all overlapping intervals.

- **Input:** `[[1,3],[2,6],[8,10],[15,18]]` -> **Output:** `[[1,6],[8,10],[15,18]]`

### Intuition & Optimal Approach
1. Sort intervals by start time $O(N \log N)$.
2. Iterate through intervals. If `current.start <= previous.end`, merge them by setting `previous.end = Math.max(previous.end, current.end)`.
```java
public static int[][] mergeIntervals(int[][] intervals) {
    if (intervals.length <= 1) return intervals;
    
    // Step 1: Sort by start time
    Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
    
    List<int[]> merged = new ArrayList<>();
    int[] currentInterval = intervals[0];
    merged.add(currentInterval);
    
    for (int[] interval : intervals) {
        int currentEnd = currentInterval[1];
        int nextStart = interval[0];
        int nextEnd = interval[1];
        
        if (nextStart <= currentEnd) {
            // Overlapping intervals, merge them
            currentInterval[1] = Math.max(currentEnd, nextEnd);
        } else {
            // Non-overlapping, add new interval
            currentInterval = interval;
            merged.add(currentInterval);
        }
    }
    return merged.toArray(new int[merged.size()][]);
}
```
- **Time Complexity:** $O(N \log N)$ due to sorting.
- **Space Complexity:** $O(N)$ for merged list output.

---

## 13. Reverse Linked List

### Problem Statement
Reverse a singly linked list. `1 -> 2 -> 3 -> 4` to `4 -> 3 -> 2 -> 1`.

### 1. Brute Force Approach (Using Stack/List)
Store node values in an external list or Stack, then overwrite values in reverse order.
```java
// O(N) Time, O(N) Space
public ListNode reverseListBrute(ListNode head) {
    Stack<Integer> stack = new Stack<>();
    ListNode curr = head;
    while (curr != null) {
        stack.push(curr.val);
        curr = curr.next;
    }
    curr = head;
    while (curr != null) {
        curr.val = stack.pop();
        curr = curr.next;
    }
    return head;
}
```

### 2. Optimal Approach (Iterative Pointer Reversal)
Maintain `prev`, `curr`, and `next` pointers. Change `curr.next = prev` in-place.
```java
public ListNode reverseListOptimal(ListNode head) {
    ListNode prev = null;
    ListNode curr = head;
    
    while (curr != null) {
        ListNode nextTemp = curr.next; // Store next node
        curr.next = prev;              // Reverse pointer
        prev = curr;                   // Move prev forward
        curr = nextTemp;               // Move curr forward
    }
    return prev; // New head
}
```
- **Time Complexity:** $O(N)$ single pass.
- **Space Complexity:** $O(1)$ in-place reversal.

---

## 14. Binary Tree Level Order Traversal (BFS)

### Problem Statement
Return level order traversal (breadth-first search) of a binary tree's node values.

### Optimal Approach (Queue BFS)
```java
public List<List<Integer>> levelOrder(TreeNode root) {
    List<List<Integer>> result = new ArrayList<>();
    if (root == null) return result;
    
    Queue<TreeNode> queue = new LinkedList<>();
    queue.offer(root);
    
    while (!queue.isEmpty()) {
        int levelSize = queue.size();
        List<Integer> currentLevel = new ArrayList<>();
        
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
- **Time Complexity:** $O(N)$ visiting every node once.
- **Space Complexity:** $O(W)$ max queue size where $W$ is tree max width ($O(N)$ worst case).

---

## 15. Number of Islands (Grid DFS / BFS)

### Problem Statement
Given an `m x n` 2D binary grid (`'1'` = land, `'0'` = water), return the number of islands. An island is surrounded by water and formed by connecting adjacent lands horizontally or vertically.

### Optimal Approach (Depth-First Search - Sink Island)
Iterate through every cell. When encountering `'1'`, increment island count and run DFS to sink all connected land (`'1'` -> `'0'`).
```java
public int numIslands(char[][] grid) {
    if (grid == null || grid.length == 0) return 0;
    
    int numIslands = 0;
    int rows = grid.length;
    int cols = grid[0].length;
    
    for (int r = 0; r < rows; r++) {
        for (int c = 0; c < cols; c++) {
            if (grid[r][c] == '1') {
                numIslands++;
                dfs(grid, r, c);
            }
        }
    }
    return numIslands;
}

private void dfs(char[][] grid, int r, int c) {
    int rows = grid.length;
    int cols = grid[0].length;
    
    // Boundary check + water check
    if (r < 0 || c < 0 || r >= rows || c >= cols || grid[r][c] == '0') {
        return;
    }
    
    // Mark visited by sinking land to '0'
    grid[r][c] = '0';
    
    // Explore 4 directions
    dfs(grid, r + 1, c);
    dfs(grid, r - 1, c);
    dfs(grid, r, c + 1);
    dfs(grid, r, c - 1);
}
```
- **Time Complexity:** $O(M \times N)$ traversing grid once.
- **Space Complexity:** $O(M \times N)$ call stack depth in worst case (all land).
