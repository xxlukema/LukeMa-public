"""
6. Zigzag Conversion

Medium

The string "PAYPALISHIRING" is written in a zigzag pattern on a given number of rows like this: (you may want to display this pattern in a fixed font for better legibility)

P   A   H   N
A P L S I I G
Y   I   R

And then read line by line: "PAHNAPLSIIGYIR"

Write the code that will take a string and make this conversion given a number of rows:

string convert(string s, int numRows);

Example 1:

Input: s = "PAYPALISHIRING", numRows = 3
Output: "PAHNAPLSIIGYIR"

Example 2:

Input: s = "PAYPALISHIRING", numRows = 4
Output: "PINALSIGYAHRPI"
Explanation:
P     I    N
A   L S  I G
Y A   H R
P     I

Example 3:

Input: s = "A", numRows = 1
Output: "A"

Constraints:

    1 <= s.length <= 1000
    s consists of English letters (lower-case and upper-case), ',' and '.'.
    1 <= numRows <= 1000

"""

# Runtime: 115 ms Beats 18.54%
# Memory: 13.7 MB Beats 19.63%
#
# Time: O(len(s))
# Space: O(len(s))
class Solution(object):
    "solution"
    def convert(self, s, numRows) -> str:
        """
        :type s: str
        :type numRows: int
        :rtype: str
        """

        # arr = [[]] * numRows

        arr = []

        # pylint: disable=unused-variable
        for i in range(numRows):
            arr.append([])

        # find an incremental formular

        counter = 0
        row = 0
        is_from_top_to_bottom = False

        for ch in s:

            if counter % numRows == 0:
                is_from_top_to_bottom = not is_from_top_to_bottom

                if counter > 0:
                    counter += 1

            if is_from_top_to_bottom:
                row = counter % numRows
            else:
                row = numRows - 1 - counter % numRows

            arr[row].append(ch)

            counter += 1

        # print(arr)

        s = ""

        for subarr in arr:
            s = s + "".join(subarr)

        return s

def driver() -> None:
    "driver"

    S = "PAYPALISHIRING"
    NUM_ROWS = 4
    # expect "PINALSIGYAHRPI"

    solution = Solution()

    ret = solution.convert(S, NUM_ROWS)

    assert ret == "PINALSIGYAHRPI", f"ret: {ret}"

    print("pass")

# driver
driver()
