/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphADT_2nd_try;

import GraphIO.GraphInput;
import java.io.Serializable;
import java.util.Collection;

/**
 * Try to create a graph that for each accessed vertex, a parallel thread reads
 * the neighbor edges and "touches" the opposite vertices so that it is already
 * in cache in case it needs to be accessed.
 * @author nuno
 */
public class UndirectedCachingGraph<V extends Serializable, E extends Comparable<E>> extends UndirectedGraph<V, E> {

    private static final long serialVersionUID = 2361610057686142827L;

    public UndirectedCachingGraph(UndirectedGraph<V, E> ug) {
        for (V v : ug.getVertices()) {
            addVertex(v);
        }
        for (E e : ug.getEdges()) {
            Pair<V> p = ug.getEndpoints(e);
            addEdge(e, p.first, p.second);
        }
    }

    @Override
    public Pair<V> getEndpoints(E e) {
        if (!containsEdge(e)) {
            throw new IllegalArgumentException("Edge must exist in graph");
        }

        final Pair<V> endpoints = edges.get(e);

        // create new thread to touch the neighbors edges
        Runnable r = new Runnable() {

            public void run() {
                Collection<E> cached_edges = getIncidentEdges(endpoints.first);
                cached_edges.addAll(getIncidentEdges(endpoints.second));
            }
        };

        r.run();

        return endpoints;
    }

    public static void main(String[] args) {

        long begin, end;

        GraphInput input = new GraphInput("../graph_1000.g2");
        UndirectedGraph bgraph = (UndirectedGraph) input.readGraph2();

        Kruskal2 kruskal2 = new Kruskal2();

        begin = System.currentTimeMillis();
        kruskal2.getMst(bgraph);
        end = System.currentTimeMillis();
        System.out.println("Time 1: " + (end - begin));

        UndirectedCachingGraph bcgraph = new UndirectedCachingGraph(bgraph);
        kruskal2 = new Kruskal2();
        begin = System.currentTimeMillis();
        kruskal2.getMst(bcgraph);
        end = System.currentTimeMillis();
        System.out.println("Time 2: " + (end - begin));


    }
}
