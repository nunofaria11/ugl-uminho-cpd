/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JungTest;

import GraphADType.GraphADT;
import GraphADType.Support.Constants;
import GraphADType.Support.TArithmeticOperations;
import GraphADType.Support.UnionFind_ADT;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import java.util.ArrayList;
import java.util.Collection;
import java.util.PriorityQueue;

/**
 *
 * @author nuno
 */
public class KruskalJung<T, Y extends Comparable<Y>> {

    private PriorityQueue<EdgeJ<Y>> Q;
    private UnionFind_ADT<T> uf;
    private UndirectedSparseGraph graph;

    public KruskalJung(UndirectedSparseGraph graph) {
        this.graph = graph;
        this.uf = new UnionFind_ADT<T>(this.graph.getVertices());
        Q = new PriorityQueue<EdgeJ<Y>>(this.graph.getEdges());
    }

    public UndirectedSparseGraph getMst() {
        UndirectedSparseGraph<T, EdgeJ<Y>> mst = new UndirectedSparseGraph<T, EdgeJ<Y>>();
        //
        int edges_processed = 0;
        int edges_added = 0;
        while (edges_processed < graph.getEdgeCount() && edges_added < graph.getVertexCount() - 1) {
            EdgeJ minEdge = Q.poll();
            T n1 = (T) graph.getEndpoints(minEdge).getFirst();
            T n2 = (T) graph.getEndpoints(minEdge).getSecond();
            T ck1 = uf.find(n1);
            T ck2 = uf.find(n2);
            if (!ck1.equals(ck2)) { // if roots are different it means it doesnt have a cycle
                mst.addEdge(minEdge, n1, n2);
                // A U {(u,v)}
                uf.union(ck1, ck2);
                edges_added++;
            }
            edges_processed++;
        }
        //
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
        GraphADT g = JungConverter.createSmallGraphADT();
        JungConverter jconv = new JungConverter();

        KruskalJung kruskalJung = new KruskalJung((UndirectedSparseGraph) jconv.ADTtoJung(g));
        UndirectedSparseGraph mst_jung = kruskalJung.getMst();
        System.out.println(mst_jung);
        System.out.println("MST Jung weight: " + kruskalJung.getMstWeight(mst_jung, Constants.intArith));
    }
}
