package com.learn.backtrack;


import org.junit.jupiter.api.Assertions;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 55 - Jump Game
 *
 * Medium
 *
 *
 */
@Log4j2
public class JumpGame {

    public static void main(String[] args) {

        // int[] nums = { 2, 3, 1, 1, 4 };
        // int[] nums = { 3, 2, 1, 0, 4 };
        // int[] nums = { 0 };
        // int[] nums = { 2, 0, 0 };
        // int[] nums = { 1, 1, 1, 0 };
        int[] nums = {
                8, 2, 4, 4, 4, 9, 5, 2, 5, 8, 8, 0, 8, 6, 9, 1, 1, 6, 3, 5, 1, 2, 6, 6, 0,
                4, 8, 6, 0, 3, 2, 8, 7, 6, 5, 1, 7, 0, 3, 4, 8, 3, 5, 9, 0, 4, 0, 1, 0, 5,
                9, 2, 0, 7, 0, 2, 1, 0, 8, 2, 5, 1, 2, 3, 9, 7, 4, 7, 0, 0, 1, 8, 5, 6, 7,
                5, 1, 9, 9, 3, 5, 0, 7, 5 };

        JumpGame jumpGame = new JumpGame();

        boolean ret1 = jumpGame.canJumpZeroPassableLuke(nums);
        log.debug("canJump Luke Zero passable: {}", () -> ret1);

        boolean ret2 = jumpGame.canJumpGreedyLuke(nums);
        log.debug("canJump Luke Greedy: {}", () -> ret2);

        boolean ret3 = jumpGame.canJumpLcBacktrack(nums);
        log.debug("canJump LC Backtrack: {}", () -> ret3);

        boolean ret4 = jumpGame.canJumpDpTopDown(nums);
        log.debug("canJump LC DP Top-down: {}", () -> ret4);

        boolean ret5 = jumpGame.canJumpLcBottomUp(nums);
        log.debug("canJump LC DP Bottom-up: {}", () -> ret5);

        boolean retLC = jumpGame.canJumpGreedyLC(nums);
        log.debug("canJump LC Greedy: {}", () -> retLC);

        Assertions.assertEquals(retLC, ret1);
        Assertions.assertEquals(retLC, ret2);
        Assertions.assertEquals(retLC, ret3);
        Assertions.assertEquals(retLC, ret4);
        Assertions.assertEquals(retLC, ret5);
    }

    /**
    * LC Backtrack 1/2
    */
    public boolean canJumpLcBacktrack(int[] nums) {
        return canJumpFromPositionLcBacktrack(0, nums);
    }

    /**
     * LC Backtrack 2/2
     */
    public boolean canJumpFromPositionLcBacktrack(int position, int[] nums) {
        if (position == nums.length - 1) {
            return true;
        }

        int furthestJump = Math.min(position + nums[position], nums.length - 1);
        for (int nextPosition = position + 1; nextPosition <= furthestJump; nextPosition++) {
            if (canJumpFromPositionLcBacktrack(nextPosition, nums)) {
                return true;
            }
        }

        return false;
    }

    /**
     * LC DP Top-down --- Start
     */
    enum CanJump {
        YES, NO, UNKNOWN
    }

    public boolean canJumpFromPosition(final int position, int[] nums, CanJump[] memo) {

        if (memo[position] == CanJump.UNKNOWN) {

            final int furthestJump = Math.min(position + nums[position], nums.length - 1);

            for (var nextPosition = furthestJump; nextPosition > position; nextPosition--) {
                if (canJumpFromPosition(nextPosition, nums, memo)) {
                    memo[position] = CanJump.YES;
                    return true;
                }
            }

            memo[position] = CanJump.NO;

            return false;

        } else {
            return memo[position] == CanJump.YES ? true : false;
        }
    }

    public boolean canJumpDpTopDown(int[] nums) {

        CanJump[] memo = new CanJump[nums.length];

        for (int i = 0; i < memo.length; i++) {
            memo[i] = CanJump.UNKNOWN;
        }

        memo[memo.length - 1] = CanJump.YES;

        return canJumpFromPosition(0, nums, memo);
    }

    /**
     * LC DP Top-down --- End
     */
    // LC DP Top-down --- End

    /**
     * LC DP Bottom-up --- Start
     */
    public boolean canJumpLcBottomUp(int[] nums) {

        CanJump[] memo = new CanJump[nums.length];

        for (int i = 0; i < memo.length; i++) {
            memo[i] = CanJump.UNKNOWN;
        }

        memo[memo.length - 1] = CanJump.YES;

        for (int i = nums.length - 2; i >= 0; i--) {
            int furthestJump = Math.min(i + nums[i], nums.length - 1);
            for (int k = i + 1; k <= furthestJump; k++) {
                if (memo[k] == CanJump.YES) {
                    memo[i] = CanJump.YES;
                    break;
                }
            }
        }

        return memo[0] == CanJump.YES;
    }

    /**
     * LC DP Bottom-up --- End
     */
    // LC DP Bottom-up --- End

    /**
     * Greedy
     */
    public boolean canJumpGreedyLuke(int[] nums) {
        int lastPos = nums[nums.length - 1];

        for (int i = nums.length - 1; i >= 0; i--) {
            if (i + nums[i] >= lastPos) {
                lastPos = i;
            }
        }

        return lastPos == 0;
    }

    /**
     * num[i] == 0 are breakers. Thie goal is to find the last zeros and verify these zeros are passable (can be fly over)
     */
    public boolean canJumpZeroPassableLuke(int[] nums) {

        if (nums.length < 2) {
            return true;
        }

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0 || // @formatter:off
                    /**
                     *  if duplicated zeros, use the last zero
                     */
                    (i + 1 < nums.length && nums[i + 1] == 0)) {
                continue;
                // @formatter:on
            } else {
                if (passableLuke(nums, i)) {
                    continue;
                } else {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Verify the last zero is passable (can be fly over)
     */
    private boolean passableLuke(int[] nums, int curr) {

        for (int i = 0; i < curr; i++) {
            if (nums[i] > curr - i || (curr == nums.length - 1 && nums[i] == curr - i)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Greedy
     */
    public boolean canJumpGreedyLC(int[] nums) {
        int lastPos = nums.length - 1;
        for (int i = nums.length - 1; i >= 0; i--) {
            if (i + nums[i] >= lastPos) {
                lastPos = i;
            }
        }
        return lastPos == 0;
    }

}
