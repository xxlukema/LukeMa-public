package com.learn.graph;

import java.util.Comparator;

/**
 * Inconsistent KnightNode.comparator with KnightNode.hashCode(): Different node positions can have same distance to destination.
 */
public class KnightNode {

    public static final Comparator<KnightNode> comparitor = (left, right) -> left.getDistance() - right.getDistance();

    int x;
    int y;
    KnightNode dest;
    KnightNode from;
    int steps;

    public KnightNode(int x, int y, KnightNode dest, int steps, KnightNode from) {
        this.x = x;
        this.y = y;
        this.dest = dest;
        this.steps = steps;
        this.from = from;
    }

    public KnightNode(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isDest() {
        return x == dest.x && y == dest.y;
    }

    @Override
    public String toString() {
        if (from == null) {
            return String.format("(%d, %d) distance: %d. from (0, 0). steps %d", x, y, getDistance(), steps);
        } else {
            return String.format("(%d, %d) distance: %d. from (%d, %d). steps %d", x, y, getDistance(), from.x, from.y,
                    steps);
            // return String.format("(%d, %d) distance: %d. steps %d. from %s.", x, y, getDistance(), steps, from);
        }
    }

    @Override
    public int hashCode() {
        return (x + ":" + y).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof KnightNode) {
            KnightNode n = (KnightNode) obj;
            return this.x == n.x && this.y == n.y;
        } else {
            return false;
        }
    }

    public int getDistance() {
        int dx = this.dest.x - x;
        int dy = this.dest.y - y;
        if (dx == 0 && dy == 0) {
            return 0;
        } else {
            if (this.dest.x + this.dest.y > 50) {
                // 2, 112
                return dx * dx + dy * dy;
            } else {
                // 5, 5
                return Math.abs(Math.abs(dx) + Math.abs(dy) - 3) + 3;
            }
        }
    }

}