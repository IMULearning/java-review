package io.imulab.review.java.graph;

public class Test {

    // Graph is at Algorithms 4th edition page 589
    public static void main(String[] args) {
        Graph<Integer> g = Graphs.directional();
        g.addEdge(0, 1);
        g.addEdge(0, 5);

        g.addEdge(2, 0);
        g.addEdge(2, 3);

        g.addEdge(3, 2);
        g.addEdge(3, 5);

        g.addEdge(4, 3);
        g.addEdge(4, 2);

        g.addEdge(5, 4);

        g.addEdge(6, 0);
        g.addEdge(6, 4);
        g.addEdge(6, 8);
        g.addEdge(6, 9);

        g.addEdge(7, 6);
        g.addEdge(7, 9);

        g.addEdge(8, 6);

        g.addEdge(9, 10);
        g.addEdge(9, 11);

        g.addEdge(10, 12);

        g.addEdge(11, 4);
        g.addEdge(11, 12);

        g.addEdge(12, 9);

        //Graphs.debug(graph);
        //Graphs.debug(graph.reversed());

        //DepthFirstSearch<Integer> dfs = new DepthFirstSearch<>(graph);
        //dfs.debug();

        //TopologicalSort<Integer> sort = new TopologicalSort<>(graph);
        //System.out.println(sort.order());

        DirectedCycle<Integer> cycleFinder = new DirectedCycle<>(g);
        cycleFinder.reportCycle();

        KosarajuSharirSCC<Integer> scc = new KosarajuSharirSCC<>(g);
        scc.debug();
        System.out.println("0 and 1 is strongly connected? " + scc.isStronglyConnected(0, 1));
        System.out.println("3 and 8 is strongly connected? " + scc.isStronglyConnected(3, 8));
        System.out.println("2 and 3 is strongly connected? " + scc.isStronglyConnected(2, 3));
        System.out.println("5 and 12 is strongly connected? " + scc.isStronglyConnected(5, 12));
    }
}
