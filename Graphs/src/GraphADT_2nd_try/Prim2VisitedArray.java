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
public class Prim2VisitedArray<V extends Serializable, E extends Comparable<E>> {

    ArrayList<V> visited;
//    PriorityQueue<E> Q;

    public Prim2VisitedArray() {
        this.visited = new ArrayList<V>();
    }

    public void addVisited(V node, UndirectedGraph<V, E> graph, PriorityQueue<E> Q) {
        visited.add(node);
        ArrayList<E> removals = new ArrayList<E>();
        for (E e : Q) {
            Pair<V> node_pair = graph.getEndpoints(e);
            V node1 = node_pair.first;
            V node2 = node_pair.second;
            if (node2.equals(node)) {
                removals.add(e);
            }
            if (node1.equals(node)) {
                removals.add(e);
            }
        }
        Q.removeAll(removals);
    }

    public UndirectedGraph getMst(UndirectedGraph<V, E> g) {

        UndirectedGraph<V, E> graph = g;
        UndirectedGraph<V, E> mst = new UndirectedGraph<V,E>();
        V start_node = g.getRandomNode();
        PriorityQueue<E> Q = new PriorityQueue<E>();
        while (visited.size() < graph.getOrder() - 1) {
            addVisited(start_node, graph, Q);
            Collection<E> nbors = g.getInEdges(start_node);
            ArrayList<E> edgesToAdd = new ArrayList<E>();
            for (E edge : nbors) {
                if (!visited.contains(g.getOpposite(start_node, edge))) {
                    edgesToAdd.add(edge);
                }
            }
            Q.addAll(edgesToAdd);
            E minEdge = Q.remove();

            Pair<V> pair = g.getEndpoints(minEdge);

            mst.addVertex(pair.first);
            mst.addVertex(pair.second);
            mst.addEdge(minEdge, g.getEndpoints(minEdge));

            start_node = (visited.contains(pair.first)) ? (pair.second) : (pair.first);
            
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
    public E getMstWeight(UndirectedGraph<V, E> graph, TArithmeticOperations<E> arith) {

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

        Prim2VisitedArray<String, EdgeJ<Integer>> prim = new Prim2VisitedArray<String, EdgeJ<Integer>>();

        UndirectedGraph<String, EdgeJ<Integer>> mst = prim.getMst(g);
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

        System.out.println("MST weight: " + prim.getMstWeight(mst, arith).data);

    }
}
