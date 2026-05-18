package com.learn.other;


import lombok.extern.log4j.Log4j2;


/**
 * LC-302 Smallest Reactangle Enclosing Black Pixels
 *
 * Hard
 *
 * You are given an m x n binary matrix image where 0 represents a white pixel and 1 represents a black pixel.
 *
 * The black pixels are connected (i.e., there is only one black region). Pixels are connected horizontally and vertically.
 *
 * Given two integers x and y that represents the location of one of the black pixels, return the area of the smallest (axis-aligned)
 * rectangle that encloses all black pixels.
 *
 * You must write an algorithm with less than O(mn) runtime complexity
 *
 * Example 1:
 * Input: image = [["0","0","1","0"],["0","1","1","0"],["0","1","0","0"]], x = 0, y = 2
 * Output: 6
 *
 * Example 2:
 * Input: image = [["1"]], x = 0, y = 0
 * Output: 1
 *
 * Constraints:
 *     m == image.length
 *     n == image[i].length
 *     1 <= m, n <= 100
 *     image[i][j] is either '0' or '1'.
 *     0 <= x < m
 *     0 <= y < n
 *     image[x][y] == '1'.
 *     The black pixels in the image only form one component.
 */
@Log4j2
public class SmallestReactangleEnclosingBlackPixels {

    public static void main(String[] args) {

        /**
         * Expected: 6
         */
        final char[][] image = {
                { '0', '0', '1', '0' },
                { '0', '1', '1', '0' },
                { '0', '1', '0', '0' } };

        final int x = 0, y = 2;

        SmallestReactangleEnclosingBlackPixels smallestReactangleEnclosingBlackPixels = new SmallestReactangleEnclosingBlackPixels();

        var ret = smallestReactangleEnclosingBlackPixels.minArea(image, x, y);
        log.debug("Smallest Reactangle Enclosing Black Pixels: {}", () -> ret);
        log.debug("Smallest Reactangle Enclosing Black Pixels {} OK", () -> "ret");
    }

    /**
     * LC - BFS
     *
     * Runtime: 2 ms Beats 67.40%
     * Memory: 42.7 MB Beats 87.67%
     *
     * Time: O(E) = O(m * n)
     * Space: O(V) = O(m * n)
     */
    public int minArea(char[][] image, int x, int y) {

        left = right = x;
        top = bottom = y;

        bfs(image, x, y);

        // DpUtils.print(image);

        return (right - left + 1) * (bottom - top + 1);
    }

    int left = 0, right = 0, top = 0, bottom = 0;

    private void bfs(char[][] image, int x, int y) {
        if (x < 0 || y < 0 || x >= image.length || y >= image[0].length) {
            return;
        }

        if (image[x][y] != '1') {
            return;
        }

        image[x][y] = 'x';

        left = Math.min(left, x);
        right = Math.max(right, x);
        top = Math.min(top, y);
        bottom = Math.max(bottom, y);

        // left
        bfs(image, x - 1, y);
        // top
        bfs(image, x, y - 1);
        // bottom
        bfs(image, x + 1, y);
        // right
        bfs(image, x, y + 1);
    }

}
