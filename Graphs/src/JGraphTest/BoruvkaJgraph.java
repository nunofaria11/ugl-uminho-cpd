/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JGraphTest;

import GraphADType.GraphADT;
import GraphADType.Support.Constants;
import GraphADType.Support.GenSaveReadADT;
import GraphADType.Support.TArithmeticOperations;
import GraphADType.Support.UnionFind_ADT;
import JungTest.EdgeJ;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import org.jgrapht.graph.WeightedMultigraph;

/**
 *
 * @author nuno
 */
public class BoruvkaJgraph<T, Y extends Comparable<Y>> {

    private ArrayList<EdgeJ> wannabes;
    private HashMap<T, EdgeJ<Y>> nbors;
    private UnionFind_ADT<T> uf;
    private WeightedMultigraph<T, EdgeJ> graph;
    private final EdgeJ<Y> maxEdge;

    public BoruvkaJgraph(WeightedMultigraph g) {
        this.graph = g;
        this.wannabes = new ArrayList<EdgeJ>(g.edgeSet().size());
        this.nbors = new HashMap<T, EdgeJ<Y>>(g.vertexSet().size());
        this.uf = new UnionFind_ADT<T>(g.vertexSet());
        this.maxEdge = getMaxEdge();
    }

    private EdgeJ<Y> getMaxEdge() {
        Collection<EdgeJ> edges = graph.edgeSet();
        Comparator<EdgeJ> max_comparator = new Comparator<EdgeJ>() {

            public int compare(EdgeJ o1, EdgeJ o2) {
                return o2.compareTo(o1);
            }
        };
        PriorityQueue<EdgeJ> q = new PriorityQueue<EdgeJ>(edges.size(), max_comparator);
        q.addAll(edges);
        return q.poll();
    }

    public Y getMstWeight(WeightedMultigraph<T, EdgeJ<Y>> g, TArithmeticOperations<Y> arith) {
        Y w = arith.zero_element();
        Collection<T> allnodes = g.vertexSet();
        Collection<T> vis = new ArrayList<T>();
        for (T n1 : allnodes) {
            vis.add(n1);
            Collection<T> neighbors = getNeighbors(n1);
            for (T n2 : neighbors) {
                if (!vis.contains(n2) && g.containsEdge(n1, n2)) {
                    w = arith.Add(w, g.getEdge(n1, n2).data);
                }
            }
        }
        return w;
    }

    private ArrayList<T> getNeighbors(T node) {
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

    public WeightedMultigraph getMst() {
        WeightedMultigraph mst = new WeightedMultigraph(EdgeJ.class);
        this.wannabes = new ArrayList<EdgeJ>(graph.edgeSet());
        this.nbors = new HashMap<T, EdgeJ<Y>>(graph.vertexSet().size());
        int next;
        // do this until there are no more wannabes
        for (int i = graph.edgeSet().size(); i != 0; i = next) {
            for (T node : graph.vertexSet()) {
                nbors.put(node, maxEdge);
            }
            next = 0;
            T l, m;
            for (EdgeJ edge : wannabes) {
                l = uf.find(graph.getEdgeSource(edge));
                m = uf.find(graph.getEdgeTarget(edge));
                if (l.equals(m)) {
                    continue;
                }
                // if this one is lower than the one in neighbors then add it
                if (edge.compareTo(nbors.get(l)) == -1) {
                    nbors.put(l, edge);
                }
                // if this one is also lower than the one in neighbors then add it
                if (edge.compareTo(nbors.get(m)) == -1) {
                    nbors.put(m, edge);
                }
                wannabes.set(next, edge);
                next++;
            }
            // for every vertex check its nearest neighbor
            for (T node : graph.vertexSet()) {
                EdgeJ edge = nbors.get(node);
                if (!edge.equals(maxEdge)) {
                    l = graph.getEdgeSource(edge);
                    m = graph.getEdgeTarget(edge);
                    if (!uf.find(l, m)) { // if they are not the same root
                        uf.union(l, m);
                        mst.addEdge(edge, l, m);
                    }
                }
            }
        }
        return mst;
    }

    public static void main(String[] args) {

        GraphADT<String, Integer> g = GenSaveReadADT.createSmallGraphADT();
        System.out.println(g);
        JGraphConverter jconv = new JGraphConverter();
        WeightedMultigraph<String, Integer> converted = jconv.ADTtoJGraph(g);
        System.out.println(converted);
        KruskalJgraph borJgraph = new KruskalJgraph(converted);

        WeightedMultigraph mst_jgraph = borJgraph.getMst();
        System.out.println(mst_jgraph);
        System.out.println("MST JGraph weight: " + borJgraph.getMstWeight(mst_jgraph, Constants.intArith));

    }
}
