package com.learn.graph;


public class GraphUtils {
    public static void printGraph(int[][] graph) {
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < graph.length; r++) {
            for (int c = 0; c < graph[0].length; c++) {
                sb.append(graph[r][c]).append(' ');
            }
            sb.append(System.lineSeparator());
        }

        System.out.println();
        System.out.println(sb.toString());
    }

    public static void printGraph(char[][] graph) {
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < graph.length; r++) {
            for (int c = 0; c < graph[0].length; c++) {
                sb.append(graph[r][c]).append(' ');
            }
            sb.append(System.lineSeparator());
        }

        System.out.println();
        System.out.println(sb.toString());
    }

    /*
    public static void printGraph(Character[][] graph) {
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < graph.length; r++) {
            for (int c = 0; c < graph.length; c++) {
                sb.append(graph[r][c]).append(' ');
            }
            sb.append(System.lineSeparator());
        }
    
        System.out.println();
        System.out.println(sb.toString());
    }
    */

    public static <T> void printGraph(T[][] graph) {
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < graph.length; r++) {
            for (int c = 0; c < graph[0].length; c++) {
                sb.append(graph[r][c]).append(' ');
            }
            sb.append(System.lineSeparator());
        }

        System.out.println();
        System.out.println(sb.toString());
    }
}
