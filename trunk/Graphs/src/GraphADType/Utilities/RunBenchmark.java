/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphADType.Utilities;

import GraphADT_2nd_try.ADTConverter;
import GraphADT_2nd_try.Boruvka2;
import GraphADT_2nd_try.Kruskal2;
import GraphADT_2nd_try.Prim2;
import GraphADT_2nd_try.UndirectedColoredGraph;
import GraphADT_2nd_try.UndirectedGraph;
import GraphADType.Algorithms.BoruvkaADT;
import GraphADType.Algorithms.BoruvkaADT2;
import GraphADType.Algorithms.KruskalADT_UF;
import GraphADType.Algorithms.PrimADT;
import GraphADType.GraphADT;
import GraphADType.GraphArraySucc;
import GraphADType.GraphMapAdj;
import GraphADType.GraphMapSucc;
import GraphADType.GraphMatrix;
import GraphADType.Support.Constants;
import GraphIO.GraphInput;
import JGraphTest.BoruvkaJgraph;
import JGraphTest.JGraphConverter;
import JGraphTest.KruskalJgraph;
import JGraphTest.PrimJgraph;
import JungTest.BoruvkaJung;
import JungTest.EdgeJ;
import JungTest.JungConverter;
import JungTest.KruskalJung;
import JungTest.PrimJung;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import org.jgrapht.graph.WeightedMultigraph;

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
            int total = 0;
            System.out.print("mstW:" + mst.getMstWeight(Constants.intArith, total) + "\t");
            return (end - begin);
        } else if (alg.equals("boruvka2")) {
            BoruvkaADT2 b = new BoruvkaADT2();
            long begin = System.currentTimeMillis();
            GraphADT mst = b.getMst(g);
            long end = System.currentTimeMillis();
//            System.out.println("boruvka time:\t" + (end - begin));
            int total = 0;
            System.out.print("mstW:" + mst.getMstWeight(Constants.intArith, total) + "\t");
            return (end - begin);
        } else if (alg.equals("kruskal")) {
            KruskalADT_UF b = new KruskalADT_UF();
            long begin = System.currentTimeMillis();
            GraphADT mst = b.getMst(g);
            long end = System.currentTimeMillis();
//            System.out.println("boruvka time:\t" + (end - begin));
            int total = 0;
            System.out.print("mstW:" + mst.getMstWeight(Constants.intArith, total) + "\t");
            return (end - begin);
        } else if (alg.equals("prim")) {
            PrimADT b = new PrimADT();
            long begin = System.currentTimeMillis();
            GraphADT mst = b.getMst(g);
            long end = System.currentTimeMillis();
//            System.out.println("prim time:\t" + (end - begin));
            int total = 0;
            System.out.print("mstW:" + mst.getMstWeight(Constants.intArith, total) + "\t");
            return (end - begin);
        }
        return -1;
    }

    public static long runLibraries(String lib, String alg, GraphADT adt) {

        if (lib.equals("jung")) {
            JungConverter jconv = new JungConverter();
            UndirectedSparseGraph<String, EdgeJ<Integer>> g = new UndirectedSparseGraph<String, EdgeJ<Integer>>();
            g = jconv.ADTtoJung(adt);

            if (alg.equals("boruvka")) {
                BoruvkaJung bor = new BoruvkaJung();
                long begin = System.currentTimeMillis();
                UndirectedSparseGraph<String, EdgeJ<Integer>> mst = bor.getMst(g);
                long end = System.currentTimeMillis();
                System.out.print("\tmstW: " + bor.getMstWeight(mst, Constants.intArith) + "\t");
                return (end - begin);
            }
            if (alg.equals("kruskal")) {
                KruskalJung bor = new KruskalJung();
                long begin = System.currentTimeMillis();
                UndirectedSparseGraph<String, EdgeJ<Integer>> mst = bor.getMst(g);
                long end = System.currentTimeMillis();
                System.out.print("\tmstW: " + bor.getMstWeight(mst, Constants.intArith) + "\t");
                return (end - begin);
            }
            if (alg.equals("prim")) {
                PrimJung bor = new PrimJung();
                long begin = System.currentTimeMillis();
                UndirectedSparseGraph<String, EdgeJ<Integer>> mst = bor.getMst(g);
                long end = System.currentTimeMillis();
                System.out.print("\tmstW: " + bor.getMstWeight(mst, Constants.intArith) + "\t");
                return (end - begin);
            }
        }
        if (lib.equals("jgraph")) {
            JGraphConverter jconv = new JGraphConverter();
            WeightedMultigraph<String, Integer> g = jconv.ADTtoJGraph(adt);

            if (alg.equals("boruvka")) {
                BoruvkaJgraph bor = new BoruvkaJgraph();
                long begin = System.currentTimeMillis();
                WeightedMultigraph<String, Integer> mst = bor.getMst(g);
                long end = System.currentTimeMillis();
                System.out.print("\tW: " + bor.getMstWeight(mst, Constants.intArith) + "\t");
                return (end - begin);
            }
            if (alg.equals("kruskal")) {
                KruskalJgraph bor = new KruskalJgraph();
                long begin = System.currentTimeMillis();
                WeightedMultigraph<String, Integer> mst = bor.getMst(g);
                long end = System.currentTimeMillis();
                System.out.print("\tmstW: " + bor.getMstWeight(mst, Constants.intArith) + "\t");
                return (end - begin);
            }
            if (alg.equals("prim")) {
                PrimJgraph bor = new PrimJgraph();
                long begin = System.currentTimeMillis();
                WeightedMultigraph<String, Integer> mst = bor.getMst(g);
                long end = System.currentTimeMillis();
                System.out.print("\tmstW: " + bor.getMstWeight(mst, Constants.intArith) + "\t");
                return (end - begin);
            }
        }
        if (lib.equals("myNewLib")) {
            ADTConverter jconv = new ADTConverter();
            UndirectedGraph<String, EdgeJ<Integer>> graph = jconv.ADT2New(adt);
            //***this conversion should be more versatile
            UndirectedColoredGraph<String,EdgeJ<Integer>> g = new UndirectedColoredGraph<String, EdgeJ<Integer>>(graph);
            if (alg.equals("prim")) {
                Prim2 prim = new Prim2();
                long begin = System.currentTimeMillis();
                UndirectedGraph<String, EdgeJ<Integer>> mst = prim.getMst(g);
                long end = System.currentTimeMillis();
                System.out.print("\tmstW: " + prim.getMstWeight(mst, Constants.myNewLibArith) + "\t");
                return (end - begin);
            }
            if (alg.equals("kruskal")) {
                Kruskal2 kruskal = new Kruskal2();
                long begin = System.currentTimeMillis();
                UndirectedGraph<String, EdgeJ<Integer>> mst = kruskal.getMst(g);
                long end = System.currentTimeMillis();
                System.out.print("\tmstW: " + kruskal.getMstWeight(mst, Constants.myNewLibArith) + "\t");
                return (end - begin);
            }
            if (alg.equals("boruvka")) {
                Boruvka2 boruvka = new Boruvka2();
                long begin = System.currentTimeMillis();
                UndirectedGraph<String, EdgeJ<Integer>> mst = boruvka.getMst(g);
                long end = System.currentTimeMillis();
                System.out.print("\tmstW: " + boruvka.getMstWeight(mst, Constants.myNewLibArith) + "\t");
                return (end - begin);
            }
        }

        return -1;
    }

    // le e converte o grafo para o tipo determinado
    public static GraphADT readGraph(String filename, String type) {
//        System.out.println(filename);
        GraphInput gin = new GraphInput(filename);
        GraphADT gg = gin.readGraphADT();
        if (type.equals("mapsucc")) {
            return ((GraphMapAdj) gg).toGraphMapSucc();
        }
        if (type.equals("arraysucc")) {
            return ((GraphMapAdj) gg).toGraphArraySucc();
        }
        if (type.equals("matrix")) {
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
        GraphADT g = RunBenchmark.alloc(args[1], size);
        g = RunBenchmark.readGraph(args[0], args[1]);
        System.out.print(args[1] + "\t" + args[2] + "\t");
        System.out.print(g.order() + "\t");
        System.out.print(g.size() + "\t");

        if (args[1].equals("jung") || args[1].equals("jgraph") || args[1].equals("myNewLib")) {
            System.out.println(runLibraries(args[1], args[2], g));
        } else {
            System.out.println(RunBenchmark.runAlgorithm(g, args[2]));
        }
    }
}
