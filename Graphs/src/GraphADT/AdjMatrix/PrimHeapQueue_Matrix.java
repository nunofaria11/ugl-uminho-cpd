/*
 * For now keep using the edges in order to sort the queue more easily, using
 * the Edge class (see method 'transformToEdges')
 *
 * TO DO: reorganize classes so it doesn't uses interfaces...
 */
package GraphADT.AdjMatrix;

import GraphADT.Edge;
import Support.PrimHeapInterface;
import java.util.Collections;
import java.util.ArrayList;

/**
 *
 * @author nuno
 */
public class PrimHeapQueue_Matrix extends GraphAdjMatrix implements PrimHeapInterface {

    private ArrayList<Edge> Q;
    private ArrayList<Integer> visited;

    public PrimHeapQueue_Matrix(GraphAdjMatrix G) {
        super(G);
        Q = new ArrayList<Edge>();
        visited = new ArrayList<Integer>();
    }

    public PrimHeapQueue_Matrix() {
        super();
        Q = new ArrayList<Edge>();
        visited = new ArrayList<Integer>();
    }

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

    public ArrayList<Edge> transformToEdges(int node, ArrayList<Integer> x) {
        ArrayList<Edge> edges = new ArrayList<Edge>();
        for (int v : x) {
            edges.add(new Edge(node, v, getWeight(node, v)));
        }
        return edges;
    }

    /*
     * TRY TO INTEGRATE THIS IMPLEMENTATION WITH THE AdjList IMPLEMENTATION
     * TO CREATE A GENERIC IMPLEMENTATION
     */
    public PrimHeapQueue_Matrix MST_PrimHeap() {
        PrimHeapQueue_Matrix mst = new PrimHeapQueue_Matrix();
        mst.addVertices(order());
        Q = new ArrayList<Edge>();
        visited = new ArrayList<Integer>();
        int currentNode = getRandomNode();
        while (visited.size() < order() - 1) { /* *** */
            addVisited(currentNode);
            ArrayList<Edge> nbors = getEdgeNeighbors(currentNode);
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
//        PrimGraphAdjLists_Queue qq = new PrimGraphAdjLists_Queue();

        PrimHeapQueue_Matrix g = new PrimHeapQueue_Matrix();
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
//        System.out.println("Connected: " + g.connected());
        PrimHeapQueue_Matrix mst = g.MST_PrimHeap();
        System.out.println(g.toString());
        System.out.println(mst.toString());
        System.out.println("Total weight: " + mst.MST_TotalWeight());
    }
}
