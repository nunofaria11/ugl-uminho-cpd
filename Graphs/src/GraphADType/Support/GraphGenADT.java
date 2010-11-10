/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphADType.Support;

import GraphADType.GraphADT;
import Utilities.Constants;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Random GraphADT generator. Focus on:
 * <ol>
 * <li>parameter interface where the random function for Y-typed values must be
 *      defined.
 * </li>
 *
 * <li>the T type must enumerable in order to be generated automatically and
 *      must also have a method in the interface mentioned on 1).
 * </li>
 * </ol>
 * @author nuno
 */
public class GraphGenADT<T, Y extends Comparable<Y>> {

    final Y MAX_WEIGHT;
    final Y MIN_WEIGHT;
    final TYRandomizer<T, Y> yrand;
    private int nEdges;
    private double prob;

    public GraphGenADT(int numV, Y MAX_WEIGHT, Y MIN_WEIGHT, TYRandomizer<T, Y> yrand) {
        this.MAX_WEIGHT = MAX_WEIGHT;
        this.MIN_WEIGHT = MIN_WEIGHT;
        this.yrand = yrand;
        this.nEdges = Constants.possibleEdgesNum(numV);
        this.prob = -1;
    }

    public GraphGenADT(double p, Y MAX_WEIGHT, Y MIN_WEIGHT, TYRandomizer<T, Y> yrand) {
        this.MAX_WEIGHT = MAX_WEIGHT;
        this.MIN_WEIGHT = MIN_WEIGHT;
        this.yrand = yrand;
        this.nEdges = -1;
        this.prob = p;
    }

    public GraphADT generate(GraphADT g) {
        if (nEdges != -1) // 1)
        {
            return this.gnm(g);
        } else // 2)
        {
            return this.gnp(g);
        }
    }

    private GraphADT gnm(GraphADT g) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private GraphADT gnp(GraphADT g) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public Collection<T> randomNodes(TArithmeticOperations<T> arith, Collection<T> alphabet, int N) {
        int numN = 0;
        int level = 1;

        return null;
    }

    /**
     * Auxiliary function to be called by node-id generator function.
     * @param alpha Alphabet to consider
     * @param root Structure to store nodes
     * @param N Number of nodes wanted
     * @param level Level in tree in which this recursive function is
     * @param arith_str Operation-handler for type <i>T</i>
     * @return Combinations of the alphabet in an N-ary tree.
     */
    private NTreeADT<T> recursiveT(
            ArrayList<T> alpha,
            NTreeADT<T> root,
            int N,
            int level,
            TArithmeticOperations<T> arith) {
        //
        if (Math.pow(alpha.size(), level) > N) {
            return null;
        }

        for (int i = 0; i < alpha.size(); i++) {
            NTreeADT child = new NTreeADT(arith.Cat(root.data, alpha.get(i)));
            recursiveT(alpha, child, N - root.size(), level + 1, arith);
            root.addChild(child);
        }
        return root;
    }

    public Collection<T> createNodeIds(
            Collection<T> alpha,
            TArithmeticOperations<T> arith,
            int N) {
        Collection<T> nodeIds = new ArrayList<T>();
        NTreeADT<T> root = new NTreeADT<T>(arith.null_element());
        root = recursiveT(new ArrayList<T>(alpha), root, N + 1, 0, arith);
        ArrayList<T> aux = (ArrayList<T>) root.BFSElements();
        // now use only N elemnts, since the recursive function may return more than needed
        for (int i = 1; i <= N; i++) {
            nodeIds.add(aux.get(i));
        }
        return nodeIds;
    }

    /*
     * Test function for strings
     */
    private static NTreeADT recursiveStr(
            ArrayList<String> alpha,
            NTreeADT<String> root,
            int N,
            int level) {
        if (Math.pow(alpha.size(), level) > N) {
            return null;
        }

        for (int i = 0; i < alpha.size(); i++) {
            NTreeADT child = new NTreeADT(root.data + alpha.get(i));
            recursiveStr(alpha, child, N - root.size(), level + 1);
            root.addChild(child);
        }
        return root;
    }

    public static void main(String[] args) throws InterruptedException {


//        int nNodes = 10;
//        Collection<Integer> alphabet = new ArrayList<Integer>(nNodes);
//        for (int i = 0; i < nNodes; i++) {
//            System.out.println(GraphADType.Support.Constants.randTYIntDouble.random(20.0, 50.0));
//            alphabet.add(i);
//        }

        ArrayList<String> alpha = new ArrayList<String>();
        alpha.add("Aa");
        alpha.add("Bb");
        alpha.add("Cc");

        NTreeADT<String> tree = new NTreeADT<String>("");
//        NTreeADT<String> res = GraphGenADT.recursiveStr(alpha, tree, 8, 0);
//        System.out.println(res.BFSElements());
        TArithmeticOperations<String> arith_str = new TArithmeticOperations<String>() {

            public String Add(String a, String b) {
                return a + b;
            }

            public String Cat(String a, String b) {
                return a + b;
            }

            public String null_element() {
                return "";
            }
        };
        GraphGenADT<String, Double> ggen = new GraphGenADT<String, Double>(0, Double.MAX_VALUE, Double.MIN_NORMAL, null);
        System.out.println(ggen.createNodeIds(alpha, arith_str, 12));


    }
}
