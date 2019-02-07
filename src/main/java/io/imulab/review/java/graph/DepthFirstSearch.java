package io.imulab.review.java.graph;

import java.util.*;

public class DepthFirstSearch<V extends Comparable<V>> {

    /**
     * An array keeping track of which vertices have been visited.
     */
    private final Map<V, Boolean> marked;

    /**
     * A map keeping track of the trace of the search. If there is an entry of k -> v, then we got to k from v.
     */
    private final Map<V, V> trace;

    /**
     * A map maintaining relation of vertex to an integer indicating the group id this vertex is in. If vertices
     * have the same componentId, they are in the same group.
     */
    private final Map<V, Integer> componentId;

    /**
     * Vertices in pre-order
     */
    private final Queue<V> pre;

    /**
     * Vertices in post order;
     */
    private final Queue<V> post;

    /**
     * Vertices in reversed post order;
     */
    private final Stack<V> reversePost;

    public DepthFirstSearch(Graph<V> graph) {
        pre = new LinkedList<>();
        post = new LinkedList<>();
        reversePost = new Stack<>();
        marked = new HashMap<>();
        trace = new LinkedHashMap<>();
        componentId = new HashMap<>();

        int c = 0;
        Stack<V> callStack = new Stack<>();
        for (V v : graph.all()) {
            if (!isMarked(v)) {
                dfs(graph, v, c++, callStack);
            }
        }
    }

    private void dfs(Graph<V> g, V v, int c, Stack<V> callStack) {
        callStack.push(v);

        pre.offer(v);
        marked.put(v, Boolean.TRUE);
        componentId.put(v, c);

        for (V w : g.adj(v)) {
            if (!isMarked(w)) {
                trace.putIfAbsent(w, v);
                dfs(g, w, c, callStack);
            }
        }

        post.offer(v);
        reversePost.push(v);

        callStack.pop();
    }

    private boolean isMarked(V vertex) {
        return marked.getOrDefault(vertex, Boolean.FALSE);
    }

    public Iterable<V> getPre() {
        return pre;
    }

    public Iterable<V> getPost() {
        return post;
    }

    public Iterable<V> getReversePost() {
        Queue<V> q = new LinkedList<>();
        //noinspection unchecked
        Stack<V> s = (Stack<V>) reversePost.clone();

        while (!s.isEmpty())
            q.offer(s.pop());

        return q;
    }

    public Map<V, Integer> getComponentId() {
        return componentId;
    }

    public void debug() {
        StringBuilder sb = new StringBuilder();

        sb.append("Trace:\n");
        trace.forEach((w, v) -> sb.append("\t").append(v.toString()).append(" -> ").append(w.toString()).append("\n"));

        sb.append("Pre Order:\n");
        sb.append("\t");
        getPre().iterator().forEachRemaining(o -> sb.append(o.toString()).append(" "));
        sb.append("\n");

        sb.append("Post Order:\n");
        sb.append("\t");
        getPost().iterator().forEachRemaining(o -> sb.append(o.toString()).append(" "));
        sb.append("\n");

        sb.append("Reversed Post Order:\n");
        sb.append("\t");
        getReversePost().iterator().forEachRemaining(o -> sb.append(o.toString()).append(" "));
        sb.append("\n");

        sb.append("Groups:\n");
        getComponentId().forEach((v, i) -> sb.append("\t").append(v.toString()).append(": ").append(i).append("\n"));
        sb.append("\n");

        System.out.println(sb.toString());
    }
}
