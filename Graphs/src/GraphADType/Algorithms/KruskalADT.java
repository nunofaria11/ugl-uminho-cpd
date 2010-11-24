/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphADType.Algorithms;

import EdgeOriented.EdgeEO;
import GraphADType.GraphADT;
import GraphADType.GraphMapAdj;
import GraphADType.GraphMapSucc;
import GraphADType.Support.Constants;
import GraphADType.Support.GenSaveReadADT;
import GraphADType.Support.GraphGenADT;
import GraphADType.Support.TArithmeticOperations;
import GraphADType.Support.YRandomizer;
import NodeOriented.Node;
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

//        System.out.println("Started processing...");
        EdgeEO<T, Y> edge;
        while (size > 1 && !Q.isEmpty()) {
            edge = Q.poll();
            graphTmp = trees.get(edge.getNode1()).clone();
            graph2 = trees.get(edge.getNode2()).clone();
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
//            System.out.println("Size: " + size);
        }
        return trees.get(key);
        //
    }

    public static void test_implementations(int size) {
        TArithmeticOperations<String> strArith = Constants.strArith;

        YRandomizer<Integer> iRand = Constants.randInteger;

        ArrayList<String> alpha = new ArrayList<String>();
        alpha.add("A");
        alpha.add("B");
        alpha.add("C");
        alpha.add("D");
        alpha.add("E");
        alpha.add("F");
        GraphGenADT ggen = new GraphGenADT(0.5, 90, 5, iRand, strArith, alpha);
        /// copy random data to three different implementations
        GraphMapAdj<String, Integer> g_map_adj = new GraphMapAdj<String, Integer>(size);
//        GraphArraySucc<String, Integer> g_array_succ = new GraphArraySucc<String, Integer>(size);
        GraphMapSucc<String, Integer> g_map_succ = new GraphMapSucc<String, Integer>(size);
        System.out.println("generating...");

        g_map_adj = (GraphMapAdj<String, Integer>) GenSaveReadADT.readTestBenchGraph(200);
        System.out.println("converting...");
        g_map_succ = g_map_adj.toGraphMapSucc();
//        System.out.println("converting...");
//        g_array_succ = g_map_adj.toGraphArraySucc();

        // test boruvka for each implementation
        KruskalADT b1 = new KruskalADT(g_map_adj); // exceeds heap memory
        KruskalADT b2 = new KruskalADT(g_map_succ);
//        BoruvkaADT b3 = new BoruvkaADT(g_array_succ);

        System.out.println("1:");
        GraphADT mst1 = b1.getMst(); // MUITO POUCO EFICIENTE - CAUSA memory heap space exceeded

        System.out.println("2:");
        GraphADT mst2 = b2.getMst();

//        System.out.println("3:");
//        GraphADT mst3 = b3.getMst();

        int total1 = 0;
        int total2 = 0;
//        int total3 = 0;
        System.out.println(mst1.getMstWeight(Constants.intArith, total1));
        System.out.println(mst2.getMstWeight(Constants.intArith, total2));
//        System.out.println(mst3.getMstWeight(Constants.intArith, total3));

    }

    public static void main2(String[] args) {
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
        TArithmeticOperations<Double> arith = new TArithmeticOperations<Double>() {

            public Double Add(Double a, Double b) {
                return a + b;
            }

            public Double Cat(Double a, Double b) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public Double null_element() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
        Double total = 0.0;
        total = (Double) mst.getMstWeight(arith, total);
        System.out.println("Total Mst Weight: " + total);
    }

    public static void main(String[] args) {
        KruskalADT.test_implementations(100);
    }
}
