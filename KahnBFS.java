package org.graphs;

import java.util.*;

public class KahnBFS {

    List<Integer> process(List<Node> nodes) {
        int n = nodes.size();
        int[] in_degree = new int[n];
        Queue<Integer> queue = new LinkedList<>();

        for (int u = 0; u < n; u++) {
            for (Node x : nodes.get(u).neighbors)
                in_degree[x.val]++;
        }

        for (int i = 0; i < n; i++)
            if (in_degree[i] == 0)
                queue.add(i);

        List<Integer> result = new ArrayList<>();
        System.out.println(Arrays.toString(in_degree));

        while (!queue.isEmpty()) {
            int val = queue.poll();
            result.add(val);
            for (Node x : nodes.get(val).neighbors) {
                if (--in_degree[x.val] == 0)
                    queue.add(x.val);
            }
        }

        return result;
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
        node5.neighbors.addAll(Arrays.asList(node2, node0));

        List<Node> nodes = new ArrayList<>();
        nodes.addAll(Arrays.asList(node0, node1, node2, node3, node4, node5));

        List<Integer> result = new KahnBFS().process(nodes);
        System.out.println(result);
//        [4, 5, 2, 0, 3, 1]
    }

}
