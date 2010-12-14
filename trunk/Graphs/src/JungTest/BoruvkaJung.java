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
import GraphADType.Support.ForestInteger_ADT;
import GraphIO.GraphInput;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.graph.util.Pair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 *
 * @author nuno
 */
public class BoruvkaJung<T, Y extends Comparable<Y>> {

    private void addAllEdges(UndirectedSparseGraph mst, ArrayList<EdgeJ> mstEdges, UndirectedSparseGraph g) {
        for (EdgeJ edge : mstEdges) {
            mst.addEdge(edge, g.getEndpoints(edge));
        }
    }

    public UndirectedSparseGraph getMst(UndirectedSparseGraph<T, EdgeJ> g) {

        ArrayList<EdgeJ> wannabes = new ArrayList<EdgeJ>(g.getEdges());
        HashMap<T, EdgeJ<Y>> nbors = new HashMap<T, EdgeJ<Y>>(g.getVertexCount());
        ForestInteger_ADT<T> uf = new ForestInteger_ADT<T>(g.getVertices());
        UndirectedSparseGraph mst = new UndirectedSparseGraph();
        ArrayList<EdgeJ> mstEdges = new ArrayList<EdgeJ>();

        int nextIteration;
        // do this until there are no more wannabes
        for (int i = g.getEdgeCount(); i != 0; i = nextIteration) {
            for (T node : g.getVertices()) {
                nbors.put(node, null);
            }
            ArrayList<EdgeJ> edges2add = new ArrayList<EdgeJ>();
            nextIteration = 0;
            T l, m;
            for (EdgeJ edge : wannabes) {
                Pair<T> nodePair = g.getEndpoints(edge);
                l = uf.find(nodePair.getFirst());
                m = uf.find(nodePair.getSecond());
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
            for (T node : g.getVertices()) {
                EdgeJ edge = nbors.get(node);
                if (edge != null) {
                    Pair<T> node_pair = g.getEndpoints(edge);
                    l = node_pair.getFirst();
                    m = node_pair.getSecond();
                    if (!uf.find(l, m)) { // if they are not the same root
                        uf.union(l, m);
                        edges2add.add(edge);
                    }
                }
            }
            wannabes.removeAll(edges2add);
            mstEdges.addAll(edges2add);
        }
        addAllEdges(mst, mstEdges, g);
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
//        GenSaveReadADT.write();
        GraphInput gin = new GraphInput(GenSaveReadADT.file_name);
        GraphADT g = gin.readGraphADT();

        // first with GraphADT ...
        BoruvkaADT borADT = new BoruvkaADT(g);
        GraphADT mstADT = borADT.getMst();
        int total = 0;
        System.out.println("MST ADT weight: " + mstADT.getMstWeight(Constants.intArith, total));

        // ... now with JUNG
        JungConverter jconv = new JungConverter();
        BoruvkaJung borJung = new BoruvkaJung();
        UndirectedSparseGraph mst_jung = borJung.getMst((UndirectedSparseGraph) jconv.ADTtoJung(g));
        System.out.println("MST Jung weight: " + borJung.getMstWeight(mst_jung, Constants.intArith));
    }
}
