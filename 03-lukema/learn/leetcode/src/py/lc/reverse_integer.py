"""
7. Reverse Integer

Medium

Given a signed 32-bit integer x, return x with its digits reversed. If reversing x causes the value to go outside the signed 32-bit
integer range [-2^31, 2^31 - 1], then return 0.

Assume the environment does not allow you to store 64-bit integers (signed or unsigned).

Example 1:

Input: x = 123
Output: 321

Example 2:

Input: x = -123
Output: -321

Example 3:

Input: x = 120
Output: 21

Constraints:

    -2^31 <= x <= 2^31 - 1

"""

# Runtime: 40 ms Beats 75.30%
# Memory: 16.3 MB Beats 18.35%
# Time: O(len(x))
# Space: O(len(x))
class Solution(object):
    "solution"

    def reverse(self, x) -> int:
        """
        :type x: int
        :rtype: int
        """

        s = str(x)
        t = ""

        # reverse
        for ch in s:
            t = ch + t

        # swap '-' to front
        if t[-1] == "-":
            t = "-" + t[0:-1]

        MIN = - 2 ** 31
        MAX = 2 ** 31 - 1

        S_MIN = str(MIN)
        S_MAX = str(MAX)

        if x >= 0:
            if len(t) < len(S_MAX):
                return int(t)
            else:
                return 0 if t > S_MAX else int(t)
        else:
            if len(t) < len(S_MIN):
                return int(t)
            else:
                return 0 if t > S_MIN else int(t)

def driver() -> None:
    "driver"

    N = 123
    # expect 321

    # N = -214748364

    solution = Solution()

    ret = solution.reverse(N)

    assert ret == 321, f"ret: {ret}"

    print("pass")



# driver
driver()
