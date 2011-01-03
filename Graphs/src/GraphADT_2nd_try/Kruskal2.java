/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphADT_2nd_try;

import GraphADType.Support.ForestTree;
import GraphADType.Support.TArithmeticOperations;
import JungTest.EdgeJ;
import JungTest.EdgeJHandler;
import java.util.PriorityQueue;

/**
 *
 * @author nuno
 */
public class Kruskal2<V, E extends Comparable<E>> {

    public UndirectedGraph<V, E> getMst(UndirectedGraph<V, E> g) {
        /*
         * EDGE QUEUE:  init with all edges of the graph, no need to control
         *              repeated edges because there are no repeated edges
         *              in the graph.
         */
        PriorityQueue<E> queue = new PriorityQueue<E>(g.getEdges());

        /*
         * New MST graph to return
         */
        UndirectedGraph<V, E> mst = new UndirectedGraph<V, E>();

        /*
         * Initialize forest tree with all vertices: each vertex will be inserted
         * in its own tree
         */
        ForestTree<V> forest = new ForestTree<V>(g.getVertices());

        while (mst.getSize() < g.getOrder() - 1) {
            E minEdge = queue.remove();
            Pair<V> endpoints = g.getEndpoints(minEdge);
            V v1 = forest.find(endpoints.first);
            V v2 = forest.find(endpoints.second);
            if(!v1.equals(v2)){
                mst.addVertex(v1);
                mst.addVertex(v2);
                mst.addEdge(minEdge, endpoints);
                forest.union(v1, v2);
            }
        }

        return mst;
    }

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

        Kruskal2<String, EdgeJ<Integer>> kruskal = new Kruskal2<String, EdgeJ<Integer>>();

        UndirectedGraph<String, EdgeJ<Integer>> mst = kruskal.getMst(g);
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

        System.out.println("MST weight: "+kruskal.getMstWeight(mst, arith).data);

    }
}
