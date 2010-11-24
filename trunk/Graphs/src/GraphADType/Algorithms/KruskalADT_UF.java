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
import GraphADType.Support.UnionFind_ADT;
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
public class KruskalADT_UF<T, Y extends Comparable<Y>> {

    private PriorityQueue<EdgeEO<T, Y>> Q;
    private UnionFind_ADT<Node<T>> uf;
    private GraphADT g;

    public KruskalADT_UF(GraphADT g) {
        this.g = g.clone();
        uf = new UnionFind_ADT<Node<T>>(this.g.getNodes());
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
        //http://penguin.ewu.edu/cscd327/Topic/Graph/Kruskal/Set_Union_Find.html
        GraphADT mst = g.clone();
        mst.clean();
        mst.addNodes(g.order());
        //
        int edges_processed = 0;
        int edges_added = 0;
        while (edges_processed < g.size() && edges_added < g.order() - 1) {
            EdgeEO minEdge = Q.poll();
            Node<T> ck1 = uf.find(minEdge.getNode1());
            Node<T> ck2 = uf.find(minEdge.getNode2());
            if (!ck1.equals(ck2)) { // if roots are different it means it doesnt have a cycle
                mst.addEdge(minEdge.getNode1(), minEdge.getNode2(), minEdge.getEdge_data());
                // A U {(u,v)}
                uf.union(ck1, ck2);
                edges_added++;
            }
            edges_processed++;
        }
        //
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
        KruskalADT_UF b1 = new KruskalADT_UF(g_map_adj);
        KruskalADT_UF b2 = new KruskalADT_UF(g_map_succ);
        BoruvkaADT b3 = new BoruvkaADT(g_array_succ);

        System.out.println("1:");
        GraphADT mst1 = b1.getMst();
        System.out.println("2:");
        GraphADT mst2 = b2.getMst();
        System.out.println("3:");
        GraphADT mst3 = b3.getMst();

        int total1 = 0;
        int total2 = 0;
        int total3 = 0;
        System.out.println(mst1.getMstWeight(Constants.intArith, total1));
        System.out.println(mst2.getMstWeight(Constants.intArith, total2));
        System.out.println(mst3.getMstWeight(Constants.intArith, total3));

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
        KruskalADT_UF kruskal = new KruskalADT_UF(g);
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
         KruskalADT_UF.test_implementations(300);
     }
}
