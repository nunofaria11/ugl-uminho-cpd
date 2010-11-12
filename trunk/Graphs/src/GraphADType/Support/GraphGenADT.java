/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphADType.Support;

import GraphADType.GraphADT;
import GraphADType.GraphMapSucc;
import NodeOriented.Node;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

/**
 * Random GraphADT generator. Focus on:
 * <ol>
 * <li>parameter interface where the random function for Y-typed values must be
 *      defined.
 * </li>
 * </ol>
 * @author nuno
 */
public class GraphGenADT<T, Y extends Comparable<Y>> {

    final Y MAX_WEIGHT;
    final Y MIN_WEIGHT;
    final YRandomizer<Y> yrand;
    final TArithmeticOperations<T> arith;
    final ArrayList<T> alphabet;
    private int nEdges;
    private double prob;

    public GraphGenADT(
            int numV,
            Y MAX_WEIGHT,
            Y MIN_WEIGHT,
            YRandomizer<Y> yrand,
            TArithmeticOperations<T> arith,
            ArrayList<T> alphabet) {
        this.MAX_WEIGHT = MAX_WEIGHT;
        this.MIN_WEIGHT = MIN_WEIGHT;
        this.yrand = yrand;
        this.nEdges = Constants.possibleEdgesNum(numV);
        this.prob = -1;
        this.arith = arith;
        this.alphabet = alphabet;
    }

    public GraphGenADT(
            double p,
            Y MAX_WEIGHT,
            Y MIN_WEIGHT,
            YRandomizer<Y> yrand,
            TArithmeticOperations<T> arith,
            ArrayList<T> alphabet) {
        this.MAX_WEIGHT = MAX_WEIGHT;
        this.MIN_WEIGHT = MIN_WEIGHT;
        this.yrand = yrand;
        this.nEdges = -1;
        this.prob = p;
        this.arith = arith;
        this.alphabet = alphabet;
    }

    /**
     * Random graph generation function, that builds a random graph according to
     * a connection probability or the number of edges wanted. The Erdős–Rényi
     * model is followed, and the sub-models are:
     * <ol>
     *  <li>In the G(n, M) model, a graph is chosen uniformly at random from the
     *      collection of all graphs which have <b>n</b> nodes and <b>M</b> edges.
     * For example, in the G(3, 2) model, each of the three possible graphs on
     * three vertices and two edges are included with probability 1/3.</li>
     * <br>
     *  <li>In the G(n, p) model, a graph is thought to be constructed by
     *      connecting nodes randomly. Each edge is included in the graph
     *      with probability <b>p</b>, with the presence or absence of any two
     *      distinct edges in the graph being independent.</li>
     * </ol>
     * @param g GraphADT instance
     * @param N number of nodes
     * @return Random graph
     */
    public GraphADT generate(GraphADT g, int N) {
        if (nEdges != -1) // 1)
        {
            return this.gnm(g, N);
        } else // 2)
        {
            return this.gnp(g, N);
        }
    }

    private GraphADT gnm(GraphADT g, int N) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private GraphADT gnp(GraphADT g, int N) {
        Random random = new Random(System.currentTimeMillis());
        // create nodes ids and insert nodes in graph
        ArrayList<T> t_node_ids = (ArrayList<T>) createNodeIds(alphabet, arith, N);
        ArrayList<Node<T>> allnodes = (ArrayList<Node<T>>) g.insertAllNodes(t_node_ids);
        ArrayList<Node<T>> visited = new ArrayList<Node<T>>();
        // create random egdes while the graph is not connected
        while (!g.connected()) {
            // For each node
            for (Node<T> i : allnodes) {
                visited.add(i);
                // For every other node
                ArrayList<Node<T>> available = new ArrayList<Node<T>>(allnodes);
                available.removeAll(visited);
                for (Node<T> j : available) {
                    // If random indicates this edge exists
                    if (random.nextDouble() <= this.prob) {
                        // Create and edge between node i and node j
                        Y random_weight = this.yrand.random(MIN_WEIGHT, MAX_WEIGHT);
                        g.addEdge(i, j, random_weight);
                    }
                }
            }
        }
        return g;
    }

    /**
     * Auxiliary function to be called by node-id generator function.
     * @param alpha Alphabet to consider
     * @param root Structure to store nodes
     * @param N Number of nodes wanted
     * @param level Level in tree in which this recursive function is
     * @param arith Operation-handler for type <i>T</i>
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

    /**
     * Generates <b>N</b> graphs (<i>N = n_areas</i>) and then joins them in
     * order to generate a graph with <b>n_areas</b> dense areas.
     * @param g Dummy graph to denote the instance type of the resulting graph
     * @param n_areas Number of density areas
     * @param total_edges Number of total edges in the cluster
     * @param total_nodes Number of total nodes in the cluster
     * @return Clustered graph
     */
    public GraphADT createCluster(
            GraphADT<T, Y> g,
            int areas,
            int total_edges,
            int total_nodes) {
        GraphADT cluster = g;
        //

        //
        return cluster;
    }

    public static void main(String[] args) throws InterruptedException {

        TArithmeticOperations<String> strArith = Constants.strArith;

        YRandomizer<Double> dRand = Constants.randDouble;
        YRandomizer<Integer> iRand = Constants.randInteger;

        ArrayList<String> alpha = new ArrayList<String>();
        alpha.add("A");
        alpha.add("B");
        alpha.add("C");
        alpha.add("D");
        alpha.add("E");
        alpha.add("F");

        GraphGenADT<String, Integer> ggen = new GraphGenADT<String, Integer>(
                0.75,
                90,
                5,
                iRand,
                strArith,
                alpha);

        GraphMapSucc<String, Integer> g = new GraphMapSucc<String, Integer>();

        g = (GraphMapSucc<String, Integer>) ggen.generate(g, 20);
        System.out.println(g.toString());
    }
}
