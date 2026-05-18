"""
ListNode
"""

from __future__ import annotations
from typing import List


class ListNode:
    """
    ListNode
    """
    def __init__(self, val=0, next_node=None):
        self.val = val
        self.next = next_node

    @staticmethod
    def toListNode(nums: List[int]) -> (ListNode | None):
        "toListNode"
        if nums is None or len(nums) == 0:
            return None

        root = ListNode(nums[0])

        last = root

        for idx in range(1, len(nums)):
            curr = ListNode(nums[idx])
            last.next = curr
            last = curr

        return root

    @staticmethod
    def print(ln: ListNode) -> None:
        "print"
        vals = []
        while ln is not None:
            vals.append(ln.val)
            ln = ln.next

        print(vals)
