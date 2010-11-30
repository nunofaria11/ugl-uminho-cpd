/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JungTest;

import GraphADType.GraphADT;
import GraphADType.Support.Constants;
import GraphADType.Support.TArithmeticOperations;
import edu.uci.ics.jung.algorithms.shortestpath.PrimMinimumSpanningTree;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.graph.util.Pair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Random;
import org.apache.commons.collections15.Factory;

/**
 *
 * @author nuno
 */
public class PrimJung<T, Y extends Comparable<Y>> {

    UndirectedSparseGraph<T, EdgeJ<Y>> graph;
    ArrayList<T> visited;
    PriorityQueue<EdgeJ<Y>> Q;

    public PrimJung(UndirectedSparseGraph<T, EdgeJ<Y>> graph) {
        this.graph = graph;
        this.visited = new ArrayList<T>();
        this.Q = new PriorityQueue<EdgeJ<Y>>();
        Q = new PriorityQueue<EdgeJ<Y>>(graph.getEdgeCount(), new Comparator<EdgeJ<Y>>() {

            public int compare(EdgeJ o1, EdgeJ o2) {
                return o1.compareTo(o2);
            }
        });
    }

    public void addVisited(T node) {
        visited.add(node);
        ArrayList<EdgeJ<Y>> removals = new ArrayList<EdgeJ<Y>>();
        for (EdgeJ e : Q) {
            Pair<T> node_pair = graph.getEndpoints(e);
            T node1 = node_pair.getFirst();
            T node2 = node_pair.getSecond();

            if (node2 == null) {
                System.out.println("edge node2 is null!!!");
            }
            if (node2.equals(node)) {
                removals.add(e);
            }
            if (node1.equals(node)) {
                removals.add(e);
            }
        }
        Q.removeAll(removals);
    }

    public String printEdges(ArrayList<EdgeJ<Y>> edges) {
        StringBuilder s = new StringBuilder();
        s.append("[");
        for (EdgeJ edge : edges) {
            s.append(graph.getEndpoints(edge)).append("[").append(edge.toString()).append("], ");
        }
        s.append("]");
        return s.toString();
    }

    private T getRandomStartNode() {
        ArrayList<T> nodes = new ArrayList<T>(graph.getVertices());
        return nodes.get(new Random().nextInt(graph.getVertexCount()));
    }

    public UndirectedSparseGraph getMst() {
        UndirectedSparseGraph<T, EdgeJ<Y>> mst = new UndirectedSparseGraph<T, EdgeJ<Y>>();
        T start = getRandomStartNode();
        while (visited.size() < graph.getVertexCount() - 1) {
            addVisited(start);
            ArrayList<T> nbors = new ArrayList<T>();
            nbors.addAll(graph.getNeighbors(start));
            HashMap<EdgeJ<Y>, T> endpoints_edges = new HashMap<EdgeJ<Y>, T>();
            for (T n2 : nbors) {
                if (!visited.contains(n2)) {
                    EdgeJ edge = graph.findEdge(start, n2);
                    endpoints_edges.put(edge, n2);
                }
            }
            Q.addAll(endpoints_edges.keySet());
            EdgeJ min = Q.poll();
            mst.addEdge(min, graph.getEndpoints(min));
            Pair<T> ends = graph.getEndpoints(min);
            start = (visited.contains(ends.getFirst())) ? (ends.getSecond()) : (ends.getFirst());
        }
        return mst;
    }



    public Y getMstWeight(UndirectedSparseGraph<T, EdgeJ<Y>> g, TArithmeticOperations<Y> arith) {
        Y w = arith.zero_element();
        Collection<T> allnodes = g.getVertices();
        Collection<T> vis = new ArrayList<T>();
        for (T n1 : allnodes) {
            vis.add(n1);
            Collection<T> nbors = g.getNeighbors(n1);
            for (T n2 : nbors) {
                if (!vis.contains(n2)) {
                    w = arith.Add(w, g.findEdge(n1, n2).data);
                }
            }
        }
        return w;
    }

    public static void main(String[] args) {
//        GraphInput gin = new GraphInput(GenSaveReadADT.file_name);
//        GraphADT g = gin.readGraphADT();
        GraphADT g = JungConverter.createSmallGraphADT();
        JungConverter jconv = new JungConverter();

        PrimJung primJung = new PrimJung((UndirectedSparseGraph) jconv.ADTtoJung(g));
        UndirectedSparseGraph mst_jung = primJung.getMst();
        System.out.println(mst_jung);
        System.out.println("MST Jung weight: " + primJung.getMstWeight(mst_jung, Constants.intArith));


        System.out.println("==========\n");
        UndirectedSparseGraph gjung = (UndirectedSparseGraph) jconv.ADTtoJung(g);
        Factory<Graph<String, Integer>> factory = new Factory<Graph<String, Integer>>() {

            public Graph<String, Integer> create() {
                return new UndirectedSparseGraph<String, Integer>();
            }
        };
        PrimMinimumSpanningTree<String, Integer> primJung_original = new PrimMinimumSpanningTree<String, Integer>(factory);
        System.out.println(primJung_original.transform(gjung));

    }
}
