package org.graphs;

import java.util.*;

public class TopologicalSortDFS {

    void dfs(Node node, boolean visited[], Stack<Integer> stack) {
        int val = node.val;
        visited[val] = true;
        List<Node> neighbors = node.neighbors;

        for (Node neighbor : neighbors) {
            if (!visited[neighbor.val])
                dfs(neighbor, visited, stack);
        }

        stack.push(val);
    }

    List<Integer> topoSort(List<Node> nodes) {
        int n = nodes.size();
        boolean[] visited = new boolean[n + 1];
        Stack<Integer> stack = new Stack<>();

        for (Node node : nodes) {
            if (!visited[node.val])
                dfs(node, visited, stack);
        }

        Collections.reverse(stack);
        return stack;
    }

    public static void main(String[] args) {
        Node node0 = new Node(0);
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);

        node2.neighbors.addAll(Arrays.asList(node3));
        node3.neighbors.addAll(Arrays.asList(node1));
        node4.neighbors.addAll(Arrays.asList(node0, node1));
        node5.neighbors.addAll(Arrays.asList(node0, node2));

        List<Node> nodes = new ArrayList<>();
        nodes.addAll(Arrays.asList(node0, node1, node2, node3, node4, node5));

        List<Integer> result = new TopologicalSortDFS().topoSort(nodes);
        System.out.println(result);
//        5  4  2  3  1  0
    }

}
