package com.learn.graph;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import lombok.ToString;
import lombok.extern.log4j.Log4j2;


/**
 * LC - 133 - Clone Graph
 *
 * Medium
 *
 * Given a reference of a node in a connected undirected graph.
 * Return a deep copy (clone) of the graph.
 *
 * Each node in the graph contains a value (int) and a list (List[Node]) of its neighbors.
 *
 * class Node {
 *     public int val;
 *     public List<Node> neighbors;
 * }
 *
 * Test case format:
 *
 * For simplicity, each node's value is the same as the node's index (1-indexed). For example, the first node with val == 1, the second node with val == 2,
 * and so on. The graph is represented in the test case using an adjacency list.
 *
 * An adjacency list is a collection of unordered lists used to represent a finite graph. Each list describes the set of neighbors of a node in the graph.
 *
 * The given node will always be the first node with val = 1. You must return the copy of the given node as a reference to the cloned graph.
 *
 * Example 1:
 * Input: adjList = [[2,4],[1,3],[2,4],[1,3]]
 * Output: [[2,4],[1,3],[2,4],[1,3]]
 * Explanation: There are 4 nodes in the graph.
 * 1st node (val = 1)'s neighbors are 2nd node (val = 2) and 4th node (val = 4).
 * 2nd node (val = 2)'s neighbors are 1st node (val = 1) and 3rd node (val = 3).
 * 3rd node (val = 3)'s neighbors are 2nd node (val = 2) and 4th node (val = 4).
 * 4th node (val = 4)'s neighbors are 1st node (val = 1) and 3rd node (val = 3).
 *
 * Example 2:
 * Input: adjList = [[]]
 * Output: [[]]
 * Explanation: Note that the input contains one empty list. The graph consists of only one node with val = 1 and it does not have any neighbors.
 *
 * Example 3:
 * Input: adjList = []
 * Output: []
 * Explanation: This an empty graph, it does not have any nodes.
 *
 * Constraints:
 *     The number of nodes in the graph is in the range [0, 100].
 *     1 <= Node.val <= 100
 *     Node.val is unique for each node.
 *     There are no repeated edges and no self-loops in the graph.
 *     The Graph is connected and all nodes can be visited starting from the given node.
 */
@Log4j2
public class CloneGraph {

    public static void main(String[] args) {

        final int[][] nums = { { 2, 4 }, { 1, 3 }, { 2, 4 }, { 1, 3 } };

        Node node = Node.toNodes(nums);

        CloneGraph cloneGraph = new CloneGraph();

        Node ret = cloneGraph.cloneGraphLukeRecursion(node);

        log.debug("node: {}", ret);
    }

    /**
     * LC - You might get into in an interview when the problem statement might look a little absurd. What is important then is to 
     *      ask the interviewer to clarify the problem.
     * 
     * LC - Note: As we can see this question has garnered a lot of negative reviews. It has a lot more dislikes than the likes. 
     *      We have tried to improve the problem statement to make it more understandable. However, these are the kinds of situations
     *      you might get into in an interview when the problem statement might look a little absurd. What is important then is to 
     *      ask the interviewer to clarify the problem. This problem statement was confusing to me as well initially and that's why I 
     *      decided to write the solution hoping to clarify most of the doubts that the readers might have had.
     */

    /**
     * Luke - DFS - Recursion
     * 
     * (1) What is also crucial to understand is that we don't want to get stuck in a cycle while we are traversing the graph. 
     * (2) It is important to use "final private HashMap<Node, Node> visited = new HashMap<>();" state to prevent infinite loop.
     * (3) We will use a hash map to store the reference of the copy of all the nodes that have already been visited and copied. The key for the hash map
     *     would be the "node of the original graph" and corresponding value would be the corresponding cloned node of the cloned graph. The "visited" is
     *     used to prevent cycles and get the cloned copy of a node.
     * 
     * Runtime: 40 ms, faster than 56.55% of Java online submissions for Clone Graph.
     * Memory Usage: 43.5 MB, less than 44.54% of Java online submissions for Clone Graph.
     * 
     * Time: O(N + M), where N is a number of nodes (vertices) and M is a number of edges.
     * Space: O(N) - This space is occupied by the visited hash map and in addition to that, space would also be occupied by the recursion stack 
     *               since we are adopting a recursive approach here. The space occupied by the recursion stack would be equal to O(H) where H is
     *               the height of the graph. Overall, the space complexity would be O(N).
     */
    public Node cloneGraphLukeRecursion(Node node) {
        if (node == null) {
            return null;
        }

        Node clone = null;

        if (visited.containsKey(node)) {
            clone = visited.get(node);
        } else {
            Node newNode = new Node(node.val);
            clone = newNode;
            visited.put(node, clone);

            node.neighbors.forEach(e -> {
                newNode.neighbors.add(cloneGraphLukeRecursion(e));
            });
        }

        return clone;
    }

    /**
     * LC - DFS - Recursion
     * 
     * (1) What is also crucial to understand is that we don't want to get stuck in a cycle while we are traversing the graph. 
     * (2) It is important to use "final private HashMap<Node, Node> visited = new HashMap<>();" state to prevent infinite loop.
     * (3) We will use a hash map to store the reference of the copy of all the nodes that have already been visited and copied. The key for the hash map
     *     would be the "node of the original graph" and corresponding value would be the corresponding cloned node of the cloned graph. The "visited" is
     *     used to prevent cycles and get the cloned copy of a node.
     * 
     * Time: O(N + M), where N is a number of nodes (vertices) and M is a number of edges.
     * Space: O(N) - This space is occupied by the visited hash map and in addition to that, space would also be occupied by the recursion stack 
     *               since we are adopting a recursive approach here. The space occupied by the recursion stack would be equal to O(H) where H is
     *               the height of the graph. Overall, the space complexity would be O(N).
     */
    final private HashMap<Node, Node> visited = new HashMap<>();

    public Node cloneGraphLcDfsRecursion(Node node) {
        if (node == null) {
            return node;
        }

        // If the node was already visited before.
        // Return the clone from the visited dictionary.
        if (visited.containsKey(node)) {
            return visited.get(node);
        }

        // Create a clone for the given node.
        // Note that we don't have cloned neighbors as of now, hence [].
        Node cloneNode = new Node(node.val, new ArrayList<>());
        // The key is original node and value being the clone node.
        visited.put(node, cloneNode);

        // Iterate through the neighbors to generate their clones
        // and prepare a list of cloned neighbors to be added to the cloned node.
        for (Node neighbor : node.neighbors) {
            cloneNode.neighbors.add(cloneGraphLcDfsRecursion(neighbor));
        }
        return cloneNode;
    }

    /**
     * LC - BFS - Iterative
     * 
     */
    public Node cloneGraphLcBfsIterative(Node node) {
        if (node == null) {
            return node;
        }

        // Hash map to save the visited node and it's respective clone
        // as key and value respectively. This helps to avoid cycles.
        final HashMap<Node, Node> visited = new HashMap<>();

        // Put the first node in the queue
        final LinkedList<Node> queue = new LinkedList<Node> ();
        queue.add(node);
        
        // Clone the node and put it in the visited dictionary.
        visited.put(node, new Node(node.val, new ArrayList<>()));

        // Start BFS traversal
        while (!queue.isEmpty()) {
            // Pop a node say "n" from the from the front of the queue.
            Node n = queue.remove();
            // Iterate through all the neighbors of the node "n"
            for (Node neighbor: n.neighbors) {
                if (!visited.containsKey(neighbor)) {
                    // Clone the neighbor and put in the visited, if not present already
                    visited.put(neighbor, new Node(neighbor.val, new ArrayList<>()));
                    // Add the newly encountered node to the queue.
                    queue.add(neighbor);
                }
                // Add the clone of the neighbor to the neighbors of the clone node "n".
                visited.get(n).neighbors.add(visited.get(neighbor));
            }
        }

        // Return the clone of the node from visited.
        return visited.get(node);
    }
}


@ToString
class Node {
    public int val;
    public List<Node> neighbors;

    public Node() {
        val = 0;
        neighbors = new ArrayList<Node>();
    }

    public Node(int _val) {
        val = _val;
        neighbors = new ArrayList<Node>();
    }

    public Node(int _val, ArrayList<Node> _neighbors) {
        val = _val;
        neighbors = _neighbors;
    }

    public static Node toNode(int val, final int[] neighbors) {
        Node node = new Node(val);
        List<Node> list = new ArrayList<>();

        if (neighbors != null) {
            for (int v : neighbors) {
                list.add(new Node(v));
            }
        }

        node.neighbors = list;

        return node;
    }

    public static Node toNodes(final int[][] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }

        Node root = new Node(1);

        List<Node> list = new ArrayList<>();
        root.neighbors = list;

        if (nums != null) {
            for (int i = 0; i < nums.length; i++) {
                Node node = new Node(i + 1);
                list.add(node);
                List<Node> neibs = new ArrayList<>();
                node.neighbors = neibs;
                for (int k : nums[i]) {
                    neibs.add(new Node(k));
                }
            }
        }

        return root;
    }
}
