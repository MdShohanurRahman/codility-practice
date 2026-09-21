# Day 2 --- Strings (Interview Study Notes)

> **Focus:** Frequency Array / Counting Table, String Immutability & `StringBuilder`, Two-Pass Traversal, Sorting vs Counting.
> **Target Platform:** Codility / MoneyLion Technical Assessment

---

## Table of Contents
1. [Problem 5: First Unique Character](#5-first-unique-character)
2. [Problem 6: Valid Anagram](#6-valid-anagram)
3. [Problem 7: Character Frequency](#7-character-frequency)
4. [Problem 8: String Compression](#8-string-compression)
5. [Day 2 Summary & Patterns Cheatsheet](#day-2-summary--patterns-cheatsheet)

---

## 5. First Unique Character

### Question
Given a string `s`, find the first character that occurs exactly once in it and return its index (0-indexed). If no unique character exists, return `-1`.

- **Example 1:** `s = "leetcode"` $\rightarrow$ Output: `0` (Character `'l'` appears once at index 0)
- **Example 2:** `s = "loveleetcode"` $\rightarrow$ Output: `2` (Character `'v'` appears once at index 2)
- **Example 3:** `s = "aabb"` $\rightarrow$ Output: `-1`

---

### 5.1 Brute Force Approach (Nested Loops)

#### Approach & Intuition
- For every character at index `i`, loop through the entire string to count how many times `s.charAt(i)` appears.
- As soon as we find a character with total count `1`, return `i`.

#### Java Code
```java
public static int firstUniqCharBruteForce(String s) {
    if (s == null || s.isEmpty()) return -1;
    int n = s.length();
    for (int i = 0; i < n; i++) {
        boolean isUnique = true;
        for (int j = 0; j < n; j++) {
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

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N^2)$
  - *Reasoning:* For each of the $N$ characters, we iterate through the remaining $N-1$ characters.
- **Space Complexity:** $\mathcal{O}(1)$

---

### 5.2 Optimal Approach (Frequency Array - Two Pass)

#### Approach & Intuition
- Use a frequency array `int[] count = new int[256]` (or `26` for lowercase English).
- **Pass 1:** Count occurrences of each character: `count[s.charAt(i)]++`.
- **Pass 2:** Scan the string from left to right. The first character with `count[s.charAt(i)] == 1` is our answer!

#### Java Code
```java
public static int firstUniqCharOptimal(String s) {
    if (s == null || s.isEmpty()) return -1;
    int[] count = new int[256];

    for (int i = 0; i < s.length(); i++) {
        count[s.charAt(i)]++;
    }

    for (int i = 0; i < s.length(); i++) {
        if (count[s.charAt(i)] == 1) {
            return i;
        }
    }
    return -1;
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N)$
  - *Reasoning:* Pass 1 takes $\mathcal{O}(N)$ time, Pass 2 takes $\mathcal{O}(N)$ time. Total $\mathcal{O}(2N) = \mathcal{O}(N)$.
- **Space Complexity:** $\mathcal{O}(1)$
  - *Reasoning:* Fixed size array of 256 integers, independent of string length $N$.

#### Codility & Interview Edge Cases
- Empty string or `null` input $\rightarrow$ return `-1`.
- Case sensitivity: Clarify with interviewer if `'A'` and `'a'` are considered equal or distinct.
- Non-ASCII characters: If Unicode characters exist, replace `int[256]` with `HashMap<Character, Integer>`.

---

## 6. Valid Anagram

### Question
Given two strings `s` and `t`, return `true` if `t` is an anagram of `s`, and `false` otherwise. (An Anagram is formed by rearranging characters of another string using all original characters exactly once).

- **Example 1:** `s = "anagram", t = "nagaram"` $\rightarrow$ Output: `true`
- **Example 2:** `s = "rat", t = "car"` $\rightarrow$ Output: `false`

---

### 6.1 Brute Force / Sorting Approach

#### Approach & Intuition
- If `s.length() != t.length()`, they cannot be anagrams.
- Convert both strings to character arrays, sort them using `Arrays.sort()`, and check if they are identical using `Arrays.equals()`.

#### Java Code
```java
public static boolean isAnagramSorting(String s, String t) {
    if (s == null || t == null || s.length() != t.length()) return false;
    char[] sArr = s.toCharArray();
    char[] tArr = t.toCharArray();
    Arrays.sort(sArr);
    Arrays.sort(tArr);
    return Arrays.equals(sArr, tArr);
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N \log N)$
  - *Reasoning:* Sorting both arrays of length $N$ takes $\mathcal{O}(N \log N)$.
- **Space Complexity:** $\mathcal{O}(N)$
  - *Reasoning:* `toCharArray()` creates new array buffers of size $N$.

---

### 6.2 Optimal Approach (Frequency Counter)

#### Approach & Intuition
- Maintain a single frequency array `int[] count = new int[256]`.
- For each index `i`, increment `count[s.charAt(i)]++` and decrement `count[t.charAt(i)]--`.
- Finally, verify if all elements in `count` array are zero.

#### Java Code
```java
public static boolean isAnagramOptimal(String s, String t) {
    if (s == null || t == null || s.length() != t.length()) return false;
    int[] count = new int[256];

    for (int i = 0; i < s.length(); i++) {
        count[s.charAt(i)]++;
        count[t.charAt(i)]--;
    }

    for (int c : count) {
        if (c != 0) return false;
    }
    return true;
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N)$
  - *Reasoning:* Single loop over string length $N$ plus constant scan over size 256 array.
- **Space Complexity:** $\mathcal{O}(1)$

#### Edge Cases
- Different lengths (`s.length() != t.length()`) $\rightarrow$ immediately return `false`.
- Empty strings `""` and `""` $\rightarrow$ return `true`.

---

## 7. Character Frequency

### Question
Given a string `s`, return the character with the highest frequency. Define a deterministic tie-breaking rule (e.g. if multiple characters tie for highest frequency, return the one that appears earliest in the string).

- **Example:** `s = "moneyllion"` $\rightarrow$ Character `'o'`, `'n'`, and `'l'` all appear twice (`2`). Since `'o'` appears first in the string, return `'o'`.

---

### 7.1 Brute Force Approach (Nested Loops)

#### Approach & Intuition
- For each character in the string, count its occurrences by iterating over the whole string.
- Keep track of the character that yields the maximum count.

#### Java Code
```java
public static char highestFrequencyBruteForce(String s) {
    if (s == null || s.isEmpty()) return '\0';
    char maxChar = s.charAt(0);
    int maxFreq = 0;

    for (int i = 0; i < s.length(); i++) {
        char curr = s.charAt(i);
        int freq = 0;
        for (int j = 0; j < s.length(); j++) {
            if (s.charAt(j) == curr) freq++;
        }
        if (freq > maxFreq) {
            maxFreq = freq;
            maxChar = curr;
        }
    }
    return maxChar;
}
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N^2)$
- **Space Complexity:** $\mathcal{O}(1)$

---

### 7.2 Optimal Approach (Frequency Array with Deterministic Tie-breaking)

#### Approach & Intuition
- **Pass 1:** Build frequency table `count[s.charAt(i)]++`.
- **Pass 2:** Iterate through the string from left to right. If `count[s.charAt(i)] > maxFreq`, update `maxFreq` and `maxChar`.
- *Why strict `>` works for tie-breaking:* By using strict greater-than (`>`), the first character that achieves `maxFreq` will be retained, automatically breaking ties in favor of the earliest appearing character!

#### Java Code
```java
public static char highestFrequencyOptimal(String s) {
    if (s == null || s.isEmpty()) return '\0';
    int[] count = new int[256];

    for (int i = 0; i < s.length(); i++) {
        count[s.charAt(i)]++;
    }

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
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N)$
- **Space Complexity:** $\mathcal{O}(1)$

---

## 8. String Compression

### Question
Compress consecutive repeated characters in a string.
- **Example 1:** `s = "aaabbc"` $\rightarrow$ Output: `"a3b2c1"`
- **Example 2:** `s = "aabcccccaaa"` $\rightarrow$ Output: `"a2b1c5a3"`

---

### 8.1 Brute Force / Naive Approach (String Concatenation in Loop)

#### Approach & Intuition
- Iterate through string, counting consecutive duplicates.
- Append character and count using `result += char + count`.
- **WHY IT IS SLOW:** Java strings are immutable! Every `+=` creates a brand new `String` object by allocating memory and copying all previous characters, resulting in $\mathcal{O}(N^2)$ runtime and massive garbage collection overhead.

#### Java Code
```java
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
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N^2)$ due to string copying in Java.
- **Space Complexity:** $\mathcal{O}(N^2)$ due to temporary String objects created in heap memory.

---

### 8.2 Optimal Approach (`StringBuilder` Single Pass)

#### Approach & Intuition
- Use a `StringBuilder` buffer which provides $\mathcal{O}(1)$ amortized append operations.
- Loop through the string. Compare `s.charAt(i)` with `s.charAt(i + 1)`.
- Increment counter if match; otherwise append `s.charAt(i)` and `count` to `StringBuilder` and reset counter to `1`.

#### Java Code
```java
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
```

#### Complexity Analysis
- **Time Complexity:** $\mathcal{O}(N)$
  - *Reasoning:* Single pass over the string with $\mathcal{O}(1)$ appends in `StringBuilder`.
- **Space Complexity:** $\mathcal{O}(N)$
  - *Reasoning:* `StringBuilder` holds the output string of length $\le 2N$.

#### Codility & Interview Edge Cases
- **Compressed string longer than original:** e.g. `"abcd"` becomes `"a1b1c1d1"` (length 8 vs 4). Always ask interviewer whether to return compressed or original if compressed length $\ge$ original length.

---

## Day 2 Summary & Patterns Cheatsheet

| Problem | Brute Force Time | Optimal Time | Space | Key Pattern / Concept |
| :--- | :--- | :--- | :--- | :--- |
| **5. First Unique Char** | $\mathcal{O}(N^2)$ | $\mathcal{O}(N)$ | $\mathcal{O}(1)$ | 2-Pass Frequency Array (`count[256]`) |
| **6. Valid Anagram** | $\mathcal{O}(N \log N)$ (Sort) | $\mathcal{O}(N)$ | $\mathcal{O}(1)$ | Single pass frequency diff (`count[s]++`, `count[t]--`) |
| **7. Char Frequency** | $\mathcal{O}(N^2)$ | $\mathcal{O}(N)$ | $\mathcal{O}(1)$ | Frequency count + strict `>` for tie-break |
| **8. Compression** | $\mathcal{O}(N^2)$ (String `+=`) | $\mathcal{O}(N)$ | $\mathcal{O}(N)$ | `StringBuilder` (Avoid immutable String concatenation) |

---

### Core Java String Gotchas for Interviews
1. **Never use String concatenation (`+=`) in a loop!** Always use `StringBuilder`.
2. **Fixed-size array (`int[256]` or `int[26]`) instead of HashMap** when input is restricted to ASCII/English alphabet $\rightarrow$ guarantees $\mathcal{O}(1)$ space and faster constant factors.
3. **Strings are immutable in Java**, so `s.charAt(i)` is $\mathcal{O}(1)$, but substring/concatenation is $\mathcal{O}(K)$.
