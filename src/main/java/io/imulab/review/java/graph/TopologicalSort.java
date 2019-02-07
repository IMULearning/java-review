package io.imulab.review.java.graph;

public class TopologicalSort<V extends Comparable<V>> {

    private Iterable<V> order;

    public TopologicalSort(Graph<V> graph) {
        DirectedCycle cycleFinder = new DirectedCycle(graph);
        if (!cycleFinder.hasCycle()) {
            DepthFirstSearch<V> dfs = new DepthFirstSearch<>(graph);
            this.order = dfs.getReversePost();
        }
    }

    public Iterable<V> order() {
        return order;
    }
}
