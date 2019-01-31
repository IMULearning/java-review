package io.imulab.review.java.graph;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

/**
 * Implementation of {@link Graph} which uses adjacency list.
 */
public class AdjacencyGraph implements Graph {

    private static final Collection<Integer> EMPTY = new HashSet<>();

    private final int V;
    private Collection<Integer>[] adj;

    @SuppressWarnings("unchecked")
    public AdjacencyGraph(int v) {
        V = v;
        adj = new Collection[V];
        for (int i = 0; i < adj.length; i++) {
            adj[i] = new HashSet<>();
        }
    }

    @Override
    public void addEdge(int v, int w) {
        rangeCheck(v);
        rangeCheck(w);

        adj[v].add(w);
        adj[w].add(v);
    }

    @Override
    public Iterable<Integer> adj(int v) {
        rangeCheck(v);

        if (adj[v] == null)
            return EMPTY;
        return Collections.unmodifiableCollection(adj[v]);
    }

    @Override
    public int V() {
        return V;
    }

    @Override
    public int E() {
        int sum = 0;

        for (Collection<Integer> integers : adj)
            for (Integer j : integers)
                sum += j;

        return sum / 2;
    }

    // utilities

    private void rangeCheck(int i) {
        if (i < 0 || i > adj.length - 1)
            throw new IndexOutOfBoundsException("index " + i + " is out of bounds of adjacency matrix.");
    }
}
