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
import java.util.PriorityQueue;
import org.jgrapht.graph.WeightedMultigraph;

/**
 *
 * @author nuno
 */
public class KruskalJgraph<T, Y extends Comparable<Y>> {

    public WeightedMultigraph getMst(WeightedMultigraph<T, EdgeJ<Y>> graph) {
        WeightedMultigraph<T, EdgeJ<Y>> mst = new WeightedMultigraph(EdgeJ.class);
        UnionFind_ADT<T> uf = new UnionFind_ADT<T>(graph.vertexSet());
        PriorityQueue<EdgeJ<Y>> Q = new PriorityQueue<EdgeJ<Y>>(graph.edgeSet());
        //
        int edges_processed = 0;
        int edges_added = 0;
        while (edges_processed < graph.edgeSet().size() && edges_added < graph.vertexSet().size() - 1) {
            EdgeJ minEdge = Q.poll();
            T n1 = (T) graph.getEdgeSource(minEdge);
            T n2 = (T) graph.getEdgeTarget(minEdge);
            T ck1 = uf.find(n1);
            T ck2 = uf.find(n2);
            if (!ck1.equals(ck2)) { // if roots are different it means it doesnt have a cycle
                mst.addVertex(n1);
                mst.addVertex(n2);
                mst.addEdge(n1, n2, minEdge);
                // A U {(u,v)}
                uf.union(ck1, ck2);
                edges_added++;
            }
            edges_processed++;
        }
        //
        return mst;
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

        GraphADT<String, Integer> g = GenSaveReadADT.createSmallGraphADT();
        System.out.println(g);
        JGraphConverter jconv = new JGraphConverter();
        WeightedMultigraph<String, Integer> converted = jconv.ADTtoJGraph(g);
        System.out.println(converted);
        KruskalJgraph kruskalJgraph = new KruskalJgraph();

        WeightedMultigraph mst_jgraph = kruskalJgraph.getMst(converted);
        System.out.println(mst_jgraph);
        System.out.println("MST JGraph weight: " + kruskalJgraph.getMstWeight(mst_jgraph, Constants.intArith));

    }
}
