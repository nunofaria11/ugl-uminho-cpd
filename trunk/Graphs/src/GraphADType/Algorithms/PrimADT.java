/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphADType.Algorithms;

import EdgeOriented.EdgeEO;
import GraphADType.GraphADT;
import GraphADType.GraphArraySucc;
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
import java.util.PriorityQueue;

/**
 *
 * @author nuno
 */
public class PrimADT<T, Y extends Comparable<Y>> {

//    GraphADT g;
    ArrayList<Node<T>> visited;
    PriorityQueue<EdgeEO<T, Y>> Q;

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

    public GraphADT getMst(GraphADT g) {
        visited = new ArrayList<Node<T>>();
        Q = new PriorityQueue<EdgeEO<T, Y>>();
        GraphADT mst = g.clone();
        mst.clean();
        mst.addNodes(g.order());
        Node<T> start = g.getRandom();
        while (visited.size() < g.order() - 1) {
            addVisited(start);
            //
            Collection<EdgeEO<T, Y>> nbors = g.getNeighborEdges(start);
            ArrayList<EdgeEO<T, Y>> newEdges = new ArrayList<EdgeEO<T, Y>>();
            for (EdgeEO<T, Y> e : nbors) {
                if (!visited.contains(e.getNode2())) {
                    newEdges.add(e);
                }
            }
            Q.addAll(newEdges);
            EdgeEO<T, Y> minEdge = Q.remove();
            mst.addEdge(minEdge.getNode1(), minEdge.getNode2(), minEdge.getEdge_data());
            start = minEdge.getNode2();
            //
        }
        return mst;
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
        // copy random data to three different implementations
        GraphMapAdj<String, Integer> g_map_adj = new GraphMapAdj<String, Integer>(size);
        GraphArraySucc<String, Integer> g_array_succ = new GraphArraySucc<String, Integer>(size);
        GraphMapSucc<String, Integer> g_map_succ = new GraphMapSucc<String, Integer>(size);
        System.out.println("generating...");

        g_map_adj = (GraphMapAdj<String, Integer>) GenSaveReadADT.readTestBenchGraph(size);
        System.out.println("converting...");
        g_map_succ = g_map_adj.toGraphMapSucc();
        System.out.println("converting...");
        g_array_succ = g_map_adj.toGraphArraySucc();

        // test boruvka for each implementation
        PrimADT b1 = new PrimADT();
        PrimADT b2 = new PrimADT();
        BoruvkaADT b3 = new BoruvkaADT(g_array_succ);

        System.out.println("1:");
        GraphADT mst1 = b1.getMst(g_map_adj);
        System.out.println("2:");
        GraphADT mst2 = b2.getMst(g_map_succ);
        System.out.println("3:");
        GraphADT mst3 = b3.getMst();

        int total1 = 0;
        int total2 = 0;
        int total3 = 0;
        System.out.println(mst1.getMstWeight(Constants.intArith, total1));
        System.out.println(mst2.getMstWeight(Constants.intArith, total2));
        System.out.println(mst3.getMstWeight(Constants.intArith, total3));

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
        PrimADT prim = new PrimADT();
        GraphADT mst_prim = prim.getMst(g);
        System.out.println(mst_prim.toString());
        // define arithmetic operations to calculate the total weight of type Y
        TArithmeticOperations<Double> arith = new TArithmeticOperations<Double>() {

            public Double Add(Double a, Double b) {
                return a + b;
            }

            public Double Cat(Double a, Double b) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public Double zero_element() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
        Double total = 0.0;
        total = (Double) mst_prim.getMstWeight(arith, total);
        System.out.println("Total Mst Weight: " + total);
    }

    public static void main2(String[] args) {
        PrimADT.test_implementations(300);
    }
}
