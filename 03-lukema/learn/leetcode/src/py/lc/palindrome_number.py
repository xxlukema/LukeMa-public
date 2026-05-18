"""
9. Palindrome Number

Easy

Given an integer x, return true if x is a palindrome, and false otherwise.


Example 1:

Input: x = 121
Output: true
Explanation: 121 reads as 121 from left to right and from right to left.

Example 2:

Input: x = -121
Output: false
Explanation: From left to right, it reads -121. From right to left, it becomes 121-. Therefore it is not a palindrome.

Example 3:

Input: x = 10
Output: false
Explanation: Reads 01 from right to left. Therefore it is not a palindrome.


Constraints:

    -2 ^ 31 <= x <= 2 ^ 31 - 1

Follow up: Could you solve it without converting the integer to a string?

"""

class Solution:
    """
    Time: O(len)
    Space: O(1)

    Runtime: 92 ms Beats 13.55%
    Memory: 16.3 MB Beats 36.94%
    """
    def isPalindrome(self, x: int) -> bool:
        "isPalindrome"
        if x < 0:
            return False

        MAX = pow(2, 31) - 1
        MAX_CARR = MAX // 10
        MAX_REM = MAX % 10

        rem = x % 10
        carr = x // 10

        new_num = 0

        while carr > 0 or rem > 0:
            # print(carr, rem)

            if carr > MAX_CARR:
                return False

            if carr == MAX_CARR and rem > MAX_REM:
                return False

            new_num = new_num * 10 + rem
            rem = carr % 10
            carr //= 10


        # print("new_num", new_num)

        return new_num == x


def driver() -> None:
    "driver"
    x = 10201

    solution = Solution()

    ret = solution.isPalindrome(x)

    assert ret is True, f"ret {ret}"

    print("pass")

driver()
