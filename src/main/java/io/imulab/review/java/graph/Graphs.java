package io.imulab.review.java.graph;

import java.util.*;

public class Graphs {

    public static <V extends Comparable<V>> Graph<V> directional() {
        return new DiGraph<>();
    }

    public static <V extends Comparable<V>> Graph<V> undirectional() {
        return new UnDiGraph<>();
    }

    public static <V extends Comparable<V>> void debug(Graph<V> graph) {
        StringBuilder sb = new StringBuilder();

        sb.append("Vertices: ");
        graph.all().forEach(o -> sb.append(o.toString()).append(" "));
        sb.append("\n");

        sb.append("Edges: ").append(graph.E()).append("\n");
        graph.all().forEach(v -> {
            sb.append("\t").append(v.toString()).append(": ");
            graph.adj(v).forEach(w -> sb.append(w.toString()).append(" "));
            sb.append("\n");
        });

        System.out.println(sb.toString());
    }

    private static class UnDiGraph<V extends Comparable<V>> implements Graph<V> {

        final Map<V, Set<V>> graph;
        V source;

        UnDiGraph() {
            this.graph = new LinkedHashMap<>();
        }

        @Override
        public V source() {
            return this.source;
        }

        @Override
        public void addEdge(V source, V dest) {
            if (this.source == null)
                this.source = source;

            ensureVertex(source);
            ensureVertex(dest);

            graph.get(source).add(dest);
            graph.get(dest).add(source);
        }

        @Override
        public Iterable<V> adj(V vertex) {
            return Collections.unmodifiableSet(graph.getOrDefault(vertex, Collections.emptySet()));
        }

        @Override
        public Iterable<V> all() {
            Set<V> all = new TreeSet<>();
            for (Set<V> e : graph.values())
                all.addAll(e);
            return Collections.unmodifiableSet(all);
        }

        @Override
        public int E() {
            int sum = 0;
            for (Set<V> e : graph.values())
                sum += e.size();
            return sum / 2;
        }

        @Override
        public Graph<V> reversed() {
            Graph<V> newGraph = new UnDiGraph<>();
            fillReverse(newGraph);
            return newGraph;
        }

        protected void fillReverse(Graph<V> newGraph) {
            graph.forEach((v, edges) -> {
                edges.forEach(v0 -> newGraph.addEdge(v0, v));
            });
        }

        void ensureVertex(V vertex) {
            this.graph.putIfAbsent(vertex, new HashSet<>());
        }
    }

    private static class DiGraph<V extends Comparable<V>> extends UnDiGraph<V> {

        public DiGraph() {
            super();
        }

        @Override
        public void addEdge(V source, V dest) {
            ensureVertex(source);
            graph.get(source).add(dest);
        }

        @Override
        public Iterable<V> all() {
            Set<V> all = new TreeSet<>();
            graph.forEach((key, value) -> {
                all.add(key);
                all.addAll(value);
            });
            return Collections.unmodifiableSet(all);
        }

        @Override
        public int E() {
            int sum = 0;
            for (Set<V> e : graph.values())
                sum += e.size();
            return sum;
        }

        @Override
        public Graph<V> reversed() {
            Graph<V> newGraph = new DiGraph<>();
            super.fillReverse(newGraph);
            return newGraph;
        }
    }
}
