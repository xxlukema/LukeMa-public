"""
2. Add Two Numbers

Medium

You are given two non-empty linked lists representing two non-negative integers. The digits are stored in reverse order, and each of
their nodes contains a single digit. Add the two numbers and return the sum as a linked list.

You may assume the two numbers do not contain any leading zero, except the number 0 itself.

Example 1:

Input: l1 = [2,4,3], l2 = [5,6,4]
Output: [7,0,8]
Explanation: 342 + 465 = 807.

Example 2:

Input: l1 = [0], l2 = [0]
Output: [0]

Example 3:

Input: l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]
Output: [8,9,9,9,0,0,0,1]



Constraints:

    The number of nodes in each linked list is in the range [1, 100].
    0 <= Node.val <= 9
    It is guaranteed that the list represents a number that does not have leading zeros.

"""

# Definition for singly-linked list.
# class ListNode:
#     def __init__(self, val=0, next=None):
#         self.val = val
#         self.next = next
from typing import Optional

from lc.ListNode import ListNode

# Runtime51 ms Beats 71.10%
# Memory 13.4 MB Beats 59.32%
# Time: O(max(len(l1), len(l2)))
# Space: O(1)
class Solution:
    "solution"

    def add_two_numbers(self, l1: Optional[ListNode], l2: Optional[ListNode]) -> Optional[ListNode]:
        " add two numbers"

        root = None
        curr = root

        curr1 = l1
        curr2 = l2

        carry = 0

        while curr1 is not None or curr2 is not None:
            tmp_sum = carry
            if curr1 is not None:
                tmp_sum += curr1.val
                curr1 = curr1.next
            if curr2 is not None:
                tmp_sum += curr2.val
                curr2 = curr2.next

            new = ListNode(tmp_sum % 10)

            if root is None:
                root = new
            else:
                curr.next = new

            curr = new

            carry = tmp_sum // 10

            # print(tmp_sum % 10, carry)

        if carry > 0:
            new = ListNode(carry)
            curr.next = new

        return root


def driver() -> None:
    "driver"

    L1 = [2, 4, 3]
    L2 = [5, 6, 4]

    LN1 = ListNode.toListNode(L1)
    LN2 = ListNode.toListNode(L2)

    solution = Solution()

    ret = solution.add_two_numbers(LN1, LN2)

    ListNode.print(ret)
