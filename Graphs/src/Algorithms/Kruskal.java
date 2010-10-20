/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Algorithms;

import GraphADT.AdjLists.GraphAdjLists;
import GraphADT.AdjMatrix.GraphAdjMatrix;
import GraphADT.Edge;
import GraphADT.GraphADT;
import Support.KruskalInterface;
import Support.UnionFind;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author nuno
 */
public class Kruskal implements KruskalInterface {

    private ArrayList<Edge> Q;
    private UnionFind uf;
    private GraphADT G;

    public Kruskal(GraphADT g) {
        G = g;
        Q = new ArrayList<Edge>();
        uf = new UnionFind(G.order());
    }
    
    /*
     * Sorts all adges in the tree
     */
    public void initQ() {
        ArrayList<Integer> visited = new ArrayList<Integer>();
        Q = new ArrayList<Edge>();
        for (int i = 0; i < G.order(); i++) {
            visited.add(i);
            ArrayList<Edge> edges = G.getEdgeNeighbors(i);//(ArrayList<Edge>) _adj.get(i);
            ArrayList<Edge> edgesToAdd = new ArrayList<Edge>();
            for (Edge e : edges) {
                if (!visited.contains(e.getDest())) {
                    edgesToAdd.add(e);
                }
            }
            Q.addAll(edgesToAdd);
        }
        Collections.sort(Q);
        visited = new ArrayList<Integer>();
    }

    public void addToQ(ArrayList<Edge> x) {
        Q.addAll(x);
        Collections.sort(Q);
    }

    public Edge extractMin() {
        return Q.remove(0);
    }

    public GraphADT MST_Kruskal_UnionFind() {
        //http://penguin.ewu.edu/cscd327/Topic/Graph/Kruskal/Set_Union_Find.html
        GraphADT mst = (GraphADT) G.clone();
        mst.clean();
        mst.addVertices(G.order());
        //
        initQ();
        //initialize forest
        uf = new UnionFind(G.order());

        int edges_processed = 0;
        int edges_added = 0;
        while (edges_processed < G.size() && edges_added < G.order() - 1) {
            Edge minEdge = extractMin();
            int ck1 = uf.find(minEdge.getFrom());
            int ck2 = uf.find(minEdge.getDest());
            if (ck1 != ck2) { // if roots are different it means it doesnt have a cycle
                mst.addEdge(minEdge);
                // A U {(u,v)}
                uf.union(ck1, ck2);
                edges_added++;
            }
            edges_processed++;
        }
        //
        return mst;
    }

    public ArrayList<Edge> getQ() {
        return Q;
    }

    public void setQ(ArrayList<Edge> q) {
        this.Q = q;
    }

    public UnionFind getUf() {
        return uf;
    }

    public void setUf(UnionFind uf) {
        this.uf = uf;
    }

    public static void main(String[] args) {
//        GraphAdjLists g = new GraphAdjLists();
        GraphAdjMatrix g = new GraphAdjMatrix();
        g.addVertices(7);
        g.addEdge(0, 1, 7);
        g.addEdge(0, 3, 5);
        g.addEdge(1, 2, 8);
        g.addEdge(1, 3, 9);
        g.addEdge(1, 4, 7);
        g.addEdge(2, 4, 5);
        g.addEdge(3, 4, 15);
        g.addEdge(3, 5, 6);
        g.addEdge(4, 5, 8);
        g.addEdge(4, 6, 9);
        g.addEdge(5, 6, 11);

        Kruskal kruskal = new Kruskal(g);
        GraphADT mst = kruskal.MST_Kruskal_UnionFind();
        System.out.println(g.toString());
        System.out.println(mst.toString());
        System.out.println("Total weight: " + mst.MST_TotalWeight());

    }
}
