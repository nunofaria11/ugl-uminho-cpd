/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Algorithms;

import GraphADT.AdjLists.GraphAdjLists;
import GraphADT.AdjMatrix.GraphAdjMatrix;
import GraphADT.Edge;
import GraphADT.GraphADT;
import Support.UnionFind;
import java.util.ArrayList;

/**
 *
 * @author nuno
 */
public class Boruvka {

    private ArrayList<Edge> wannabes;
    private ArrayList<Edge> nbors;
    private UnionFind uf;
    private GraphADT G;

    public Boruvka(GraphAdjMatrix G) {
        this.G = G;
        wannabes = new ArrayList<Edge>();
        nbors = new ArrayList<Edge>();
        uf = new UnionFind(G.order());
    }

    public Boruvka(GraphAdjLists G) {
        this.G = G;
        wannabes = new ArrayList<Edge>();
        nbors = new ArrayList<Edge>();
        uf = new UnionFind(G.order());
    }

    public Boruvka(GraphADT G) {
        this.G = G;
        wannabes = new ArrayList<Edge>();
        nbors = new ArrayList<Edge>();
        uf = new UnionFind(G.order());
    }

    public ArrayList<Edge> getNbors() {
        return nbors;
    }

    public void setNbors(ArrayList<Edge> nbors) {
        this.nbors = nbors;
    }

    public UnionFind getUf() {
        return uf;
    }

    public void setUf(UnionFind uf) {
        this.uf = uf;
    }

    public ArrayList<Edge> getWannabes() {
        return wannabes;
    }

    public void setWannabes(ArrayList<Edge> wannabes) {
        this.wannabes = wannabes;
    }

    /*
     * [http://flylib.com/books/en/3.56.1.52/1/]
     * This implementation of Boruvka's MST algorithm uses a version of the
     * union-find ADT from Chapter 4 (with the single-parameter find added to
     * the interface) to associate indices with MST subtrees as they are built.
     * Each phase checks all the remaining edges; those that connect disjoint
     * subtrees are kept for the next phase. The array a has the edges not yet
     * discarded and not yet in the MST. The index N is used to store those being
     * saved for the next phase (the code resets E from N at the end of each phase)
     * and the index h is used to access the next edge to be checked. Each
     * component's nearest neighbor is kept in the array b with find component
     * numbers as indices. At the end of each phase, each component is united with
     * its nearest neighbor and the nearest-neighbor edges added to the MST.
     */
    public GraphADT MST_Boruvka_UnionFind() {
        GraphADT mst = (GraphADT) G.clone();
        mst.clean();
        mst.addVertices(G.order());

        Edge maxEdge = new Edge(-1, -1, Integer.MAX_VALUE);
        this.uf = new UnionFind(G.order());
        this.wannabes = new ArrayList<Edge>(G.size());

        ArrayList<Edge> allEdges = G.getAllEdges();

        for (int i = 0; i < allEdges.size(); i++) {
            wannabes.add(i, allEdges.get(i));
        }
        this.nbors = new ArrayList<Edge>(G.order());
        for (int i = 0; i < G.order(); i++) {
            nbors.add(i, null);
        }
        int next;

        // Repeat until there is only on tree
        for (int i = allEdges.size(); i != 0; i = next) {

            int l, m, n;
            for (int o = 0; o < G.order(); o++) {
                nbors.set(o, maxEdge);
            }
            // search every node of very sub-tree
            next = 0;
            for (Edge e : wannabes) {
                l = uf.find(e.getFrom());
                m = uf.find(e.getDest());

                if (l == m) {
                    continue;
                }
                if (e.getWeight() < nbors.get(l).getWeight()) {
                    this.nbors.set(l, e);
                }
                if (e.getWeight() < nbors.get(m).getWeight()) {
                    this.nbors.set(m, e);
                }
                this.wannabes.set(next, e);
                next++;
            }
            // for every vertex check its nearest neighbor
            for (n = 0; n < G.order(); n++) {
                Edge nEdge = this.nbors.get(n);
                if (!nEdge.equals(maxEdge)) {
                    l = nEdge.getFrom();
                    m = nEdge.getDest();
                    if (!uf.find(l, m)) {
                        uf.union(l, m);
                        mst.addEdge(nEdge);
                    }
                }
            }

        }


        return mst;
    }

    public static void main(String[] args) {

        GraphAdjLists g = new GraphAdjLists();
//        GraphAdjMatrix g = new GraphAdjMatrix();
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

        Boruvka bor = new Boruvka(g);
        GraphADT mst = bor.MST_Boruvka_UnionFind();
        System.out.println(g.toString());
        System.out.println(mst.toString());
        System.out.println("Total weight: " + mst.MST_TotalWeight());


    }
}
