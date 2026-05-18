package com.learn.tree;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import lombok.extern.log4j.Log4j2;

/**
 * https://www.youtube.com/watch?v=NHLValhbnVE
 */
@Log4j2
class ShortestPathBfs {

  public static void main(String[] args) {

    int[][] times = {
        { 1, 2, 6 },
        { 1, 4, 1 },
        { 2, 4, 2 },
        { 4, 5, 1 },
        { 2, 5, 2 },
        { 2, 3, 5 },
        { 5, 3, 5 },
    };

    // n: number of edges
      final int N = times.length;

    // k: source
    int k = 1;

    ShortestPathBfs shortestPathBfs = new ShortestPathBfs();

    shortestPathBfs.networkDelayTime(times, N, k);
  }

  public int networkDelayTime(int[][] times, int n, int k) {
    // Build the graph table
    Map<Integer, List<int[]>> graph = new HashMap<>();
    for (int[] time : times) {
      int src = time[0];
      int tar = time[1];
      int weight = time[2];
      if (!graph.containsKey(src)) {
        graph.put(src, new LinkedList<>());
      }
      graph.get(src).add(new int[] { tar, weight });
    }

    log.info("graph {}", () -> graph);

    // Define min heap
    Queue<int[]> minHeap = new PriorityQueue<>((a, b) -> a[1] - b[1]);

    // Define a hashset to keep track of visited nodes
    Set<Integer> visited = new HashSet<>();

    minHeap.add(new int[] { k, 0 });

    int res = 0;

    // Perform BFS
    while (!minHeap.isEmpty()) {
      int[] top = minHeap.poll();
      int src = top[0];
      int srcWeight = top[1];
      if (visited.contains(src))
        continue;
      res = srcWeight;

      log.info("res: {}", res);

      visited.add(src);
      if (!graph.containsKey(src)) {
        continue;
      }
      for (int[] edge : graph.get(src)) {
        int tar = edge[0];
        int tarWeight = edge[1];
        minHeap.add(new int[] { tar, srcWeight + tarWeight });
      }

      log.info("minHeap: {}", () -> minHeap);
    }

    log.info("res: {}", res);

    return visited.size() == n ? res : -1;
  }
}
