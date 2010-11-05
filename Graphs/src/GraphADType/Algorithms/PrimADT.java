/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphADType.Algorithms;

import EdgeOriented.EdgeEO;
import GraphADType.GraphADT;
import GraphADType.GraphMapAdj;
import GraphADType.YArithmeticOperations;
import NodeOriented.Node;
import java.util.ArrayList;
import java.util.Collection;
import java.util.PriorityQueue;

/**
 *
 * @author nuno
 */
public class PrimADT<T, Y extends Comparable<Y>> {

    GraphADT g;
    ArrayList<Node<T>> visited;
    PriorityQueue<EdgeEO<T, Y>> Q;

    public PrimADT(GraphADT g) {
        this.g = g;
        visited = new ArrayList<Node<T>>();
        Q = new PriorityQueue<EdgeEO<T, Y>>();
    }

    public void addVisited(Node<T> v) {
        visited.add(v);
        // whenever we add a visited node we have to remove
        // all references to that node in the queue
        ArrayList<EdgeEO> removals = new ArrayList<EdgeEO>();
        for (EdgeEO e : Q) {
            if (e.getNode2().equals(v)) {
                removals.add(e);
            }
        }
        Q.removeAll(removals);
    }

    public GraphADT getMst() {
        GraphADT mst = g.clone();
        mst.clean();
        mst.addNodes(g.order());
        Node<T> start = g.getRandom();
        while (visited.size() < g.order() - 1) {
            addVisited(start);
            //
            Collection<EdgeEO<T, Y>> nbors = g.getNeighborEdges(start);
            // UPDATE: It was necessary to add another field to the Edge class: 'from'
            ArrayList<EdgeEO<T, Y>> newEdges = new ArrayList<EdgeEO<T, Y>>();
            for (EdgeEO<T, Y> e : nbors) {
                if (!visited.contains(e.getNode2())) {
                    newEdges.add(e);
                }
            }
            Q.addAll(newEdges);
            EdgeEO<T, Y> minEdge = Q.remove(); // * The sorting is done in the Edge class by implementing it with the Comparable interface
            mst.addEdge(minEdge.getNode1(), minEdge.getNode2(), minEdge.getEdge_data());
            start = minEdge.getNode2();
            //
        }
        return mst;
    }

    public static void main(String[] args) {
        GraphMapAdj<String, Double> g = new GraphMapAdj<String, Double>(7);
        // create nodes...
        Node<String> n0 = new Node<String>("A");
        Node<String> n1 = new Node<String>("B");
        Node<String> n2 = new Node<String>("C");
        Node<String> n3 = new Node<String>("D");
        Node<String> n4 = new Node<String>("E");
        Node<String> n5 = new Node<String>("F");
        Node<String> n6 = new Node<String>("G");
        // add nodes to graph...
        g.addNode(n0);
        g.addNode(n1);
        g.addNode(n2);
        g.addNode(n3);
        g.addNode(n4);
        g.addNode(n5);
        g.addNode(n6);
        // link nodes together...
        g.addEdge(n0, n1, 7.1);
        g.addEdge(n0, n3, 5.1);
        g.addEdge(n1, n2, 8.1);
        g.addEdge(n1, n3, 9.1);
        g.addEdge(n1, n4, 7.1);
        g.addEdge(n2, n4, 5.1);
        g.addEdge(n3, n4, 15.1);
        g.addEdge(n3, n5, 6.1);
        g.addEdge(n4, n5, 8.1);
        g.addEdge(n4, n6, 9.1);
        g.addEdge(n5, n6, 11.1);
        // create Prim instance...
        PrimADT prim = new PrimADT(g);
        GraphADT mst_prim = prim.getMst();
        System.out.println(mst_prim.toString());
        // define arithmetic operations to calculate the total weight of type Y
        YArithmeticOperations<Double> arith = new YArithmeticOperations<Double>() {

            public Double Add(Double a, Double b) {
                return a + b;
            }
        };
        Double total = 0.0;
        total = (Double) mst_prim.getMstWeight(arith, total);
        System.out.println("Total Mst Weight: " + total);
    }
}
