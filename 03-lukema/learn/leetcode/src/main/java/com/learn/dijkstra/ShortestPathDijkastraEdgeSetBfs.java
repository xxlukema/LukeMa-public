package com.learn.dijkstra;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.extern.log4j.Log4j2;


/**
 * https://www.youtube.com/watch?v=pVfj6mxhdMw
 *
 * EdgeSet, BreadthFirstSearch
 */
@Log4j2
public class ShortestPathDijkastraEdgeSetBfs {

    private boolean valueChanged = false;

    public static void main(String[] args) {

        Set<Edge> edgeSet = ShortestPathUtils.createEdgeSet();

        ShortestPathDijkastraEdgeSetBfs shortestPathDijkastra = new ShortestPathDijkastraEdgeSetBfs();

        shortestPathDijkastra.findShortestPath(edgeSet, "A");

    }

    public void findShortestPath(Set<Edge> edgeSet, String root) {

        List<String> visited = new ArrayList<>();
        List<String> unvisited = new LinkedList<>();

        /**
         * Init unvisited
         */
        Set<String> vertices = new HashSet<>();
        edgeSet.forEach(edge -> {
            vertices.add(edge.getVertices()[0]);
            vertices.add(edge.getVertices()[1]);
        });
        unvisited.addAll(vertices);

        log.debug("Unvisited: {}", () -> unvisited);
        log.debug("Graph: {}", () -> edgeSet);

        /**
         * 2. Init distanceTree
         */
        Map<String, Vertex> shortestPath = new HashMap<>();

        /**
         * 3. Init root
         */
        shortestPath.put(root, new Vertex(root, 0, root));
        visited.add(root);
        unvisited.remove(root);

        unvisited.forEach(ch -> {
            shortestPath.put(ch, new Vertex(ch, Integer.MAX_VALUE, null));
        });

        /**
         * 4. Visit unvisited
         */
        while (true) {
            valueChanged = false;
            Iterator<String> itUnvisited = unvisited.iterator();
            while (itUnvisited.hasNext()) {
                // visiting B
                String visiting = itUnvisited.next();
                // Find the edges from visited veritx
                List<Edge> edges = this.findEdges(edgeSet, visiting);
                edges.stream()
                        .filter(
                                edge -> visited.contains(edge.getVertices()[0])
                                        || visited.contains(edge.getVertices()[1]))
                        .forEach(edge -> {
                            Vertex vertex = shortestPath.get(visiting);
                            String from = edge.getVertices()[0].equals(visiting) ? edge.getVertices()[1]
                                    : edge.getVertices()[0];
                            int distance = edge.getLength() + shortestPath.get(from).getDistance();
                            if (distance < vertex.getDistance()) {
                                vertex.setDistance(distance);
                                vertex.setFrom(from);
                                valueChanged = true;
                            }
                        });
                itUnvisited.remove();
                visited.add(visiting);
            }

            if (!valueChanged) {
                break;
            }

            unvisited.addAll(visited);
            unvisited.remove(root);
            visited.add(root);
        }

        /**
         * Log the path
         */
        shortestPath.forEach((key, vertex) -> {
            log.debug("{}: {}", () -> key, () -> vertex);
        });

    }

    private List<Edge> findEdges(Set<Edge> graph, String ch) {
        return graph.stream().filter(edge -> edge.getVertices()[0].equals(ch) || edge.getVertices()[1].equals(ch))
                .toList();
    }

}
