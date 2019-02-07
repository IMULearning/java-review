package io.imulab.review.java.graph;

import java.util.LinkedHashMap;
import java.util.Map;

public class KosarajuSharirSCC<V extends Comparable<V>> {

    private final Map<V, Boolean> marked;
    private final Map<V, Integer> id;

    public KosarajuSharirSCC(final Graph<V> graph) {
        this.id = new LinkedHashMap<>();
        this.marked = new LinkedHashMap<>();

        DepthFirstSearch<V> dfs = new DepthFirstSearch<>(graph.reversed());
        int c = 0;
        System.out.println("topological order on reversed graph: " + dfs.getReversePost());
        for (V v : dfs.getReversePost()) {
            if (!isMarked(v)) {
                dfs(graph, v, c++);
            }

        }
    }

    private void dfs(Graph<V> graph, V v, int c) {
        marked.put(v, true);
        id.put(v, c);

        for (V w : graph.adj(v)) {
            if (!isMarked(w))
                dfs(graph, w, c);
        }
    }

    private boolean isMarked(V vertex) {
        return marked.getOrDefault(vertex, Boolean.FALSE);
    }

    public boolean isStronglyConnected(V v, V w) {
        Integer g1 = id.get(v);
        Integer g2 = id.get(w);
        return g1 != null && g2 != null && g1.equals(g2);
    }

    public void debug() {
        StringBuilder sb = new StringBuilder();

        sb.append("Groups:\n");
        id.forEach((v, i) -> sb.append("\t").append(v.toString()).append(": ").append(i).append("\n"));
        sb.append("\n");

        System.out.println(sb.toString());
    }
}
