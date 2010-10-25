/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Algorithms;

import GraphAD.Edge;
import GraphAD.GraphAD;
import GraphAD.Representations.GraphAdjMatrix;
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
        for (Edge e : _fib_heap.getAllElements()) {
            if (e.getDest() == v) {
                _fib_heap.delete(e, e.getWeight());
            }
        }
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
            // UPDATE: It was necessary to add another field to the Edge class: 'from'
            ArrayList<Edge> newEdges = new ArrayList<Edge>();
            for (Edge e : nbors) {
                if (!visited.contains(e.getDest())) {
                    newEdges.add(e);
                }
            }
            addToHeap(newEdges);
            Edge minEdge = extractMin();
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

        PrimFibonacciHeap prim = new PrimFibonacciHeap(g);
        GraphAD mst = prim.MST_PrimHeap();
        System.out.println(g.toString());
        System.out.println(mst.toString());
        System.out.println("Total weight: " + mst.MST_TotalWeight());

    }
}
