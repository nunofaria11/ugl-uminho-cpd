/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Algorithms;

import GraphAD.Edge;
import GraphAD.GraphAD;
import GraphAD.Representations.GraphAdjLists;
import Utilities.FibonacciHeap;
import java.util.ArrayList;

/**
 *
 * @author nuno
 */
public class PrimFibonacciHeap {

    private FibonacciHeap<Edge> _fib_heap;
    private ArrayList<Integer> visited;
    private GraphAD G;

    public PrimFibonacciHeap(FibonacciHeap _fib_heap) {
        this._fib_heap = _fib_heap;
        this.visited = new ArrayList<Integer>();
    }

    public PrimFibonacciHeap(GraphAD g) {
        G = g;
        this._fib_heap = new FibonacciHeap();
        this.visited = new ArrayList<Integer>();
    }

    /*
     * Methods
     */
    public FibonacciHeap<Edge> getFib_heap() {
        return _fib_heap;
    }

    public void setFib_heap(FibonacciHeap<Edge> _fib_heap) {
        this._fib_heap = _fib_heap;
    }

    public ArrayList<Integer> getVisited() {
        return visited;
    }

    public void setVisited(ArrayList<Integer> visited) {
        this.visited = visited;
    }

    /**
     * Extracts minimum value in queue and returns it
     * @return minimum value in queue
     */
    public Edge extractMin() {
        return _fib_heap.removeMin().getData();
    }

    public void addToHeap(Edge x) {
        // the key sorting factor in the heap will be the weight of the connection
        _fib_heap.insert(x, x.getWeight());
    }

    public void addToHeap(ArrayList<Edge> x) {
        for (Edge e : x) {
            addToHeap(e);
        }
    }

    public void addVisited(int v) {
        visited.add(v);
        // whenever we add a visited node we have to remove
        // all references to that node in the queue
        ArrayList<Edge> removals = new ArrayList<Edge>();
        System.out.println("*** Fib tree: " + _fib_heap.toString());
        System.out.println("*** ALL ELS: " + _fib_heap.getAllElements());
        for (Edge e : _fib_heap.getAllElements()) {
            if (e.getDest() == v) {
                System.out.println("*** Removing " + e.toString());
                removals.add(e);
//                _fib_heap.delete(e, e.getWeight());
            }
        }
        removeAll(removals);
    }

    public void removeAll(ArrayList<Edge> rem) {
        for (Edge e : rem) {
            _fib_heap.delete(e, e.getWeight());
        }
    }

    public GraphAD MST_PrimHeap() {
        GraphAD mst = (GraphAD) G.clone();
        mst.clean();
        mst.addVertices(G.order());
        _fib_heap = new FibonacciHeap<Edge>();
        visited = new ArrayList<Integer>();
        int currentNode = G.getRandomNode();
        while (visited.size() < G.order() - 1) {
            addVisited(currentNode);
            ArrayList<Edge> nbors = G.getEdgeNeighbors(currentNode);
            ArrayList<Edge> newEdges = new ArrayList<Edge>();
            for (Edge e : nbors) {
                if (!visited.contains(e.getDest())) {
                    newEdges.add(e);
                }
            }
            addToHeap(newEdges);
            System.out.println("Visited: " + visited);
            System.out.println("Adding to heap: " + newEdges.toString());
            System.out.println("ALL ELS: " + _fib_heap.getAllElements());
            System.out.println(_fib_heap.toString());

            Edge minEdge = extractMin();
            System.out.println("Extracted: " + minEdge.toString());
            mst.addEdge(minEdge.getFrom(), minEdge.getDest(), minEdge.getWeight());
            currentNode = minEdge.getDest();
            System.out.println("Next node:" + currentNode + "\n");

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

        System.out.println(g.toString());
        PrimFibonacciHeap prim = new PrimFibonacciHeap(g);
        GraphAD mst = prim.MST_PrimHeap();

        System.out.println(mst.toString());
        System.out.println("Total weight: " + mst.MST_TotalWeight());

    }
}
