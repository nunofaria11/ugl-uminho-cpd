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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

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

    public void runAlgorithms(GraphADT g) {
        BoruvkaADT bor = new BoruvkaADT(g);
        KruskalADT krusk = new KruskalADT(g);
        KruskalADT_UF krusk_uf = new KruskalADT_UF(g);
        PrimADT prim = new PrimADT(g);

        GraphADT mst;
        int total = 0;
        mst = bor.getMst();
        System.out.println("Boruvka MST weight: " + mst.getMstWeight(Constants.intArith, total));
        total = 0;
        mst = krusk.getMst();
        System.out.println("Kruskal MST weight: " + mst.getMstWeight(Constants.intArith, total));
        total = 0;
        mst = krusk_uf.getMst();
        System.out.println("Kruskal_UF MST weight: " + mst.getMstWeight(Constants.intArith, total));
        total = 0;
        mst = prim.getMst();
        System.out.println("Prun MST weight: " + mst.getMstWeight(Constants.intArith, total));
        total = 0;

    }

    public static void test1(){
        ArrayList<String> alpha = new ArrayList<String>();
        alpha.add("A");
        alpha.add("B");
        alpha.add("C");
        
        GraphGenADT<String, Integer> ggen = new GraphGenADT<String, Integer>(0.50, 50, 5, Constants.randInteger, Constants.strArith, alpha);
        GraphMapAdj<String, Integer> temp = new GraphMapAdj<String, Integer>();
        GraphADT<String, Integer> g = ggen.generate(temp, 50);

        System.out.println(g.toString());
        System.out.println("MST: ");
        BoruvkaADT<String, Integer> k = new BoruvkaADT<String, Integer>(g);
        GraphADT mst = k.getMst();
        System.out.println(mst);
        int total = 0;
//        System.out.println(mst.getMstWeight(Constants.intArith, total));

    }

    

    public static void main(String[] args) {

//        BenchmarkADT bench = new BenchmarkADT();
////        bench.createAndWriteGraphs();
//        ArrayList<GraphADT> graphs = bench.readBenchGraphs();
//        for(GraphADT g : graphs){
//            System.out.println("Size: "+g.order());
//            bench.runAlgorithms(g);
//            System.out.println("=================");
//        }

        BenchmarkADT.test1();


    }
}
