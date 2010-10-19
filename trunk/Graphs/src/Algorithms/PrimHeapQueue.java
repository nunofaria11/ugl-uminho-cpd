/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Algorithms;

/*
 * For this prim-queued implementation the queue must be sorted by weight.
 */

import GraphADT.AdjLists.GraphAdjLists;
import GraphADT.AdjMatrix.GraphAdjMatrix;
import GraphADT.Edge;
import GraphADT.GraphADT;
import Support.PrimHeapInterface;
import java.util.Collections;
import java.util.ArrayList;

/**
 *
 * @author nuno
 */
public class PrimHeapQueue implements PrimHeapInterface {

    private ArrayList<Edge> Q;
    private ArrayList<Integer> visited;
    private GraphADT G;

    public PrimHeapQueue(GraphAdjLists g) {
        G = g;
        Q = new ArrayList<Edge>();
        visited = new ArrayList<Integer>();
    }

    public PrimHeapQueue(GraphAdjMatrix g) {
        G = g;
        Q = new ArrayList<Edge>();
        visited = new ArrayList<Integer>();
    }

    /**
     * Extracts minimum value in queue and returns it
     * @return minimum value in queue
     */
    public Edge extractMin() {
        return Q.remove(0);
    }

    public void addToQ(Edge x) {
        Q.add(x);
        Collections.sort(Q);
    }

    public void addToQ(ArrayList x) {
        ArrayList<Edge> xx = (ArrayList<Edge>) x;
        Q.addAll(xx);
        Collections.sort(Q);
    }

    public ArrayList<Edge> getQ() {
        return Q;
    }

    public void setQ(ArrayList<Edge> Q) {
        this.Q = Q;
    }

    public boolean QisEmpty() {
        return Q.isEmpty();
    }

    public boolean isInQ(Edge x) {
        return Q.contains(x);
    }

    public void addVisited(int v) {
        visited.add(v);
        // whenever we add a visited node we have to remove
        // all references to that node in the queue
        ArrayList<Edge> removals = new ArrayList<Edge>();
        for (Edge e : Q) {
            if (e.getDest() == v) {
                removals.add(e);
            }
        }
        Q.removeAll(removals);
    }

    /*
     * TRY TO INTEGRATE THIS IMPLEMENTATION WITH THE AdjMatrix IMPLEMENTATION
     * TO CREATE A GENERIC IMPLEMENTATION
     */
    public GraphADT MST_PrimHeap() {
        GraphADT mst = (GraphADT) G.clone();
        mst.clean();
        mst.addVertices(G.order());
        Q = new ArrayList<Edge>();
        visited = new ArrayList<Integer>();
        int currentNode = G.getRandomNode();
        while (visited.size() < G.order() - 1) {
            addVisited(currentNode);
            ArrayList<Edge> nbors = G.getEdgeNeighbors(currentNode);
            // UPDATE: It was necessary to add another field to the Edge class: 'from'
            ArrayList<Edge> newEdges = new ArrayList<Edge>();
            for (Edge e : nbors) {
                if (!visited.contains(e.getDest())) {
                    newEdges.add(e);
                }
            }
            addToQ(newEdges);
            Edge minEdge = extractMin(); // * The sorting is done in the Edge class by implementing it with the Comparable interface
            mst.addEdge(minEdge.getFrom(), minEdge.getDest(), minEdge.getWeight());
            currentNode = minEdge.getDest();
        }
        return mst;
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

        PrimHeapQueue prim = new PrimHeapQueue(g);
        GraphADT mst = prim.MST_PrimHeap();
        System.out.println(g.toString());
        System.out.println(mst.toString());
        System.out.println("Total weight: " + mst.MST_TotalWeight());



    }
}
