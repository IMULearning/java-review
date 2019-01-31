package io.imulab.review.java.graph;

import java.util.*;

public class Search {

    /**
     * The graph used for search
     */
    private final Graph graph;

    /**
     * The declared source to start the search from.
     */
    private final int s;

    /**
     * Boolean array to track vertices history, true if visited, false if not.
     */
    private final boolean[] marked;

    /**
     * Integer array to track path history. If edgeTo[i] = j, then we got to vertex i from vertex j.
     */
    private final int[] edgeTo;

    public Search(Graph graph, int s) {
        this.graph = graph;
        this.s = s;
        this.marked = new boolean[graph.V()];
        this.edgeTo = new int[graph.V()];
    }

    public void dfs() {
        reset();
        dfs(s);
    }

    private void dfs(int s) {
        rangeCheck(s);

        marked[s] = true;

        for (Integer v : graph.adj(s)) {
            if (marked[v])
                continue;

            dfs(v);
            edgeTo[v] = s;
        }
    }

    /**
     * Computes shortest paths from increasing distance from the source. Hence, it computes the most efficient
     * path.
     */
    public void bfs() {
        reset();

        Queue<Integer> work = new LinkedList<>();

        work.offer(s);
        marked[s] = true;

        while (!work.isEmpty()) {
            int s = work.poll();

            for (Integer v : graph.adj(s)) {
                if (!marked[v]) {
                    work.offer(v);
                    marked[v] = true;
                    edgeTo[v] = s;
                }
            }
        }
    }

    public boolean hasPathTo(int v) {
        return marked[v];
    }

    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v))
            return Collections.EMPTY_LIST;

        Stack<Integer> paths = new Stack<>();
        for (int c = v; c != s; c = edgeTo[c])
            paths.push(c);

        return paths;
    }

    // utility

    private void rangeCheck(int index) {
        assert index >= 0 && index < graph.V();
    }

    private void reset() {
        for (int i = 0; i < marked.length; i++)
            marked[i] = false;
        for (int i = 0; i < marked.length; i++)
            edgeTo[i] = -1;
    }
}
