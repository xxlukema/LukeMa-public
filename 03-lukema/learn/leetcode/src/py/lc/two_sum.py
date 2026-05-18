"""
1. Two Sum

Easy

Given an array of integers nums and an integer target, return indices of the two numbers such that they add up to target.

You may assume that each input would have exactly one solution, and you may not use the same element twice.

You can return the answer in any order.

Example 1:

Input: nums = [2,7,11,15], target = 9
Output: [0,1]
Explanation: Because nums[0] + nums[1] == 9, we return [0, 1].

Example 2:

Input: nums = [3,2,4], target = 6
Output: [1,2]

Example 3:

Input: nums = [3,3], target = 6
Output: [0,1]



Constraints:

    2 <= nums.length <= 104
    -109 <= nums[i] <= 109
    -109 <= target <= 109
    Only one valid answer exists.


Follow-up: Can you come up with an algorithm that is less than O(n2) time complexity?
"""

from typing import List


# colors = ["red", "green", "blue", "purple"]
# for idx2, color in enumerate(colors):
#     print(f"=== {idx2}, {color}")

# Luke
# Runtime 82 ms Beats 46.70%
# Memory 17.7 MB Beats 14.70%
# Time: O()
class Solution:
    "solution"
    def two_sum(self, nums: List[int], target: int) -> List[int]:
        "Two Sums"

        # Time: O(n)
        # Space: O(n)
        num_set = set()

        # Time: O(n)
        for idx, num in enumerate(nums):
            diff = target - num
            # Time: O(n)
            if diff in num_set:
                return [nums.index(diff), idx]
            else:
                num_set.add(num)

        return []

def driver() -> None:
    "driver"
    NUMS = [2,7,11,15]
    TARGET = 9

    # NUMS = [3,2,4]
    # NUMS = [3,3]
    # TARGET = 6

    solution = Solution()

    ret = solution.two_sum(NUMS, TARGET)

    print(ret)

driver()
