/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphADType.Utilities;

import GraphADType.Algorithms.BoruvkaADT;
import GraphADType.Algorithms.BoruvkaADT2;
import GraphADType.Algorithms.KruskalADT_UF;
import GraphADType.Algorithms.PrimADT;
import GraphADType.GraphADT;
import GraphADType.GraphArraySucc;
import GraphADType.GraphMapAdj;
import GraphADType.GraphMapSucc;
import GraphADType.GraphMatrix;
import GraphIO.GraphInput;

/**
 *
 * @author nuno
 */
public class RunBenchmark {

    public static GraphADT alloc(String type, int size) {
//        System.out.println(type);
        if (type.equals("mapadj")) {
            return new GraphMapAdj<String, Integer>(size);
        }
        if (type.equals("mapsucc")) {
            return new GraphMapSucc<String, Integer>(size);
        }
        if (type.equals("arraysucc")) {
            return new GraphArraySucc<String, Integer>(size);
        }
        if (type.equals("matrix")) {
            return new GraphMatrix<String, Integer>(size);
        }
        return null;
    }

    public static long runAlgorithm(GraphADT g, String alg) {
        if (alg.equals("boruvka")) {
            BoruvkaADT b = new BoruvkaADT(g);
            long begin = System.currentTimeMillis();
            GraphADT mst = b.getMst();
            long end = System.currentTimeMillis();
//            System.out.println("boruvka time:\t" + (end - begin));
//            int total = 0;
//            System.out.println("MST weight" + mst.getMstWeight(Constants.intArith, total));
            return (end - begin);
        } else if (alg.equals("boruvka2")) {
            BoruvkaADT2 b = new BoruvkaADT2();
            long begin = System.currentTimeMillis();
            GraphADT mst = b.getMst(g);
            long end = System.currentTimeMillis();
//            System.out.println("boruvka time:\t" + (end - begin));
//            int total = 0;
//            System.out.println("MST weight" + mst.getMstWeight(Constants.intArith, total));
            return (end - begin);
        } else if (alg.equals("kruskal")) {
            KruskalADT_UF b = new KruskalADT_UF(g);
            long begin = System.currentTimeMillis();
            GraphADT mst = b.getMst();
            long end = System.currentTimeMillis();
//            System.out.println("kruskal time:\t" + (end - begin));
//            int total = 0;
//            System.out.println("MST weight" + mst.getMstWeight(Constants.intArith, total));
            return (end - begin);
        } else if (alg.equals("prim")) {
            PrimADT b = new PrimADT(g);
            long begin = System.currentTimeMillis();
            GraphADT mst = b.getMst();
            long end = System.currentTimeMillis();
//            System.out.println("prim time:\t" + (end - begin));
//            int total = 0;
//            System.out.println("MST weight" + mst.getMstWeight(Constants.intArith, total));
            return (end - begin);
        }
        return -1;
    }

    // le e converte o grafo para o tipo determinado
    public static GraphADT readGraph(String filename, String type) {
//        System.out.println(filename);
        GraphInput gin = new GraphInput(filename);
        GraphADT gg = gin.readGraphADT();

        if (type.equals("mapsucc")) {
//            System.out.println("convert to mapsucc...");
            return ((GraphMapAdj) gg).toGraphMapSucc();
        }
        if (type.equals("arraysucc")) {
//            System.out.println("convert to arraysucc...");
            return ((GraphMapAdj) gg).toGraphArraySucc();
        }
        if (type.equals("matrix")) {
//            System.out.println("convert to arraysucc...");
            return ((GraphMapAdj) gg).toGraphMatrix();
        }
        return gg;
    }

    public static String getOnlyNumerics(String str) {
        if (str == null) {
            return null;
        }
        StringBuilder strBuff = new StringBuilder();
        char c;
        for (int i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            if (Character.isDigit(c)) {
                strBuff.append(c);
            }
        }
        return strBuff.toString();
    }

    public static void main(String[] args) {
        /*
         * bench_graphADT_50.ser mapadj boruvka
         * args[0] -> graph to read and execute
         * args[1] -> graph implementation to use
         * args[2] -> algorithm to run
         */

        // need to get size according to the file name to allocate N nodes in the
        // 'arraysucc' implementation
        int size = Integer.parseInt(getOnlyNumerics(args[0]));
//        System.out.println(size);

        GraphADT g = RunBenchmark.alloc(args[1], size);
        g = RunBenchmark.readGraph(args[0], args[1]);
        System.out.print(args[1] + "\t" + args[0] + "\t");
        System.out.print(g.order() + "\t");
        System.out.print(g.size() + "\t");
        System.out.println(RunBenchmark.runAlgorithm(g, args[2]));


    }
}
