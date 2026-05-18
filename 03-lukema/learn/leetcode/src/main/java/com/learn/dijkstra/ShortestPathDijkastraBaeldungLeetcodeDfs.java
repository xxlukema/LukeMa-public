package com.learn.dijkstra;

import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import lombok.extern.log4j.Log4j2;

/**
 * https://www.baeldung.com/java-dijkstra - best explained.
 * https://www.youtube.com/watch?v=NHLValhbnVE - Eric Programming - PriorityQueue.
 * 
 * AdjacencyList, GreedyDepthFirstSearch
 * 
 * Time Complexity = O(E + V^2) where:
 * V is the number of vertices
 * E is the total number of edges
 * 
 */
@Log4j2
public class ShortestPathDijkastraBaeldungLeetcodeDfs {

    public static void main(String[] args) {

        /**
         * 1. EdgeSet
         */
        Set<Edge> edgeSet = ShortestPathUtils.createEdgeSetBaeldung();
        log.debug(() -> "----------- Edge Set -----------");
        edgeSet.forEach(edge -> {
            log.debug("Vertices: {} - {}. edgeLength: {}", () -> edge.getVertices()[0], () -> edge.getVertices()[1],
                    () -> edge.getLength());
        });

        /**
         * 2. AdjacencyList
         */
        Map<String, Node> adjacentNodesGraph = ShortestPathUtils.edgeSetToAdjacencyNodes(edgeSet);

        /**
         * 3. Display graph
         */
        log.debug(() -> "----------- AdjacencyList -----------");
        adjacentNodesGraph.forEach((key, node) -> {
            node.getAdjacentNodeToEdgeLengthMap().forEach((kk, vv) -> {
                log.debug(
                        "This node key: {}. This node name (equals node key): {}. Adjacnet node name: {}. Edge length: {}",
                        () -> key, () -> node.getName(), () -> kk.getName(), () -> vv);
            });
        });

        ShortestPathDijkastraBaeldungLeetcodeDfs shortestPathDijkastraBaeldungLeetcodeDfs = new ShortestPathDijkastraBaeldungLeetcodeDfs();

        shortestPathDijkastraBaeldungLeetcodeDfs.findShortestPath(adjacentNodesGraph, "A");
    }

    private void findShortestPath(Map<String, Node> adjacentNodesGraph, String root) {

        /**
         * 1. Initial root/source node distance to zero.
         */
        Node rootNode = adjacentNodesGraph.get(root);
        rootNode.setDistance(0);
        rootNode.setFrom(rootNode.getName());

        /**
         * 2. Put the root/source node to unsettledNodeQueue PriorityQueue.
         */
        Queue<Node> unsettledNodeQueue = new PriorityQueue<>((a, b) -> a.getDistance() - b.getDistance());
        unsettledNodeQueue.add(rootNode);

        /**
         * 3. settledNodesSet contains shortest path from single surce.
         */
        Set<Node> settledNodeSet = ConcurrentHashMap.newKeySet();

        /**
         * 4. Visit unsettledNodeQueue PriorityQueue.
         */
        while (!unsettledNodeQueue.isEmpty()) {
            /**
             * 5. Get/Visit the node of shortest distance from the top of the PriorityQueue. This is current node.
             */
            Node currentNode = unsettledNodeQueue.poll();
            /**
             * 6. Update currentNode's neighbors' distances and add its neighbors to unsettledNodeQueue PriorityQueue.
             */
            currentNode.getAdjacentNodeToEdgeLengthMap().forEach((neighbor, edgeLength) -> {
                /**
                 * 7. Check the neighbors are not settled/visited to prevent cyclic. If the neighbor is in settledNodeSet, skip it.
                 */
                if (!settledNodeSet.contains(neighbor)) {
                    /**
                     * 8. Update the neighbor's distance.
                     */
                    int distance = currentNode.getDistance() + edgeLength;
                    if (distance < neighbor.getDistance()) {
                        neighbor.setDistance(distance);
                        neighbor.setFrom(currentNode.getName());
                    }
                    /**
                     * 9. Add the neighbor to unsettledNodeQueue PriorityQueue.
                     */
                    unsettledNodeQueue.add(neighbor);
                }
            });
            /**
             * Add current node to settled/visited node set.
             */
            settledNodeSet.add(currentNode);
        }

        log.debug(() -> "----------- Shortest Path -----------");

        /**
         * Settled/Visited node distances are shortest path from single source.
         */
        settledNodeSet.forEach(node -> {
            log.debug("Node name :{}. Distance from source: {}, Previous node name: {}", () -> node.getName(),
                    () -> node.getDistance(), () -> node.getFrom());
        });
    }

}
