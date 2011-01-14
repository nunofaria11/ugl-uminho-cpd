/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphADT_2nd_try;

import GraphADType.Support.TArithmeticOperations;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.PriorityQueue;

/**
 *
 * @author nuno
 */
public class Prim2<V extends Serializable, E extends Comparable<E>> {
    // AQUI FALTA EM VEZ DE USAR AS CLASSES EXTENDIDAS USAR A ABSTRACTA !!!

    //MELHORAR A VERSATILIDADE DISTO |
    //                               V
    public BaseGraph<V, E> getMst(UndirectedColoredGraph<V, E> g) {
        // create new instance of undirected graph
        BaseGraph<V, E> mst = g.create();
        // edge queue
        PriorityQueue<E> queue = new PriorityQueue<E>();
        V start_node = g.getRandomNode();
        while (mst.getSize() < g.getOrder() - 1) {
            g.visitVertex(start_node);      // ***
            for (E edge : queue) {
                if (g.isIncident(start_node, edge)) {
                    if (g.visitedVertex(g.getOpposite(start_node, edge))) {
                        g.visitEdge(edge);
                    }
                }
            }
            Collection<E> nbors = g.getInEdges(start_node);
            ArrayList<E> edgesToAdd = new ArrayList<E>();
            for (E edge : nbors) {
                if (!g.visitedEdge(edge)) {
                    edgesToAdd.add(edge);                    
                }
            }
            queue.addAll(edgesToAdd);
            E minEdge = queue.remove();
            while (g.visitedEdge(minEdge)) {
                minEdge = queue.remove();
            }
            Pair<V> pair = g.getEndpoints(minEdge);

            mst.addVertex(pair.first);
            mst.addVertex(pair.second);
            mst.addEdge(minEdge, g.getEndpoints(minEdge));
            g.visitEdge(minEdge);
            start_node = (g.visitedVertex(pair.first)) ? (pair.second) : (pair.first);
        }
        return mst;
    }

    public BaseGraph<V, E> getMst_IndexedColored(UndirectedIndexedColoredGraph<V, E> g) {
        // create new instance of undirected graph
        BaseGraph<V, E> mst = g.create();
        // edge queue
        PriorityQueue<E> queue = new PriorityQueue<E>();
        V start_node = g.getRandomNode();
        while (mst.getSize() < g.getOrder() - 1) {
            g.visitVertex(start_node);      // ***
            for (E edge : queue) {
                if (g.isIncident(start_node, edge)) {
                    if (g.visitedVertex(g.getOpposite(start_node, edge))) {
                        g.visitEdge(edge);
                    }
                }
            }
            Collection<E> nbors = g.getInEdges(start_node);
            ArrayList<E> edgesToAdd = new ArrayList<E>();
            for (E edge : nbors) {
                if (!g.visitedEdge(edge)) {
                    edgesToAdd.add(edge);
                }
            }
            queue.addAll(edgesToAdd);
            E minEdge = queue.remove();
            while (g.visitedEdge(minEdge)) {
                minEdge = queue.remove();
            }
            Pair<V> pair = g.getEndpoints(minEdge);

            mst.addVertex(pair.first);
            mst.addVertex(pair.second);
            mst.addEdge(minEdge, g.getEndpoints(minEdge));
            g.visitEdge(minEdge);
            start_node = (g.visitedVertex(pair.first)) ? (pair.second) : (pair.first);
        }
        return mst;
    }

    /**
     * This method works well because the operations are based on the interface.
     * Even if an edge is not a primary Class like "Integer", the arithmetic
     * operations are supported as longs as the arithmetic interface is correct
     *
     * Restriction: The graph must already be an MST. Think about flagging the
     *              graph.
     *
     * @param graph
     * @param arith
     * @return MST weight of an already in MST-form graph
     */
    public E getMstWeight(BaseGraph<V, E> graph, TArithmeticOperations<E> arith) {

        UndirectedColoredGraph<V, E> g = new UndirectedColoredGraph<V, E>(graph);

        E weight = arith.zero_element();
        for (V vertex : g.getVertices()) {
            for (E edge : g.getIncidentEdges(vertex)) {
                if (!g.visitedEdge(edge)) {
                    weight = arith.Add(weight, edge);
                    g.visitEdge(edge);
                }
            }
        }
        return weight;
    }

    public static void main(String[] args) {
        UndirectedColoredGraph<String, EdgeJ<Integer>> g = new UndirectedColoredGraph<String, EdgeJ<Integer>>();

        g.addVertex("A");
        g.addVertex("B");
        g.addVertex("C");
        g.addVertex("D");
        g.addVertex("E");
        g.addVertex("F");
        g.addVertex("G");
        EdgeJHandler handler = new EdgeJHandler();
        g.addEdge(new EdgeJ<Integer>(7, handler), "A", "B");
        g.addEdge(new EdgeJ<Integer>(5, handler), "A", "D");
        g.addEdge(new EdgeJ<Integer>(8, handler), "B", "C");
        g.addEdge(new EdgeJ<Integer>(9, handler), "B", "D");
        g.addEdge(new EdgeJ<Integer>(7, handler), "B", "E");
        g.addEdge(new EdgeJ<Integer>(5, handler), "C", "E");
        g.addEdge(new EdgeJ<Integer>(6, handler), "D", "F");
        g.addEdge(new EdgeJ<Integer>(15, handler), "D", "E");
        g.addEdge(new EdgeJ<Integer>(8, handler), "E", "F");
        g.addEdge(new EdgeJ<Integer>(9, handler), "E", "G");
        g.addEdge(new EdgeJ<Integer>(11, handler), "F", "G");

        System.out.println(g.toString());

        Prim2<String, EdgeJ<Integer>> prim = new Prim2<String, EdgeJ<Integer>>();

        UndirectedGraph<String, EdgeJ<Integer>> mst = (UndirectedGraph<String, EdgeJ<Integer>>) prim.getMst(g);
        System.out.println(mst);
        /*
         * MST weight
         */
        TArithmeticOperations<EdgeJ<Integer>> arith = new TArithmeticOperations<EdgeJ<Integer>>() {

            EdgeJHandler handler = new EdgeJHandler();

            public EdgeJ<Integer> Add(EdgeJ<Integer> a, EdgeJ<Integer> b) {
                return new EdgeJ<Integer>(a.data + b.data, handler);
            }

            public EdgeJ<Integer> Cat(EdgeJ<Integer> a, EdgeJ<Integer> b) {
                return Add(a, b);
            }

            public EdgeJ<Integer> zero_element() {
                return new EdgeJ<Integer>(0, handler);
            }
        };

        System.out.println("MST weight: "+prim.getMstWeight(mst, arith).data);

    }
}
