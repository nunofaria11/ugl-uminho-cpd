/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JungTest;

import GraphADType.Algorithms.BoruvkaADT;
import GraphADType.GraphADT;
import GraphADType.Support.Constants;
import GraphADType.Support.GenSaveReadADT;
import GraphADType.Support.TArithmeticOperations;
import GraphADType.Support.UnionFind_ADT;
import GraphIO.GraphInput;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.graph.util.Pair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 *
 * @author nuno
 */
public class BoruvkaJung<T, Y extends Comparable<Y>> {

    private ArrayList<EdgeJ> wannabes;
    private HashMap<T, EdgeJ<Y>> nbors;
    private UnionFind_ADT<T> uf;
    private UndirectedSparseGraph<T, EdgeJ> graph;
    private final EdgeJ<Y> maxEdge;

    public BoruvkaJung(UndirectedSparseGraph g) {
        this.graph = g;
        this.wannabes = new ArrayList<EdgeJ>(g.getEdgeCount());
        this.nbors = new HashMap<T, EdgeJ<Y>>(g.getVertexCount());
        this.uf = new UnionFind_ADT<T>(g.getVertices());
        this.maxEdge = getMaxEdge();
    }

    private EdgeJ<Y> getMaxEdge() {
        Collection<EdgeJ> edges = graph.getEdges();
        Comparator<EdgeJ> max_comparator = new Comparator<EdgeJ>() {

            public int compare(EdgeJ o1, EdgeJ o2) {
                return o2.compareTo(o1);
            }
        };
        PriorityQueue<EdgeJ> q = new PriorityQueue<EdgeJ>(edges.size(), max_comparator);
        q.addAll(edges);
        return q.poll();
    }

    public UndirectedSparseGraph getMst() {
        UndirectedSparseGraph mst = new UndirectedSparseGraph();
        this.wannabes = new ArrayList<EdgeJ>(graph.getEdges());
        this.nbors = new HashMap<T, EdgeJ<Y>>(graph.getVertexCount());
        int next;
        // do this until there are no more wannabes
        for (int i = graph.getEdgeCount(); i != 0; i = next) {
            for (T node : graph.getVertices()) {
                nbors.put(node, maxEdge);
            }
            next = 0;
            T l, m;
            for (EdgeJ edge : wannabes) {
                Pair<T> nodePair = graph.getEndpoints(edge);
                l = uf.find(nodePair.getFirst());
                m = uf.find(nodePair.getSecond());
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
            for (T node : graph.getVertices()) {
                EdgeJ edge = nbors.get(node);
                if (!edge.equals(maxEdge)) {
                    Pair<T> node_pair = graph.getEndpoints(edge);
                    l = node_pair.getFirst();
                    m = node_pair.getSecond();
                    if (!uf.find(l, m)) { // if they are not the same root
                        uf.union(l, m);
                        mst.addEdge(edge, l, m);
                    }
                }
            }
        }
        return mst;
    }

    public Y getMstWeight(UndirectedSparseGraph<T, EdgeJ<Y>> g, TArithmeticOperations<Y> arith) {
        Y w = arith.zero_element();
        Collection<T> allnodes = g.getVertices();
        Collection<T> vis = new ArrayList<T>();
        for (T n1 : allnodes) {
            vis.add(n1);
            Collection<T> neighbors = g.getNeighbors(n1);
            for (T n2 : neighbors) {
                if (!vis.contains(n2)) {
                    w = arith.Add(w, g.findEdge(n1, n2).data);
                }
            }
        }
        return w;
    }

    public static void main(String[] args) {
//        GraphADT g = JungConverter.createSmallGraphADT();
        // generate random graph
        GenSaveReadADT.write();
        GraphInput gin = new GraphInput(GenSaveReadADT.file_name);
        GraphADT g = gin.readGraphADT();

        // first with GraphADT ...
        BoruvkaADT borADT = new BoruvkaADT(g);
        GraphADT mstADT = borADT.getMst();
        int total = 0;
        System.out.println("MST ADT weight: " + mstADT.getMstWeight(Constants.intArith, total));

        // ... now with JUNG
        JungConverter jconv = new JungConverter();
        BoruvkaJung borJung = new BoruvkaJung((UndirectedSparseGraph) jconv.ADTtoJung(g));
        UndirectedSparseGraph mst_jung = borJung.getMst();
        System.out.println("MST Jung weight: " + borJung.getMstWeight(mst_jung, Constants.intArith));
    }
}
