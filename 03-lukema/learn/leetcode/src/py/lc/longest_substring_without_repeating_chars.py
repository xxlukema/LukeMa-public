"""
3. Longest Substring Without Repeating Characters

Medium

Given a string s, find the length of the longest substring
without repeating characters.

Example 1:

Input: s = "abcabcbb"
Output: 3
Explanation: The answer is "abc", with the length of 3.

Example 2:

Input: s = "bbbbb"
Output: 1
Explanation: The answer is "b", with the length of 1.

Example 3:

Input: s = "pwwkew"
Output: 3
Explanation: The answer is "wke", with the length of 3.
Notice that the answer must be a substring, "pwke" is a subsequence and not a substring.



Constraints:

    0 <= s.length <= 5 * 10 ^ 4
    s consists of English letters, digits, symbols and spaces.

"""

# 1. two pointers
# 2. queue of substr chars
#    Time: O(n * max_len)
#    Space: O(max_len)
#    Runtime 122 ms Beats 22.55%
#    Memory 16.4 MB Beats 36.83%
class Solution:
    "solution"
    def lengthOfLongestSubstring(self, s: str) -> int:
        """
        Input: s = "abcabcbb"
                    |  ^
        Output: 3
        """

        queue = []
        max_len = 0

        for ch in s:
            if queue.count(ch) != 0:
                while queue.pop(0) != ch:
                    pass

            queue.append(ch)
            max_len = max(max_len, len(queue))

        return max_len

def driver() -> None:
    "driver"

    # S = "abcabcbb"
    # expect 3
    S = "aab"


    solution = Solution()

    ret = solution.lengthOfLongestSubstring(S)

    assert ret == 2, f"ret: {ret}"

    print("pass")

# driver
driver()
