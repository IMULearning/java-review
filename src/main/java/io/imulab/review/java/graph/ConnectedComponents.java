package io.imulab.review.java.graph;

public class ConnectedComponents {

    private final Graph graph;

    /**
     * Boolean array to assist dfs.
     */
    private final boolean marked[];

    /**
     * Component id for each vertices. Vertices belonging to the same component will have same index.
     */
    private final int components[];

    public ConnectedComponents(Graph graph) {
        this.graph = graph;
        this.marked = new boolean[graph.V()];
        this.components = new int[graph.V()];

        int c = 0;
        for (int s = 0; s < graph.V(); s++) {
            if (marked[s])
                continue;

            dfs(s, c++);
        }
    }

    private void dfs(int s, final int c) {
        marked[s] = true;
        components[s] = c;

        for (Integer v : graph.adj(s)) {
            if (marked[v])
                continue;

            dfs(v, c);
        }
    }

    public int id(int v) {
        rangeCheck(v);
        return components[v];
    }

    public boolean isConnected(int v, int w) {
        rangeCheck(v);
        rangeCheck(w);

        return id(v) == id(w);
    }

    // utility

    private void rangeCheck(int index) {
        assert index >= 0 && index < graph.V();
    }
}
