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
import Support.UnionFind;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 *
 * @author nuno
 */
public class KruskalADT<T, Y extends Comparable<Y>> {

    GraphADT g;
    PriorityQueue<EdgeEO<T, Y>> Q;

    public KruskalADT(GraphADT g) {
        this.g = g;
//        Q = new PriorityQueue<EdgeEO<T, Y>>();
        initQ();

    }

    public void initQ() {
        ArrayList<Node<T>> visited = new ArrayList<Node<T>>();
        Q = new PriorityQueue<EdgeEO<T, Y>>();
        for (Node<T> i : new ArrayList<Node<T>>(g.getNodes())) {
            visited.add(i);
            Collection<EdgeEO<T, Y>> edges = g.getNeighborEdges(i);
            ArrayList<EdgeEO<T, Y>> edgesToAdd = new ArrayList<EdgeEO<T, Y>>();
            for (EdgeEO<T, Y> e : edges) {
                if (!visited.contains(e.getNode2())) {
                    edgesToAdd.add(e);
                }
            }
            Q.addAll(edgesToAdd);
        }
    }

    public GraphADT getMst() {
        GraphADT mst = g.clone(); // we clone the graph so that the resulting mst has the same Graph-type than 'g'
        mst.clean();
        mst.addNodes(g.order());
        //
        Node<T> key = null;
        int size = 0;
        Map<Node<T>, GraphADT<T, Y>> trees = new HashMap<Node<T>, GraphADT<T, Y>>();
        GraphADT<T, Y> graphTmp, graph2;
        for (Node<T> node : new ArrayList<Node<T>>(g.getNodes())) {
            graphTmp = mst.clone();
            graphTmp.clean();
            graphTmp.addNode(node);
            trees.put(node, graphTmp);
            key = node;
            size++;
        }
        EdgeEO<T, Y> edge;
        while (size > 1 && !Q.isEmpty()) {
            edge = Q.poll();
            graphTmp = trees.get(edge.getNode1());
            graph2 = trees.get(edge.getNode2());
            if (!graph2.equals(graphTmp)) {
                for (Node<T> node : graph2.getNodes()) {
                    graphTmp.addNode(node);
                }
                for (EdgeEO<T, Y> edge1 : graph2.getEdges()) {
                    graphTmp.addEdge(edge1.getNode1(), edge1.getNode2(), edge1.getEdge_data());
                }
                graphTmp.addEdge(edge.getNode1(), edge.getNode2(), edge.getEdge_data());
                for (Node<T> node : graph2.getNodes()) {
                    trees.put(node, graphTmp);
                }
                size--;
            }
        }
        return trees.get(key);
        //
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
        KruskalADT kruskal = new KruskalADT(g);
        GraphADT mst = kruskal.getMst();
        System.out.println(mst.toString());
        // define arithmetic operations to calculate the total weight of type Y
        YArithmeticOperations<Double> arith = new YArithmeticOperations<Double>() {

            public Double Add(Double a, Double b) {
                return a + b;
            }
        };
        Double total = 0.0;
        total = (Double) mst.getMstWeight(arith, total);
        System.out.println("Total Mst Weight: " + total);
    }
}
