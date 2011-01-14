/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphADT_2nd_try;

import GraphADType.Support.Constants;
import GraphIO.GraphInput;
import JGraphTest.BoruvkaJgraph;
import JGraphTest.JGraphConverter;
import JGraphTest.KruskalJgraph;
import JGraphTest.PrimJgraph;
import JungTest.BoruvkaJung;
import JungTest.JungConverter;
import JungTest.KruskalJung;
import JungTest.PrimJung;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import org.jgrapht.graph.WeightedMultigraph;

/**
 *
 * @author nuno
 */
public class RunTests {

    public static long run(String lib, String alg, BaseGraph graph) {

        if (lib.equals("jung")) {
            JungConverter jconv = new JungConverter();
            UndirectedSparseGraph<String, EdgeJ<Integer>> g = new UndirectedSparseGraph<String, EdgeJ<Integer>>();
            g = jconv.BaseGraphtoJung(graph);

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
            WeightedMultigraph<String, Integer> g = jconv.BaseGraph2JGraph(graph);

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
        if (lib.equals("regular") || lib.equals("indexed")) {

            BaseGraph g = convertType(lib, alg, graph);
            if (alg.equals("prim")) {
                Prim2 prim = new Prim2();
                long begin, end;
                BaseGraph<String, EdgeJ<Integer>> mst;
                if (lib.equals("regular")) {
                    begin = System.currentTimeMillis();
                    mst = prim.getMst((UndirectedColoredGraph) g);
                    end = System.currentTimeMillis();
                } else { // if "indexed"
                    begin = System.currentTimeMillis();
                    mst = prim.getMst_IndexedColored((UndirectedIndexedColoredGraph) g);
                    end = System.currentTimeMillis();
                }
                System.out.print("\tmstW: " + prim.getMstWeight(mst, Constants.myNewLibArith) + "\t");
                return (end - begin);
            }

            if (alg.equals("prim_visited")) {
                Prim2VisitedArray bor = new Prim2VisitedArray();
                long begin = System.currentTimeMillis();
                BaseGraph<String, EdgeJ<Integer>> mst = bor.getMst(g);
                long end = System.currentTimeMillis();
                System.out.print("\tmstW: " + bor.getMstWeight(mst, Constants.myNewLibArith) + "\t");
                return (end - begin);
            }

            if (alg.equals("kruskal")) {
                Kruskal2 kruskal = new Kruskal2();
                long begin = System.currentTimeMillis();
                BaseGraph<String, EdgeJ<Integer>> mst = kruskal.getMst(g);
                long end = System.currentTimeMillis();
                System.out.print("\tmstW: " + kruskal.getMstWeight(mst, Constants.myNewLibArith) + "\t");
                return (end - begin);
            }
            if (alg.equals("boruvka")) {
                Boruvka2 boruvka = new Boruvka2();
                long begin = System.currentTimeMillis();
                BaseGraph<String, EdgeJ<Integer>> mst = boruvka.getMst(g);
                long end = System.currentTimeMillis();
                System.out.print("\tmstW: " + boruvka.getMstWeight(mst, Constants.myNewLibArith) + "\t");
                return (end - begin);
            }
        }
        return -1;
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

    public static BaseGraph convertType(String type, String alg, BaseGraph bg) {

        if (type.equals("indexed")) {
            if (alg.equals("prim_visited")) {
                return new UndirectedIndexedGraph(bg);
            }
            return new UndirectedIndexedColoredGraph(bg);

        }
        if (type.equals("regular")) {
            if (alg.equals("prim_visited")) {
                return bg;
            }
            return new UndirectedColoredGraph(bg);
        }
        return null;
    }

    public static void main(String[] args) {
        /*
         * args[0] -> graph to read and execute
         * args[1] -> graph implementation to use
         * args[2] -> algorithm to run
         */
        String filename = args[0];
        String lib = args[1];
        String alg = args[2];

        GraphInput in = new GraphInput(filename);
        BaseGraph g = in.readGraph2();

        System.out.print(lib + "\t" + alg + "\t");
        System.out.print(g.getOrder() + "\t");
        System.out.print(g.getSize() + "\t");

        System.out.println(RunTests.run(lib, alg, g));
    }
}
