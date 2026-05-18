package com.learn.graph;


import java.util.ArrayList;
import java.util.List;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class SpiralMatrix {

    public static void main(String[] args) {

        int[][] matrix = {
                { 1, 2, 3 },
                { 4, 5, 6 },
                { 7, 8, 9 } };

        int[][] matrix2 = {
                { 1, 2, 3, 4 },
                { 5, 6, 7, 8 },
                { 9, 10, 11, 12 } };

        int[][] matrix3 = {
                { 6, 9, 7 } };

        int[][] matrix4 = {
                { 7 },
                { 9 },
                { 6 } };

        SpiralMatrix spiralMatrix = new SpiralMatrix();

        List<Integer> ret = spiralMatrix.spiralOrder(matrix);
        log.info("spiralMatrix Luke: {}", () -> ret);

        List<Integer> ret2 = spiralMatrix.spiralOrder(matrix2);
        log.info("spiralMatrix Luke 2: {}", () -> ret2);

        List<Integer> ret3 = spiralMatrix.spiralOrder(matrix3);
        log.info("spiralMatrix Luke 3: {}", () -> ret3);

        List<Integer> ret4 = spiralMatrix.spiralOrder(matrix4);
        log.info("spiralMatrix Luke 4: {}", () -> ret4);

    }

    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> result = new ArrayList<>();

          final int N = Math.min(matrix.length, matrix[0].length);

        for (int counter = 0; counter < (N + 1) / 2; counter++) {
            spiralOrder(matrix, counter, result);
        }

        return result;
    }

    public void spiralOrder(int[][] matrix, int counter, List<Integer> result) {

        boolean[][] visited = new boolean[matrix.length][matrix[0].length];

        // top
        for (int i = counter; i < matrix[0].length - counter; i++) {

            log.debug("counter: {}, i: {}, value: {}", counter, i, matrix[counter][i]);

            result.add(matrix[counter][i]);
            visited[counter][i] = true;
        }

        // right
        for (int i = counter + 1; i < matrix.length - counter; i++) {

            log.debug("counter: {}, i: {}, value: {}", counter, i, matrix[i][matrix[0].length - 1 - counter]);

            result.add(matrix[i][matrix[0].length - 1 - counter]);
            visited[i][matrix[0].length - 1 - counter] = true;
        }

        // bottom
        for (int i = matrix[0].length - 1 - counter - 1; i >= counter; i--) {

            log.debug("counter: {}, i: {}, value: {}", counter, i, matrix[matrix.length - 1 - counter][i]);

            if (!visited[matrix.length - 1 - counter][i]) {
                result.add(matrix[matrix.length - 1 - counter][i]);
            }
        }

        // left
        for (int i = matrix.length - 1 - counter - 1; i > counter; i--) {

            log.debug("counter: {}, i: {}, value: {}", counter, i, matrix[i][counter]);

            if (!visited[i][counter]) {
                result.add(matrix[i][counter]);
            }
        }
    }

}
