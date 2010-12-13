/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphADType.Algorithms;

import EdgeOriented.Edge;
import GraphADType.GraphADT;
import GraphADType.GraphArraySucc;
import GraphADType.GraphMapAdj;
import GraphADType.GraphMapSucc;
import GraphADType.Support.Constants;
import GraphADType.Support.GenSaveReadADT;
import GraphADType.Support.GraphGenADT;
import GraphADType.Support.TArithmeticOperations;
import GraphADType.Support.YRandomizer;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 
 * @author nuno
 */
public class BoruvkaADT2<T, Y extends Comparable<Y>> {

    public GraphADT getMst(GraphADT g) {
        // mst edges
        ArrayList<Edge<T, Y>> mstEdges = new ArrayList<Edge<T, Y>>();

        // get wannabe edges in MST from original graph (unduplicated ones)
        //        ArrayList<EdgeEO<T, Y>> wannabes = new ArrayList<EdgeEO<T, Y>>(g.getUnduplicatedEdges());
        g.initWorklist(ArrayList.class);
        g.fillWorklist();
        // build control forest
        g.initUnionFind();

        // initialize forest (nbors hashmap - each node is initially a root)
        HashMap<T, Edge<T, Y>> nbors = new HashMap<T, Edge<T, Y>>(g.order());

        ArrayList<T> allnodes = new ArrayList<T>(g.getNodes());

        // the nextIteration variable controls if any wannabe was processed
        // - if it wasnt, no more wannabes means stop
        // - if wannabes were processed keep on to next iteration
        int nextIteration;
        // Repeat until there is only one tree
        for (int i = g.size(); i != 0; i = nextIteration) {
            // initialize each node's minEdge with null - which will correspond to the maximum edge
            for (T node : allnodes) {
                nbors.put(node, null);
            }
            // edges to add found in this iteration
            ArrayList<Edge<T, Y>> edges2add = new ArrayList<Edge<T, Y>>();
            T l, m;
            nextIteration = 0;
            for (Edge<T, Y> e : (ArrayList<Edge<T, Y>>) g._worklist) {
                // get both nodes of the edge and then see if they have already found a smaller edge (in 'nbors' map)
                l = (T) g._union_find.find(e.getNode1());
                m = (T) g._union_find.find(e.getNode2());
                if (l.equals(m)) {
                    continue;
                }
                // if e.getWeight() < nbors.get(l).getWeight()
                // (weight==null)?(isMax):(notMax)
                if (e.compareTo(nbors.get(l)) == -1 || nbors.get(l) == null) {
                    nbors.put(l, e);
                }
                // if e.getWeight() < nbors.get(m).getWeight()
                // (weight==null)?(isMax):(notMax)
                if (e.compareTo(nbors.get(m)) == -1 || nbors.get(m) == null) {
                    nbors.put(m, e);
                }
                nextIteration++;
            }
            // for every vertex check its nearest neighbor
            for (T n : allnodes) {
                if (nbors.get(n) != null) {
                    Edge<T, Y> nEdge = nbors.get(n);
                    l = nEdge.getNode1();
                    m = nEdge.getNode2();
                    if (!g._union_find.find(l, m)) {
                        g._union_find.union(l, m);
                        edges2add.add(nEdge);
                    }
                }
            }
            // remove wannabe edges that are already in the mst (duplicated ones also)
            g._worklist.removeAll(edges2add);
            mstEdges.addAll(edges2add);
        }
        // build graph of same type and fill it with the MST info
        GraphADT mst = g.clone();
        mst.clean();
        mst.addNodes(g.order());
        mst.addAllEdges(mstEdges);
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

        // copy random data to three different implementations
        GraphMapAdj<String, Integer> g_map_adj = new GraphMapAdj<String, Integer>(size);
        GraphArraySucc<String, Integer> g_array_succ = new GraphArraySucc<String, Integer>(size);
        GraphMapSucc<String, Integer> g_map_succ = new GraphMapSucc<String, Integer>(size);
        System.out.println("generating...");

        g_map_adj = (GraphMapAdj<String, Integer>) GenSaveReadADT.read();
        System.out.println("converting...");
        g_map_succ = g_map_adj.toGraphMapSucc();
        System.out.println("converting...");
        g_array_succ = g_map_adj.toGraphArraySucc();

        // test boruvka for each implementation
        BoruvkaADT2 b1 = new BoruvkaADT2();
        BoruvkaADT2 b2 = new BoruvkaADT2();
        BoruvkaADT2 b3 = new BoruvkaADT2();

        System.out.println("1:");
        GraphADT mst1 = b1.getMst(g_map_adj);
        System.out.println("2:");
        GraphADT mst2 = b2.getMst(g_map_succ);
        System.out.println("3:");
        GraphADT mst3 = b3.getMst(g_array_succ);

        int total1 = 0;
        int total2 = 0;
        int total3 = 0;
        System.out.println(mst1.getMstWeight(Constants.intArith, total1));
        System.out.println(mst2.getMstWeight(Constants.intArith, total2));
        System.out.println(mst3.getMstWeight(Constants.intArith, total3));

    }

    public static void testArraySucc(int size) {
        TArithmeticOperations<String> strArith = Constants.strArith;

        YRandomizer<Integer> iRand = Constants.randInteger;

        ArrayList<String> alpha = new ArrayList<String>();
        alpha.add("A");
        alpha.add("B");
        alpha.add("C");
        alpha.add("D");
        alpha.add("E");
        alpha.add("F");

        // copy random data to three different implementations
//        GraphMapAdj<String, Integer> g_map_adj = new GraphMapAdj<String, Integer>();
        GraphArraySucc<String, Integer> g_array_succ = new GraphArraySucc<String, Integer>(size);
        System.out.println("generating...");
        GraphGenADT<String, Integer> ggen = new GraphGenADT<String, Integer>(
                0.5,
                90, // maximum
                5, // minimum
                iRand, // interface for random Y-weight values
                strArith, // interface for node ids creation operations
                alpha);     // alphabet to consider in node-ids

//        g_array_succ = (GraphArraySucc<String, Integer>) ggen.generate(g_array_succ, size);
        GraphMapAdj<String, Integer> g_map_adj = new GraphMapAdj<String, Integer>(size);
        g_map_adj = (GraphMapAdj<String, Integer>) ggen.generate(g_map_adj, size);
//        GraphMapAdj<String, Integer> g_map_adj = (GraphMapAdj<String, Integer>) GenSaveReadADT.readTestBenchGraph();
        g_array_succ = g_map_adj.toGraphArraySucc();
//        System.out.println(g_array_succ);

        BoruvkaADT b3 = new BoruvkaADT(g_array_succ);
        GraphADT mst = b3.getMst();
        int total = 0;
        System.out.println(mst.getMstWeight(Constants.intArith, total));

    }

    public static void main(String[] args) {
        GraphMapAdj<String, Double> g = new GraphMapAdj<String, Double>(7);
        // create nodes...
//        Node<String> n0 = new Node<String>("A");
//        Node<String> n1 = new Node<String>("B");
//        Node<String> n2 = new Node<String>("C");
//        Node<String> n3 = new Node<String>("D");
//        Node<String> n4 = new Node<String>("E");
//        Node<String> n5 = new Node<String>("F");
//        Node<String> n6 = new Node<String>("G");
        String n0 ="A";
        String n1 ="B";
        String n2 ="C";
        String n3 ="D";
        String n4 ="E";
        String n5 ="F";
        String n6 ="G";
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

        System.out.println("Connected: " + g.connected());
        // create Boruvka instance...
        BoruvkaADT2 bor = new BoruvkaADT2();
        GraphADT mst = bor.getMst(g);
        System.out.println(mst.toString());
        // define arithmetic operations to calculate the total weight of type Y - in this case Y=Double
        TArithmeticOperations<Double> arith = new TArithmeticOperations<Double>() {

            public Double Add(Double a, Double b) {
                return a + b;
            }

            public Double Cat(Double a, Double b) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public Double zero_element() {
                return -1.0;
            }
        };
        Double total = 0.0;
        total = (Double) mst.getMstWeight(arith, total);
        System.out.println("Total Mst Weight: " + total);
    }

    public static void main2(String[] args) {
//        GenSaveReadADT.write();
        BoruvkaADT2.test_implementations(GenSaveReadADT.NUM_NODES);
//        BoruvkaADT.testArraySucc(400);
    }
}
