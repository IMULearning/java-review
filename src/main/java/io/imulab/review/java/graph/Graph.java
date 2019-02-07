package io.imulab.review.java.graph;

public interface Graph<V extends Comparable<V>> {

    /**
     * The source of the graph. The source argument on the first addEdge call will be set as source of this graph.
     *
     * @return  the source of the graph.
     */
    V source();

    /**
     * Add an edge between the source vertex and the destination vertex. Direction is left for the implementation
     * to decide.
     *
     * @param source    source vertex
     * @param dest      destination vertex
     */
    void addEdge(V source, V dest);

    /**
     * Returns all adjacent vertices to the vertex.
     *
     * @param vertex    vertex to query adjacent vertices for.
     * @return          an iterable of all adjacent vertices.
     */
    Iterable<V> adj(V vertex);

    /**
     * @return  the number of edges in the graph.
     */
    int E();

    /**
     * @return  all vertices in this graph, in an iterable order.
     */
    Iterable<V> all();

    /**
     * @return  the reverse graph of this graph.
     */
    Graph<V> reversed();
}
