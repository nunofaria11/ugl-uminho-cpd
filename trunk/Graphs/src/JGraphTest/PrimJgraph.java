/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JGraphTest;

import GraphADType.GraphADT;
import GraphADType.Support.Constants;
import GraphADType.Support.GenSaveReadADT;
import GraphADType.Support.TArithmeticOperations;
import GraphADT_2nd_try.EdgeJ;
import GraphADT_2nd_try.EdgeJHandler;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Random;
import org.jgrapht.graph.WeightedMultigraph;

/**
 *
 * @author nuno
 */
public class PrimJgraph<T, Y extends Comparable<Y>> {
    // EdgeJ is shared between JUNG and JGraph libraries

    ArrayList<T> visited;

    public PrimJgraph() {
        this.visited = new ArrayList<T>();
    }

    private void addVisited(T node, PriorityQueue<EdgeJ<Y>> Q, WeightedMultigraph<T, EdgeJ<Y>> graph) {
        visited.add(node);
        ArrayList<EdgeJ<Y>> removals = new ArrayList<EdgeJ<Y>>();
        for (EdgeJ e : Q) {

            T node1 = graph.getEdgeSource(e);
            T node2 = graph.getEdgeTarget(e);

            if (node2.equals(node)) {
                removals.add(e);
            }
            if (node1.equals(node)) {
                removals.add(e);
            }
        }
        Q.removeAll(removals);
    }

    private T getRandomStartNode(WeightedMultigraph<T, EdgeJ<Y>> graph) {
        ArrayList<T> nodes = new ArrayList<T>(graph.vertexSet());
        return nodes.get(new Random().nextInt(nodes.size()));
    }

    private ArrayList<T> getNeighbors(T node, WeightedMultigraph<T, EdgeJ<Y>> graph) {
        ArrayList<T> nbors = new ArrayList<T>();
        ArrayList<T> allnodes = new ArrayList<T>(graph.vertexSet());
        allnodes.remove(node);
        for (T otherNode : allnodes) {
            if (graph.containsEdge(node, otherNode)) {
                nbors.add(otherNode);
            }
        }
        return nbors;
    }

    public WeightedMultigraph getMst(WeightedMultigraph<T, EdgeJ<Y>> graph) {

        PriorityQueue<EdgeJ<Y>> Q = new PriorityQueue<EdgeJ<Y>>();
        WeightedMultigraph<T, EdgeJ<Y>> mst = new WeightedMultigraph(EdgeJ.class);
        T start = getRandomStartNode(graph);
        while (visited.size() < graph.vertexSet().size() - 1) {
            addVisited(start, Q, graph);
            ArrayList<T> nbors = new ArrayList<T>();
            nbors.addAll(getNeighbors(start, graph));
            HashMap<EdgeJ<Y>, T> endpoints_edges = new HashMap<EdgeJ<Y>, T>();
            for (T n2 : nbors) {
                if (!visited.contains(n2)) {
                    EdgeJ edge = graph.getEdge(start, n2);
                    endpoints_edges.put(edge, n2);
                }
            }
            Q.addAll(endpoints_edges.keySet());
            EdgeJ min = Q.poll();
            T minTarget = (graph.getEdgeSource(min).equals(start)) ? (graph.getEdgeTarget(min)) : (graph.getEdgeSource(min));
            mst.addVertex(start);
            mst.addVertex(minTarget);
            mst.addEdge(start, minTarget, min);
            start = minTarget;
        }
        return mst;
    }

    public Y getMstWeight(WeightedMultigraph<T, EdgeJ<Y>> g, TArithmeticOperations<Y> arith) {
        Y w = arith.zero_element();
        Collection<T> allnodes = g.vertexSet();
        Collection<T> vis = new ArrayList<T>();
        for (T n1 : allnodes) {
            vis.add(n1);
            Collection<T> nbors = getNeighbors(n1, g);
            for (T n2 : nbors) {
                if (!vis.contains(n2) && g.containsEdge(n1, n2)) {
                    w = arith.Add(w, g.getEdge(n1, n2).data);
                }
            }
        }
        return w;
    }

    public static void main(String[] args) {
//        GraphInput gin = new GraphInput(GenSaveReadADT.file_name);
//        GraphADT g = gin.readGraphADT();
        GraphADT<String, Integer> g = GenSaveReadADT.createSmallGraphADT();
        System.out.println(g);
        JGraphConverter jconv = new JGraphConverter();
        WeightedMultigraph<String, Integer> converted = jconv.ADTtoJGraph(g);
        System.out.println(converted);
        PrimJgraph primJgraph = new PrimJgraph();

        WeightedMultigraph mst_jgraph = primJgraph.getMst(converted);
        System.out.println(mst_jgraph);
        System.out.println("MST JGraph weight: " + primJgraph.getMstWeight(mst_jgraph, Constants.intArith));

    }
}
