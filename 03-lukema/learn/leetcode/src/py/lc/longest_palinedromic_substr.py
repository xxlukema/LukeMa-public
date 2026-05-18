"""
5. Longest Palinedromic Substring

Medium

Given a string s, return the longest palindromic substring in s.

Example 1:

Input: s = "babad"
Output: "bab"
Explanation: "aba" is also a valid answer.

Example 2:

Input: s = "cbbd"
Output: "bb"

Constraints:

    1 <= s.length <= 1000
    s consist of only digits and English letters.

"""

# Time: O(n ^ 2)
# Space: O(n)
# Runtime 650 ms Beats 67.24%
# Memory 16.5 MB Beats 29.93%
class Solution:
    "solution"
    def longestPalindrome(self, s: str) -> str:
        """
        babad
        cbbd
        """

        S_LEN = len(s)
        width = []

        for pos in range(S_LEN):
            # 1. aba pattern
            inc = 0
            while pos - inc >= 0 and pos + inc < S_LEN and s[pos-inc] == s[pos + inc]:
                inc += 1

            width.append((inc - 1) * 2 + 1)

            # 2. abba pattern
            inc = 0
            while pos - inc >= 0 and pos + 1 + inc < S_LEN and s[pos-inc] == s[pos + 1 + inc]:
                inc += 1

            width[pos] = max(width[pos], inc * 2)

        MAX_LEN = max(width)

        for pos in range(S_LEN):
            if width[pos] == MAX_LEN:
                if width[pos] % 2 == 1:
                    return s[pos - width[pos] // 2 : pos + 1 + width[pos] // 2]
                else:
                    return s[pos + 1 - width[pos] // 2 : pos + 1 + width[pos] // 2]

        return ""


def driver() -> None:
    "driver"

    # S = "babad"
    # expect 3

    S = "cbbd"
    # expect 2

    solution = Solution()
    result = solution.longestPalindrome(S)

    print(result)

driver()
