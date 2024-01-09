package org.graphs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class CourseSchedule {

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        List<List<Integer>> adjList = new ArrayList<>();
        Queue<Integer> queue = new LinkedList<>();

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
            visitedCount++;
            for (int neighbor : adjList.get(course)) {
                if (--in_degree[neighbor] == 0) {
                    queue.offer(neighbor);
                }
            }
        }

        return visitedCount == numCourses;
    }

    public static void main(String[] args) {
        System.out.println(new CourseSchedule().canFinish(2, new int[][]{{1, 0}}));
    }

}
