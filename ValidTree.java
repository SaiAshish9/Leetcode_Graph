package org.graphs;

import java.util.*;

public class ValidTree {

    public boolean validTree(int n, int[][] edges) {
        if (n == 0 || edges.length != n - 1)
            return false;

        List<Integer>[] graph = new List[n];
        Queue<Integer> queue = new ArrayDeque<>(Arrays.asList(0));
        Set<Integer> seen = new HashSet<>(Arrays.asList(0));

        for (int i = 0; i < n; ++i)
            graph[i] = new ArrayList<>();

        for (int[] edge : edges) {
            final int u = edge[0];
            final int v = edge[1];
            graph[u].add(v);
            graph[v].add(u);
        }

        while (!queue.isEmpty()) {
            int element = queue.poll();
            for (int v : graph[element]) {
                if (!seen.contains(v)) {
                    queue.offer(v);
                    seen.add(v);
                }
            }
        }
    
        return seen.size() == n;
    }

    public static void main(String[] args) {
        System.out.println(new ValidTree().validTree(5, new int[][]{
                {0, 1}, {0, 2}, {0, 3}, {1, 4}
        }));
    }

}
