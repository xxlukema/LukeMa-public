package com.learn.graph;

import java.util.PriorityQueue;
import java.util.Queue;

import lombok.extern.log4j.Log4j2;

/**
 * From (0, 0) to (4, 3) should be 3 steps. But this program gives result of 5.
 * 
 * Inconsistent KnightNode.comparator with KnightNode.hashCode(): Different node positions can have same distance to destination.
 */
@Log4j2
public class MinKnightMoveDepthFirst {

    public static void main(String[] args) {
        MinKnightMoveDepthFirst minKnigtMove = new MinKnightMoveDepthFirst();

        // int moves = minKnigtMove.minKnightMoves(4, 3);
        // int moves = minKnigtMove.minKnightMoves(0, 3);
        int moves = minKnigtMove.minKnightMoves(4, 3);

        log.debug("moves: {}", () -> moves);
    }

    public int minKnightMoves(int x, int y) {

        x = Math.abs(x);
        y = Math.abs(y);

        KnightNode dest = new KnightNode(x, y, null, 0, null);

        int[][] mat = new int[x + 6][y + 6];

        KnightNode start = new KnightNode(0, 0, dest, 0, null);

        if (start.isDest()) {
            return 0;
        }

        walk(start, mat, dest);

        return mat[x + 2][y + 2];
    }

    void walk(KnightNode node, int[][] mat, KnightNode dest) {

        log.debug("current node: {}", node);

        int a = node.x;
        int b = node.y;

        if (mat[a + 2][b + 2] == 0 || mat[a + 2][b + 2] > node.steps) {
            mat[a + 2][b + 2] = node.steps;
        }

        if (node.isDest()) {
            return;
        } else {
            int counter = node.steps + 1;
            KnightNode next = null;

            /**
             * Inconsistent KnightNode.comparator with KnightNode.hashCode(): Different node positions can have same distance to destination.
             */
            if (node.y == node.dest.y && node.x == node.dest.x - 3) {
                // Solving Inconsistent KnightNode.comparator with KnightNode.hashCode() caused issue.
                next = new KnightNode(node.x + 2, node.y - 1, dest, counter, node);
            } else if (node.x == node.dest.x && node.y == node.dest.y - 3) {
                // Solving Inconsistent KnightNode.comparator with KnightNode.hashCode() caused issue.
                next = new KnightNode(node.x - 1, node.y + 2, dest, counter, node);
            } else {
                /**
                 * Inconsistent KnightNode.comparator with KnightNode.hashCode(): Different node positions can have same distance to destination.
                 */
                Queue<KnightNode> q = new PriorityQueue<>(10, KnightNode.comparitor);

                q.add(new KnightNode(a + 2, b + 1, dest, counter, node));
                q.add(new KnightNode(a + 2, b - 1, dest, counter, node));
                q.add(new KnightNode(a - 2, b + 1, dest, counter, node));
                q.add(new KnightNode(a - 2, b - 1, dest, counter, node));

                q.add(new KnightNode(a + 1, b + 2, dest, counter, node));
                q.add(new KnightNode(a - 1, b + 2, dest, counter, node));
                q.add(new KnightNode(a + 1, b - 2, dest, counter, node));
                q.add(new KnightNode(a - 1, b - 2, dest, counter, node));

                next = q.poll();
                q.clear();
                q = null;

                log.debug("next from queue: {}", next);
            }

            // log.debug("next node: {}", next);

            walk(next, mat, dest);
        }
    }

}
