package org.graphs;

import java.util.*;

public class CourseSchedule2 {

    public int[] findOrder(int numCourses, int[][] prerequisites) {
        List<List<Integer>> adjList = new ArrayList<>();
        Queue<Integer> queue = new LinkedList<>();
        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < numCourses; i++) {
            adjList.add(new ArrayList<>());
        }

        int[] in_degree = new int[numCourses];
        int visitedCount = 0;

        for (int[] prerequisite : prerequisites) {
            int course = prerequisite[0];
            int preRequisiteCourse = prerequisite[1];
            adjList.get(preRequisiteCourse).add(course);
            in_degree[course]++;
        }

        for (int i = 0; i < numCourses; i++) {
            if (in_degree[i] == 0) {
                queue.offer(i);
            }
        }

        while (!queue.isEmpty()) {
            int course = queue.poll();
            result.add(course);
            visitedCount++;
            for (int neighbor : adjList.get(course)) {
                if (--in_degree[neighbor] == 0) {
                    queue.offer(neighbor);
                }
            }
        }

        if (visitedCount == numCourses) {
            return result.stream().mapToInt(Integer::intValue).toArray();
        }

        return new int[0];
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(new CourseSchedule2().findOrder(2, new int[][]{
                {1, 0}
        })));
    }

}
