/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphADType.Utilities;

import GraphADType.Algorithms.BoruvkaADT;
import GraphADType.Algorithms.KruskalADT;
import GraphADType.Algorithms.KruskalADT_UF;
import GraphADType.Algorithms.PrimADT;
import GraphADType.GraphADT;
import GraphADType.GraphArraySucc;
import GraphADType.GraphMapAdj;
import GraphADType.Support.Constants;
import GraphADType.Support.GraphGenADT;
import GraphADType.Support.TArithmeticOperations;
import GraphADType.Support.YRandomizer;
import GraphIO.GraphInput;
import GraphIO.GraphOutput;
import NodeOriented.Node;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Benchmark utility for GraphADT
 * @author nuno
 */
public class BenchmarkADT {

    int MIN_GRAPH_SIZE = 10;
    int MAX_GRAPH_SIZE = 50;
    int STEP = 10;

    public void createAndWriteGraphs() {
        TArithmeticOperations<String> strArith = Constants.strArith;

        YRandomizer<Integer> iRand = Constants.randInteger;

        ArrayList<String> alpha = new ArrayList<String>();
        alpha.add("A");
        alpha.add("B");
        alpha.add("C");
        alpha.add("D");
        alpha.add("E");
        alpha.add("F");

        for (int size = MIN_GRAPH_SIZE; size <= MAX_GRAPH_SIZE; size += STEP) {
            GraphOutput gout = new GraphOutput("bench_graphADT_" + size + ".ser");
            GraphGenADT ggen = new GraphGenADT(0.5, 90, 5, iRand, strArith, alpha);
            GraphMapAdj<String, Integer> g = new GraphMapAdj<String, Integer>();
            g = (GraphMapAdj<String, Integer>) ggen.generate(g, size);
            try {
                gout.saveGraphADT(g);
            } catch (IOException ex) {
                System.out.println("Error writing graph: " + ex.toString());
            }
        }
    }

    public ArrayList<GraphADT> readBenchGraphs() {
        ArrayList<GraphADT> graphs = new ArrayList<GraphADT>();
        for (int size = MIN_GRAPH_SIZE; size <= MAX_GRAPH_SIZE; size += STEP) {
            GraphInput gin = new GraphInput("bench_graphADT_" + size + ".ser");
            GraphADT g_adt_read = gin.readGraphADT();
            graphs.add(g_adt_read);
        }
        return graphs;
    }

    public void runBoruvka(GraphADT g) {
        BoruvkaADT bor = new BoruvkaADT(g);
        GraphADT mst;
        int total = 0;
        long begin = System.currentTimeMillis();
        mst = bor.getMst();
        long end = System.currentTimeMillis();
        System.out.println("Time boruvka: " + (end - begin) + " ms");
//        System.out.println("Boruvka MST weight: " + mst.getMstWeight(Constants.intArith, total));
    }

    public void runKruskal(GraphADT g) {
        KruskalADT krusk = new KruskalADT();
        GraphADT mst;
        int total = 0;
        long begin = System.currentTimeMillis();
        mst = krusk.getMst(g);
        long end = System.currentTimeMillis();
        System.out.println("Time kruskal: " + (end - begin) + " ms");
//        System.out.println("Kruskal MST weight: " + mst.getMstWeight(Constants.intArith, total));
    }

    public void runKruskalUF(GraphADT g) {
        KruskalADT_UF krusk = new KruskalADT_UF();
        GraphADT mst;
        int total = 0;
        long begin = System.currentTimeMillis();
        mst = krusk.getMst(g);
        long end = System.currentTimeMillis();
        System.out.println("Time kruskal_uf: " + (end - begin) + " ms");
//        System.out.println("Kruskal_UF MST weight: " + mst.getMstWeight(Constants.intArith, total));
    }

    public void runPrim(GraphADT g) {
        PrimADT prim = new PrimADT();
        GraphADT mst;
        int total = 0;
        long begin = System.currentTimeMillis();
        mst = prim.getMst(g);
        long end = System.currentTimeMillis();
        System.out.println("Time prim: " + (end - begin) + " ms");
//        System.out.println("Prim MST weight: " + mst.getMstWeight(Constants.intArith, total));
    }

    public void runAlgorithms(GraphADT g) {
        runKruskal(g);
        runKruskalUF(g);
        runPrim(g);


        runBoruvka(g);
    }

    public static void test1() throws IOException {
        ArrayList<String> alpha = new ArrayList<String>();
        alpha.add("A");
        alpha.add("B");
        alpha.add("C");

        GraphGenADT<String, Integer> ggen = new GraphGenADT<String, Integer>(0.80, 50, 5, Constants.randInteger, Constants.strArith, alpha);
        GraphMapAdj<String, Integer> temp = new GraphMapAdj<String, Integer>();
        GraphADT<String, Integer> g = ggen.generate(temp, 100);

        GraphOutput gdot = new GraphOutput("test1.dot");
        gdot.GraphtoDot(g);

//        System.out.println(g.toString());
//        System.out.println("MST: ");
//        BoruvkaADT<String, Integer> k = new BoruvkaADT<String, Integer>(g);
//        GraphADT mst = k.getMst();

        int total = 0;
//        System.out.println(mst.getMstWeight(Constants.intArith, total));

    }

    public static void testShiftMap(HashMap<String, Integer> map, String element) {
        ArrayList<String> keys = new ArrayList<String>(map.keySet());
        keys.remove(element);
        ArrayList<String> keys2change = new ArrayList<String>();
        for (String el : keys) {
            if (map.get(el) >= map.get(element) && map.get(el) != -1) {
                keys2change.add(el);
            }
        }
        for (String el : keys2change) {
            map.put(el, map.get(el) + 1);
        }
    }

    public static void main(String[] args) {

//        BenchmarkADT bench = new BenchmarkADT();
//        bench.createAndWriteGraphs();
//        ArrayList<GraphADT> graphs = bench.readBenchGraphs();
//        for (GraphADT g : graphs) {
//            System.out.println("Size: " + g.order());
//            bench.runAlgorithms(g);
//            System.out.println("=================");
//        }

//        GraphInput gin = new GraphInput("bench50_test.ser");
//        GraphMapAdj g = (GraphMapAdj) gin.readGraphADT();
//        GraphArraySucc gg = g.toGraphArraySucc();
//        Node node = gg.getRandom();
//        System.out.println(node);
//        System.out.println(gg.getNeighborEdges(node));

//        HashMap<String, Integer> map = new HashMap<String, Integer>();
//        map.put("A", 0);
//        map.put("B", 1);
//        map.put("C", -1);
//        map.put("D", 2);
//        System.out.println(map);
//        testShiftMap(map, "A");
//        System.out.println(map);

        GraphArraySucc<String, Integer> g = new GraphArraySucc<String, Integer>(4);
        String A = "A";
        String B = "B";
        String C = "C";
        String D = "D";

//        g.addArc(A, B, Integer.SIZE);
//        g.addArc(B, C, Integer.SIZE);
//        g.addArc(D, B, Integer.SIZE);
//        g.addArc(A, C, Integer.SIZE);
//        g.addArc(C, A, Integer.SIZE);
//        g.addArc(C, B, Integer.SIZE);
//        g.addArc(B, D, Integer.SIZE);
//        g.addArc(B, A, Integer.SIZE);

        g.addEdge(A, B, Integer.SIZE);
        g.addEdge(B, C, Integer.SIZE);
        g.addEdge(B, D, Integer.SIZE);
        g.addEdge(A, C, Integer.SIZE);

        System.out.println(g.stateString());
        System.out.println(g.getNeighborEdges(D));


    }
}
