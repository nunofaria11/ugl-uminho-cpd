/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphADT_2nd_try;

import GraphIO.GraphInput;
import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Try to create a graph that for each accessed vertex, a parallel thread reads
 * the neighbor edges and "touches" the opposite vertices so that it is already
 * in cache in case it needs to be accessed.
 * @author nuno
 */
public class UndirectedCachingGraph<V extends Serializable, E extends Comparable<E>> extends UndirectedGraph<V, E> {

    private static final long serialVersionUID = 2361610057686142827L;
    Pair<V> endpoints;
    Callable<Set<E>> fetcher = new Callable<Set<E>>() {

        public Set<E> call() throws Exception {
            return verts.get(endpoints.first);
        }
    };
    ExecutorService fetchingExecutor;

    public UndirectedCachingGraph(UndirectedGraph<V, E> ug) {
        for (V v : ug.getVertices()) {
            addVertex(v);
        }
        for (E e : ug.getEdges()) {
            Pair<V> p = ug.getEndpoints(e);
            addEdge(e, p.first, p.second);
        }
        endpoints = null;
        fetchingExecutor = Executors.newFixedThreadPool(1);
    }

    @Override
    public Pair<V> getEndpoints(E e) {
        if (!containsEdge(e)) {
            throw new IllegalArgumentException("Edge must exist in graph");
        }
        endpoints = edges.get(e);
        Future<Set<E>> future_edges = fetchingExecutor.submit(fetcher);
        try {
//            System.out.println("Future edges: " + future_edges.get());
            future_edges.get();
        } catch (InterruptedException ex) {
            Logger.getLogger(UndirectedCachingGraph.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(UndirectedCachingGraph.class.getName()).log(Level.SEVERE, null, ex);
        }
        return endpoints;
    }

    public static void main(String[] args) {

        long begin, end;

        GraphInput input = new GraphInput("../graph_500.g2");
        UndirectedGraph bgraph = (UndirectedGraph) input.readGraph2();
        System.out.println("Done reading...");
        Kruskal2 kruskal2 = new Kruskal2();

//        begin = System.currentTimeMillis();
//        kruskal2.getMst(bgraph);
//        end = System.currentTimeMillis();
//        System.out.println("Time 1: " + (end - begin));

        UndirectedCachingGraph bcgraph = new UndirectedCachingGraph(bgraph);

        kruskal2 = new Kruskal2();
        System.out.println("Starting...");
        begin = System.currentTimeMillis();
        kruskal2.getMst(bcgraph);
        end = System.currentTimeMillis();
        System.out.println("Time 2: " + (end - begin));


    }
}
