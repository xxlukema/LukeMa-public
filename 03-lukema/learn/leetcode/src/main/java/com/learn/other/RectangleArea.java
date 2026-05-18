package com.learn.other;


import java.util.PriorityQueue;

import lombok.extern.log4j.Log4j2;


/**
 * LC - 223 - Rectangle Area
 *
 * Medium
 *
 * Meaningless if...else combinations.
 */
@Log4j2
public class RectangleArea {

    public static void main(String[] args) {

        final int ax1 = -3, ay1 = 0, ax2 = 3, ay2 = 4, bx1 = 0, by1 = -1, bx2 = 9, by2 = 2;

        RectangleArea rectangleArea = new RectangleArea();

        var ret = rectangleArea.computeArea(ax1, ay1, ax2, ay2, bx1, by1, bx2, by2);
        log.debug("Rectangle Area: {}", () -> ret);
        log.debug("Rectangle Area {} OK", () -> "ret");

    }

    public int computeArea(int ax1, int ay1, int ax2, int ay2, int bx1, int by1, int bx2, int by2) {

        int area = 0;

        record Point(int x, int y) {
        }

        PriorityQueue<Point> horizontal = new PriorityQueue<>((a, b) -> a.x - b.x);
        PriorityQueue<Point> vertical = new PriorityQueue<>((a, b) -> a.y - b.y);

        int areaA = Math.abs((ax1 - ax2) * (ay1 - ay2));
        int areaB = Math.abs((bx1 - bx2) * (by1 - by2));

        area = areaA + areaB;

        /**
         * Find overlap
         */

        /**
         * Build pq
         */
        horizontal.add(new Point(ax1, ay1));
        horizontal.add(new Point(ax2, ay2));
        horizontal.add(new Point(bx1, by1));
        horizontal.add(new Point(bx2, by2));

        vertical.add(new Point(ax1, ay1));
        vertical.add(new Point(ax2, ay2));
        vertical.add(new Point(bx1, by1));
        vertical.add(new Point(bx2, by2));

        /**
         * Find overlap area
         */
        /*
        Point h1 = horizontal.poll();
        Point h2 = horizontal.poll();
        Point h3 = horizontal.poll();
        Point h4 = horizontal.poll();

        Point v1 = vertical.poll();
        Point v2 = vertical.poll();
        Point v3 = vertical.poll();
        Point v4 = vertical.poll();

        int overlap = 0;

        // a first then b
        if (v1.x == ax1 && v1.y == ay1) {
            // then b
            if (v2.x == bx1 && v2.y == by1) {
                if (v3.x == bx2 && v3.y == by2) {
                    // b closes first
                } else {
                    // a closes first
                }
            }
        }

        if (v1.x == bx1 && v1.y == by1) {
            // b first
            if (v2.x == bx2 && v2.y == by2) {
                // no overlap
                return area;
            }
        }

        area -= overlap;
        */

        return area;
    }
}
