package org.graphs;

import java.util.*;

public class CloneGraph {

    public Node cloneGraph(Node node) {
        if (node == null) {
            return null;
        }

        Queue<Node> queue = new ArrayDeque<>();
        Map<Node, Node> map = new HashMap<>();

        queue.offer(node);
        map.put(node, new Node(node.val));

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            for (Node neighbor : current.neighbors) {
                if (!map.containsKey(neighbor)) {
                    map.put(neighbor, new Node(neighbor.val));
                    queue.offer(neighbor);
                }
                map.get(current).neighbors.add(map.get(neighbor));
            }
        }

        return map.get(node);
    }

    public static void main(String[] args) {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);

        node1.neighbors.addAll(Arrays.asList(node2, node4));
        node2.neighbors.addAll(Arrays.asList(node1, node3));
        node3.neighbors.addAll(Arrays.asList(node2, node4));
        node4.neighbors.addAll(Arrays.asList(node1, node3));

        Node clonedGraph = new CloneGraph().cloneGraph(node1);
        System.out.println(clonedGraph);
    }

}
