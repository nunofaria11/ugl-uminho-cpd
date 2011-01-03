/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphADT_2nd_try;

import GraphADType.Support.ForestTree;
import GraphADType.Support.TArithmeticOperations;
import JungTest.EdgeJ;
import JungTest.EdgeJHandler;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author nuno
 */
public class Boruvka2<V, E extends Comparable<E>> {

    public UndirectedGraph<V, E> getMst(UndirectedGraph<V, E> g) {
        UndirectedGraph<V, E> mst = new UndirectedGraph<V, E>();

        /*
         * Wannabe edges
         */
        ArrayList<E> wannabes = new ArrayList<E>(g.getEdges());

        /*
         * Initialize forest
         */
        ForestTree<V> forest = new ForestTree<V>(g.getVertices());

        /*
         * Neighbors map
         */
        HashMap<V, E> nbors = new HashMap<V, E>(g.getOrder());

        /*
         * MST edges
         */
        ArrayList<E> mstEdges = new ArrayList<E>();

        int nextIteration;
        for (int i = g.getSize(); i != 0; i = nextIteration) {

            nbors = new HashMap<V, E>();

            nextIteration = 0;
            ArrayList<E> edges2add = new ArrayList<E>();

            for (E edge : wannabes) {

                Pair<V> endpoints = g.getEndpoints(edge);
                V v1 = forest.find(endpoints.first);
                V v2 = forest.find(endpoints.second);

                if (v1.equals(v2)) {
                    continue;
                }

                if (!nbors.containsKey(v1) || edge.compareTo(nbors.get(v1)) == -1) {
                    nbors.put(v1, edge);
                }
                if (!nbors.containsKey(v2) || edge.compareTo(nbors.get(v2)) == -1) {
                    nbors.put(v2, edge);
                }
                nextIteration++;
            }

            for (V vertex : nbors.keySet()) {
                E edge = nbors.get(vertex);
                Pair<V> endpoints = g.getEndpoints(edge);
                V v1 = forest.find(endpoints.first);
                V v2 = forest.find(endpoints.second);

                if (!forest.find(v1, v2)) {
                    forest.union(v1, v2);
                    edges2add.add(edge);
                }
            }
            //
            wannabes.removeAll(edges2add);
            for (E edge : edges2add) {
                mstEdges.add(edge);
            }
        }

        for (E edge : mstEdges) {
            Pair<V> endpoints = g.getEndpoints(edge);
            
            mst.addVertex(endpoints.first);
            mst.addVertex(endpoints.second);
            
            mst.addEdge(edge, endpoints.first, endpoints.second);
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

        Boruvka2<String, EdgeJ<Integer>> boruvka = new Boruvka2<String, EdgeJ<Integer>>();

        UndirectedGraph<String, EdgeJ<Integer>> mst = boruvka.getMst(g);
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

        System.out.println("MST weight: " + boruvka.getMstWeight(mst, arith).data);

    }
}
