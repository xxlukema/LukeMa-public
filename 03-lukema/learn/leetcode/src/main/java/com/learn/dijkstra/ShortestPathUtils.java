package com.learn.dijkstra;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ShortestPathUtils {

    /**
     * 1A. Create EdgeSet
     */
    public static Set<Edge> createEdgeSetBaeldung() {

        Set<Edge> edgeSet = new HashSet<>();

        /**
         * 1A. EdgeSet
         */
        edgeSet.add(new Edge(new String[] { "A", "B" }, 10));
        edgeSet.add(new Edge(new String[] { "A", "C" }, 15));
        edgeSet.add(new Edge(new String[] { "B", "F" }, 15));
        edgeSet.add(new Edge(new String[] { "B", "D" }, 12));
        edgeSet.add(new Edge(new String[] { "D", "F" }, 1));
        edgeSet.add(new Edge(new String[] { "D", "E" }, 2));
        edgeSet.add(new Edge(new String[] { "F", "E" }, 5));
        edgeSet.add(new Edge(new String[] { "C", "E" }, 10));

        return edgeSet;
    }

    /**
     * 1A. Create EdgeSet
     */
    public static Set<Edge> createEdgeSet() {

        Set<Edge> edgeSet = new HashSet<>();

        /**
         * 1A. EdgeSet
         */
        edgeSet.add(new Edge(new String[] { "A", "B" }, 6));
        edgeSet.add(new Edge(new String[] { "A", "D" }, 1));
        edgeSet.add(new Edge(new String[] { "D", "B" }, 2));
        edgeSet.add(new Edge(new String[] { "D", "E" }, 1));
        edgeSet.add(new Edge(new String[] { "E", "B" }, 2));
        edgeSet.add(new Edge(new String[] { "C", "B" }, 5));
        edgeSet.add(new Edge(new String[] { "E", "C" }, 5));

        return edgeSet;
    }

    /**
     * 2. Build AdjacencyList from EdgeSet
     */
    public static Map<String, Node> edgeSetToAdjacencyNodes(Set<Edge> edgeSet) {

        Map<String, Node> adjacentNodesGraph = new ConcurrentHashMap<>();

        edgeSet.forEach(edge -> {

            String nodeName0 = edge.getVertices()[0];
            String nodeName1 = edge.getVertices()[1];

            Node node0 = adjacentNodesGraph.get(nodeName0);
            Node node1 = adjacentNodesGraph.get(nodeName1);

            if (node0 == null) {
                node0 = new Node(nodeName0, null, Integer.MAX_VALUE);
                adjacentNodesGraph.put(nodeName0, node0);
            }
            if (node1 == null) {
                node1 = new Node(nodeName1, null, Integer.MAX_VALUE);
                adjacentNodesGraph.put(nodeName1, node1);
            }

            node0.getAdjacentNodeToEdgeLengthMap().put(node1, edge.getLength());
            node1.getAdjacentNodeToEdgeLengthMap().put(node0, edge.getLength());
        });

        return adjacentNodesGraph;
    }

}
