"""
LC - 8 String to Integer (aoi)

Easy

Implement the myAtoi(string s) function, which converts a string to a 32-bit signed integer (similar to C/C++'s atoi function).

The algorithm for myAtoi(string s) is as follows:

    * Read in and ignore any leading whitespace.
    * Check if the next character (if not already at the end of the string) is '-' or '+'. Read this character in if it is either.
      This determines if the final result is negative or positive respectively. Assume the result is positive if neither is present.
    * Read in next the characters until the next non-digit character or the end of the input is reached. The rest of the string is ignored.
    * Convert these digits into an integer (i.e. "123" -> 123, "0032" -> 32). If no digits were read, then the integer is 0.
      Change the sign as necessary (from step 2).
    * If the integer is out of the 32-bit signed integer range [-2 ^ 31, 2 ^ 31 - 1], then clamp the integer so that it remains in the range.
      Specifically, integers less than -2 ^ 31 should be clamped to -2 ^ 31, and integers greater than 2 ^ 31 - 1 should be clamped to 2 ^ 31 - 1.
    * Return the integer as the final result.

Note:

    Only the space character ' ' is considered a whitespace character.
    Do not ignore any characters other than the leading whitespace or the rest of the string after the digits.

Example 1:

Input: s = "42"
Output: 42
Explanation: The underlined characters are what is read in, the caret is the current reader position.
Step 1: "42" (no characters read because there is no leading whitespace)
         ^
Step 2: "42" (no characters read because there is neither a '-' nor '+')
         ^
Step 3: "42" ("42" is read in)
           ^
The parsed integer is 42.
Since 42 is in the range [-2 ^ 31, 2 ^ 31 - 1], the final result is 42.

Example 2:

Input: s = "   -42"
Output: -42
Explanation:
Step 1: "   -42" (leading whitespace is read and ignored)
            ^
Step 2: "   -42" ('-' is read, so the result should be negative)
             ^
Step 3: "   -42" ("42" is read in)
               ^
The parsed integer is -42.
Since -42 is in the range [-2 ^ 31, 2 ^ 31 - 1], the final result is -42.

Example 3:

Input: s = "4193 with words"
Output: 4193
Explanation:
Step 1: "4193 with words" (no characters read because there is no leading whitespace)
         ^
Step 2: "4193 with words" (no characters read because there is neither a '-' nor '+')
         ^
Step 3: "4193 with words" ("4193" is read in; reading stops because the next character is a non-digit)
             ^
The parsed integer is 4193.
Since 4193 is in the range [-2 ^ 31, 2 ^ 31 - 1], the final result is 4193.



Constraints:

    0 <= s.length <= 200
    s consists of English letters (lower-case and upper-case), digits (0-9), ' ', '+', '-', and '.'.

"""

class Solution:
    """
    LC Solution
    Time: O(N)
    Space: O(1)

    Runtime: 56 ms Beats 29.86%
    Memory: 16.2 MB Beats 79.56
    """

    def myAtoi(self, s: str) -> int:
        "my_atoi"

        s =s.strip()

        is_positive = True
        LEN = len(s)
        idx = 0

        if len(s) == 0:
            return 0

        if s[0] == "-":
            is_positive = False
            idx = 1

        elif s[0] == "+":
            idx = 1

        if is_positive:
            MAX = pow(2, 31) - 1
        else:
            MAX = pow(2, 31)

        num = 0

        while idx < LEN:
            digit = s[idx]

            if digit < "0" or digit > "9":
                break

            digit = int(digit)

            if num * 10 > MAX - digit:
                return MAX if is_positive else -MAX

            num = num * 10 + digit
            idx = idx + 1

        return num if is_positive else -num


class Solution2:
    """
    Luke Solution
    Time: O(N)
    Space: O(N)

    Runtime: 54 ms Beats 40.63%
    Memory: 16.5 MB Beats 7.72%
    """

    def myAtoi(self, s: str) -> int:
        "my_atoi"

        is_positive = True
        shoud_next_be_digit = False

        buff = []

        for ch in s:

            if shoud_next_be_digit:
                if ch < "0" or ch > "9":
                    break

            if ch < "0" or ch > "9":
                if not shoud_next_be_digit:
                    if ch == "-":
                        is_positive = not is_positive
                        shoud_next_be_digit = True
                    elif ch == "+":
                        shoud_next_be_digit = True
                    elif ch == " ":
                        continue
                    else:
                        break
            else:
                buff.append(ch)
                shoud_next_be_digit = True

        # remove leading zeros
        new_buf_no_leading_0 = []

        for idx, ch in enumerate(buff):
            if ch == "0":
                if len(new_buf_no_leading_0) == 0:
                    continue

            new_buf_no_leading_0.append(ch)

        buff = new_buf_no_leading_0

        if len(buff) == 0:
            return 0

        MIN = pow(2, 31)
        MAX = pow(2, 31) - 1

        MIN_STR = str(MIN)
        MAX_STR = str(MAX)

        LEN = len(buff)

        if is_positive:
            if LEN < len(MAX_STR):
                return int("".join(buff))
        else:
            if LEN < len(MIN_STR):
                return -int("".join(buff))

        if LEN > len(MAX_STR):
            return int(MAX_STR) if is_positive else -int(MIN_STR)

        # equal len to MAX

        if is_positive:
            STR = MAX_STR
        else:
            STR = MIN_STR

        is_all_prefix_equal = True

        for idx, ch in enumerate(STR):
            if is_all_prefix_equal:
                if buff[idx] > ch:
                    return int(MAX_STR) if is_positive else -int(MIN_STR)
                elif buff[idx] < ch:
                    return int("".join(buff)) if is_positive else -int("".join(buff))

        return int(MAX_STR) if is_positive else -int(MIN_STR)


def driver() -> None:
    "driver"

    # 1. s = "-4193+ with words"
    # expect -4193

    # 2.
    # s = "words and 987"
    # expect 0

    # 3.
    # s = "42"

    # 4.
    # s = "    -42"

    # 5.
    # s = "-10291283472332"
    # expect -2147483648

    # 6.
    s = "21474836460"
    # expect 2147483647

    # 7.
    s = "  0000000000012345678"
    # expect 12345678

    # 8.
    s = "1095502006p8"

    s = ""

    # N = -214748364

    solution = Solution()

    ret = solution.myAtoi(s)

    # 1. assert ret == -4193, f"ret: {ret}"
    # 2. assert ret == 0, f"ret: {ret}"
    # 3. assert ret == 42, f"ret: {ret}"
    # 4. assert ret == -42, f"ret: {ret}"
    assert ret == 0, f"ret: {ret}"

    print("pass")


# driver
driver()
