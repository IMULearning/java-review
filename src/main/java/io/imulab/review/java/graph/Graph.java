package io.imulab.review.java.graph;

public interface Graph {

    /**
     * Add edge between two vertices.
     *
     * @param v first vertex
     * @param w second vertex
     */
    void addEdge(int v, int w);

    /**
     * Vertices adjacent to a vertex.
     *
     * @param v vertex to query adjacent vertices with.
     * @return  iterable collection of adjacent vertices.
     */
    Iterable<Integer> adj(int v);

    /**
     * Number of vertices.
     *
     * @return  the total number of vertices.
     */
    int V();

    /**
     * Number of edges.
     *
     * @return  the total number of edges.
     */
    int E();
}
