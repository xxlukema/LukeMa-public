package com.learn.graph;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.log4j.Log4j2;

/**
 * Runtime: 82 ms, faster than 77.92% of Java online submissions for Minimum Knight Moves.
 * Memory Usage: 64.4 MB, less than 61.85% of Java online submissions for Minimum Knight Moves.
 */
@Log4j2
public class MinKnightMoveBreadthFirst {

    public static void main(String[] args) {
        MinKnightMoveBreadthFirst minKnigtMove = new MinKnightMoveBreadthFirst();

        // int moves = minKnigtMove.minKnightMoves(4, 3);
        // int moves = minKnigtMove.minKnightMoves(0, 3);
        // int moves = minKnigtMove.minKnightMoves(4, 3);
        int moves = minKnigtMove.minKnightMoves(4, 3);

        log.debug("moves: {}", () -> moves);
    }

    public int minKnightMoves(int x, int y) {

        x = Math.abs(x);
        y = Math.abs(y);

        KnightNode dest = new KnightNode(x, y, null, 0, null);

        int[][] mat = new int[x + 8][y + 8];
        boolean[][] walked = new boolean[x + 8][y + 8];

        KnightNode start = new KnightNode(0, 0, dest, 0, null);

        if (start.isDest()) {
            return 0;
        }

        List<KnightNode> list = walk(start, mat, dest, walked);

        while (mat[x + 2][y + 2] == 0) {

            List<KnightNode> list2 = new ArrayList<>();

            for (KnightNode node : list) {
                List<KnightNode> newList = walk(node, mat, dest, walked);
                if (newList != null) {
                    list2.addAll(newList);
                }
            }

            list.clear();

            if (list2.isEmpty()) {
                break;
            } else {
                list = list2;
            }
        }

        return mat[x + 2][y + 2];
    }

    List<KnightNode> walk(KnightNode node, int[][] mat, KnightNode dest, boolean[][] walked) {

        // log.debug("current node: {} {}", node, node.isDest());

        int a = node.x;
        int b = node.y;

        if (a >= mat.length - 2 || b >= mat[0].length - 2 || a <= -2 || b <= -2) {
            return null;
        }

        if (mat[a + 2][b + 2] == 0 || mat[a + 2][b + 2] > node.steps) {
            mat[a + 2][b + 2] = node.steps;
        }

        if (node.isDest()) {
            log.debug("dest node: {} {}", node, node.isDest());
            log.debug("steps to dest node: {}", mat[a + 2][b + 2]);

            while (node.from != null) {
                log.debug("from: {}", node.from);
                node = node.from;
            }

            return null;
        }

        int counter = node.steps + 1;

        List<KnightNode> list = new ArrayList<>();

        addNext(a + 2, b + 1, dest, counter, node, walked, list);
        addNext(a + 2, b - 1, dest, counter, node, walked, list);
        addNext(a - 2, b + 1, dest, counter, node, walked, list);
        addNext(a - 2, b - 1, dest, counter, node, walked, list);

        addNext(a + 1, b + 2, dest, counter, node, walked, list);
        addNext(a + 1, b - 2, dest, counter, node, walked, list);
        addNext(a - 1, b + 2, dest, counter, node, walked, list);
        addNext(a - 1, b - 2, dest, counter, node, walked, list);

        return list;
    }

    void addNext(int x, int y, KnightNode dest, int counter, KnightNode node, boolean[][] walked,
            List<KnightNode> list) {

        if (x < -2 || y < -2 || x > dest.x + 2 || y > dest.y + 2) {
            return;
        }

        if (walked[x + 2][y + 2]) {
            return;
        } else {
            walked[x + 2][y + 2] = true;
            list.add(new KnightNode(x, y, dest, counter, node));
        }
    }

}
