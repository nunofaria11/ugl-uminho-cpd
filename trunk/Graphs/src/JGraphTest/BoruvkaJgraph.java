/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JGraphTest;

import GraphADType.GraphADT;
import GraphADType.Support.Constants;
import GraphADType.Support.GenSaveReadADT;
import GraphADType.Support.TArithmeticOperations;
import GraphADType.Support.ForestInteger_ADT;
import JungTest.EdgeJ;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import org.jgrapht.graph.WeightedMultigraph;

/**
 *
 * @author nuno
 */
public class BoruvkaJgraph<T, Y extends Comparable<Y>> {

    public Y getMstWeight(WeightedMultigraph<T, EdgeJ<Y>> g, TArithmeticOperations<Y> arith) {
        Y w = arith.zero_element();
        Collection<T> allnodes = g.vertexSet();
        Collection<T> vis = new ArrayList<T>();
        for (T n1 : allnodes) {
            vis.add(n1);
            Collection<T> neighbors = getNeighbors(n1, g);
            for (T n2 : neighbors) {
                if (!vis.contains(n2) && g.containsEdge(n1, n2)) {
                    w = arith.Add(w, g.getEdge(n1, n2).data);
                }
            }
        }
        return w;
    }

    private ArrayList<T> getNeighbors(T node, WeightedMultigraph<T, EdgeJ<Y>> graph) {
        ArrayList<T> neighbors = new ArrayList<T>();
        ArrayList<T> allnodes = new ArrayList<T>(graph.vertexSet());
        allnodes.remove(node);
        for (T otherNode : allnodes) {
            if (graph.containsEdge(node, otherNode)) {
                neighbors.add(otherNode);
            }
        }
        return neighbors;
    }

    private void addAllEdges(WeightedMultigraph<T, EdgeJ> mst, ArrayList<EdgeJ> mstEdges, WeightedMultigraph<T, EdgeJ> g) {
        for (EdgeJ edge : mstEdges) {
            T source = g.getEdgeSource(edge);
            T target = g.getEdgeTarget(edge);
            if (!mst.containsVertex(source)) {
                mst.addVertex(source);
            }
            if (!mst.containsVertex(target)) {
                mst.addVertex(target);
            }
            mst.addEdge(source, target, edge);
        }
    }

    public WeightedMultigraph getMst(WeightedMultigraph<T, EdgeJ> graph) {
        WeightedMultigraph mst = new WeightedMultigraph(EdgeJ.class);
        ArrayList<EdgeJ> wannabes = new ArrayList<EdgeJ>(graph.edgeSet());
        HashMap<T, EdgeJ<Y>> nbors = new HashMap<T, EdgeJ<Y>>(graph.vertexSet().size());
        ForestInteger_ADT<T> uf = new ForestInteger_ADT<T>(graph.vertexSet());

        ArrayList<EdgeJ> mstEdges = new ArrayList<EdgeJ>();

        int nextIteration;
        // do this until there are no more wannabes
        for (int i = graph.edgeSet().size(); i != 0; i = nextIteration) {
            for (T node : graph.vertexSet()) {
                nbors.put(node, null);
            }
            ArrayList<EdgeJ> edges2add = new ArrayList<EdgeJ>();
            nextIteration = 0;
            T l, m;
            for (EdgeJ edge : wannabes) {
                l = uf.find(graph.getEdgeSource(edge));
                m = uf.find(graph.getEdgeTarget(edge));
                if (l.equals(m)) {
                    continue;
                }
                // if this one is lower than the one in neighbors then add it
                if (nbors.get(l) == null || edge.compareTo(nbors.get(l)) == -1) {
                    nbors.put(l, edge);
                }
                // if this one is also lower than the one in neighbors then add it
                if (nbors.get(m) == null || edge.compareTo(nbors.get(m)) == -1) {
                    nbors.put(m, edge);
                }
//                wannabes.set(nextIteration, edge);
                nextIteration++;
            }
            // for every vertex check its nearest neighbor
            for (T node : graph.vertexSet()) {

                EdgeJ edge = nbors.get(node);
                if (edge != null) {
                    l = graph.getEdgeSource(edge);
                    m = graph.getEdgeTarget(edge);
                    if (!uf.find(l, m)) { // if they are not the same root
                        uf.union(l, m);
//                        mst.addEdge(edge, l, m);
                        edges2add.add(edge);
                    }
                }
            }
            wannabes.removeAll(edges2add);
            mstEdges.addAll(edges2add);
        }
        addAllEdges(mst, mstEdges, graph);
        return mst;
    }

    public static void main(String[] args) {

        GraphADT<String, Integer> g = GenSaveReadADT.createSmallGraphADT();
        System.out.println(g);
        JGraphConverter jconv = new JGraphConverter();
        WeightedMultigraph<String, Integer> converted = jconv.ADTtoJGraph(g);
        System.out.println(converted);
        BoruvkaJgraph borJgraph = new BoruvkaJgraph();

        WeightedMultigraph mst_jgraph = borJgraph.getMst(converted);
        System.out.println(mst_jgraph);
        System.out.println("MST JGraph weight: " + borJgraph.getMstWeight(mst_jgraph, Constants.intArith));

    }
}
