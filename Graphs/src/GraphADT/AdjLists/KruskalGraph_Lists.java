/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphADT.AdjLists;

import GraphADT.Edge;
import Support.UnionFind;
import java.util.Collections;
import java.util.ArrayList;
import java.util.TreeSet;

/**
 *
 * @author nuno
 */
public class KruskalGraph_Lists extends GraphAdjLists {

    private ArrayList<Edge> Q;
    private ArrayList<Integer> visited;
    private int[] parent;
    private boolean[] root;

    public KruskalGraph_Lists(GraphAdjLists g) {
        super(g);
        Q = new ArrayList<Edge>();
        visited = new ArrayList<Integer>();
    }

    public KruskalGraph_Lists() {
        super();
        Q = new ArrayList<Edge>();
        visited = new ArrayList<Integer>();
    }

    /*
     * Sorts all adges in the tree
     */
    public void initQ() {
        visited = new ArrayList<Integer>();
        Q = new ArrayList<Edge>();
        for (int i = 0; i < order(); i++) {
            visited.add(i);
            ArrayList<Edge> edges = (ArrayList<Edge>) _adj.get(i);
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
//        System.out.println("\tQsize: " + Q.size());
        return Q.remove(0);
    }

    public void addVisited(int v) {
        visited.add(v);
        // whenever we add a visited node we have to remove
        // all references to that node in the queue
        ArrayList<Edge> removals = new ArrayList<Edge>();
        for (Edge e : Q) {
            if (e.getDest() == v) {
                System.out.println("Removing: " + removals);
                removals.add(e);
            }
        }
        Q.removeAll(removals);
    }

    public KruskalGraph_Lists MST_Kruskal_UnionFind() {
        //http://penguin.ewu.edu/cscd327/Topic/Graph/Kruskal/Set_Union_Find.html
        KruskalGraph_Lists mst = new KruskalGraph_Lists();
        mst.addVertices(order());
        //
        initQ();
        //initialize forest
        UnionFind uf = new UnionFind(order());

        int edges_processed = 0;
        int edges_added = 0;
        while (edges_processed < size() && edges_added < order() - 1) {
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

    public ArrayList<Integer> getVisited() {
        return visited;
    }

    public void setVisited(ArrayList<Integer> visited) {
        this.visited = visited;
    }

    public static void main(String[] args) {
        KruskalGraph_Lists g = new KruskalGraph_Lists();
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
        KruskalGraph_Lists mst = new KruskalGraph_Lists(g.MST_Kruskal_UnionFind());
        System.out.println(g.toString());
        System.out.println(mst.toString());
        System.out.println("Total weight: " + mst.MST_TotalWeight());

    }
}
