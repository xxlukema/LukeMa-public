package com.learn.graph;


public class RotateImage {

    public static void main(String[] args) {

        char[][] matrix = {
                { '1', '2', '3', 'a' },
                { '4', '5', '6', 'b' },
                { '7', '8', '9', 'c' },
                { 'w', 'x', 'y', 'z' } };

        GraphUtils.printGraph(matrix);

        RotateImage rotateImage = new RotateImage();
        // rotateImage.rotateLuke(matrix);
        rotateImage.rotateLC(matrix);

        GraphUtils.printGraph(matrix);
    }

    /**
     * Rotate 90 degrees clockwise
     */
    public void rotateLuke(char[][] graph) {

          final int N = graph.length;

        for (int row = 0; row < (N + 1) / 2; row++) {
            for (int col = 0; col < N / 2; col++) {

                // char tmp = graph[col][N - 1 - row];

                //graph[col][N - 1 - row] = 

            }
        }
    }

    public void rotateLC(char[][] matrix) {

          final int N = matrix.length;

        for (int row = 0; row < (N + 1) / 2; row++) {
            for (int col = 0; col < (N + 0) / 2; col++) {

                char temp = matrix[N - 1 - col][row];

                matrix[N - 1 - col][row] = matrix[N - 1 - row][N - col - 1];
                matrix[N - 1 - row][N - col - 1] = matrix[col][N - 1 - row];
                matrix[col][N - 1 - row] = matrix[row][col];
                matrix[row][col] = temp;
            }
        }
    }
}
