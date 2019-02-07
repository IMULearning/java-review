package io.imulab.review.java.graph;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Stack;

public class DirectedCycle<V extends Comparable<V>> {

    private final Map<V, Boolean> marked;
    private final Map<V, V> edgeTo;
    private final Stack<V> cycle;
    private final Stack<V> callStack;

    public DirectedCycle(Graph<V> graph) {
        marked = new LinkedHashMap<>();
        edgeTo = new LinkedHashMap<>();
        cycle = new Stack<>();
        callStack = new Stack<>();

        for (V v : graph.all()) {
            if (!isMarked(v))
                dfs(graph, v);
        }
    }

    private void dfs(Graph<V> graph, V v) {
        callStack.push(v);
        marked.put(v, true);

        for (V w : graph.adj(v)) {
            if (hasCycle())
                return;
            else if (!isMarked(w)) {
                edgeTo.put(w, v);
                dfs(graph, w);
            } else if (callStack.contains(w)) {
                for (V x = v; x != null && !x.equals(w); x = edgeTo.get(x))
                    cycle.push(x);
                cycle.push(w);
                cycle.push(v);
            }
        }

        callStack.pop();
    }

    private boolean isMarked(V vertex) {
        return marked.getOrDefault(vertex, Boolean.FALSE);
    }

    public boolean hasCycle() {
        return !cycle.isEmpty();
    }

    public Iterable<V> getCycle() {
        return cycle;
    }

    public void reportCycle() {
        if (!hasCycle())
            return;

        StringBuilder sb = new StringBuilder();
        sb.append("Cycle detected!\n");
        sb.append("\t").append(getCycle().toString()).append("\n");

        System.out.println(sb);
    }
}
